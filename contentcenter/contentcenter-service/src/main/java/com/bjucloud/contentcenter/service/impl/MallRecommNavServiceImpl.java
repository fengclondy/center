package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallRecommNavDAO;
import com.bjucloud.contentcenter.domain.MallRecommNav;
import com.bjucloud.contentcenter.dto.MallRecommNavDTO;
import com.bjucloud.contentcenter.dto.MallRecommNavECMDTO;
import com.bjucloud.contentcenter.service.MallRecommNavService;

@Service("mallRecommNavService")
public class MallRecommNavServiceImpl implements MallRecommNavService {

	@Resource
	private MallRecommNavDAO mallRecommNavDAO;
	private final static Logger LOGGER = LoggerFactory.getLogger(MallRecommNavServiceImpl.class);

	/**
	 * <p>
	 * Discription:[楼层导航查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallRecommNavDTO> queryMallRecNavList(String publishFlag, Pager page) {
		LOGGER.debug("------------------导航查询接口,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page));
		DataGrid<MallRecommNavDTO> dg = new DataGrid<MallRecommNavDTO>();
		List<MallRecommNavDTO> list = this.mallRecommNavDAO.queryListDTO(null, publishFlag, page);
		dg.setTotal(this.mallRecommNavDAO.queryCountDTO(null, publishFlag));
		dg.setRows(list);
		LOGGER.debug("------------------导航查询接口，返回结果：" + JSON.toJSONString(dg));
		return dg;
	}

	/**
	 * <p>
	 * Discription:[楼层导航条件查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallRecommNavDTO> queryMallRecNavList(MallRecommNavDTO mallRecommNavDTO, String publishFlag, Pager page) {
		DataGrid<MallRecommNavDTO> dg = new DataGrid<MallRecommNavDTO>();
		try {
			LOGGER.debug("----------------导航查询queryMallNavList---------");
			List<MallRecommNavDTO> list = this.mallRecommNavDAO.queryListDTOToAdmin(mallRecommNavDTO, null, publishFlag, page);
			dg.setTotal(this.mallRecommNavDAO.queryCountDTOToAdmin(mallRecommNavDTO, null, publishFlag));
			dg.setRows(list);
		} catch (Exception e) {
			LOGGER.error("执行方法【queryMallNavList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	/**
	 * <p>
	 * Discription:[楼层导航根据id查询]
	 * </p>
	 */
	@Override
	public MallRecommNavDTO getMallRecNavById(Integer id) {
		MallRecommNavDTO mrd = new MallRecommNavDTO();
		MallRecommNav mallRecommNav = this.mallRecommNavDAO.queryById(id);
		try {
			mrd.setId(mallRecommNav.getId());
			mrd.setIsImg(mallRecommNav.getIsImg());
			mrd.setnType(mallRecommNav.getnType());
			mrd.setPicSrc(mallRecommNav.getPicSrc());
			mrd.setRecId(mallRecommNav.getRecId());
			mrd.setSortNum(mallRecommNav.getSortNum());
			mrd.setStatus(mallRecommNav.getStatus());
			mrd.setTitle(mallRecommNav.getTitle());
			mrd.setUrl(mallRecommNav.getUrl());
		} catch (Exception e) {
			LOGGER.error("执行方法【getMallNavById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mrd;
	}

	public MallRecommNavECMDTO getRecNavECMById(Integer id) {
		MallRecommNavECMDTO mallNav = new MallRecommNavECMDTO();
		try {
			mallNav = this.mallRecommNavDAO.queryRecNavECMById(id);
		} catch (Exception e) {
			LOGGER.error("执行方法【queryRecNavECMById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mallNav;
	}

	/**
	 * <p>
	 * Discription:[楼层导航新增]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallRecNav(MallRecommNavDTO mallRecommNavDTO) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			LOGGER.debug("------------执行方法addMallNav-----------");
			MallRecommNav mn = new MallRecommNav();
			mn.setIsImg(mallRecommNavDTO.getIsImg());
			mn.setnType(mallRecommNavDTO.getnType());
			mn.setPicSrc(mallRecommNavDTO.getPicSrc());
			mn.setRecId(mallRecommNavDTO.getRecId());
			mn.setSortNum(mallRecommNavDTO.getSortNum());
			mn.setStatus(mallRecommNavDTO.getStatus());
			mn.setTitle(mallRecommNavDTO.getTitle());
			mn.setUrl(mallRecommNavDTO.getUrl());
			this.mallRecommNavDAO.add(mn);
			LOGGER.debug("------------执行方法addMallNav结束------------");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[楼层导航根据id修改状态，0，不可用；1，可用]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallRecNavStatus(Integer id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallRecommNavDAO.updateStatusById(id, publishFlag);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[楼层导航修改对象]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallRecNav(MallRecommNavDTO mallRecommNavDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			LOGGER.debug("--------------------执行motifyMallNav方法-----------");
			MallRecommNav mn = new MallRecommNav();
			BeanUtils.copyProperties(mallRecommNavDTO, mn);
			mn.setId(mallRecommNavDTO.getId());
			mn.setIsImg(mallRecommNavDTO.getIsImg());
			mn.setnType(mallRecommNavDTO.getnType());
			mn.setPicSrc(mallRecommNavDTO.getPicSrc());
			mn.setRecId(mallRecommNavDTO.getRecId());
			mn.setSortNum(mallRecommNavDTO.getSortNum());
			mn.setStatus(mallRecommNavDTO.getStatus());
			mn.setTitle(mallRecommNavDTO.getTitle());
			mn.setUrl(mallRecommNavDTO.getUrl());
			this.mallRecommNavDAO.update(mn);
			LOGGER.debug("--------------------执行motifyMallNav方法结束----------------");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[楼层导航根据id修改排序号]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallRecNavSort(Integer id, Integer sortNum) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallRecommNavDAO.updateSortNumberById(id, sortNum);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[楼层导航根据id删除]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> delete(Integer id) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallRecommNavDAO.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public DataGrid<MallRecommNavECMDTO> equeryMallRecNavList(MallRecommNavECMDTO mallRecommNavECMDTO, String publishFlag, Pager page) {
		DataGrid<MallRecommNavECMDTO> dg = new DataGrid<MallRecommNavECMDTO>();
		LOGGER.debug("----------------导航查询queryMallNavList---------");
		List<MallRecommNavECMDTO> list = this.mallRecommNavDAO.equeryListDTOToAdmin(mallRecommNavECMDTO, null, publishFlag, page);
		dg.setTotal(this.mallRecommNavDAO.queryCountECMDTOToAdmin(mallRecommNavECMDTO, null, publishFlag));
		dg.setRows(list);
		return dg;
	}

}
