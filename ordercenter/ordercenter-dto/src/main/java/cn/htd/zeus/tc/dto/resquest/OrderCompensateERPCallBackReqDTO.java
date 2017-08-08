package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

public class OrderCompensateERPCallBackReqDTO  extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6695413215707103152L;
	
	private String orderCode;
	
	private String categroyCode;//品类编号
	
	private String brandCode;//中台品牌编号
	
	private String result;
	
	private String errormessage;
	
	private String JL_SholesalerCode;
	
	private String JL_WholesalePayment;
	
	private String JL_CreateRecordCode;
	
	private String JL_ComPanyCode;
	
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
	public String getCategroyCode() {
		return categroyCode;
	}
	public void setCategroyCode(String categroyCode) {
		this.categroyCode = categroyCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	
}
