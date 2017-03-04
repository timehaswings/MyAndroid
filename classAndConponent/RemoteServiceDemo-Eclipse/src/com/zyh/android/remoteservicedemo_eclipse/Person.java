package com.zyh.android.remoteservicedemo_eclipse;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

	private String name, address;
	private int age;
	
	public Person(){}
	
	private Person(Parcel from){
		name = from.readString();
		address = from.readString();
		age = from.readInt();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(address);
		dest.writeInt(age);
	}

	public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}

		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		}
	};
}
