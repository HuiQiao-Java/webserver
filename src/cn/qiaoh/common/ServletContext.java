package cn.qiaoh.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 这个类用来解析xml配置文件，并将解析出来的结果复制给四个静态成员变量 
 * 注意：此类使用dom4j来解析xml文件，在使用此方法之前一定要为工程导包
 * 导包的路径为：WebContent/WEB-INF/lib/..
 */
public class ServletContext {
	// 定义静态的成员变量
	public static int port;// 端口号
	public static int maxThread;// 线程池最大线程数量
	public static String protocol;// http协议
	public static String rootContent;// http文件的根目录
	public static String notFounPage;//404页面配置
	
	public static Map<String, String> typeMap =new HashMap<>();

	// 使用静态代码块为静态的成员变量赋值
	static {
		init();
	}

	// 初始化方法，使用dom4j来解析xml文件
	private static void init() {
		try {
			// 创建dom4j解析的对象
			Document doc = new SAXReader().read("config/web.xml");
			// 获得根元素
			Element rootServer = doc.getRootElement();
			// 获得元素connector
			Element connector = rootServer.element("service").
					element("connector");
			//通过connector的元素属性为静态成员变量进行初始化赋值
			//测试用parseInt方法可不可以
			port = Integer.parseInt(connector.attributeValue("port"));
			maxThread = Integer.valueOf(connector.attributeValue("maxThread"));
			protocol = connector.attributeValue("protocol");
			//获取元素webroot中的文本
			rootContent = rootServer.element("service").elementText("webRoot");
			//解析not-found-pages文本
			notFounPage = rootServer.element("service").elementText("not-found-page");
			//解析type-mappings
			List<Element> lists = rootServer.element("type-mappings").elements();
			for (Element element : lists) {
				String ext = element.attributeValue("ext");
				String type = element.attributeValue("type");
				typeMap.put(ext, type);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
