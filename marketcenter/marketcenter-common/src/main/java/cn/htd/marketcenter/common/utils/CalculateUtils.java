package cn.htd.marketcenter.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数值计算类型工具类.
 * 
 * @author jiangkun
 */
public class CalculateUtils {

	/**
	 * BigDecimal数据相加
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		BigDecimal result = BigDecimal.ZERO;
		if (b1 == null) {
			b1 = BigDecimal.ZERO;
		}
		if (b2 == null) {
			b2 = BigDecimal.ZERO;
		}
		result = b1.add(b2);
		return result;
	}

	/**
	 * BigDecimal数据相减
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
		BigDecimal result = BigDecimal.ZERO;
		if (b1 == null) {
			b1 = BigDecimal.ZERO;
		}
		if (b2 == null) {
			b2 = BigDecimal.ZERO;
		}
		result = b1.subtract(b2);
		return result;
	}

	/**
	 * BigDecimal数据相除保留两位小数四舍五入
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
		return divide(b1, b2, 2, RoundingMode.HALF_UP);
	}

	/**
	 * BigDecimal数据相除
	 * 
	 * @param b1
	 * @param b2
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2, int scale, RoundingMode roundingMode) {
		if (b1 == null) {
			b1 = BigDecimal.ZERO;
		}
		if (b2 == null) {
			throw new ArithmeticException("divisor b2 is null");
		}
		if (roundingMode == null) {
			roundingMode = RoundingMode.HALF_UP;
		}
		return b1.divide(b2, scale, roundingMode);
	}

	/**
	 * BigDecimal数据乘积保留两位小数四舍五入
	 * 
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
		return multiply(b1, b2, 2, RoundingMode.HALF_UP);
	}

	/**
	 * BigDecimal数据乘积保留指定位数小数
	 * 
	 * @param b1
	 * @param b2
	 * @param scale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2, int scale, RoundingMode roundingMode) {
		if (b1 == null) {
			b1 = BigDecimal.ZERO;
		}
		if (b2 == null) {
			b2 = BigDecimal.ZERO;
		}
		if (roundingMode == null) {
			roundingMode = RoundingMode.HALF_UP;
		}
		return b1.multiply(b2).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * BigDecimal保留两位小数四舍五入
	 * 
	 * @param b
	 * @return
	 */
	public static BigDecimal setScale(BigDecimal b) {
		return setScale(b, 2, RoundingMode.HALF_UP);
	}

	/**
	 * BigDecimal保留指定位数小数
	 * 
	 * @param b
	 * @param newScale
	 * @param roundingMode
	 * @return
	 */
	public static BigDecimal setScale(BigDecimal b, int newScale, RoundingMode roundingMode) {
		BigDecimal result = BigDecimal.ZERO;
		if (b == null) {
			return BigDecimal.ZERO;
		}
		if (roundingMode == null) {
			roundingMode = RoundingMode.HALF_UP;
		}
		result = b.setScale(newScale, roundingMode);
		return result;
	}

}
