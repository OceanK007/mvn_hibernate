package com.ocean.oto.bi.foreign;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="oto_bi_foreign_person")
@Table(name="oto_bi_foreign_person")
public class Person implements Serializable
{
	private static final long serialVersionUID = 7355712628370235477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	// This is the owner table since it has @JoinColumn. we can get person info as well as address (since it got address id)
	@OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, optional=false) // Default FetchType is EAGER
	@JoinColumn(name="address_id", referencedColumnName = "id")
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
