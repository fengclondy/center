package cn.htd.goodscenter.domain.productplus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class OuterChannelBrandMapping implements Serializable {
    private Long brandMappingId;
    @NotNull(message = "渠道编码不能为空")
    private String channelCode;
    @NotNull(message = "外接渠道品牌编码不能为空")
    private String outerChannelBrandCode;
    @NotNull(message = "外接渠道品牌名称不能为空")
    private String outerChannelBrandName;
    @NotNull(message = "品牌ID不能为空")
    private Long brandId;
    /** 冗余字段， 我司品牌  **/
    private String brandName;

    private Integer deleteFlag;
    @NotNull(message = "创建人ID不能为空")
    private Long createId;
    @NotNull(message = "创建人名称不能为空")
    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getBrandMappingId() {
        return brandMappingId;
    }

    public void setBrandMappingId(Long brandMappingId) {
        this.brandMappingId = brandMappingId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getOuterChannelBrandCode() {
        return outerChannelBrandCode;
    }

    public void setOuterChannelBrandCode(String outerChannelBrandCode) {
        this.outerChannelBrandCode = outerChannelBrandCode == null ? null : outerChannelBrandCode.trim();
    }

    public String getOuterChannelBrandName() {
        return outerChannelBrandName;
    }

    public void setOuterChannelBrandName(String outerChannelBrandName) {
        this.outerChannelBrandName = outerChannelBrandName == null ? null : outerChannelBrandName.trim();
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "OuterChannelBrandMapping{" +
                "brandMappingId=" + brandMappingId +
                ", channelCode='" + channelCode + '\'' +
                ", outerChannelBrandCode='" + outerChannelBrandCode + '\'' +
                ", outerChannelBrandName='" + outerChannelBrandName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
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