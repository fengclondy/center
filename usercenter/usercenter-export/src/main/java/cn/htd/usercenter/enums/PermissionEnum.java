package cn.htd.usercenter.enums;

public enum PermissionEnum {

    MENU("1", "菜单"), PAGE("2", "页面"), FUNCTION("3", "功能");

    private String code;
    private String value;

    PermissionEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static PermissionEnum getEnumByCode(String code) {
        if (code != null) {
            for (PermissionEnum permissionType : PermissionEnum.values()) {
                if (code.equals(permissionType.getCode())) {
                    return permissionType;
                }
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
