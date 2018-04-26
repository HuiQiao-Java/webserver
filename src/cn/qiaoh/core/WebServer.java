package cn.qiaoh.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.qiaoh.common.ServletContext;

/**
 * 这个类代表服务器端，用来接收用户请求和对客户端进行响应
 *
 * 两个成员变量：serversocket 和 threadPool，一个代表客户端，另外一个是线程池
 */
public class WebServer {
	private ServerSocket ss;
	private ExecutorService threadPool;

	public WebServer() {
		try {
			ss = new ServerSocket(ServletContext.port);
			threadPool = Executors.newFixedThreadPool(ServletContext.maxThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			//这个死循环是为了保证服务一直运行下去，这一点忘记了，所以只能显示一次
			//这一点一定多回顾一下
			while(true) {
				Socket socket = ss.accept();
				threadPool.execute(new ClientHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//创建主函数启动客户端
		new WebServer().start();
	}
}
