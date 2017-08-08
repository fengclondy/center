package cn.htd.tradecenter.dto;

import java.io.Serializable;

public class TradeOrderItemStockDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer productStock;//商品库存
	
	private Integer lockStock;//锁库存

	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}

	public Integer getLockStock() {
		return lockStock;
	}

	public void setLockStock(Integer lockStock) {
		this.lockStock = lockStock;
	}

}
