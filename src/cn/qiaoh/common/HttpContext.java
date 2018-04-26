package cn.qiaoh.common;

import java.util.HashMap;
import java.util.Map;

public class HttpContext {
	//创建
	public static final int NUMBER_OK = 200;
	public static final int NUMBER_NOTFOUND = 404;
	public static final int NUMBER_ERROR = 500;
	
	public static final String DESC_OK = "OK";
	public static final String DESC_NOTFOUND = "NOT FOUND";
	public static final String DESC_ERROR = "ERROR";
	
	public static Map<String, Integer> statusMaps = new HashMap<>();
	
	static {
		statusMaps.put(DESC_ERROR, NUMBER_ERROR);
		statusMaps.put(DESC_NOTFOUND, NUMBER_NOTFOUND);
		statusMaps.put(DESC_OK, NUMBER_OK);
	}
}
