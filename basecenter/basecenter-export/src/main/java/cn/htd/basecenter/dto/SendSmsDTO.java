package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

public class SendSmsDTO implements Serializable {

	private static final long serialVersionUID = 4872401186302214798L;

	private String phone;

	private String smsType;

	private List<String> parameterList;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public List<String> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

}
