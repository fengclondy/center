package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.dto.TradeInventoryDTO;
import cn.htd.goodscenter.dto.TradeInventoryInDTO;
import cn.htd.goodscenter.dto.TradeInventoryOutDTO;

public interface TradeInventoryDAO extends BaseDAO<TradeInventoryInDTO> {

	/**
	 * <p>
	 * Discription:[根据skuId查询库存]
	 * </p>
	 */
	public TradeInventoryDTO queryBySkuId(Long skuId);

	/**
	 * <p>
	 * Discription:[根据条件查询库存列表]
	 * </p>
	 */
	public List<TradeInventoryOutDTO> queryTradeInventoryList(@Param("entity") TradeInventoryInDTO tradeInventoryInDTO, @Param("page") Pager page);

	/**
	 * <p>
	 * Discription:[根据skuId查询商品的名称、状态]
	 * </p>
	 */
	public TradeInventoryOutDTO queryItemBySkuId(Long skuId);

	/**
	 * <p>
	 * Discription:[根据skuIds批量修改库存量]
	 * </p>
	 */
	public Integer modifyInventoryBySkuIds(@Param("skuid") Long skuId, @Param("inventory") Integer inventory);
	
	public List<TradeInventoryDTO> querySkuInventoryBySyncTime(@Param("syncTime") Date syncTime,@Param("page") Pager pager);
}
