package cn.htd.searchcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Description: [商品属性对象]
 * </p>
 */
public class SearchItemAttr implements Serializable {

	private static final long serialVersionUID = -212851812667326995L;

	private Long id;
	private String name;
	private List<SearchItemAttrValue> values = new ArrayList<SearchItemAttrValue>();

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

	public List<SearchItemAttrValue> getValues() {
		return values;
	}

	public void setValues(List<SearchItemAttrValue> values) {
		this.values = values;
	}

}
