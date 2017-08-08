package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.membercenter.domain.MemberBackupContactInfo;
import cn.htd.membercenter.domain.VerifyDetailInfo;

public class MemberDetailInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MemberBaseInfoDTO memberBaseInfoDTO;// 会员基本信息

	private List<MemberBackupContactInfo> memberBackupContactInfo;// 会员备份联系人信息

	private VerifyResultDTO verifyResultDTO;// 审核结果信息

	private MemberInvoiceInfoDTO memberInvoiceInfo;// 发票信息

	private List<VerifyDetailInfo> verifyDetailInfoList;// 信息修改履历信息

	public MemberBaseInfoDTO getMemberBaseInfoDTO() {
		return memberBaseInfoDTO;
	}

	public void setMemberBaseInfoDTO(MemberBaseInfoDTO memberBaseInfoDTO) {
		this.memberBaseInfoDTO = memberBaseInfoDTO;
	}

	public List<MemberBackupContactInfo> getMemberBackupContactInfo() {
		return memberBackupContactInfo;
	}

	public void setMemberBackupContactInfo(List<MemberBackupContactInfo> memberBackupContactInfo) {
		this.memberBackupContactInfo = memberBackupContactInfo;
	}

	public MemberInvoiceInfoDTO getMemberInvoiceInfo() {
		return memberInvoiceInfo;
	}

	public void setMemberInvoiceInfo(MemberInvoiceInfoDTO memberInvoiceInfo) {
		this.memberInvoiceInfo = memberInvoiceInfo;
	}

	public List<VerifyDetailInfo> getVerifyDetailInfoList() {
		return verifyDetailInfoList;
	}

	public void setVerifyDetailInfoList(List<VerifyDetailInfo> verifyDetailInfoList) {
		this.verifyDetailInfoList = verifyDetailInfoList;
	}

	/**
	 * @return the verifyResultDTO
	 */
	public VerifyResultDTO getVerifyResultDTO() {
		return verifyResultDTO;
	}

	/**
	 * @param verifyResultDTO
	 *            the verifyResultDTO to set
	 */
	public void setVerifyResultDTO(VerifyResultDTO verifyResultDTO) {
		this.verifyResultDTO = verifyResultDTO;
	}

}
