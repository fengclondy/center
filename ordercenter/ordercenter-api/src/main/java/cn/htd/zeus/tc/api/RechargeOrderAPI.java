package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.EmptyResDTO;
import cn.htd.zeus.tc.dto.response.RechargeOrderListResDTO;
import cn.htd.zeus.tc.dto.resquest.PaymentOrderInfoReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderQueryReqDTO;
import cn.htd.zeus.tc.dto.resquest.RechargeOrderReqDTO;

public interface RechargeOrderAPI {

	EmptyResDTO insertRechargeOrder(RechargeOrderReqDTO rechargeOrderReqDTO);

	RechargeOrderListResDTO selectRechargeOrder(RechargeOrderQueryReqDTO rechargeOrderQueryReqDTO);
    
	/*
     * 收付款回调
     */
	EmptyResDTO updatePaymentOrderInfo(PaymentOrderInfoReqDTO paymentOrderInfoReqDTO);
}
