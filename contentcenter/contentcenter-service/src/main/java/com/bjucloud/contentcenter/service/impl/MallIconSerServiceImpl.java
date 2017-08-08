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
import com.bjucloud.contentcenter.dao.MallIconSerDAO;
import com.bjucloud.contentcenter.domain.MallIconSer;
import com.bjucloud.contentcenter.dto.MallIconSerDTO;
import com.bjucloud.contentcenter.dto.MallIconSerInDTO;
import com.bjucloud.contentcenter.service.MallIconSerService;

@Service("mallIconSerService")
public class MallIconSerServiceImpl implements MallIconSerService {

	@Resource
	private MallIconSerDAO mallIconSerDAO;
	private final static Logger LOGGER = LoggerFactory.getLogger(MallIconSerServiceImpl.class);

	/**
	 * <p>
	 * Discription:[图标服务查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallIconSerDTO> queryMallIconSerList(String publishFlag, Pager page) {
		LOGGER.debug("------------------导航查询接口queryMallIconSerList,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page));
		DataGrid<MallIconSerDTO> dg = new DataGrid<MallIconSerDTO>();
		List<MallIconSerDTO> list = this.mallIconSerDAO.queryListDTO(null, publishFlag, page);
		dg.setTotal(this.mallIconSerDAO.queryCountDTO(null, publishFlag));
		dg.setRows(list);
		LOGGER.debug("------------------服务图标查询接口，返回结果：" + JSON.toJSONString(dg));
		return dg;
	}

	/**
	 * <p>
	 * Discription:[图标服务条件查询]
	 * </p>
	 */
	@Override
	public DataGrid<MallIconSerDTO> queryMallIconSerList(MallIconSerDTO mallIconSerDTO, String publishFlag, Pager page) {
		DataGrid<MallIconSerDTO> dg = new DataGrid<MallIconSerDTO>();
		try {
			LOGGER.debug("------------------服务图标查询接口queryMallIconSerList,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page));
			List<MallIconSerDTO> list = this.mallIconSerDAO.queryListDTOToAdmin(mallIconSerDTO, null, publishFlag, page);
			dg.setTotal(this.mallIconSerDAO.queryCountDTOToAdmin(mallIconSerDTO, null, publishFlag));
			dg.setRows(list);
			LOGGER.debug("------------------服务图标查询接口，返回结果：" + JSON.toJSONString(dg));
		} catch (Exception e) {
			LOGGER.error("执行方法【queryMallIconSerList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	/**
	 * <p>
	 * Discription:[图标服务根据id查询]
	 * </p>
	 */
	@Override
	public MallIconSerDTO getMallIconSerById(Integer id) {
		MallIconSer mallIconSer = this.mallIconSerDAO.queryById(id);
		MallIconSerDTO mallIconSerDTO = new MallIconSerDTO();
		try {
			LOGGER.debug("------------------执行方法 getMallIconSerById ");
			BeanUtils.copyProperties(mallIconSer, mallIconSerDTO);
			mallIconSerDTO.setIconType(mallIconSer.getIconType());
			mallIconSerDTO.setTitle(mallIconSer.getTitle());
			mallIconSerDTO.setSortNum(mallIconSer.getSortNum());
			mallIconSerDTO.setSugSize(mallIconSer.getSugSize());
			mallIconSerDTO.setIconLink(mallIconSer.getIconLink());
			LOGGER.debug("------------------执行方法getMallIconSerById方法结束");
		} catch (Exception e) {
			LOGGER.error("执行方法【getMallIconSerById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mallIconSerDTO;
	}

	/**
	 * <p>
	 * Discription:[图标服务新增]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallIconSer(MallIconSerInDTO mallIconSerInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			LOGGER.debug("------------------执行方法 addMallIconSer ");
			MallIconSer mallIconSer = new MallIconSer();
			BeanUtils.copyProperties(mallIconSerInDTO, mallIconSer);
			mallIconSer.setIconLink(mallIconSerInDTO.getIconLink());
			mallIconSer.setIconType(mallIconSerInDTO.getIconType());
			mallIconSer.setSortNum(mallIconSerInDTO.getSortNum());
			mallIconSer.setStatus(mallIconSerInDTO.getStatus());
			mallIconSer.setSugSize(mallIconSerInDTO.getSugSize());
			mallIconSer.setTitle(mallIconSerInDTO.getTitle());
			this.mallIconSerDAO.add(mallIconSer);
			LOGGER.debug("------------------执行方法 addMallIconSer结束");
		} catch (Exception e) {
			LOGGER.error("执行方法【addMallIconSer】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[图标服务根据id修改状态，0，可用；1，不可用]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallIconSerStatus(Integer id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallIconSerDAO.updateStatusById(id, publishFlag);
		} catch (Exception e) {
			LOGGER.debug("执行motifyMallIconSerStatus报错{}", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		er.setResultMessage("调用到了，motifyMallIconSerStatus …………id:" + id + "|publishFlag:" + publishFlag);
		return er;
	}

	/**
	 * <p>
	 * Discription:[图标服务根据id修改排序号]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallIconSerSort(Integer id, Integer sortNum) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallIconSerDAO.updateSortNumberById(id, sortNum);
		} catch (Exception e) {
			LOGGER.debug("执行modifyMallIconSerSort报错{}", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		er.setResultMessage("调用到了，modifyMallIconSerSort …………id:" + id + "|sortNum:" + sortNum);
		return er;
	}

	/**
	 * <p>
	 * Discription:[图标服务根据id删除]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> delete(Integer id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallIconSerDAO.delete(id);
		} catch (Exception e) {
			LOGGER.debug("执行delete方法报错{}", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:[图标服务修改对象]
	 * </p>
	 */
	@Override
	public ExecuteResult<String> motifyMallIconSer(MallIconSerInDTO mallIconSerInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			er.setResultMessage("-------------执行方法【motifyNallIconSer】----");
			MallIconSer mallIconSer = new MallIconSer();
			BeanUtils.copyProperties(mallIconSerInDTO, mallIconSer);
			mallIconSer.setId(mallIconSerInDTO.getId());
			mallIconSer.setImgPath(mallIconSer.getImgPath());
			mallIconSer.setIconLink(mallIconSerInDTO.getIconLink());
			mallIconSer.setIconType(mallIconSer.getIconType());
			mallIconSer.setSortNum(mallIconSerInDTO.getSortNum());
			mallIconSer.setStatus(mallIconSer.getStatus());
			mallIconSer.setSugSize(mallIconSer.getSugSize());
			mallIconSer.setTitle(mallIconSer.getTitle());
			this.mallIconSerDAO.update(mallIconSer);
		} catch (Exception e) {
			LOGGER.error("执行方法【motifyMallIconSer】报错！{}");
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<Integer> queryMallIconSerByTitle(String title,Integer id) {
		ExecuteResult<Integer> er = new ExecuteResult<Integer>();
		List<MallIconSerDTO> mallIconSerInDTOs = mallIconSerDAO.queryMallIconSerByTitle(title,id);
		Integer num = mallIconSerInDTOs.size();
		if(num ==0){
			er.setResult(0);
			return er;
		}
		return null;
	}
}
