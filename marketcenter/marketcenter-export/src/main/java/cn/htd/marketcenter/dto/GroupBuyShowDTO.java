package cn.htd.marketcenter.dto;

import java.io.Serializable;

public class GroupBuyShowDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7968914703768537067L;
	
	private String num;
	
	private String price;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
