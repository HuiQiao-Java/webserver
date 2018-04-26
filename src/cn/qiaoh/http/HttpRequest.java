package cn.qiaoh.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Predicate;

/**
 * 这个类是用来读取客户端的请求的，封装了客户端的相关请求信息
 * 
 *
 */
public class HttpRequest {
	// 封装四个请求信息
	// 1.请求方式method 2.请求资源的路径uri 3.请求的协议
	private String method;
	private String uri;
	private String protocol;
	
	private String ext;//请求路径的后缀，根据这个后缀名，产生对应的content-type

	// 创建构造方法，完成对数据的读取
	public HttpRequest(Socket socket) {
		// 通过socket得到输入流
		try {
			// 这里使用bufferedreader来读取数据，因为只要读取对行数据，
			// 所以用bufferedreader
			// 使用当中的 readline()方法
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String firstLine = reader.readLine();
			//读取第一行数据之后，对数据进行切割，然后对成员变量进行初始化赋值
			String[] split = firstLine.split(" ");
			method = split[0];
			uri = split[1];
			protocol = split[2];
			
			//设置主页
			if("/".equals(uri)) {
				uri = "/index.html";
			}
			
			//根据uri来对ext完成初始化
			ext = uri.substring(uri.lastIndexOf(".")+1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getExt() {
		return ext;
	}

	// 创建开放三个参数的读取方法
	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getProtocol() {
		return protocol;
	}
}
