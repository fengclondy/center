package cn.htd.searchcenter.service;

import cn.htd.searchcenter.domain.MemberCompanyInfoDTO;

public interface MemberCompanyInfoService {


	public String queryMemberBaseInfoBySellerId(Long sellerId) throws Exception;

	public MemberCompanyInfoDTO queryMemberCompanyInfoBySellerId(Long sellerId) throws Exception;
}
