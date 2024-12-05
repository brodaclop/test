package com.example.test.records.output;

import java.util.Arrays;
import java.util.Objects;

public record FindCompanyResult(int total_results, CompanyWithOfficers[] items) {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(items);
		result = prime * result + Objects.hash(total_results);
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
		FindCompanyResult other = (FindCompanyResult) obj;
		return Arrays.equals(items, other.items) && total_results == other.total_results;
	}

}
