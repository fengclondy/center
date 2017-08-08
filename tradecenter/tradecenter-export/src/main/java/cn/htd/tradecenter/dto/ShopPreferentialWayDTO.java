package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Description: [计价方式DTO]
 * </p>
 */
public class ShopPreferentialWayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;// 优惠方式id，主键

	private Long templateId;// 运费模板id

//	private Integer deliveryType;// 优惠方式，1件数，2重量，3体积 4金额

	private String preferentialWay;//优惠方式；1件数， 2金额

	private Integer strategy;// 策略，1满减，2包邮

	private BigDecimal full;// 满多少件/重量/体积

	private BigDecimal reduce;// 减多少钱（元）

	private Long shopId;// 店铺id

	private Long sellerId;// 卖家id

	private Date createTime;// 优惠方式创建时间

//	private Date updateTime;// 优惠方式修改时间

	private Date modifyTime;//优惠方式修改时间
	private Byte deleteFlag;// 是否删除

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

	public Integer getStrategy() {
		return strategy;
	}

	public void setStrategy(Integer strategy) {
		this.strategy = strategy;
	}

	public BigDecimal getFull() {
		return full;
	}

	public void setFull(BigDecimal full) {
		this.full = full;
	}

	public BigDecimal getReduce() {
		return reduce;
	}

	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
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

	public String getPreferentialWay() {
		return preferentialWay;
	}

	public void setPreferentialWay(String preferentialWay) {
		this.preferentialWay = preferentialWay;
	}
}