package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallRecfloorAdverDAO;
import com.bjucloud.contentcenter.domain.MallRecfloorAdver;
import com.bjucloud.contentcenter.dto.MallRecfloorAdverDTO;
import com.bjucloud.contentcenter.service.MallRecfloorAdverService;

@Service("mallRecfloorAdverService")
public class MallRecfloorAdverServiceImpl implements MallRecfloorAdverService {

	@Resource
	private MallRecfloorAdverDAO mallRecfloorAdverDAO;

	private final static Logger LOGGER = LoggerFactory.getLogger(MallRecfloorAdverServiceImpl.class);

	@Override
	public DataGrid<MallRecfloorAdverDTO> queryList(MallRecfloorAdverDTO mallRecfloorAdverDTO, String publishFlag,Pager page) {
		DataGrid<MallRecfloorAdverDTO> dg = new DataGrid<MallRecfloorAdverDTO>();
		try {
			List<MallRecfloorAdverDTO> queryList = mallRecfloorAdverDAO.queryListDTO(mallRecfloorAdverDTO, publishFlag,	page);
			Long queryCount = mallRecfloorAdverDAO.queryCountDTO(mallRecfloorAdverDTO, publishFlag);
			dg.setRows(queryList);
			dg.setTotal(queryCount);
		} catch (Exception e) {
			LOGGER.error("执行方法【queryList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	@Override
	public MallRecfloorAdverDTO getMallRecfloorAdverById(Integer id) {
		MallRecfloorAdverDTO mallRecfloorAdverDTO = new MallRecfloorAdverDTO();
		MallRecfloorAdver mallRecfloorAdver = mallRecfloorAdverDAO.queryById(id);
		try {
			mallRecfloorAdverDTO.setId(mallRecfloorAdver.getId());
			mallRecfloorAdverDTO.setAdType(mallRecfloorAdver.getAdType());
			mallRecfloorAdverDTO.setPicSrc(mallRecfloorAdver.getPicSrc());
			mallRecfloorAdverDTO.setSortNum(mallRecfloorAdver.getSortNum());
			mallRecfloorAdverDTO.setTitle(mallRecfloorAdver.getTitle());
			mallRecfloorAdverDTO.setSubTitle(mallRecfloorAdver.getSubTitle());
			mallRecfloorAdverDTO.setAdKeyword(mallRecfloorAdver.getAdKeyword());
			mallRecfloorAdverDTO.setUrl(mallRecfloorAdver.getUrl());
			mallRecfloorAdverDTO.setRecId(mallRecfloorAdver.getRecId());
			mallRecfloorAdverDTO.setStatus(mallRecfloorAdver.getStatus());
			mallRecfloorAdverDTO.setCreated(mallRecfloorAdver.getCreated());
			mallRecfloorAdverDTO.setModified(mallRecfloorAdver.getModified());
		} catch (Exception e) {
			LOGGER.error("执行方法【getMallRecfloorAdverById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mallRecfloorAdverDTO;
	}

	@Override
	public ExecuteResult<String> addMallRecfloorAdver(MallRecfloorAdverDTO mallRecfloorAdverDTO) {

		ExecuteResult<String> er = new ExecuteResult();
		try {
			LOGGER.debug("------------执行方法addMallRecfloorAdver-----------");
			MallRecfloorAdver mfa = new MallRecfloorAdver();
			mfa.setAdType(mallRecfloorAdverDTO.getAdType());
			mfa.setPicSrc(mallRecfloorAdverDTO.getPicSrc());
			mfa.setSortNum(mallRecfloorAdverDTO.getSortNum());
			mfa.setTitle(mallRecfloorAdverDTO.getTitle());
			mfa.setSubTitle(mallRecfloorAdverDTO.getSubTitle());
			mfa.setAdKeyword(mallRecfloorAdverDTO.getAdKeyword());
			mfa.setUrl(mallRecfloorAdverDTO.getUrl());
			mfa.setRecId(mallRecfloorAdverDTO.getRecId());
			mfa.setStatus(mallRecfloorAdverDTO.getStatus());
			this.mallRecfloorAdverDAO.add(mfa);
			LOGGER.debug("------------执行方法addMallRecfloorAdver结束-----------");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return er;
	}

	@Override
	public ExecuteResult<String> motifyMallRecfloorAdverStatus(Integer id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallRecfloorAdverDAO.updateStatusById(id, publishFlag);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

		return er;
	}

	@Override
	public ExecuteResult<String> delete(Integer id) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallRecfloorAdverDAO.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<String> update(MallRecfloorAdverDTO mallRecfloorAdverDTO) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			LOGGER.debug("------------执行方法addMallRecfloorAdver-----------");
			MallRecfloorAdver mfa = new MallRecfloorAdver();
			mfa.setId(mallRecfloorAdverDTO.getId());
			mfa.setAdType(mallRecfloorAdverDTO.getAdType());
			mfa.setPicSrc(mallRecfloorAdverDTO.getPicSrc());
			mfa.setSortNum(mallRecfloorAdverDTO.getSortNum());
			mfa.setTitle(mallRecfloorAdverDTO.getTitle());
			mfa.setSubTitle(mallRecfloorAdverDTO.getSubTitle());
			mfa.setAdKeyword(mallRecfloorAdverDTO.getAdKeyword());
			mfa.setUrl(mallRecfloorAdverDTO.getUrl());
			mfa.setRecId(mallRecfloorAdverDTO.getRecId());
			mfa.setStatus(mallRecfloorAdverDTO.getStatus());
			this.mallRecfloorAdverDAO.update(mfa);
			LOGGER.debug("------------执行方法addMallRecfloorAdver结束-----------");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

}
