/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package cn.htd.searchcenter.searchData;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;

import com.github.pagehelper.Page;

public class SearchDataGrid<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<T> rows = new ArrayList<T>();
	private List<Object> dataList = new ArrayList<Object>();
	private List<FacetField> facetList = new ArrayList<FacetField>();
	private Map<String, Object> screenMap = new LinkedHashMap<String, Object>();
	private String shopActivityItemQuery;
	private String shopActivityItemFilterQuery;
	
	private int total = 0;
	private int pageNum = 0;
	private int pageSize = 0;
	private int pages = 0;
	private int size = 0;
	private int startRow;
	private int endRow;
	
	public SearchDataGrid() {
	}
	

	public List<FacetField> getFacetList() {
		return facetList;
	}

	public void setFacetList(List<FacetField> facetList) {
		this.facetList = facetList;
	}


	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return this.pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
		return this.startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return this.endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public Map<String, Object> getScreenMap() {
		return screenMap;
	}

	public void setScreenMap(Map<String, Object> screenMap) {
		this.screenMap = screenMap;
	}

	public List<Object> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}
	
	
	public List<T> getRows() {
		return rows;
	}


	public void setRows(List<T> rows) {
		if (rows instanceof Page) {
			Page page = (Page) rows;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.total = (int)page.getTotal();
			this.pages = page.getPages();
			this.rows = page;
			this.size = page.size();
			this.startRow = page.getStartRow();
			this.endRow = page.getEndRow();
		} else {
			this.rows = rows;
		}
	}


	public String getShopActivityItemQuery() {
		return shopActivityItemQuery;
	}


	public void setShopActivityItemQuery(String shopActivityItemQuery) {
		this.shopActivityItemQuery = shopActivityItemQuery;
	}


	public String getShopActivityItemFilterQuery() {
		return shopActivityItemFilterQuery;
	}


	public void setShopActivityItemFilterQuery(String shopActivityItemFilterQuery) {
		this.shopActivityItemFilterQuery = shopActivityItemFilterQuery;
	}
}