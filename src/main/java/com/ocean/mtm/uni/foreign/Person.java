package com.ocean.mtm.uni.foreign;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "mtm_uni_foreign_person") // Providing name so, it won't throw DuplicationMappingexception
@Table(name="mtm_uni_foreign_person")
public class Person implements Serializable
{
	private static final long serialVersionUID = 7355712628370235477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	/**	You know that in one-to-many mapping, two persons can't have same address. But in many-to-many mapping, two persons can have same address. 
	One solution was to put person_id on address table, but this will create duplicate entries (same address but different person_id) since two persons can have same address.
	So a separate table is made to make mapping of person_id and address_id separately. In this way there won't be any duplicate entries in person or in address tables.**/
	// This is the owner table since it has @JoinColumn. we can get person info as well as address
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)	// Default FetchType is LAZY
	//@JoinColumn(name="address_id")				// it's useless even you define it, no column will be created	
	@JoinTable(name="mtm_uni_foreign_person_address", joinColumns=@JoinColumn(name="person_id"), inverseJoinColumns=@JoinColumn(name="address_id"))
	private List<Address> addresses;
	// Default name for name attribute = mtm_uni_foreign_person_mtm_uni_foreign_address (ownerTableName_nonOwnerTableName)
	// Default name for joinColumns attribute = mtm_uni_foreign_person_id (ownerTableName_primaryKeyNameOfOwnerTable)
	// Default name for inverseJoinColumns attribute = mtm_uni_foreign_address_id (propertyName_primaryKeyNameOfNonOwnerTable)
	
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
