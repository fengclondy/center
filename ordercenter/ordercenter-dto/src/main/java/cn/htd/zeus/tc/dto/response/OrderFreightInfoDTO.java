package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderFreightInfoDTO extends GenricResDTO implements Serializable {

	private static final long serialVersionUID = -8809903055881188468L;

	private BigDecimal freight;

	/**
	 * @return the freight
	 */
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * @param freight
	 *            the freight to set
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

}
