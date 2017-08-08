/**
 * 
 */
package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

/**
 * @author ly
 *
 */
public class ShopOrderStatisticsDayReportReqDTO  extends GenricReqDTO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2201392147583838595L;
	

    private Integer shopId;


	public Integer getShopId() {
		return shopId;
	}


	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
}
