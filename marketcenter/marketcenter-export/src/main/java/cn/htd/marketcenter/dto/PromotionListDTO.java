/**
 * 
 */
package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 促销活动列表展示
 *
 */
public class PromotionListDTO extends PromotionInfoDTO implements Serializable {

	private static final long serialVersionUID = -2105509991695223019L;

	private String itemName;// 商品名称
	private String itemCode;// 商品编码
	private String cName;// 促销商品所属类目

	/**
	 * 限时购活动
	 */
	private List<TimelimitedInfoDTO> timelimitedInfoDTO;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public List<TimelimitedInfoDTO> getTimelimitedInfoDTO() {
		return timelimitedInfoDTO;
	}

	public void setTimelimitedInfoDTO(List<TimelimitedInfoDTO> timelimitedInfoDTO) {
		this.timelimitedInfoDTO = timelimitedInfoDTO;
	}

}
