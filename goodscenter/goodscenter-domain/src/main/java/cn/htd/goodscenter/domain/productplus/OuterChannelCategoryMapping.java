package cn.htd.goodscenter.domain.productplus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class OuterChannelCategoryMapping implements Serializable {
    private Long categoryMappingId;
    @NotNull(message = "渠道编码不能为空")
    private String channelCode;
    @NotNull(message = "外接渠道类目编码不能为空")
    private String outerChannelCategoryCode;
    @NotNull(message = "外接渠道类目名称不能为空")
    private String outerChannelCategoryName;
    @NotNull(message = "类目ID不能为空")
    private Long categoryId;
    private Long firstCategoryId; // 一级类目id
    private Long secondCategoryId; // 二级类目id
    private String categoryName; // 类目名称
    private Integer deleteFlag;
    @NotNull(message = "创建人ID不能为空")
    private Long createId;
    @NotNull(message = "创建人名称不能为空")
    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;
    // 类目参数 ，冗余字段，非dto传入
    private Long[] categoryIdParam;

    public Long getCategoryMappingId() {
        return categoryMappingId;
    }

    public void setCategoryMappingId(Long categoryMappingId) {
        this.categoryMappingId = categoryMappingId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getOuterChannelCategoryCode() {
        return outerChannelCategoryCode;
    }

    public void setOuterChannelCategoryCode(String outerChannelCategoryCode) {
        this.outerChannelCategoryCode = outerChannelCategoryCode == null ? null : outerChannelCategoryCode.trim();
    }

    public String getOuterChannelCategoryName() {
        return outerChannelCategoryName;
    }

    public void setOuterChannelCategoryName(String outerChannelCategoryName) {
        this.outerChannelCategoryName = outerChannelCategoryName == null ? null : outerChannelCategoryName.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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
        this.createName = createName == null ? null : createName.trim();
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
        this.modifyName = modifyName == null ? null : modifyName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long[] getCategoryIdParam() {
        return categoryIdParam;
    }

    public void setCategoryIdParam(Long[] categoryIdParam) {
        this.categoryIdParam = categoryIdParam;
    }

    @Override
    public String toString() {
        return "OuterChannelCategoryMapping{" +
                "categoryMappingId=" + categoryMappingId +
                ", channelCode='" + channelCode + '\'' +
                ", outerChannelCategoryCode='" + outerChannelCategoryCode + '\'' +
                ", outerChannelCategoryName='" + outerChannelCategoryName + '\'' +
                ", categoryId=" + categoryId +
                ", firstCategoryId=" + firstCategoryId +
                ", secondCategoryId=" + secondCategoryId +
                ", categoryName='" + categoryName + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", modifyId=" + modifyId +
                ", modifyName='" + modifyName + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}