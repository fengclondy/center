package com.bjucloud.contentcenter.service.impl;


import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallClassifyDAO;
import com.bjucloud.contentcenter.domain.MallClassify;
import com.bjucloud.contentcenter.dto.MallClassifyDTO;
import com.bjucloud.contentcenter.service.MallClassifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service("mallClassifyService")
public class MallClassifyServiceImpl implements MallClassifyService {
	private final static Logger logger = LoggerFactory.getLogger(MallClassifyServiceImpl.class);

	@Resource
	private MallClassifyDAO mallClassifyDAO;

	@Override
	public DataGrid<MallClassifyDTO> queryMallCassifyList(MallClassifyDTO mallCassifyDTO, Pager page) {

		DataGrid<MallClassifyDTO> dg = new DataGrid<MallClassifyDTO>();
		dg.setRows(mallClassifyDAO.queryMallCassifyList(mallCassifyDTO, page));
		dg.setTotal(mallClassifyDAO.queryMallCassifyCount(mallCassifyDTO));
		return dg;
	}

	@Override
	public ExecuteResult<String> addMallCassify(MallClassifyDTO mallClassifyDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallClassify insertObj = new MallClassify();
			insertObj.setCreated(new Date());
			insertObj.setEndTime(mallClassifyDTO.getEndTime());
			insertObj.setModified(mallClassifyDTO.getModified());
			insertObj.setPlatformId(mallClassifyDTO.getPlatformId());
			insertObj.setStartTime(mallClassifyDTO.getStartTime());
			insertObj.setStatus(mallClassifyDTO.getStatus());
			insertObj.setTitle(mallClassifyDTO.getTitle());
			insertObj.setType(mallClassifyDTO.getType());
			if (mallClassifyDAO.addMallCassify(insertObj) > 0) {
				er.setResult("操作成功");
			} else {
				er.addErrorMsg("操作失败");
			}
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}
	
	// 文档分类修改
	@Override
	public ExecuteResult<String> modifyInfoById(MallClassifyDTO mallCassifyDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallClassify updateObj = new MallClassify();
			updateObj.setEndTime(mallCassifyDTO.getEndTime());
			updateObj.setId(mallCassifyDTO.getId());
			updateObj.setIsDeleted(mallCassifyDTO.getIsDeleted());
			updateObj.setModified(new Date());
			updateObj.setPlatformId(mallCassifyDTO.getPlatformId());
			updateObj.setStartTime(mallCassifyDTO.getStartTime());
			updateObj.setStatus(mallCassifyDTO.getStatus());
			updateObj.setTitle(mallCassifyDTO.getTitle());
			updateObj.setType(mallCassifyDTO.getType());

			mallClassifyDAO.update(updateObj);
			er.setResultMessage("修改成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			er.addErrorMsg("修改失败");
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<String> modifyStatusById(int id, int status) {
		logger.info("modifyStatusById---id-status--:{}{}", id, status);
		ExecuteResult<String> er = new ExecuteResult<String>();
		MallClassify mallClassify = new MallClassify();
		try {
			mallClassify.setId(id);
			mallClassify.setStatus(status);
			mallClassify.setModified(new Date());
			mallClassify.setStartTime(status == 2 ? null : new Date());
			mallClassifyDAO.update(mallClassify);
			er.setResultMessage("操作成功");
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<String> delById(int id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			mallClassifyDAO.delete(id);
			er.setResult("操作成功");
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
		}
		return er;
	}

}
