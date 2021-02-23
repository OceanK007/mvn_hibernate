package com.ocean.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.dao.EmployeeDao;
import com.ocean.entity.Employee;
import com.ocean.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
	@Autowired
	EmployeeDao employeeDAO;
	
	@Override
	@Transactional
	public void persistEmployee(Employee employee)
	{
		employeeDAO.persistEmployee(employee);
	}

	@Override
	@Transactional
	public Employee findEmployeeById(Long id)
	{
		return employeeDAO.findEmployeeById(id);
	}

	@Override
	@Transactional
	public void updateEmployee(Employee employee)
	{
		employeeDAO.updateEmployee(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(Employee employee)
	{
		employeeDAO.deleteEmployee(employee);
	}
}
