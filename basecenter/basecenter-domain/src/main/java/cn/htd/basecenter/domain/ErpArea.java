package cn.htd.basecenter.domain;

/**
 * 基础省市县编码表
 */
public class ErpArea {

	private String areaCode;

	private String areaName;

	private String areaFatherCode;

	private String isLastLevel;

	private int isUpdateFlag;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaFatherCode() {
		return areaFatherCode;
	}

	public void setAreaFatherCode(String areaFatherCode) {
		this.areaFatherCode = areaFatherCode;
	}

	public String getIsLastLevel() {
		return isLastLevel;
	}

	public void setIsLastLevel(String isLastLevel) {
		this.isLastLevel = isLastLevel;
	}

	public int getIsUpdateFlag() {
		return isUpdateFlag;
	}

	public void setIsUpdateFlag(int isUpdateFlag) {
		this.isUpdateFlag = isUpdateFlag;
	}

}
