package cn.htd.pricecenter.enums;

public enum TerminalPriceType {
	RETAIL_PRICE("0", "零售价"), 
	SALE_PRICE("1", "销售价"),
	VIP_PRICE("2", "VIP价");
	
	private String code;
	private String label;

	TerminalPriceType(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
