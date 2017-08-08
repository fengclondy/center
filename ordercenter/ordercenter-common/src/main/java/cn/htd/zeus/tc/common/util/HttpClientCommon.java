/*
 * #%L
 * BroadleafCommerce Profile
 * %%
 * Copyright (C) 2009 - 2015 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package cn.htd.zeus.tc.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * http请求类
 * 
 * @author zhangding
 * 
 */
public class HttpClientCommon
{

	/**
	 * GET请求
	 * 
	 * @param url
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpGet(final String url) throws Exception
	{

		return httpGet(url, null);

	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param code
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpGet(final String url, String code) throws Exception
	{

		if (null == code)
		{
			code = "UTF-8";
		}

		final URL getURL = new URL(url);
		final HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
		connection.connect();

		final int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK)
		{

			final InputStream inStream = connection.getInputStream();

			final BufferedReader in = new BufferedReader(new InputStreamReader(inStream, code));
			final StringBuffer buffer = new StringBuffer();
			int ch;
			while ((ch = in.read()) > -1)
			{
				buffer.append((char) ch);
			}

			/* 解析完毕关闭输入流 */
			in.close();
			/* 关闭URL连接 */
			if (connection != null)
			{
				connection.disconnect();
			}

			return buffer.toString();

		}
		else
		{
			throw new RuntimeException("Bad response status, " + responseCode);
		}

	}

	/**
	 * POST请求
	 * 支持参数postEntity传入中文  2016-10-11
	 * @author zhangding
	 * @param url
	 * @param postEntity
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 * @
	 */
	public static String httpPost(final String url, final String postEntity) throws Exception
	{

		return httpPost(url, postEntity, null);
	}

	/**
	 * POST请求
	 * 支持参数postEntity传入中文  2016-10-11
	 * @author zhangding
	 * @param url
	 * @param postEntity
	 * @param code
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(final String url, final String postEntity, final String code) throws Exception
	{

		return httpPost(url, null, postEntity, code);
	}

	/**
	 * POST请求
	 * 支持参数postEntity传入中文  2016-10-11
	 * @author zhangding
	 * @param url
	 * @param postHeaders
	 * @param postEntity
	 * @return
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(final String url, final Map<String, String> postHeaders, final String postEntity)
			throws Exception
	{

		return httpPost(url, postHeaders, postEntity, null);
	}

	/**
	 * POST请求
	 * 支持参数postEntity传入中文  2016-10-11
	 * @author zhangding
	 * @param url
	 * @param postHeaders
	 * @param postEntity
	 * @param code
	 * @throws Exception
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(final String url, final Map<String, String> postHeaders, final String postEntity, String code)
			throws Exception
	{

		if (null == code)
		{
			code = "UTF-8";
		}

		final URL postURL = new URL(url);
		final HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		if (null != postHeaders)
		{
			for (final String pKey : postHeaders.keySet())
			{
				connection.setRequestProperty(pKey, postHeaders.get(pKey));
			}
		}
		connection.connect();

		if (null != postEntity)
		{
			final DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.write(postEntity.getBytes());
			out.flush();
			out.close(); // flush and close
		}
		final int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK)
		{

			final InputStream inStream = connection.getInputStream();

			final BufferedReader in = new BufferedReader(new InputStreamReader(inStream, code));
			final StringBuffer buffer = new StringBuffer();
			int ch;
			while ((ch = in.read()) > -1)
			{
				buffer.append((char) ch);
			}

			/* 解析完毕关闭输入流 */
			in.close();
			/* 关闭URL连接 */
			if (connection != null)
			{
				connection.disconnect();
			}

			return buffer.toString();

		}
		else
		{
			throw new RuntimeException("Bad response status, " + responseCode);
		}

	}
}
