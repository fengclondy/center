package com.bjucloud.contentcenter.enums;

/**
 * Created by thinkpad on 2016/11/8.
 * 楼层广告类型
 */
public enum FloorAdviertisementEnums {

    AD(1, "广告"),
    SLIDER(2,"楼层通栏广告");


    FloorAdviertisementEnums(Integer value, String name){
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
