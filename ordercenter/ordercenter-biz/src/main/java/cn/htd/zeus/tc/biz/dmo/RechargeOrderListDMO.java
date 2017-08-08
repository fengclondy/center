package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

public class RechargeOrderListDMO extends GenericDMO{

	/**
	 *
	 */
	private static final long serialVersionUID = -7688585475193182751L;

	private List<RechargeOrderDMO> rechargeOrder;

	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<RechargeOrderDMO> getRechargeOrder() {
		return rechargeOrder;
	}

	public void setRechargeOrder(List<RechargeOrderDMO> rechargeOrder) {
		this.rechargeOrder = rechargeOrder;
	}

}
