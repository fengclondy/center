package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;

public class OrderQueryPageSizeResDTO extends GenricResDTO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -9087479126175343409L;

	private Integer size;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
