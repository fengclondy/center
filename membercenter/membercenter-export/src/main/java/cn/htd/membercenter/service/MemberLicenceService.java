package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberLicenceInfoVO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberStatusVO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberLicenceService</p>
* @author root
* @date 2016年11月25日
* <p>Description: 
*			非会员转会员  、密码找回、手机号更改  审核功能相关接口
* </p>
 */
public interface MemberLicenceService {
    
	/**
	 * 非会员转会员审核
	 * @param MemberUncheckedDetailDTO
	 * @return
	 */
	public ExecuteResult<Boolean> verifyNonMemberToMember(MemberUncheckedDetailDTO memberUncheckedDetailDTO);
	
	
	/**
	 * 密码找回审核
	 * @param MemberLicenceInfoDetailDTO
	 * @return
	 */
	public ExecuteResult<Boolean> verifyPasswordRecovery(MemberLicenceInfoDetailDTO memberLicenceInfoDetailDTO);
	
	
	/**
	 * 手机号更改审核
	 * @param memberStatusVO
	 * @return
	 */
	public ExecuteResult<Boolean> verifyPhoneChange(MemberLicenceInfoDetailDTO memberLicenceInfoDetailDTO);
	
	/**
	 * 会员解除归属关系待审核
	 * @param memberStatusVO
	 * @return
	 */
	public ExecuteResult<Boolean> verifyRemoveRelationship(MemberRemoveRelationshipDTO MemberRemoveRelationshipDTO);
	
	
	/**
	 * 保存修改非会员发票信息
	 * @param memberStatusVO
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberInvoiceInfo(MemberInvoiceDTO memberInvoiceDTO);
	
}
