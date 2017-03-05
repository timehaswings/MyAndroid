package com.javax.servlet.test;

import java.util.Random;
import java.net.InetAddress;
import org.json.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ListDataServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		// String result = "{'status':true, 'content':[{'id':1, 'path':'...', 'name':'李美'},{},{}]}";
		// String result = "{'status':false, "content":"请求失败"}";
		resp.setCharacterEncoding("GBK");
		try{
			String rootPath = "http://"+InetAddress.getLocalHost().getHostAddress()+":8080/webapps/images/";
			int randomNum = new Random().nextInt(4); // 取0 ~ 4之间的随机数 包含0 但是不包含4
			boolean status = false;
			String content = "";
			if (randomNum == 0){
				status = false;
				content = "数据请求失败";
			}else{
				status = true;
				// 数据封装
				JSONArray data = new JSONArray();
				for (int i = 0; i < 13; i++){
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", i+1);
					jsonObject.put("path", rootPath+"image("+(i+1)+").jpg");
					jsonObject.put("name", "李美"+(i+1));
					data.put(jsonObject);
				}
				content = data.toString();
			}

			JSONObject result = new JSONObject();
			result.put("status", status);
			result.put("content", content);
			
			
			resp.getWriter().println(result.toString());
		}catch(JSONException e){
			resp.getWriter().println("{status:false,content:'服务器出现异常'}");
		}
		
	}
}