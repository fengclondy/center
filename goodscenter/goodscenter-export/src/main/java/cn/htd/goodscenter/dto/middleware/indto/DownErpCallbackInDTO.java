package cn.htd.goodscenter.dto.middleware.indto;

import java.io.Serializable;

public class DownErpCallbackInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8702076549478055045L;
	/**
	 * ERP编码  非必填
	 */
	private String erpCode;
	/**
	 * ERP状态  必填
	 */
	private String erpStatus;
	/**
	 * 下行ERP错误信息 非必填
	 */
	private String erpErrorMsg;
	/**
	 * 中台编码 必填
	 */
	private String middleGroundCode;
	//商品编码ID
	private Long itemId;
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getErpStatus() {
		return erpStatus;
	}
	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}
	public String getErpErrorMsg() {
		return erpErrorMsg;
	}
	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}
	public String getMiddleGroundCode() {
		return middleGroundCode;
	}
	public void setMiddleGroundCode(String middleGroundCode) {
		this.middleGroundCode = middleGroundCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
}
