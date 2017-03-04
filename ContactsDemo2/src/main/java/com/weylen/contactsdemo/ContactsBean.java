package com.weylen.contactsdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhou on 2016/4/15.
 */
public class ContactsBean implements Parcelable{

    private String name;
    private String phone;
    private int rawContactId;

    public ContactsBean(){

    }

    public ContactsBean(String name, String phone, int rawContactId) {
        this.name = name;
        this.phone = phone;
        this.rawContactId = rawContactId;
    }

    protected ContactsBean(Parcel in) {
        name = in.readString();
        phone = in.readString();
        rawContactId = in.readInt();
    }

    public static final Creator<ContactsBean> CREATOR = new Creator<ContactsBean>() {
        @Override
        public ContactsBean createFromParcel(Parcel in) {
            return new ContactsBean(in);
        }

        @Override
        public ContactsBean[] newArray(int size) {
            return new ContactsBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRawContactId() {
        return rawContactId;
    }

    public void setRawContactId(int rawContactId) {
        this.rawContactId = rawContactId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeInt(rawContactId);
    }
}
