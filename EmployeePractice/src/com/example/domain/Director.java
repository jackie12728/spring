package com.example.domain;

public class Director extends Manager implements RegularStaff {
	private double budget;
	private double baseBonus = 500000;

	public Director(String name, String ssn, double salary, String dept, Branch branch, double budget) {
		super(name, ssn, salary, dept, branch);
		this.budget = budget;
	}
	
	public double getBudget() {
		return budget;
	}
	
	@Override
	public String toString() {
		return super.toString() +
		String.format("預算金額：%s%.2f元%n", this.getBranch().getCurrency(), budget);
	}
	
	@Override
	public double getPay() {
		return this.getSalary() + this.getEmployees().size()*10000;
	}
	
	@Override
	public double getBonus() {
		return this.baseBonus*calcPerMultiplier();
	}
}
