package com.ranger.common;

import java.util.ArrayList;
import java.util.List;

public class People {

	public People() {
		
	}
	
	public People(String name, char gender, int age, String email) {
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

}
