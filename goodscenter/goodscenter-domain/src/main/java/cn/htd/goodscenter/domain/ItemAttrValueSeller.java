package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [item_attr_value商家属性值关联表]
 * </p>
 */
public class ItemAttrValueSeller extends AbstractDomain implements Serializable {

	private static final long serialVersionUID = 8742432937807704561L;

	private Long sellerAttrValueId;
	private Long sellerAttrId;// 商家属性关联ID
	private Long valueId;// 属性值ID
	private String valueName;// 属性值名称
	private Integer sortNumber;// 排序号
	private Integer attrValueStatus;// 属性值状态
	private Date created;
	private Date modified;
	private Long createId;
	private String createName;
	private Long modifyId;
	private String modifyName;

	public Long getSellerAttrValueId() {
		return sellerAttrValueId;
	}

	public void setSellerAttrValueId(Long sellerAttrValueId) {
		this.sellerAttrValueId = sellerAttrValueId;
	}

	public Long getSellerAttrId() {
		return sellerAttrId;
	}

	public void setSellerAttrId(Long sellerAttrId) {
		this.sellerAttrId = sellerAttrId;
	}

	public Long getValueId() {
		return valueId;
	}

	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getAttrValueStatus() {
		return attrValueStatus;
	}

	public void setAttrValueStatus(Integer attrValueStatus) {
		this.attrValueStatus = attrValueStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	@Override
	public Long getCreateId() {
		return createId;
	}

	@Override
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Override
	public String getCreateName() {
		return createName;
	}

	@Override
	public void setCreateName(String createName) {
		this.createName = createName;
	}

	@Override
	public Long getModifyId() {
		return modifyId;
	}

	@Override
	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	@Override
	public String getModifyName() {
		return modifyName;
	}

	@Override
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}
