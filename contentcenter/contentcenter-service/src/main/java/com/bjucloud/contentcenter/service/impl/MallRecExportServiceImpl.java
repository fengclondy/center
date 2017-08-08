package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallRecMybatisDAO;
import com.bjucloud.contentcenter.dto.MallRecDTO;
import com.bjucloud.contentcenter.service.MallRecExportService;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:供远程调用的接口的实现 商城楼层推荐]
 * </p>
 */
@Service("mallRecExportService")
public class MallRecExportServiceImpl implements MallRecExportService {

	private static final Logger logger = LoggerFactory.getLogger(MallRecExportServiceImpl.class);

	@Resource
	private MallRecMybatisDAO mallRecMybatisDAO;

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:新建楼层的远程调用方法的接口实现]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallRec(MallRecDTO mallRecDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			mallRecMybatisDAO.add(mallRecDTO);
			executeResult.setResultMessage("保存成功");
		} catch (Exception e) {
			logger.error("执行方法【addMallRec】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:根据id查询楼层的详情]
	 * </p>
	 */
	@Override
	public MallRecDTO getMallRecById(Long id) {
		MallRecDTO dto = null;
		try {
			dto = mallRecMybatisDAO.queryDTOById(id);
		} catch (Exception e) {
			logger.error("执行方法【getMallRecById】报错！{}", e);
			throw new RuntimeException(e);
		}

		return dto;
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:查询楼层的列表 分页]
	 * </p>
	 */
	@Override
	public DataGrid<MallRecDTO> queryMallRecList(MallRecDTO mallRecDTO, Pager page) {
		// 查询数据库中所有的数据 返回list
		DataGrid<MallRecDTO> dataGrid = new DataGrid<MallRecDTO>();
		try {
			List<MallRecDTO> listMallRecDTO = mallRecMybatisDAO.queryDTOList(mallRecDTO, page);
			// 查询数据库中所有的数据 返回个数
			long size = mallRecMybatisDAO.queryCount(mallRecDTO);
			// 将数据库中的总条数set到dataGrid的total中
			dataGrid.setTotal(size);
			// 将数据库的数据set到dataGrid的总行数中
			dataGrid.setRows(listMallRecDTO);

		} catch (Exception e) {
			logger.error("执行方法【queryMallRecList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:修改楼层]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallRec(MallRecDTO mallRecDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			mallRecMybatisDAO.update(mallRecDTO);
			executeResult.setResultMessage("编辑成功");
		} catch (Exception e) {
			logger.error("执行方法【modifyMallRec】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	/**
	 * 
	 * <p>
	 * Discription:[方法功能中文描述:楼层的上下架]
	 * </p>
	 * 
	 * @param id
	 * @param publishFlag
	 */
	@Override
	public ExecuteResult<String> modifyMallRecStatus(Long id, String publishFlag) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			// 上架状态
			if ("1".equals(publishFlag)) {
				// 根据id获取楼层信息
				MallRecDTO mallRecDTO = mallRecMybatisDAO.queryDTOById(id);
				// 状态（上架）
				mallRecDTO.setStatusDTO(1);
				// 根据 楼层号和状态 获取到当前楼层号发布的数量
				long size = mallRecMybatisDAO.queryCount(mallRecDTO);
				// 如果当前楼层号已经发布的数量>0 不允许将这个楼层号进行发布 返回错误信息给前台
				if (size > 0) {
					executeResult.setResultMessage("存在已经发布的楼层");
					executeResult.setResult("0");
					return executeResult;
				}
			}
			// 不是上架状态 可以 发布
			mallRecMybatisDAO.modifyMallRecStatus(id, publishFlag);
			executeResult.setResultMessage("保存成功");
			executeResult.setResult("1");
		} catch (Exception e) {
			logger.error("执行方法【modifyMallRecStatus】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<String> deleteMallRec(Long id) {
		ExecuteResult<String> executeResult = new ExecuteResult<String>();
		try {
			mallRecMybatisDAO.delete(id);
			executeResult.setResultMessage("删除成功");
			executeResult.setResult("1");
		} catch (Exception e) {
			logger.error("执行方法【delete】报错！{}", e);
			executeResult.getErrorMessages().add(e.getMessage());
		}
		return executeResult;
	}
}
