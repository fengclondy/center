package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class OrderQueryResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1553059700130448367L;
	
	private String orderNo;
	
	private String tradeNo;
	
	private Date createTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
