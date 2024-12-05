package com.example.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Company;
import com.example.test.records.truapi.CompanySearchResult;

@ActiveProfiles("test")
@SpringBootTest
public class CompanyFetchServiceTest {

	private static final String SEARCH_ENDPOINT_URI = "/Search?Query={query}";
	private static final Company TEST_ACTIVE_COMPANY = new Company("1234", "ltd", "Active company", "active", "2001-01-01", new Address("London", "W1J 7NT", "1", "", "UK"));
	private static final Company TEST_INACTIVE_COMPANY = new Company("2345", "ltd", "Inactive company", "dissolved", "2001-01-01", new Address("London", "W1J 7NT", "1", "", "UK"));
	
	@Autowired
	private CompanyFetchService uut;
	
	@MockBean
	private DataFetchService dataFetchService;

	@Test
	void shouldQueryByName_whenCompanyNumberIsAbsent() {
		doReturn(new CompanySearchResult(new Company[0])).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "test_company");
		var result = uut.fetchCompany(new FindCompanyInput("test_company", null), false);

		assertThat(result).isEmpty();
	}

	@Test
	void shouldQueryByNumber_whenCompanyNameIsAbsent() {
		doReturn(new CompanySearchResult(new Company[0])).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "test_company_no");
		var result = uut.fetchCompany(new FindCompanyInput(null, "test_company_no"), false);

		assertThat(result).isEmpty();
	}

	@Test
	void shouldQueryByNumber_whenBothNameAndNumberArePresent() {
		doReturn(new CompanySearchResult(new Company[0])).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "test_company_no");
		doThrow(AssertionError.class).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "test_company");
		var result = uut.fetchCompany(new FindCompanyInput("test_company", "test_company_no"), false);

		assertThat(result).isEmpty();
	}
	
	@Test
	void shouldFilterOutInactiveCompanies_whenSearchingForActiveOnly() {
		doReturn(new CompanySearchResult(new Company[] { TEST_INACTIVE_COMPANY, TEST_ACTIVE_COMPANY })).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "company");
		var result = uut.fetchCompany(new FindCompanyInput("company", null), true);

		assertThat(result).usingRecursiveAssertion().isEqualTo(new Company[] { TEST_ACTIVE_COMPANY });
	}

	@Test
	void shouldReturnAllCompanies_whenNotSearchingForActiveOnly() {
		doReturn(new CompanySearchResult(new Company[] { TEST_INACTIVE_COMPANY, TEST_ACTIVE_COMPANY })).when(dataFetchService).fetch(CompanySearchResult.class, SEARCH_ENDPOINT_URI, "company");
		var result = uut.fetchCompany(new FindCompanyInput("company", null), false);

		assertThat(result).usingRecursiveAssertion().isEqualTo(new Company[] { TEST_INACTIVE_COMPANY, TEST_ACTIVE_COMPANY });
	}
	
}
