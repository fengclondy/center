package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2016/11/30.
 */
public class PromotionBuyerDetailDefineDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id; // ID
	private Long ruleId; // 规则ID
	private String buyerCode; // 会员编码
	private String buyerName; // 会员名称
	private int deleteFlag; // 删除标志
	private Long createId; // 创建人ID
	private String createName; // 创建人名称
	private Date createTime; // 创建时间
	private Long modifyId; // 修改人ID
	private String modifyName; // 修改人名称
	private Date modifyTime; // 修改时间
	private List<Long> deleteDetailList;

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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
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

	public List<Long> getDeleteDetailList() {
		return deleteDetailList;
	}

	public void setDeleteDetailList(List<Long> deleteDetailList) {
		this.deleteDetailList = deleteDetailList;
	}
}
