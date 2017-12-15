package cn.htd.goodscenter.common.constants;

/**
 * 结果码枚举
 * 商品中心 : 12000-12999
 */
public enum ResultCodeEnum {

    SUCCESS("00000", "成功"),

    // INPUT
    INPUT_PARAM_IS_NULL("12001", "入参为空"),

    INPUT_PARAM_IS_ILLEGAL("12002", "入参非法"),

    // OUTPUT
    OUTPUT_IS_NULL("12011", "返回结果为空"),

    // 库存
    STOCK_IN_PARAM_IS_ILLEGAL("12101", "库存入参非法"),

    STOCK_PUBLISH_INFO_IS_NULL("12102", "库存信息不存在"),

    STOCK_AVAILABLE_STOCK_NOT_ENOUGH("12103", "可卖库存不足"),

    STOCK_DISPLAY_STOCK_NOT_ENOUGH("12104", "可见库存不足"),

    STOCK_RESERVE_STOCK_NOT_ENOUGH("12105", "锁定库存不足"),

    STOCK_NO_RESVER_REOCRD("12106", "此操作之前没有锁定库存, 请先锁定库存"),

    STOCK_NO_REDUCE_REOCRD("12107", "此操作之前没有扣减库存，请先扣减库存"),

    STOCK_OFF_SHELF_FALI("12108", "下架失败"),

    ERROR("99999", "系统错误");



    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
