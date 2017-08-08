package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * Description: [商品品牌]
 * </p>
 */
public class ItemBrandDTO implements Serializable {

	private static final long serialVersionUID = 2464215588527124822L;

	private Long brandId;// 品牌ID

	private String brandName;// 品牌名称

	private String brandLogoUrl;// 品牌logo地址

	private Integer brandStatus;// 品牌状态：1、有效；2、无效

	private String brandKey; // 品牌首字母

	private String erpStatus; // ERP状态

	private Date erpDownTime; // ERP下行时间

	private String erpErrorMsg; // ERP下行信息

	private Long thirdLevCid;// 平台三级类目ID

	private Long[] brandIds;// 批量选定的品牌ID 在添加品牌的时候用到

	private Long createId;//创建人ID

	private String createName;//创建人名称

	private Date createTime;//创建时间

	private Long modifyId;//修改人ID

	private String modifyName;//修改人名称

	private Date modifyTime;//修改时间

	private String categoryName;

	public String getBrandKey() {
		return brandKey;
	}

	public void setBrandKey(String brandKey) {
		this.brandKey = brandKey;
	}

	public Long getThirdLevCid() {
		return thirdLevCid;
	}

	public void setThirdLevCid(Long thirdLevCid) {
		this.thirdLevCid = thirdLevCid;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
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

	public Long[] getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(Long[] brandIds) {
		this.brandIds = brandIds;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
}
