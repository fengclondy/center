package cn.htd.membercenter.dto;

import java.io.Serializable;

public class CupidMemberInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6791859844656222510L;

	private String uid;

	private String password;

	private String passwdEncryType;

	private String contactPersonName;

	private String contactPersonMobile;

	private String companyName;

	private String companyAddress;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswdEncryType() {
		return passwdEncryType;
	}

	public void setPasswdEncryType(String passwdEncryType) {
		this.passwdEncryType = passwdEncryType;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonMobile() {
		return contactPersonMobile;
	}

	public void setContactPersonMobile(String contactPersonMobile) {
		this.contactPersonMobile = contactPersonMobile;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

}
