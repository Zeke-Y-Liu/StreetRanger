package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class People {
	
	private Long _id;
	
	public People(Long id, String name, char gender, int age, String email) {
		this._id = id;
		this._name = name;
		this._age = age;
		this._gender = gender;
		this._email = email;
	}
	
	private String _name;
	private char _gender;
	private int _age;
	private String _email;

	private List<Tag> _tags;

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public char getGender() {
		return _gender;
	}

	public void setGender(char gender) {
		this._gender = gender;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		this._email = email;
	}

	public int getAge() {
		return _age;
	}

	public void setAge(int age) {
		this._age = age;
	}

	public List<Tag> getTags() {
		if(_tags == null) {
			_tags = new ArrayList<Tag>();
		}
		return _tags;
	}

	public void setTags(List<Tag> tags) {
		this._tags = tags;
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		this._id = id;
	}
	
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(7, 13)
	       .append(_name)
	       .append(_age)
	       .append(_gender)
	       .append(_email)
	       .toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) { return false; }
		People p = (People) obj;
		return new EqualsBuilder()
			.append(_name, p.getName())
			.append(_age, p.getAge())
			.append(_gender, p.getGender())
			.append(_email, p.getEmail()).isEquals();
	}
}
