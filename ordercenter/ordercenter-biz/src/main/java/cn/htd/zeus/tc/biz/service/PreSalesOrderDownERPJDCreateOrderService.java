package cn.htd.zeus.tc.biz.service;

import java.util.List;
import java.util.Map;

import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.PreSalesOrderCallBackDMO;
import cn.htd.zeus.tc.dto.resquest.PreSalesOrderCallBackReqDTO;

public interface PreSalesOrderDownERPJDCreateOrderService {
	 
	/*
     * 预售下行接口-查询
     */
    public List<JDOrderInfoDMO> selectERPOrderNOFromJDOrderInfo(Map paramMap);
    
    /*
     * 处理预售下行接口业务逻辑
     */
    public void preSalesOrderDown(JDOrderInfoDMO[] tasks);
    
    /*
     * 预售下行回调接口
     */
    public PreSalesOrderCallBackDMO executeJDCreateOrderCallBack(PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO);
}
