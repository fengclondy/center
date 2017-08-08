package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [店铺自定义域名dto]
 * </p>
 */
public class ShopDomainDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private Long sellerId;// 卖家id
	private Long shopId;// 店铺id
	private String domain;// 自定义域名
	private Integer state;// 自定义域名状态(1、待审核；2、驳回；3、审核通过)
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private String reviewer;// 审核人

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long value) {
		this.sellerId = value;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long value) {
		this.shopId = value;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer value) {
		this.state = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public String getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(String value) {
		this.reviewer = value;
	}

}
