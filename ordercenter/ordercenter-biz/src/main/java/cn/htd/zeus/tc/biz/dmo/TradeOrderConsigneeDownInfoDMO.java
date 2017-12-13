package cn.htd.zeus.tc.biz.dmo;

import java.util.Date;

public class TradeOrderConsigneeDownInfoDMO {
	private Long id;

	private String orderNo;

	private String erpLockBalanceCode;

	private Date consigneeTime;

	private Integer downStatus;

	private Long downTimes;

	private Date downTime;

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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getErpLockBalanceCode() {
		return erpLockBalanceCode;
	}

	public void setErpLockBalanceCode(String erpLockBalanceCode) {
		this.erpLockBalanceCode = erpLockBalanceCode == null ? null
				: erpLockBalanceCode.trim();
	}

	public Date getConsigneeTime() {
		return consigneeTime;
	}

	public void setConsigneeTime(Date consigneeTime) {
		this.consigneeTime = consigneeTime;
	}

	public Integer getDownStatus() {
		return downStatus;
	}

	public void setDownStatus(Integer downStatus) {
		this.downStatus = downStatus;
	}

	public Long getDownTimes() {
		return downTimes;
	}

	public void setDownTimes(Long downTimes) {
		this.downTimes = downTimes;
	}

	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
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