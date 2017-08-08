package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemSkuPublishInfo implements Serializable {
	    /**
	 * 
	 */
	private static final long serialVersionUID = -5154007933125263436L;

		private Long id;

	    private Long skuId;

	    private Long itemId;

	    private String skuCode;

	    private Integer isBoxFlag; //是否是包厢库存标记

	    private String note; //备注

	    private Integer displayQuantity; //显示库存，可用库存

	    private Integer reserveQuantity; //锁定库存

	    private Integer mimQuantity; //起定数

	    private Integer isPurchaseLimit; //是否限购

	    private Integer maxPurchaseQuantity; //最大限购数

	    private Integer isVisable; //是否可见标记

	    private Date visableTime; //上架时间

	    private Date invisableTime; //下架时间

	    private Integer isAutomaticVisable; //自动上架标志

	    private Integer automaticVisableWithStock; //按可见库存自动上下架标志 0 否 1 是，当is_automatic_visable字段为1时，该字段生效

	    private Date automaticStarttime; //自动开始时间

	    private Date automaticEndtime; //自动结束时间

	    private Double shippingCost; //运费

	    private Integer erpSync; //同步ERP库存标志 0:否 1：是

	    private Long createId;

	    private String createName;

	    private Date createTime;

	    private Long modifyId;

	    private String modifyName;

	    private Date modifyTime;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getSkuId() {
	        return skuId;
	    }

	    public void setSkuId(Long skuId) {
	        this.skuId = skuId;
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
	        this.skuCode = skuCode == null ? null : skuCode.trim();
	    }

	    public Integer getIsBoxFlag() {
	        return isBoxFlag;
	    }

	    public void setIsBoxFlag(Integer isBoxFlag) {
	        this.isBoxFlag = isBoxFlag;
	    }

	    public String getNote() {
	        return note;
	    }

	    public void setNote(String note) {
	        this.note = note == null ? null : note.trim();
	    }

	    public Integer getDisplayQuantity() {
	        return displayQuantity;
	    }

	    public void setDisplayQuantity(Integer displayQuantity) {
	        this.displayQuantity = displayQuantity;
	    }

	    public Integer getReserveQuantity() {
	        return reserveQuantity;
	    }

	    public void setReserveQuantity(Integer reserveQuantity) {
	        this.reserveQuantity = reserveQuantity;
	    }

	    public Integer getMimQuantity() {
	        return mimQuantity;
	    }

	    public void setMimQuantity(Integer mimQuantity) {
	        this.mimQuantity = mimQuantity;
	    }

	    public Integer getIsPurchaseLimit() {
	        return isPurchaseLimit;
	    }

	    public void setIsPurchaseLimit(Integer isPurchaseLimit) {
	        this.isPurchaseLimit = isPurchaseLimit;
	    }

	    public Integer getMaxPurchaseQuantity() {
	        return maxPurchaseQuantity;
	    }

	    public void setMaxPurchaseQuantity(Integer maxPurchaseQuantity) {
	        this.maxPurchaseQuantity = maxPurchaseQuantity;
	    }

	    public Integer getIsVisable() {
	        return isVisable;
	    }

	    public void setIsVisable(Integer isVisable) {
	        this.isVisable = isVisable;
	    }

	    public Date getVisableTime() {
	        return visableTime;
	    }

	    public void setVisableTime(Date visableTime) {
	        this.visableTime = visableTime;
	    }

	    public Date getInvisableTime() {
	        return invisableTime;
	    }

	    public void setInvisableTime(Date invisableTime) {
	        this.invisableTime = invisableTime;
	    }

	    public Integer getIsAutomaticVisable() {
	        return isAutomaticVisable;
	    }

	    public void setIsAutomaticVisable(Integer isAutomaticVisable) {
	        this.isAutomaticVisable = isAutomaticVisable;
	    }

	    public Integer getAutomaticVisableWithStock() {
	        return automaticVisableWithStock;
	    }

	    public void setAutomaticVisableWithStock(Integer automaticVisableWithStock) {
	        this.automaticVisableWithStock = automaticVisableWithStock;
	    }

	    public Date getAutomaticStarttime() {
	        return automaticStarttime;
	    }

	    public void setAutomaticStarttime(Date automaticStarttime) {
	        this.automaticStarttime = automaticStarttime;
	    }

	    public Date getAutomaticEndtime() {
	        return automaticEndtime;
	    }

	    public void setAutomaticEndtime(Date automaticEndtime) {
	        this.automaticEndtime = automaticEndtime;
	    }

	    public Double getShippingCost() {
	        return shippingCost;
	    }

	    public void setShippingCost(Double shippingCost) {
	        this.shippingCost = shippingCost;
	    }

	    public Integer getErpSync() {
	        return erpSync;
	    }

	    public void setErpSync(Integer erpSync) {
	        this.erpSync = erpSync;
	    }

	    public Long getCreateId() {
	        return createId;
	    }

	    public void setCreateId(Long createId) {
	        this.createId = createId;
	    }

	    public String getCreateName() {
	        return createName;
	    }

	    public void setCreateName(String createName) {
	        this.createName = createName == null ? null : createName.trim();
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public Long getModifyId() {
	        return modifyId;
	    }

	    public void setModifyId(Long modifyId) {
	        this.modifyId = modifyId;
	    }

	    public String getModifyName() {
	        return modifyName;
	    }

	    public void setModifyName(String modifyName) {
	        this.modifyName = modifyName == null ? null : modifyName.trim();
	    }

	    public Date getModifyTime() {
	        return modifyTime;
	    }

	    public void setModifyTime(Date modifyTime) {
	        this.modifyTime = modifyTime;
	    }

	@Override
	public String toString() {
		return "ItemSkuPublishInfo{" +
				"id=" + id +
				", skuId=" + skuId +
				", itemId=" + itemId +
				", skuCode='" + skuCode + '\'' +
				", isBoxFlag=" + isBoxFlag +
				", note='" + note + '\'' +
				", displayQuantity=" + displayQuantity +
				", reserveQuantity=" + reserveQuantity +
				", mimQuantity=" + mimQuantity +
				", isPurchaseLimit=" + isPurchaseLimit +
				", maxPurchaseQuantity=" + maxPurchaseQuantity +
				", isVisable=" + isVisable +
				", visableTime=" + visableTime +
				", invisableTime=" + invisableTime +
				", isAutomaticVisable=" + isAutomaticVisable +
				", automaticVisableWithStock=" + automaticVisableWithStock +
				", automaticStarttime=" + automaticStarttime +
				", automaticEndtime=" + automaticEndtime +
				", shippingCost=" + shippingCost +
				", erpSync=" + erpSync +
				", createId=" + createId +
				", createName='" + createName + '\'' +
				", createTime=" + createTime +
				", modifyId=" + modifyId +
				", modifyName='" + modifyName + '\'' +
				", modifyTime=" + modifyTime +
				'}';
	}
}