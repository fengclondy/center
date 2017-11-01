package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.SellerBelongRelationDTO;

/**
 * 归属关系
 * 
 * @author thinkpad
 *
 */
public interface BelongRelationshipService {

	/**
	 * 查询会员归属关系列表
	 * 
	 * @param page
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<DataGrid<BelongRelationshipDTO>> selectBelongRelationList(
			Pager page, String companyName, String contactMobile,
			String belongSellerName);

	/**
	 * 查询会员归属关系详细信息
	 * 
	 * @param memberId
	 * @return
	 */

	public ExecuteResult<BelongRelationshipDTO> selectBelongRelationInfo(
			Long memberId, Long sellerId);

	/**
	 * 保存会员归属关系
	 * 
	 * @param belongRelationshipDto
	 * @return
	 */
	public ExecuteResult<String> updateBelongRelationInfo(
			BelongRelationshipDTO belongRelationshipDto);
	/**
	 * 根据买家ID和卖家ID查询是否有归属关系
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<Boolean> selectIsBelongRelation(Long buyerId,Long sellerId);
	
	/**
	 * 根据会员编码查询会员归属关系信息
	 * 
	 * @param memberCodeList
	 * @return
	 */

	public ExecuteResult<List<SellerBelongRelationDTO>> queryBelongRelationListByMemberCodeList(
			List<String> memberCodeList);
}
