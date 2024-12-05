package com.example.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.records.input.FindCompanyInput;
import com.example.test.records.output.FindCompanyResult;
import com.example.test.services.CompanyFindService;

@RestController
public class FindCompanyController {
	
	@Autowired
	private CompanyFindService companyFindService;


	@RequestMapping(
			path = "find-company",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public FindCompanyResult findCompany(
			@RequestBody FindCompanyInput input,
			@RequestParam(defaultValue = "false") boolean onlyActive
		) {
		
		var companies = companyFindService.findCompany(input, onlyActive);
		
		return new FindCompanyResult(companies.length, companies);
	}
	
}
