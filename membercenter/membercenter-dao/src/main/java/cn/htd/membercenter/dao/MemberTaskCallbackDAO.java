package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.CompanyRelationDownErpCallbackDTO;
import cn.htd.membercenter.dto.MemberDownCallbackDTO;
import cn.htd.membercenter.dto.SalesmanDownErpCallbackDTO;

public interface MemberTaskCallbackDAO {
	public int updateErpStatus(@Param("dto") MemberDownCallbackDTO dto);

	public int updateCompanyCode(@Param("dto") MemberDownCallbackDTO dto);

	public int updateBoxErpStatus(@Param("dto") CompanyRelationDownErpCallbackDTO dto);

	public int updateBusinessErpStatus(@Param("dto") SalesmanDownErpCallbackDTO dto);

	/**
	 * @param dto
	 */
	public void delNoUseBusinessErpStatus(@Param("dto") SalesmanDownErpCallbackDTO dto);
}
