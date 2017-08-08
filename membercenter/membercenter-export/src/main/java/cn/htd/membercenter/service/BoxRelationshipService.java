package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.domain.BoxRelationship;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;
import cn.htd.membercenter.dto.BelongRelationshipDTO;
import cn.htd.membercenter.dto.BoxRelationImportDTO;
import cn.htd.membercenter.dto.MemberShipDTO;

/**
 * 包厢关系及经营关系
 * 
 * @author thinkpad
 *
 */
public interface BoxRelationshipService {

	/**
	 * 查询会员包厢关系列表
	 * 
	 * @param page
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<DataGrid<BelongRelationshipDTO>> selectBoxRelationList(Pager page, String companyName,
			String contactMobile, String boxCompanyName);

	/**
	 * 查询会员包厢关系详细信息
	 * 
	 * @param boxId
	 * @return
	 */
	public ExecuteResult<BelongRelationshipDTO> selectBoxRelationInfo(Long boxId);

	/**
	 * 根据会员ID、商家ID、品类品牌Id查询当前归属客户经理
	 * 
	 * @param memberId
	 * @param sellerId
	 * @param categoryId
	 * @param brandId
	 * @return
	 */
	public ExecuteResult<ApplyBusiRelationDTO> selectBusiRelation(Long memberId, Long sellerId, Long categoryId,
			Long brandId);

	/**
	 * 根据会员名称或者公司名称精确查询该会员或者公司ID
	 * 
	 * @param companyName
	 * @param buyerSellerType
	 *            会员/商家类型 1：会员，2：商家'
	 * @return
	 */
	public ExecuteResult<Long> selectCompanyID(String companyName, String buyerSellerType);

	/**
	 * 导入会员包厢关系
	 * 
	 * @param businessRelatVerifyDto
	 *            ：memberId、sellerId、modifyId、modifyName
	 * @return
	 */
	public ExecuteResult<BoxRelationImportDTO> importBoxRelation(List<ApplyBusiRelationDTO> businessRelatVerifyDtoList);

	/**
	 * 根据会员ID和商家ID查询是否有包厢关系
	 * 
	 * @param sellerId
	 *            卖家id
	 * @param memberId
	 *            买家ID
	 * @return
	 */
	public ExecuteResult<Integer> selectMemberIdSellerIdLong(Long sellerId, Long memberId);

	/**
	 * 根据会员ID和商家ID查询是否有经营关系
	 * 
	 * @param memberId
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<Integer> selectBusiRelationListLong(Long memberId, Long sellerId);

	/**
	 * 查询所有跟会员有经营关系的供应商ID列表
	 * 
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<List<ApplyBusiRelationDTO>> selectBusiRelationBuyerIdList(Long memberId);

	/**
	 * 查询会员包厢关系列表ByMemberId
	 * 
	 * @param page
	 * @param companyName
	 * @param contactMobile
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<DataGrid<BoxRelationship>> selectBoxRelationListByMemberId(Long memberId);


	/**
	 * 通过会员memberCode查询供应商信息
	 */
	public ExecuteResult<List<MemberShipDTO>> selectBoxRelationship(String memberCode);
}
