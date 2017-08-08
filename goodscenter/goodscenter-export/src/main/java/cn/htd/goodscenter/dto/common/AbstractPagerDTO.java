package cn.htd.goodscenter.dto.common;

/**
 * 分页查询入参
 * TODO：排序字段需要重新设计，考虑多条件排序规则
 * 
 * @author zhangxiaolong
 *
 */
public abstract class AbstractPagerDTO {
	//当前页
	private Integer start;
	//每页记录数
	private Integer pageSize=10;
	//排序列
	private String sortColumn;
    //排序类型 1 升序 2 降序
	private String sortType;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
}
