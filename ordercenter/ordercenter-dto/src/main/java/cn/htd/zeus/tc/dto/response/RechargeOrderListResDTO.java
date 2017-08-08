package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class RechargeOrderListResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 2346846905652661198L;

	private List<RechargeOrderResDTO> rechargeOrder;

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<RechargeOrderResDTO> getRechargeOrder() {
		return rechargeOrder;
	}

	public void setRechargeOrder(List<RechargeOrderResDTO> rechargeOrder) {
		this.rechargeOrder = rechargeOrder;
	}

}
