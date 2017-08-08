package cn.htd.membercenter.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	protected static transient Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private final static String GET_METHED = "GET";
	private final static String POST_METHED = "POST";
	private final static String DEFAUT_CHARSET = "UTF-8";

	/**
	 * get
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpGet(String urlString) {
		return httpRequst(urlString, null, DEFAUT_CHARSET, GET_METHED);
	}

	/**
	 * get
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpGet(String urlString, String charset) {
		return httpRequst(urlString, null, charset, GET_METHED);
	}

	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpPost(String urlString, JSONObject jsonParam) {
		return httpRequst(urlString, jsonParam, DEFAUT_CHARSET, POST_METHED);
	}

	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static String httpPost(String urlString, JSONObject jsonParam, String charset) {
		return httpRequst(urlString, jsonParam, charset, "POST");
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param charset
	 *            编码格式
	 * @param isNeedResponse
	 *            是否需要返回结果
	 * @return
	 */
	public static String httpRequst(String urlString, JSONObject jsonParam, String charset, String methodName) {
		URL url = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Charset", charset);
			conn.setRequestMethod(methodName);
			conn.setRequestProperty("contentType", "application/json");
			conn.connect();
			if (methodName.equals(POST_METHED)) {
				out = conn.getOutputStream();
				out.write(jsonParam.toJSONString().getBytes());
				out.flush();
				out.close();
			}
			logger.info(String.valueOf(conn.getResponseCode()));
			if (conn.getResponseCode() == 200) {
				logger.info("url:" + urlString + "连接成功");
				in = conn.getInputStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i = -1;
				while ((i = in.read()) != -1) {
					baos.write(i);
				}
				return baos.toString(charset);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param postHeaders
	 * @param postEntity
	 * @return
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(final String url, final Map<String, String> postHeaders, final String postEntity)
			throws Exception {

		return httpPost(url, postHeaders, postEntity, null);
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 * @param postHeaders
	 * @param postEntity
	 * @param code
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(final String url, final Map<String, String> postHeaders, final String postEntity,
			String code) throws Exception {

		if (null == code) {
			code = "UTF-8";
		}

		final URL postURL = new URL(url);
		final HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		if (null != postHeaders) {
			for (final String pKey : postHeaders.keySet()) {
				connection.setRequestProperty(pKey, postHeaders.get(pKey));
			}
		}
		connection.connect();

		if (null != postEntity) {
			final DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(postEntity.getBytes());
			out.flush();
			out.close(); // flush and close
		}
		final int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {

			final InputStream inStream = connection.getInputStream();

			final BufferedReader in = new BufferedReader(new InputStreamReader(inStream, code));
			final StringBuffer buffer = new StringBuffer();
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}

			/* 解析完毕关闭输入流 */
			in.close();
			/* 关闭URL连接 */
			if (connection != null) {
				connection.disconnect();
			}

			return buffer.toString();

		} else {
			throw new RuntimeException("Bad response status, " + responseCode);
		}

	}
}
