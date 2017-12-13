package cn.htd.promotion.cpc.biz.handle;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 
* @ClassName: MyX509TrustManager.java
* @Title:证书信任管理器（用于https请求）
* @Description:以下代码只是为了方便合作方测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码
* @Copyright: Copyright (c) 2016
* @Company: www.blueplus.cc
* 
* @author BluePlus
* @date:2016年9月1日
* @version 1.0
 */
public class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}