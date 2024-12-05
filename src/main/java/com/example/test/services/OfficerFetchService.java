package com.example.test.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.test.records.output.ActiveOfficer;
import com.example.test.records.truapi.OfficerListResult;

@Service
public class OfficerFetchService {
	@Autowired
	private DataFetchService dataFetchService;
	
	public ActiveOfficer[] fetchOfficers(String companyNumber) {
		var result = dataFetchService.fetch(OfficerListResult.class, "/Officers?CompanyNumber={companyNumber}", companyNumber);
		return Arrays
				.stream(result.items())
				.filter(officer -> officer.resigned_on() == null)
				.map(ActiveOfficer::new)
				.toArray(ActiveOfficer[]::new);
	}

}
