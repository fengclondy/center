package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopTemplatesDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long shopId;// 店铺id
	private String templatesName;// 模板名称
	private String status;// 状态 1：使用中，2：未使用
	private String templatesInfo;// 模板说明
	private String color;// 颜色
	private Date created;// 创建日期
	private Date modified;// 修改日期
	private Long createId; //创建人Id
	private String createName; //创建人名称
	private Long modifyId;//修改人ID
	private String modifyName; //修改人名称

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long value) {
		this.shopId = value;
	}

	public String getTemplatesName() {
		return this.templatesName;
	}

	public void setTemplatesName(String value) {
		this.templatesName = value;
	}

	public String getTemplatesInfo() {
		return this.templatesInfo;
	}

	public void setTemplatesInfo(String value) {
		this.templatesInfo = value;
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

	public String getStatus() {
		return status;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
}
