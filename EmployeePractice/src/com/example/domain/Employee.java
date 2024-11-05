package com.example.domain;

import java.text.NumberFormat;
import java.util.Objects;

public abstract class Employee {
	private static int nextId = 101;
	private int empId;
	private String name = "Name";
	private String ssn = "A100000000";
	private double salary = 27490;
	private Branch branch;
	protected NumberFormat formatter = NumberFormat.getInstance();

	public Employee(String name, String ssn, double salary, Branch branch) {
		this.empId = nextId;
		nextId++;
		if (name != null && name.trim().length()!=0)
			this.name = name;
		if (ssn != null && ssn.trim().length()!=0)
			this.ssn = ssn;
		if(salary>27490)
			this.salary = salary;
		this.branch = branch;
	}

	@Override
	public int hashCode() {
		return Objects.hash(empId, ssn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return empId == other.empId && Objects.equals(ssn, other.ssn);
	}

	public int getEmpId() {
		return this.empId;
	}

	public String getName() {
		return this.name;
	}

	public String getSsn() {
		return this.ssn;
	}

	public double getSalary() {
		return this.salary;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setName(String name) {
		if (name != null && name.trim().length()!=0)
			this.name = name;
		else
			System.out.printf("請輸入文字！%n");
	}

	public void raiseSalary(double increase) {
		if (increase < 0)
			System.out.printf("請輸入正的數值！%n");
		else
			salary += increase;
	}

	@Override
	public String toString() {
		return "=====員工資料====="+"\n"+
	           "編號：" + empId + "\n" +
			   "姓名：" + name + "\n" +
	           "SSN：" + ssn + "\n" +
			   "薪水：" + this.branch.getCurrency() + formatter.format(salary) + "元" + "\n";
	}
	
	public abstract double getPay();
}
