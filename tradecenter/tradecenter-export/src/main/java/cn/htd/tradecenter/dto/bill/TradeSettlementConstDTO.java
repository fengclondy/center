package cn.htd.tradecenter.dto.bill;

import java.util.Date;

/**
 * Created by taolei on 17/2/14. 结算单明细
 */
public class TradeSettlementConstDTO {

	private Long id;
	private String constType;
	private String constKey;
	private String constValue;
	private String constStatus;
	private Integer displayOrder;
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

	public String getConstType() {
		return constType;
	}

	public void setConstType(String constType) {
		this.constType = constType;
	}

	public String getConstKey() {
		return constKey;
	}

	public void setConstKey(String constKey) {
		this.constKey = constKey;
	}

	public String getConstValue() {
		return constValue;
	}

	public void setConstValue(String constValue) {
		this.constValue = constValue;
	}

	public String getConstStatus() {
		return constStatus;
	}

	public void setConstStatus(String constStatus) {
		this.constStatus = constStatus;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

}
