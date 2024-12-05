package com.example.test.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.output.ActiveOfficer;
import com.example.test.records.output.CompanyWithOfficers;
import com.example.test.records.output.FindCompanyResult;
import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Company;
import com.example.test.services.CompanyFindService;

@SpringBootTest
@ActiveProfiles("test")
public class FindCompanyControllerTest {

	private static final Company TEST_COMPANY = new Company("1234", "ltd", "Test company", "active", "2001-01-01", new Address("London", "W1J 7NT", "1", "", "UK"));
	private static final ActiveOfficer TEST_OFFICER = new ActiveOfficer("An Officer", "Big Cheese", "2002-03-04", new Address("London", "W1J 7NT", "1", "", "UK"));
	
	@Autowired
	private FindCompanyController uut;
	
	@MockBean
	private CompanyFindService companyFindService;
	
	@Test
	void shouldReturnCorrectLength_whenResponseIsEmpty() {
		var companyArray = new CompanyWithOfficers[0];
		doReturn(companyArray).when(companyFindService).findCompany(any(), anyBoolean());
		var result = uut.findCompany(new FindCompanyInput("test company", null), false);
		assertThat(result).isEqualTo(new FindCompanyResult(0, companyArray));
	}

	@Test
	void shouldReturnCorrectLength_whenResponseIsNotEmpty() {
		var companyArray = new CompanyWithOfficers[] { new CompanyWithOfficers(TEST_COMPANY, new ActiveOfficer[] { TEST_OFFICER })};
		doReturn(companyArray).when(companyFindService).findCompany(any(), anyBoolean());
		var result = uut.findCompany(new FindCompanyInput("test company", null), false);
		assertThat(result).isEqualTo(new FindCompanyResult(1, companyArray));
	}
	
	
}
