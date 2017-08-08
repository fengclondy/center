/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: MemberImportSuccInfoDTO.java
 * Author:   Administrator
 * Date:     下午3:16:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 */

package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MemberImportSuccInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String memberCode;
	private String memberId;
	private String memberName;

	private String accountNo;

	/**
	 * @return the memberCode
	 */
	public String getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName
	 *            the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

}
