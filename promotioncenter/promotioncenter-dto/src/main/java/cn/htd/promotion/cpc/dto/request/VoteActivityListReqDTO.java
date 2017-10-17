package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;

public class VoteActivityListReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141977245560399338L;
	
	//当前页
	private Integer currentPage;
	//每页记录数
	private Integer pageSize;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
