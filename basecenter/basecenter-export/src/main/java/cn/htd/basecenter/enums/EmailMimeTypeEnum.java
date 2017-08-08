package cn.htd.basecenter.enums;

public enum EmailMimeTypeEnum {
	HTML("HTML格式", "text/html"), PLAIN("普通文本", "text/plain");

	private String name;
	private String value;

	EmailMimeTypeEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}
}
