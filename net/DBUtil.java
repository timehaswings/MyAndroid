package com.javax.servlet.test;

import java.sql.*;

public class DBUtil{

	private static final String DB_URL = "jdbc:mysql://localhost:3306/employee";
	private static final String DB_NAME = "root";
	private static final String DB_PWD = "king";

	public static Connection connectMySQL() throws SQLException, ClassNotFoundException{
		// 1.加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		// 2.取得链接对象
		Connection conn = DriverManager.getConnection(DB_URL, DB_NAME, DB_PWD);
		if (conn != null){
			System.out.println("连接数据库成功");
		}
		return conn;
	}

	// 检查此用户名是否已经注册
	private static boolean isExit(Connection conn, String name) throws SQLException {
		String sql = "select count(name) from manager where name = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery(); // 执行查询
		if(rs != null && rs.next()){
			int count = rs.getInt("count(name)");
			System.out.println("count:"+count);
			rs.close();
			ps.close();
			return count != 0;
		}
		return false;
	}

	// 注册方法
	public static boolean register(String name, String password, String address, String phone){
		try{
			Connection conn = connectMySQL();
			if(!isExit(conn, name)){
				String sql = "insert into manager values(null, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, address);
				ps.setString(4, phone);
				ps.execute();
				ps.close();
				conn.close();
				return true;
			}else{
				System.out.println("此用户名已经注册");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("出现异常");
		}
		return false;
	}
}