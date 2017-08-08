package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/*
 *预售下行回调DTO
 */
public class PreSalesOrderCallBackReqDTO extends GenricReqDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3982391661862792725L;

	//订单号
	@NotEmpty(message = "orderNo不能为空")
	private String orderNo;
	
	//erp预售下行状态
	@NotNull(message = "erpResultStatus不能为空")
	private Integer erpResultStatus;//0-未下行 1-已下行MQ未返回  2-已下行MQ已返回
	
	//ERP预售下行返回码
	@NotEmpty(message = "erpResultCode不能为空")
	private String erpResultCode;
	
	//ERP预售下行返回结果描述
	private String erpResultMsg;
	
	//分销单号
	private String saleNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getErpResultStatus() {
		return erpResultStatus;
	}

	public void setErpResultStatus(Integer erpResultStatus) {
		this.erpResultStatus = erpResultStatus;
	}

	public String getErpResultCode() {
		return erpResultCode;
	}

	public void setErpResultCode(String erpResultCode) {
		this.erpResultCode = erpResultCode;
	}

	public String getErpResultMsg() {
		return erpResultMsg;
	}

	public void setErpResultMsg(String erpResultMsg) {
		this.erpResultMsg = erpResultMsg;
	}

	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
}
