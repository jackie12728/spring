package com.example.domain;

public class Admin extends Employee {
	private int hours;
	public Admin(String name, String ssn, double salary, int hours, Branch branch) {
		super(name, ssn, salary, branch);
		this.hours = hours;
	}

	@Override
	public double getPay() {
		return this.getSalary()*hours/160;
	}
}
