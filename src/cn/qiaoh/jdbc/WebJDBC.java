package cn.qiaoh.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.qiaoh.utils.JDBCUtils;

public class WebJDBC {
	private String username;
	private String password;
	private Connection connection;
	private Statement statement ;

	public WebJDBC(String uri) {
		String substring = uri.substring(uri.indexOf("?") + 1);
		String[] split = substring.split("&");
		// 不是这样的，在这里的map数据结构，应该是根据username这个key
		// 获取username的具体内容
		// 不是直接保存的用户名和密码的具体数据
		username = split[0].substring(split[0].indexOf("=") + 1);
		password = split[1].substring(split[1].indexOf("=") + 1);

		// for (String string : split) {
		// String[] s = string.split("=");
		// String key = s[0];
		// String value = s[1];
		// }
		
		// 初始化和数据库的连接
		// 完成statement对象的初始化
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void regist() {
		// 这个类完成注册功能
		try {
			String sql = "insert into user values(null,"+username+","+password+")";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, null);
		}
	}

	public boolean login() {
		//完成登陆功能
		ResultSet rs = null;
		String sql = "select * from user where username = "+username+
				" and password = "+password;
		boolean flag = false;
		try {
			rs = statement.executeQuery(sql);
			if(rs.next()) {
				//登陆成功
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, null);
		}
		return flag;
	}

}
