package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [查询子集类目 出参dto]
 * </p>
 */

public class QueryChildCategoryOutDTO implements Serializable {
	private static final long serialVersionUID = -2729081174251794580L;
	private String childCategorys;

	public String getChildCategorys() {
		return childCategorys;
	}

	public void setChildCategorys(String childCategorys) {
		this.childCategorys = childCategorys;
	}

}
