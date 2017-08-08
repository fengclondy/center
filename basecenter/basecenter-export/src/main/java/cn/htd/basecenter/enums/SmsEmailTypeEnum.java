package cn.htd.basecenter.enums;

public enum SmsEmailTypeEnum {

	SMS("短信", "1"),
	EMAIL("邮件", "2");

	private SmsEmailTypeEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	private String name;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
