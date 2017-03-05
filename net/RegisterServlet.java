package com.javax.servlet.test;

import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, java.io.IOException{
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String address = req.getParameter("address");
		String phone = req.getParameter("phone");

		System.out.println("×¢²áÐÅÏ¢£º"+name+"-"+password+"-"+address+"-"+phone);
		boolean isSuccess = DBUtil.register(name, password, address, phone);
		
		System.out.println("isSuccess::"+isSuccess);
		
		resp.setCharacterEncoding("GBK");
		java.io.PrintWriter	out = resp.getWriter();
		String result = isSuccess ? "×¢²á³É¹¦" : "×¢²áÊ§°Ü";
		out.println(result);
	}
}