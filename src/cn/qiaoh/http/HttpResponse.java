package cn.qiaoh.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import cn.qiaoh.common.HttpContext;
import cn.qiaoh.common.ServletContext;

/**
 * 这个类是负责响应客户端响应的类
 * 有四个成员变量
 * 
 */
public class HttpResponse {
	//设置成员变量
	private String protocol;//协议名字
	private int status;//状态码
	private String contentType;//数据格式
	private int contentLength;//数据长度
	private PrintStream ps;//输出流
	private File file;//响应的file文件
	
	public HttpResponse(Socket socket,File file) {
		//这个构造函数其实也可以多一些参数？不需要一直在外边set？，可以写完思考一下
		//完成初始化，得到相应地输出流
		try {
			ps = new PrintStream(socket.getOutputStream());
			//对当前类中的file进行初始化
			this.file = file;
			//这里同时也对status进行初始化，根据file类型是否存在来决定
			if(!file.exists()) {
				//如果file不存在，就让把成员变量file再次指向404文件
				this.file = new File(ServletContext.rootContent+ServletContext.notFounPage);
				//并且把status设置为404
				status = HttpContext.NUMBER_NOTFOUND;
			}else {
				status = HttpContext.NUMBER_OK;
			}
			//通过得到的file对contentlength也完成初始化
			contentLength = (int)this.file.length();//但是这个强制类型转换有可能会出问题
			
			//这个参数暂时再内部赋值，因为没有任何理由再外部，目前这个参数是写死的
			protocol = "HTTP/1.1";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//设置boolean变量，控制响应头只能被写入一次
	private boolean isSend;
	public void respond() {
		//发送响应头
		//加上判断，如果没有发送可以发送，但是发送过不能再发送
		if(!isSend) {
			//发送响应头的这一步，因为把响应状态写死了，所以要修改
			//因为这个响应的文字和响应状态存在一一对应关系，所以有map集合保存
//			ps.println(protocol+" "+status+" "+"ok");
			ps.println(protocol+" "+status+" "+HttpContext.statusMaps.get(status));
			ps.println("content-type"+contentType);
			ps.println("content-length"+contentLength);
			ps.println();
			isSend = true;
		}
		
		try {
			//发送响应实体
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytes = new byte[contentLength];
			bis.read(bytes);
			System.out.println(new String(bytes));
			//这里不是print我的天啊！！！是write
//			ps.print(bytes);
			ps.write(bytes);
			ps.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setProticol(String protocol) {
		this.protocol = protocol;
		//*******************
	}

	public void setStatus(int status) {
		this.status = status;
		//*******************
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
		//*******************
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public PrintStream getPs() {
		return ps;
	}

	public void setPs(PrintStream ps) {
		this.ps = ps;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public int getStatus() {
		return status;
	}

	public String getContentType() {
		return contentType;
	}
	
	
}
