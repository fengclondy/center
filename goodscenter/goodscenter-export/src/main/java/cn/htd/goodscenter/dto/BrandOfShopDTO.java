package cn.htd.goodscenter.dto;

import java.io.Serializable;

/**
 * <p>
 * Description: [根据类目查询品牌]
 * </p>
 */
public class BrandOfShopDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long secondCid; // 二级类目ID
	private Long thirdCid; // 三级类目ID
	private Long shopId; // 店铺ID
	private boolean isIgnoreErpStatus; // 是否忽略下行erp的状态

	public Long getSecondCid() {
		return secondCid;
	}

	public void setSecondCid(Long secondCid) {
		this.secondCid = secondCid;
	}

	public Long getThirdCid() {
		return thirdCid;
	}

	public void setThirdCid(Long thirdCid) {
		this.thirdCid = thirdCid;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public boolean getIsIgnoreErpStatus() {
		return isIgnoreErpStatus;
	}

	public void setIsIgnoreErpStatus(boolean isIgnoreErpStatus) {
		this.isIgnoreErpStatus = isIgnoreErpStatus;
	}

}
