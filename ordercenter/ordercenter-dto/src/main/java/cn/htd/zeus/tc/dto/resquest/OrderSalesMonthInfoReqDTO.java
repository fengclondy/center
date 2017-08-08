package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;


public class OrderSalesMonthInfoReqDTO extends GenricReqDTO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -822243326505665755L;
	
	private Long supperlierId;//供应商ID

	public Long getSupperlierId() {
		return supperlierId;
	}

	public void setSupperlierId(Long supperlierId) {
		this.supperlierId = supperlierId;
	}
    
}