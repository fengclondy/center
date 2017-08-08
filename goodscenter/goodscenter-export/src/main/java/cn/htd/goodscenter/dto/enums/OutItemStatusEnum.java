package cn.htd.goodscenter.dto.enums;

/**
 * 外接商品状态
 */
public enum OutItemStatusEnum {
    NOT_SHELVES("1", "未上架"),
    SHELVED("2", "已上架");

    private String code;
    private String label;

    OutItemStatusEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static OutItemStatusEnum getEnumByCode(Integer code) {
        if (code != null) {
            for (OutItemStatusEnum userType : OutItemStatusEnum.values()) {
                if (userType.getCode().equals(code)) {
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
