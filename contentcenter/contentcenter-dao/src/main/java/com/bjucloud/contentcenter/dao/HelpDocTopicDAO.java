package com.bjucloud.contentcenter.dao;


import java.util.List;

import com.bjucloud.contentcenter.domain.HelpDocCategory;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.domain.MallDocument;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;

/**
 * @Purpose 帮助主题DAO
 * @author zf.zhang
 * @since 2017-03-30 16:43
 *
 */
public interface HelpDocTopicDAO extends BaseDAO<MallDocument> {
	
	
	/**
	 * 添加主题
	 * @param helpDocTopicDTO
	 */
	void addHelpDocTopic(HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 删除主题
	 * @param helpDocTopicDTO
	 */
	void deleteHelpDocTopicByParams(HelpDocTopicDTO helpDocTopicDTO);
	

	
	/**
	 * 根据条件查询主题总数
	 * @param helpDocTopicDTO
	 * @return
	 */
	int getHelpDocTopicsCount(HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 分页查询主题
	 * @param page
	 * @param helpDocTopicDTO
	 * @return
	 */
	List<HelpDocTopic> getHelpDocTopicsForPage(@Param("page")Pager<HelpDocTopicDTO> page, @Param("dto")HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 根据条件查询单个主题
	 * @param helpDocTopicDTO
	 * @return
	 */
	HelpDocTopic getSingleHelpDocTopicByParams(HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 根据文档主题判断是否重复
	 * @param content
	 */
	int queryByContent(@Param("topictName") String topictName);

	/**
	 * 更新主题
	 * @param helpDocTopicDTO
	 */
	void updateHelpDocTopic(HelpDocTopicDTO helpDocTopicDTO);
	/**
	 * 更改帮助文档内容
	 * @param doc
	 */
	void editHelpDocTopic(HelpDocTopicDTO doc);
	/**
	 * 删除帮助文档
	 * @param topictCode
	 */
	void deleteHelpDocTopic(@Param("topictCode") String topictCode);
	/**
	 * 查找单条帮助文档
	 * @param topictCode
	 * @return
	 */
	HelpDocTopic queryHelpDocByTopicCode(@Param("topictCode") String topictCode);

	/**
	 * 根据条件查询帮助文档
	 * @param page
	 * @param dto
	 * @return
	 */
	List<HelpDocTopic> queryHelpDocTopicForPage(@Param("page") Pager<HelpDocTopicDTO> page,@Param("dto") HelpDocTopicDTO dto);
	/**
	 * 根据sortnum和二级编码判断sortnum不重复
	 * @param sortNum
	 * @param secondCategoryCode
	 * @return
	 */
	int queryByCount(@Param("sortNum") int sortNum,@Param("secondCategoryCode") String secondCategoryCode);
	

}
