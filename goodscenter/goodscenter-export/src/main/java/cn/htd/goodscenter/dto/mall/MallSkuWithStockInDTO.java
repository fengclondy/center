package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品详情入参
 * @author chenkang
 */
public class MallSkuWithStockInDTO implements Serializable {

	private String skuCode; //商品sku编码 【必传】

	private Integer isBoxFlag; //是否包厢关系 【内部供应商商品必传：0-大厅 1-包厢】

	private String cityCode; // 城市站【全国传1 ,费全国传cityCode】

	private Map<String, Object> tunnelMap = new HashMap<>(); // 注：隧道字段

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
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
		return "MallSkuWithStockInDTO{" +
				", skuCode='" + skuCode + '\'' +
				", isBoxFlag=" + isBoxFlag +
				", tunnelMap=" + tunnelMap +
				'}';
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
