package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemAttrValueItemDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long attrId;// 属性ID
	private Long valueId;// 属性值ID
	private Long itemId;// 商品ID
	private Integer attrType;// 属性类型:1:销售属性;2:非销售属性
	private Integer sortNumber;// 排序号。越小越靠前。
	private Integer status;// 记录状态 1.新建 2.删除
	private Date created;// 创建日期
	private Date modified;// 修改日期

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long value) {
		this.attrId = value;
	}

	public Long getValueId() {
		return this.valueId;
	}

	public void setValueId(Long value) {
		this.valueId = value;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long value) {
		this.itemId = value;
	}

	public Integer getAttrType() {
		return this.attrType;
	}

	public void setAttrType(Integer value) {
		this.attrType = value;
	}

	public Integer getSortNumber() {
		return this.sortNumber;
	}

	public void setSortNumber(Integer value) {
		this.sortNumber = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date value) {
		this.created = value;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date value) {
		this.modified = value;
	}

}
