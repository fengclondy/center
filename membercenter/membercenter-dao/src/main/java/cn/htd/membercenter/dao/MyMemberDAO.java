package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyMemberSearchDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;

public interface MyMemberDAO {

	/**
	 * 
	 * @param pager
	 * @param stringName
	 * @param canMallLogin
	 * @param hasGuaranteeLicense
	 * @param hasBusinessLicense
	 *            查询我的会员/担保会员列表
	 * @return
	 */
	public List<MyMemberDTO> selectByTypeList(@Param("page") Pager page, @Param("sellerId") Long sellerId,
			@Param("memberSearch") MyMemberSearchDTO memberSearch, @Param("canMallLogin") Integer canMallLogin,
			@Param("hasGuaranteeLicense") Integer hasGuaranteeLicense,
			@Param("hasBusinessLicense") Integer hasBusinessLicense);

	/**
	 * 查询我的会员/担保会员列表总数
	 * 
	 * @param sellerId
	 * @param memberSearch
	 * @param canMallLogin
	 * @param hasGuaranteeLicense
	 * @param hasBusinessLicense
	 * @return
	 */
	public Long selectByTypeListCount(@Param("sellerId") Long sellerId,
			@Param("memberSearch") MyMemberSearchDTO memberSearch, @Param("canMallLogin") Integer canMallLogin,
			@Param("hasGuaranteeLicense") Integer hasGuaranteeLicense,
			@Param("hasBusinessLicense") Integer hasBusinessLicense);

	/**
	 * 查询非会员列表
	 * 
	 * @param pager
	 * @param vendorId
	 *            归属商家ID
	 * @param memberSearch
	 *            高级查询
	 * @param canMallLogin
	 * @return
	 */
	public List<MyNoMemberDTO> selectNoMemberList(@Param("page") Pager page, @Param("sellerId") Long sellerId,
			@Param("vendorId") Long vendorId, @Param("memberSearch") MyMemberSearchDTO memberSearch,
			@Param("canMallLogin") Integer canMallLogin);

	/**
	 * 查询我的会员/担保会员详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MyMemberDTO selectMyMemberInfo(@Param("memberId") Long memberId);

	/**
	 * 查询非会员详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MyNoMemberDTO selectNoMemberInfo(@Param("memberId") Long memberId);

	/**
	 * 注册非会员保存会员/商家表信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public void saveNoMemberCompanyInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 注册非会员保存会员发票表信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public void saveNoMemberInvoiceInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 注册非会员保存会员归属关系表信息中会员属性
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public void saveNoMemberbelongInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员注册状态保存为待审核
	 */
	public void saveNoMemberStatusInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员注册时，设置会员基本信息表里的登录商城为否且获得会员自增ID
	 */
	public Long getNoMemberBaseID(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员修改，变更会员/商家信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public Boolean updateMemberCompanyInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员修改，变更发票信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public Boolean updateMemberInvoiceInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员修改，变更会员基本信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public Boolean updateMemberBaseInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员修改，变更会员归属信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public Boolean updateBelongRelationship(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员修改，变更会员开户行信息表信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public Boolean updatMememberBankInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员转会员，新增会员证照信息表信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public void insertMememberLicenceInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * 非会员转会员，新增会员证照信息表信息
	 * 
	 * @param myNoMemberDto
	 * @return
	 */
	public void deleteMemberLicenceInfo(MyNoMemberDTO myNoMemberDto);

	/**
	 * @param taxManId
	 * @param long1
	 * @return
	 */
	public List<MyNoMemberDTO> getNoMemberTaxManId(@Param("taxManId") String taxManId,
			@Param("memberId") Long memberId);

	/**
	 * @param memberName
	 * @param long1
	 * @return
	 */
	public List<MyNoMemberDTO> getNoMemberName(@Param("memberName") String memberName,
			@Param("memberId") Long memberId);

	/**
	 * @param myNoMemberDto
	 */
	public void updateBelongRelationshipbuyerFeature(MyNoMemberDTO myNoMemberDto);
}
