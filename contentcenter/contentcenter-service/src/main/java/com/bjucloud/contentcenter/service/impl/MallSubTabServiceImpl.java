package com.bjucloud.contentcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bjucloud.contentcenter.dao.MallSubTabDAO;
import com.bjucloud.contentcenter.domain.MallSubTab;
import com.bjucloud.contentcenter.dto.MallSubTabDTO;
import com.bjucloud.contentcenter.dto.MallSubTabECMDTO;
import com.bjucloud.contentcenter.service.MallSubTabService;

/**
 * 
 * <p>
 * Description: [页签功能实现类]
 * </p>
 */
@Service("mallSubTabService")
public class MallSubTabServiceImpl implements MallSubTabService {

	public static final Logger logger = LoggerFactory.getLogger(MallSubTabServiceImpl.class);

	@Resource
	private MallSubTabDAO mallSubTabDAO;

	/**
	 * 
	 * <p>
	 * Description: [页签分页以及查询所有]
	 * </p>
	 */
	@Override
	public DataGrid<MallSubTabDTO> queryMallSubTabPage(MallSubTabDTO mllSubTabDTO, Pager page) {
		DataGrid<MallSubTabDTO> mallSubTab = new DataGrid<MallSubTabDTO>();
		MallSubTab mallTab = new MallSubTab();
		List<MallSubTabDTO> rows = new ArrayList<MallSubTabDTO>();
		BeanUtils.copyProperties(mllSubTabDTO, mallTab);
		Long total = null;
		try {
			rows = mallSubTabDAO.queryMallSubTabPage(page, mallTab);
			total = mallSubTabDAO.queryMallSubCount(mallTab);
		} catch (Exception e) {
			logger.error("执行【queryMallSubTabPage】报错！{}", e);
			throw new RuntimeException();
		}
		mallSubTab.setRows(rows);
		mallSubTab.setTotal(total);
		return mallSubTab;
	}

	public DataGrid<MallSubTabECMDTO> equeryMallSubTabPage(MallSubTabECMDTO mallSubTabECMDTO, Pager page) {
		DataGrid<MallSubTabECMDTO> mallSubTabECM = new DataGrid<MallSubTabECMDTO>();
		List<MallSubTabECMDTO> rows = new ArrayList<MallSubTabECMDTO>();
		Long total = null;
		rows = mallSubTabDAO.equeryMallSubTabPage(page, mallSubTabECMDTO);
		total = mallSubTabDAO.equeryMallSubCount(mallSubTabECMDTO);
		mallSubTabECM.setRows(rows);
		mallSubTabECM.setTotal(total);
		return mallSubTabECM;
	}

	/**
	 * 
	 * <p>
	 * Description: [页签新增]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallSubTab(MallSubTabDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		MallSubTab mallSubTab = new MallSubTab();
		BeanUtils.copyProperties(dto, mallSubTab);
		try {
			mallSubTabDAO.add(mallSubTab);
			result.setResultMessage("1");
		} catch (Exception e) {
			logger.info("执行【addMallSubTab】失败！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description: [页签获取数据]
	 * </p>
	 */
	@Override
	public MallSubTabDTO getMallSubTabData(Long id) {
		MallSubTabDTO mallSubTabDTO = new MallSubTabDTO();
		try {
			MallSubTab mallSubTab = mallSubTabDAO.queryById(id);
			BeanUtils.copyProperties(mallSubTab, mallSubTabDTO);
			mallSubTabDTO.setSortNum(mallSubTab.getSortNum());
		} catch (Exception e) {
			logger.error("执行方法【getMallSubTabData】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mallSubTabDTO;
	}

	/**
	 * 
	 * <p>
	 * Discription:[根据页签id 查询对象和子站主题信息]
	 * </p>
	 */
	@Override
	public MallSubTabECMDTO getMallSubTabECMData(Long id) {
		MallSubTabECMDTO subTabDTO = new MallSubTabECMDTO();
		try {
			subTabDTO = mallSubTabDAO.queryByIdECM(id);
		} catch (Exception e) {
			logger.error("执行方法【getMallSubTabECMData】报错！{}", e);
			throw new RuntimeException(e);
		}
		return subTabDTO;
	}

	/**
	 * 
	 * <p>
	 * Description: [页签功能实现类]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> updateMallSubData(MallSubTabDTO dto) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		MallSubTab mallSubTab = new MallSubTab();
		try {
			BeanUtils.copyProperties(dto, mallSubTab);
			mallSubTabDAO.update(mallSubTab);
			result.setResultMessage("1");
			result.setResult("0");
		} catch (Exception e) {
			logger.error("执行方法【updateMallSubData】报错！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * Description: [页签是否启用]
	 * </p>
	 */
	public ExecuteResult<String> updateStatus(Long id, String status) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			mallSubTabDAO.updateStatus(status, id);
			result.setResult("0");
		} catch (Exception e) {
			logger.error("执行方法【updateMallSubData】报错！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 *
	 * <p>
	 * Description: [页签是否启用]
	 * </p>
	 */
	public ExecuteResult<String> deleteMallSub(Long id) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			mallSubTabDAO.deleteMallSub(id);
			result.setResult("0");
		} catch (Exception e) {
			logger.error("执行方法【deleteMallSub】报错！{}", e);
			result.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return result;
	}

}
