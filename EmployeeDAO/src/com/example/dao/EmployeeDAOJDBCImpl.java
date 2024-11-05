package com.example.dao;

import java.sql.*;
import java.util.ArrayList;

import com.example.model.Employee;

public class EmployeeDAOJDBCImpl implements EmployeeDAO {

	private Connection con;
	
	public EmployeeDAOJDBCImpl() {
		String url = "jdbc:mysql://localhost:3306/EmployeeDB";
		String username = "root";
		String password = "abc123";
		try {
			con = DriverManager.getConnection(url, username, password);
		} catch(SQLException ex) {
			System.err.println("資料庫連線失敗！"+ex);
			System.exit(0);
		}
	}

	@Override
	public void close() throws Exception {
		try {
			if(con!=null)
				con.close();
		} catch(SQLException ex) {
			System.err.println("資料庫連線關閉失敗！+ex");
		}
	}

	@Override
	public void add(Employee emp) throws DAOException {
		String sql = "INSERT INTO EMPLOYEE VALUES (?,?,?,?,?)";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, emp.getId());
			pstmt.setString(2, emp.getFirstName());
			pstmt.setString(3, emp.getLastName());
			pstmt.setDate(4, new java.sql.Date(emp.getBirthDate().getTime()));
			pstmt.setFloat(5, emp.getSalary());
			if(pstmt.executeUpdate()!=1)
				System.out.println("員工新增失敗！");
		} catch(SQLException ex) {
			throw new DAOException("資料庫新增失敗！", ex);
		}
	}

	@Override
	public void update(Employee emp) throws DAOException {
		String sql = "UPDATE EMPLOYEE SET FIRSTNAME=?, LASTNAME=?, BIRTHDATE=?, SALARY=? WHERE ID=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(5, emp.getId());
			pstmt.setString(1, emp.getFirstName());
			pstmt.setString(2, emp.getLastName());
			pstmt.setDate(3, new java.sql.Date(emp.getBirthDate().getTime()));
			pstmt.setFloat(4, emp.getSalary());
			if(pstmt.executeUpdate()!=1)
				System.out.println("員工更新失敗！");
		} catch(SQLException ex) {
			throw new DAOException("資料庫更新失敗！", ex);
		}
	}

	@Override
	public void delete(int id) throws DAOException {
		String sql = "DELETE FROM EMPLOYEE WHERE ID=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			if(pstmt.executeUpdate()!=1)
				System.out.println("員工刪除失敗！");
		} catch(SQLException ex) {
			throw new DAOException("資料庫刪除失敗！", ex);
		}
	}

	@Override
	public Employee findById(int id) throws DAOException {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID=?";
		Employee emp = null;
		try(PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				emp = new Employee(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getDate("BIRTHDATE"), rs.getFloat("SALARY"));
			return emp;
		} catch(SQLException ex) {
			throw new DAOException("資料庫查詢失敗！", ex);
		}
	}

	@Override
	public Employee[] getAllEmployees() throws DAOException {
		String sql = "SELECT * FROM EMPLOYEE";
		ArrayList<Employee> emps = new ArrayList<>();
		try(Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
				emps.add(new Employee(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getDate("BIRTHDATE"), rs.getFloat("SALARY")));
			return emps.toArray(new Employee[0]);
		} catch(SQLException ex) {
			throw new DAOException("資料庫查詢失敗！", ex);
		}
	}

}
