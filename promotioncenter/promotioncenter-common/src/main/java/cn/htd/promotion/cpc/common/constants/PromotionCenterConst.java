package cn.htd.promotion.cpc.common.constants;

public class PromotionCenterConst {

	// 处理成功
	public static final String RETURN_SUCCESS = "00000";
	// 系统异常
	public static final String SYSTEM_ERROR = "99999";
	// *******************************************************
	// 通用返回码
	// *******************************************************
    // LOCK FAIL
	public static final String LOCK_FAIL_ERROR = "15000";
	// 入参不合法
	public static final String PARAMETER_ERROR = "15001";
	// 促销活动数据不存在
	public static final String PROMOTION_NOT_EXIST = "15002";
	// 促销活动已过有效期
	public static final String PROMOTION_HAS_EXPIRED = "15003";
	// 促销活动未过有效期
	public static final String PROMOTION_NOT_EXPIRED = "15004";
	// 促销活动未启用
	public static final String PROMOTION_NOT_VALID = "15005";
	// 促销活动状态不正确
	public static final String PROMOTION_STATUS_NOT_CORRECT = "15006";
	// 促销活动未开始
	public static final String PROMOTION_NO_START = "15007";
    // 促销活动已被修改
    public static final String PROMOTION_HAS_MODIFIED = "15008";
	// 促销规则数据不存在
	public static final String RULE_NOT_EXIST = "15011";
	// 促销规则数据名称重复
	public static final String RULE_NAME_REPEAT = "15012";
	// 会员信息不存在
	public static final String MEMBER_INFO_NOT_EXIST = "15009";

	// *******************************************************

	// *******************************************************
	// 秒杀返回码
	// *******************************************************
	// 秒杀活动结果数据不存在
	public static final String TIMELIMITED_RESULT_NOT_EXIST = "15201";
	// 秒杀活动参加结果更新失败
	public static final String TIMELIMITED_RESULT_UPDATE_ERROR = "15202";
	// 秒杀活动期间重复
	public static final String TIMELIMITED_DURING_REPEAT = "15204";
	// 该商品没有秒杀活动
	public static final String SKU_NO_TIMELIMITED = "15205";
	// 会员没有秒杀权限
	public static final String TIMELIMITED_BUYER_NO_AUTHIORITY = "15206";
	// 秒杀商品已抢光
	public static final String TIMELIMITED_SKU_NO_REMAIN = "15207";
	// 订单中存在秒杀活动的商品
	public static final String HAS_TIMELIMITED_SKU = "15210";
	// 订单中存在参加秒杀活动的商品和购买商品不符合
	public static final String TIMELIMITED_SKUCODE_ERROR = "15211";
	// 会员已经秒光限秒数量
	public static final String TIMELIMITED_BUYER_NO_COUNT = "15212";
	// 会员参加秒杀活动状态不正确
	public static final String BUYER_IMELIMITED_STATUS_ERROR = "15203";
	// 指定订单行提交锁定时没有预锁记录
	public static final String BUYER_TIMELIMITED_NO_PREREVERSE = "15213";
	// 会员参加秒杀活动信息不存在
	public static final String BUYER_TIMELIMITED_INFO_ERROR = "15214";
	// 会员下单秒杀活动有重复锁定记录
	public static final String BUYER_TIMELIMITED_DOUBLE_REVERSE = "15215";
	// 指定订单行参加秒杀活动无锁定记录
	public static final String BUYER_TIMELIMITED_NO_REVERSE = "15217";
	// 买家已参加该秒杀活动不能再次秒杀
	public static final String BUYER_HAS_TIMELIMITED_ERROR = "15218";
	//供应商秒杀活动设置不正确
	public static final String VMS_TIMELIMITED_RESULT_ERROR = "15304";
	// *******************************************************

}
