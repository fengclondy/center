package cn.htd.basecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.basecenter.domain.BaseWebsiteMessage;
import cn.htd.basecenter.dto.WebSiteMessageDTO;
import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;

/**
 * <p>
 * Description: [站内信服务]
 * </p>
 */
public interface BaseWebSiteMessageDAO extends BaseDAO<WebSiteMessageDTO> {

	/**
	 * <p>
	 * Discription:[按照参数类型查询站内信列表]
	 * </p>
	 */
	public List<WebSiteMessageDTO> queryWebSiteMessageList(@Param("entity") WebSiteMessageDTO entity, @Param("page") Pager page);

	/**
	 * <p>
	 * Discription:[站内信修改阅读状态]
	 * </p>
	 */
	//public void modifyWebSiteMessage(@Param("id") String id, @Param("wmRead") String wmRead);
	public void modifyWebSiteMessage(@Param("entity") BaseWebsiteMessage entity);

	/**
	 * <p>
	 * Discription:[站内信数量统计]
	 * </p>
	 */
	public int totalMessages(BaseWebsiteMessage baseWebsiteMessage);

	/**
	 * 
	 * <p>
	 * Discription:[根据输入参数返回站内信详情]
	 * </p>
	 */
	public WebSiteMessageDTO getWebSiteMessageInfo(@Param("entity") WebSiteMessageDTO entity);
}