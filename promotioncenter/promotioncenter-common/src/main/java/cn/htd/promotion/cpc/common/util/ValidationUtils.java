package cn.htd.promotion.cpc.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;

/**
 * 校验工具类
 * 
 * @author jiangkun
 */
public class ValidationUtils {

	public final static String ERROR_MSG_SEPERATOR = ";";

	private static Validator validator;

	static {
		if (validator == null) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
		}
	}

	/**
	 * 校验Obj对象
	 * 
	 * @param obj
	 *            待校验对象
	 * @return
	 */
	public static <T> ValidateResult validateEntity(T obj) {

		ValidateResult result = new ValidateResult();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);

		if (CollectionUtils.isNotEmpty(constraintViolations)) {
			result.setHasErrors(true);
			for (ConstraintViolation<T> cv : constraintViolations) {
				result.addErrorMsg(cv.getPropertyPath().toString(), cv.getMessage());
			}
		}
		return result;
	}

	/**
	 * 校验Obj的单个属性
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static <T> ValidateResult validateProperty(T obj, String propertyName) {

		ValidateResult result = new ValidateResult();
		Set<ConstraintViolation<T>> constraintViolations = validator.validateProperty(obj, propertyName, Default.class);

		if (CollectionUtils.isNotEmpty(constraintViolations)) {
			result.setHasErrors(true);
			for (ConstraintViolation<T> cv : constraintViolations) {
				result.addErrorMsg(propertyName, cv.getMessage());
			}
		}
		return result;
	}

	/**
	 * 校验Obj的单个属性
	 * 
	 * @param objClass
	 * @param propertyName
	 * @param obj
	 * @return
	 */
	public static <T> ValidateResult validateValue(Class<T> objClass, String propertyName, Object obj) {

		ValidateResult result = new ValidateResult();
		Set<ConstraintViolation<T>> constraintViolations = validator.validateValue(objClass, propertyName, obj,
				Default.class);

		if (CollectionUtils.isNotEmpty(constraintViolations)) {
			result.setHasErrors(true);
			for (ConstraintViolation<T> cv : constraintViolations) {
				result.addErrorMsg(propertyName, cv.getMessage());
			}
		}
		return result;
	}
}
