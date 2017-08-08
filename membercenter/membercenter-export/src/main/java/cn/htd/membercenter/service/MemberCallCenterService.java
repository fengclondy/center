package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.GradeMemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberCallCenterDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;

public interface MemberCallCenterService {
	/**
	 * 查询呼叫中心信息
	 * 
	 * @param memberCode
	 * @param companyName
	 * @param artificialPersonMobile
	 * @return
	 */
	public ExecuteResult<MemberCallCenterDTO> selectMemberCallCenterInfo(String memberCode, String companyName,
			String artificialPersonMobile);

	/**
	 * 呼叫中心根据来电手机号（客户经理手机号）查询
	 * 
	 * @param artificialPersonMobile
	 * @return
	 */
	public ExecuteResult<MemberCallCenterDTO> selectMobilePhoneCallCenterInfo(String managerMobile);

	/**
	 * 呼叫中心 开始时间和结束时间查询会员等级信息 例：参数格式：年月日：(‘2012-06-08’)或'20120608'
	 * 年月日时分秒：格式‘2012-06-08 10:48:55’
	 * 
	 * @param packageActiveStartTime
	 *            套餐生效开始时间
	 * @param packageActiveEndTime
	 *            套餐生效结束时间
	 * @return
	 */
	public ExecuteResult<List<GradeMemberCompanyInfoDTO>> selectGradeInfoList(String packageActiveStartTime,
			String packageActiveEndTime);

	/**
	 * 查询供应商IDby名称
	 */
	public ExecuteResult<MemberBaseInfoDTO> selectMemberBaseName(String memberCode, String buyerSellerType);

	/**
	 * 根据买家和卖家Code查询当前分组ID
	 */
	public ExecuteResult<MemberGroupDTO> selectBuyCodeSellCode(String sellerCode, String buyerCode);

	/**
	 * 根据会员编码查询会员收货地址信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<List<MemberConsigAddressDTO>> selectConsigAddressList(String memberCode);

	/**
	 * 根据会员编码查询会员发票信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode);

	/**
	 * 根据会员编码查询会员发票信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode, String channelCode);

	/**
	 * 根据会员编码查询会员等级信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberGradeDTO> queryMemberGradeInfo(String memberCode);

	/**
	 * 根据支付帐号查用户信息
	 */
	public ExecuteResult<MemberBaseInfoDTO> selectMemberInfoByAccountNo(String accountNo);

	/**
	 * 根据支付帐号查供应商信息
	 */
	public ExecuteResult<MemberBaseInfoDTO> selectSellerInfoByAccountNo(String accountNo);
}
