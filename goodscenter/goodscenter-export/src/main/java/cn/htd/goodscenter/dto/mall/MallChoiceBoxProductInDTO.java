package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;

/**
 * 商城商品详情- 库存查询（包含上下架信息）
 */
public class MallChoiceBoxProductInDTO implements Serializable {

    private String skuCode; //商品sku编码

    private String productChannelCode; // 商品渠道编码　10：内部供应商商品 20：外部供应商商品 30开头：外接渠道商品

    private Integer isLogin; // 是否登录

    private Integer isHasRelation; // 是否有经营关系 0:无， 1：有

    private String cityCode; // 城市站code
	
	private String supplierCode; // 供应商编码

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getProductChannelCode() {
        return productChannelCode;
    }

    public void setProductChannelCode(String productChannelCode) {
        this.productChannelCode = productChannelCode;
    }

    public Integer getIsHasRelation() {
        return isHasRelation;
    }

    public void setIsHasRelation(Integer isHasRelation) {
        this.isHasRelation = isHasRelation;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }
}
