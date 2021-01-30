package com.ocean.mto.uni.foreign;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
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
		// Starting transaction
		Transaction transaction = session.beginTransaction();
		
		// If in @JoinColumn nullable=true (by default), so Person object can be saved without Address object.
		Address address = new Address();
		address.setAddressDetail("Delhi");
				
		Person person = new Person();
		person.setName("Ocean");
		person.setAddress(address);
		session.save(person);

		Person person2 = new Person();
		person2.setName("Life");
		person2.setAddress(address);
		session.save(person2);

		transaction.commit();
		session.close();
		
		System.out.println("Data successfully saved");
		
		//############ Codes start - Retrieving object using .get() method ############//
		{
			// Here you will face (n+1) problem. So use HQL, criteria or native SQL with JOIN
			Session session2 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data");
			Person p = (Person) session2.get(Person.class, 1L);
			System.out.println("Person name: "+p.getName());
			System.out.println("Person addresss: "+p.getAddress().getAddressDetail());

			Person p2 = (Person) session2.get(Person.class, 2L);
			System.out.println("Person name: "+p2.getName());
			System.out.println("Person addresss: "+p2.getAddress().getAddressDetail());
			// In lazy loading, session needs to be opened as it'll hit database again to retrieve addressDetail. 
			session2.close();
		}
		//############ Codes end - Retrieving object with session opened ############//
				
		//############ Codes start - Retrieving object using HQL with Join ############//
		{
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(2L);
			Session session3 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data using HQL to fetch another object");
			Query query = session3.createQuery("FROM com.ocean.mto.uni.foreign.Person p INNER JOIN FETCH p.address WHERE p.id IN (:ids)");
			query.setParameterList("ids", idList);
			List<Person> personList = query.list();
			session3.close();
			for(Person p: personList)
			{
				System.out.println("Person name: "+p.getName());
				System.out.println("Person addresss: "+p.getAddress().getAddressDetail());
			}
		}
		//############ Codes end - Retrieving object using HQL to fetch another object ############//
	}
}
