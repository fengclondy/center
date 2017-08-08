package com.bjucloud.contentcenter.enums;

/**
 * Created by thinkpad on 2016/11/8.
 * 广告类型
 */
public enum AdviertisementEnums {

    THEME(1, "主题广告"),
    LOGIN(2, "登录广告"),
    HEAD(3, "头部广告"),
    CATEGORY(4, "类目广告"),
    HOTFEWIMG(5, "热销小图"),
    HOTBIGIMG(6, "热销大图"),
    CREDITS(7, "积分广告"),
    MOBILE_QUICK_NAV(8,"移动端快速导航"),
    MOBILE_THEME(9,"移动端主题广告"),
    MOBILE_GOODS(10,"移动端推荐商品");

    AdviertisementEnums(Integer value, String name){
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
