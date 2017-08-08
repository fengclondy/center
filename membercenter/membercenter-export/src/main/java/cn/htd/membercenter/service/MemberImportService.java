/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberImportService</p>
* @author jiangt
* @date 2016年12月1日
* <p>Description: 
*			会员供应商导入相关接口
* </p>
 */
package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberImportFailInfoDTO;
import cn.htd.membercenter.dto.MemberImportInfoDTO;

public interface MemberImportService {

	/**
	 * 会员导入
	 * 
	 * @param List<MemberImportFailInfoDTO>
	 * @return
	 */
	public ExecuteResult<MemberImportInfoDTO> memberImport(List<MemberImportFailInfoDTO> impInfoList);

	/**
	 * 供应商导入
	 * 
	 * @param List<MemberImportFailInfoDTO>
	 * @return
	 */
	public ExecuteResult<MemberImportInfoDTO> sellerImport(List<MemberImportFailInfoDTO> impInfoList);

}
