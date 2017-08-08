package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.GradeMemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberCallCenterDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;

public interface MemberCallCenterDAO {
	/**
	 * 会员呼叫中心查询
	 * 
	 * @param memberCode
	 * @param companyName
	 * @param artificialPersonMobile
	 * @return
	 */
	public MemberCallCenterDTO selectMemberCallCenterInfo(@Param("memberCode") String memberCode,
			@Param("companyName") String companyName, @Param("artificialPersonMobile") String artificialPersonMobile);

	/**
	 * 根据开始时间和结束时间查询会员等级基本信息
	 * 
	 * @param packageActiveStartTime
	 *            例：格式：年月日：(‘2012-06-08’)或'20120608' 年月日时分秒：格式‘2012-06-08
	 *            10:48:55’
	 * @param packageActiveEndTime
	 * @return
	 */
	public List<GradeMemberCompanyInfoDTO> selectGradeList(
			@Param("packageActiveStartTime") String packageActiveStartTime,
			@Param("packageActiveEndTime") String packageActiveEndTime);

	/**
	 * 查询供应商IDby名称
	 */
	public MemberBaseInfoDTO selectMemberBaseName(@Param("memberCode") String memberCode,
			@Param("buyerSellerType") String buyerSellerType);

	/**
	 * 根据买家和卖家ID查询当前分组ID和等级
	 */
	public MemberGroupDTO selectBuyCodeSellCode(@Param("sellerCode") String sellerCode,
			@Param("buyerCode") String buyerCode);

	/**
	 * 根据会员编码查询会员收货地址信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public List<MemberConsigAddressDTO> selectConsigAddressList(@Param("memberCode") String memberCode);

	/**
	 * 根据会员编码查询会员发票信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public MemberInvoiceDTO queryMemberInvoiceInfo(@Param("memberCode") String memberCode);

	/**
	 * 根据会员编码查询会员等级信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public MemberGradeDTO queryMemberGradeInfo(@Param("memberCode") String memberCode);

	/**
	 * 根据会员编码查询会员等级信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public MemberCallCenterDTO queryCompanyInfoByCode(@Param("companyCode") String companyCode);

	/**
	 * @param memberCode
	 * @param channelCode
	 * @return
	 */
	public MemberInvoiceDTO queryMemberInvoiceInfoByChannelCode(@Param("memberCode") String memberCode,
			@Param("channelCode") String channelCode);
}
