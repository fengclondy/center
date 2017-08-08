package cn.htd.searchcenter.forms;

public class SearchShopForm {
	private String addressCode;
	private String keyword;
	private Integer page;
	private Integer rows;
	private Integer sort;
	private Long buyerId;
	private String boxRelationVenderIdList;
	public String getBoxRelationVenderIdList() {
		return boxRelationVenderIdList;
	}
	public void setBoxRelationVenderIdList(String boxRelationVenderIdList) {
		this.boxRelationVenderIdList = boxRelationVenderIdList;
	}
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
}
