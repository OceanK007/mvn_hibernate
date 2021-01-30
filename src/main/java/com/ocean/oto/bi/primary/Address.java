package com.ocean.oto.bi.primary;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="oto_bi_primary_address")
@Table(name="oto_bi_primary_address")
public class Address implements Serializable
{
	private static final long serialVersionUID = -4845447990535701578L;

	// There is not need of @GeneratedValue annotation as person id will be used as id value
	@Id
	@Column(name="person_id")
	private Long id;
	
	@Column(name="address_detail")
	private String addressDetail;

	// This is the owner table since it has @JoinColumn. we can get person info as well as address (since it got person id)
	// @MapsId annotation, which indicates that the primary key values will be copied from the Person entity
	@OneToOne
	@MapsId
	@JoinColumn(name="person_id")
    private Person person;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getAddressDetail()
	{
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail)
	{
		this.addressDetail = addressDetail;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}
}
