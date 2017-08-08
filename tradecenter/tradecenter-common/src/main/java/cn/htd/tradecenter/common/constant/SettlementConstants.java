package cn.htd.tradecenter.common.constant;
/**
 * @author zhangzhifeng
 * @version 1.0.0
 * @since 2017年02月14日
 */
public final class SettlementConstants {
	
    /**
     * private constructor
     * Creates a new instance of StatusContants.
     */
    private SettlementConstants() { }
    
    
    // 排序修改时间字段名称
    public static final String SORT_NAME_MODIFYTIME = "o.modify_time";
    // 排序ASC(升序、从小到大)
    public static final String SORT_DIR_ASC = "ASC";
    // 排序DESC(降序、从大到小)
    public static final String SORT_DIR_DESC = "DESC";
    
    
    // 出结算单时间间隔【默认为7天】
    public static final String TIME_INTERVAL = "7";
    
    
    
    //cronJob调度任务
    public static final Long SYSTEM_CRON_ID = 10000001L;
    public static final String SYSTEM_CRON_NAME = "system_cron";
    
    
    //操作类型 1.job调度 2.运营系统管理员
    public static final String OPERATE_TYPE_JOB = "1";
    public static final String OPERATE_TYPE_SYSTEM = "2";
    
    
    //订单状态 10:待审核,20:待支付,21:审核通过待支付,31:已支付待拆单，32：已支付已拆单待下行ERP,40:待发货,50:已发货,61:买家收货,62:到期自动收货
    public static final String ORDER_STATUS_40 = "40";
    public static final String ORDER_STATUS_50 = "50";
    public static final String ORDER_STATUS_61 = "61";
    public static final String ORDER_STATUS_62 = "62";
    
    
    //是否是取消订单 0:未取消，1：已取消  
    public static final String IS_CANCEL_ORDER_0 = "0";
    public static final String IS_CANCEL_ORDER_1 = "1";
    
    //是否已结算 0：未结算，1：已结算，2：结算处理中
    public static final Integer IS_SETTLED_0 = 0;
    public static final Integer IS_SETTLED_1 = 1;
    public static final Integer IS_SETTLED_2 = 2;
    
    
	// 结算单执行计划任务
	public static final String SETTLEMENT_SCHEDULE_TASK = "settlementScheduleTask";
    // 运行标记
	public static final String RUN_FLAG = "runFlag";
	// 任务次数
	public static final String SETTLEMENT_RUN_COUNT = "settlementRunCount";
	// 运行状态：1.运行中，2.运行完成
	public static final String RUNING = "1";
	public static final String RUN_COMPLETE = "2";
	
    // 结算单状态(settlement_status)/商品渠道(product_channel)/结算单类型(settlement_type)/使用商品渠道(use_product_channel)
	public static final String CONST_TYPE_SETTLEMENT_STATUS = "settlement_status";
	public static final String CONST_TYPE_PRODUCT_CHANNEL = "product_channel";
	public static final String CONST_TYPE_SETTLEMENT_TYPE = "settlement_type";
	public static final String CONST_TYPE_USE_PRODUCT_CHANNEL = "use_product_channel";
	public static final String CONST_TYPE_ACCOUNTS_RECEIVABLE = "accounts_receivable";
	
	// 启用(enable)/停用(disable)
	public static final String SETTLEMENTCONST_STATUS_ENABLE = "1";
	public static final String SETTLEMENTCONST_STATUS_DISABLE = "0";
	
	
	// 成功(success)/失败(failed)
	public static final String SETTLEMENTCOMP_STATUS_SUCCESS = "1";
	public static final String SETTLEMENTCOMP_STATUS_TEXT_SUCCESS = "成功";
	public static final String SETTLEMENTCOMP_STATUS_FAILED = "0";
	public static final String SETTLEMENTCOMP_STATUS_TEXT_FAILED = "失败";
	
	//查询未结算单数量 1-成功  0-失败
	public static final String SETTLEMENT_COUNT_SUCCESS = "1";
	public static final String SETTLEMENT_COUNT_SUCCESS_TEXT = "成功";
	public static final String SETTLEMENT_COUNT_FAILED = "0";
	public static final String SETTLEMENT_COUNT_FAILED_TEXT = "失败";

	
}

