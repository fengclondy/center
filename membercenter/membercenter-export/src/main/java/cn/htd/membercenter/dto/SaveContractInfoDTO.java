/**************************************************************************************** 
        
 ****************************************************************************************/
package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * <Description> 合同信息(保存使用) <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月20日 <br>
 */

public class SaveContractInfoDTO implements Serializable {
	
	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 合同id(不需传递) <br>
	 */
	public Long contractId;
	
	/**
	 * 合同编码(不需传递) <br>
	 */
	public String contractCode;
	
	/**
	 * 是否签订标志 0-未签约 1-已签约 (不需传递) <br>
	 */
	public int contractStatus;
	
	/**
	 * 签订时间 (不需传递) <br>
	 */
	public Date signTime;

	/**
	 * 供应商编码(大B编码) <br>
	 */
	public String vendorCode;
	
	/**
	 * 供应商名称(大B名称) <br>
	 */
	public String vendorName;
	
	/**
	 * 供货商地址 <br>
	 */
	public String vendorLocationAddr;
	
	/**
	 * 供货商法人名称 <br>
	 */
	public String vendorArtificialPersonName;
	
	/**
	 * 会员店编码(小B编码) <br>
	 */
	public String memberCode;
	
	/**
	 * 会员店名称(小B名称) <br>
	 */
	public String memberName;
	
	/**
	 * 会员店地址 <br>
	 */
	public String memberLocationAddr;
	
	/**
	 * 会员店法人名称 <br>
	 */
	public String memberArtificialPersonName;
	
	/**
	 * 静态合同页面url(不需传递) <br>
	 */
	public String contractUrl;
	
	/**
	 * 合同金额(不需传递) <br>
	 */
	public Double contractCredit;
	
	/**
	 * 合同标题(不需传递) <br>
	 */
	public String contractTitle;
	
	/**
	 * 签署人编码 <br>
	 */
	public String signAuthorCode;
	
	/**
	 * 签署人名称 <br>
	 */
	public String signAuthorName;
	
	/**
	 * 合同生效时间(不需传递) <br>
	 */
	public Date signStartTime;
	
	/**
	 * 合同截止日期(不需传递) <br>
	 */
	public Date signEndTime;
	
	/**
	 * 创建人id <br>
	 */
	public Long createId;
	
	/**
	 * 创建人名称 <br>
	 */
	public String createName;
	
	/**
	 * 创建时间 <br>
	 */
	public Date createTime;
	
	/**
	 * 修改人id <br>
	 */
	public Long modifyId;
	
	/**
	 * 修改人名称 <br>
	 */
	public String modifyName;
	
	/**
	 * 修改时间 <br>
	 */
	public Date modifyTime;

	/** 
	 * get contractId
	 * @return Returns the contractId.<br> 
	 */
	public Long getContractId() {
		return contractId;
	}

	/** 
	 * set contractId
	 * @param contractId The contractId to set. <br>
	 */
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	/** 
	 * get contractCode
	 * @return Returns the contractCode.<br> 
	 */
	public String getContractCode() {
		return contractCode;
	}

	/** 
	 * set contractCode
	 * @param contractCode The contractCode to set. <br>
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	/** 
	 * get contractStatus
	 * @return Returns the contractStatus.<br> 
	 */
	public int getContractStatus() {
		return contractStatus;
	}

	/** 
	 * set contractStatus
	 * @param contractStatus The contractStatus to set. <br>
	 */
	public void setContractStatus(int contractStatus) {
		this.contractStatus = contractStatus;
	}

	/** 
	 * get signTime
	 * @return Returns the signTime.<br> 
	 */
	public Date getSignTime() {
		return signTime;
	}

	/** 
	 * set signTime
	 * @param signTime The signTime to set. <br>
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/** 
	 * get vendorCode
	 * @return Returns the vendorCode.<br> 
	 */
	public String getVendorCode() {
		return vendorCode;
	}

	/** 
	 * set vendorCode
	 * @param vendorCode The vendorCode to set. <br>
	 */
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	/** 
	 * get memberCode
	 * @return Returns the memberCode.<br> 
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/** 
	 * set memberCode
	 * @param memberCode The memberCode to set. <br>
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/** 
	 * get contractUrl
	 * @return Returns the contractUrl.<br> 
	 */
	public String getContractUrl() {
		return contractUrl;
	}

	/** 
	 * set contractUrl
	 * @param contractUrl The contractUrl to set. <br>
	 */
	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	/** 
	 * get contractCredit
	 * @return Returns the contractCredit.<br> 
	 */
	public Double getContractCredit() {
		return contractCredit;
	}

	/** 
	 * set contractCredit
	 * @param contractCredit The contractCredit to set. <br>
	 */
	public void setContractCredit(Double contractCredit) {
		this.contractCredit = contractCredit;
	}

	/** 
	 * get contractTitle
	 * @return Returns the contractTitle.<br> 
	 */
	public String getContractTitle() {
		return contractTitle;
	}

	/** 
	 * set contractTitle
	 * @param contractTitle The contractTitle to set. <br>
	 */
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	/** 
	 * get signAuthorCode
	 * @return Returns the signAuthorCode.<br> 
	 */
	public String getSignAuthorCode() {
		return signAuthorCode;
	}

