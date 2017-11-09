/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	DeleteFlagEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 删除标记枚举
 * History:
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.basecenter.common.enums;

/**
 * 梦网错误枚举
 *
 * @author jiangkun
 */
public enum MengWangErrorEnum {

    PARAM_ERROR("-1", "参数为空"),
    OVER_THOUNSAND_PHONE_NUM("-2", "电话号码个数超过1000"),
    MEMORY_ALLOCATE_ERROR("-10", "申请缓存空间失败"),
    PHONE_NUM_ERROR("-11", "电话号码中有非数字字符"),
    MOBILE_NUM_ERROR("-12", "有异常电话号码"),
    PHONE_COUNT_ERROR("-13", "电话号码个数与实际个数不相等"),
    OVER_THOUNSAND_PHONE_NUM2("-14", "实际号码个数超过1000"),
    WAIT_TIME_OUT("-101", "发送消息等待超时"),
    SEND_MSG_ERROR("-102", "发送或接收消息失败"),
    RECEIVE_MSG_TIMEOUT("-103", "接收消息超时"),
    OTHER_ERROR("-200", "其他错误"),
    WEB_SERVER_ERROR("-999", "web服务器内部错误"),
    ACCOUNT_LOGIN_FAIL("-10001", "用户登陆不成功"),
    NOT_ENOUGH_BALANCE("-10003", "用户余额不足"),
    CONTENT_TOO_LONG("-10011", "信息内容超长"),
    NO_AUTHORITY("-10029", "此用户没有权限从此通道发送信息"),
    SEND_MOBILE_ERROR("-10030", "不能发送移动号码"),
    ILLEGAL_MOBILE_NUM("-10031", "手机号码(段)非法"),
    FORBIDDEN_IP_ADDR("-10057", "IP受限"),
    CONNECT_OVER_LIMIT("-10056", "连接数超限");

    private String code;
    private String content;

    MengWangErrorEnum(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // 获取枚举content对应名字方法
    public static String getMengWangErrorContent(String code) {
        for (MengWangErrorEnum errorEnum : MengWangErrorEnum.values()) {
            if (code.equals(errorEnum.getCode())) {
                return errorEnum.content;
            }
        }
        return "";
    }
}
