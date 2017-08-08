package com.bjucloud.contentcenter.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FloorInfoDTO implements Serializable{
    private static final long serialVersionUID = 9193906134094015640L;
    private Long id;

    private String name;// 名称

    private String iconUrl;//图标地址

    private String leftPicTitle;

    private String leftPicUrl;//左侧主图

    private String leftPicLinkUrl;

    private Long sortNum;// 显示顺序

    private String status;//状态

    private String showBrand;//是否展示品牌

    private Long createId;

    private String createName;

    private Date createTime;

    private Long modifyId;

    private String modifyName;

    private Date modifyTime;

    private List<FloorContentSubDTO> subDTOList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public String getShowBrand() {
        return showBrand;
    }

    public void setShowBrand(String showBrand) {
        this.showBrand = showBrand;
    }

    public List<FloorContentSubDTO> getSubDTOList() {
        return subDTOList;
    }

    public void setSubDTOList(List<FloorContentSubDTO> subDTOList) {
        this.subDTOList = subDTOList;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLeftPicUrl() {
        return leftPicUrl;
    }

    public void setLeftPicUrl(String leftPicUrl) {
        this.leftPicUrl = leftPicUrl;
    }

    public String getLeftPicTitle() {
        return leftPicTitle;
    }

    public void setLeftPicTitle(String leftPicTitle) {
        this.leftPicTitle = leftPicTitle;
    }

    public String getLeftPicLinkUrl() {
        return leftPicLinkUrl;
    }

    public void setLeftPicLinkUrl(String leftPicLinkUrl) {
        this.leftPicLinkUrl = leftPicLinkUrl;
    }
}