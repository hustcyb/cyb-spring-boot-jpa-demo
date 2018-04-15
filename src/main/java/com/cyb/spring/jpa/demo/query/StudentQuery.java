package com.cyb.spring.jpa.demo.query;

public class StudentQuery {

	private String name;
	
	private Byte minAge;
	
	private Byte maxAge;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getMinAge() {
		return minAge;
	}

	public void setMinAge(Byte minAge) {
		this.minAge = minAge;
	}

	public Byte getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Byte maxAge) {
		this.maxAge = maxAge;
	}
}
