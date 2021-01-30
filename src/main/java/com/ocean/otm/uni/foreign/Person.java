package com.ocean.otm.uni.foreign;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "otm_uni_foreign_person") // Providing name so, it won't throw DuplicationMappingexception
@Table(name="otm_uni_foreign_person")
public class Person implements Serializable
{
	private static final long serialVersionUID = 7355712628370235477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	// This is the owner table since it has @JoinColumn. we can get person info as well as address
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL) // Default FetchType is LAZY
	@JoinColumn(name="person_id", referencedColumnName = "id")	// person_id will be created in address table.
	// If you don't define @JoinColumn then an extra table will be created with name otm_uni_foreign_person_otm_uni_foreign_address (Refer @JoinTable)
	/** Since we are doing one-to-many mapping, means one person can have many addresses. But two persons can't have same address.
	* Means if a person has 4 different address then we have to make 4 entries in person table using 4 different address_id and 
	* it's not a good idea. But if we put person_id in address table then we don't have to make multiple entries in person table. 
	* That's why person_id is created in address table. **/
	private List<Address> addresses;
	
	@Column(name = "name")
	private String name;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<Address> getAddresses()
	{
		return addresses;
	}

	public void setAddresses(List<Address> addresses)
	{
		this.addresses = addresses;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
