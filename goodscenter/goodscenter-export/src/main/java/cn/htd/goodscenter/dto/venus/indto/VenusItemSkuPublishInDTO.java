package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;
import cn.htd.pricecenter.dto.StandardPriceDTO;

/**
 * itemsku上架表单信息
 * 
 * @author zhangxiaolong
 *
 */
public class VenusItemSkuPublishInDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7863405239161049595L;

	private Long id;
	// 商品sku编码
	@NotNull(message="skuId不能为null")
	private Long skuId;
    //sku编码
	@NotNull(message="skuCode不能为null")
	private String skuCode;
	//商品itemId
	@NotNull(message="itemId不能为null")
	private Long itemId;
	// 副标题
	private String subTitle;
	// 0:大厅  1：包厢
	private Integer isBoxFlag; //是否是包厢库存标记

	private String note;
	// 显示库存
	@NotNull(message="displayQty不能为null")
	@Digits(integer=100,fraction=100,message="displayQty要为数字")
	private String displayQty;

	private String reserveQty; //锁定库存
	// 同步erp库存标志 0
	private String erpSync;
	// 起订量
	private String minBuyQty;
	// 是否限购
	private String isPurchaseLimit;
	// 最大采购量
	private String maxPurchaseQty;
	// 运费
		private String shippingCost;
	// 上架状态 0 下架 1 上架
	private String isVisible;

	private Date visableTime;//上架时间
	private Date inVisableTime;//下架时间


	// 是否自动上下架
	private String isAutomaticVisible;
	// 按照库存自动上下架
	private String isAutomaticVisibleByStock;
	// 上架时间
	private Date automaticVisibleUpTime;
	// 下架时间
	private Date automaticVisibleDownTime;
	// 描述
	//private ItemDescribe describe;
	// 销售区域
	private ItemSalesArea itemSaleArea;
	private List<ItemSalesAreaDetail> itemSaleAreaDetailList;
	//商品上架标准价格
	private StandardPriceDTO standardPrice;
	//上架类型 1 包厢上架 2 大厅上架
	@NotEmpty(message="shelfType不能为null")
	private String shelfType;
	//操作人员id
	@NotNull(message="operatorId不能为null")
	@Digits(integer=100,fraction=100,message="displayQty要为数字")
	private Long operatorId;
	//操作人员名称
	@NotEmpty(message="operatorName不能为null")
	private String operatorName;

	private Long createId;
	private String createName;
	private Long modifyId;
	private String modifyName;
	private Date createTime;
	private Date modifyTime;
	@NotEmpty(message="supplierCode不能为空")
	private String supplierCode;
	//1 是预售
	private Integer preSaleFlag;

	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDisplayQty() {
		return displayQty;
	}
	public void setDisplayQty(String displayQty) {
		this.displayQty = displayQty;
	}
	public String getErpSync() {
		return erpSync;
	}
	public void setErpSync(String erpSync) {
		this.erpSync = erpSync;
	}
	public String getMinBuyQty() {
		return minBuyQty;
	}
	public void setMinBuyQty(String minBuyQty) {
		this.minBuyQty = minBuyQty;
	}
	public String getIsPurchaseLimit() {
		return isPurchaseLimit;
	}
	public void setIsPurchaseLimit(String isPurchaseLimit) {
		this.isPurchaseLimit = isPurchaseLimit;
	}
	public String getMaxPurchaseQty() {
		return maxPurchaseQty;
	}
	public void setMaxPurchaseQty(String maxPurchaseQty) {
		this.maxPurchaseQty = maxPurchaseQty;
	}
	public String getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(String shippingCost) {
		this.shippingCost = shippingCost;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	public String getIsAutomaticVisible() {
		return isAutomaticVisible;
	}
	public void setIsAutomaticVisible(String isAutomaticVisible) {
		this.isAutomaticVisible = isAutomaticVisible;
	}
	public String getIsAutomaticVisibleByStock() {
		return isAutomaticVisibleByStock;
	}
	public void setIsAutomaticVisibleByStock(String isAutomaticVisibleByStock) {
		this.isAutomaticVisibleByStock = isAutomaticVisibleByStock;
	}
	public Date getAutomaticVisibleUpTime() {
		return automaticVisibleUpTime;
	}
	public void setAutomaticVisibleUpTime(Date automaticVisibleUpTime) {
		this.automaticVisibleUpTime = automaticVisibleUpTime;
	}
	public Date getAutomaticVisibleDownTime() {
		return automaticVisibleDownTime;
	}
	public void setAutomaticVisibleDownTime(Date automaticVisibleDownTime) {
		this.automaticVisibleDownTime = automaticVisibleDownTime;
	}
//	public ItemDescribe getDescribe() {
//		return describe;
//	}
//	public void setDescribe(ItemDescribe describe) {
//		this.describe = describe;
//	}
	public ItemSalesArea getItemSaleArea() {
		return itemSaleArea;
	}
	public void setItemSaleArea(ItemSalesArea itemSaleArea) {
		this.itemSaleArea = itemSaleArea;
	}
	public List<ItemSalesAreaDetail> getItemSaleAreaDetailList() {
		return itemSaleAreaDetailList;
	}
	public void setItemSaleAreaDetailList(
			List<ItemSalesAreaDetail> itemSaleAreaDetailList) {
		this.itemSaleAreaDetailList = itemSaleAreaDetailList;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public StandardPriceDTO getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(StandardPriceDTO standardPrice) {
		this.standardPrice = standardPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public String getReserveQty() {
		return reserveQty;
	}

	public void setReserveQty(String reserveQty) {
		this.reserveQty = reserveQty;
	}

	public Date getVisableTime() {
		return visableTime;
	}

	public void setVisableTime(Date visableTime) {
		this.visableTime = visableTime;
	}

	public Date getInVisableTime() {
		return inVisableTime;
	}

	public void setInVisableTime(Date inVisableTime) {
		this.inVisableTime = inVisableTime;
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
		this.createName = createName;
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
		this.modifyName = modifyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Integer getPreSaleFlag() {
		return preSaleFlag;
	}

	public void setPreSaleFlag(Integer preSaleFlag) {
		this.preSaleFlag = preSaleFlag;
	}

	private boolean newVms;

	public boolean isNewVms() {
		return newVms;
	}

	public void setNewVms(boolean newVms) {
		this.newVms = newVms;
	}
}
