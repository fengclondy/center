package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;

public class ProductPlusAccessInfoInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2570964579463729708L;
	
	private Integer currentPage;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
