package com.zyh.android.listmultiitemdemo;

/**
 * 信息实体类
 * 
 * @author Administrator
 */
public class MessageEntity {

	private int id; // 信息的id
	private String sender; // 发出者
	private String receiver; // 接收者
	private String content; // 内容
	private String time; // 发出的时间
	private String imgPath; // 头像的路径
	/**
	 * 0表示别人发的 TYPE_LEFT
	 * 1表示自己发的 TTPE_RIGHT
	 */
	private int type = -1; // 类型 主要指的是是否由自己发出的

	public MessageEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
