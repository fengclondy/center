package cn.htd.goodscenter.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [批量修改库存量的入参]
 * </p>
 */
public class InventoryModifyDTO implements Serializable {

	private static final long serialVersionUID = 4562318562602731030L;

	private Long skuId; // sku编码
	private Integer totalInventory; // 时间库存量

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getTotalInventory() {
		return totalInventory;
	}

	public void setTotalInventory(Integer totalInventory) {
		this.totalInventory = totalInventory;
	}

}
