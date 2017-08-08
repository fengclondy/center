package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemOldDTO implements Serializable {

	private static final long serialVersionUID = 1l;
	private Long id;// 主键
	private Long itemId;// 商品编码
	private Long cid;// 类目ID 三级类目
	private String cName;// 类目名称
	private Long sellerId;// 卖家id
	private String itemName;// 二手商品名称
	private Integer recency;// 新旧程度 全新10 非全新9
	private BigDecimal priceOld;// 原价
	private BigDecimal price;// 现价
	private BigDecimal freight;// 运费
	private Long sellerTel;// 卖家联系方式
	private String sellerName;// 卖家姓名
	private String provinceCode;// 省编码
	private String provinceName;// 省名称
	private String cityCode;// 市编码
	private String cityName;// 市名称
	private String districtCode;// 区编码
	private String districtName;// 区名称
	private String describeUr;// 商品描述url，存在jfs中
	private Date created;// 创建时间
	private Date modified;// 修改时间
	private String comment;// 批注
	private String platformUserId;// 批注人id
	private Integer status;// 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定，
										// 7： 申请解锁 8删除

	// 表外后加字段
	private Integer[] statuss;// 状态数组 为空时查询状态为4在售的商品
	private String imgUrl;// 查询商品图片get 0
	private Date createdstr;// 创建时间开始
	private Date createdend;// 创建时间结束
	private Long[] sellerIds; // 用户ID组
	private String timeDifference;// 当前时间与发布时间差

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcName() {
		return cName;
	}

	public Integer[] getStatuss() {
		return statuss;
	}

	public void setStatuss(Integer[] statuss) {
		this.statuss = statuss;
	}

	public Date getCreatedstr() {
		return createdstr;
	}

	public void setCreatedstr(Date createdstr) {
		this.createdstr = createdstr;
	}

	public Date getCreatedend() {
		return createdend;
	}

	public void setCreatedend(Date createdend) {
		this.createdend = createdend;
	}

	public Long[] getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(Long[] sellerIds) {
		this.sellerIds = sellerIds;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long value) {
		this.itemId = value;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String value) {
		this.itemName = value;
	}

	public Integer getRecency() {
		return this.recency;
	}

	public void setRecency(Integer value) {
		this.recency = value;
	}

	public BigDecimal getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(BigDecimal priceOld) {
		this.priceOld = priceOld;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Long getSellerTel() {
		return this.sellerTel;
	}

	public void setSellerTel(Long value) {
		this.sellerTel = value;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public void setSellerName(String value) {
		this.sellerName = value;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String value) {
		this.provinceCode = value;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String value) {
		this.provinceName = value;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String value) {
		this.cityCode = value;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String value) {
		this.cityName = value;
	}

	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String value) {
		this.districtCode = value;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String value) {
		this.districtName = value;
	}

	public String getDescribeUr() {
		return this.describeUr;
	}

	public void setDescribeUr(String value) {
		this.describeUr = value;
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

	public String getComment() {
		return this.comment;
	}

	public void setComment(String value) {
		this.comment = value;
	}

	public String getPlatformUserId() {
		return this.platformUserId;
	}

	public void setPlatformUserId(String value) {
		this.platformUserId = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

}
