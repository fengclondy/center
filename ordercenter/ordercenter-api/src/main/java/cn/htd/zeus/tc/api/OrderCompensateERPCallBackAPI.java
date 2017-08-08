package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.OrderCompensateERPCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.OrderCompensateERPCallBackReqDTO;

public interface OrderCompensateERPCallBackAPI {
	
	/*
	 *  五合一下行提供给中间件回调的方法
	 */
	public OrderCompensateERPCallBackResDTO orderCompensateERPCallBack(OrderCompensateERPCallBackReqDTO orderCompensateERPCallBackReqDTO);
	
}
