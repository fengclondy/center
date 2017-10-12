package cn.htd.promotion.cpc.dto.request;

import java.io.Serializable;
import java.util.List;

public class BaseImageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主图，背景
	 */
	private String mainImageUrl;

	private int height;

	private int width;

	private List<BaseImageSubDTO> subImageList;

	public String getMainImageUrl() {
		return mainImageUrl;
	}

	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<BaseImageSubDTO> getSubImageList() {
		return subImageList;
	}

	public void setSubImageList(List<BaseImageSubDTO> subImageList) {
		this.subImageList = subImageList;
	}

}
