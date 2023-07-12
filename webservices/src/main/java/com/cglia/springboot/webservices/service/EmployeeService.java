package com.cglia.springboot.webservices.service;

import java.util.List;

import com.cglia.springboot.webservices.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	public Employee findById(int id);
	public int save(Employee employee);
	public int update(int id, Employee employee);
	public void deleteById(int id);
	
}
