package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class ShopModifyDetailDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long shopId;// 店铺ID 不会返回参数
	private Long[] shopIds; // 店铺ID组
	private Long applicantUserId; //申请人ID
	private String applicantUserName; //申请人姓名
	private String contentName; //内容名
	private String changeTableId; //修改表ID
	private String changeFieldId; //修改字段ID
	private String beforeChange;// 修改前的内容
	private String afterChange;// 修改后的内容
	private Date createTime;// 创建时间
	private Long createId; //创建人ID
	private String createName; //创建人名称
	private String beforeChangeName;
	private String afterChangeName;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}


	public String getBeforeChange() {
		return this.beforeChange;
	}

	public void setBeforeChange(String value) {
		this.beforeChange = value;
	}

	public String getAfterChange() {
		return this.afterChange;
	}

	public void setAfterChange(String value) {
		this.afterChange = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public void setChangeTableId(String changeTableId) {
		this.changeTableId = changeTableId;
	}

	public void setChangeFieldId(String changeFieldId) {
		this.changeFieldId = changeFieldId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getContentName() {
		return contentName;
	}

	public String getChangeTableId() {
		return changeTableId;
	}

	public String getChangeFieldId() {
		return changeFieldId;
	}

	public Long getCreateId() {
		return createId;
	}

	public String getCreateName() {
		return createName;
	}

	public Long getApplicantUserId() {
		return applicantUserId;
	}

	public String getApplicantUserName() {
		return applicantUserName;
	}

	public void setApplicantUserId(Long applicantUserId) {

		this.applicantUserId = applicantUserId;
	}

	public void setApplicantUserName(String applicantUserName) {
		this.applicantUserName = applicantUserName;
	}

	public void setShopIds(Long[] shopIds) {
		this.shopIds = shopIds;
	}

	public Long[] getShopIds() {
		return shopIds;
	}

	public String getBeforeChangeName() {
		return beforeChangeName;
	}

	public void setBeforeChangeName(String beforeChangeName) {
		this.beforeChangeName = beforeChangeName;
	}

	public String getAfterChangeName() {
		return afterChangeName;
	}

	public void setAfterChangeName(String afterChangeName) {
		this.afterChangeName = afterChangeName;
	}
}
