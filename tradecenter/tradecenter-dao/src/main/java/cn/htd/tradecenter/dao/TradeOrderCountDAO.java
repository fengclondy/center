/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	TradeOrderErpDistributionUpdateDAO.java
 * Author:   	jiangkun
 * Date:     	2016年11月23日
 * Description: 订单分销单DAO
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月23日	1.0			创建
 */
package cn.htd.tradecenter.dao;

import java.util.List;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.tradecenter.dto.TradeOrderCountDTO;

/**
 * 订单信息统计DAO
 * 
 * @author jiangkun
 */
public interface TradeOrderCountDAO extends BaseDAO<TradeOrderCountDTO> {

	/**
	 * 取得订单状态统计信息
	 * 
	 * @param todoDTO
	 * @return
	 */
	public List<TradeOrderCountDTO> queryOrderStatusCount(TradeOrderCountDTO todoDTO);

}