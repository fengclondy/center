package cn.htd.membercenter.dto;

import java.io.Serializable;

public class MemberExternalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 收货人
	 */
	private String receivePerson;
	/**
	 * 收货电话
	 */
	private String receivePhone;
	/**
	 * 收货地址
	 */
	private String receiveAddress;
	/**
	 * 会员编码
	 */
	private String memberCode;
	
	public String getReceivePerson() {
		return receivePerson;
	}
	
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	
	public String getReceivePhone() {
		return receivePhone;
	}
	
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	
	public String getReceiveAddress() {
		return receiveAddress;
	}
	
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	
}
