package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchGetStockResDTO extends JDResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6742639429828809010L;

	private List<StockNewResultVoDTO> data = new ArrayList<StockNewResultVoDTO>();

	/**
	 * @return the data
	 */
	public List<StockNewResultVoDTO> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<StockNewResultVoDTO> data) {
		this.data = data;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
