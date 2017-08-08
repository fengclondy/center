package cn.htd.goodscenter.domain;

/**
 * 商品属性类
 *
 */
public class CategoryAttr extends AbstractDomain {

	private Long id; // ID
	private Long cid; // 类目id
	private Long valueId; // 属性值id
	private String valueName; // 属性值名称
	private String indexKey; // 属性值的首字母。供页面以a、b、c等的形式显示
	private Integer attrType; // 属性类型:1:销售属性;2:非销售属性
	private Integer selectType; // 是否多选。1：单选；2：多选
	private Integer sortNumber; // 排序号。越小越靠前。
	private Integer optionType; // 是否必填。1：必填；2：非必填
	private String features; // 特性：以“key”对应 前期：color表示是颜色属性 size表示尺码
	private Long attrId; // 属性id
	private String attrName; // 属性名称
	private Integer status; // 记录状态 1.新建 2.删除

	public Long getValueId() {
		return valueId;
	}

	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getIndexKey() {
		return indexKey;
	}

	public void setIndexKey(String indexKey) {
		this.indexKey = indexKey;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getOptionType() {
		return optionType;
	}

	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid2) {
		this.cid = cid2;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTtrId() {
		return attrId;
	}

	public void setTtrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

}
