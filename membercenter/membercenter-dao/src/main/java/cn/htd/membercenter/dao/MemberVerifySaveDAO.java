package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.ApplyBusiRelationDTO;

public interface MemberVerifySaveDAO {
	/**
	 * 1.保存审核结果，更新审核信息
	 * 
	 * @param memberId
	 * @param verifyStatus
	 */
	public void saveMemberVerifyInfo(@Param("memberId") Long memberId, @Param("verifyStatus") String verifyStatus,
			@Param("modifyId") Long modifyId, @Param("modifyName") String modifyName);

	/**
	 * 2.保存审核结果，更新会员注册状态信息
	 * 
	 * @param memberId
	 * @param verifyStatus
	 */
	public void saveStatusInfoTypeByMemberId(@Param("verifyId") Long verifyId, @Param("memberId") Long memberId,
			@Param("verifyStatus") String verifyStatus, @Param("modifyId") Long modifyId,
			@Param("modifyName") String modifyName);

	/**
	 * 3.保存通过审核的品牌品类信息
	 * 
	 * @param applyBusiRelationDto
	 */
	public void insertBusinessRelationInfo(ApplyBusiRelationDTO applyBusiRelationDto);

	/**
	 * 4.保存归属关系表里的会员属性信息
	 * 
	 * @param applyBusiRelationDto
	 */
	public void updateBelongRelationInfo(ApplyBusiRelationDTO applyBusiRelationDto);

	/**
	 * 驳回自动归属于0801公司
	 */
	public void updateBelongRelationRebut(@Param("id") Long id, @Param("curBelongSellerId") Long curBelongSellerId,
			@Param("modifyId") Long modifyId, @Param("modifyName") String modifyName);

	/**
	 * @param memberId
	 * @param string
	 */
	public void updateShowIdMsg(@Param("memberId") Long memberId, @Param("show") int show);

	/**
	 * @param memberId
	 * @return
	 */
	public int queryShowIdMsg(@Param("memberId") Long memberId);
}
