package com.zyh.android.work_loginregister;

import android.os.Parcel;
import android.os.Parcelable;

public class RegisterInfo implements Parcelable{

	private String account, password, birthday, address;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public RegisterInfo(String account, String password, String birthday, String address) {
		super();
		this.account = account;
		this.password = password;
		this.birthday = birthday;
		this.address = address;
	}

	public RegisterInfo() {
		super();
	}
	
	private RegisterInfo(Parcel source){
		this.account = source.readString();
		this.password = source.readString();
		this.birthday = source.readString();
		this.address = source.readString();
	}

	@Override
	public String toString() {
		return "RegisterInfo [account=" + account + ", password=" + password + ", birthday=" + birthday + ", address="
				+ address + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(account);
		dest.writeString(password);
		dest.writeString(birthday);
		dest.writeString(address);
	}
	
	public static final Parcelable.Creator<RegisterInfo> CREATOR = new Parcelable.Creator<RegisterInfo>() {
		
		@Override
		public RegisterInfo[] newArray(int size) {
			return new RegisterInfo[size];
		}
		
		@Override
		public RegisterInfo createFromParcel(Parcel source) {
			return new RegisterInfo(source);
		}
	};
}
