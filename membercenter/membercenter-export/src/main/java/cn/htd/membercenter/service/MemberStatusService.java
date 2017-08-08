package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.VerifyDetailInfo;
import cn.htd.membercenter.dto.MemberLicenceInfoDetailDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierDTO;
import cn.htd.membercenter.dto.MemberRemoveRelationshipDTO;
import cn.htd.membercenter.dto.MemberUncheckedDTO;
import cn.htd.membercenter.dto.MemberUncheckedDetailDTO;

/**
 * 
 * <p>
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * </p>
 * <p>
 * Title: MemberStatusService
 * </p>
 * 
 * @author root
 * @date 2016年11月26日
 *       <p>
 *       Description: 非会员转会员 、密码找回、手机号 查询列表/查询详细信息相关功能接口
 *       </p>
 */
public interface MemberStatusService {

	/**
	 * 非会员转会员 待审核列表接口
	 * 
	 * @param page
	 * @param memberUncheckedDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryNonMemberToMemberList(Pager page,
			MemberUncheckedDTO memberUncheckedDTO);

	/**
	 * 密码找回 待审核接口
	 * 
	 * @param page
	 * @param memberUncheckedDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryPasswordRecoveryVerifyList(Pager page,
			MemberUncheckedDTO memberUncheckedDTO);

	/**
	 * 手机号更改 待审核接口
	 * 
	 * @param page
	 * @param memberUncheckedDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberUncheckedDTO>> queryPhoneChangeVerifyList(Pager page,
			MemberUncheckedDTO memberUncheckedDTO);

	/**
	 * 非会员转会员待审核详细信息查询接口
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberUncheckedDetailDTO> queryNonMemberToMemberDetail(Long memberId);

	/**
	 * 密码找回待审核详细信息查询接口
	 * 
	 * @param memberId
	 * @param infoType
	 * @return
	 */
	public ExecuteResult<MemberLicenceInfoDetailDTO> queryPasswordRecoveryVerifyDetail(Long memberId, String infoType);

	/**
	 * 手机号更改待审核详细信息查询接口
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberLicenceInfoDetailDTO> queryPhoneChangeVerifyDetail(Long memberId);

	/**
	 * 根据会员id查询外部供应商信息修改履历
	 * 
	 * @param id
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<VerifyDetailInfo>> getVerifyById(Pager page, Long id);

	/**
	 * 查询会员解除归属关系待审核列表
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberRemoveRelationshipDTO>> queryRemoveRelationship(Pager page,
			MemberRemoveRelationshipDTO dto);

	/**
	 * 查询会员解除归属关系待审核详情
	 * 
	 * @param status
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberRemoveRelationshipDTO> queryRemoveRelationshipDetail(String status, Long memberId);

	/**
	 * 查询外部商家列表
	 * 
	 * @param memberOutsideSupplierDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberOutsideSupplierDTO>> queryOutsideSupplier(Pager page,
			MemberOutsideSupplierDTO memberOutsideSupplierDTO);

	/**
	 * 查询外部商家公司信息和合同财务信息
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MemberOutsideSupplierCompanyDTO> queryOutsideSupplierCompany(Long memberId);

	/**
	 * 查询外部商家公司信息和合同财务信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public ExecuteResult<MemberOutsideSupplierCompanyDTO> queryOutsideSupplierCompanyByCode(String memberCode);

}
