package cn.htd.promotion.cpc.biz.handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 
 * @ClassName: HttpProtocolHandler.java
 * @Title:HTTP请求工具类
 * @Description:以下代码只是为了方便合作方测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码
 * @Copyright: Copyright (c) 2016
 * @Company: www.blueplus.cc
 * 
 * @author BluePlus
 * @date:2016年9月1日
 * @version 1.0
 */
public class HttpProtocolHandler {

	/**
	 * 发送https请求
	 * 
	 * @param requestURL
	 * @param method
	 *            POST/GET
	 * @param outputStr
	 * @return
	 * @throws Exception
	 */
	public static String httpsRequest(String requestURL, String method,
			String outputStr) throws Exception {

		TrustManager[] tm = { new MyX509TrustManager() };

		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

		sslContext.init(null, tm, new java.security.SecureRandom());

		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL url = new URL(requestURL);

		String responseStr = null;
		HttpsURLConnection conn = null;
		System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				return urlHostName.equals(session.getPeerHost());
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		// 读取超时
		int readTimeOut = 200000;

		// 连接超时
		int connectTimeOut = 200000;
		try {
			conn = (HttpsURLConnection) url.openConnection();

			conn.setSSLSocketFactory(ssf);

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setReadTimeout(readTimeOut);
			conn.setConnectTimeout(connectTimeOut);

			// 设置请求方式
			conn.setRequestMethod(method);

			conn.setRequestProperty("content-type", "text/html");

			// 将菜单封装数据json传给微信服务器
			if (outputStr != null) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			InputStream inputStream = conn.getInputStream();

			responseStr = readStreamParameter(inputStream);

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();// 断开连接
		}

		return responseStr;
	}

	private static String readStreamParameter(InputStream in) {
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = null;
		try {
			// 此处需要将输入流转码成UTF-8,因为微信是用UTF-8做POST请求
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
}
