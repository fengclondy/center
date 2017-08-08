package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;
import java.util.List;

public class VenusItemSetShelfStatusInDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2895512776174273709L;
	//上架类型 1 包厢上架 2 大厅上架
	private String shelfType;
	private List<Long> skuIdList;
	//0 下架 1 上架
	private String setStatusFlag;
	//是否可以低于分销限价
	private boolean isBelowFloorPrice;
	private Long operatorId;
	private String operatorName;
	//供应商编码
	private String supplierCode;
	//是否按可见库存上架
	private Integer avaliableStockFlag;
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
	public String getSetStatusFlag() {
		return setStatusFlag;
	}
	public void setSetStatusFlag(String setStatusFlag) {
		this.setStatusFlag = setStatusFlag;
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
	public boolean isBelowFloorPrice() {
		return isBelowFloorPrice;
	}
	public void setBelowFloorPrice(boolean isBelowFloorPrice) {
		this.isBelowFloorPrice = isBelowFloorPrice;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public Integer getAvaliableStockFlag() {
		return avaliableStockFlag;
	}
	public void setAvaliableStockFlag(Integer avaliableStockFlag) {
		this.avaliableStockFlag = avaliableStockFlag;
	}

	
}
