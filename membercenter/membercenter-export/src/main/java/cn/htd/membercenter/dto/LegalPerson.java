package cn.htd.membercenter.dto;

import java.io.Serializable;

public class LegalPerson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String realName;// 真实姓名
	private String certNo;// 身份证号码
	private String mobileNo;// 手机号码
	private String email;// 联系邮箱 字符串(0-128)
	private String address;// 常住地址

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
