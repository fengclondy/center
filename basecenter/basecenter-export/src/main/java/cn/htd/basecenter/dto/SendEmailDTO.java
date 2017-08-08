package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.basecenter.enums.EmailMimeTypeEnum;

public class SendEmailDTO implements Serializable {

	private static final long serialVersionUID = 4872401186302214798L;

	private String email;

	private String emailType;

	private List<String> parameterList;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public List<String> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<String> parameterList) {
		this.parameterList = parameterList;
	}

}
