package com.ocean.main;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ocean.entity.Employee;
import com.ocean.service.EmployeeService;

public class MainClass
{
	public static void main(String[] args)
	{
		System.out.println("load context");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Employee em = new Employee();
        em.setName("Ocean");
        EmployeeService emService = (EmployeeService) context.getBean("employeeServiceImpl");
        emService.persistEmployee(em);
        System.out.println("Saved name :" + emService.findEmployeeById(1L).getName());       
        em.setName("Life");
        emService.updateEmployee(em);
        System.out.println("Updated age :" + emService.findEmployeeById(1L).getName());
        emService.deleteEmployee(em);
        System.out.println("Employee Deleted");
        context.close();
	}
}
