package cn.htd.searchcenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import cn.htd.common.Pager;
public class SearchItemSkuInDTO implements Serializable {

	private static final long serialVersionUID = 871356129957380540L;

	private Pager<SearchItemSkuOutDTO> pager = new Pager<SearchItemSkuOutDTO>();// 分页对象
	private List<Long> brandIds = new ArrayList<Long>();// 品牌ID组
	private Long cid;// 商品类目ID
	private String keyword;// 搜索关键字
	private String attributes;// [属性ID:属性值ID;...] 此形式字符串
	private String areaCode;// 区域编码3
	private Long buyerId;// 买家ID
	private int orderSort;// 1 时间升序 2时间降序 3评价升序 4评价降序 5销量升序 6销量降序 7价格升序 8价格降序
	private List<Long> itemIds = new ArrayList<Long>();// 查询商品id组下所以得SKU

	private Long payType;// 支付方式，1：货到付款

	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}

	public List<Long> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Long> brandIds) {
		this.brandIds = brandIds;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Pager<SearchItemSkuOutDTO> getPager() {
		return pager;
	}

	public void setPager(Pager<SearchItemSkuOutDTO> pager) {
		this.pager = pager;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getOrderSort() {
		return orderSort;
	}

	public void setOrderSort(int orderSort) {
		this.orderSort = orderSort;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public List<Long> getItemIds() {
		return itemIds;
	}

	public void setItemIds(List<Long> itemIds) {
		this.itemIds = itemIds;
	}

}
