package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.NoticeMybatisDAO;
import com.bjucloud.contentcenter.domain.Notice;
import com.bjucloud.contentcenter.dto.MallNoticeDTO;
import com.bjucloud.contentcenter.service.NoticeExportService;

/**
 * 
 * <p>
 * Description: [公告服务的处理接口的实现类]
 * </p>
 */
@Service("noticeExportService")
public class NoticeExportServiceImpl implements NoticeExportService {
	private final static Logger LOGGER = LoggerFactory.getLogger(NoticeExportServiceImpl.class);

	@Resource
	private NoticeMybatisDAO noticeMybatisDAO;

	@Override
	public DataGrid<MallNoticeDTO> queryNoticeList(Pager page, MallNoticeDTO dto) {

		DataGrid<MallNoticeDTO> dataGrid = new DataGrid<MallNoticeDTO>();

		List<MallNoticeDTO> noticeDTOs = noticeMybatisDAO.queryListDTO(dto, page);
		Long count = noticeMybatisDAO.queryCount(dto, page);

		dataGrid.setRows(noticeDTOs);
		dataGrid.setTotal(count);

		return dataGrid;
	}

	@Override
	public MallNoticeDTO getNoticeInfo(Long id) {
		return noticeMybatisDAO.queryDTOById(id);
	}

	@Override
	public ExecuteResult<String> deleteNoticeById(Long id) {
		ExecuteResult<String> res = new ExecuteResult<String>();
		Integer count = noticeMybatisDAO.delete(id);
		if (count > 0) {
			res.setResult("1");
		} else {
			res.setResult("0");
		}
		return res;
	}

