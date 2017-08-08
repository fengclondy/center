package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [店铺品牌]
 * </p>
 */
public class ShopBrand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -583834160961641287L;

	private Long id;

	private Long shopId;// 铺店id

	private Long brandId;// 品牌id

	private Long platformId;// 平台id

	private String status;// 店铺品牌状态：1为申请，2为通过，3为驳回

	private Date created;// 创建时间

	private Date modified;// 更新时间

	private Long operatorId;// 操作人

	private String comment;// 回复

	private Long sellerId;// 卖家id

	private Long cid;// 三级类目id

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

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
}
