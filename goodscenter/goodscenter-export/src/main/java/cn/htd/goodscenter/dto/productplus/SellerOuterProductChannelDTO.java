package cn.htd.goodscenter.dto.productplus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 商家接入外接渠道DTO
 * @author chenkang
 */
public class SellerOuterProductChannelDTO implements Serializable {
    /**
     * pk
     */
    private Long accessId;
    /**
     * 卖家ID
     */
    private Long sellerId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 一个卖家接入所有渠道
     */
    private String channelCodes;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 接入状态
     */
    private Integer accessStatus;
    /**
     * 接入时间
     */
    private Date accessTime;

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public Integer getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(Integer accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String toString() {
        return "SellerOuterProductChannelDTO{" +
                "accessId=" + accessId +
                ", sellerId=" + sellerId +
                ", companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", channelCodes=" + channelCodes +
                ", channelName='" + channelName + '\'' +
                ", accessStatus=" + accessStatus +
                ", accessTime=" + accessTime +
                ", createId=" + createId +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", modifyId=" + modifyId +
                ", modifyName='" + modifyName + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }

    public String getChannelCodes() {
        return channelCodes;
    }

    public void setChannelCodes(String channelCodes) {
        this.channelCodes = channelCodes;
    }
}