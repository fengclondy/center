package com.bjucloud.contentcenter.common.constants;

public enum TransactionRelationStatus {

	status_one("1","批发"),status_two("2","其他");
	
	private TransactionRelationStatus(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	private String key;
	
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
