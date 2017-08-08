package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class VenusItemSkuAutoShelfStatusInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3029864256051188162L;
	
	//上架类型 1 包厢 2 大厅
	@NotEmpty(message="上架类型不能为空")
	private String shelfType;
	@NotNull(message="上架类型不能为空")
	private List<Long> skuIdList;
	@NotNull(message="operatorId不能为空")
	private Long operatorId;
	@NotEmpty(message="operatorName不能为空")
	private String operatorName;
	//按可见库存上下架 0 否 1 是
	@NotEmpty(message="是否按可见库存上下架标记不能为空")
	private String sortByStockStatus;
	private String upShelfTime;
	private String downShelfTime;
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public List<Long> getSkuIdList() {
		return skuIdList;
	}
	public void setSkuIdList(List<Long> skuIdList) {
		this.skuIdList = skuIdList;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getSortByStockStatus() {
		return sortByStockStatus;
	}
	public void setSortByStockStatus(String sortByStockStatus) {
		this.sortByStockStatus = sortByStockStatus;
	}
	public String getUpShelfTime() {
		return upShelfTime;
	}
	public void setUpShelfTime(String upShelfTime) {
		this.upShelfTime = upShelfTime;
	}
	public String getDownShelfTime() {
		return downShelfTime;
	}
	public void setDownShelfTime(String downShelfTime) {
		this.downShelfTime = downShelfTime;
	}
	

}
