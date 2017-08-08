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
public class ShopOrderAnalsisDayReportResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1963061597934608971L;

	private List<ShopOrderAnalysisDayReportListResDTO> shopOrderAnalysisDayReportListResDTOList;

	public List<ShopOrderAnalysisDayReportListResDTO> getShopOrderAnalysisDayReportListResDTOList() {
		return shopOrderAnalysisDayReportListResDTOList;
	}

	public void setShopOrderAnalysisDayReportListResDTOList(
			List<ShopOrderAnalysisDayReportListResDTO> shopOrderAnalysisDayReportListResDTOList) {
		this.shopOrderAnalysisDayReportListResDTOList = shopOrderAnalysisDayReportListResDTOList;
	}

	
}
