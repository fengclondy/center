package cn.htd.tradecenter.common.utils;

public class PaySDK {

	/**
	 * key
	 */
	public String key;

	/**
	 * 支付时partenerId
	 */
	public String partenerId;

	/**
	 * 跳转到支付地址
	 */
	public String url;

	/**
	 * 同步回调地址
	 */
	public String return_url;

	/**
	 * 异步回调地址
	 */
	public String notify_url;

	/**
	 * 批量回调地址
	 * 
	 */
	public String batchReturn_url;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getBatchReturn_url() {
		return batchReturn_url;
	}

	public void setBatchReturn_url(String batchReturn_url) {
		this.batchReturn_url = batchReturn_url;
	}

}
