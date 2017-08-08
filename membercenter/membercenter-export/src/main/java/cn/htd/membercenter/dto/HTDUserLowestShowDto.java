package cn.htd.membercenter.dto;

import java.io.Serializable;

public class HTDUserLowestShowDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean showFlg;

	private Double lowestPoint;

	private Double pointDistance;

	public boolean isShowFlg() {
		return showFlg;
	}

	public void setShowFlg(boolean showFlg) {
		this.showFlg = showFlg;
	}

	public Double getLowestPoint() {
		return lowestPoint;
	}

	public void setLowestPoint(Double lowestPoint) {
		this.lowestPoint = lowestPoint;
	}

	public Double getPointDistance() {
		return pointDistance;
	}

	public void setPointDistance(Double pointDistance) {
		this.pointDistance = pointDistance;
	}

}
