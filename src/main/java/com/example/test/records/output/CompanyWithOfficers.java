package com.example.test.records.output;

import java.util.Arrays;
import java.util.Objects;

import com.example.test.records.shared.Address;
import com.example.test.records.truapi.Company;

public record CompanyWithOfficers(String company_number, String company_type, String title, String company_status, String date_of_creation, Address address, ActiveOfficer[] officers) {
	public CompanyWithOfficers(Company company, ActiveOfficer[] officers) {
		this(company.company_number(), company.company_type(), company.title(), company.company_status(), company.date_of_creation(), company.address(), officers);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(officers);
		result = prime * result
				+ Objects.hash(address, company_number, company_status, company_type, date_of_creation, title);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CompanyWithOfficers other = (CompanyWithOfficers) obj;
		return Objects.equals(address, other.address) && Objects.equals(company_number, other.company_number)
				&& Objects.equals(company_status, other.company_status)
				&& Objects.equals(company_type, other.company_type)
				&& Objects.equals(date_of_creation, other.date_of_creation) && Arrays.equals(officers, other.officers)
				&& Objects.equals(title, other.title);
	}
}
