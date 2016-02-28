package cn.edu.buaa.binarywang.hadoop.practice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogRecord {
	/**
	 * 222.68.172.190 - - [18/Sep/2013:06:49:57 +0000] "GET /images/my.jpg HTTP/1.1" 200 19939
	 * "http://www.angularjs.cn/A00n"
	 * "Mozilla/5.0 (Windows NT 6.1)	AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36"
	 * 拆解为以下8个变量：
	 */

	private String remoteAddr;//remote_addr: 记录客户端的ip地址, 222.68.172.190
	private String remoteUser; //remote_user: 记录客户端用户名称, –
	private String timeLocal;//time_local: 记录访问时间与时区, [18/Sep/2013:06:49:57 +0000]
	private String request; //request: 记录请求的url与http协议, “GET /images/my.jpg HTTP/1.1″
	private int status; //status: 记录请求状态,成功是200, 200
	private long bodyBytesSent; //body_bytes_sent: 记录发送给客户端文件主体内容大小, 19939
	private String httpReferer; //http_referer: 用来记录从那个页面链接访问过来的, “http://www.angularjs.cn/A00n”
	private String httpUserAgent; //http_user_agent: 记录客户浏览器的相关信息, “Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36″

	//定义辅助变量
	private String requestMethod;//GET,POST,...
	private String requestUrl;
	private String requestHttpVersion;//HTTP/1.1

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestHttpVersion() {
		return requestHttpVersion;
	}

	public void setRequestHttpVersion(String requestHttpVersion) {
		this.requestHttpVersion = requestHttpVersion;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteUser() {
		return remoteUser;
	}

	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}

	public String getTimeLocal() {
		return timeLocal;
	}

	public void setTimeLocal(String timeLocal) {
		this.timeLocal = timeLocal;
	}

	public String getRequest() {
		return request;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getBodyBytesSent() {
		return bodyBytesSent;
	}

	public void setBodyBytesSent(long bodyBytesSent) {
		this.bodyBytesSent = bodyBytesSent;
	}

	public String getHttpReferer() {
		return httpReferer;
	}

	public void setHttpReferer(String httpReferer) {
		this.httpReferer = httpReferer;
	}

	public String getHttpUserAgent() {
		return httpUserAgent;
	}

	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}

	private static SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	public Date getAccessTime() {
		try {
			return df.parse(timeLocal);
		} catch (ParseException e) {
			throw new IllegalArgumentException("非法日志格式");
		}
	}
		
}
