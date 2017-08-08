/**
 * 
 */
package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author ly
 *
 */
public class ShopOrderStatisticsDayReportResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8762380680663995404L;
	
	private List<ShopOrderStatisticsDayReportListResDTO> shopOrderStatisticsDayReportListResDTO;

	public List<ShopOrderStatisticsDayReportListResDTO> getShopOrderStatisticsDayReportListResDTO() {
		return shopOrderStatisticsDayReportListResDTO;
	}

	public void setShopOrderStatisticsDayReportListResDTO(
			List<ShopOrderStatisticsDayReportListResDTO> shopOrderStatisticsDayReportListResDTO) {
		this.shopOrderStatisticsDayReportListResDTO = shopOrderStatisticsDayReportListResDTO;
	}
}
