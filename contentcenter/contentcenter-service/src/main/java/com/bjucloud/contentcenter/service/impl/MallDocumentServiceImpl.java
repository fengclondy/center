package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallDocumentDAO;
import com.bjucloud.contentcenter.domain.MallDocument;
import com.bjucloud.contentcenter.dto.MallDocumentDTO;
import com.bjucloud.contentcenter.dto.MallTypeDTO;
import com.bjucloud.contentcenter.service.MallDocumentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("mallDocumentService")
public class MallDocumentServiceImpl implements MallDocumentService {
	private final static Logger logger = LoggerFactory.getLogger(MallDocumentServiceImpl.class);
	@Resource
	private MallDocumentDAO mallDocumentDAO;

	/**
	 * <p>
	 * Discription:[帮助文档列表查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallDocumentDTO> queryMallDocumentList(MallDocumentDTO mallDocumentDTO, Pager pager) {
		List<MallDocumentDTO> documents = mallDocumentDAO.queryMallDocumentList(mallDocumentDTO, pager);
		long counts = mallDocumentDAO.queryMallDocumentCount(mallDocumentDTO);
		DataGrid<MallDocumentDTO> datagrid = new DataGrid<MallDocumentDTO>();
		datagrid.setRows(documents);
		datagrid.setTotal(counts);
		return datagrid;
	}

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档详情查询]
	 * </p>
	 */
	@Override
	public MallDocumentDTO getMallDocumentById(Long id) {
		logger.info("getMallDocumentById---id---:{}", id);
		MallDocumentDTO mallDocumentDTO = mallDocumentDAO.getMallDocumentById(id);
		return mallDocumentDTO;
	}

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档添加]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallDocument(MallDocumentDTO mallDocumentDTO) {
		logger.info("addMallDocument---mallDocumentDTO---:{}", mallDocumentDTO);
		ExecuteResult<String> result = new ExecuteResult<String>();
		MallDocument mallDocument = new MallDocument();
		try {
			mallDocument.setClassifyId(mallDocumentDTO.getMallClassifyId());
			mallDocument.setContentUrl(mallDocumentDTO.getMallContentUrl());
			mallDocument.setEndTime(mallDocumentDTO.getMallEndTime());
			mallDocument.setPlatformId(mallDocumentDTO.getMallPlatformId());
			mallDocument.setSortNum(mallDocumentDTO.getMallSortNum());
			mallDocument.setStartTime(mallDocumentDTO.getMallStartTime());
			mallDocument.setStatus(mallDocumentDTO.getMallStatus());
			mallDocument.setTitle(mallDocumentDTO.getMallTitle());
			mallDocument.setType(mallDocumentDTO.getMallType());
			mallDocument.setCreated(new Date());
			mallDocument.setModified(new Date());
			// 默认为未删除
			mallDocument.setIsDeleted("0");
			mallDocumentDAO.add(mallDocument);
			result.setResultMessage("操作成功");
		} catch (Exception e) {
			result.addErrorMsg(e.getMessage());
			result.setResultMessage("操作失败");
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档修改]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyInfoById(MallDocumentDTO mallDocumentDTO) {
		logger.info("modifyInfoById---mallDocumentDTO---:{}", mallDocumentDTO);
		ExecuteResult<String> result = new ExecuteResult<String>();
		MallDocument mallDocument = new MallDocument();
		try {
			mallDocument.setId(mallDocumentDTO.getMallId());
			mallDocument.setClassifyId(mallDocumentDTO.getMallClassifyId());
			mallDocument.setContentUrl(mallDocumentDTO.getMallContentUrl());
			mallDocument.setEndTime(mallDocumentDTO.getMallEndTime());
			mallDocument.setPlatformId(mallDocumentDTO.getMallPlatformId());
			mallDocument.setSortNum(mallDocumentDTO.getMallSortNum());
			mallDocument.setStartTime(mallDocumentDTO.getMallStartTime());
			mallDocument.setStatus(mallDocumentDTO.getMallStatus());
			mallDocument.setTitle(mallDocumentDTO.getMallTitle());
			mallDocument.setType(mallDocumentDTO.getMallType());
			mallDocument.setModified(new Date());
			mallDocument.setIsDeleted(mallDocumentDTO.getMallIsDeleted() != null ? mallDocumentDTO.getMallIsDeleted().toString() : null);
			mallDocumentDAO.update(mallDocument);
			result.setResultMessage("操作成功！");
		} catch (Exception e) {
			result.addErrorMsg("操作失败！");
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Discription:[帮助文档上下架]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyStatusById(int id, int status) {
		logger.info("modifyInfoById---id-status--:{}{}", id, status);
		ExecuteResult<String> result = new ExecuteResult<String>();
		MallDocument mallDocument = new MallDocument();
		try {
			mallDocument.setId(id);
			mallDocument.setStatus(status);
			mallDocument.setModified(new Date());
			mallDocument.setStartTime(status == 1 ? null : new Date());
			mallDocumentDAO.update(mallDocument);
			result.setResultMessage("操作成功");
		} catch (Exception e) {
			result.addErrorMsg(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[根据类型查询分类列表]
	 * </p>
	 */

	@Override
	public List<MallTypeDTO> queryMallDocumentListByType(String type) {
		List<MallTypeDTO> listRecord = new ArrayList<MallTypeDTO>();
		if (StringUtils.isEmpty(type)) {
			return listRecord;
		}
		return mallDocumentDAO.queryMallDocumentListByType(Integer.parseInt(type));
	}

	/**
	 * <p>
	 * 根据id删除文档
	 * </p>
	 */
	@Override
	public ExecuteResult<String> delById(Long id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			mallDocumentDAO.delete(id);
			er.setResult("操作成功");
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
		}
		return er;
	}
}
