package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

public class OrderQueryReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1754520051302626884L;

	/*
	 * 会员code即买家code(小B)
	 */
	private String buyerCode;

	/*
	 *卖家code即大B
	 */
	private String sellerCode;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
}
