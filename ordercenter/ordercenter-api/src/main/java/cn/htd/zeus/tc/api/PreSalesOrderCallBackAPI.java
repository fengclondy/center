package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.PreSalesOrderCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.PreSalesOrderCallBackReqDTO;

/*
 * 预售下行-回调接口
 */
public interface PreSalesOrderCallBackAPI {
	
	/*
	 * 预售下行-回调方法
	 */
	public PreSalesOrderCallBackResDTO preSalesOrderCallBack(PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO);
}