	/** 
	 * set signAuthorCode
	 * @param signAuthorCode The signAuthorCode to set. <br>
	 */
	public void setSignAuthorCode(String signAuthorCode) {
		this.signAuthorCode = signAuthorCode;
	}

	/** 
	 * get signAuthorName
	 * @return Returns the signAuthorName.<br> 
	 */
	public String getSignAuthorName() {
		return signAuthorName;
	}

	/** 
	 * set signAuthorName
	 * @param signAuthorName The signAuthorName to set. <br>
	 */
	public void setSignAuthorName(String signAuthorName) {
		this.signAuthorName = signAuthorName;
	}

	/** 
	 * get signStartTime
	 * @return Returns the signStartTime.<br> 
	 */
	public Date getSignStartTime() {
		return signStartTime;
	}

	/** 
	 * set signStartTime
	 * @param signStartTime The signStartTime to set. <br>
	 */
	public void setSignStartTime(Date signStartTime) {
		this.signStartTime = signStartTime;
	}

	/** 
	 * get signEndTime
	 * @return Returns the signEndTime.<br> 
	 */
	public Date getSignEndTime() {
		return signEndTime;
	}

	/** 
	 * set signEndTime
	 * @param signEndTime The signEndTime to set. <br>
	 */
	public void setSignEndTime(Date signEndTime) {
		this.signEndTime = signEndTime;
	}

	/** 
	 * get createId
	 * @return Returns the createId.<br> 
	 */
	public Long getCreateId() {
		return createId;
	}

	/** 
	 * set createId
	 * @param createId The createId to set. <br>
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/** 
	 * get createName
	 * @return Returns the createName.<br> 
	 */
	public String getCreateName() {
		return createName;
	}

	/** 
	 * set createName
	 * @param createName The createName to set. <br>
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	/** 
	 * get createTime
	 * @return Returns the createTime.<br> 
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/** 
	 * set createTime
	 * @param createTime The createTime to set. <br>
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/** 
	 * get modifyId
	 * @return Returns the modifyId.<br> 
	 */
	public Long getModifyId() {
		return modifyId;
	}

	/** 
	 * set modifyId
	 * @param modifyId The modifyId to set. <br>
	 */
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	/** 
	 * get modifyName
	 * @return Returns the modifyName.<br> 
	 */
	public String getModifyName() {
		return modifyName;
	}

	/** 
	 * set modifyName
	 * @param modifyName The modifyName to set. <br>
	 */
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	/** 
	 * get modifyTime
	 * @return Returns the modifyTime.<br> 
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/** 
	 * set modifyTime
	 * @param modifyTime The modifyTime to set. <br>
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/** 
	 * get vendorName
	 * @return Returns the vendorName.<br> 
	 */
	public String getVendorName() {
		return vendorName;
	}

	/** 
	 * set vendorName
	 * @param vendorName The vendorName to set. <br>
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/** 
	 * get vendorLocationAddr
	 * @return Returns the vendorLocationAddr.<br> 
	 */
	public String getVendorLocationAddr() {
		return vendorLocationAddr;
	}

	/** 
	 * set vendorLocationAddr
	 * @param vendorLocationAddr The vendorLocationAddr to set. <br>
	 */
	public void setVendorLocationAddr(String vendorLocationAddr) {
		this.vendorLocationAddr = vendorLocationAddr;
	}

	/** 
	 * get vendorArtificialPersonName
	 * @return Returns the vendorArtificialPersonName.<br> 
	 */
	public String getVendorArtificialPersonName() {
		return vendorArtificialPersonName;
	}

	/** 
	 * set vendorArtificialPersonName
	 * @param vendorArtificialPersonName The vendorArtificialPersonName to set. <br>
	 */
	public void setVendorArtificialPersonName(String vendorArtificialPersonName) {
		this.vendorArtificialPersonName = vendorArtificialPersonName;
	}

	/** 
	 * get memberName
	 * @return Returns the memberName.<br> 
	 */
	public String getMemberName() {
		return memberName;
	}

	/** 
	 * set memberName
	 * @param memberName The memberName to set. <br>
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/** 
	 * get memberLocationAddr
	 * @return Returns the memberLocationAddr.<br> 
	 */
	public String getMemberLocationAddr() {
		return memberLocationAddr;
	}

	/** 
	 * set memberLocationAddr
	 * @param memberLocationAddr The memberLocationAddr to set. <br>
	 */
	public void setMemberLocationAddr(String memberLocationAddr) {
		this.memberLocationAddr = memberLocationAddr;
	}

	/** 
	 * get memberArtificialPersonName
	 * @return Returns the memberArtificialPersonName.<br> 
	 */
	public String getMemberArtificialPersonName() {
		return memberArtificialPersonName;
	}

	/** 
	 * set memberArtificialPersonName
	 * @param memberArtificialPersonName The memberArtificialPersonName to set. <br>
	 */
	public void setMemberArtificialPersonName(String memberArtificialPersonName) {
		this.memberArtificialPersonName = memberArtificialPersonName;
	}
	
}
