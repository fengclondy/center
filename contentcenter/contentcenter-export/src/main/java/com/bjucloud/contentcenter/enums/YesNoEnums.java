/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName: 	YesNoEnum.java
 * Author:   	jiangkun
 * Date:     	2018年1月9日
 * Description: 是/否/异常 枚举
 */
package com.bjucloud.contentcenter.enums;

/**
 * 是/否/异常 枚举
 */
public enum YesNoEnums {

    NO("否", 0), YES("是", 1);

    private String name;
    private int value;

    YesNoEnums(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    // 获取枚举value对应名字方法
    public static String getName(int value) {
        for (YesNoEnums yesNoEnum : YesNoEnums.values()) {
            if (value == yesNoEnum.getValue()) {
                return yesNoEnum.name;
            }
        }
        return "";
    }
}
