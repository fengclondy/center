package cn.htd.searchcenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [商品属性对象]
 * </p>
 */
public class SearchItemAttrValue implements Serializable {

	private static final long serialVersionUID = 1878550677473628783L;

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
