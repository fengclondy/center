package com.bjucloud.contentcenter.common.enmu;

public class GlobalConstant {

	public static final int VALID_FLAG_YES = 0;

	public static final int VALID_FLAG_NO = 1;

	public static final int DELETED_FLAG_YES = 1;

	public static final int DELETED_FLAG_NO = 0;

	public static final int IS_VMS_INNER_USER_YES = 1;

	public static final int IS_VMS_INNER_USER_NO = 0;

	public static final int IS_CUSTOMER_MANAGER_YES = 1;

	public static final int IS_CUSTOMER_MANAGER_NO = 0;

	public static final boolean OPERATE_FLAG_SUCCESS = true;

	public static final boolean OPERATE_FLAG_FAIL = false;

	public static final Integer ERP_ADD = 0;// 会员新增下行ERP

	public static final Integer ERP_MODIFY = 1;// 会员新增下行ERP

	public static final Boolean SEND_ERP = true;// 是否下行ERP

	public static final Boolean SEND_PAY = false;// 是否下行支付

	public static final String INFO_TYPE_VERIFY = "11";// 会员运营审核

	public static final String ERP_STATUS_SEND_STATUS = "2";// ERP成功状态

	public static final String NET_PROPERTY_BUYYER = "1";// 进销属性，买家

	public static final String NET_PROPERTY_SELLER = "0";// 进销属性，供应商

	public static final String INFO_TYPE_ERP_ADD = "5";// 会员新增下行ERP
	public static final String INFO_TYPE_ERP_MODIFY = "6";// 会员更新下行ERP
	public static final String INFO_TYPE_DEFAULT = "0";// 默认信息修改类型
	public static final String INFO_TYPE_VERIFY_COOPERATE = "12";// 供应商审核
	public static final String INFO_TYPE_MEMBER_MODIFY = "15";// 会员信息修改审核
	public static final String INFO_TYPE_MEMBER_BACKUP_MODIFY = "16";// 会员金融信息修改审核
	public static final String INFO_TYPE_UNMEMBER_VERIFY = "13";// 非会员注册审核

	public static final String VERIFY_STATUS_WAIT = "1";// 待审核状态
	public static final String VERIFY_STATUS_REFUZE = "3";// 审核驳回
	public static final String VERIFY_STATUS_ACCESS = "2";// 审核通过
	public static final String DEFAULT_MEMBER_COOPERATE = "0801";// 默认供应商

	public static final String DEFAULT_ERP_AREA_CODE = "90010101";// 默认ERP地区代码
	public static final String DEFAULT_ERP_SELLER_TYPE = "51";// 默认ERP供应商类型
	public static final String DEFAULT_ERP_CURRENCY_TYPE = "01";// 默认ERP币种
	public static final String DEFAULT_ERP_SELLER_BUYER_TYPE = "1501";// 默认ERP客商类型

	public static final String IS_BUYER = "1";// 买家

	public static final String IS_SELLER = "2";// 卖家

	public static final String INER_SELLER_TYPE = "1";// 买家

	public static final String OUTER_SELLER_TYPE = "2";// 买家

	public static final int FLAG_YES = 1;// 是标志

	public static final int FLAG_NO = 0;// 否标志

	public static final Long MAXIMPORT_EXPORT_COUNT = Long.valueOf(10000);// 会员运营审核

	public static final String ERP_DEAL_STR = "0000000000";

	public static final int SUCCESS = 1;

	public static final int FAILURE = 0;

	public static final String ERP_DEAL_STR_SPIL = "@";

	public static final int IS_CENTER_STORE_YES = 1;

	public static final int IS_CENTER_STORE_NO = 0;

	public static final String STATUS_YES = "1";

	public static final String STATUS_NO = "0";

	public static final Long NULL_DEFAUL_VALUE = Long.valueOf(0);

	public static final int RECORD_TYPE_MEMBER_ID = 1;

	// 会员等级规则变更操作
	public static final String MEMBER_SCORE_SET_OPERATE = "1";
	// 会员保底经验值变更操作
	public static final String MEMBER_SCORE_RULE_OPERATE = "2";
	// 会员权重值变更操作
	public static final String MEMBER_SCORE_WEIGHT_OPERATE = "3";

	/**
	 * 系统管理员用户ID (程序默认登录时使用)
	 */
	public static final long ADMIN_USER_ID = 0;

	/**
	 * 根节点项目权限ID
	 */
	public static final String ROOT_PERMISSION_ID = "0";

	/**
	 * SSO用Ticket有效时间(分钟)
	 */
	public static final int TICKET_TIMEOUT_MINUTES = 30;

	/**
	 * 最大允许登录失败次数
	 */
	public static final int MAX_LOGIN_FAILED_COUNT = 5;
}
