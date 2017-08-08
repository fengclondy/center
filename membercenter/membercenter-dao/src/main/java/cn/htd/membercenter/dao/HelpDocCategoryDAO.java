package cn.htd.membercenter.dao;

import java.util.List;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.membercenter.domain.HelpDocCategory;
import cn.htd.membercenter.dto.HelpDocCategoryDTO;

/**
 * @Purpose 帮助分类DAO
 * @author zf.zhang
 * @since 2017-03-30 16:43
 *
 */
public interface HelpDocCategoryDAO extends BaseDAO<HelpDocCategory> {
	
	/**
	 * 根据条件查询分类
	 * @param helpDocCategoryDTO
	 * @return
	 */
	List<HelpDocCategory> getHelpDocCategoriesByParams(HelpDocCategoryDTO helpDocCategoryDTO);
	
	
	/**
	 * 根据条件查询单条分类
	 * @param helpDocCategoryDTO
	 * @return
	 */
	HelpDocCategory getSingleHelpDocCategoriesByParams(HelpDocCategoryDTO helpDocCategoryDTO);
	
	
	
}
