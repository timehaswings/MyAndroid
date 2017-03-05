package com.weylen.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientServer {
	
	private int index; // 连接上服务器的序号
	public static final int MAX_SIZE = 1; // 定义聊天室最大的人数
	private List<Socket> clients = new ArrayList<>();
	private ServerSocket serverSocket;
	private boolean isWhile = true; // 判断是否循环是否一直运行
	// 创建一个缓存的线程池
	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	public static void main(String args[]){
		new MultiClientServer().startServer();
	} 
	
	/**
	 * 启动服务器
	 */
	private void startServer(){
		try {
			serverSocket = new ServerSocket(54321);
			openClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打开连接
	 * @throws IOException 
	 */
	private void openClient() throws IOException{
		isWhile = true;
		while(clients.size() < MAX_SIZE){
			Socket client = serverSocket.accept();
			index++;
			clients.add(client);
			// 给每一个连接上服务器的客服端分配一个线程
			executorService.execute(new ReadFromClient(client, index));
		}
		isWhile = false;
	}
	
	/**
	 * 循环读取客服端发送的消息
	 */
	private class ReadFromClient extends Thread{
		
		private Socket client;
		private int code;
		private boolean isExit;
		private ReadFromClient(Socket client, int code){
			this.client = client;
			this.code = code;
		}
		
		@Override
		public void run(){
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));	
				String line = null;
				while(!isExit && (line = reader.readLine()) != null){
					System.out.println("接收到第"+code+"个人发送的消息："+line);
					if ("exit".equals(line)){
						clients.remove(client);
						PrintWriter printWriter = new PrintWriter(client.getOutputStream());
						printWriter.println("exitOk");
						printWriter.flush();
						client.close();
						if (!isWhile){
							openClient();
						}		
						isExit = true;
					}else{
						sendToAll("第"+code+"个人：" + line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 发送消息给所有人
		 * @param line
		 * @throws IOException
		 */
		private void sendToAll(String line) throws IOException{
			for(Socket client : clients){
				PrintWriter printWriter = new PrintWriter(client.getOutputStream());
				printWriter.println(line);
				printWriter.flush();
			}
		}
	}
}
