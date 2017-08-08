/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: MemberImportFailInfoDTO.java
 * Author:   Administrator
 * Date:     下午3:17:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 */

package cn.htd.membercenter.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MemberImportFailInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberCode;

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

}
