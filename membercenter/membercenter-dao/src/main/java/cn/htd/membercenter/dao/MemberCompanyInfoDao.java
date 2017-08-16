package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoDTO;
import cn.htd.membercenter.dto.MemberCompanyInfoVO;

/**
 * Created by thinkpad on 2016/11/17.
 */
public interface MemberCompanyInfoDao {
	List<MemberCompanyInfoVO> searchMemberCompanyInfoList(MemberCompanyInfoVO memberCompanyInfoVO);

	void updateMemberCompanyInfoList(MemberCompanyInfoVO memberCompanyInfoVO);

	// 根据条件查询有效会员信息
	public List<MemberCompanyInfoDTO> searchMemberCompanyInfo(@Param("dto") MemberCompanyInfoDTO dto);

	// 校验公司名称和法人手机号唯一性
	public List<MemberCompanyInfoDTO> searchVoidMemberCompanyInfo(@Param("dto") MemberCompanyInfoDTO dto);

	// 检查公司名称唯一性查出归属供应商id
	public Long selectBelongSellerId(@Param("companyName") String companyName);

	/**
	 * @param accountNo
	 * @return
	 */
	public MemberBaseInfoDTO selectMemberInfoByAccountNo(@Param("accountNo") String accountNo);

	/**
	 * @param accountNo
	 * @return
	 */
	public MemberBaseInfoDTO selectSellerInfoByAccountNo(@Param("accountNo") String accountNo);
	
	public String queryCompanyCodeBySellerId(Long sellerId);
	
	public int updateCompanyTime(@Param("loginId") String loginId);
}
