package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;

/**
 * 商城商品详情- 库存查询（包含上下架信息）
 */
public class MallSkuStockInDTO implements Serializable {

    private String skuCode; //商品sku编码

    private String productChannelCode; // 商品渠道编码　10：内部供应商商品 20：外部供应商商品 30开头：外接渠道商品
	
	private String supplierCode; // 供应商编码

    private Integer isBoxFlag; // 是否包厢关系 【内部供应商商品必传：0-大厅 1-包厢】

    private String cityCode; // 城市站【全国传1 ,费全国传cityCode】

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getIsBoxFlag() {
        return isBoxFlag;
    }

    public void setIsBoxFlag(Integer isBoxFlag) {
        this.isBoxFlag = isBoxFlag;
    }

    public String getProductChannelCode() {
        return productChannelCode;
    }

    public void setProductChannelCode(String productChannelCode) {
        this.productChannelCode = productChannelCode;
    }


    public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
