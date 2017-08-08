package cn.htd.basecenter.service;

import java.util.List;

import cn.htd.basecenter.dto.BasePlacardDTO;
import cn.htd.basecenter.dto.PlacardCondition;
import cn.htd.basecenter.dto.PlacardInfo;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [公告信息]
 * </p>
 */
public interface BasePlacardService {

	/**
	 * 会员中心查询未读店铺公告数量(前端商城会员中心用)
	 */
	public ExecuteResult<Long> getUnReadSellerPlacardCount(Long memberId);

	/**
	 * 将未读店铺公告全部更新成已读(前端商城会员中心用)
	 */
	public ExecuteResult<String> updateAllUnReadSellerPlacardReadStatus(Long memberId, Long modifyId,
			String modifyName);

	/**
	 * 根据公告ID将未读店铺公告更新成已读(前端商城会员中心用)
	 */
	public ExecuteResult<String> updateUnReadSellerPlacard2HasRead(Long id, Long memberId, Long userId,
			String userName);

	/**
	 * 会员中心查询公告列表(前端商城会员中心／卖家中心用)
	 */
	public ExecuteResult<DataGrid<PlacardInfo>> getPlacardList(PlacardCondition placardCondition,
			Pager<PlacardCondition> pager);

	/**
	 * 查询公告详情(前端商城会员中心／卖家中心用)
	 */
	public ExecuteResult<PlacardInfo> getPlacardDetailById(Long id, Long memberId, Long userId, String userName);

	/**
	 * 根据公告ID删除公告信息(前端商城会员中心用)
	 */
	public ExecuteResult<String> deleteSellerPlacard4Member(Long placardId, Long memberId, Long userId,
			String userName);

	/**
	 * 查询平台公告列表
	 */
	public ExecuteResult<DataGrid<BasePlacardDTO>> queryPlatformPlacardList(Pager<BasePlacardDTO> pager);

	/**
	 * 查询商家消息列表
	 */
	public ExecuteResult<DataGrid<BasePlacardDTO>> querySellerPlacardList(Long sendSellerId,
			Pager<BasePlacardDTO> pager);

	/**
	 * 查询公告详情
	 */
	public ExecuteResult<BasePlacardDTO> queryBasePlacardById(Long id);

	/**
	 * 平台新建公告信息
	 */
	public ExecuteResult<BasePlacardDTO> addPlatformBasePlacard(BasePlacardDTO basePlacardDTO);

	/**
	 * 卖家新建公告信息
	 */
	public ExecuteResult<BasePlacardDTO> addSellerPlacard(BasePlacardDTO basePlacardDTO);

	/**
	 * 平台更新公告信息
	 */
	public ExecuteResult<BasePlacardDTO> updatePlatformBasePlacard(BasePlacardDTO basePlacardDTO);

	/**
	 * 卖家更新公告信息
	 */
	public ExecuteResult<BasePlacardDTO> updateSellerBasePlacard(BasePlacardDTO basePlacardDTO);

	/**
	 * 批量删除公告信息
	 */
	public ExecuteResult<BasePlacardDTO> deleteBasePlacard(List<Long> placardIdList, Long modifyId, String modifyName);

}
