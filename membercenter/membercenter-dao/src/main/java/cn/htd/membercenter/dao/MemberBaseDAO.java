package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberAddrInfoDTO;
import cn.htd.membercenter.dto.MemberBaseDTO;

public interface MemberBaseDAO {

	public MemberBaseDTO queryMemberBaseInfoById(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);
	
	public MemberAddrInfoDTO queryMemberBaseInfoByMemberCodeAndType(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public List<MemberBaseDTO> queryMemberInfoBySellerId(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO,
			@Param("pager") Pager<MemberBaseDTO> pager);

	public long queryMemberInfoBySellerIdCount(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public MemberBaseDTO queryMemberBaseInfo4order(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public MemberBaseDTO queryMemberLoginInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public MemberBaseDTO queryMemberBaseInfo(@Param("memberBaseDTO") MemberBaseDTO memberBaseDTO);

	public Integer getRealNameStatus(@Param("memberId") Long memberId);

	/**
	 * 根据公司名称判断会员信息是否存在
	 * 
	 * @param companyName
	 * @return
	 */
	public Long selectBooleanCompanyName(@Param("companyName") String companyName);

	/**
	 * @param memberCode
	 * @return
	 */
	public MemberBaseDTO queryMemberBelongInfo(String memberCode);

	/**
	 * @param artificialPersonMobile
	 * @return
	 */
	public long queryMemberInfoByCellPhone(String artificialPersonMobile);
}
