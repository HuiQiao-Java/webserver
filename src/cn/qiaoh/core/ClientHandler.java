package cn.qiaoh.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import cn.qiaoh.common.ServletContext;
import cn.qiaoh.http.HttpRequest;
import cn.qiaoh.http.HttpResponse;

/**
 * 这个类是是用来处理客户端请求，并且负责响应客户端请求的
 * 
 * @author qiaoh
 *
 */
public class ClientHandler implements Runnable {
	// 声明成员变量socket
	Socket socket;

	// 这个客户端负责对将socket传入进来，并且完成对成员的初始化
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	// 重写线程任务类中的run方法
	@Override
	public void run() {
		// 创建request对象，并且同时传入sockt完成对其初始化
		HttpRequest request = new HttpRequest(socket);
		// 获得读取之后的uri
		String uri = request.getUri();
		System.out.println("打印是否正确读取了响应的uri：");
		System.out.println(uri);
		// 使用得到的uri完成对请求资源路径的更改
		String path = ServletContext.rootContent + uri;
		System.out.println(path);
		File file = new File(path);
		
		//根据request解析类返回的uri创建的file对象来初始化response
		HttpResponse httpResponse = new HttpResponse(socket, file);
		//根据request中得到的ext参数，完成respondse中的contenttpye赋值
		//这一步专业解释叫做  完成动态获取网页类型  --记住这个名次，别太low
		String key = request.getExt();
		httpResponse.setContentType(ServletContext.typeMap.get(key));
		//设置httpresponse中的protocol，之后想了想，这一步不必再外部赋值，再响应内部赋值
		
		System.out.println("下边打印是否正确的向响应中传入正确的参数：");
		System.out.println(httpResponse.getProtocol());
		System.out.println(httpResponse.getStatus());
		System.out.println(httpResponse.getContentType());
		System.out.println(httpResponse.getContentLength());
		System.out.println(httpResponse.getFile().getAbsolutePath());
		httpResponse.respond();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
