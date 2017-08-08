package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class OrderCreateListInfoReqMarketDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1972049621202015283L;
	
	//是否有外接渠道商品
	private Integer hasProductplusFlag;
    
	private Map<String,Object> extendMap;
	
	private boolean hasUsedCoupon;
	
	/**
	 * 秒杀--订单支付限制时间
	 */
	private Date payTimeLimit;
	
	/**
	 * 是否是秒杀订单
	 */
	private boolean isTimelimitedOrder;
	
	public Integer getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(Integer hasProductplusFlag) {
		this.hasProductplusFlag = hasProductplusFlag;
	}

	public Map<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<String, Object> extendMap) {
		this.extendMap = extendMap;
	}
   
	public boolean isHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(boolean hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public boolean isTimelimitedOrder() {
		return isTimelimitedOrder;
	}

	public void setTimelimitedOrder(boolean isTimelimitedOrder) {
		this.isTimelimitedOrder = isTimelimitedOrder;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}
	
}
