package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;

import cn.htd.goodscenter.dto.InventoryModifyDTO;
import cn.htd.goodscenter.dto.TradeInventoryDTO;
import cn.htd.goodscenter.dto.TradeInventoryInDTO;
import cn.htd.goodscenter.dto.TradeInventoryOutDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [库存]
 * </p>
 */
public interface TradeInventoryExportService {

	/**
	 * <p>
	 * Discription:[根据skuId查询库存]
	 * </p>
	 */
	public ExecuteResult<TradeInventoryDTO> queryTradeInventoryBySkuId(Long skuId);

	/**
	 * <p>
	 * Discription:[商品库存列表查询]
	 * </p>
	 */
	public ExecuteResult<DataGrid<TradeInventoryOutDTO>> queryTradeInventoryList(TradeInventoryInDTO dto, Pager page);

	/**
	 * <p>
	 * Discription:[批量修改库存量]
	 * </p>
	 */
	public ExecuteResult<String> modifyInventoryByIds(List<InventoryModifyDTO> dto);
	
	public List<TradeInventoryDTO> querySkuInventoryBySyncTime(Date syncTime,@Param("page") Pager pager);

	/**
	 * <p>
	 * Discription:[批量保存商家商品库存信息]
	 * </p>
	 */
	ExecuteResult<String> saveSkuInventoryList(List<VenusItemSkuPublishInDTO> dtos);
}
