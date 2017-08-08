package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberGroupDTO;

public interface MemberGroupService {

	/**
	 * 查询会员已分组列表信息
	 * 
	 * @param memberGroupDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberGroupDTO>> queryMemberGroupListInfo(MemberGroupDTO memberGroupDTO,
			Pager<MemberGroupDTO> pager);

	/**
	 * 查询会员未分组列表信息
	 * 
	 * @param memberGroupDTO
	 * @param pager
	 * @return
	 */
	public ExecuteResult<List<MemberGroupDTO>> queryMemberNoneGroupListInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 查询供应商会员分组信息下拉展示
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<List<MemberGroupDTO>> queryMemberGroupListInfo4select(MemberGroupDTO memberGroupDTO);

	/**
	 * 根据分组编码group_id查询会员分组详情信息
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<MemberGroupDTO> queryMemberGroupInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 删除会员分组信息
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<Boolean> deleteMemberGroupInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 新增会员分组信息
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<Boolean> insertMemberGroupInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 更新会员分组信息
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<Boolean> updateMemberGroupInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 查询会员分级分组列表信息
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<List<MemberGroupDTO>> queryMemberGradeAndGroupList(MemberGroupDTO memberGroupDTO);

	/**
	 * 根据商家编码查询有归属关系的会员编码集合
	 * 
	 * @param memberGroupDTO
	 * @return
	 */
	public ExecuteResult<List<String>> querySubMemberIdBySellerId(MemberGroupDTO memberGroupDTO);

	/**
	 * 根据商家编码，会员等级编码，分组编码查询会员编码集合
	 * 
	 * @param sellerId
	 * @param gradeList
	 * @param groupList
	 * @return
	 */
	public ExecuteResult<List<String>> querySubMemberIdByGradeInfoAndGroupInfo(MemberGroupDTO memberGroupDTO);

	/**
	 * 根据买家和买家查询分组信息
	 * 
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public ExecuteResult<MemberGroupDTO> queryGroupInfoBySellerBuyerId(Long buyerId, Long sellerId);
}
