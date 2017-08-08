package cn.htd.tradecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderItemsDTO;

public interface TradeOrderItemsSellerDAO extends BaseDAO<TradeOrderItemsDTO> {

	// /**
	// *
	// * <p>
	// * Discription:[根据订单ID查询订单行信息]
	// * </p>
	// *
	// * @param orderNo
	// */
	// List<TradeOrderItemsDTO> queryItemsByOrderId(@Param("orderNo") String
	// orderNo);
	//
	// /**
	// * 根据子订单行id获取子订单信息
	// *
	// * @param orderItemNo
	// * @return
	// */
	// TradeOrderItemsQueryOutDTO queryItemByOrderItemId(@Param("orderItemNo")
	// String orderItemNo);
	//
	// /**
	// * 根据订单id查询订单行信息，带有分页的订单行信息
	// *
	// * @param orderNo
	// * @return
	// */
	// List<TradeOrderItemsQueryOutDTO>
	// queryItemsInfoByOrderId(@Param("orderNo") String orderNo);
	//
	// /**
	// * 根据id获取订单行信息(部分和连表数据)
	// *
	// * @param orderItemNo
	// * @return
	// */
	// TradeOrderItemsQueryOutDTO queryTradeItemsByOrderId(@Param("orderItemNo")
	// String orderItemNo);
	//
	// /**
	// * 批量更新议价信息
	// *
	// * @param list
	// */
	// void batchUpdateItemsChangePriceInfo(@Param("list")
	// List<TradeOrderItemsDTO> list);
	//
	// /**
	// * 根据子订单行号获取子订单信息 add by lh 2016/12/05
	// *
	// * @param orderItemNo
	// * @return
	// */
	// TradeOrderItemsDTO queryItemByOrderItemNo(@Param("orderItemNo") String
	// orderItemNo);
	//
	// /**
	// * 新增订单行
	// *
	// * @param tradeOrderItemsDTO
	// */
	// void insertOrderItems(@Param("orderItem") TradeOrderItemsDTO
	// tradeOrderItemsDTO);
	//
	// /**
	// * 删除订单行，（软删除，实质是修改状态）
	// *
	// * @param tradeOrderItemsDTO
	// */
	// void deleteOrderItems(@Param("orderItem") TradeOrderItemsDTO
	// tradeOrderItemsDTO);
	//
	// /**
	// * 更新订单行
	// *
	// * @param tradeOrderItemsDTO
	// */
	// void updateOrderItems(@Param("orderItem") TradeOrderItemsDTO
	// tradeOrderItemsDTO);
	//
	// /*
	// * 更新订单行状态
	// *
	// * @param record
	// *
	// * @return
	// */
	// int updateOrderItemStatus(TradeOrderItemsDTO record);

	/**
	 * 卖家中心查询订单行，因为需要展示的信息很少，所以新建一个方法 根据订单号查询订单行信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public List<TradeOrderItemsDTO> queryTradeOrderItemForSeller(@Param("orderNo") String orderNo);

	public Long updateStatus(@Param("orderNo") String orderNo,@Param("orderStatus")String orderItemStatus);


}
