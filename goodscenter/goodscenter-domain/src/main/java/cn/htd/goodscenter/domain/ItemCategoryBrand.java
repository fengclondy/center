package cn.htd.goodscenter.domain;

import java.util.Date;

/**
 * <p>
 * Description: [类目品牌关联实体类]
 * </p>
 */
public class ItemCategoryBrand extends AbstractDomain {

	private Long categoryBrandId;// 类目品牌关联ID，表主键
	private Long secondLevCid;// 平台二级类目ID
	private Long thirdLevCid;// 平台三级类目ID
	private Long brandId;// 品牌ID
	private int sortNum;// 品牌在类目下的排序顺序，排序号越小，越靠前
	private int cbrandStatus;// 品牌在当前类目下是否有效：1、有效；2、无效
	private String brandName;// 品牌名称

	public Long getCategoryBrandId() {
		return categoryBrandId;
	}

	public void setCategoryBrandId(Long categoryBrandId) {
		this.categoryBrandId = categoryBrandId;
	}

	public Long getSecondLevCid() {
		return secondLevCid;
	}

	public void setSecondLevCid(Long secondLevCid) {
		this.secondLevCid = secondLevCid;
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

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public int getCbrandStatus() {
		return cbrandStatus;
	}

	public void setCbrandStatus(int cbrandStatus) {
		this.cbrandStatus = cbrandStatus;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
