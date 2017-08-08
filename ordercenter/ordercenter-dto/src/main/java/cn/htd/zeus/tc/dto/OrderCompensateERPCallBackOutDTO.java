package cn.htd.zeus.tc.dto;

import java.io.Serializable;


public class OrderCompensateERPCallBackOutDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6913054811590531948L;
	private String code;//响应码
	private String msg;//响应描述
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
