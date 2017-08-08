package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;

public interface MemberBusinessRelationDAO {

	public List<MemberBusinessRelationDTO> queryMemberBusinessRelationListInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO,
			@Param("pager") Pager<MemberBusinessRelationDTO> pager);

	public long queryMemberBusinessRelationListInfoCount(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public void updateMemberBusinessRelationInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public void deleteMemberBusinessRelationInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public void insertMemberBusinessRelationInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public List<MemberBusinessRelationDTO> queryMemberBusinessRelationPendingAudit(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public MemberBusinessRelationDTO queryMemberBusinessRelationDetail(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public long queryMemberBoxRelationInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public void insertMeberBoxRelationInfo(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);

	public List<MemberBusinessRelationDTO> queryMemberBusinessRelationList(@Param("buyerId") Long buyerId,
			@Param("sellerId") Long sellerId, @Param("categoryId") Long categoryId, @Param("brandId") Long brandId);

	public void removeBussinessRelation(
			@Param("memberBusinessRelationDTO") MemberBusinessRelationDTO memberBusinessRelationDTO);
	
	public List<MemberBusinessRelationDTO> selectMemberBussinsessRelationShip(@Param("memberId") Long memberId);
}
