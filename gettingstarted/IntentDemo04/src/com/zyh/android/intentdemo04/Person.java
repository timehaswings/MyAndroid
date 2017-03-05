package com.zyh.android.intentdemo04;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Person implements Parcelable{

	private String name;
	private String address;
	private int age;

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
	public String toString() {
		return "Person [name=" + name + ", address=" + address + ", age=" + age + "]";
	}

	public Person(String name, String address, int age) {
		super();
		this.name = name;
		this.address = address;
		this.age = age;
	}

	public Person() {
		super();
	}
	
	private Person(Parcel source){
		// 读取数据的顺序是和写入的顺序一致
		this.name = source.readString(); // 读取名字
		this.address = source.readString();// 读取地址
		this.age = source.readInt(); // 读取年龄
	}

	@Override // 没有什么作用 默认返回就可以了
	public int describeContents() {
		return 0;
	}

	@Override // 将数据写入到内存里面
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name); // 写名字
		dest.writeString(address); // 写地址
		dest.writeInt(age); // 写年龄
		Log.d("zhou", "..writeToParcel..写入数据");
	}

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override // 从保存的内存读取数据 1.传入的参数就是保存了数据的对象
		public Person createFromParcel(Parcel source) {
			Log.d("zhou", "..createFromParcel..读取数据");
			return new Person(source);
		}

		@Override // 构建指定泛型类型的数组
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
}
