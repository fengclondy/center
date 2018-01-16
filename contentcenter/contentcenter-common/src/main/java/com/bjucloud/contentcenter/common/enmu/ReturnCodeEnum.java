/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName: 	ReturnCodeEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 处理返回值枚举
 */
package com.bjucloud.contentcenter.common.enmu;

/**
 * 处理返回值枚举
 */
public enum ReturnCodeEnum {

    RETURN_SUCCESS("00000", "处理完成"),
    SYSTEM_ERROR("99999", "系统异常"),
    PARAMETER_ERROR("99001", "入参错误"),
    PARAMETER_NOT_NULL("99002", "入参不能为空"),
    PARAMETER_TYPE_ERROR("99002", "入参类型错误"),
    PARAMETER_VALUE_ERROR("99003", "入参值错误"),
    AD_NOT_EXISTS("25001", "弹屏广告不存在"),
    AD_HAS_DELETED("25002", "弹屏广告已删除"),
    AD_PERIOD_REPEAT("25003", "相同终端在同一有效期内存在重叠弹屏活动")


    ;

    private String code;
    private String desc;

    ReturnCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    // 获取枚举对应名字方法
    public static String getDesc(String code) {
        for (ReturnCodeEnum enumObj : ReturnCodeEnum.values()) {
            if (code.equals(enumObj.code)) {
                return enumObj.desc;
            }
        }
        return "";
    }
}
