package cn.htd.tradecenter.domain;

import java.util.Date;

public class ExpressModel {

	private Long id;// 主键

	private Long sellerId;// 商家ID

	private Long shopId;// 店铺ID

	private String name;// 模版名称

	private String deliveryCode;// 快递公司id

	private String deliveryName;// 快递公司名称

	private String expressPicUrl;// 快递单图片url

	private String modelWidth;// 模版宽度

	private String modelHeigh;// 模版高度

	private String expressPrint;// 模版默认打印项

	private String model;// 模版html

	private int isDefault;// 模版是否默认

	private String moveLeft;// 左右偏移

	private String moveTop;// 上下偏移

	private Long createId;// 创建人ID

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人名称

	private Date modifyTime;// 修改时间

	private Date startTime;// 修改时间

	private Date endTime;// 修改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getExpressPicUrl() {
		return expressPicUrl;
	}

	public void setExpressPicUrl(String expressPicUrl) {
		this.expressPicUrl = expressPicUrl;
	}

	public String getModelWidth() {
		return modelWidth;
	}

	public void setModelWidth(String modelWidth) {
		this.modelWidth = modelWidth;
	}

	public String getModelHeigh() {
		return modelHeigh;
	}

	public void setModelHeigh(String modelHeigh) {
		this.modelHeigh = modelHeigh;
	}

	public String getExpressPrint() {
		return expressPrint;
	}

	public void setExpressPrint(String expressPrint) {
		this.expressPrint = expressPrint;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(String moveLeft) {
		this.moveLeft = moveLeft;
	}

	public String getMoveTop() {
		return moveTop;
	}

	public void setMoveTop(String moveTop) {
		this.moveTop = moveTop;
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
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
}