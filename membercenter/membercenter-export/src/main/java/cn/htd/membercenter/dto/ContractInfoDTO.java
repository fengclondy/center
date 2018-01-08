package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.Date;

/** 
 * <Description> 合同基础信息  <br> 
 *  
 * @author zhoutong<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年12月18日 <br>
 */
public class ContractInfoDTO implements Serializable {
	
	/**
	 * serialVersionUID <br>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 合同id <br>
	 */
	public Long contractId;
	
	/**
	 * 合同编码 <br>
	 */
	public String contractCode;
	
	/**
	 * 是否签订标志 0-未签约 1-已签约 <br>
	 */
	public String contractStatus;
	
	/**
	 * 签订时间 <br>
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
	 * 静态合同页面url <br>
	 */
	public String contractUrl;
	
	/**
	 * 合同金额 <br>
	 */
	public Double contractCredit;
	
	/**
	 * 合同标题 <br>
	 */
	public String contractTitle;

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
	public String getContractStatus() {
		return contractStatus;
	}

	/** 
	 * set contractStatus
	 * @param contractStatus The contractStatus to set. <br>
	 */
	public void setContractStatus(String contractStatus) {
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
