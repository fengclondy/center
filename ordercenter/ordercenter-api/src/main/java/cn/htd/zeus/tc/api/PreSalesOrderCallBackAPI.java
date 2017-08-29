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

	/*
	 * 汇掌柜预售订单更新订单支付截止时间
	 */
	public PreSalesOrderCallBackResDTO preSalesOrderUpdateStatus(
			PreSalesOrderCallBackReqDTO preSalesOrderCallBackReqDTO);
}
