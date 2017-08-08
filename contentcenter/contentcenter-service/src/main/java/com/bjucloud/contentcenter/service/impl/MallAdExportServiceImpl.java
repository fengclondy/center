package com.bjucloud.contentcenter.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.common.utils.DateDealUtils;
import com.bjucloud.contentcenter.dao.MallAdCountDAO;
import com.bjucloud.contentcenter.dao.MallAdvertiseDAO;
import com.bjucloud.contentcenter.domain.MallAdvertise;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallAdCountDTO;
import com.bjucloud.contentcenter.dto.MallAdDTO;
import com.bjucloud.contentcenter.dto.MallAdInDTO;
import com.bjucloud.contentcenter.dto.MallAdQueryDTO;
import com.bjucloud.contentcenter.enums.AdTableTypeEnums;
import com.bjucloud.contentcenter.service.MallAdExportService;

/**
 * <p>
 * Description: 商城广告服务实现
 * </p>
 */
@Service("mallAdExportService")
public class MallAdExportServiceImpl implements MallAdExportService {

	private static final Logger logger = LoggerFactory.getLogger(MallAdExportServiceImpl.class);

	@Resource
	private MallAdvertiseDAO mallAvDao;
	@Resource
	private MallAdCountDAO mallAdCountDAO;

	@Override
	public DataGrid<MallAdDTO> queryMallAdList(Pager page,MallAdQueryDTO mallAdQueryDTO) {
		DataGrid<MallAdDTO> dg = new DataGrid<MallAdDTO>();
		try {
			dg.setTotal(this.mallAvDao.queryCount(mallAdQueryDTO));
			dg.setRows(this.mallAvDao.queryPage(mallAdQueryDTO, page));
		} catch (Exception e) {
			logger.error("执行方法【queryMallAdList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	/**
	 * <p>
	 * Discription:广告详情查询
	 * </p>
	 */

	@Override
	public MallAdDTO getMallAdById(Long id) {
		MallAdDTO dto = this.mallAvDao.queryToById(id);
		return dto;
	}

	/**
	 * <p>
	 * Discription:广告添加
	 * </p>
	 */
	@Override
	public ExecuteResult<String> addMallAd(MallAdInDTO mallAdInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallAdvertise ma = new MallAdvertise();
			BeanUtils.copyProperties(mallAdInDTO, ma);
			ma.setAdTitle(mallAdInDTO.getTitle());
			ma.setAdUrl(mallAdInDTO.getAdURL());
			ma.setSortNum(mallAdInDTO.getSortNumber());
			ma.setStatus(1);
			ma.setTheme_id(mallAdInDTO.getThemeId());
			ma.setPlatformId(mallAdInDTO.getPlatformId());
			if ("0".equals(mallAdInDTO.getPublishFlag())) {
				ma.setStartTime(new Date());
			}
			this.mallAvDao.add(ma);
		} catch (Exception e) {
			logger.error("执行方法【addMallAd】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * 广告删除
	 */
	@Override
	public ExecuteResult<String> delById(Long id) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			int delcount = mallAvDao.delete(id);
			if (delcount > 0) {
				er.setResult("操作成功");
			} else {
				er.addErrorMsg("操作失败");
			}
		} catch (Exception e) {
			er.addErrorMsg("操作失败");
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:广告修改
	 * </p>
	 */
	@Override
	public ExecuteResult<String> modifyMallBanner(MallAdInDTO mallAdInDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallAdvertise ma = new MallAdvertise();
			BeanUtils.copyProperties(mallAdInDTO, ma);
			ma.setAdTitle(mallAdInDTO.getTitle());
			ma.setAdUrl(mallAdInDTO.getAdURL());
			ma.setSortNum(mallAdInDTO.getSortNumber());
			ma.setStatus(1);
			ma.setTheme_id(mallAdInDTO.getThemeId());
			if ("0".equals(mallAdInDTO.getPublishFlag())) {
				ma.setStartTime(new Date());
			}
			this.mallAvDao.update(ma);
		} catch (Exception e) {
			logger.error("执行方法【modifyMallBanner】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	/**
	 * <p>
	 * Discription:广告上下架
	 * </p>
	 * 
	 * @param id
	 * @param publishFlag
	 */
	@Override
	public ExecuteResult<String> modifyMallAdStatus(Long id, String publishFlag) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		MallAdDTO mallAdDTO = null;
		MallAdvertise ma = null;
		Date date = null;
		try {
			mallAdDTO = this.mallAvDao.queryToById(id);
			if ("0".equals(publishFlag)) {
				date = new Date();
			}
			ma = new MallAdvertise();
			BeanUtils.copyProperties(mallAdDTO, ma);
			ma.setAdTitle(mallAdDTO.getTitle());
			ma.setAdUrl(mallAdDTO.getAdURL());
			ma.setSortNum(mallAdDTO.getSortNumber());
			ma.setStatus(Integer.valueOf(publishFlag));
			ma.setTheme_id(mallAdDTO.getThemeId());
			ma.setStartTime(date);
			this.mallAvDao.update(ma);
		} catch (Exception e) {
			logger.error("执行方法【modifyMallAdStatus】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

	@Override
	public ExecuteResult<MallAdCountDTO> saveMallAdCount(Long mallAdId, Long adTableType) throws Exception {
		ExecuteResult<MallAdCountDTO> executeResult = new ExecuteResult<MallAdCountDTO>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date().getTime());

		MallAdCountDTO mallAdCountDTO = new MallAdCountDTO();
		mallAdCountDTO.setMallAdId(mallAdId);
		mallAdCountDTO.setClickDateBegin(date);
		mallAdCountDTO.setClickDateEnd(date);
		mallAdCountDTO.setTableType(adTableType);
		List<MallAdCountDTO> listMallAdCount = mallAdCountDAO.queryList(mallAdCountDTO, null);

		if (listMallAdCount.size() == 0) {
			mallAdCountDAO.add(mallAdCountDTO);
		} else {
			mallAdCountDTO = listMallAdCount.get(0);
			mallAdCountDAO.update(mallAdCountDTO);
		}
		executeResult.setResult(mallAdCountDTO);
		return executeResult;
	}

	@Override
	public MallAdCountDTO findMallAdCountById(long id) {
		MallAdCountDTO mallAdCountDTO = mallAdCountDAO.queryById(id);
		if (mallAdCountDTO != null) {
			this.buildMallAdInfo(mallAdCountDTO);
		}
		return mallAdCountDTO;
	}

	@Override
	public DataGrid<MallAdCountDTO> findAdCountList(MallAdCountDTO mallAdCount, Pager pager) {
		DataGrid<MallAdCountDTO> result = new DataGrid<MallAdCountDTO>();
		long count = mallAdCountDAO.queryCount(mallAdCount);
		if (count > 0) {
			List<MallAdCountDTO> listMallAdCount = mallAdCountDAO.queryList(mallAdCount, pager);
			for (MallAdCountDTO mallAdCountDTO : listMallAdCount) {
				this.buildMallAdInfo(mallAdCountDTO);
			}
			result.setRows(listMallAdCount);
		}
		result.setTotal(count);
		return result;
	}

	public MallAdCountDTO buildMallAdInfo(MallAdCountDTO mallAdCountDTO) {
		if (AdTableTypeEnums.advertise.ordinal() == mallAdCountDTO.getTableType()) {
			MallAdvertise mallAdvertise = mallAvDao.queryById(mallAdCountDTO.getMallAdId());
			mallAdCountDTO.setMallAdName(mallAdvertise.getAdTitle());
			mallAdCountDTO.setMallAdType(mallAdvertise.getAdType() + "");
		}
		return mallAdCountDTO;
	}

	@Override
	public DataGrid<AdReportOutDTO> queryReportList(AdReportInDTO adReportInDto, Pager pager) {
		// 去掉日期格式
		adReportInDto.setClickDate(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDate()));
		adReportInDto.setClickDateBegin(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDateBegin()));
		adReportInDto.setClickDateEnd(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDateEnd()));

		DataGrid<AdReportOutDTO> dtos = new DataGrid<AdReportOutDTO>();
		List<AdReportOutDTO> res = mallAdCountDAO.queryReportList(adReportInDto, pager);
		long count = mallAdCountDAO.queryReportCount(adReportInDto);
		if (adReportInDto.getDateFormat() != null && adReportInDto.getDateFormat().length() > 0) {
			for (AdReportOutDTO dto : res) {
				dto.setClickDate(DateDealUtils.getDateStrToStr(dto.getClickDate(), "yyyyMMdd", adReportInDto.getDateFormat()));
			}
		}
		dtos.setRows(res);
		dtos.setTotal(count);
		return dtos;
	}

	@Override
	public DataGrid<MallAdDTO> getMobileShopAdList(Pager pager, MallAdQueryDTO mallAdQueryDTO) {
		DataGrid<MallAdDTO> dataGrid = new DataGrid<MallAdDTO>();

		try {
			dataGrid.setTotal(this.mallAvDao.queryMobileShopAdCount(mallAdQueryDTO));
			dataGrid.setRows(this.mallAvDao.queryMobileShopAdList(pager,mallAdQueryDTO));
		} catch (Exception e) {
			logger.error("执行方法【queryMallAdList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dataGrid;

	}

	@Override
	public MallAdDTO getMobileShopAdById(Long id) {
		MallAdDTO dto = this.mallAvDao.queryMobileShopAdById(id);
		return dto;
	}

	@Override
	public ExecuteResult<String> modifyMobileShopAd(MallAdDTO mallAdDTO) {
		ExecuteResult<String> er = new ExecuteResult<String>();
		try {
			MallAdvertise ma = new MallAdvertise();
			BeanUtils.copyProperties(mallAdDTO, ma);
			ma.setAdTitle(mallAdDTO.getTitle());
			ma.setAdUrl(mallAdDTO.getAdURL());
			ma.setSortNum(mallAdDTO.getSortNumber());
			ma.setStatus(mallAdDTO.getStatus());
			ma.setStartTime(new Date());
			ma.setModified(new Date());
			this.mallAvDao.update(ma);
		} catch (Exception e) {
			logger.error("执行方法【modifyMallBanner】报错！{}", e);
			er.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		return er;
	}

}
