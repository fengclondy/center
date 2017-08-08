package cn.htd.basecenter.service.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;

/**
 * 短信基础类
 * 
 * @author jiangkun
 */
public abstract class BaseSmsClient {

	protected static final Logger logger = LoggerFactory.getLogger(BaseSmsClient.class);

	protected String serviceURL = "";
	protected String sn = "";// 序列号
	protected String pwd = "";// 密码

	/**
	 * 创建参数
	 * 
	 * @param parameters
	 * @param recvEncoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected static String buildParam(final Map<String, String> parameters, final String recvEncoding)
			throws UnsupportedEncodingException {
		StringBuffer params = new StringBuffer();
		for (final Iterator<Entry<String, String>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			final Entry<String, String> element = iter.next();
			params.append(element.getKey());
			params.append("=");
			params.append(URLEncoder.encode(element.getValue(), recvEncoding));
			params.append("&");
		}
		if (params.length() > 0) {
			params = params.deleteCharAt(params.length() - 1);
		}
		return params.toString();
	}

	/**
	 * 建立连接
	 * 
	 * @param reqUrl
	 * @return
	 * @throws IOException
	 */
	protected static HttpURLConnection prepareConn(final String reqUrl) throws IOException {
		final URL url = new URL(reqUrl);
		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");

		conn.setConnectTimeout(5000);// （单位：毫秒）jdk
		// 1.5换成这个,连接超时
		conn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
		conn.setDoOutput(true);
		return conn;
	}

	/**
	 * 发送短信
	 * 
	 * @param config
	 * @param phoneNum
	 * @param content
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	public String sendSms(BaseSmsConfigDTO config, String phoneNum, String content)
			throws BaseCenterBusinessException, Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsClient-sendSms", JSONObject.toJSONString(config),
				JSONObject.toJSONString(phoneNum), JSONObject.toJSONString(content));
		String result = "";
		try {
			initClient(config);
			result = clientSendSms(phoneNum, content);
		} catch (BaseCenterBusinessException bcbe) {
			throw bcbe;
		} catch (Exception e) {
			throw e;
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseSmsClient-sendSms", JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 初始化
	 * 
	 * @param config
	 * @throws Exception
	 */
	protected void initClient(BaseSmsConfigDTO config) throws Exception {
		this.sn = config.getMsgAccount();
		this.pwd = config.getMsgPassword();
		this.serviceURL = config.getMsgUrl();
	}

	/**
	 * 发送短信
	 * 
	 * @param config
	 * @param phoneNum
	 * @param content
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	protected abstract String clientSendSms(String phoneNum, String content)
			throws BaseCenterBusinessException, Exception;
}
