package cn.htd.tradecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.VenusTradeOrdersQueryInDTO;

public interface TradeOrdersDAO extends BaseDAO<TradeOrdersDTO> {
	/**
	 * 运营系统根据订单号查询订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	public TradeOrdersDTO queryTradeOrderByOrderNo(String orderNo);

	/**
	 * 新增订单数据
	 * 
	 * @param venusOrderDTO
	 */
	public void addTradeOrderInfo(TradeOrdersDTO venusOrderDTO);

	/**
	 * 根据订单编号更新订单状态信息
	 * 
	 * @param orderDTO
	 * @return
	 */
	public int updateTradeOrdersStatusInfo(TradeOrdersDTO orderDTO);

	/**
	 * 根据订单编号更新订单异常状态信息
	 * 
	 * @param orderDTO
	 * @return
	 */
	public int updateTradeOrdersErpDownStatusInfo(TradeOrdersDTO orderDTO);

	/**
	 * 根据条件查询VMS处理订单列表
	 * 
	 * @param conditionDTO
	 * @return
	 */
	public Long queryTradeOrderCountByCondition(@Param("entity") VenusTradeOrdersQueryInDTO conditionDTO);

	/**
	 * 根据条件查询VMS处理订单列表
	 * 
	 * @param conditionDTO
	 * @param page
	 * @return
	 */
	public List<TradeOrdersDTO> queryTradeOrderListByCondition(@Param("entity") VenusTradeOrdersQueryInDTO conditionDTO,
			@Param("page") Pager<VenusTradeOrdersQueryInDTO> pager);

	/**
	 * 订单议价时保存订单议价信息
	 * 
	 * @param orderDTO
	 * @return
	 */
	public int updateTradeOrdersNegotiateInfo(TradeOrdersDTO orderDTO);

	/**
	 * 订单议价
	 * 
	 * @param ordersDTO
	 * @return
	 */
	public int changePrice(TradeOrdersDTO ordersDTO);

	/**
	 * 确认发货，添加物流
	 * @param ordersDTO
	 * @return
     */
	public int confimDeliver(TradeOrdersDTO ordersDTO);


}
