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
import com.bjucloud.contentcenter.dao.MallNavDAO;
import com.bjucloud.contentcenter.domain.MallNav;
import com.bjucloud.contentcenter.dto.MallNavDTO;
import com.bjucloud.contentcenter.dto.MallNavInDTO;
import com.bjucloud.contentcenter.service.MallNavService;

@Service("mallNavService")
public class MallNavServiceImpl implements MallNavService {

	@Resource
	private MallNavDAO mallNavDAO;
	private final static Logger LOGGER = LoggerFactory.getLogger(MallNavServiceImpl.class);

	/**
	 * <p>
	 * Discription:[首页导航查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallNavDTO> queryMallNavList(String publishFlag, Pager page) {
		LOGGER.debug("------------------导航查询接口,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page));
		DataGrid<MallNavDTO> dg = new DataGrid<MallNavDTO>();
		List<MallNavDTO> list = this.mallNavDAO.queryListDTO(null, publishFlag, page);
		dg.setTotal(this.mallNavDAO.queryCountDTO(null, publishFlag));
		dg.setRows(list);
		LOGGER.debug("------------------导航查询接口，返回结果：" + JSON.toJSONString(dg));
		return dg;
	}

	/**
	 * <p>
	 * Discription:[首页导航条件查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallNavDTO> queryMallNavList(MallNavDTO mallNavDTO, String publishFlag, Pager page) {
		DataGrid<MallNavDTO> dg = new DataGrid<MallNavDTO>();
		try {
			LOGGER.debug("----------------导航查询queryMallNavList---------");
			List<MallNavDTO> list = this.mallNavDAO.queryListDTOToAdmin(mallNavDTO, null, publishFlag, page);
			dg.setTotal(this.mallNavDAO.queryCountDTOToAdmin(mallNavDTO, null, publishFlag));
			dg.setRows(list);
		} catch (Exception e) {
			LOGGER.error("执行方法【queryMallNavList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	/**
	 * <p>
	 * Discription:[首页导航根据id查询]
	 * </p>
	 */
	@Override
	public MallNavDTO getMallNavById(long id) {
		MallNavDTO msd = new MallNavDTO();
		try {
			msd = this.mallNavDAO.queryById(id);
		} catch (Exception e) {
			LOGGER.error("执行方法【getMallNavById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return msd;
	}

	/**
	 * <p>
	 * Discription:[首页导航条件查询]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallNav(MallNavInDTO mallNavInDTO) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			LOGGER.debug("------------执行方法addMallNav-----------");
			MallNav mn = new MallNav();
			mn.setNavLink(mallNavInDTO.getNavLink());
			mn.setNavTitle(mallNavInDTO.getNavTitle());
			mn.setSortNum(mallNavInDTO.getSortNum());
			mn.setStatus(mallNavInDTO.getStatus());
			mn.setThemeId(mallNavInDTO.getThemeId());
			this.mallNavDAO.add(mn);
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
	 * Discription:[首页导航根据id修改状态；0，不可用；1，可用]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallNavStatus(Long id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallNavDAO.updateStatusById(id, publishFlag);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[首页导航根据id修改排序号]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallNavSort(Long id, Integer sortNum) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallNavDAO.updateSortNumberById(id, sortNum);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[首页导航根据id删除]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> delete(Long id) {
		ExecuteResult<String> er = new ExecuteResult();
		try {
			this.mallNavDAO.delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[首页导航修改对象]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallNav(MallNavInDTO mallNavInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			LOGGER.debug("--------------------执行motifyMallNav方法-----------");
			MallNav mn = new MallNav();
			BeanUtils.copyProperties(mallNavInDTO, mn);
			mn.setNavLink(mallNavInDTO.getNavLink());
			mn.setNavTitle(mallNavInDTO.getNavTitle());
			mn.setSortNum(mallNavInDTO.getSortNum());
			mn.setStatus(mallNavInDTO.getStatus());
			mn.setThemeId(mallNavInDTO.getThemeId());
			this.mallNavDAO.update(mn);
			LOGGER.debug("--------------------执行motifyMallNav方法结束----------------");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return er;
	}

}
