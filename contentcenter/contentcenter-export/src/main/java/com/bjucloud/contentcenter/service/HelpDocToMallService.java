package com.bjucloud.contentcenter.service;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.domain.HelpDocumentFirstCategory;

import java.util.List;

/**
 * @Purpose 帮助中心service
 * @author zf.zhang
 * @since 2017-3-30 14:01
 *
 */
public interface HelpDocToMallService {

	/**
	 * 根据父分类编码查询所有子分类
	 * 
	 * @param parentCategoryCode
	 * @return
	 */
	ExecuteResult<List<HelpDocCategory>> getCategoriesByParentCategoryCode(String parentCategoryCode);

	/**
	 * 根据分类编码查询所有主题
	 * 
	 * @param categoryCode
	 * @return
	 */
	ExecuteResult<List<HelpDocTopic>> getTopicsByCategoryCode(String categoryCode);
	
	/**
	 * 根据主题编号查询主题
	 * @param topictCode
	 * @return
	 */
	ExecuteResult<HelpDocTopic> getTopicByTopictCode(String topictCode);
	
	ExecuteResult<HelpDocumentFirstCategory> getFirstCategoriesByFirstCategory(String firstCategory);
	
	ExecuteResult<HelpDocumentFirstCategory> getSecondCategoriesByFirstCategory(String secondCategory);
	
	ExecuteResult<HelpDocumentFirstCategory> getHelpDocTopicByTopictCode(String topictCode);

}
