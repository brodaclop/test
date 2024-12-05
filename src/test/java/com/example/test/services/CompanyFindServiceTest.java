package com.example.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.output.ActiveOfficer;
import com.example.test.records.output.CompanyWithOfficers;
import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Company;

@ActiveProfiles("test")
@SpringBootTest
class CompanyFindServiceTest {
	
	@Autowired
	private CompanyFindService uut;
	
	@MockBean
	private CompanyFetchService companyFetchService;
	
	@MockBean
	private OfficerFetchService officerFetchService;
	
	private static final Company TEST_COMPANY = new Company("1234", "ltd", "Test company", "active", "2001-01-01", new Address("London", "W1J 7NT", "1", "", "UK"));
	private static final ActiveOfficer TEST_OFFICER = new ActiveOfficer("An Officer", "Big Cheese", "2002-03-04", new Address("London", "W1J 7NT", "1", "", "UK"));
	
	@Test
	void shouldReturnEmptyArray_whenCompanyNotFound() {
		var testInput = new FindCompanyInput("Test company", null);
		doReturn(new Company[0]).when(companyFetchService).fetchCompany(testInput, false);
		var company = uut.findCompany(testInput, false);
		assertThat(company).isEmpty();
	}

	@Test
	void shouldReturnCompany_whenOfficersNotFound() {
		var testInput = new FindCompanyInput(TEST_COMPANY.title(), null);
		doReturn(new Company[] { TEST_COMPANY }).when(companyFetchService).fetchCompany(testInput, false);
		doReturn(new ActiveOfficer[0]).when(officerFetchService).fetchOfficers(TEST_COMPANY.company_number());
		var company = uut.findCompany(testInput, false);
		assertThat(company).usingRecursiveComparison().isEqualTo(new CompanyWithOfficers[] { new CompanyWithOfficers(TEST_COMPANY, new ActiveOfficer[0])});
	}

	@Test
	void shouldReturnCompanyAndOfficers_whenOfficersFound() {
		var testInput = new FindCompanyInput(TEST_COMPANY.title(), null);
		doReturn(new Company[] { TEST_COMPANY }).when(companyFetchService).fetchCompany(testInput, false);
		doReturn(new ActiveOfficer[] { TEST_OFFICER }).when(officerFetchService).fetchOfficers(TEST_COMPANY.company_number());
		var company = uut.findCompany(testInput, false);
		assertThat(company).usingRecursiveComparison().isEqualTo(new CompanyWithOfficers[] { new CompanyWithOfficers(TEST_COMPANY, new ActiveOfficer[] { TEST_OFFICER })});
	}
	
	
}
