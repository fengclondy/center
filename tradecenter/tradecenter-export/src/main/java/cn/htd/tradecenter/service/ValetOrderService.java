/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    ValetOrderService.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 代客下单服务接口  
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.TradeOrdersDTO;
import cn.htd.tradecenter.dto.VenusCreateTradeOrderDTO;

/**
 * 代客下单服务接口
 */
public interface ValetOrderService {

    /**
     * 代客下单新增订单
     * 
     * @param venusInDTO 订单信息
     * @return 新增订单结果
     */
    ExecuteResult<TradeOrdersDTO> createValetOrder(VenusCreateTradeOrderDTO venusInDTO);
}
