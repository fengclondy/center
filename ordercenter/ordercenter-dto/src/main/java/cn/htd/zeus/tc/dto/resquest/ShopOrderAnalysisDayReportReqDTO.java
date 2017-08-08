/**
 * 
 */
package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

/**
 * @author ly
 *
 */
public class ShopOrderAnalysisDayReportReqDTO extends GenricReqDTO implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4819326735990395372L;

    private Integer shopId;

    private String skuCode;

    private String goodsName;

    private String itemCode;

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}
