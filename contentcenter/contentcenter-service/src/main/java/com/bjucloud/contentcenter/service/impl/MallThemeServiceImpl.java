package com.bjucloud.contentcenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.MallThemeDAO;
import com.bjucloud.contentcenter.domain.MallTheme;
import com.bjucloud.contentcenter.dto.MallThemeDTO;
import com.bjucloud.contentcenter.service.MallThemeService;

@Service("mallThemeService")
public class MallThemeServiceImpl implements MallThemeService {

	@Resource
	private MallThemeDAO mallThemeDao;

	@Override
	public DataGrid<MallThemeDTO> queryMallThemeList(MallThemeDTO mallThemeDTO, String publishFlag, Pager page) {
		DataGrid<MallThemeDTO> dg = new DataGrid<MallThemeDTO>();

		List<MallThemeDTO> list = mallThemeDao.queryListDTO(mallThemeDTO, publishFlag, page);
		dg.setRows(list);
		dg.setTotal(mallThemeDao.queryListCountDTO(mallThemeDTO, publishFlag));

		return dg;
	}

	@Override
	public MallThemeDTO getMallThemeById(long id) {
		MallThemeDTO dto = new MallThemeDTO();
		dto = mallThemeDao.queryById(id);
		return dto;
	}

	@Override
	public ExecuteResult<String> addMallTheme(MallThemeDTO mallThemeDTO) {

		ExecuteResult er = new ExecuteResult();
		// try{
		MallTheme mt = new MallTheme();
		if (mallThemeDTO.getAddressId() != null || ("").equals(mallThemeDTO.getAddressId())) {
			mt.setAddressId(mallThemeDTO.getAddressId());
		}
		if (mallThemeDTO.getcId() != null || ("").equals(mallThemeDTO.getcId())) {
			mt.setcId(mallThemeDTO.getcId());
		}
		if (mallThemeDTO.getClev() != null || ("").equals(mallThemeDTO.getClev())) {
			mt.setClev(mallThemeDTO.getClev());
		}
		if (mallThemeDTO.getColor() != null || ("").equals(mallThemeDTO.getColor())) {
			mt.setColor(mallThemeDTO.getColor());
		}
		if (mallThemeDTO.getColorb() != null || ("").equals(mallThemeDTO.getColorb())) {
			mt.setColorb(mallThemeDTO.getColorb());
		}
		if (mallThemeDTO.getSortNum() != null || ("").equals(mallThemeDTO.getSortNum())) {
			mt.setSortNum(mallThemeDTO.getSortNum());
		}
		mt.setStatus(mallThemeDTO.getStatus());
		mt.setThemeName(mallThemeDTO.getThemeName());
		mt.setType(mallThemeDTO.getType());
		mt.setUserId(mallThemeDTO.getUserId());
		mallThemeDao.add(mt);
		return er;
	}

	@Override
	public ExecuteResult<String> delete(Long id) {
		ExecuteResult er = new ExecuteResult();
		mallThemeDao.delete(id);
		return er;
	}

	@Override
	public ExecuteResult<String> motifyMallThemeStatus(Long id, String publishFlag) {
		ExecuteResult er = new ExecuteResult();
		int idd = mallThemeDao.updateStatusById(id, publishFlag);
		er.setResult(idd);
		return er;
	}

	@Override
	public ExecuteResult<String> motifyMallTheme(MallThemeDTO mallThemeDTO) {
		ExecuteResult er = new ExecuteResult();
		MallTheme mt = new MallTheme();
		if (mallThemeDTO.getAddressId() != null || ("").equals(mallThemeDTO.getAddressId())) {
			mt.setAddressId(mallThemeDTO.getAddressId());
		}
		if (mallThemeDTO.getcId() != null || ("").equals(mallThemeDTO.getcId())) {
			mt.setcId(mallThemeDTO.getcId());
		}
		if (mallThemeDTO.getClev() != null || ("").equals(mallThemeDTO.getClev())) {
			mt.setClev(mallThemeDTO.getClev());
		}
		if (mallThemeDTO.getColor() != null || ("").equals(mallThemeDTO.getColor())) {
			mt.setColor(mallThemeDTO.getColor());
		}
		if (mallThemeDTO.getColorb() != null || ("").equals(mallThemeDTO.getColorb())) {
			mt.setColorb(mallThemeDTO.getColorb());
		}
		if (mallThemeDTO.getSortNum() != null || ("").equals(mallThemeDTO.getSortNum())) {
			mt.setSortNum(mallThemeDTO.getSortNum());
		}
		if (mallThemeDTO.getCreated() != null || ("").equals(mallThemeDTO.getCreated())) {
			mt.setCreated(mallThemeDTO.getCreated());
		}
		if (mallThemeDTO.getId() != null || ("").equals(mallThemeDTO.getId())) {
			mt.setId(mallThemeDTO.getId());
		}
		if (mallThemeDTO.getStatus() != null || ("").equals(mallThemeDTO.getStatus())) {
			mt.setStatus(mallThemeDTO.getStatus());
		}
		if (mallThemeDTO.getThemeName() != null || ("").equals(mallThemeDTO.getThemeName())) {
			mt.setThemeName(mallThemeDTO.getThemeName());
		}
		if (mallThemeDTO.getType() != null || ("").equals(mallThemeDTO.getType())) {
			mt.setType(mallThemeDTO.getType());
		}
		if (mallThemeDTO.getUserId() != null || ("").equals(mallThemeDTO.getUserId())) {
			mt.setUserId(mallThemeDTO.getUserId());
		}
		int add = mallThemeDao.update(mt);
		er.setResult(add);
		return er;
	}

}
