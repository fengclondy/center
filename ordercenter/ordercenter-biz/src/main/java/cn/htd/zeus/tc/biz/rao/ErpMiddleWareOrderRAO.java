/**
 * 
 */
package cn.htd.zeus.tc.biz.rao;

import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

/**
 * @author ly
 *
 */
public interface ErpMiddleWareOrderRAO{

	/**
	 * 
	 * @param messageId 消息ID
	 * @param lockBalanceCode 锁定余额编码
	 * @return
	 */
	public OtherCenterResDTO<String> erpBalanceUnlock(String messageId,String lockBalanceCode);
}
