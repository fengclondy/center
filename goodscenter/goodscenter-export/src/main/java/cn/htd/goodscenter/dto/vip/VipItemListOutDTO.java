package cn.htd.goodscenter.dto.vip;

import java.io.Serializable;
import java.util.List;

public class VipItemListOutDTO implements Serializable{

	private static final long serialVersionUID = 2546473681648834206L;
	private Long itemId;
	private Long skuId;
	private String skuCode;
	private String itemCode;
	private String itemName;
	// 1 VIP套餐 2 智慧门店套餐
	private Integer vipItemType;
	// 同步VIP会员标记:当is_vip_item=1时，该字段为1时有效 0 无效 1 有效
	private Integer vipSyncFlag;

	private Long vipItemSellerId;
	
	private List<VipItemEntryInfoDTO> vipItemEntryInfoList;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getVipSyncFlag() {
		return vipSyncFlag;
	}

	public void setVipSyncFlag(Integer vipSyncFlag) {
		this.vipSyncFlag = vipSyncFlag;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getVipItemType() {
		return vipItemType;
	}

	public void setVipItemType(Integer vipItemType) {
		this.vipItemType = vipItemType;
	}

	public List<VipItemEntryInfoDTO> getVipItemEntryInfoList() {
		return vipItemEntryInfoList;
	}

	public void setVipItemEntryInfoList(
			List<VipItemEntryInfoDTO> vipItemEntryInfoList) {
		this.vipItemEntryInfoList = vipItemEntryInfoList;
	}


	public Long getVipItemSellerId() {
		return vipItemSellerId;
	}

	public void setVipItemSellerId(Long vipItemSellerId) {
		this.vipItemSellerId = vipItemSellerId;
	}
}
