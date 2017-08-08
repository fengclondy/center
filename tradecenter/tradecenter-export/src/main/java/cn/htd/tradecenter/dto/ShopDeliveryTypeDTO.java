package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description: [店铺运费定义DTO]
 * </p>
 */
public class ShopDeliveryTypeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 运送方式id，主键

	private Long templateId;// 运费模板ID

	private Long sellerId;// 卖家id

	private Long shopId;// 店铺id

	private Integer deliveryType;// 配送方式，1快递，2EMS，3平邮

	private BigDecimal firstPart;// 首件/首重量/首体积

	private BigDecimal firstPrice;// 首费

//	private BigDecimal continues;// 续件/续重量/续体积

	private BigDecimal continuePart;//续件/续重量/续体积
	private BigDecimal continuePrice;// 续费

	private String deliveryTo;// 配送至城市编码

	private String deliveryAddress;// 配送至城市名称

	private byte deleteFlag;//删除标记

	private Long createId;//创建人ID

	private String createName;//创建人名称

	private Date createTime;//创建时间

	private Long modifyId;//更新人ID

	private String modifyName;//更新人名称

	private Date modifyTime;//更新时间

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public BigDecimal getContinuePart() {
		return continuePart;
	}

	public void setContinuePart(BigDecimal continuePart) {
		this.continuePart = continuePart;
	}

	private Date updateTime;// 运送方式时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public BigDecimal getFirstPart() {
		return firstPart;
	}

	public void setFirstPart(BigDecimal firstPart) {
		this.firstPart = firstPart;
	}

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public BigDecimal getContinuePrice() {
		return continuePrice;
	}

	public void setContinuePrice(BigDecimal continuePrice) {
		this.continuePrice = continuePrice;
	}

	public String getDeliveryTo() {
		return deliveryTo;
	}

	public void setDeliveryTo(String deliveryTo) {
		this.deliveryTo = deliveryTo;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(byte deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}