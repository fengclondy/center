package cn.htd.membercenter.util;

/**
 * Created by thinkpad on 2016/11/17.
 */
public class MemberUtil {
	/* 判断会员类型：会员、非会员、担保会员 */

	public static String judgeMemberType(String canMallLogin, String hasGuaranteeLicense, String hasBusinessLicense) {
		String memberType = "";
		// 非商城登陆则为非会员
		if ("0".equals(canMallLogin)) {
			memberType = "1";
			// memberType = MemberTypeEnum.NONE_MEMBER.getCode();
		} else {
			// 商城登陆且有营业执照则为正式会员
			if ("1".equals(hasBusinessLicense)) {
				memberType = "2";
				// memberType = MemberTypeEnum.REAL_MEMBER.getCode();
			} else {
				// 商城登陆没有营业执照有担保执照则为担保会员
				if ("1".equals(hasGuaranteeLicense)) {
					memberType = "3";
					// memberType = MemberTypeEnum.GUARANTEE_MEMBER.getCode();
				}
			}
		}
		return memberType;
	}
}
