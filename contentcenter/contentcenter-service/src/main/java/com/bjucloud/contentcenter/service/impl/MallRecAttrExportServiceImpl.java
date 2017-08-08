package com.bjucloud.contentcenter.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallRecommendAttrDAO;
import com.bjucloud.contentcenter.domain.MallRecommendAttr;
import com.bjucloud.contentcenter.dto.MallRecAttrDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrECMDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrInDTO;
import com.bjucloud.contentcenter.dto.MallRecAttrQueryDTO;
import com.bjucloud.contentcenter.service.MallRecAttrExportService;

@Service("mallRecAttrExportService")
public class MallRecAttrExportServiceImpl implements MallRecAttrExportService {

	private static final Logger logger = LoggerFactory.getLogger(MallRecAttrExportServiceImpl.class);

	@Resource
	private MallRecommendAttrDAO mallRecAttrDAO;

	@Override
	public DataGrid<MallRecAttrDTO> queryMallRecAttrList(Pager page, MallRecAttrQueryDTO mallRecAttrQueryDTO) {
		DataGrid<MallRecAttrDTO> dg = new DataGrid<MallRecAttrDTO>();
		try {
			MallRecommendAttr mra = new MallRecommendAttr();
			BeanUtils.copyProperties(mallRecAttrQueryDTO, mra);
			dg.setTotal(this.mallRecAttrDAO.queryCount(mra));
			dg.setRows(this.mallRecAttrDAO.queryPage(page, mra));
		} catch (Exception e) {
			logger.error("执行方法【queryMallRecAttrList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	@Override
	public MallRecAttrDTO getMallRecAttrById(Long id) {
		MallRecAttrDTO mrad = new MallRecAttrDTO();
		try {
			MallRecommendAttr mra = this.mallRecAttrDAO.queryById(id);
			BeanUtils.copyProperties(mra, mrad);
			mrad.setSortNum(mra.getSortNumber());
		} catch (Exception e) {
			logger.error("执行方法【queryMallRecAttrList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mrad;
	}

	@Override
	public ExecuteResult<String> addMallRecAttr(MallRecAttrInDTO mallRecAttrInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallRecommendAttr mra = new MallRecommendAttr();
			BeanUtils.copyProperties(mallRecAttrInDTO, mra);
			mra.setSortNumber(mallRecAttrInDTO.getSortNum());
			if (mallRecAttrInDTO.getTimeFlag() == 0) {
				mra.setStartTime(new Date());
			}
			mra.setStatus(1);
			this.mallRecAttrDAO.add(mra);
		} catch (Exception e) {
			logger.error("执行方法【addMallRecAttr】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<String> modifyMallRecAttr(MallRecAttrInDTO mallRecAttrInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallRecommendAttr mra = new MallRecommendAttr();
			BeanUtils.copyProperties(mallRecAttrInDTO, mra);
			mra.setSortNumber(mallRecAttrInDTO.getSortNum());
			if (mallRecAttrInDTO.getTimeFlag() == 0) {
				mra.setStartTime(new Date());
			}
			mra.setStatus(1);
			this.mallRecAttrDAO.update(mra);
		} catch (Exception e) {
			logger.error("执行方法【modifyMallRecAttr】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<String> modifyMallRecAttrStatus(Long id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			this.mallRecAttrDAO.modifyMallRecAttrStatus(id, publishFlag);
		} catch (Exception e) {
			logger.error("执行方法【modifyMallRecAttrStatus】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public DataGrid<MallRecAttrECMDTO> equeryMallRecAttrList(Pager page, MallRecAttrECMDTO mallRecAttrECMDTO) {
		DataGrid<MallRecAttrECMDTO> dg = new DataGrid<MallRecAttrECMDTO>();
		dg.setTotal(this.mallRecAttrDAO.equeryCount(mallRecAttrECMDTO));
		dg.setRows(this.mallRecAttrDAO.equeryPage(page, mallRecAttrECMDTO));
		return dg;
	}

	/**
	 * <p>
	 * 根据id推荐
	 * </p>
	 */
	@Override
	public ExecuteResult<String> delById(Long id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			mallRecAttrDAO.delete(id);
			er.setResult("操作成功");
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
		}
		return er;
	}

}
