package com.zyh.android.list_work01;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageBean implements Parcelable{
	
	private int id;
	private String title, time, content, sender, receiver;
	private boolean isRead; // 表示读取状态
	
	/**
	 * 记住：在bean里面必须存在一个无参数的构造器
	 */
	public MessageBean(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override // 内容描述 默认返回就行
	public int describeContents() {
		return 0;
	}

	@Override // 保存数据
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(time);
		dest.writeString(sender);
		dest.writeString(receiver);
		dest.writeInt(isRead ? 1 : -1);
	}
	
	private MessageBean(Parcel source){
		id = source.readInt();
		title = source.readString();
		content = source.readString();
		time = source.readString();
		sender = source.readString();
		receiver = source.readString();
		isRead = source.readInt() == 1 ? true : false;
	}
	
	public static final Parcelable.Creator<MessageBean> CREATOR = new Parcelable.Creator<MessageBean>() {
		@Override // 读取数据
		public MessageBean createFromParcel(Parcel source) {
			return new MessageBean(source);
		}

		@Override
		public MessageBean[] newArray(int size) {
			return new MessageBean[size];
		}
	};
}
