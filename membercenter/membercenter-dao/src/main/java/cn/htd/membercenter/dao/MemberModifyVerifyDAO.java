package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.MemberStatusInfo;
import cn.htd.membercenter.domain.VerifyInfo;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.TableSqlDto;
import cn.htd.membercenter.dto.VerifyDetailInfoDTO;
import cn.htd.membercenter.dto.VerifyResultDTO;

public interface MemberModifyVerifyDAO {
	public long selectMemberModifyCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectMemberModifyVerify(
			@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO, @Param("pager") Pager pager);

	public List<MemberStatusInfo> selectModifyVerify(@Param("id") Long id);

	public Long selectVerifyCountByVerifyIds(List<Long> ids);

	public List<VerifyDetailInfoDTO> selectVerifyByVerifyIds(List<Long> ids);

	public int updateTables(List<TableSqlDto> sqlList);

	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectUnMemberVerify(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("pager") Pager pager);

	public long selectUnMemberVerifyCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO);

	public int updateVerifyInfo(@Param("dto") VerifyInfo verufyInfo);

	public VerifyInfo getVerifyInfoById(@Param("id") Long id);

	public VerifyResultDTO selectUnMemeberStatus(@Param("id") Long id);
	@SuppressWarnings("rawtypes")
	public List<MemberBaseInfoDTO> selectUnMemberCompanyNameVerify(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,
			@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("pager") Pager pager);
	public Long selectUnMemberCompanyNameVerifyCount(@Param("memberBaseInfoDTO") MemberBaseInfoDTO memberBaseInfoDTO,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
