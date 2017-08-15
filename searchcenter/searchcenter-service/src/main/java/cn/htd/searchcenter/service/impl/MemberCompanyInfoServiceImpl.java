package cn.htd.searchcenter.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.searchcenter.dao.MemberCompanyInfoDAO;
import cn.htd.searchcenter.datasource.DataSource;
import cn.htd.searchcenter.domain.MemberCompanyInfoDTO;
import cn.htd.searchcenter.service.MemberCompanyInfoService;

@Service("memberCompanyInfoService")
@DataSource("dataSource_memberCenter")
public class MemberCompanyInfoServiceImpl implements  MemberCompanyInfoService{

	@Resource
	private MemberCompanyInfoDAO memberCompanyInfoDao;

	@Override
	public String queryMemberBaseInfoBySellerId(Long sellerId) throws Exception {
		return memberCompanyInfoDao.queryMemberBaseInfoBySellerId(sellerId);
	}


	@Override
	public MemberCompanyInfoDTO queryMemberCompanyInfoBySellerId(Long sellerId)
			throws Exception {
		return memberCompanyInfoDao.queryMemberCompanyInfoBySellerId(sellerId);
	}
}
