package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [广告点击量 报表类输入参数dto]
 * </p>
 */
public class AdReportInDTO implements Serializable {

	private static final long serialVersionUID = 3527613047270244097L;
	private Long adCount;// 广告链接点击次数
	private String clickDate;// 统计日期

	private String mallAdName;// 广告名称
	private String mallAdType;// 广告类型

	private String clickDateBegin;// 开始时间
	private String clickDateEnd;// 结束时间

	private String dateFormat; // 不传默认为yyyyMMdd

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Long getAdCount() {
		return adCount;
	}

	public void setAdCount(Long adCount) {
		this.adCount = adCount;
	}

	public String getClickDate() {
		return clickDate;
	}

	public void setClickDate(String clickDate) {
		this.clickDate = clickDate;
	}

	public String getMallAdName() {
		return mallAdName;
	}

	public void setMallAdName(String mallAdName) {
		this.mallAdName = mallAdName;
	}

	public String getMallAdType() {
		return mallAdType;
	}

	public void setMallAdType(String mallAdType) {
		this.mallAdType = mallAdType;
	}

	public String getClickDateBegin() {
		return clickDateBegin;
	}

	public void setClickDateBegin(String clickDateBegin) {
		this.clickDateBegin = clickDateBegin;
	}

	public String getClickDateEnd() {
		return clickDateEnd;
	}

	public void setClickDateEnd(String clickDateEnd) {
		this.clickDateEnd = clickDateEnd;
	}

}
