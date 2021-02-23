package com.ocean.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ocean.dao.EmployeeDao;
import com.ocean.entity.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao
{
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public void persistEmployee(Employee employee)
	{
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.save(employee);
		t.commit();
		session.close();
	}

	@Override
	public Employee findEmployeeById(Long id)
	{
		return (Employee) sessionFactory.getCurrentSession().get(Employee.class, id);
	}

	@Override
	public void updateEmployee(Employee employee)
	{
		sessionFactory.getCurrentSession().update(employee);
	}

	@Override
	public void deleteEmployee(Employee employee)
	{
		sessionFactory.getCurrentSession().delete(employee);
	}
}
