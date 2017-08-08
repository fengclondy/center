package cn.htd.basecenter.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [基础省市区镇编码表]
 * </p>
 */
public class AddressInfo implements Serializable {

	private static final long serialVersionUID = -7667606312154895090L;
	/**
	 * 省市区镇编码
	 */
	private String code = "";
	/**
	 * 地址名称
	 */
	private String name = "";
	/**
	 * 等级:1省 2市 3县/县级市/区 4镇/街道
	 */
	private int level;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
