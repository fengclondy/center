package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBaseDTO;
import cn.htd.membercenter.dto.MemberBusinessRelationDTO;

public interface MemberBusinessRelationService {

	/**
	 * 根据会员编码查询下属商家的经营关系
	 * 
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBusinessRelationDTO>> queryMemberBusinessRelationListInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO,
			Pager<MemberBusinessRelationDTO> pager);

	/**
	 * 根据会员编码查询下属商家的未建立经营关系
	 * 
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBusinessRelationDTO>> queryMemberNoneBusinessRelationListInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO,
			Pager<MemberBusinessRelationDTO> pager);

	/**
	 * 查询待审核的经营关系信息
	 * 
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<MemberBaseDTO> queryMemberBusinessRelationPendingAudit(
			MemberBusinessRelationDTO memberBusinessRelationDTO);

	/**
	 * 根据商家编码和会员编码删除商家会员的经营关系
	 * 
	 * @param memberInvoiceDTO
	 * @return
	 */
	public ExecuteResult<Boolean> deleteMemberBusinessRelationInfo(
			List<MemberBusinessRelationDTO> mbrDTO);

	/**
	 * 新增商家会员的经营关系
	 * 
	 * @param memberInvoiceDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMemberBusinessRelationInfo(
			List<MemberBusinessRelationDTO> mbrDTO);

	/**
	 * 查询会员供应商包厢关系、
	 * 
	 * 
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<Boolean> queryMemberBoxRelationInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO);

	/**
	 * 创建会员供应商包厢关系
	 * 
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMeberBoxRelationInfo(
			MemberBusinessRelationDTO memberBusinessRelationDTO);

	/**
	 * 查询会员卖家和买家经营关系列表
	 */
	public ExecuteResult<String> getQueryMemberBusinessRelationCode(
			String messageId, Long buyerId, Long sellerId, Long categoryId,
			Long brandId);
	
	/**
	 * 根据买家id查询经营关系列表
	 */
	public ExecuteResult<List<MemberBusinessRelationDTO>> selectMemberBussinsessRelationShip(Long memberId);
	
	/**
	 * 查询商家所以的关联关系品牌品类
	 * @param SellerId
	 * @return
	 */
	public ExecuteResult<List<MemberBusinessRelationDTO>> queryCategoryIdAndBrandIdBySellerId(MemberBusinessRelationDTO memberBusinessRelationDTO);
	
	/**
	 * 根据品牌品类查询经营关系
	 * @param memberBusinessRelationDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBusinessRelationDTO>> queryMemberBussinessByCategoryId(MemberBusinessRelationDTO memberBusinessRelationDTO,
			Pager<MemberBusinessRelationDTO> pager);
}
