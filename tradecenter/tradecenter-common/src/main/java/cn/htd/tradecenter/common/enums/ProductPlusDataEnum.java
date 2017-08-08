/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	DeleteFlagEnum.java
 * Author:   	jiangkun
 * Date:     	2016年11月20日
 * Description: 删除标记枚举 
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月20日	1.0			创建
 */

package cn.htd.tradecenter.common.enums;

/**
 * 删除标记枚举
 * 
 * @author jiangkun
 */
public enum ProductPlusDataEnum {

	JD_BRAND_CODE_PAY("京东品牌-支付传入-other", "other"),
	JD_BRAND_CODE_ERP("品牌", "000028"),
	JD_CATEGORY_CODE_ERP("品类", "50"),
	PRODUCT_CODE_ERP("商城", "9001"),
	PRODUCT_CODE_ERP_EXTERNAL_SUPPLIER("0014","平台公司在POP交易");

	private String name;
	private String code;

	ProductPlusDataEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	// 获取枚举value对应名字方法
	public static String getName(String code) {
		for (ProductPlusDataEnum dataEnum : ProductPlusDataEnum.values()) {
			if (code.equals(dataEnum.getCode())) {
				return dataEnum.name;
			}
		}
		return "";
	}
}
