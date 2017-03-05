package com.zyh.android.intentdemo04;

import java.io.Serializable;

public class Person2 implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String adress;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person2(String name, String adress, int age) {
		super();
		this.name = name;
		this.adress = adress;
		this.age = age;
	}

	public Person2() {
		super();
	}

	@Override
	public String toString() {
		return "Person2 [name=" + name + ", adress=" + adress + ", age=" + age + "]";
	}
}
