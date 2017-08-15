package cn.htd.searchcenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.searchcenter.domain.MemberCompanyInfoDTO;

public interface MemberCompanyInfoDAO {

	public MemberCompanyInfoDTO queryMemberCompanyInfoBySellerId(@Param("sellerId") Long sellerId);

	public String queryMemberBaseInfoBySellerId(@Param("sellerId") Long sellerId);
}
