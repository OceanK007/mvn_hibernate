package com.ocean.otm.bi.foreign;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="otm_bi_foreign_address")
@Table(name="otm_bi_foreign_address")
public class Address implements Serializable
{
	private static final long serialVersionUID = -4845447990535701578L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	// This is the owner table since we has used @JoinColumn here. We can get person info as well as address
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)	// Default fetchType is LAZY
	@JoinColumn(name="person_id")
	private Person person;

	
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

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
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
