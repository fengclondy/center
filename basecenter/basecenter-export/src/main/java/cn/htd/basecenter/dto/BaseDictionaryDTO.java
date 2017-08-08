package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [字典表]
 * </p>
 */
public class BaseDictionaryDTO implements Serializable {

	private static final long serialVersionUID = -4865333716151448827L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 名称
	 */
	private String name = "";
	/**
	 * 编码
	 */
	private String code = "";
	/**
	 * 值
	 */
	private String value = "";
	/**
	 * 类型ID
	 */
	private Long type;
	/**
	 * 类型名称
	 */
	private String typeName = "";
	/**
	 * 字典分组编码
	 */
	private String parentCode = "";
	/**
	 * 字典分组名称
	 */
	private String parentName = "";
	/**
	 * 备注
	 */
	private String remark = "";
	/**
	 * 0:禁用，1:启用
	 */
	private int status;
	/**
	 * 排序号
	 */
	private int sortNum;
	/**
	 * 创建者ID
	 */
	private Long createId;
	/**
	 * 创建者名称
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新者
	 */
	private Long modifyId;
	/**
	 * 更行者名称
	 */
	private String modifyName;
	/**
	 * 更新时间
	 */
	private Date modifyTime;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
