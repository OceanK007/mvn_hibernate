package com.ocean;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseDao
{
	public static void main(String[] args)
	{
		Properties properties = new Properties();
		try
		{
			properties.load(DatabaseDao.class.getClassLoader().getResourceAsStream("db.properties"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Properties: "+properties);
		// Create configuration instance
		Configuration configuration = new Configuration();

		// Pass hibernate configuration file
		configuration.configure("hibernate.cfg.xml").mergeProperties(properties);// or use .addProperties(properties)

		// Since version 4.x, service registry is being used
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build(); 

		// Before hibernate v4 SessionFactory object can be easily be created by:
		// SessionFactory sessionFactory = configuration.buildSessionFactory();
		
		// Create session factory instance
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		// Open session
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Student student = new Student();
		student.setFirstName("Ocean");
		student.setLastName("Life");
		
		session.persist(student);
		
		transaction.commit();
		session.close();
		
		System.out.println("Data successfully saved");
	}
}
