package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [类型表]
 * </p>
 */
public class BaseTypeDTO implements Serializable {

	private static final long serialVersionUID = 8811157437471925121L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 类型
	 */
	private String type = "";
	/**
	 * 名称
	 */
	private String name = "";
	/**
	 * 0:禁用，1:启用
	 */
	private int status;
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
	private Date createtime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
