package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PreSaleProdQueryDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3365187635660020177L;
	//品牌名称
	private String brandName;
	//品牌编码
	private String brandCode;
	//商品名称
	private String itemName;
	//商品编码
	private String itemCode;
	//商品id
	private Long itemId;
	//商品sku编码
	private String skuCode;
	//商品skuid
	private Long skuId;
	//商品erp编码
	private String erpCode;
	//商品spu编码
	private String spuCode;
	//预售标志 0.非预售 2.总部预售，3.分部预售
	private Integer isPreSell;
	//供应商编码
	private Long sellerId;
	//供应商名称
	private String sellerName;
	//供应商编码
	private String sellerCode;
	//库存数量
	private Integer kcNum;
	//一级类目id
	private Long firstCatId;
	//一级类目名称
	private String firstCatName;
	//二级类目id
	private Long secondCatId;
	//二级类目名称
	private String secondCatName;
	//三级类目id
	private Long thirdCatId;
	//三级类目名称
	private String thirdCatName;
	//建议零售价
	private BigDecimal recommendPrice;
	//销售价
	private BigDecimal salePrice;
	//vip价格
	private BigDecimal vipPrice;
	//上架状态：1 上架 2 下架
	private Integer listStatus;
	//1 是全国
	private Integer isWholeCountry;
	//销售区域
	//private List<PreSaleItemRegion> preSaleItemRegionList;
	
	private List<PreSaleProductSaleAreaDTO> preSaleItemRegionList;
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	public String getSpuCode() {
		return spuCode;
	}
	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}
	public Integer getIsPreSell() {
		return isPreSell;
	}
	public void setIsPreSell(Integer isPreSell) {
		this.isPreSell = isPreSell;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerCode() {
		return sellerCode;
	}
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
	public Integer getKcNum() {
		return kcNum;
	}
	public void setKcNum(Integer kcNum) {
		this.kcNum = kcNum;
	}
	public Long getFirstCatId() {
		return firstCatId;
	}
	public void setFirstCatId(Long firstCatId) {
		this.firstCatId = firstCatId;
	}
	public String getFirstCatName() {
		return firstCatName;
	}
	public void setFirstCatName(String firstCatName) {
		this.firstCatName = firstCatName;
	}
	public Long getSecondCatId() {
		return secondCatId;
	}
	public void setSecondCatId(Long secondCatId) {
		this.secondCatId = secondCatId;
	}
	public String getSecondCatName() {
		return secondCatName;
	}
	public void setSecondCatName(String secondCatName) {
		this.secondCatName = secondCatName;
	}
	public Long getThirdCatId() {
		return thirdCatId;
	}
	public void setThirdCatId(Long thirdCatId) {
		this.thirdCatId = thirdCatId;
	}
	public String getThirdCatName() {
		return thirdCatName;
	}
	public void setThirdCatName(String thirdCatName) {
		this.thirdCatName = thirdCatName;
	}
	public BigDecimal getRecommendPrice() {
		return recommendPrice;
	}
	public void setRecommendPrice(BigDecimal recommendPrice) {
		this.recommendPrice = recommendPrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Integer getListStatus() {
		return listStatus;
	}
	public void setListStatus(Integer listStatus) {
		this.listStatus = listStatus;
	}
	
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Integer getIsWholeCountry() {
		return isWholeCountry;
	}
	public void setIsWholeCountry(Integer isWholeCountry) {
		this.isWholeCountry = isWholeCountry;
	}
	public List<PreSaleProductSaleAreaDTO> getPreSaleItemRegionList() {
		return preSaleItemRegionList;
	}
	public void setPreSaleItemRegionList(
			List<PreSaleProductSaleAreaDTO> preSaleItemRegionList) {
		this.preSaleItemRegionList = preSaleItemRegionList;
	}
	
}
