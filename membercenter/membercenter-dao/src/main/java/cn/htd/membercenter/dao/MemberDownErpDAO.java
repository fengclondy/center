package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberErpDTO;

public interface MemberDownErpDAO {
	public List<MemberErpDTO> selectErpDownListType1(@Param("dto") MemberErpDTO dto,
			@Param("page") @SuppressWarnings("rawtypes") Pager pager);

	public Long selectErpDownListType1Count(@Param("dto") MemberErpDTO dto);

	public List<MemberErpDTO> selectErpDownListType2(@Param("dto") MemberErpDTO dto,
			@Param("page") @SuppressWarnings("rawtypes") Pager pager);

	public Long selectErpDownListType2Count(@Param("dto") MemberErpDTO dto);

	public List<MemberErpDTO> selectErpDownListType3(@Param("dto") MemberErpDTO dto,
			@Param("page") @SuppressWarnings("rawtypes") Pager pager);

	public Long selectErpDownListType3Count(@Param("dto") MemberErpDTO dto);

	public int updateMemberDownErp(@Param("id") Long id);

	public int updateCompanyRelationDownErp(@Param("id") Long id);

	public int updateSalesManDownErp(@Param("id") Long id);
}
