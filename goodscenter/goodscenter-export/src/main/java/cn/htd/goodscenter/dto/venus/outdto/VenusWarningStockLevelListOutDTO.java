package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusWarningStockLevelListOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 318686396091762111L;
	private Long skuId;
	//商品编码
	private String skuCode;
	//商品名称
	private String itemName;
	//显示库存
	private String displayQty;
	//ERP可卖数
	private String erpAvaliableQty;
	private String itemCode;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getDisplayQty() {
		return displayQty;
	}
	public void setDisplayQty(String displayQty) {
		this.displayQty = displayQty;
	}
	public String getErpAvaliableQty() {
		return erpAvaliableQty;
	}
	public void setErpAvaliableQty(String erpAvaliableQty) {
		this.erpAvaliableQty = erpAvaliableQty;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
}
