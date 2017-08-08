package cn.htd.goodscenter.dto;

import java.io.Serializable;

public class WaitAuditItemInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//1为待审核供应商商品
	private String infoType;
	private Integer count;
	
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
