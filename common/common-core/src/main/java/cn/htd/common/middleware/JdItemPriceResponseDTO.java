package cn.htd.common.middleware;

import java.io.Serializable;
import java.math.BigDecimal;

public class JdItemPriceResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal price;//成本价
	private BigDecimal jdPrice;//销售价
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getJdPrice() {
		return jdPrice;
	}
	public void setJdPrice(BigDecimal jdPrice) {
		this.jdPrice = jdPrice;
	}
	
	

}
