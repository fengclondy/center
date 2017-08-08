package com.bjucloud.contentcenter.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.alibaba.fastjson.JSON;
import com.bjucloud.contentcenter.dao.MallSpecialSubjectDAO;
import com.bjucloud.contentcenter.dto.MallSpecialSubjectDTO;
import com.bjucloud.contentcenter.service.MallSpecialSubjectExportService;

/**
 * 专题管理功能列表实现
 * @author root
 *
 */
@Service("mallSpecialSubjectExportService")
public class MallSpecialSubjectServiceImpl implements MallSpecialSubjectExportService{
	
	public static final Logger logger = LoggerFactory.getLogger(MallSpecialSubjectServiceImpl.class);
	
	@Resource
	private MallSpecialSubjectDAO mallSpecialSubjectDAO;

	
	/**
	 * 分页查询专题列表
	 */
	@Override
	public DataGrid<MallSpecialSubjectDTO> querySubjectList(Pager page, MallSpecialSubjectDTO mallSpecialSubjectDTO) {
		logger.debug("--------------------专题管理列表查询接口---------------------");
		DataGrid<MallSpecialSubjectDTO> dg = new DataGrid<MallSpecialSubjectDTO>();
		try {
			logger.debug("------------------专题管理列表查询接口 ,  传入参数 page="+JSON.toJSONString(page)+",mallSpecialSubjectDTO = "+JSON.toJSONString(mallSpecialSubjectDTO)+"----------------------------");
			dg.setTotal(mallSpecialSubjectDAO.querySubjectCount(mallSpecialSubjectDTO));
			dg.setRows(mallSpecialSubjectDAO.querySubjectList(page, mallSpecialSubjectDTO));
			logger.debug("-----------------专题管理列表查询接口 ,  返回结果: "+JSON.toJSONString(dg)+"----------------------");
		} catch (Exception e) {
			logger.error("执行方法【querySubjectList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	
	/**
	 * 添加专题
	 */
	@Override
	public ExecuteResult<String> addSpecialSubject(MallSpecialSubjectDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallSpecialSubjectDAO.add(dto);
		} catch (Exception e) {
			logger.error("执行方法【addSpecialSubject】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	
	/**
	 * 修改专题
	 */
	@Override
	public ExecuteResult<String> modifySpecialSubject(MallSpecialSubjectDTO dto) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallSpecialSubjectDAO.update(dto);
		} catch (Exception e) {
			logger.error("执行方法【modifySpecialSubject】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	
	/**
	 * 根据id获取专题
	 */
	@Override
	public MallSpecialSubjectDTO getSpecialSubjectById(Long id) {
		return mallSpecialSubjectDAO.getSpecialSubjectById(id);
	}

}
