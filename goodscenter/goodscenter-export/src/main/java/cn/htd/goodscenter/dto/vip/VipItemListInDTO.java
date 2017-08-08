package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;

public class VipItemListInDTO implements Serializable{

	private static final long serialVersionUID = 6421215209015630594L;
	//vip套餐商品code
	private String vipSkuCode;
	//vip套餐商品名称
	private String vipItemName;
	//  1 VIP套餐 2 智慧门店套餐
	private Integer vipItemType;
	//0801 页面不传
	private Long sellerId;

	public String getVipSkuCode() {
		return vipSkuCode;
	}

	public void setVipSkuCode(String vipSkuCode) {
		this.vipSkuCode = vipSkuCode;
	}

	public String getVipItemName() {
		return vipItemName;
	}

	public void setVipItemName(String vipItemName) {
		this.vipItemName = vipItemName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(Integer vipItemType) {
		this.vipItemType = vipItemType;
	}
}
