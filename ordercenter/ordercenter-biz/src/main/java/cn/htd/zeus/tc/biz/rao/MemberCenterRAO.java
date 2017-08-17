package cn.htd.zeus.tc.biz.rao;

import java.util.List;

import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberBuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberDetailInfo;
import cn.htd.membercenter.dto.MemberGradeDTO;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

public interface MemberCenterRAO {

	/*
	 * 根据会员code和type查询会员信息
	 */
	public OtherCenterResDTO<MemberBaseInfoDTO> selectMemberBaseName(String memberCode,
			String buyerSellerType, String messageId);

	/*
	 * 调用会员中心-查询会员分组id和买家等级
	 */
	public OtherCenterResDTO<MemberGroupDTO> selectBuyCodeSellCode(String sellerCode,
			String buyerCode, String messageId);

	/*
	 * 调用会员中心-查询会员收货地址信息
	 */
	public OtherCenterResDTO<List<MemberConsigAddressDTO>> queryConsigAddressList(String memberCode,
			String messageId);

	/*
	 * 调用会员中心-查询会员发票信息
	 */
	public OtherCenterResDTO<MemberInvoiceDTO> queryMemberInvoiceInfo(String memberCode,
			String channelCode, String messageId);

	/*
	 * 调用会员中心-查询会员等级信息
	 */
	public OtherCenterResDTO<MemberGradeDTO> queryMemberGradeInfo(String memberCode,
			String messageId);

	/*
	 * 调用会员中心-根据用户ID查询用户编码
	 */
	public OtherCenterResDTO<String> queryMemberCodeByMemberId(long memberId, String messageId);

	/*
	 * 调用会员中心-根据用户ID查询会员信息
	 */
	public OtherCenterResDTO<MemberBaseInfoDTO> getMemberDetailBySellerId(long memberId,
			String messageId);

	/*
	 * 根据卖家code和渠道编码查询卖家的地址
	 */
	public OtherCenterResDTO<String> selectChannelAddressDTO(String messageId, String memberCode,
			String channelCode);

	/*
	 * 根据卖家code和渠道编码查询卖家的地址 重写selectChannelAddressDTO方法
	 */
	public OtherCenterResDTO<MemberConsigAddressDTO> selectChannelAddressDTO4Common(
			String messageId, String memberCode, String channelCode);

	/*
	 * 根据code查询id
	 */
	public OtherCenterResDTO<Long> getMemberIdByCode(String memberCode, String messageId);

	/*
	 * 根据会员id查询注册地址
	 */
	public OtherCenterResDTO<MemberDetailInfo> getMemberDetailById(Long memberId, String messageId);

	/**
	 * 查询会员等级详细信息
	 * 
	 * @param memberId
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<MemberBuyerGradeInfoDTO> queryBuyerGradeInfo(Long memberId,
			String messageId);

	/**
	 * 查询外部供应商是否有平台公司身份
	 * 
	 * @param memberCode
	 * @param messageId
	 * @return
	 */
	public OtherCenterResDTO<String> isHasInnerComapanyCert(String memberCode, String messageId);

	/**
	 * 查询外部供应商对应的内部供应商code
	 * 
	 * @param memberCode
	 * @return
	 */
	public OtherCenterResDTO<MemberBaseInfoDTO> getInnerInfoByOuterHTDCode(String memberCode,
			String messageId);

	/**
	 * 根据买家ID、卖家ID、商品类目、商品品牌查询经营关系信息
	 * 
	 * @param memberId
	 * @param sellerId
	 * @param categoryId
	 * @param brandId
	 * @return
	 */
	public OtherCenterResDTO<ApplyBusiRelationDTO> selectBusiRelation(Long memberId, Long sellerId,
			Long categoryId, Long brandId);
}
