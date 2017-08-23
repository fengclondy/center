package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreateOrderListInfoReqDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6226976318161817142L;

	// 买家留言
	private String buyerRemarks;

	// 卖家编码
	@NotNull(message = "sellerId不能为空")
	private Long sellerId;

	// 订单来源
	@NotBlank(message = "orderFrom不能为空")
	private String orderFrom;
	
	// 订单行集合
	@NotEmpty(message = "skuList不能为空")
	@Valid
	private List<OrderCreateSkuListInfoReqDTO> skuList;

	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public List<OrderCreateSkuListInfoReqDTO> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<OrderCreateSkuListInfoReqDTO> skuList) {
		this.skuList = skuList;
	}
}
