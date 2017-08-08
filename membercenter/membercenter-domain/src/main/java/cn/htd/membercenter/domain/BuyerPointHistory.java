package cn.htd.membercenter.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BuyerPointHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long historyId;// 经验值履历ID

	private Long buyerId;// 会员店ID

	private String pointType;// 经验值类型

	private String orderId;// 订单ID

	private BigDecimal providePoint;// 发放经验值ֵ

	private Date provideTime;// 发放时间

	private Byte isVisible;// 是否显示标识

	private Byte deleteFlag;// 0、未删除，1、已删除

	private Long createId;// 创建人id

	private String createName;// 创建人名称

	private Date createTime;// 创建时间

	private Long modifyId;// 更新人ID

	private String modifyName;// 更新人名称

	private Date modifyTime;// 更新时间

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType == null ? null : pointType.trim();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getProvidePoint() {
		return providePoint;
	}

	public void setProvidePoint(BigDecimal providePoint) {
		this.providePoint = providePoint;
	}

	public Date getProvideTime() {
		return provideTime;
	}

	public void setProvideTime(Date provideTime) {
		this.provideTime = provideTime;
	}

	public Byte getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Byte isVisible) {
		this.isVisible = isVisible;
	}

	public Byte getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Byte deleteFlag) {
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
}