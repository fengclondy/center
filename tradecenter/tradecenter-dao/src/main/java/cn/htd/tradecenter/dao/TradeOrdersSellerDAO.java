package cn.htd.tradecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderQueryInForSellerDTO;
import cn.htd.tradecenter.dto.TradeOrderQueryOutForSellerDTO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;

/**
 * Created by lh on 2016/11/18.
 */
public interface TradeOrdersSellerDAO extends BaseDAO<TradeOrdersDTO> {

	// /**
	// * 查询所有订单
	// * @param inDTO
	// * @param pager
	// * @return
	// */
	// List<TradeOrdersQueryOutDTO> queryTradeOrders(@Param("entity")
	// TradeOrdersQueryInDTO inDTO, @Param("page") Pager<TradeOrdersQueryInDTO>
	// pager);
	//
	// /**
	// * 查询订单数量
	// * @param inDTO
	// * @return
	// */
	// Long queryTradeOrdersCount(@Param("entity") TradeOrdersQueryInDTO inDTO);
	//
	// /**
	// * 查询订单数量(根据不同的状态或者状态数组来查询一个sellerId下的不同状态或者时间的数量order_items)12/7
	// * @param inDTO
	// * @return
	// */
	// Long queryTradeOrdersCountByStatus(@Param("entity") TradeOrdersQueryInDTO
	// inDTO);
	//
	// /**
	// * 根据订单号查询订单
	// * @param orderNo
	// * @return
	// */
	// TradeOrdersDTO queryTradeOrderById(@Param("orderNo") String orderNo);
	//
	// /**
	// * 根据订单Id查询订单基本信息
	// * @param orderNo
	// * @return
	// */
	// TradeOrdersDTO getTradeOrderInfoById(@Param("orderNo")String orderNo);
	//
	// /**
	// * 根据输入框输入的条件进行条件查询
	// * @param inDTO
	// * @param pager
	// * @return
	// */
	// List<TradeOrdersQueryOutDTO> searchTradeOrder(@Param("entity")
	// TradeOrdersQueryInDTO inDTO, @Param("page") Pager<TradeOrdersQueryInDTO>
	// pager);
	//
	//
	// /**
	// * VMS商品+订单条件查询
	// * @param inDTO
	// * @param pager
	// * @return
	// */
	// List<TradeOrdersQueryOutDTO>
	// outerSearchTradeOrder(@Param("entity")TradeOrdersQueryInDTO
	// inDTO,@Param("page") Pager<TradeOrdersQueryInDTO> pager);
	//
	//
	// /**
	// * 审核通过订单
	// * @param tradeOrdersNewDTO
	// */
	// Integer updateOrderApprove(TradeOrdersDTO tradeOrdersNewDTO);
	//
	// /**
	// * 议价更新订单信息
	// * @param tradeOrdersNewDTO
	// * @return
	// */
	// Integer updateOrderInfoForChangePrice(TradeOrdersDTO tradeOrdersNewDTO);

	/**
	 * 卖家中心订单以及订单行信息查询以及导出
	 * 
	 * @param tradeOrderQueryInForSellerDTO
	 * @param pager
	 * @return
	 */
	List<TradeOrderQueryOutForSellerDTO> queryOrderForSeller(
			@Param("entity") TradeOrderQueryInForSellerDTO tradeOrderQueryInForSellerDTO,
			@Param("page") Pager<TradeOrderQueryInForSellerDTO> pager);

	/**
	 * 卖家中心订单以及订单行信息查询以及导出数量
	 * 
	 * @param tradeOrderQueryInForSellerDTO
	 * @return
	 */
	Long queryOrderCountsForSeller(@Param("entity") TradeOrderQueryInForSellerDTO tradeOrderQueryInForSellerDTO);

	Long queryStatusCountsForSeller(@Param("entity") TradeOrderQueryInForSellerDTO tradeOrderQueryInForSellerDTO);

	Long updateOrderStatus(@Param("orderNo") String orderNo, @Param("orderStatus") String orderStauts);
}
