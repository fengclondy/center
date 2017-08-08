package cn.htd.membercenter.service;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBaseDTO;

public interface MemberBaseService {

	/**
	 * 根据会员编码查询会员信息
	 * 
	 * @param RoleId
	 * @param name
	 * @return
	 */
	public ExecuteResult<MemberBaseDTO> queryMemberBaseInfoById(MemberBaseDTO memberBaseDTO);

	/**
	 * 根据是否商城登陆，担保证明标志，经营执照标志判断会员类型
	 * 
	 * @param canMallLogin
	 * @param hasGuaranteeLicense
	 * @param hasBusinessLicense
	 * @return
	 */
	public String judgeMemberType(String canMallLogin, String hasGuaranteeLicense, String hasBusinessLicense);

	/**
	 * 根据会员编码，公司名称，法人姓名模糊查询对应的会员列表信息
	 * 
	 * @param memberBaseDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBaseDTO>> queryMemberInfoBySellerId(MemberBaseDTO memberBaseDTO,
			Pager<MemberBaseDTO> pager);

	/**
	 * 根据会员编码查询会员订单相关信息
	 * 
	 * @param RoleId
	 * @param name
	 * @return
	 */
	@Deprecated
	public ExecuteResult<MemberBaseDTO> queryMemberBaseInfo4order(MemberBaseDTO memberBaseDTO);

	/**
	 * 根据区域编码查询地址信息
	 * 
	 * @param code
	 * @return
	 */
	public String getAddressBaseByCode(String code);

	/**
	 * 查询用户登陆信息
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<MemberBaseDTO> queryMemberLoginInfo(MemberBaseDTO memberBaseDTO);

	/**
	 * 根据memberId查询企业实名认证状态
	 */
	public ExecuteResult<Integer> selectIsRealNameAuthenticated(Long memberId);

	/**
	 * 查询库中是否有公司名称
	 * 
	 * @param companyName
	 * @return
	 */
	public ExecuteResult<Long> selectBooleanCompanyName(String companyName);

	/**
	 * 查询当前会员的归属公司信息
	 * 
	 * @return
	 */
	public ExecuteResult<MemberBaseDTO> queryMemberBelongInfo(String memberCode);
	
	/**
	 * 查询当前会员的归属公司信息
	 * 
	 * @return
	 */
	public ExecuteResult<MemberBaseDTO> queryMemberBelongInfoNew(String memberCode);

	/**
	 * 根据会员手机查询会员信息
	 * 
	 * @param artificialPersonMobile
	 * @return
	 */

	public ExecuteResult<Long> queryMemberInfoByCellPhone(
			@Param("artificialPersonMobile") String artificialPersonMobile);

}
