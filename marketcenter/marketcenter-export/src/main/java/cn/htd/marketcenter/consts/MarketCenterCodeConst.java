package cn.htd.marketcenter.consts;

public class MarketCenterCodeConst {

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
	// 优惠券返回码
	// *******************************************************
	// 会员优惠券信息不存在
	public static final String COUPON_NOT_EXIST = "15101";
	// 非会员领取优惠券信息
	public static final String COUPON_NOT_MEMBER_COLLECT = "15102";
	// 会员领取优惠券的领取期间还未开始
	public static final String COUPON_COLLECT_NOT_START = "15103";
	// 会员领取优惠券的领取期间已过期
	public static final String COUPON_COLLECT_HAS_EXPIRED = "15104";
	// 会员优惠券已领光
	public static final String COUPON_TOTAL_COLLECTED = "15105";
	// 会员已到优惠券领取上限数量
	public static final String COUPON_RECEIVE_LIMITED = "15106";
	// 会员优惠券不能被使用
	public static final String BUYER_COUPON_NO_USE = "15107";
	// 会员优惠券已过期
	public static final String BUYER_COUPON_HAS_EXPIRED = "15108";
	// 会员优惠券余额不足
	public static final String BUYER_COUPON_BALANCE_DEFICIENCY = "15109";
	// 会员购买商品不满足优惠券使用规则
	public static final String BUYER_COUPON_NO_AVALIBLE_PRODUCTS = "15110";
	// 会员优惠券可优惠金额为0
	public static final String BUYER_COUPON_CANNOT_DISCOUNT = "15111";
	// 会员优惠券为可用或锁定不能被删除
	public static final String BUYER_COUPON_CANNOT_DELETE = "15112";
	// 指定订单行所用优惠券已处理了不同的金额
	public static final String BUYER_COUPON_DEAL_DIFF_NONEY = "15113";
	// 指定订单行所用优惠券记录重复锁定
	public static final String BUYER_COUPON_DOUBLE_REVERSE = "15114";
	// 指定订单行所用优惠券无锁定记录
	public static final String BUYER_COUPON_NO_REVERSE = "15115";
	// 自动发券时根据等级取得发送对象会员列表时失败
	public static final String COUPON_AUTO_PRESENT_NO_MEMBER = "15116";
	// 没有对应的触发返券的促销活动
	public static final String COUPON_NO_TRIGGER_SEND = "15117";
	// 会员优惠券数据异常
	public static final String BUYER_COUPON_DATA_ERROR = "15118";
	// 会员优惠券使用记录状态异常
	public static final String BUYER_COUPON_STATUS_ERROR = "15119";
	// 会员没有参加领券活动的权限
	public static final String COUPON_BUYER_NO_AUTHIORITY = "15120";
	// 会员优惠券已失效
    public static final String BUYER_COUPON_STATUS_INVALID = "15121";

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
	// 团购返回码
	// *******************************************************

	// 团购限制买家购买数量
	public static final int GROUP_LIMIT_PRODUCT_NUM = 5;
	


}
