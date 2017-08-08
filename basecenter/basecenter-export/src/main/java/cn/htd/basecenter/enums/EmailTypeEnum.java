package cn.htd.basecenter.enums;

public enum EmailTypeEnum {

	SMTP("SMTP", "2"),
	POP3("POP3", "1"),
	IMAP("IMAP", "3");

	private EmailTypeEnum(String name, String code) {
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

	// 获取枚举value对应名字方法
	public static String getName(String code) {
		for (EmailTypeEnum emailType : EmailTypeEnum.values()) {
			if (emailType.getCode().equals(code)) {
				return emailType.name;
			}
		}
		return "";
	}

}
