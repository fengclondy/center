package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.CycleTradeForbiddenMember;
import cn.htd.membercenter.dto.CycleTradeForbiddenMemberDTO;

/**
 * 互为上下游禁止交易
 * 
 * @author xmz
 * @CreateDate 2018-01-13
 * 
 */
public interface CycleTradeForbiddenMemberDAO {

	/**
	 * 新增互为上下游禁止交易会员
	 * 
	 * @param dto
	 */
	public void insertCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);

	/**
	 * 删除互为上下游禁止交易会员
	 * 
	 * @param dto
	 */
	public void deleteCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);
	
	/**
	 * 修改互为上下游禁止交易会员
	 * @param dto
	 */
	public void updateCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);

	/**
	 * 查询互为上下游禁止交易会员条数
	 * 
	 * @param memberCode
	 * @param memberName
	 * @return
	 */
	public Long selecCycleTradeForbiddenMemberCount(@Param("dto") CycleTradeForbiddenMemberDTO dto);
	
	/**
	 * 根据id查询互为上下游禁止交易会员信息
	 * @param dto
	 * @return
	 */
	public CycleTradeForbiddenMember queryCycleTradeForbiddenMember(@Param("dto") CycleTradeForbiddenMemberDTO dto);
	
	/**
	 * 查询互为上下游禁止交易会员集合
	 * 
	 * @param memberCode
	 * @param memberName
	 * @return
	 */
	public List<CycleTradeForbiddenMember> selectCycleTradeForbiddenMemberList(
			@Param("dto") CycleTradeForbiddenMemberDTO dto, @Param("pager") Pager<CycleTradeForbiddenMemberDTO> pager);
}
