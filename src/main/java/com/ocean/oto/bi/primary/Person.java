package com.ocean.oto.bi.primary;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="oto_bi_primary_person")
@Table(name="oto_bi_primary_person")
public class Person implements Serializable
{
	private static final long serialVersionUID = 7355712628370235477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	// The mappedBy attribute is now moved to the Person class since the foreign key is now present in the address table.
	// @PrimaryKeyJoinColumn annotation, which indicates that the primary key of the Person entity is used as the foreign key value for the associated Address entity
	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "person") // Default FetchType is EAGER
	@PrimaryKeyJoinColumn
	private Address address;
	
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

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
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
