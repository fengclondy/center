package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberGroupDTO;
import cn.htd.membercenter.dto.MemberGroupRelationDTO;

public interface MemberGroupDAO {

	public List<MemberGroupDTO> queryMemberGroupListInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO,
			@Param("pager") Pager<MemberGroupDTO> pager);

	public long queryMemberGroupListCount(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<MemberGroupDTO> queryMemberGroupListInfo4select(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<MemberGroupDTO> queryMemberNoneGroupListInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public MemberGroupDTO queryMemberGroupInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<MemberGroupRelationDTO> queryMemberGroupRelationListInfoByGroupId(
			@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);
	
	public List<MemberGroupRelationDTO> queryMemberGroupRelationListInfoByGroupIdPage(
			@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO,@Param("pager") Pager<MemberGroupDTO> pager);

	public void deleteMemberGroupInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public int insertMemberGroupInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public void insertMemberGroupRelationInfo(
			@Param("memberGroupRelationDTO") MemberGroupRelationDTO memberGroupRelationDTO);

	public void updateMemberGroupInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public void deleteMemberGroupRelationInfo(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<MemberGroupDTO> queryMemberGradeAndGroupList(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public long queryMemberGradeAndGroupListCount(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<String> querySubMemberIdBySellerId(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<String> querySubMemberIdByGradeId(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public List<String> querySubMemberIdByGroupId(@Param("memberGroupDTO") MemberGroupDTO memberGroupDTO);

	public MemberGroupDTO queryGroupInfoBySellerBuyerId(@Param("buyerId") Long buyerId,
			@Param("sellerId") Long sellerId);


}
