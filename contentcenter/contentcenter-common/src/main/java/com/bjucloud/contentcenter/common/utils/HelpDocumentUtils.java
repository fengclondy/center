package com.bjucloud.contentcenter.common.utils;


import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.htd.common.dao.util.RedisDB;


/**
 * @Title: HelpDocumentUtils.java
 * @Description: 帮助中心工具类
 * @date 2017年2月18日 下午11:43:02
 * @version V1.0
 **/
@Component("helpDocumentUtils")
public class HelpDocumentUtils {
	
	
	// Redis帮助中心数据
	private static final String REDIS_HELPDOC_SCATEGORY_KEY = "B2B_MIDDLE_HELPDOC_SCATEGORY_OSB_SEQ";
	private static final String REDIS_HELPDOC_TOPIC_KEY = "B2B_MIDDLE_HELPDOC_TOPIC_OSB_SEQ";
	
	// 二级分类初始值10
	private static final String REDIS_HELPDOC_SCATEGORY_INIT_KEY = "10";
	// 主题初始值1000
	private static final String REDIS_HELPDOC_TOPIC_INIT_KEY = "1000";


	@Resource
	private RedisDB redisDB;

	
	/**
	 * 生成二级分类编号
	 * @param firstCategoryCode 一级分类编码
	 * @return
	 */
	public String generateSecondCategoryCode(String firstCategoryCode) {
		
		String sub3 = getHelpDocCacheSeq(REDIS_HELPDOC_SCATEGORY_KEY,REDIS_HELPDOC_SCATEGORY_INIT_KEY);
		StringBuilder stringBuilder = new StringBuilder(firstCategoryCode);
		stringBuilder.append(sub3);
		String secondCategoryCode = stringBuilder.toString();
		return secondCategoryCode;
	}
	
	/**
	 * 生成主题编号
	 * @param secondCategoryCode 二级分类编码
	 * @return
	 */
	public String generateTopicCode(String secondCategoryCode) {
		
		String sub3 = getHelpDocCacheSeq(REDIS_HELPDOC_TOPIC_KEY,REDIS_HELPDOC_TOPIC_INIT_KEY);
		StringBuilder stringBuilder = new StringBuilder(secondCategoryCode);
		stringBuilder.append(sub3);
		String topicCode = stringBuilder.toString();
		return topicCode;
	}

	private String getHelpDocCacheSeq(String seqKey,String initValue) {
		String value = redisDB.get(seqKey);
		if(null != value && !"".equals(value)){
			Long seqIndexLong = redisDB.incr(seqKey);
			return String.valueOf(seqIndexLong);
		}else{
			redisDB.set(seqKey, initValue);
			return initValue;
		}
	}
	
	

}
