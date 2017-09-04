package cn.htd.promotion.cpc.api;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.SeckillInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionTimelimitedShowDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

/**
 * 秒杀活动服务
 */
public interface PromotionTimelimitedInfoAPI {

	/**
	 * 根据商品编码取得秒杀信息
	 * 
	 * @param messageId
	 * @param skuCode
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoResDTO> getSkuPromotionTimelimitedInfo(String messageId, String skuCode);

	/**
	 * 汇掌柜APP - 根据会员编码和促销活动id查询总部秒杀信息
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @return
	 */
	public ExecuteResult<TimelimitedInfoResDTO> getPromotionTimelimitedByBuyerCodeAndPromotionId(String messageId,
			String buyerCode, String promotionId);

	/**
	 * 汇掌柜APP - 根据会员编码查询是否有总部秒杀信息
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @return
	 */
	public boolean getPromotionTimelimitedByBuyerCode(String messageId, String buyerCode);

	/**
	 * 汇掌柜APP - 查询秒杀活动列表
	 * 
	 * 粉丝 未登录 默认取汇通达O2O旗舰店的秒杀商品；已登录则取归属会员店的秒杀商品 (根据buyerCode)
	 * 
	 * @param messageId
	 * @param buyerCode
	 *            会员编码
	 * @param page
	 * @return
	 */
	public ExecuteResult<DataGrid<PromotionTimelimitedShowDTO>> getPromotionTimelimitedList(String messageId,
			String buyerCode, Pager<TimelimitedInfoResDTO> page);

	/**
	 * 汇掌柜APP - 查询秒杀活动详情
	 * 
	 * @param messageId
	 * @param promotionId
	 * @param buyerCode
	 * @param buyerGrade
	 * @return
	 */
	public ExecuteResult<PromotionTimelimitedShowDTO> getPromotionTimelimitedInfoDetail(String messageId,
			String promotionId, String buyerCode, String buyerGrade);

	/**
	 * 汇掌柜APP - 根据会员编码查询是否有总部秒杀是否有效
	 * 
	 * @param messageId
	 * @param buyerCode
	 * @param promotionId
	 * @return
	 */
	public ExecuteResult<Boolean> checkTimelimitedIsAvailableByBuyerCode(String messageId, String buyerCode,
			String promotionId);

	/**
	 * 校验是否有参加秒杀活动的资格
	 * 
	 * @param messageId
	 * @param seckillInfoReqDTO
	 * @return
	 */
	public ExecuteResult<String> checkSeckillQualifications(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);

	/**
	 * 锁定秒杀库存
	 * 
	 * @param messageId
	 * @param seckillInfoReqDTO
	 * @return
	 */
	public ExecuteResult<String> reserveStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);

	/**
	 * 释放秒杀库存
	 * 
	 * @param messageId
	 * @param seckillInfoReqDTO
	 * @return
	 */
	public ExecuteResult<String> releaseStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);

	/**
	 * 扣减秒杀库存
	 * 
	 * @param messageId
	 * @param seckillInfoReqDTO
	 * @return
	 */
	public ExecuteResult<String> reduceStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);

	/**
	 * 回滚秒杀库存
	 * 
	 * @param messageId
	 * @param seckillInfoReqDTO
	 * @return
	 */
	public ExecuteResult<String> rollbackStock(String messageId, SeckillInfoReqDTO seckillInfoReqDTO);
}
