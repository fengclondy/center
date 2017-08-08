package cn.htd.searchcenter.forms;

import java.math.BigDecimal;
public class SearchItemForm {
	private String addressCode;
	private String keyword;
	private Integer page;
	private Integer rows;
	private Integer sort;
	private Long categoryId;
	private Long brandId;
	private Long buyerId;
	private BigDecimal priceStart;
	private BigDecimal priceEnd;
	private Long shopId;
	private Long belongRelationSellerId;
	private String belongRelationSellerIdAndName;
	private String hasSku;
	private String canBuy;
	private String onlyProductPlus;
	private String isLogin;
	private String isHotCome;
	private String isAccessJD;
	private String buyerGrade;
	private String shieldCidAndBrandId;
	private String filterParam;
	private String itemCollectionList;
	private String promotionItemIdList;
	private String sellerIdList;
	private String itemCodeList;
	private String promotionItemFlag;
	
	public String getPromotionItemFlag() {
		return promotionItemFlag;
	}

	public void setPromotionItemFlag(String promotionItemFlag) {
		this.promotionItemFlag = promotionItemFlag;
	}

	public String getItemCodeList() {
		return itemCodeList;
	}

	public void setItemCodeList(String itemCodeList) {
		this.itemCodeList = itemCodeList;
	}

	public String getSellerIdList() {
		return sellerIdList;
	}

	public void setSellerIdList(String sellerIdList) {
		this.sellerIdList = sellerIdList;
	}

	public String getPromotionItemIdList() {
		return promotionItemIdList;
	}

	public void setPromotionItemIdList(String promotionItemIdList) {
		this.promotionItemIdList = promotionItemIdList;
	}

	public String getItemCollectionList() {
		return itemCollectionList;
	}

	public void setItemCollectionList(String itemCollectionList) {
		this.itemCollectionList = itemCollectionList;
	}

	public String getBelongRelationSellerIdAndName() {
		return belongRelationSellerIdAndName;
	}

	public void setBelongRelationSellerIdAndName(
			String belongRelationSellerIdAndName) {
		this.belongRelationSellerIdAndName = belongRelationSellerIdAndName;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public BigDecimal getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(BigDecimal priceStart) {
		this.priceStart = priceStart;
	}

	public BigDecimal getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(BigDecimal priceEnd) {
		this.priceEnd = priceEnd;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getBelongRelationSellerId() {
		return belongRelationSellerId;
	}

	public void setBelongRelationSellerId(Long belongRelationSellerId) {
		this.belongRelationSellerId = belongRelationSellerId;
	}

	public String getBuyerGrade() {
		return buyerGrade;
	}

	public void setBuyerGrade(String buyerGrade) {
		this.buyerGrade = buyerGrade;
	}

	public String getShieldCidAndBrandId() {
		return shieldCidAndBrandId;
	}

	public void setShieldCidAndBrandId(String shieldCidAndBrandId) {
		this.shieldCidAndBrandId = shieldCidAndBrandId;
	}

	public String getFilterParam() {
		return filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}

	public String getHasSku() {
		return hasSku;
	}

	public void setHasSku(String hasSku) {
		this.hasSku = hasSku;
	}

	public String getCanBuy() {
		return canBuy;
	}

	public void setCanBuy(String canBuy) {
		this.canBuy = canBuy;
	}

	public String getOnlyProductPlus() {
		return onlyProductPlus;
	}

	public void setOnlyProductPlus(String onlyProductPlus) {
		this.onlyProductPlus = onlyProductPlus;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getIsHotCome() {
		return isHotCome;
	}

	public void setIsHotCome(String isHotCome) {
		this.isHotCome = isHotCome;
	}

	public String getIsAccessJD() {
		return isAccessJD;
	}

	public void setIsAccessJD(String isAccessJD) {
		this.isAccessJD = isAccessJD;
	}
}
