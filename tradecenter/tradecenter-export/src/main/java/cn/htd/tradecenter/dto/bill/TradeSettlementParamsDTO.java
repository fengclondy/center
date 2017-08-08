package cn.htd.tradecenter.dto.bill;

import java.io.Serializable;

public class TradeSettlementParamsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8625323331127977511L;
	
	private String status;
	
	private String createStartDate;
	
	private String createEndDate;
	
	private String overStartDate;
	
	private String overEndDate;
	
	private String sellerName;
	
	private String settlementNo;

	private String sellerCode;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}

	public String getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(String createEndDate) {
		this.createEndDate = createEndDate;
	}

	public String getSettlementNo() {
		return settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOverEndDate() {
		return overEndDate;
	}

	public void setOverEndDate(String overEndDate) {
		this.overEndDate = overEndDate;
	}

	public String getOverStartDate() {
		return overStartDate;
	}

	public void setOverStartDate(String overStartDate) {
		this.overStartDate = overStartDate;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

}
