package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class ItemCategoryDTO implements Serializable {
	private static final long serialVersionUID = 2990627565835351070L;
	private Long categoryCid; // 类目id
	@NotNull(message = "父级ID不能为空")
	private Long categoryParentCid; // 父级ID
	private Long[] categoryParentCids; // 父级ID组
	@NotNull(message = "类目名称不能为空")
	private String categoryCName; // 类目名称
	@NotNull(message = "类目级别不能为空")
	private Integer categoryLev; // 类目级别 1：一级；2：二级；3:三级
	private Integer categoryStatus; // 1:有效;0:删除
	@NotNull(message = "是否叶子节点不能为空")
	private Integer categoryHasLeaf; // 是否叶子节点 1:是；0：不是
	@NotNull(message = "排序号不能为空")
	private Integer categorySortNumber;// 排序号
	@NotNull(message = "是否前台首页展示不能为空")
	private Integer categoryHomeShow; // 是否前台首页展示,1:是;0:否
	@NotNull(message = "创建人ID不能为空")
	private Long categoryCreateId; // 创建人ID
	@NotNull(message = "创建名称不能为空")
	private String categoryCreateName; // 创建名称
	private boolean flag; // 是否只编辑三级类目图片标志 true:是，false：否
	private String categoryPicUrl; // 三级类目图片地址
	private Date categoryCreateTime; // 创建日期
	private Long categoryModifyId; // 创建人ID
	private String categoryModifyName; // 修改日期
	private Date categoryModifyTime; // 修改日期

	public Long[] getCategoryParentCids() {
		return categoryParentCids;
	}

	public void setCategoryParentCids(Long[] categoryParentCids) {
		this.categoryParentCids = categoryParentCids;
	}

	public Long getCategoryCid() {
		return categoryCid;
	}

	public void setCategoryCid(Long categoryCid) {
		this.categoryCid = categoryCid;
	}

	public Long getCategoryParentCid() {
		return categoryParentCid;
	}

	public void setCategoryParentCid(Long categoryParentCid) {
		this.categoryParentCid = categoryParentCid;
	}

	public String getCategoryCName() {
		return categoryCName;
	}

	public void setCategoryCName(String categoryCName) {
		this.categoryCName = categoryCName;
	}

	public Integer getCategoryLev() {
		return categoryLev;
	}

	public void setCategoryLev(Integer categoryLev) {
		this.categoryLev = categoryLev;
	}

	public Integer getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(Integer categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public Integer getCategoryHasLeaf() {
		return categoryHasLeaf;
	}

	public void setCategoryHasLeaf(Integer categoryHasLeaf) {
		this.categoryHasLeaf = categoryHasLeaf;
	}

	public Integer getCategorySortNumber() {
		return categorySortNumber;
	}

	public void setCategorySortNumber(Integer categorySortNumber) {
		this.categorySortNumber = categorySortNumber;
	}

	public Integer getCategoryHomeShow() {
		return categoryHomeShow;
	}

	public void setCategoryHomeShow(Integer categoryHomeShow) {
		this.categoryHomeShow = categoryHomeShow;
	}

	public Long getCategoryCreateId() {
		return categoryCreateId;
	}

	public void setCategoryCreateId(Long categoryCreateId) {
		this.categoryCreateId = categoryCreateId;
	}

	public String getCategoryCreateName() {
		return categoryCreateName;
	}

	public void setCategoryCreateName(String categoryCreateName) {
		this.categoryCreateName = categoryCreateName;
	}

	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * @return the categoryPicUrl
	 */
	public String getCategoryPicUrl() {
		return categoryPicUrl;
	}

	/**
	 * @param categoryPicUrl
	 *            the categoryPicUrl to set
	 */
	public void setCategoryPicUrl(String categoryPicUrl) {
		this.categoryPicUrl = categoryPicUrl;
	}

	public Date getCategoryCreateTime() {
		return categoryCreateTime;
	}

	public void setCategoryCreateTime(Date categoryCreateTime) {
		this.categoryCreateTime = categoryCreateTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getCategoryModifyId() {
		return categoryModifyId;
	}

	public void setCategoryModifyId(Long categoryModifyId) {
		this.categoryModifyId = categoryModifyId;
	}

	public String getCategoryModifyName() {
		return categoryModifyName;
	}

	public void setCategoryModifyName(String categoryModifyName) {
		this.categoryModifyName = categoryModifyName;
	}

	public Date getCategoryModifyTime() {
		return categoryModifyTime;
	}

	public void setCategoryModifyTime(Date categoryModifyTime) {
		this.categoryModifyTime = categoryModifyTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ItemCategoryDTO [categoryCid=" + categoryCid + ", categoryParentCid=" + categoryParentCid
				+ ", categoryParentCids=" + Arrays.toString(categoryParentCids) + ", categoryCName=" + categoryCName
				+ ", categoryLev=" + categoryLev + ", categoryStatus=" + categoryStatus + ", categoryHasLeaf="
				+ categoryHasLeaf + ", categorySortNumber=" + categorySortNumber + ", categoryHomeShow="
				+ categoryHomeShow + ", categoryCreateId=" + categoryCreateId + ", categoryCreateName="
				+ categoryCreateName + ", flag=" + flag + ", categoryPicUrl=" + categoryPicUrl + ", categoryCreateTime="
				+ categoryCreateTime + ", categoryModifyId=" + categoryModifyId + ", categoryModifyName="
				+ categoryModifyName + ", categoryModifyTime=" + categoryModifyTime + "]";
	}

}
