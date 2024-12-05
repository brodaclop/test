package com.example.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.test.records.output.ActiveOfficer;
import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Officer;
import com.example.test.records.truapi.OfficerListResult;

@ActiveProfiles("test")
@SpringBootTest
public class OfficerFetchServiceTest {

	private static final String SEARCH_ENDPOINT_URI = "/Officers?CompanyNumber={companyNumber}";
	private static final Officer TEST_ACTIVE_OFFICER= new Officer("Big Officer", "Big Cheese", "2001-02-03", null, new Address("London", "W1J 7NT", "1", "", "UK"));
	private static final Officer TEST_INACTIVE_OFFICER  = new Officer("Little Officer", "Little Cheese", "2001-02-03", "2007-08-09", new Address("London", "W1J 7NT", "1", "", "UK"));
	
	@Autowired
	private OfficerFetchService uut;
	
	@MockBean
	private DataFetchService dataFetchService;

	@Test
	void shouldFilterOutInactiveOfficers() {
		doReturn(new OfficerListResult(new Officer[] { TEST_INACTIVE_OFFICER, TEST_ACTIVE_OFFICER })).when(dataFetchService).fetch(OfficerListResult.class, SEARCH_ENDPOINT_URI, "1234");
		var result = uut.fetchOfficers("1234");

		assertThat(result).usingRecursiveAssertion().isEqualTo(new ActiveOfficer[] { new ActiveOfficer(TEST_ACTIVE_OFFICER) });
	}

}
