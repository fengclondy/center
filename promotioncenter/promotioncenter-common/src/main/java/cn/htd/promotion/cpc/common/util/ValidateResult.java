package cn.htd.promotion.cpc.common.util;


public class ValidateResult {
	/**
	 * 返回码
	 */
	private String reponseMsg = null;
	
	private Boolean isPass=true;
	
	public String getReponseMsg() {
		return reponseMsg;
	}
	public void setReponseMsg(String reponseMsg) {
		this.reponseMsg = reponseMsg;
	}
	public Boolean getIsPass() {
		return isPass;
	}
	public Boolean isPass() {
		return isPass;
	}
	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}
}
