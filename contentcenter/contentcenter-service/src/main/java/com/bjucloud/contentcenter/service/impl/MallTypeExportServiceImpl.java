package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.bjucloud.contentcenter.dao.MallTypeDAO;
import com.bjucloud.contentcenter.dao.MallTypeSubDAO;
import com.bjucloud.contentcenter.dto.HTDEditDetailInfoDTO;
import com.bjucloud.contentcenter.dto.HTDMallTypeDTO;
import com.bjucloud.contentcenter.dto.HTDMallTypeSubDTO;
import com.bjucloud.contentcenter.service.MallTypeExportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * <p>
 * Description: [前台类目维护接口的实现类]
 * </p>
 */
@Service("mallTypeExportService")
public class MallTypeExportServiceImpl implements MallTypeExportService {
	private final static Logger LOGGER = LoggerFactory.getLogger(MallTypeExportServiceImpl.class);

	@Resource
	private MallTypeDAO mallTypeDAO;
	@Resource
	private MallTypeSubDAO mallTypeSubDAO;

	@Override
	public ExecuteResult<HTDMallTypeDTO> getMallTypeById(Long id) {
		ExecuteResult<HTDMallTypeDTO> executeResult = new ExecuteResult<HTDMallTypeDTO>();
		try {
			if (id == null || id == 0L){
				executeResult.addErrorMessage("参数不能为空！");
				return executeResult;
			}
			HTDMallTypeDTO mallTypeDTO = mallTypeDAO.selectById(id);
			List<HTDMallTypeSubDTO> mallTypeSubDTOs = mallTypeSubDAO.selectByTypeId(id);
			mallTypeDTO.setMallTypeSubDTOs(mallTypeSubDTOs);

			executeResult.setResult(mallTypeDTO);
			executeResult.setResultMessage("Logo查询成功");
		}catch (RuntimeException e){
			LOGGER.error("执行方法：【getMallTypeById】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}
	/**
	 *
	 * <p>
	 * Discription:[方法功能中文描述:修改前台类目]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallType(HTDMallTypeDTO mallTypeDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			mallTypeDAO.update(mallTypeDTO);
			List<HTDMallTypeSubDTO> mallTypeSubDTOs = mallTypeDTO.getMallTypeSubDTOs();
			mallTypeSubDAO.deleteByTypeId(mallTypeDTO.getId());
			if (mallTypeSubDTOs != null && mallTypeSubDTOs.size() > 0){
				for (HTDMallTypeSubDTO mallTypeSubDTO : mallTypeSubDTOs){
					mallTypeSubDTO.setTypeId(mallTypeDTO.getId());
					mallTypeSubDAO.insert(mallTypeSubDTO);
				}
			}
			executeResult.setResultMessage("编辑成功");
		} catch (Exception e) {
			LOGGER.error("执行方法【modifyMallType】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> addMallType(HTDMallTypeDTO mallTypeDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			mallTypeDAO.insert(mallTypeDTO);
			List<HTDMallTypeSubDTO> mallTypeSubDTOs = mallTypeDTO.getMallTypeSubDTOs();
			if (mallTypeSubDTOs != null && mallTypeSubDTOs.size() > 0){
				for (HTDMallTypeSubDTO mallTypeSubDTO : mallTypeSubDTOs){
					mallTypeSubDTO.setTypeId(mallTypeDTO.getId());
					mallTypeSubDAO.insert(mallTypeSubDTO);
				}
			}
			executeResult.setResultMessage("保存成功");
		} catch (Exception e) {
			LOGGER.error("执行方法【addMallType】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public DataGrid<HTDMallTypeDTO> queryAll(Pager page) {
		DataGrid<HTDMallTypeDTO> dataGrid = new DataGrid<HTDMallTypeDTO>();
		try{
			List<HTDMallTypeDTO> list = mallTypeDAO.selectAll(page);
			Long count = mallTypeDAO.selectAllCount();
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
		}catch (Exception e){
			LOGGER.error("执行方法【queryAll】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dataGrid;
	}


	@Override
	public DataGrid<HTDMallTypeDTO> queryByName(String name) {
		DataGrid<HTDMallTypeDTO> result = new DataGrid<HTDMallTypeDTO>();
		try {
			List<HTDMallTypeDTO> hotWordDTOs = mallTypeDAO.selectByName(name);
			result.setRows(hotWordDTOs);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}

}