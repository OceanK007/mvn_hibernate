package com.ocean.otm.bi.foreign;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "otm_bi_foreign_person") // Providing name so, it won't throw DuplicationMappingexception
@Table(name="otm_bi_foreign_person")
public class Person implements Serializable
{
	private static final long serialVersionUID = 7355712628370235477L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@OneToMany(mappedBy="person", fetch=FetchType.LAZY, cascade = CascadeType.ALL) // Default FetchType is LAZY
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
