/**
 * 
 */
package cn.htd.zeus.tc.biz.rao;

import cn.htd.zeus.tc.dto.othercenter.response.OtherCenterResDTO;

/**
 * @author ly
 *
 */
public interface MiddleWareBaseRAO {

	
	/**
	 * 
	 * @return
	 */
	public OtherCenterResDTO<String> getMiddleWareToken(String messageId);
}
