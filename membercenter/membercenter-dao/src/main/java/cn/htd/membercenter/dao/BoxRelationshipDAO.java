package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberShipDTO;

public interface BoxRelationshipDAO {

	/**
	 * 查询会员包厢关系数量
	 * 
	 * @param memberId
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public Long selectBoxRelationListCount(@Param("companyName") String companyName, @Param("artificialPersonMobile") String artificialPersonMobile,
			@Param("boxSellerName") String boxSellerName, @Param("boxId") Long boxId);
	/**
	 * 查询会员包厢关系列表
	 * 
	 * @param memberId
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public List<BelongRelationshipDTO> selectBoxRelationList(@Param("page") Pager page,
			@Param("companyName") String companyName, @Param("artificialPersonMobile") String artificialPersonMobile,
			@Param("boxSellerName") String boxSellerName, @Param("boxId") Long boxId);

	/**
	 * 根据会员ID和商家ID查询是否有经营关系
	 * 
	 * @param memberId
	 * @param sellerId
	 * @return
	 */
	public List<ApplyBusiRelationDTO> selectBusiRelationList(@Param("memberId") Long memberId,
			@Param("sellerId") Long sellerId);

	/**
	 * 通过买家ID查询所有卖家id列表
	 */
	public List<ApplyBusiRelationDTO> selectBusiRelationBuyerIdList(@Param("memberId") Long memberId);

	/**
	 * 根据会员ID、商家ID、品类品牌Id查询当前归属客户经理
	 * 
	 * @param memberId
	 * @param sellerId
	 * @param categoryId
	 * @param brandId
	 * @return
	 */
	public ApplyBusiRelationDTO selectBusiRelation(@Param("memberId") Long memberId, @Param("sellerId") Long sellerId,
			@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);

	/**
	 * 根据会员名称或者公司名称精确查询该会员或者公司ID
	 * 
	 * @param companyName
	 * @param buyerSellerType
	 * @return
	 */
	public List<MemberBaseDTO> selectCompanyID(@Param("companyName") String companyName,
			@Param("buyerSellerType") String buyerSellerType);

	/**
	 * @param memberId
	 * @return
	 */
	public List<BoxRelationship> selectBoxRelationListByMemberId(@Param("memberId") Long memberId);

	/**
	 * 根据卖家ID查询所有有包厢关系的买家ID
	 * 
	 * @param sellerId
	 * @return
	 */
	public List<BoxRelationship> queryBoxRelationListBySellerId(Long sellerId);

	/**
	 * 通过会员memberCode查询供应商信息
	 */
	public List<MemberShipDTO> selectBoxRelationshipList(@Param("memberCode") String memberCode);
	
	/**
	 * 通过会员memberCode查询供应商信息
	 */
	public List<MemberShipDTO> queryBoxRelationshipList(@Param("memberCode") String memberCode);
	
	/**
	 * 根基会员code精确查询公司名称
	 * @param memberId
	 * @return
	 */
	public String selectCompanyName(@Param("memberCode") String memberId,@Param("memberName") String memberName);
	
}
