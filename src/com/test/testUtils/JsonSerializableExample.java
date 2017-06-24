package com.test.testUtils;

import java.util.HashMap;
import java.util.Map;

import com.Serialize.Interface.JsonSerializable;

public class JsonSerializableExample implements JsonSerializable{

	private static int count=0;

	private int id;
	private String position;
	private String name;
	private double salary;

	private String otherField;
	private int nonReqdField;
	private char [] inheritedNonReqdField;
	
	public JsonSerializableExample(int id, String name, String position) {
		count++;
		this.id =id;
		this.name =name;
		this.position =position;
	}
	
	@Override
	public Object prepareJSON() {
		Map<String, Object> map = new HashMap<>();
		//Add fields you need with their name as key
		map.put("position", position);
		map.put("id", id);
		map.put("name", name);
		map.put("salary", salary);
		return map;
	}
	
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	public int getId() {
		return id;
	}


	public static int getCount() {
		return count;
	}


	
	
}
