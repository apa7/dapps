package top.apa7.dapp.utils;

/**
 * 日志工具
 * 
 * @author: apa7
 * @date:2015年12月28日
 * @Copyright
 */
public class LogUtil {
	
	/**
	 * @author apa7
	 * @date 2016年7月1日下午2:36:57
	 * @Desc
	 * @param clazz 类名
	 * @param method 方法名
	 * @param msg 信息
	 * @return
	 */
	public static String msg(String clazz, String method, String msg){
		//"[BaseApiController][update]"
		return new StringBuilder().append("[")
				.append(clazz)
				.append("][")
				.append(method)
				.append("]")
				.append(msg)
				.toString();
	}

	public static String msg(String clazz, String method, String msg, String ip){
		//"[BaseApiController][update]"
		return new StringBuilder().append("[")
				.append(clazz)
				.append("][")
				.append(method)
				.append("] msg=")
				.append(msg)
				.append("ip=")
				.append(ip)
				.toString();
	}

}
