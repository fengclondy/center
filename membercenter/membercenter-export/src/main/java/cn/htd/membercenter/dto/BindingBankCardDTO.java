package cn.htd.membercenter.dto;

import java.io.Serializable;

public class BindingBankCardDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String realName;// 真实姓名
	private String certNo;// 身份证号码
	private String userId;// 用户ID
	private String mobile;// 手机号码
	private String bankCardNo;// 银行卡号
	private String publicTag;// 银行卡账户类型
	private String bankCode;// 银行简称
	private String bankName;// 银行名称
	private String province;// 开户省
	private String city;// 开户城市
	private String bindId;// 绑定Id

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPublicTag() {
		return publicTag;
	}

	public void setPublicTag(String publicTag) {
		this.publicTag = publicTag;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the bindId
	 */
	public String getBindId() {
		return bindId;
	}

	/**
	 * @param bindId
	 *            the bindId to set
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

}