	@Override
	public ExecuteResult<String> modifyNoticeRecommend(Long id, Integer isRecommend) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			Notice notice = new Notice();
			notice.setId(id);
			notice.setIsRecommend(isRecommend);
			// 只能有一个置顶公告，修改其他置顶公告为不置顶
			List<Notice> queryList = noticeMybatisDAO.queryList(notice, new Pager<Object>());
			if (queryList != null && queryList.size() > 0) {
				for (Notice nDto : queryList) {
					nDto.setIsRecommend(0);
					noticeMybatisDAO.update(nDto);
				}
			}
			int count = noticeMybatisDAO.updateBySelect(notice);
			if (count > 0) {
				result.setResultMessage("操作成功");
			} else {
				result.setResultMessage("操作失败");
			}
		} catch (Exception e) {
			LOGGER.error("执行方法【modifyNoticeRecommend】报错！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> modifyNoticeStatus(Long id, Integer status) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			Notice notice = new Notice();
			notice.setId(id);
			notice.setStatus(status);
			int count = noticeMybatisDAO.updateBySelect(notice);
			if (count > 0) {
				result.setResultMessage("操作成功");
			} else {
				result.setResultMessage("操作失败");
			}
		} catch (Exception e) {
			LOGGER.error("执行方法【modifyNoticeStatus】报错！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public ExecuteResult<String> addNotice(MallNoticeDTO mallNoticeDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		if (StringUtils.isBlank(mallNoticeDTO.getTitle())) {
			result.addErrorMessage("公告标题不能为空！");
			return result;
		}
		if (mallNoticeDTO.getNoticeType() == null) {
			result.addErrorMessage("公告类型不能为空！");
			return result;
		}
		if (1 == mallNoticeDTO.getNoticeType() && StringUtils.isBlank(mallNoticeDTO.getContent())) {
			result.addErrorMessage("公告内容不能为空");
			return result;
		}
		if (2 == mallNoticeDTO.getNoticeType() && StringUtils.isBlank(mallNoticeDTO.getUrl())) {
			result.addErrorMessage("公告链接不能为空");
			return result;
		}
		Notice notice = new Notice();
		// 1 首先查询 公告的下一个序号
		notice.setPlatformId(mallNoticeDTO.getPlatformId());

		Long currentSortNum = noticeMybatisDAO.getSortNumByCondation(mallNoticeDTO);
		Long nextSortNum = 0L;
		if (currentSortNum != null) {
			nextSortNum = currentSortNum + 1;
		}
		notice.setSortNum(nextSortNum.intValue());
		notice.setContent(mallNoticeDTO.getContent());
		if (mallNoticeDTO.getIsRecommend() != null) {// 默认不置顶
			notice.setIsRecommend(mallNoticeDTO.getIsRecommend());
		} else {
			notice.setIsRecommend(0);
		}
		if (mallNoticeDTO.getStatus() != null) {// 默认草稿
			notice.setStatus(mallNoticeDTO.getStatus());
		} else {
			notice.setStatus(2);
		}
		if (mallNoticeDTO.getPlatformId() != null) {// 默认平台公告
			notice.setPlatformId(mallNoticeDTO.getPlatformId());
		} else {
			notice.setPlatformId(0L);
		}
		notice.setTitle(mallNoticeDTO.getTitle());
		notice.setNoticeType(mallNoticeDTO.getNoticeType());
		notice.setUrl(mallNoticeDTO.getUrl());
		notice.setThemeId(mallNoticeDTO.getThemeId());
		noticeMybatisDAO.add(notice);
		result.setResultMessage("操作成功");
		return result;
	}

	@Override
	public ExecuteResult<String> modifyNoticeInfo(MallNoticeDTO mallNoticeDTO) {

		ExecuteResult<String> result = new ExecuteResult<String>();
		if (mallNoticeDTO.getId() == null) {
			result.addErrorMessage("公告ID不能为空！");
			return result;
		}
		MallNoticeDTO notice = this.noticeMybatisDAO.queryDTOById(mallNoticeDTO.getId());

		if (notice == null) {
			result.addErrorMessage("公告不存在！");
			return result;
		}
		notice.setStatus(mallNoticeDTO.getStatus());
		notice.setTitle(mallNoticeDTO.getTitle());
		notice.setContent(mallNoticeDTO.getContent());
		notice.setUrl(mallNoticeDTO.getUrl());
		notice.setId(mallNoticeDTO.getId());
		notice.setSortNum(mallNoticeDTO.getSortNum());
		notice.setNoticeType(mallNoticeDTO.getNoticeType());
		int count = noticeMybatisDAO.updateDto(notice);
		if (count > 0) {
			result.setResultMessage("操作成功");
		} else {
			result.setResultMessage("操作失败");
		}
		return result;
	}

	@Override
	public ExecuteResult<MallNoticeDTO> updateNoticSortNum(MallNoticeDTO mallNoticeDTO, Integer modifyNum) {
		ExecuteResult<MallNoticeDTO> res = new ExecuteResult<MallNoticeDTO>();
		res.setResult(mallNoticeDTO);
		if (mallNoticeDTO.getId() == null || modifyNum == null) {
			throw new RuntimeException("公告ID 和 操作代码值不能为空");
		}
		MallNoticeDTO queryNo = new MallNoticeDTO();
		queryNo.setPlatformId(mallNoticeDTO.getPlatformId());
		queryNo.setSortNum(mallNoticeDTO.getSortNum());
		if (modifyNum > 0) {
			queryNo.setIsRecommend(1);
		} else {
			queryNo.setIsRecommend(-1);
		}
		List<MallNoticeDTO> noticList = noticeMybatisDAO.queryListByNextSort(queryNo, new Pager());
		// 上下移对象
		MallNoticeDTO notice = new MallNoticeDTO();
		notice.setId(mallNoticeDTO.getId());

		if (noticList == null || noticList.size() == 0) {
			// 查询置顶的公告信息 下移公告时设置为不置顶
			MallNoticeDTO query = new MallNoticeDTO();
			query.setPlatformId(0L);
			query.setIsRecommend(1);
			List<MallNoticeDTO> list = noticeMybatisDAO.queryListDTO(query, new Pager());
			if (list.size() > 0) {
				MallNoticeDTO dto = list.get(0);

				MallNoticeDTO preUpdateNotice = new MallNoticeDTO();
				preUpdateNotice.setId(dto.getId());
				preUpdateNotice.setIsRecommend(0);
				preUpdateNotice.setSortNum(dto.getSortNum());
				noticeMybatisDAO.updateDto(preUpdateNotice);
			}
			// 上移上面只有一个置顶的对象 设置自己为置顶
			notice.setIsRecommend(1);
		}
		if (noticList != null && noticList.size() > 0) {
			MallNoticeDTO preDto = noticList.get(0);
			if (noticList.size() == 1 && preDto.getIsRecommend() == 1) {
				MallNoticeDTO preUpdateNotice = new MallNoticeDTO();
				preUpdateNotice.setId(preDto.getId());
				preUpdateNotice.setIsRecommend(0);
				preUpdateNotice.setSortNum(preDto.getSortNum());
				noticeMybatisDAO.updateDto(preUpdateNotice);
				// 上移上面只有一个置顶的对象 设置自己为置顶
				notice.setIsRecommend(1);
			} else {
				Integer mun = preDto.getSortNum();// 得到上移下移时交换的排序号
				MallNoticeDTO preUpdateNotice = new MallNoticeDTO();
				preUpdateNotice.setId(preDto.getId());
				preUpdateNotice.setIsRecommend(preDto.getIsRecommend());
				preUpdateNotice.setSortNum(mallNoticeDTO.getSortNum());
				noticeMybatisDAO.updateDto(preUpdateNotice);
				// 交换排序号
				notice.setSortNum(mun);
			}
		}
		// 下移时置顶对象设置为不置顶
		if (modifyNum == 1) {
			notice.setIsRecommend(0);
		}
		noticeMybatisDAO.updateDto(notice);
		return res;
	}
}