package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [店铺装修备份信息]
 * </p>
 * 
 */
public class ShopFrameBackupDTO implements Serializable {

	private static final long serialVersionUID = -8729347161213029265L;

	private Long id;

	/**
	 * 店铺ID
	 */
	private Long shopId;

	/**
	 * 框架ID
	 */
	private Long frameId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public ShopFrameBackupDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getFrameId() {
		return frameId;
	}

	public void setFrameId(Long frameId) {
		this.frameId = frameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
