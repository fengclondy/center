package com.bjucloud.contentcenter.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;

/**
 * @Purpose 帮助分类DAO
 * @author zf.zhang
 * @since 2017-03-30 16:43
 *
 */
public interface HelpDocCategoryDAO extends BaseDAO<HelpDocCategory> {
	
	/**
	 * 添加分类
	 * @param helpDocCategoryDTO
	 */
	void addHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 删除分类
	 * @param helpDocCategoryDTO
	 */
	void deleteHelpDocCategoryByParams(HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 删除分类下的所有主题
	 * @param categoryCode
	 */
	void deleteAllTopicsByCategoryCode(@Param("categoryCode")String categoryCode);
	
	/**
	 * 更新分类
	 * @param helpDocCategoryDTO
	 */
	void updateHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 根据条件查询分类总数
	 * @param helpDocCategoryDTO
	 * @return
	 */
	int getHelpDocCategoriesCount( @Param("dto")HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 分页查询分类
	 * @param page
	 * @param helpDocCategoryDTO
	 * @return
	 */
	List<HelpDocCategory> getHelpDocCategoriesForPage(@Param("page")Pager<HelpDocCategoryDTO> page, @Param("dto")HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 根据条件查询所有分类
	 * @return
	 */
	List<HelpDocCategory> getHelpDocCategoriesByParams( @Param("dto")HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 根据条件查询单个分类
	 * @param helpDocCategoryDTO
	 * @return
	 */
	HelpDocCategory getSingleHelpDocCategoryByParams( @Param("dto")HelpDocCategoryDTO helpDocCategoryDTO);



}
