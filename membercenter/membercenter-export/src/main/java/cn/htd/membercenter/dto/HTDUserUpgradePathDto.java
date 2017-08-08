package cn.htd.membercenter.dto;

import java.io.Serializable;

public class HTDUserUpgradePathDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HTDUserUpgradeDto userUpgradeDto;

	private int iPos;

	private int jPos;

	private int weight;

	public HTDUserUpgradeDto getUserUpgradeDto() {
		return userUpgradeDto;
	}

	public void setUserUpgradeDto(HTDUserUpgradeDto userUpgradeDto) {
		this.userUpgradeDto = userUpgradeDto;
	}

	public int getiPos() {
		return iPos;
	}

	public void setiPos(int iPos) {
		this.iPos = iPos;
	}

	public int getjPos() {
		return jPos;
	}

	public void setjPos(int jPos) {
		this.jPos = jPos;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public boolean equals(Object a) {
		HTDUserUpgradePathDto target = null;
		if (a instanceof HTDUserUpgradePathDto) {
			target = (HTDUserUpgradePathDto) a;
			if (target.getiPos() == this.iPos && target.getjPos() == this.jPos) {
				return true;
			}
		}
		return false;
	}
}
