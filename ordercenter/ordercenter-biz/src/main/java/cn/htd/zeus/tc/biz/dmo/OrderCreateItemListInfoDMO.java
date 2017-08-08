package cn.htd.zeus.tc.biz.dmo;

public class OrderCreateItemListInfoDMO {

    //商品SKU编码
	private String skuCode;
	
	//商品名称
	private String goodsName;
	
	//商品图片地址
	private String skuPictureUrl;
	
	//品牌名称
	private String brandName;
	
	//商品数量
	private Long goodsCount;

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

	public String getSkuPictureUrl() {
		return skuPictureUrl;
	}

	public void setSkuPictureUrl(String skuPictureUrl) {
		this.skuPictureUrl = skuPictureUrl;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}
		
}
