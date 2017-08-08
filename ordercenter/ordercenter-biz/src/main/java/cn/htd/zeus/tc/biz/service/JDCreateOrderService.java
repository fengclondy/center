package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.JDCreateOrderCallBackDMO;
import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCreateInfoDMO;
import cn.htd.zeus.tc.dto.resquest.BatchGetStockReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderCallBackReqDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderReqDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCreateListInfoReqDTO;

public interface JDCreateOrderService {
	 
	/*
	 * 京东抛单查询接口
	 */
    public List<JDOrderInfoDMO> selectJDOrderNOFromJDOrderInfo(Map paramMap);
    
    /*
     * 京东确认订单接口业务逻辑
     */
    public void jdSureCreateOrder(JDOrderInfoDMO[] tasks);
   
    /*
     * 京东抛单回调接口
     */
    public JDCreateOrderCallBackDMO executeJDCreateOrderCallBack(JDCreateOrderCallBackReqDTO jdCreateOrderCallBackReqDTO);
    
    /*
     * 京东创建订单接口
     */
    public OrderCreateInfoDMO createJDOrder(String messageId,JDCreateOrderReqDTO jdCreateOrderReqDTO,OrderCreateListInfoReqDTO orderTemp);
}
