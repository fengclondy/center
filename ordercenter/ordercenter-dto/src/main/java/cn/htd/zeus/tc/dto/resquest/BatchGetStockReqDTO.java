package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;
import java.util.List;

public class BatchGetStockReqDTO  extends GenricReqDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3571808014385783033L;


	private String area;
	
	private List<BatchGetStockSkuNumsReqDTO> skuNums;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<BatchGetStockSkuNumsReqDTO> getSkuNums() {
		return skuNums;
	}

	public void setSkuNums(List<BatchGetStockSkuNumsReqDTO> skuNums) {
		this.skuNums = skuNums;
	}

}
