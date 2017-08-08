package cn.htd.basecenter.dto;

import java.io.Serializable;

public class ValidSmsConfigDTO implements Serializable {

	private static final long serialVersionUID = 2648782374271000247L;

	private Long id;

	private Long operatorId;

	private String operatorName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}