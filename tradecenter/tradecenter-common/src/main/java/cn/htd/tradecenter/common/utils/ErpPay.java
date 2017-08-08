package cn.htd.tradecenter.common.utils;

public class ErpPay {
	/**
	 * key
	 */
	private String signKey;

	/**
	 * 支付时partenerId
	 */
	private String partenerId;

	/**
	 * 跳转到支付地址
	 */
	private String url;

	/**
	 * 同步回调地址
	 */
	private String return_url;

	/**
	 * 支付异步回调地址
	 */
	private String notify_url;

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getPartenerId() {
		return partenerId;
	}

	public void setPartenerId(String partenerId) {
		this.partenerId = partenerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
}
