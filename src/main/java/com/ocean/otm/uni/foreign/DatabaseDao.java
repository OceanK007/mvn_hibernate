package com.ocean.otm.uni.foreign;

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
		Address address2 = new Address();
		address2.setAddressDetail("Pune");
		// You can persist address first then persist person. Or
		// Hibernate will auto persist the address when you save person object.
				
		List<Address> addressList = new ArrayList<Address>();
		addressList.add(address);
		addressList.add(address2);

		Person person = new Person();
		person.setName("Ocean");
		person.setAddresses(addressList);		
		session.save(person);
		
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
			System.out.println("Person addresss: "+p.getAddresses().get(0).getAddressDetail());
			System.out.println("Person addresss2: "+p.getAddresses().get(1).getAddressDetail());
			// In lazy loading, session needs to be opened as it'll hit database again to retrieve addressDetail. 
			session2.close();
		}
		//############ Codes end - Retrieving object with session opened ############//
				
		//############ Codes start - Retrieving object using HQL with Join ############//
		{
			Session session3 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data using HQL with Join");
			// We are using package name along with Person class because there are multiple Person classes
			//Query query = session3.createQuery("FROM com.ocean.otm.uni.foreign.Person p INNER JOIN FETCH p.addresses WHERE p.id = :id");
			Query query = session3.createQuery("SELECT DISTINCT(p) FROM com.ocean.otm.uni.foreign.Person p INNER JOIN FETCH p.addresses WHERE p.id = :id");
			// If you don't use DISTINCT(p) then two person objects will be retrieved. Since there are two addresses for person with id 1. But since you 
			// are using hibernate bi-directional mappings, you don't need 2 records. Use getAddresses() to get all addresses related to person with id 1
			query.setLong("id", 1L);
			List<Person> persons = query.list();
			session3.close();
			for(Person per : persons)
			{
				System.out.println("Person name: "+per.getName());
				System.out.println("Person addresss: "+per.getAddresses().get(0).getAddressDetail());
				System.out.println("Person addresss2: "+per.getAddresses().get(1).getAddressDetail());
			}
		}
		//############ Codes end - Retrieving object using HQL to fetch another object ############//
	}
}
