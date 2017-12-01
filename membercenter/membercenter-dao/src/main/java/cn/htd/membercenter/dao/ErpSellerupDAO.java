package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.domain.ErpInnerVendorUpLog;
import cn.htd.membercenter.domain.MemberCompanyInfo;
import cn.htd.membercenter.domain.MemberInvoiceInfo;
import cn.htd.membercenter.dto.ErpSellerupDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;

public interface ErpSellerupDAO {

	public int saveErpToMemberBaseInfo(@Param("dto") MemberBaseInfoDTO dto);

	public int saveErpToMemberCompanyInfo(@Param("dto") MemberCompanyInfo dto);

	public int saveErpToMemberInvoiceInfo(@Param("dto") MemberInvoiceInfo dto);

	public int saveErpToMemberErpLog(@Param("dto") ErpInnerVendorUpLog dto);

	public int updateMemberInfo(@Param("dto") MemberBaseInfoDTO dto);

	public int updateMemberBaseInfo(@Param("dto") ErpSellerupDTO dto);

	public int updateCompanyInfo(@Param("dto") ErpSellerupDTO dto);
	
	public int updateInvoiceInfo(@Param("dto") ErpSellerupDTO dto);
}
