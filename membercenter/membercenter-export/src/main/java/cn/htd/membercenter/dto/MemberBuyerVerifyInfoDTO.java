package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberBuyerVerifyInfoDTO</p>
* @author root
* @date 2016年12月27日
* <p>Description: 
*			买家中心  审批信息表
* </p>
 */
public class MemberBuyerVerifyInfoDTO implements Serializable{

	private static final long serialVersionUID = -7333639857655714087L;
	
	private Long id;//审批信息表id
    private Long belongSellerId;//所属卖家ID
    private String recordType;//记录类型
    private Long recordId;//记录ID
    private Long verifierId;//审核人ID
    private String verifierName;//审核人名称
    private String verifyTime;//审核时间
    private String verifyStatus;//审核状态 1为待审核，2为通过，3为驳回',
    private String remark;//审核备注
    private Long createId;//创建人id
    private String createName;//创建人名称
    private String createTime;//
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBelongSellerId() {
		return belongSellerId;
	}
	public void setBelongSellerId(Long belongSellerId) {
		this.belongSellerId = belongSellerId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public Long getVerifierId() {
		return verifierId;
	}
	public void setVerifierId(Long verifierId) {
		this.verifierId = verifierId;
	}
	public String getVerifierName() {
		return verifierName;
	}
	public void setVerifierName(String verifierName) {
		this.verifierName = verifierName;
	}
	public String getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    
    
}
