package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品详情入参【商城】
 * @author chenkang
 */
public class MallSkuInDTO implements Serializable {

	private String itemCode; //商品item编码 【必传】

	private String skuCode; //商品sku编码 【必传，详情页首次进入可不传】

	private Map<String, Object> tunnelMap = new HashMap<>(); // 注：隧道字段

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Map<String, Object> getTunnelMap() {
		return tunnelMap;
	}

	public void setTunnelMap(Map<String, Object> tunnelMap) {
		this.tunnelMap = tunnelMap;
	}

	@Override
	public String toString() {
		return "MallSkuInDTO{" +
				"itemCode='" + itemCode + '\'' +
				", skuCode='" + skuCode + '\'' +
				", tunnelMap=" + tunnelMap +
				'}';
	}
}
