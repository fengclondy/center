package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BelongRelationship;
import cn.htd.membercenter.dto.BelongRelationshipDTO;

public interface BelongRelationshipDAO {

	/**
	 * 查询会员归属关系列表
	 * 
	 * @param memberId
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public List<BelongRelationshipDTO> selectBelongRelationList(@Param("page") Pager page,
			@Param("companyName") String companyName, @Param("contactMobile") String contactMobile,
			@Param("belongSellerName") String belongSellerName);

	/**
	 * 查询会员归属关系详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public BelongRelationshipDTO selectBelongRelationInfo(@Param("memberId") Long memberId,
			@Param("sellerId") Long sellerId);

	/**
	 * 查询会员历史归属列表
	 * 
	 * @param memberId
	 * @return
	 */
	public List<BelongRelationship> selectBelongHistoryList(@Param("memberId") Long memberId);

	/**
	 * 根据会员ID修改会员基本信息表归属关系
	 * 
	 * @param belongRelationshipDto
	 * @return
	 */
	public void updateBaseInfo(BelongRelationshipDTO belongRelationshipDto);

	/**
	 * 根据会员ID修改会员归属关系表信息
	 * 
	 * @param belongRelationshipDto
	 * @return
	 */
	public void updateBelongInfo(BelongRelationshipDTO belongRelationshipDto);

	/**
	 * 新增会员归属关系：belongRelationshipDto中要设置归属商家和客户经理的ID
	 * 
	 * @param belongRelationshipDto
	 * @return
	 */
	public void insertBelongInfo(BelongRelationshipDTO belongRelationshipDto);

	/**
	 * 根据买家ID和卖家ID查询是否有归属关系
	 * 
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public Long selectIsBelongRelation(@Param("buyerId") Long buyerId, @Param("sellerId") Long sellerId);

	/**
	 * 更新会员归属关系审核状态
	 * 
	 * @param belongRelationshipDto
	 * @return
	 */
	public int updateBelongVerify(@Param("dto") BelongRelationshipDTO belongRelationshipDto);

	
}
