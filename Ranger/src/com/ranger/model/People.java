package com.ranger.model;

import java.util.List;

public class People {

	private String _name;
	private String _gender;
	private String _age;
	private String _mail;

	private List<Tag> _tags;

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public String getGender() {
		return _gender;
	}

	public void setGender(String gender) {
		this._gender = gender;
	}

	public String getMail() {
		return _mail;
	}

	public void setMail(String mail) {
		this._mail = mail;
	}

	public String getAge() {
		return _age;
	}

	public void setAge(String age) {
		this._age = age;
	}

	public List<Tag> getTags() {
		return _tags;
	}

	public void setTags(List<Tag> tags) {
		this._tags = tags;
	}

}
