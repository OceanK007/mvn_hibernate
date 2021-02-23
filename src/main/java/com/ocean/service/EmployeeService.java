package com.ocean.service;

import com.ocean.entity.Employee;

public interface EmployeeService
{
	void persistEmployee(Employee employee);
	 
    Employee findEmployeeById(Long id);
 
    void updateEmployee(Employee employee);
 
    void deleteEmployee(Employee employee);
}
