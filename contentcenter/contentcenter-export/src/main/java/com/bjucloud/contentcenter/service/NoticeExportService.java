package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.MallNoticeDTO;

/**
 * 
 * <p>
 * Description: [公告服务的处理接口]
 * </p>
 */
public interface NoticeExportService {

	/**
	 * 
	 * <p>
	 * Discription:[公告列表查询 dto 传入查询 参数 noticeStatus 1生效 2是无效]
	 * </p>
	 */

	public DataGrid<MallNoticeDTO> queryNoticeList(Pager page, MallNoticeDTO dto);

	/**
	 * 
	 * <p>
	 * Discription:[公告详情查询]
	 * </p>
	 */
	public MallNoticeDTO getNoticeInfo(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[公告置顶/取消置顶]
	 * </p>
	 */
	public ExecuteResult<String> modifyNoticeRecommend(Long id, Integer isRecommend);

	/**
	 * 
	 * <p>
	 * Discription:[公告上下架]
	 * </p>
	 */
	public ExecuteResult<String> modifyNoticeStatus(Long id, Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[公告添加 公告类型 ： 1 文字公告 公告内容不能为空 2 链接公告 公告url不能为空 ]
	 * </p>
	 */
	public ExecuteResult<String> addNotice(MallNoticeDTO mallNoticeDTO);

	/**
	 * <p>
	 * Discription:[公告修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyNoticeInfo(MallNoticeDTO mallNoticeDTO);

	/**
	 * 
	 * <p>
	 * Discription:[公告上下移动 上移动传入1 下移传入-1 ]
	 * </p>
	 */
	public ExecuteResult<MallNoticeDTO> updateNoticSortNum(MallNoticeDTO mallNoticeDTO, Integer modifyNum);

	/**
	 * 
	 * <p>
	 * Discription:[删除公告根据ID]
	 * </p>
	 */
	public ExecuteResult<String> deleteNoticeById(Long id);
}
