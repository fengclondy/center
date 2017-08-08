package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.domain.VerifyDetailInfo;

public class MemberInfoMotifyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long operatorId;
	private String operatorName;
	private String industryCategory;
	private Long memberId;
	private String artificialPersonName;
	private String artificialPersonIdcard;
	private String companyName;
	private List<VerifyDetailInfo> verifyDetailInfoList;

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public String getArtificialPersonIdcard() {
		return artificialPersonIdcard;
	}

	public void setArtificialPersonIdcard(String artificialPersonIdcard) {
		this.artificialPersonIdcard = artificialPersonIdcard;
	}

	/**
	 * @return the verifyDetailInfoList
	 */
	public List<VerifyDetailInfo> getVerifyDetailInfoList() {
		return verifyDetailInfoList;
	}

	/**
	 * @param verifyDetailInfoList
	 *            the verifyDetailInfoList to set
	 */
	public void setVerifyDetailInfoList(List<VerifyDetailInfo> verifyDetailInfoList) {
		this.verifyDetailInfoList = verifyDetailInfoList;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName
	 *            the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	/**
	 * @return the industryCategory
	 */
	public String getIndustryCategory() {
		return industryCategory;
	}

	/**
	 * @param industryCategory
	 *            the industryCategory to set
	 */
	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}
}
