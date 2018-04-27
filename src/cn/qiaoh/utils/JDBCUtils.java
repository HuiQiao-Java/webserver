package cn.qiaoh.utils;
/**
 * 这个是jdbc的工具类，提供两个静态方法
 * 1.getConnection() 返回一个Connection对象
 * 2.close(Connection,Statement,ResultSet)提供关闭的方法
 * @author qiaoh
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	//创建静态的变量properties
	//完成对properties的静态加载
	static Properties properties;
	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//创建方法getConnection()
	public static Connection getConnection() {
		//获得驱动
		//获得Connetion对象
		try {
			Class.forName(properties.getProperty("driverClass"));
			Connection connection = DriverManager.getConnection(
					properties.getProperty("jdbcUrl"),
					properties.getProperty("user"),
					properties.getProperty("password"));
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//创建close方法
	//注意关闭的顺序，对于有嵌套关系的对象，先关闭内层对象，在关闭外层对象
	public static void close(Connection connection,
							Statement statement,
							ResultSet resultSet) {
		//关闭内层对象resultSet
		//添加判断模块防止空指针异常
		if (resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				resultSet = null;
			}
		}
		
		//关闭次外层对象statement
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				statement = null;
			}
		}
		
		//关闭外层对象Connection
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				connection = null;
			}
		}
	}
	
}
