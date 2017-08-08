package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;

public class VenusWarningStockLevelDataOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8097013374053741612L;
	
	private Integer sumOfWarningProducts;
	private Integer sumOfProductsWithoutWarning;
	public Integer getSumOfWarningProducts() {
		return sumOfWarningProducts;
	}
	public void setSumOfWarningProducts(Integer sumOfWarningProducts) {
		this.sumOfWarningProducts = sumOfWarningProducts;
	}
	public Integer getSumOfProductsWithoutWarning() {
		return sumOfProductsWithoutWarning;
	}
	public void setSumOfProductsWithoutWarning(Integer sumOfProductsWithoutWarning) {
		this.sumOfProductsWithoutWarning = sumOfProductsWithoutWarning;
	}
	

}
