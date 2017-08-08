package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

public class OrderQuerySupprBossReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -1799331258620535904L;

	/**
	 * 会员code
	 */
	private String buyerCode;
	
	/**
	 * 卖家编号
	 */
	private String sellerCode;

	/**
	 * 卖家名称
	 */
	private String sellerName;
	
	/**
	 * 订单状态集合
	 */
	private List<?> orderStatus;

	/**
	 * 分页条数
	 */
	private Integer pageSize;

	/**
	 * 分页页数
	 */
	private Integer currentPage;

	/**
	 * 是否删除
	 */
	private Integer orderDeleteStatus;

	/**
	 * 是否取消
	 */
	private Integer isCancelOrder;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public List<?> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<?> orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getOrderDeleteStatus() {
		return orderDeleteStatus;
	}

	public void setOrderDeleteStatus(Integer orderDeleteStatus) {
		this.orderDeleteStatus = orderDeleteStatus;
	}

	public Integer getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(Integer isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

}
