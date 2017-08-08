package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberGradeRuleHistoryDTO;
import cn.htd.membercenter.dto.MemberScoreSetDTO;

public interface MemberScoreSetDAO {

	public List<MemberScoreSetDTO> queryMemberScoreSetList(
			@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public List<MemberScoreSetDTO> queryMemberScoreRuleList(
			@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void insertMemberScoreSet(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void insertMemberScoreRule(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void deleteMemberScoreSet(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void deleteMemberScoreRule(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public List<MemberGradeRuleHistoryDTO> queryMemberScoreRuleHistory(
			@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO,
			@Param("pager") Pager<MemberGradeRuleHistoryDTO> pager);

	public long queryMemberScoreRuleHistoryCount(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void insertMemberScoreRuleHistory(
			@Param("memberGradeRuleHistoryDTO") MemberGradeRuleHistoryDTO memberGradeRuleHistoryDTO);

	public void insertMemberScoreWeight(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public void deleteMemberScoreWeight(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

	public MemberScoreSetDTO queryMemberScoreWeight(@Param("memberScoreSetDTO") MemberScoreSetDTO memberScoreSetDTO);

}