package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [快递单模版Dto]
 * </p>
 */
public class ExpressModelDTO implements Serializable {

	private static final long serialVersionUID = 7985268737079579814L;

	private Long id;// 主键

	private String name;// 模版名称

	private String deliveryCode;// 快递公司id

	private String deliveryName;// 快递公司名称

	private String expressPicUrl;// 快递单图片url

	private String modelWidth;// 模版宽度

	private String modelHeigh;// 模版高度

	private String expressPrint;// 模版默认打印项

	private int isDefault;// 模版是否默认

	private Long sellerId;// 卖家id

	private Long shopId;// 店铺id

	private String model;// 模版内容

	private String moveLeft;// 左右偏移

	private String moveTop;// 上下偏移

	private Long createId;// 创建人ID

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人名称

	private Date modifyTime;// 修改时间

	private String startTime;

	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}