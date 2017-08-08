package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

/**
 * Created by thinkpad on 2017/3/7.
 */
public class SearchRecommendWordDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long recommendWordId;//搜索推荐
    private String hotWord;//品牌名称

    public Long getRecommendWordId() {
        return recommendWordId;
    }

    public void setRecommendWordId(Long recommendWordId) {
        this.recommendWordId = recommendWordId;
    }

    public String getHotWord() {
        return hotWord;
    }

    public void setHotWord(String hotWord) {
        this.hotWord = hotWord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
