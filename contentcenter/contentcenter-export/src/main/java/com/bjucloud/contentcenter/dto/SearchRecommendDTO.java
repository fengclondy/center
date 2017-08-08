package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkpad on 2017/3/7.
 */
public class SearchRecommendDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long createId;
    private String createName;
    private Date createTime;
    private Long modifyId;
    private String modifyName;
    private Date modifyTime;
    private List<SearchRecommendProductDTO> productDTOList;
    private List<SearchRecommendWordDTO> wordDTOList;

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
        this.name = name;
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

    public List<SearchRecommendProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<SearchRecommendProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    public List<SearchRecommendWordDTO> getWordDTOList() {
        return wordDTOList;
    }

    public void setWordDTOList(List<SearchRecommendWordDTO> wordDTOList) {
        this.wordDTOList = wordDTOList;
    }
}
