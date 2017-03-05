package com.weylen.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleClientServer {
	// http单向交互模式 客服端-->发出请求-->服务器接收接收请求然后响应(无法完成即时通讯)
	// 可以使用http来做一个心跳包模式 每隔一段时间请求一次服务器 耗电量 耗内存 流量
	// TCP/TP 面向连接(Socket) 面向无连接(UDP)
	// Socket（客服端） 两个客服端同时在线
	// ServerSocket（服务端）
	public static void main(String args[]){
		new SingleClientServer().startServer();
	}
	
	/**
	 * 启动服务器
	 */
	public void startServer(){
		try {
			// 1~1024系统端口号(80)	
			ServerSocket serverSocket = new ServerSocket(12321);
			// 阻塞方法 当有一个客服端连接上此服务器之后即打开方法 并且返回连接上此服务器的客服端对象
			Socket client = serverSocket.accept();
			InetAddress inetAddress = client.getInetAddress();
			System.out.println("address:"+inetAddress.getHostAddress());
			new ReadFromClient(client).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 循环读取客服端发送的消息
	 */
	private class ReadFromClient extends Thread{
		
		private Socket client;
		private ReadFromClient(Socket client){
			this.client = client;
		}
		
		@Override
		public void run(){
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());
				String line = null;
				System.out.println("开始读取客服端的数据");
				while((line = reader.readLine()) != null){
					System.out.println("服务器接收的数据:"+line);
					printWriter.println("ni sb me");
					printWriter.flush();
				}
				System.out.println("读取客服端数据结束");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
