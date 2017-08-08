package cn.htd.goodscenter.dto.stock;

/**
 * 库存变动类型
 * @author chenkang
 */
public enum StockTypeEnum {
    /**
     * 锁定库存
     */
    RESERVE("RESERVE"),
    /**
     * 释放库存
     */
    RELEASE("RELEASE"),
    /**
     * 扣减库存
     */
    REDUCE("REDUCE"),
    /**
     * 回滚库存
     */
    ROLLBACK("ROLLBACK");

    private String type;

    StockTypeEnum(String type) {
        this.type = type;
    }
}
