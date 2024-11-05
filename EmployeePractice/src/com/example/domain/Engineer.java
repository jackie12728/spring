package com.example.domain;

public class Engineer extends Employee implements RegularStaff {
	private String[] skills = new String[5];
	private int skillCount;

	public Engineer(String name, String ssn, double salary, Branch branch) {
		super(name, ssn, salary, branch);
	}
	
	public void addSkill(String skill) {
		if(skillCount<5) {
			skills[skillCount] = skill;
			skillCount++;
		} else
			System.out.printf("只能註冊5個技能%n");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		if(skills.length>0) {
			sb.append("技能：");
			for(String skill:skills) {
				if(skill!=null)
					sb.append(skill);
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	@Override
	public double getPay() {
		return this.getSalary() + skills.length*3000;
	}
	
	@Override
	public double getBonus() {
		return getSalary()*calcPerMultiplier();
	}
}
