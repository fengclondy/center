package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

public class OrderQuerySupprMangerReqDTO extends GenricReqDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -6456303427170463633L;

	private String memberName;

	private String htdvendor;

	private Integer pageSize;

	private Integer currentPage;

	private List<?> orderStatus;

	private String customerManagerID;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getHtdvendor() {
		return htdvendor;
	}

	public void setHtdvendor(String htdvendor) {
		this.htdvendor = htdvendor;
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

	public List<?> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(List<?> orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustomerManagerID() {
		return customerManagerID;
	}

	public void setCustomerManagerID(String customerManagerID) {
		this.customerManagerID = customerManagerID;
	}

}
