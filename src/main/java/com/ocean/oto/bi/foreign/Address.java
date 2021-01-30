package com.ocean.oto.bi.foreign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="oto_bi_foreign_address")
@Table(name="oto_bi_foreign_address")
public class Address implements Serializable
{
	private static final long serialVersionUID = -4845447990535701578L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="address_detail")
	private String addressDetail;

	@OneToOne(mappedBy = "address")
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
