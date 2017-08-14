package com.bjucloud.contentcenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Purpose 帮助主题DAO
 * @author zf.zhang
 * @since 2017-03-30 16:43
 *
 */
public interface HelpDocumentTopicDAO extends BaseDAO<HelpDocTopic> {
	
	
	/**
	 * 根据条件查询主题
	 * @param helpDocTopicDTO
	 * @return
	 */
	List<HelpDocTopic> getHelpDocTopicsByParams(HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 获取除内容字段的主题（性能上提升,content的容量很大）
	 * @param helpDocTopicDTO
	 * @return
	 */
	List<HelpDocTopic> getNoContentFieldOfTopicsByParams(HelpDocTopicDTO helpDocTopicDTO);
	
	/**
	 * 根据主题编码查询主题
	 * @param topictCode
	 * @return
	 */
	HelpDocTopic getHelpDocTopicsByTopicCode(@Param("topictCode") String topictCode);
	
	
	
}
