package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.util.Map;

public class OrderCompensateERPCallBackInDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324551488703699917L;
	private String orderCode;//响应码
	private String result;//是否成功：0：失败  1：成功
	private String errormessage;//错误信息
	private String JL_SholesalerCode;//ERP分销单号
	private String JL_WholesalePayment;//ERP分销收款单号
	private String JL_CreateRecordCode;//ERP送货建档号
	private String JL_ComPanyCode;//ERP公司代码
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public String getJL_SholesalerCode() {
		return JL_SholesalerCode;
	}
	public void setJL_SholesalerCode(String jL_SholesalerCode) {
		JL_SholesalerCode = jL_SholesalerCode;
	}
	public String getJL_WholesalePayment() {
		return JL_WholesalePayment;
	}
	public void setJL_WholesalePayment(String jL_WholesalePayment) {
		JL_WholesalePayment = jL_WholesalePayment;
	}
	public String getJL_CreateRecordCode() {
		return JL_CreateRecordCode;
	}
	public void setJL_CreateRecordCode(String jL_CreateRecordCode) {
		JL_CreateRecordCode = jL_CreateRecordCode;
	}
	public String getJL_ComPanyCode() {
		return JL_ComPanyCode;
	}
	public void setJL_ComPanyCode(String jL_ComPanyCode) {
		JL_ComPanyCode = jL_ComPanyCode;
	}
}
