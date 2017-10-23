package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class TimelimitPurchaseItemInfoDTO implements Serializable{

	private static final long serialVersionUID = 7538908482273829296L;
	/**
	  * 商品名称
	  */
	 private String itemCode;
	 /**
	  * 商品编码
	  */
     private String itemName;
     /**
	  * 商品 item id
	  */
    private String itemId;
    
    
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemId() {
		return itemId;
	}
	
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
     

}
