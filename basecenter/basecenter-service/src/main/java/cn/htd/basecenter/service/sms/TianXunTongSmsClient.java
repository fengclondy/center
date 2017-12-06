package cn.htd.basecenter.service.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;

@Service("tianXunTongSmsClient")
public class TianXunTongSmsClient extends BaseSmsClient {

	@Override
	protected String clientSendSms(final String phoneNum, final String content) throws Exception {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("PhoneNumber", phoneNum);
		map.put("SmsContent", content);
		map.put("userid", sn);
		map.put("userkey", pwd);
		final String result = sendToRequest(serviceURL, map, "gb2312");
		if (StringUtils.isBlank(result) || !result.startsWith("00/1")) {
			throw new BaseCenterBusinessException(ReturnCodeConst.TIANXUNTONG_SMS_SEND_ERROR,
					"tianxuntong result code: " + result);
		}
		return result;
	}

	/**
	 * 天信通发送短信
	 * 
	 * @param reqUrl
	 * @param parameters
	 * @param recvEncoding
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	private String sendToRequest(final String reqUrl, final Map<String, String> parameters, final String recvEncoding)
			throws BaseCenterBusinessException, Exception {
		HttpURLConnection conn = null;
		String responseContent = null;
		BufferedReader rd = null;
		InputStream in = null;
		try {
			conn = prepareConn(reqUrl);
			final byte[] b = buildParam(parameters, recvEncoding).getBytes();
			conn.getOutputStream().write(b, 0, b.length);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();

			in = conn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			final StringBuffer tempStr = new StringBuffer();
			final String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
		} catch (BaseCenterBusinessException bcbe) {
			throw bcbe;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (final IOException e) {
					throw e;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					throw e;
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return responseContent;
	}

	@Override
	protected String queryBalance() throws BaseCenterBusinessException, Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
