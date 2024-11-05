package com.example.dao;

import java.util.SortedMap;
import java.util.TreeMap;

import com.example.model.Employee;

public class EmployeeDAOMapImpl implements EmployeeDAO {
	private SortedMap<Integer, Employee> employees = new TreeMap<>();

	@Override
	public void close() throws Exception {
		System.out.println("關閉資源...");
	}

	@Override
	public void add(Employee emp) throws DAOException {
		int id = emp.getId();
		if (employees.containsKey(id))
			throw new DAOException(id + " 員工已存在，新增失敗！");
		employees.put(id, emp);
	}

	@Override
	public void update(Employee emp) throws DAOException {
		int id = emp.getId();
		if (employees.containsKey(id))
			throw new DAOException(id + " 員工已存在，修改失敗！");
		employees.put(id, emp);
	}

	@Override
	public void delete(int id) throws DAOException {
		if (!employees.containsKey(id))
			throw new DAOException(id + " 員工不存在，刪除失敗！");
		employees.remove(id);
	}

	@Override
	public Employee findById(int id) throws DAOException {
		Employee emp = employees.get(id);
		if (!employees.containsKey(id))
			throw new DAOException(id + " 員工不存在，查詢失敗！");
		return emp;
	}

	@Override
	public Employee[] getAllEmployees() throws DAOException {
		return employees.values().toArray(new Employee[0]);
	}

}
