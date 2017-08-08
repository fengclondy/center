package cn.htd.usercenter.common.constant;

/**
 * @author Administrator
 *
 */
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

	/**
	 * 系统管理员用户ID (程序默认登录时使用)
	 */
	public static final long ADMIN_USER_ID = 0;

	/**
	 * SSO用Ticket有效时间(分钟)
	 */
	public static final int TICKET_TIMEOUT_MINUTES = 30;

	/**
	 * 最大允许登录失败次数
	 */
	public static final int MAX_LOGIN_FAILED_COUNT = 5;

	/**
	 * 最早角色为小b时，会员编码规则为：HTD+2开头的8位，如HTD20000000（自增长）
	 */
	public static final String HTD2 = "htd2";
	/**
	 * 最早角色为平台公司时，供应商编码规则为：HTD+3开头的8位，如HTD30000000（自增长）
	 */
	public static final String HTD3 = "htd3";
	/**
	 * 最早角色为外部供应商时，外部供应商编码规则为：HTD+4开头的8位，如HTD40000000（自增长）
	 */
	public static final String HTD4 = "htd4";
}
