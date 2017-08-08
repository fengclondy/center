package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.MemberContractInfo;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberStatusVO;
import cn.htd.membercenter.dto.MemberUncheckedDTO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;

/**
 * Created by thinkpad on 2016/11/15.
 */
public interface MemberStatusDao {

	public MemberUncheckedDetailDTO searchUncheckMemberStatusDetail(MemberStatusVO memberStatusVO);

	public List<MemberUncheckedDTO> searchUncheckMemberStatus(MemberStatusVO memberStatusVO);

	public int searchUncheckMemberStatusCount(MemberStatusVO memberStatusVO);

	public void updateCheckMember(MemberStatusVO memberStatusVO);

	/**
	 * 查询非会员转会员待审列表数量
	 * 
	 * @param dto
	 * @return
	 */
	public int queryNonMemberToMemberListCount(@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 查询非会员转会员待审列表
	 * 
	 * @param dto
	 * @return
	 */
	public List<MemberUncheckedDTO> queryNonMemberToMemberList(@Param("page") Pager page,
			@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 查询密码找回待审列表数量
	 * 
	 * @param dto
	 * @return
	 */
	public int queryPasswordRecoveryVerifyListCount(@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 查询密码找回待审列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	public List<MemberUncheckedDTO> queryPasswordRecoveryVerifyList(@Param("page") Pager page,
			@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 查询手机号更改待审核列表数量
	 * 
	 * @param dto
	 * @return
	 */
	public int queryPhoneChangeVerifyListCount(@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 查询手机号更改待审列表
	 * 
	 * @param page
	 * @param dto
	 * @return
	 */
	public List<MemberUncheckedDTO> queryPhoneChangeVerifyList(@Param("page") Pager page,
			@Param("dto") MemberUncheckedDTO dto);

	/**
	 * 根据会员ID查询非会员转会员详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberUncheckedDetailDTO queryNonMemberToMemberDetail(@Param("member_id") Long memberId);

	/**
	 * 根据会员ID,信息类型 查询非会员转会员详细信息
	 * 
	 * @param memberId
	 * @param infoType
	 * @return
	 */
	public MemberLicenceInfoDetailDTO queryPasswordRecoveryVerifyDetail(@Param("member_id") Long memberId,
			@Param("info_type") String infoType);

	/**
	 * 根据会员ID查询手机号更改审核详细信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberLicenceInfoDetailDTO queryPhoneChangeVerifyDetail(@Param("member_id") Long memberId);

	/**
	 * 查询会员解除归属关系待审核列表
	 * 
	 * @param dto
	 * @return
	 */
	public List<MemberRemoveRelationshipDTO> queryRemoveRelationship(@Param("page") Pager page,
			@Param("dto") MemberRemoveRelationshipDTO dto);

	/**
	 * 查询会员解除归属关系待审核列表
	 * 
	 * @param dto
	 * @return
	 */
	public Long queryRemoveRelationshipCount(@Param("dto") MemberRemoveRelationshipDTO dto);

	/**
	 * 查询会员解除归属关系待审核详情
	 * 
	 * @param dto
	 * @return
	 */
	public MemberRemoveRelationshipDTO queryRemoveRelationshipDetail(@Param("status") String status,
			@Param("memberId") Long memberId);

	/**
	 * 查询外部商家列表
	 * 
	 * @param dto
	 * @return
	 */
	public List<MemberOutsideSupplierDTO> queryOutsideSupplier(@Param("page") Pager page,
			@Param("dto") MemberOutsideSupplierDTO dto);

	public long queryOutsideSupplierCount(@Param("dto") MemberOutsideSupplierDTO dto);

	/**
	 * 查询外部商家公司信息
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberOutsideSupplierCompanyDTO queryOutsideSupplierCompany(Long memberId);

	/**
	 * 根据id获取当前归属公司名称
	 * 
	 * @param memberId
	 * @return
	 */
	public String getBelongCompanyName(Long memberId);

	/**
	 * 根据id获取原始归属公司名称
	 * 
	 * @param memberId
	 * @return
	 */
	public String getOriginalBelongCompanyName(Long memberId);

	/**
	 * 根据会员Id获取合同列表
	 * 
	 * @param memberId
	 * @return
	 */
	public List<MemberContractInfo> queryContractList(Long memberId);

	/**
	 * 查询履历
	 * 
	 * @param id
	 * @param infoType
	 * @param pager
	 * @return
	 */
	public List<VerifyDetailInfo> getVerifyById(@Param("id") Long id, @Param("infoType") String infoType,
			@Param("recordType") String recordType, @Param("pager") Pager pager);
	/**
	 * 查询履历总数
	 * @param id
	 * @param infoType
	 * @param recordType
	 * @return
	 */
	public Long getVerifyByIdCount(@Param("id") Long id, @Param("infoType") String infoType,
			@Param("recordType") String recordType);
}
