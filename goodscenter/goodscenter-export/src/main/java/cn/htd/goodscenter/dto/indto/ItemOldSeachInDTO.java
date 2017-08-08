package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * <p>
 * Description: [二手商品搜索入参]
 * </p>
 */
public class ItemOldSeachInDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;// 关键字
	private Long cid; // 类目ID 可为任意级别
	private BigDecimal price;// 固定价格条件
	private BigDecimal pricemin;// 区间价格条件 最小值
	private BigDecimal pricemax;// 区间价格条件 最最大值
	private Integer recency;// 新旧程度 全新10 非全新9
	private String provinceCode;// 省编码
	private String cityCode;// 市编码
	private String districtCode;// 区编码
	private Integer orderType;// 1 价格正序 2 价格倒序 3商品发布时间正序 4 商品发布时间倒序
	private List<Long> cids;// 三级类目组 （入参不用传）

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Long> getCids() {
		return cids;
	}

	public void setCids(List<Long> cids) {
		this.cids = cids;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPricemin() {
		return pricemin;
	}

	public void setPricemin(BigDecimal pricemin) {
		this.pricemin = pricemin;
	}

	public BigDecimal getPricemax() {
		return pricemax;
	}

	public void setPricemax(BigDecimal pricemax) {
		this.pricemax = pricemax;
	}

	public Integer getRecency() {
		return recency;
	}

	public void setRecency(Integer recency) {
		this.recency = recency;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

}
