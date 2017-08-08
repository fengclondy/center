package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PromotionItemDetailDefineDTO implements Serializable {

	private static final long serialVersionUID = -4018825079560294355L;
	private Long id;
	private Long ruleId; // 规则ID
	private String skuCode; // 商品编码
	private String itemName; // 商品名称
	private int deleteFlag; // 删除标志
	private Long createId; // 创建人ID
	private String createName; // 创建人名称
	private Date createTime; // 创建时间
	private Long modifyId; // 修改人ID
	private String modifyName; // 修改人ID
	private Date modifyTime; // 修改人名称
	private List<Long> deleteDetailList;

	public List<Long> getDeleteDetailList() {
		return deleteDetailList;
	}

	public void setDeleteDetailList(List<Long> deleteDetailList) {
		this.deleteDetailList = deleteDetailList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
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
