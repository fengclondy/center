package cn.htd.promotion.cpc.biz.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose 转换父类
 * @author zhangzhifeng
 * @param <SOURCE>
 * @param <TARGET>
 * @since 2017-8-22 11:58
 * 
 */
public abstract class AbstractConvert<SOURCE, TARGET> {

	/**
	 * 返回多个目标对象
	 * @param sources
	 * @return List<TARGET>
	 */
	public List<TARGET> toTarget(List<SOURCE> sources) {

		if (null == sources){
			return null;
		}
		if (sources.size() < 1){
			return null;
		}

		List<TARGET> target = new ArrayList<TARGET>(sources.size());
		for (SOURCE source : sources) {
			target.add(toTarget(source));
		}

		return target;
	}

	/**
	 * 返回单个目标对象
	 * @param source
	 * @return TARGET
	 */
	public TARGET toTarget(SOURCE source) {
		if (null == source) {
			return null;
		}

		return populateTarget(source);
	}

	/**
	 * 填充目标对象
	 * @param source
	 * @return TARGET
	 */
	protected abstract TARGET populateTarget(SOURCE source);
	
	
	public List<SOURCE> toSource(List<TARGET> targets) {

		if (null == targets){
			return null;
		}
		if (targets.size() < 1){
			return null;
		}

		List<SOURCE> sources = new ArrayList<SOURCE>(targets.size());
		for (TARGET target : targets) {
			sources.add(toSource(target));
		}

		return sources;
	}

	public SOURCE toSource(TARGET target) {
		if (null == target) {
			return null;
		}

		return populateSource(target);
	}

	protected abstract SOURCE populateSource(TARGET target);


}
