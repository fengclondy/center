package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Description: [品牌]
 * </p>
 */
public class ItemBrand extends AbstractDomain implements Serializable{

	private Long brandId;// 品牌ID

	private String brandName;// 品牌名称

	private String brandLogoUrl;// 品牌logo地址

	private Integer brandStatus;// 品牌状态：1、有效；0、无效

	private String brandKey; // 品牌首字母

	private String erpStatus; // erp下行状态

	private Date erpDownTime; // erp下行时间

	private String erpErrorMsg; //

	public String getBrandName() {
		return brandName;
	}

	public String getBrandKey() {
		return brandKey;
	}

	public void setBrandKey(String brandKey) {
		this.brandKey = brandKey;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandLogoUrl() {
		return brandLogoUrl;
	}

	public void setBrandLogoUrl(String brandLogoUrl) {
		this.brandLogoUrl = brandLogoUrl;
	}

	public Integer getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(Integer brandStatus) {
		this.brandStatus = brandStatus;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getErpStatus() {
		return erpStatus;
	}

	public void setErpStatus(String erpStatus) {
		this.erpStatus = erpStatus;
	}

	public Date getErpDownTime() {
		return erpDownTime;
	}

	public void setErpDownTime(Date erpDownTime) {
		this.erpDownTime = erpDownTime;
	}

	public String getErpErrorMsg() {
		return erpErrorMsg;
	}

	public void setErpErrorMsg(String erpErrorMsg) {
		this.erpErrorMsg = erpErrorMsg;
	}

	@Override
	public String toString() {
		return "ItemBrand{" +
				"brandId=" + brandId +
				", brandName='" + brandName + '\'' +
				", brandLogoUrl='" + brandLogoUrl + '\'' +
				", brandStatus=" + brandStatus +
				", brandKey='" + brandKey + '\'' +
				", erpStatus='" + erpStatus + '\'' +
				", erpDownTime=" + erpDownTime +
				", erpErrorMsg='" + erpErrorMsg + '\'' +
				'}';
	}
}
