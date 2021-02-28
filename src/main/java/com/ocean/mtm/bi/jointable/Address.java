package com.ocean.mtm.bi.jointable;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="mtm_bi_jointable_address")
@Table(name="mtm_bi_jointable_address")
public class Address implements Serializable
{
	private static final long serialVersionUID = -4845447990535701578L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	// This is not the owner table since it got mappedBy attribute in @ManyToMany annotation.
	@ManyToMany(mappedBy="addresses", fetch=FetchType.LAZY, cascade=CascadeType.ALL)		// Default FetchType LAZY
	private List<Person> persons;
	
	@Column(name="address_detail")
	private String addressDetail;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<Person> getPersons()
	{
		return persons;
	}

	public void setPersons(List<Person> persons)
	{
		this.persons = persons;
	}

	public String getAddressDetail()
	{
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail)
	{
		this.addressDetail = addressDetail;
	}
}
