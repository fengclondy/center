package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.WebSiteMessageDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public interface BaseWebSiteMessageService {

	/**
	 * <p>
	 * Discription:[站内信发送]
	 * </p>
	 */
	public ExecuteResult<String> addWebMessage(WebSiteMessageDTO webSiteMessageDTO);

	/**
	 * <p>
	 * Discription:[站内信数量统计]
	 * </p>
	 */
	public int totalMessages(long userId, int messageType, int readFlag);

	/**
	 * <p>
	 * Discription:[修改站内信阅读状态]
	 * </p>
	 */
	public ExecuteResult<String> modifyWebSiteMessage(String[] ids, String wmRead);

	/**
	 * <p>
	 * Discription:[按照参数查询站内信列表]
	 * </p>
	 */
	public DataGrid<WebSiteMessageDTO> queryWebSiteMessageList(WebSiteMessageDTO webSiteMessageDTO,
			Pager<WebSiteMessageDTO> page);

	/**
	 *
	 * <p>
	 * Discription:根据传入条件查询站内信详情
	 * </p>
	 */
	public ExecuteResult<WebSiteMessageDTO> getWebSiteMessageInfo(WebSiteMessageDTO webSiteMessageDTO);
}
