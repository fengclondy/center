package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类目属性DTO实体类
 */
public class CategoryAttrDTO implements Serializable {
	private static final long serialVersionUID = 2990627565835351070L;
	private Long attrId; // ID
	private Long attrCid; // 类目id
	private Long attrValueId; // 属性值id
	private String attrValueName; // 属性值名称
	private String attrIndexKey; // 属性值的首字母。供页面以a、b、c等的形式显示
	private Integer attrAttrType; // 属性类型:1:销售属性;2:非销售属性
	private Integer attrSelectType; // 是否多选。1：单选；2：多选
	private Integer attrSortNumber; // 排序号。越小越靠前。
	private Integer attrOptionType; // 是否必填。1：必填；2：非必填
	private String attrFeatures; // 特性：以“key”对应 前期：color表示是颜色属性 size表示尺码
	private Long attrAttrId; // 属性id
	private String attrAttrName; // 属性名称
	private Integer attrStatus; // 记录状态 1.新建 2.删除
	private Long attrCreateId; // 创建人ID
	private String attrCreateName; // 创建名称
	private Date attrCreateTime; // 创建日期
	private Long attrModifyId; // 创建人ID
	private Date attrModifyName; // 修改日期
	private Date attrModifyTime; // 修改日期
	private List<CategoryAttrDTO> valueList; // 属性值列表

	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Long getAttrCid() {
		return attrCid;
	}

	public void setAttrCid(Long attrCid) {
		this.attrCid = attrCid;
	}

	public Integer getAttrAttrType() {
		return attrAttrType;
	}

	public Long getAttrValueId() {
		return attrValueId;
	}

	public void setAttrValueId(Long attrValueId) {
		this.attrValueId = attrValueId;
	}

	public String getAttrValueName() {
		return attrValueName;
	}

	public void setAttrValueName(String attrValueName) {
		this.attrValueName = attrValueName;
	}

	public String getAttrIndexKey() {
		return attrIndexKey;
	}

	public void setAttrIndexKey(String attrIndexKey) {
		this.attrIndexKey = attrIndexKey;
	}

	public void setAttrAttrType(Integer attrAttrType) {
		this.attrAttrType = attrAttrType;
	}

	public String getAttrFeatures() {
		return attrFeatures;
	}

	public void setAttrFeatures(String attrFeatures) {
		this.attrFeatures = attrFeatures;
	}

	public Long getAttrAttrId() {
		return attrAttrId;
	}

	public Integer getAttrSelectType() {
		return attrSelectType;
	}

	public void setAttrSelectType(Integer attrSelectType) {
		this.attrSelectType = attrSelectType;
	}

	public Integer getAttrSortNumber() {
		return attrSortNumber;
	}

	public void setAttrSortNumber(Integer attrSortNumber) {
		this.attrSortNumber = attrSortNumber;
	}

	public Integer getAttrOptionType() {
		return attrOptionType;
	}

	public void setAttrOptionType(Integer attrOptionType) {
		this.attrOptionType = attrOptionType;
	}

	public void setAttrAttrId(Long attrAttrId) {
		this.attrAttrId = attrAttrId;
	}

	public String getAttrAttrName() {
		return attrAttrName;
	}

	public void setAttrAttrName(String attrAttrName) {
		this.attrAttrName = attrAttrName;
	}

	public Integer getAttrStatus() {
		return attrStatus;
	}

	public void setAttrStatus(Integer attrStatus) {
		this.attrStatus = attrStatus;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getAttrCreateId() {
		return attrCreateId;
	}

	public void setAttrCreateId(Long attrCreateId) {
		this.attrCreateId = attrCreateId;
	}

	public String getAttrCreateName() {
		return attrCreateName;
	}

	public void setAttrCreateName(String attrCreateName) {
		this.attrCreateName = attrCreateName;
	}

	public Date getAttrCreateTime() {
		return attrCreateTime;
	}

	public void setAttrCreateTime(Date attrCreateTime) {
		this.attrCreateTime = attrCreateTime;
	}

	public Long getAttrModifyId() {
		return attrModifyId;
	}

	public void setAttrModifyId(Long attrModifyId) {
		this.attrModifyId = attrModifyId;
	}

	public Date getAttrModifyName() {
		return attrModifyName;
	}

	public void setAttrModifyName(Date attrModifyName) {
		this.attrModifyName = attrModifyName;
	}

	public Date getAttrModifyTime() {
		return attrModifyTime;
	}

	public void setAttrModifyTime(Date attrModifyTime) {
		this.attrModifyTime = attrModifyTime;
	}

	public List<CategoryAttrDTO> getValueList() {
		return valueList;
	}

	public void setValueList(List<CategoryAttrDTO> valueList) {
		this.valueList = valueList;
	}

	@Override
	public String toString() {
		return "CategoryAttrDTO{" +
				"attrId=" + attrId +
				", attrCid=" + attrCid +
				", attrValueId=" + attrValueId +
				", attrValueName='" + attrValueName + '\'' +
				", attrIndexKey='" + attrIndexKey + '\'' +
				", attrAttrType=" + attrAttrType +
				", attrSelectType=" + attrSelectType +
				", attrSortNumber=" + attrSortNumber +
				", attrOptionType=" + attrOptionType +
				", attrFeatures='" + attrFeatures + '\'' +
				", attrAttrId=" + attrAttrId +
				", attrAttrName='" + attrAttrName + '\'' +
				", attrStatus=" + attrStatus +
				", attrCreateId=" + attrCreateId +
				", attrCreateName='" + attrCreateName + '\'' +
				", attrCreateTime=" + attrCreateTime +
				", attrModifyId=" + attrModifyId +
				", attrModifyName=" + attrModifyName +
				", attrModifyTime=" + attrModifyTime +
				", valueList=" + valueList +
				'}';
	}
}
