package cn.htd.membercenter.common.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON转换类
 *
 * @author kefanghui
 * @Created 2014-5-26
 *
 */
public class JSONTransformUtil {

	protected static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 说明：把JavaBean对象转换为json字符串
	 *
	 * @param object
	 *            JavaBean对象
	 * @return String json字符串
	 */
	public static String toJsonStr(final Object object) {

		// ObjectMapper objectMapper = new ObjectMapper();

		if (null != object) {
			try {
				return objectMapper.writeValueAsString(object);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 说明：把json字符串转换为相应的JavaBean对象
	 *
	 * @param content
	 *            原始json字符串数据
	 * @param valueType
	 *            要转换的JavaBean类型
	 * @return JavaBean对象
	 */
	public static <T> T toObject(final String content, final Class<T> valueType) {

		final ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		if (null != content && !"".equals(content.trim())) {
			try {
				return objectMapper.readValue(content, valueType);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static <T> T toObject(final String content, final JavaType valueType) {

		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		if (null != content && !"".equals(content.trim())) {
			try {
				return objectMapper.readValue(content, valueType);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 说明：把json字符串转换为相应的List<JavaBean>集合
	 *
	 * @param content
	 *            原始json字符串数据
	 * @param valueType
	 *            要转换的JavaBean类型
	 * @return List<T> List<JavaBean>集合
	 */
	public static <T> List<T> toList(final String content, final Class<T> valueType) {

		// ObjectMapper objectMapper = new ObjectMapper();
		// 处理特殊字符
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		final JavaType javaType = getJavaType(ArrayList.class, valueType);

		if (null != content && !"".equals(content.trim())) {
			try {
				return objectMapper.readValue(content, javaType);
			} catch (final Exception e) {
				e.printStackTrace();
			}

		}

		return null;

	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 */
	public static JavaType getJavaType(final Class<?> collectionClass, final Class<?>... elementClasses) {

		// ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
}
