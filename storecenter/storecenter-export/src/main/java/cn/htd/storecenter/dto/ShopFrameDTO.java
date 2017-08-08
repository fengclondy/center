package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * Description: [店铺装修框架]
 * </p>
 * 
 */
public class ShopFrameDTO implements Serializable {

	private static final long serialVersionUID = 5067482872361612952L;

	private Long id;

	/**
	 * 店铺ID
	 */
	private Long shopId;

	/**
	 * 店铺头部模块JSON
	 */
	private String headModulesJson;

	/**
	 * 店铺BODY布局JSON
	 */
	private String bodyLayoutsJson;

	/**
	 * 店铺页尾模块JSON
	 */
	private String footerModulesJson;

	/**
	 * 店铺主颜色
	 */
	private String color;

	/**
	 * 是否显示背景颜色
	 */
	private boolean showBgColor;

	/**
	 * 页面背景颜色
	 */
	private String bgColor;

	/**
	 * 背景图片
	 */
	private String bgImgUrl;

	/**
	 * 背景图片重复类型
	 */
	private String bgRepeat;

	/**
	 * 背景图像的对齐类型
	 */
	private String bgAlign;

	/**
	 * 版本类型：0 ：发布版， 1：编辑版，2： 历史版本
	 */
	private Integer versionType;

	private List<ShopModuleDTO> headModules;

	private List<ShopLayoutDTO> bodyLayouts;

	private List<ShopModuleDTO> footerModules;

	public ShopFrameDTO() {
		super();
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public List<ShopModuleDTO> getHeadModules() {
		return headModules;
	}

	public void setHeadModules(List<ShopModuleDTO> headModules) {
		this.headModules = headModules;
	}

	public List<ShopLayoutDTO> getBodyLayouts() {
		return bodyLayouts;
	}

	public void setBodyLayouts(List<ShopLayoutDTO> bodyLayouts) {
		this.bodyLayouts = bodyLayouts;
	}

	public List<ShopModuleDTO> getFooterModules() {
		return footerModules;
	}

	public void setFooterModules(List<ShopModuleDTO> footerModules) {
		this.footerModules = footerModules;
	}

	public String getHeadModulesJson() {
		return headModulesJson;
	}

	public void setHeadModulesJson(String headModulesJson) {
		this.headModulesJson = headModulesJson;
	}

	public String getBodyLayoutsJson() {
		return bodyLayoutsJson;
	}

	public void setBodyLayoutsJson(String bodyLayoutsJson) {
		this.bodyLayoutsJson = bodyLayoutsJson;
	}

	public String getColor() {
		return color;
	}

	public String getFooterModulesJson() {
		return footerModulesJson;
	}

	public void setFooterModulesJson(String footerModulesJson) {
		this.footerModulesJson = footerModulesJson;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isShowBgColor() {
		return showBgColor;
	}

	public void setShowBgColor(boolean showBgColor) {
		this.showBgColor = showBgColor;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getBgImgUrl() {
		return bgImgUrl;
	}

	public void setBgImgUrl(String bgImgUrl) {
		this.bgImgUrl = bgImgUrl;
	}

	public String getBgRepeat() {
		return bgRepeat;
	}

	public void setBgRepeat(String bgRepeat) {
		this.bgRepeat = bgRepeat;
	}

	public String getBgAlign() {
		return bgAlign;
	}

	public void setBgAlign(String bgAlign) {
		this.bgAlign = bgAlign;
	}

	public Long getId() {
		return id;
	}

	/**
	 * 版本类型：0 ：发布版， 1：编辑版，2： 历史版本
	 */
	public Integer getVersionType() {
		return versionType;
	}

	/**
	 * 版本类型：0 ：发布版， 1：编辑版，2： 历史版本
	 */
	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ShopFrameDTO [id=" + id + ", shopId=" + shopId + ", headModulesJson=" + headModulesJson
				+ ", bodyLayoutsJson=" + bodyLayoutsJson + ", footerModulesJson=" + footerModulesJson + ", color="
				+ color + ", showBgColor=" + showBgColor + ", bgColor=" + bgColor + ", bgImgUrl=" + bgImgUrl
				+ ", bgRepeat=" + bgRepeat + ", bgAlign=" + bgAlign + ", versionType=" + versionType + ", headModules="
				+ headModules + ", bodyLayouts=" + bodyLayouts + ", footerModules=" + footerModules + "]";
	}

}
