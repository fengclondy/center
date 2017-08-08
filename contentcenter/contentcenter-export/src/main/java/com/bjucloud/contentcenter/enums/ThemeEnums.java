package com.bjucloud.contentcenter.enums;

/**
 * Created by thinkpad on 2016/11/8.
 * 主题类型
 */
public enum ThemeEnums {

    HOME(1,"首页"),
    CATEGORY(2,"类目"),
    AREA(3,"地区"),
    HOME_MOBILE(5,"移动端首页");

    ThemeEnums(Integer value,String name){
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
