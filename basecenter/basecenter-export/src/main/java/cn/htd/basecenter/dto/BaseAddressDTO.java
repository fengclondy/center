package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Description: [基础省市区镇编码表]
 * </p>
 */
public class BaseAddressDTO implements Serializable {

	private static final long serialVersionUID = -7667606312154895090L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 省市区镇编码
	 */
	private String code = "";
	/**
	 * 父级编码
	 */
	private String parentCode = "";
	/**
	 * 地址名称
	 */
	private String name = "";
	/**
	 * 等级:1省 2市 3县/县级市/区 4镇/街道
	 */
	private int level;
	/**
	 * 删除标记：0有效 1已删除
	 */
	private int deleteFlag;
	/**
	 * ERP下行状态
	 */
	private String erpStatus = "";
	/**
	 * ERP下行时间
	 */
	private Date erpDownTime;
	/**
	 * ERP下行错误信息
	 */
	private String erpErrorMsg = "";
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
	 * 更新者ID
	 */
	private Long modifyId;
	/**
	 * 更新者名称
	 */
	private String modifyName;
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	/**
	 * 父级地址信息列表
	 */
	private List<BaseAddressDTO> parentBaseAddressDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
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

	public List<BaseAddressDTO> getParentBaseAddressDTO() {
		return parentBaseAddressDTO;
	}

	public void setParentBaseAddressDTO(List<BaseAddressDTO> parentBaseAddressDTO) {
		this.parentBaseAddressDTO = parentBaseAddressDTO;
	}
}
