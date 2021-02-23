package com.ocean.dao;

import com.ocean.entity.Employee;

public interface EmployeeDao
{
	 void persistEmployee(Employee employee);
     
     Employee findEmployeeById(Long id);
      
     void updateEmployee(Employee employee);
      
     void deleteEmployee(Employee employee);
}
