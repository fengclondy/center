package cn.htd.marketcenter.domain;

/**
 * 优惠规则校验用对象
 */
public class BuyerCheckInfo {
	/**
	 * 买家编码
	 */
	private String buyerCode;
	/**
	 * 买家会员等级
	 */
	private String buyerGrade;
	/**
	 * 会员所属供应商分组ID
	 */
	private String buyerGroupId;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public String getBuyerGroupId() {
		return buyerGroupId;
	}

	public void setBuyerGroupId(String buyerGroupId) {
		this.buyerGroupId = buyerGroupId;
	}
}