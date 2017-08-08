package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopRenovationDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// id
	private String moduleName;// 模块名称
	private BigDecimal price;// 价格
	private String pictureUrl;// 图片地址
	private Integer moduleGroup;// 1:基础模块,2:系统模块
	private Integer modultType;// 1：header，2：模板1广告位，4：推荐位，9：模板1广告位Outer，13：模板2轮播图，12：模板2轮播图Outer，11：模板2广告位，10：模板,12广告位Outer
	private Integer status;// 状态，1:有效 ,2:删除 3使用中
	private Date created;// 创建日期
	private Date modified;// 修改日期
	private Long skuId;// 商品skuid
	private String position;// 位置 （从a开始顺延 页面从上至下）
	private Long templatesId;// 模板id (1模板一 ，2 模板二 ）
	private String chainUrl; // 跳转地址
	private Integer hasPrice;// 是否有报价

	public String getChainUrl() {
		return chainUrl;
	}

	public void setChainUrl(String chainUrl) {
		this.chainUrl = chainUrl;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String value) {
		this.moduleName = value;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(String value) {
		this.pictureUrl = value;
	}

	public Integer getModuleGroup() {
		return this.moduleGroup;
	}

	public void setModuleGroup(Integer value) {
		this.moduleGroup = value;
	}

	public Integer getModultType() {
		return this.modultType;
	}

	public void setModultType(Integer value) {
		this.modultType = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
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

	public Long getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Long value) {
		this.skuId = value;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String value) {
		this.position = value;
	}

	public Long getTemplatesId() {
		return templatesId;
	}

	public void setTemplatesId(Long templatesId) {
		this.templatesId = templatesId;
	}

	public Integer getHasPrice() {
		return hasPrice;
	}

	public void setHasPrice(Integer hasPrice) {
		this.hasPrice = hasPrice;
	}
}
