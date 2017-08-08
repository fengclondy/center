package cn.htd.goodscenter.common.utils;


public class ValidateResult {
	private String message;
	private Boolean isPass=true;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean isPass() {
		return isPass;
	}
	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}
}
