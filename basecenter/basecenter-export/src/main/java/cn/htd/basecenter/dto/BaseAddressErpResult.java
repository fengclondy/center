package cn.htd.basecenter.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * <p>
 * Description: [基础省市区镇编码下行ERP回调结果]
 * </p>
 */
public class BaseAddressErpResult implements Serializable {

	private static final long serialVersionUID = -7667606312154895090L;
	/**
	 * 省市区镇编码
	 */
	@NotEmpty(message = "省市区镇编码不能为空")
	private String areaCode = "";
	/**
	 * 是否成功
	 */
	@Range(min = 0, max = 1, message = "ERP处理结果只能是0或者1")
	private int result = 0;
	/**
	 * 地址名称
	 */
	private String errorMessage = "";

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
