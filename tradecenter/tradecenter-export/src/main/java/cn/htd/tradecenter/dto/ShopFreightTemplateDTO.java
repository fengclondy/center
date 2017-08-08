package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Description: [运费模板DTO]
 * </p>
 */
public class ShopFreightTemplateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 运费模板id，主键

	private String templateName;// 模板名称

	private Long provinceId;// 省

	private Long cityId;// 市

	private Long countyId;// 县

//	private String addressDetails;// 详细地址

	private String deliveryTime;// 发货时间

	private Integer postageFree;// 是否包邮，1自定义运费，2卖家承担运费

	private String deliveryType;// 运送方式，1快递，2EMS，3平邮

	private Integer valuationWay;// 计价方式，1件数，2重量，3体积

	private Long shopId;// 店铺id

	private Long sellerId;// 卖家id

	private Long createId;//创建人ID

	private String createName;//创建人名称

	private Date createTime;// 模板创建时间

	private Long modifyId;//更新人ID

	private String modifyName;//更新人名称

	private Date modifyTime;//更新时间

	private String deliveryName;// 运送方式文字

	private String valuationWayName;// 计价方式文字

	private List<ShopPreferentialWayDTO> shopPreferentialWayList;// 优惠方式子表

	private List<ShopDeliveryTypeDTO> shopDeliveryTypeList;// 运送方式子表

	private Byte deleteFlag;//是否删除（假删）1，未删，2已删

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

	private Date startTime;// 开始时间

	private Date endTime;// 结束时间

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

	public Byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getValuationWayName() {
		return valuationWayName;
	}

	public void setValuationWayName(String valuationWayName) {
		this.valuationWayName = valuationWayName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPostageFree() {
		return postageFree;
	}

	public void setPostageFree(Integer postageFree) {
		this.postageFree = postageFree;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getValuationWay() {
		return valuationWay;
	}

	public void setValuationWay(Integer valuationWay) {
		this.valuationWay = valuationWay;
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

	public List<ShopDeliveryTypeDTO> getShopDeliveryTypeList() {
		return shopDeliveryTypeList;
	}

	public void setShopDeliveryTypeList(List<ShopDeliveryTypeDTO> shopDeliveryTypeList) {
		this.shopDeliveryTypeList = shopDeliveryTypeList;
	}

	public List<ShopPreferentialWayDTO> getShopPreferentialWayList() {
		return shopPreferentialWayList;
	}

	public void setShopPreferentialWayList(List<ShopPreferentialWayDTO> shopPreferentialWayList) {
		this.shopPreferentialWayList = shopPreferentialWayList;
	}
}