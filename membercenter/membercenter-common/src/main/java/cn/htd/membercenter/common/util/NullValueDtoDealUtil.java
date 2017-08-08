package cn.htd.membercenter.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司 FileName: NullValueDtoDealUtil.java
 * Author: bs.xu Date: 2016年12月30日 Description: 对实体类DTO空值进行处理 History:
 * <author> <time> <version> <desc> bs.xu 2016年12月30日 1.0 创建
 */

public class NullValueDtoDealUtil {

	private static final Logger log = LoggerFactory.getLogger(NullValueDtoDealUtil.class);

	private static final String METHOD_SET = "set";

	private static final String METHOD_GET = "get";

	private static final int UPPER_BEGIN_INDEX = 0;

	private static final int UPPER_END_INDEX = 1;

	private static final int SET_INT_VAL = 0;

	private static final Long SET_LONG_VAL = 0L;

	private static final Byte SET_BYTE_VAL = 0;

	private static final Short SET_SHORT_VAL = 0;

	private static final Float SET_FLOAT_VAL = 0F;

	private static final Double SET_DOUBLE_VAL = 0D;

	private static final Boolean SET_BOOLEAN_VAL = false;

	private static final char SET_CHAR_VAL = 0;

	public static <T> T parseObjectToValue(T t) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		@SuppressWarnings("unchecked")
		Class<T> clzz = (Class<T>) t.getClass();
		Field[] fields = clzz.getDeclaredFields();
		int l = fields.length;
		for (int i = 0; i < l; i++) {
			Method getMethod = getGetMethod(fields[i], clzz);
			if (null != getMethod) {
				Object obj = getMethod.invoke(t);
				Method methodSet = setSetMethod(fields[i], clzz);
				if (null == obj && null != methodSet) {
					Object fieldType = fields[i].getGenericType();
					if (fieldType == Boolean.class || fieldType == boolean.class) {
						methodSet.invoke(t, SET_BOOLEAN_VAL);
					} else if (fieldType == Integer.class || fieldType == int.class) {
						methodSet.invoke(t, SET_INT_VAL);
					} else if (fieldType == Long.class || fieldType == long.class) {
						methodSet.invoke(t, SET_LONG_VAL);
					} else if (fieldType == Byte.class || fieldType == byte.class) {
						methodSet.invoke(t, SET_BYTE_VAL);
					} else if (fieldType == Float.class || fieldType == float.class) {
						methodSet.invoke(t, SET_FLOAT_VAL);
					} else if (fieldType == Double.class || fieldType == double.class) {
						methodSet.invoke(t, SET_DOUBLE_VAL);
					} else if (fieldType == char.class) {
						methodSet.invoke(t, SET_CHAR_VAL);
					} else if (fieldType == Short.class || fieldType == short.class) {
						methodSet.invoke(t, SET_SHORT_VAL);
					} else if (fieldType == Integer.class || fieldType == int.class) {
						methodSet.invoke(t, SET_LONG_VAL);
					} else {
						methodSet.invoke(t, fields[i].getType().newInstance());
					}

				}
			}

		}
		return t;
	}

	/**
	 * 通过field获取set方法
	 * 
	 * @param field
	 * @param clzz
	 * @return
	 */
	public static <T> Method setSetMethod(Field field, Class<T> clzz) {
		Method method = null;
		if (null != field) {
			String fieldName = field.getName();
			try {
				StringBuffer methodBufferName = new StringBuffer();
				methodBufferName.append(METHOD_SET)
						.append(fieldName.substring(UPPER_BEGIN_INDEX, UPPER_END_INDEX).toUpperCase())
						.append(fieldName.substring(UPPER_END_INDEX, fieldName.length()));
				method = clzz.getMethod(methodBufferName.toString(), field.getType());
				return method;
			} catch (NoSuchMethodException e) {
				log.info(fieldName + " field fitered");
			} catch (SecurityException e) {
				log.warn(fieldName + "have SecurityException" + e);
			}
		}
		return null;
	}

	/**
	 * 通过field获取get方法
	 * 
	 * @param field
	 * @param clzz
	 * @return
	 */
	public static <T> Method getGetMethod(Field field, Class<T> clzz) {
		Method method = null;
		if (null != field) {
			String fieldName = field.getName();
			try {
				StringBuffer methodBufferName = new StringBuffer();
				methodBufferName.append(METHOD_GET)
						.append(fieldName.substring(UPPER_BEGIN_INDEX, UPPER_END_INDEX).toUpperCase())
						.append(fieldName.substring(UPPER_END_INDEX, fieldName.length()));
				method = clzz.getMethod(methodBufferName.toString());
				return method;
			} catch (NoSuchMethodException e) {
				log.info(fieldName + " field fitered");
			} catch (SecurityException e) {
				log.warn(fieldName + " field have SecurityException,Exception:" + e);
			}
		}
		return null;
	}
}
