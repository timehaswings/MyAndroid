package com.zyh.android.listviewfooterview;

public class MessageInfo {

	private String palyer, event, code, name, type;
	private int count;

	public String getPalyer() {
		return palyer;
	}

	public void setPalyer(String palyer) {
		this.palyer = palyer;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public MessageInfo(String palyer, String event, String code, String name, String type, int count) {
		super();
		this.palyer = palyer;
		this.event = event;
		this.code = code;
		this.name = name;
		this.type = type;
		this.count = count;
	}

	public MessageInfo() {
		super();
	}

}
