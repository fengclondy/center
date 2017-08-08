package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * 
 * <p>
 * Description: [广告点击量 报表类输出参数dto]
 * </p>
 */
public class AdReportOutDTO implements Serializable {
	private static final long serialVersionUID = 3930494349721168365L;
	private Long id;// id
	private Long mallAdId;// 广告id
	private Long adCount;// 广告链接点击次数
	private String clickDate;// 点击日期
	private String mallAdName;// 广告名称
	private String mallAdType;// 广告类型

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMallAdId() {
		return mallAdId;
	}

	public void setMallAdId(Long mallAdId) {
		this.mallAdId = mallAdId;
	}

	public Long getAdCount() {
		return adCount;
	}

	public void setAdCount(Long adCount) {
		this.adCount = adCount;
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

	public String getClickDate() {
		return clickDate;
	}

	public void setClickDate(String clickDate) {
		this.clickDate = clickDate;
	}

}
