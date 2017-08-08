package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Description: [店铺自定义装修中的单一控件]
 * </p>
 */
public class ShopWidgetDTO implements Serializable {

	private static final long serialVersionUID = -764615779067889323L;

	/**
	 * 框架ID
	 */
	private Long frameId;

	/**
	 * 控件id
	 */
	private String widgetId;

	/**
	 * 宽度
	 */
	private String width;

	/**
	 * 是否显示标题
	 */
	private Boolean showTitle;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 布局样式
	 */
	private String layout;

	/**
	 * 用于存储其他值
	 */
	private String val;

	private List<ShopWidgetSheetDTO> sheets;

	public ShopWidgetDTO() {
		super();
	}

	public ShopWidgetDTO(String widgetId, List<ShopWidgetSheetDTO> sheets) {
		super();
		this.widgetId = widgetId;
		this.sheets = sheets;
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

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Boolean getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(Boolean showTitle) {
		this.showTitle = showTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public List<ShopWidgetSheetDTO> getSheets() {
		return sheets;
	}

	public void setSheets(List<ShopWidgetSheetDTO> sheets) {
		this.sheets = sheets;
	}

}
