package com.weylen.mysql.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StanderConnect {
	
	public static final String DB_URL = "jdbc:mysql://localhost:3306/consumption";
	public static final String DB_USER = "root";
	public static final String DB_PWD = "king";
	
	public static void main(String args[]){
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接MYSQL
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			if (connection != null) {
				System.out.println("数据库连接成功");
			}
			String dateStr = "2014-12-10";
			// 创建一个执行SQL操作的对象
//			Statement statement = connection.createStatement();
			// 执行查询的操作
//			ResultSet rs = statement.executeQuery("select * from events where date(date) > date('"+dateStr+"')");
			String sql = "select * from events where date(date) > date(?) and id > ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			// 1.参数的下标  2.value
			ps.setString(1, dateStr);
			ps.setInt(2, 2);
			// 执行sql
			ResultSet rs = ps.executeQuery();
			
			while(rs != null && rs.next()){ // next方法就是将光标移动到下一行，如果存在则返回true
				// rs.findColumn("id") 得到此列所在的下标
				int id = rs.getInt(rs.findColumn("id"));
				String title = rs.getString(rs.findColumn("title")); // 在java.sql里面 下标是从1开始
				String desc = rs.getString(rs.findColumn("description"));
				String date = rs.getString(rs.findColumn("date"));
				double money = rs.getDouble(rs.findColumn("money"));
				System.out.println("id:"+id+",title:"+title+",desc:"+desc+",date:"+date+",money:"+money);
			}
			
			rs.close();
			ps.close();
//			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
