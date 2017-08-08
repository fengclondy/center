/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: MemberImportDAO.java
 * Author:   Administrator
 * Date:     下午3:31:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 */

package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.MemberImportFailInfoDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;

/**
 * @author Administrator
 *
 */
public interface MemberImportDAO {

	/**
	 * @param memberImportFailInfoDTO
	 * @return
	 */
	MemberImportSuccInfoDTO queryMemberInfo(
			@Param("memberImportFailInfoDTO") MemberImportFailInfoDTO memberImportFailInfoDTO);

	/**
	 * @param memberImportFailInfoDTO
	 * @return
	 */
	MemberImportSuccInfoDTO querySellerInfo(
			@Param("memberImportFailInfoDTO") MemberImportFailInfoDTO memberImportFailInfoDTO);

}
