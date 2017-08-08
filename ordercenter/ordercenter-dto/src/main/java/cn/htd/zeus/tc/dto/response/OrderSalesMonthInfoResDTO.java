package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.Date;

public class OrderSalesMonthInfoResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -90588250568577840L;

	private Integer salesMonthYear;

	private Long salesAmount;

    private Date countTime;

    public Integer getSalesMonthYear() {
        return salesMonthYear;
    }

    public void setSalesMonthYear(Integer salesMonthYear) {
        this.salesMonthYear = salesMonthYear;
    }

    public Long getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Long salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }
}
