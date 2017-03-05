package com.javax.servlet.test;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class UploadServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		// 取得所有的头信息名字
		/**
		java.util.Enumeration<String> names = req.getHeaderNames();
		while(names.hasMoreElements()){ // 判断是否有更多的元素
			String headerName = names.nextElement(); // 取得下一个元素
			String headerValue = req.getHeader(headerName);
			System.out.println(headerName + "-->" +headerValue);
		}*/
		String fileName = req.getHeader("FileName");
		String fileLength = req.getHeader("FileLength");
		// 1.取得ServletContext类对象
		ServletContext cxt = getServletContext();
		// 2.取得指定文件或者文件夹的绝对路径
		String absolutePath = cxt.getRealPath("upload"); // 参数是文件或者文件夹的名字
		System.out.println("absolutePath:" + absolutePath);
		// 以当前的时间作为文件的名字
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); // 1.模板 2.地区
		String name = format.format(new Date());
		ServletInputStream sis = req.getInputStream();// 获取请求的输入流，参数或者上传到数据都是在输入流里面
		File file = new File(absolutePath, fileName == null ? name : fileName);
		FileOutputStream fos = new FileOutputStream(file);
		int length = 0; // 表示所有写入的字节总数
		int i = -1; // 用来接收每次读取到的字节数
		byte[] bytes = new byte[1024]; // 用来保存读取到的字节
		while((i = sis.read(bytes, 0, bytes.length)) != -1){
			fos.write(bytes, 0, i);
			fos.flush();
			length += i;
		}
		// 表示流写完
		fos.close();
		sis.close();

		resp.setCharacterEncoding("GBK");
		PrintWriter out = resp.getWriter();

		if (fileLength != null){
			try{
				int fileLen = Integer.parseInt(fileLength);
				if(fileLen == length){
					out.println("上传成功");
				}else{
					out.println("上传失败");
					file.delete();
				}
			}catch(NumberFormatException e){
				out.println("上传失败");
				file.delete();
			}
			
		}
		
	}
}