package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.MemberContractInfo;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;
import cn.htd.membercenter.dto.QueryRegistProcessDTO;

/**
 * 经营关系
 * 
 * @author thinkpad
 *
 */
public interface ApplyRelationshipService {

	/**
	 * 申请解除会员归属关系
	 * 
	 * @param memberId
	 * @return
	 */

	public ExecuteResult<String> applyNoBelongRelationship(BelongRelationshipDTO belongRelationshipDto);

	/**
	 * 非会员转会员申请
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<String> applynoMemberToMember(MyNoMemberDTO myNoMemberDto);

	/**
	 * 查询会员申请经营关系待审核列表
	 * 
	 * @return
	 */
	public ExecuteResult<DataGrid<ApplyBusiRelationDTO>> selectBusinessRelationship(Long curBelongSellerId,
			String companyName);
	
	/**
	 * 分页查询会员申请经营关系待审核列表
	 * 
	 * @return
	 */
	public ExecuteResult<DataGrid<ApplyBusiRelationDTO>> selectBusinessRelationship(Long curBelongSellerId,
			String companyName  , Pager pager);

	/**
	 * 查询注册进度
	 * 
	 * @param memberId
	 * @param companyName
	 * @return
	 */
	public ExecuteResult<QueryRegistProcessDTO> queryRegistProcess(Long memberId, String companyName,
			Long curBelongSellerId);

	/**
	 * 经营关系审核：2通过，3驳回
	 * 
	 * @param memberId
	 * @param companyName
	 * @return
	 */
	public ExecuteResult<String> busiRelationVerify(ApplyBusiRelationDTO businessRelationVerify);

	/**
	 * 新增商家入驻
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public ExecuteResult<String> insertOutSellerInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 修改商家入驻
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public ExecuteResult<String> updateOutSellerInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 商家入驻-》修改合同信息
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public ExecuteResult<String> updateMemberContractInfo(MemberContractInfo memberContractInfo);

	/**
	 * 商家入驻-》新增合同信息
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public ExecuteResult<String> insertMemberContractInfo(MemberOutsideSupplierCompanyDTO outCompanyDto);

	/**
	 * 商家入驻-》检查商家注册信息
	 * 
	 * @param outCompanyDto
	 * @return
	 */
	public ExecuteResult<Boolean> checkOutCompanyRegister(MemberOutsideSupplierCompanyDTO outCompanyDto);
	/**
	 * 根据供应商查询会员注册进度列表
	 *  @param dto
	 * @param pager
	 * @param cusCompanyId  @return
	 */
	ExecuteResult<DataGrid<QueryRegistProcessDTO>> queryRegistProcessList(QueryRegistProcessDTO dto, Pager pager, Long cusCompanyId);

	/**
	 * 会员注册进度详情
	 * @param memberId
	 * @param cusCompanyId
	 * @return
	 */
	ExecuteResult<QueryRegistProcessDTO> queryRegistProcessDetail(Long memberId, Long cusCompanyId);

}
