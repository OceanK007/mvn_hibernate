package com.ocean.oto.uni.foreign;

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
		// You can persist address first then persist person. Or
		// Hibernate will auto persist the address when you save person object.
				
		Person person = new Person();
		person.setName("Ocean");
		person.setAddress(address);		
		session.save(person);
		
		transaction.commit();
		session.close();
		
		System.out.println("Data successfully saved");
		
		//############ Codes start - Retrieving object using .get() method ############//
		{
			Session session2 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data");
			Person p = (Person) session2.get(Person.class, 1L);
			System.out.println("Person name: "+p.getName());
			System.out.println("Person addresss: "+p.getAddress().getAddressDetail());
			// In lazy loading, session needs to be opened as it'll hit database again to retrieve addressDetail. 
			session2.close();
		}
		//############ Codes end - Retrieving object with session opened ############//
				
		//############ Codes start - Retrieving object using HQL with Join ############//
		{
			Session session3 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data using HQL with Join");
			// We are using package name along with Person class because there are multiple Person classes
			Query query = session3.createQuery("FROM com.ocean.oto.uni.foreign.Person p INNER JOIN FETCH p.address WHERE p.id = :id");
			// No need to use DISTINCT(p) since there is one to one mapping here. Inner join will result only one row.
			query.setLong("id", 1L);
			List<Person> persons = query.list();
			Person per = persons.get(0);
			session3.close();
			System.out.println("Person name: "+per.getName());
			System.out.println("Person addresss: "+per.getAddress().getAddressDetail());
		}
		//############ Codes end - Retrieving object using HQL to fetch another object ############//
		
		//############ Codes start - Retrieving object using Native SQL with Join ############//
		{
			Session session3 = sessionFactory.openSession();
			System.out.println("------------------------------ Retrieving data using Native SQL with Join");
			Query query = session3.createSQLQuery("SELECT p.*,a.* FROM oto_uni_foreign_person p INNER JOIN oto_uni_foreign_address a ON p.address_id = a.id WHERE p.id = :id")
						  .addEntity("p", Person.class)
						  .addJoin("a", "p.address");
			query.setLong("id", 1L);
			List<Object[]> records = query.list();
			for(Object[] row: records)
			{
				Person per = (Person) row[0];
				Address add = (Address) row[1];
				System.out.println("Person name: "+per.getName());
				System.out.println("Person addresss: "+per.getAddress().getAddressDetail());
			}
			session3.close();			
			//############ Codes end - Retrieving object using HQL to fetch another object ############//
		}
	}
}
