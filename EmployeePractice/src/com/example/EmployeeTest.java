package com.example;

import com.example.domain.*;

public class EmployeeTest {

	public static void main(String[] args) {
		Employee[] emps = new Employee[5]; 
		emps[0] = new Admin("Sean", "A123456789", 50000, 200, Branch.London);
		emps[1] = new Engineer("Amy", "B210987654", 70000, Branch.Paris);
		emps[2] = new Admin("View", "C111222333", 10500, 180, Branch.Taipei);
		emps[3] = new Manager("Sonic", "D444555666", 101000, "The", Branch.Tokyo);
		emps[4] = new Director("Tiobe", "E777888999", 15600, "Index", Branch.London, 330000);
		
		emps[0].setName("Bean");
		emps[0].raiseSalary(300.27);
		emps[1].setName("English");
		emps[1].raiseSalary(27000);
		if(emps[1] instanceof Engineer) {
			Engineer eng = (Engineer) emps[1];
			eng.addSkill("Java");
			eng.addSkill("Android");
		}
		
		if(emps[3] instanceof Manager) {
			Manager man = (Manager) emps[3];
			man.addEmployee(emps[0]);
			man.addEmployee(emps[1]);
			man.addEmployee(emps[2]);
		}
		
		if(emps[4] instanceof Manager)
			((Manager) emps[4]).addEmployee(emps[3]);
		
		for(int i=0; i<emps.length; i++) {
			System.out.print(emps[i]);
			System.out.println("本月薪資：" + String.format("%s%.2f", emps[i].getBranch().getCurrency(), emps[i].getPay()) + "元");
			if(emps[i] instanceof RegularStaff) {
				System.out.printf("%s年終獎金：%s%.2f元%n", emps[i].getName(), emps[i].getBranch().getCurrency(), ((RegularStaff)emps[i]).getBonus());
				System.out.printf("尾牙摸彩抽中：%s%n", RegularStaff.getLuckDraw());
			}
		}
	}
}