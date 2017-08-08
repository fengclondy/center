package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:查询商品信息列表的的入参DTO]
 * </p>
 */
public class ItemQueryInDTO implements Serializable {
	private static final long serialVersionUID = -6339767973191891159L;

	private Integer id; // 平台商品编码
	private String itemName;// 商品名称
	private Integer itemId;// 商品ID
	private Long cid;// 类目ID
	private Long[] cids;// 类目ID
	private Integer itemStatus;// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
								// 7： 申请解锁
	private Long productId;// 商品货号
	private Long skuId;// sku编码（关联sku编码）
	private Long[] shopIds;// 店铺id组
	private Integer operator;// 操作方，1：商家，2：平台
	private Integer platLinkStatus; // 商品库状态 1：未符合待入库2：待入库3：已入库4：删除

	private Long shopCid;// 店铺分类ID
	private Integer minInvetory;// 最小库存
	private Integer maxInvetory;// 最大库存
	private BigDecimal minPrice;// 最小价格
	private BigDecimal maxPrice;// 最大价格

	private Long platItemId;// 平台商品ID
	private Long sellerId;
	private Long brand;

	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private List<Integer> itemStatusList = new ArrayList<Integer>();// 商品状态列表
	private List<Long> brandIdList = new ArrayList<Long>();// 品牌ID组

	private Integer copied;// 1 未加入商品库 2 已加入平台商品库

	private Long shopFreightTemplateId;// 商品运费模版Id

	private Long payType;// 支付方式，1：货到付款

	// add by lh 商品sku状态
	private Long skuStatus;

	// 商品编码，外接商品时保存外接商品SKUID
	private String itemCode;
	private String skuCode;
	private Integer shelvesStatus; //上下架状态   4:未上架   5:已上架
	
	public Integer getShelvesStatus() {
		return shelvesStatus;
	}

	public void setShelvesStatus(Integer shelvesStatus) {
		this.shelvesStatus = shelvesStatus;
	}

	public Long getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(Long skuStatus) {
		this.skuStatus = skuStatus;
	}

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public Long getShopFreightTemplateId() {
		return shopFreightTemplateId;
	}

	public void setShopFreightTemplateId(Long shopFreightTemplateId) {
		this.shopFreightTemplateId = shopFreightTemplateId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public List<Long> getBrandIdList() {
		return brandIdList;
	}

	public void setBrandIdList(List<Long> brandIdList) {
		this.brandIdList = brandIdList;
	}

	public Integer getPlatLinkStatus() {
		return platLinkStatus;
	}

	public Long[] getCids() {
		return cids;
	}

	public void setCids(Long[] cids) {
		this.cids = cids;
	}

	public void setPlatLinkStatus(Integer platLinkStatus) {
		this.platLinkStatus = platLinkStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long[] getShopIds() {
		return shopIds;
	}

	public void setShopIds(Long[] shopIds) {
		this.shopIds = shopIds;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Long getShopCid() {
		return shopCid;
	}

	public void setShopCid(Long shopCid) {
		this.shopCid = shopCid;
	}

	public Integer getMinInvetory() {
		return minInvetory;
	}

	public void setMinInvetory(Integer minInvetory) {
		this.minInvetory = minInvetory;
	}

	public Integer getMaxInvetory() {
		return maxInvetory;
	}

	public void setMaxInvetory(Integer maxInvetory) {
		this.maxInvetory = maxInvetory;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Long getPlatItemId() {
		return platItemId;
	}

	public void setPlatItemId(Long platItemId) {
		this.platItemId = platItemId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Integer> getItemStatusList() {
		return itemStatusList;
	}

	public void setItemStatusList(List<Integer> itemStatusList) {
		this.itemStatusList = itemStatusList;
	}

	public Integer getCopied() {
		return copied;
	}

	public void setCopied(Integer copied) {
		this.copied = copied;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBrand() {
		return brand;
	}

	public void setBrand(Long brand) {
		this.brand = brand;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
}
