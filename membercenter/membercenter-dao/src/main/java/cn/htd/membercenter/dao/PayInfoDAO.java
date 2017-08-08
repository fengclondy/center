package cn.htd.membercenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.BindingBankCardCallbackDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;

public interface PayInfoDAO {
	public int updateMemberCertInfo(@Param("dto") MemberBaseInfoDTO dto);

	public int updateMemberPhoneVerify(@Param("dto") MemberBaseInfoDTO dto);
	
	public int updateMemberBankInfo(@Param("dto") MemberOutsideSupplierCompanyDTO dto);
}
