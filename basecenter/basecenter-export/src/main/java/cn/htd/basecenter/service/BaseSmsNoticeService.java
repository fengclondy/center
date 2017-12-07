package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.BaseSmsNoticeDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

public interface BaseSmsNoticeService {

	/**
	 * 新增预警短信信息
	 * @param noticeDTO
	 * @return
	 * @throws Exception
	 */
	public ExecuteResult<String> insertBaseSmsNotice(BaseSmsNoticeDTO noticeDTO);
	
	/**
	 * 删除预警短信信息
	 * @param noticeDTO
	 * @return
	 * @throws Exception
	 */
	public ExecuteResult<String> deleteBaseSmsNotice(BaseSmsNoticeDTO noticeDTO);
	
	/**
	 * 查询预警短信信息（分页）
	 * @param noticeDTO
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public ExecuteResult<DataGrid<BaseSmsNoticeDTO>> queryBaseSmsNotice(BaseSmsNoticeDTO noticeDTO, Pager<BaseSmsNoticeDTO> pager);
}
