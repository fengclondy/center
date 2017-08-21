package cn.htd.pricecenter.enums;

public enum TerminalTypeEnum {
	HZG_TYPE(0, "汇掌柜");

	private int code;
	private String label;

	TerminalTypeEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
