package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.domain.HelpDocCategory;
import cn.htd.membercenter.domain.HelpDocFirstCategory;
import cn.htd.membercenter.domain.HelpDocTopic;

/**
 * @Purpose 帮助中心service
 * @author zf.zhang
 * @since 2017-3-30 14:01
 *
 */
public interface HelpDocumentService {

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
	
	ExecuteResult<HelpDocFirstCategory> getFirstCategoriesByFirstCategory(String firstCategory);
	
	ExecuteResult<HelpDocFirstCategory> getSecondCategoriesByFirstCategory(String secondCategory);
	
	ExecuteResult<HelpDocFirstCategory> getHelpDocTopicByTopictCode(String topictCode);

}
