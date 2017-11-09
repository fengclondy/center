package cn.htd.basecenter.enums;

public enum SmsChannelTypeEnum {

	MANDAO("漫道", "1"),
	TIANXUNTONG("天信通", "2"),
	MENGWANG("梦网", "3");

	private SmsChannelTypeEnum(String name, String code) {
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
