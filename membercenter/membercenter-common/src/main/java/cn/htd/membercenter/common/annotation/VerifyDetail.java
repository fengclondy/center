/**
 * 用于记录操作履历
 */
package cn.htd.membercenter.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VerifyDetail {
	public String type() default "NORMAL";// 是否字典

	public String[]key() default { "-1" };// 字典key数组

	public String[]value() default { "-1" };// 字典value数组

	public String tableId() default "-1";// 表名

	public String fieldId() default "-1";// 字段名

	public String contentName() default "-1";// 修改内容
}
