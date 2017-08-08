package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [非法字符类]
 * </p>
 */
public class IllegalCharacterDTO implements Serializable {
	private static final long serialVersionUID = -7667606312154895090L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 类型
	 */
	private String type = "";
	/**
	 * 内容
	 */
	private String content = "";
	/**
	 * 是否有效(0:有效，1无效)
	 */
	private int deleteFlag;
	/**
	 * 创建者
	 */
	private Long createId;
	/**
	 * 创建者
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
	 * 更新者
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
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
