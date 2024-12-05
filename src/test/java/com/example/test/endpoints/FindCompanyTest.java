package com.example.test.endpoints;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import com.example.test.controllers.FindCompanyController;
import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.output.ActiveOfficer;
import com.example.test.records.output.CompanyWithOfficers;
import com.example.test.records.output.FindCompanyResult;
import com.example.test.records.shared.Address;

@SpringBootTest
@ActiveProfiles("test")
@EnableWireMock(@ConfigureWireMock(port = 9090))
public class FindCompanyTest {

	@Autowired
	private FindCompanyController uut;
	
	@Value("classpath:com/example/test/endpoints/company-search-result.json")
	private Resource mockCompanySearchResult;
	@Value("classpath:com/example/test/endpoints/officer-search-result.json")
	private Resource mockOfficerSearchResult;
	
	@Test
	void shouldAssembleExpectedOutput() throws IOException {
		stubFor(get("/Search?Query=06500244").willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(mockCompanySearchResult.getContentAsString(Charset.forName("UTF-8")))));
		stubFor(get("/Officers?CompanyNumber=06500244").willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(mockOfficerSearchResult.getContentAsString(Charset.forName("UTF-8")))));
		
		var result = uut.findCompany(new FindCompanyInput(null, "06500244"), false);

		var expected = 				new FindCompanyResult(
				1,
				new CompanyWithOfficers[] {
					new CompanyWithOfficers(
						"06500244", 
						"ltd", 
						"BBC LIMITED", 
						"dissolved", 
						"2008-02-11", 
						new Address("Retford", "DN22 0AD", "Boswell Cottage Main Street", "North Leverton", "England"), 
						new ActiveOfficer[] { new ActiveOfficer(
							"ANTLES, Kerri",
							"director",
							"2017-04-01",
							new Address("Leeds", "LS2 7JF", "The Leeming Building", "Vicar Lane", "England")
						)}
					)
				}
			);
		
		assertThat(result)
			.isEqualTo(
					expected
			);
	}
	
	
}
