package com.example.domain;

import java.util.ArrayList;

public class Manager extends Employee implements RegularStaff {
	private String deptName;
	private ArrayList employees;
	private double baseBonus = 100000;

	public String getDeptName() {
		return deptName;
	}

	public Manager(String name, String ssn, double salary, String dept, Branch branch) {
		super(name, ssn, salary, branch);
		deptName = dept;
		employees = new ArrayList();
	}

	public ArrayList getEmployees() {
		return employees;
	}

	public boolean addEmployee(Employee e) {
		if(employees.contains(e))
			return false;
		employees.add(e);
		return true;
	}
	
	public boolean removeEmployee(Employee e) {
		if(!employees.contains(e))
			return false;
		employees.remove(e);
		return true;
	}
	
	public String getStaffDetails() {
		StringBuilder sb = new StringBuilder();
		if(!employees.isEmpty()) {
			sb.append(String.format("%s管理員工：",this.getName()));
			for(Object obj:employees) {
				if(obj instanceof Employee) {
					Employee e = (Employee) obj;
					sb.append(String.format("%s %d, ",e.getName(), e.getEmpId()));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return super.toString() +
		"管理部門：" + deptName + "\n" +
		getStaffDetails();
	}
	
	@Override
	public double getPay() {
		return this.getSalary() + employees.size()*2000;
	}

	@Override
	public double getBonus() {
		return this.baseBonus*calcPerMultiplier();
	}
}
