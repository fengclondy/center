package cn.htd.basecenter.common.constant;

public class ReturnCodeConst {

	// 处理成功
	public static final String RETURN_SUCCESS = "00000";
	// 根据条件查询数据过多超过10000条
	public static final String TOO_MANY_DATA_ERROR = "99998";
	// 系统异常
	public static final String SYSTEM_ERROR = "99999";
	// *******************************************************
	// 通用返回码
	// *******************************************************
	// 入参不合法
	public static final String PARAMETER_ERROR = "01001";
	// 类型重复
	public static final String TYPE_HOMONYMY_ERROR = "01002";
	// 没有公告信息
	public static final String NO_PLACARD_ERROR = "01003";
	// 公告已被删除
	public static final String PLACARD_HAS_DELETE = "01004";
	// 公告正在发送中
	public static final String PLACARD_IS_SENDING = "01005";
	// 公告已发送
	public static final String PLACARD_HAS_SENT = "01006";
	// 没有地址信息
	public static final String NO_ADDRESS_ERROR = "01007";
	// 地址信息重复
	public static final String ADDRESS_HAS_REPEATED = "01008";
	// 父级地址信息不存在
	public static final String ADDRESS_NO_PARENT = "01009";
	// 末级地址不能在继续添加下级
	public static final String ADDRESS_IS_LEAF_ERROR = "01010";
	// 存在下级地址不能删除
	public static final String ADDRESS_CANNOT_DELETE = "01011";
	// 地址已删除
	public static final String ADDRESS_HAS_DELETED = "01012";
	// 没有字典信息
	public static final String NO_DICTIONARY_ERROR = "01013";
	// 字典信息重复
	public static final String DICTIONARY_HAS_REPEATED = "01014";
	// 字典信息父类型不存在
	public static final String NO_PARENT_DICTIONARY_ERROR = "01015";
	// 短信配置信息不存在
	public static final String NO_SMS_CONFIG_ERROR = "01016";
	// 短信模版信息不存在
	public static final String NO_SMS_TEMPLATE_ERROR = "01017";
	// 邮件配置信息不存在
	public static final String NO_EMAIL_CONFIG_ERROR = "01018";
	// 邮件模版信息不存在
	public static final String NO_EMAIL_TEMPLATE_ERROR = "01019";
	// 漫道短信发送错误
	public static final String MANDAO_SMS_SEND_ERROR = "01020";
	// 天讯通短信发送错误
	public static final String TIANXUNTONG_SMS_SEND_ERROR = "01021";
	// 邮件发送错误
	public static final String EMAIL_SEND_ERROR = "01022";
}
