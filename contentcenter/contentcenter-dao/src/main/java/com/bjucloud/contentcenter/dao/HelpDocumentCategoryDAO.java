package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;
import java.util.List;

/**
 * @Purpose 帮助分类DAO
 * @author zf.zhang
 * @since 2017-03-30 16:43
 *
 */
public interface HelpDocumentCategoryDAO extends BaseDAO<HelpDocCategory> {
	
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
