package com.example.test.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.output.CompanyWithOfficers;

@Service
public class CompanyFindService {
	
	@Autowired
	private CompanyFetchService companyFetchService;
	
	@Autowired
	private OfficerFetchService officerFetchService;
	
	public CompanyWithOfficers[] findCompany(FindCompanyInput input, boolean onlyActive) {
		var companies = companyFetchService.fetchCompany(input, onlyActive);
		return Arrays.stream(companies)		
			.parallel()
			.map(company -> new CompanyWithOfficers(company, officerFetchService.fetchOfficers(company.company_number())))
			.toArray(CompanyWithOfficers[]::new);

	}
	
	
}
