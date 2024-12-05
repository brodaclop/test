package com.example.test.records.truapi;

import com.example.test.records.shared.Address;

public record Company(String company_number, String company_type, String title, String company_status, String date_of_creation, Address address) {
	
}
