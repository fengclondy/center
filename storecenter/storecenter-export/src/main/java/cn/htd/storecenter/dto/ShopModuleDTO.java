package cn.htd.storecenter.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [店铺自定义装修中的模块 无DB （页面布局）]
 * </p>
 */
public class ShopModuleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 模块ID
	 */
	private Integer mId;

	/**
	 * 模块名称（用于回显）
	 */
	private String mName;

	/**
	 * 对应控件ID
	 */
	private String widgetId;

	/**
	 * 类型
	 */
	private String mType;

	/**
	 * 所在grid的类型 5e\3e\2e
	 */
	private String mWidth;

	public ShopModuleDTO() {
		super();
	}

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getWidgetId() {
		return widgetId;
	}

	public void setWidgetId(String widgetId) {
		this.widgetId = widgetId;
	}

	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	public String getmWidth() {
		return mWidth;
	}

	public void setmWidth(String mWidth) {
		this.mWidth = mWidth;
	}

	@Override
	public String toString() {
		return "Module [mId=" + mId + ", mName=" + mName + ", widgetId=" + widgetId + ", mType=" + mType + ", mWidth=" + mWidth + "]";
	}

}
