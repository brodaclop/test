package com.example.test.services;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.truapi.Company;
import com.example.test.records.truapi.CompanySearchResult;

@Service
public class CompanyFetchService {

	@Autowired
	private DataFetchService dataFetchService;

	public Company[] fetchCompany(FindCompanyInput input, boolean onlyActive) {
		var queryString = Optional.ofNullable(input.companyNumber()).orElse(input.companyName());
		var companySearchResponse = dataFetchService.fetch(CompanySearchResult.class, "/Search?Query={query}", queryString);
		return Arrays
				.stream(companySearchResponse.items())
				.filter(company -> !onlyActive || company.company_status() == "active")
				.toArray(Company[]::new);
	}
}
