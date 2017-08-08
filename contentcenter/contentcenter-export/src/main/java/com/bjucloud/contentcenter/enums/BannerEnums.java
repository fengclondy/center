package com.bjucloud.contentcenter.enums;

/**
 * Created by thinkpad on 2016/11/8.
 * 主题类型
 */
public enum BannerEnums {

    BIGWHEEL(1,"1"),//大轮播
    LITTLEWHEEL(2,"2"),//小轮播
    MOBILE_HOME_TOP(3,"移动端首页顶部轮播图");


    BannerEnums(Integer value, String name){
        this.value=value;
        this.name=name;
    }

    private Integer value;

    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
