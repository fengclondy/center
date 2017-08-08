package com.bjucloud.contentcenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.domain.HelpDocFirstCategory;
import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;


/**
 * @Purpose 帮助中心service
 * @author zf.zhang
 * @since 2017-3-30 14:01
 *
 */
public interface HelpDocumentService {
	
	/**
	 * 添加分类
	 * @param helpDocCategoryDTO
	 */
	ExecuteResult<?> addHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 根据分类编号删除分类
	 * @param code
	 * @return
	 */
	ExecuteResult<?> deleteHelpDocCategoryByCode(String code);
	
	/**
	 * 更新分类
	 * @param helpDocCategoryDTO
	 */
	ExecuteResult<?> updateHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 分页查询分类
	 * @param page
	 * @param helpDocCategoryDTO
	 * @return
	 */
	ExecuteResult<DataGrid<HelpDocCategory>> getHelpDocCategoriesForPage(Pager<HelpDocCategoryDTO> page, HelpDocCategoryDTO helpDocCategoryDTO);
	
	/**
	 * 根据分类编号查询单个分类
	 * @param code
	 * @return
	 */
	ExecuteResult<HelpDocCategory> getSingleHelpDocCategoryByCode(String code);
	
	/**
	 * 查询所有完成的一级分类(包含发布的二级分类)
	 * @return
	 */
	ExecuteResult<List<HelpDocFirstCategory>> getFirstCmptHelpDocCategory();


	/**
	 * 查询所有一级分类
	 * @return
	 */
	ExecuteResult<List<HelpDocCategory>> getTopHelpDocCategory();
	
	/**
	 * 根据一级分类编码查询所有二级分类
	 * @param firstCategoryCode
	 * @return
	 */
	ExecuteResult<List<HelpDocCategory>> getSecondHelpDocCategory(String firstCategoryCode);

	/**
	 * 分页查询文章
	 * @param page
	 * @param helpDocTopicDTO
	 * @return
	 */
	ExecuteResult<DataGrid<HelpDocTopic>> getHelpDocForPage(Pager<HelpDocTopicDTO> page, HelpDocTopicDTO helpDocTopicDTO);

	/**
	 * 添加帮助主题文档
	 * @param helpDocTopicDTO
	 * @return
	 */
	ExecuteResult<?> addHelpDocument(HelpDocTopicDTO helpDocTopicDTO);
	/**
	 * 修改帮助主题
	 * @param doc
	 * @return
	 */
	ExecuteResult<?> editHelpDocTopic(HelpDocTopicDTO doc);
	/**
	 * 修改发布状态
	 * @param isPublish
	 * @return
	 */
	ExecuteResult<?> updateIsPublis(HelpDocTopicDTO dto);
	/**
	 * 删除帮助文档
	 * @param topictCode
	 * @return
	 */
	ExecuteResult<?> deleteHelpDocTopic(String topictCode);
	/**
	 * 查询单条帮助文档
	 * @param topictCode
	 * @return
	 */
	ExecuteResult<HelpDocTopic> queryHelpDocByTopicCode(String topictCode);
	/**
	 * 根据指定条件查询帮助文档
	 * @param page
	 * @param dto
	 * @return
	 */
	ExecuteResult<DataGrid<HelpDocTopic>> queryHelpDocTopicForPage(Pager<HelpDocTopicDTO> page, HelpDocTopicDTO dto);
	
	
}
