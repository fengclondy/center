package cn.htd.common.middleware;

/**
 * Created by GZG on 2016/12/29.
 */
public enum  MiddlewareResponseCodeEnum {

    NOT_EXIST("4001", "不存在"),
    NOT_EXIST_RELATE("4002","不存在关系"),
    EXIST_RELATE("1","存在关系");


    private String code;
    private String label;

    MiddlewareResponseCodeEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static MiddlewareResponseCodeEnum getEnumBycode(String code) {
        if (code != null) {
            for (MiddlewareResponseCodeEnum userType : MiddlewareResponseCodeEnum.values()) {
                if (userType.getCode() == code) {
                    return userType;
                }
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
