package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreate4huilinReqDTO implements Serializable {

	private static final long serialVersionUID = -7963788605565343410L;

	// 接口编号
	@NotBlank(message = "messageId不能为空")
	private String messageId;

	// 会员编号
	@NotNull(message = "buyerId不能为空")
	private Long buyerId;

	// 订单集合
	@NotNull(message = "orderList不能为空")
	@Valid
	private List<OrderCreateOrderListInfoReqDTO> orderList;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public List<OrderCreateOrderListInfoReqDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderCreateOrderListInfoReqDTO> orderList) {
		this.orderList = orderList;
	}
}
