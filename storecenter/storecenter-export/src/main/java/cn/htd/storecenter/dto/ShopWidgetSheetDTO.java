package cn.htd.storecenter.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [店铺自定义装修中的单一控件中的单条数据]
 * </p>
 */
public class ShopWidgetSheetDTO implements Serializable {

	private static final long serialVersionUID = 4043164873351304045L;

	/**
	 * 框架ID
	 */
	private Long frameId;

	/**
	 * 控件id
	 */
	private String widgetId;

	/**
	 * 图片地址
	 */
	private String picUrl;

	/**
	 * 跳转地址
	 */
	private String chainUrl;

	/**
	 * 内容-用于自定义
	 */
	private String txt;

	/**
	 * 排序号
	 */
	private Integer orderNo;

	/**
	 * 关联其他实体ID
	 */
	private Long itemId;

	private Object item;

	public ShopWidgetSheetDTO() {
		super();
	}

	public Long getFrameId() {
		return frameId;
	}

	public void setFrameId(Long frameId) {
		this.frameId = frameId;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getChainUrl() {
		return chainUrl;
	}

	public void setChainUrl(String chainUrl) {
		this.chainUrl = chainUrl;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
