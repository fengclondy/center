package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;

public class ChargeConditionInfoDMO {
    //品牌代码
	private String brandCode;
	
	//品类代码
	private String classCode;
	
	//客户代码
	private String customerCode;
	
	//交易金额
	private BigDecimal chargeAmount;
	
	//公司代码
	private String  companyCode;
	
	//行级订单编号
	private String itemOrderNo;

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getItemOrderNo() {
		return itemOrderNo;
	}

	public void setItemOrderNo(String itemOrderNo) {
		this.itemOrderNo = itemOrderNo;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
