package cn.htd.zeus.tc.biz.service;

import cn.htd.zeus.tc.biz.dmo.RechargeOrderDMO;
import cn.htd.zeus.tc.biz.dmo.RechargeOrderListDMO;
import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderReqDTO;

/**
 * 充值订单中心
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: RechargeOrderService.java
 * Author:   jiaop
 * Date:     2016-8-25 下午4:47:39
 * Description: //模块目的、功能描述
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
public interface RechargeOrderService {

	EmptyResDTO insertRechargeOrder(RechargeOrderReqDTO rechargeOrderReqDTO);

	RechargeOrderListDMO selectRechargeOrder(RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO);

	EmptyResDTO updatePaymentOrderInfo(PaymentOrderInfoReqDTO paymentOrderInfoReqDTO,String messageId);
	
	RechargeOrderDMO selectRechargeOrderByOrderNo(RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO);
}
