package com.example.test.records.output;

import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Officer;

public record ActiveOfficer(String name, String officer_role, String appointed_on, Address address) {
	public ActiveOfficer(Officer officer) {
		this(officer.name(), officer.officer_role(), officer.appointed_on(), officer.address());
	}
}
