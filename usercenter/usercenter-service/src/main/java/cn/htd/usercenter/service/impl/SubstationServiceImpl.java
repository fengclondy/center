package cn.htd.usercenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dto.AddressInfo;
import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dao.EmployeeDAO;
import cn.htd.usercenter.dao.SubstationDAO;
import cn.htd.usercenter.dto.CityDTO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.ProvinceDTO;
import cn.htd.usercenter.dto.SubstationDTO;
import cn.htd.usercenter.service.SubstationService;

@Service("substationService")
public class SubstationServiceImpl implements SubstationService {

	@Resource
	private SubstationDAO substationDAO;
	@Resource
	private EmployeeDAO employeeDAO;

	@Autowired
	private BaseAddressService baseAddressService;

	private final static Logger logger = LoggerFactory.getLogger(SubstationServiceImpl.class);

	/**
	 * 根据分站名称查询分站列表
	 */
	@Override
	public ExecuteResult<DataGrid<SubstationDTO>> querySubstationByName(SubstationDTO substation, int page, int rows) {
		ExecuteResult<DataGrid<SubstationDTO>> rs = new ExecuteResult<DataGrid<SubstationDTO>>();
		try {
			@SuppressWarnings("rawtypes")
			Pager pager = new Pager();
			pager.setPage(page);
			pager.setRows(rows);
			DataGrid<SubstationDTO> dg = new DataGrid<SubstationDTO>();
			List<SubstationDTO> substationList = substationDAO.querySubstationByName(substation, pager);
			long count = substationDAO.querySubstationByNameCount(substation);
			try {
				if (substationList != null) {
					dg.setRows(substationList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->querySubstationByName=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> addSubstation(SubstationDTO substationDTO, Long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (StringUtils.isNotBlank(substationDTO.getName()) && StringUtils.isNotBlank(substationDTO.getAreas())) {
				// 判断城市是否被占用
				String area = substationDTO.getAreas();
				String[] strs = area.split(";");
				for (int i = 0; i < strs.length; i++) {
					String[] str = strs[i].split("&");
					String[] citys = str[1].split(",");
					for (int j = 0; j < citys.length; j++) {
						String cityCode = citys[j];
						List<SubstationDTO> substationList = substationDAO.isHasUsed(cityCode, substationDTO);
						if (substationList.size() != 0) {// 城市已经被占用
							CityDTO city = getCityName(cityCode);
							rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
							rs.addErrorMessage(city.getName() + "已经被占用，请重新选择！！");
							return rs;
						}
					}
				}
				// 判断分站名称是否被占用
				String name = substationDTO.getName();
				List<SubstationDTO> substationList = substationDAO.isRepeat(substationDTO);
				if (substationList.size() != 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage(name + "已经被占用，请重新填写！！");
					return rs;
				}
				substationDTO.setDeleted_flag(GlobalConstant.DELETED_FLAG_NO);
				substationDAO.addSubstation(substationDTO, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("保存成功！");

			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写分站信息！");
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->addSubstation=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateSubstation(String name, String areas, int substationId, Long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(areas)) {
				SubstationDTO substationDTO = new SubstationDTO();
				substationDTO.setName(name);
				substationDTO.setSubstationId(substationId);
				String[] strs = areas.split(";");
				for (int i = 0; i < strs.length; i++) {
					String[] str = strs[i].split("&");
					String[] citys = str[1].split(",");
					for (int j = 0; j < citys.length; j++) {
						String cityCode = citys[j];
						List<SubstationDTO> substationList = substationDAO.isHasUsed(cityCode, substationDTO);
						if (substationList.size() != 0) {// 城市已经被占用
							CityDTO city = getCityName(cityCode);
							rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
							rs.addErrorMessage(city.getName() + "已经被占用，请重新选择！！");
							return rs;
						}
					}
				}

				// 判断分站名称是否被占用
				List<SubstationDTO> substationList = substationDAO.isRepeat(substationDTO);
				if (substationList.size() != 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage(name + "已经被占用，请重新填写！！");
					return rs;
				}
				substationDAO.updateSubstation(name, areas, substationId, GlobalConstant.DELETED_FLAG_NO, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写分站信息！");
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->updateSubstation=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> deleteSubstation(int substationId, Long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {

			List<EmployeeDTO> employeeList = employeeDAO.selectIsHasMan(substationId);
			if (employeeList.size() > 0) {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("该分站下面有人员，请先删除人员！");
			} else {
				substationDAO.updateSubstationFlag(substationId, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->deleteSubstation=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<SubstationDTO>> selectAllSubstation() {
		ExecuteResult<DataGrid<SubstationDTO>> rs = new ExecuteResult<DataGrid<SubstationDTO>>();
		try {
			DataGrid<SubstationDTO> dg = new DataGrid<SubstationDTO>();
			List<SubstationDTO> substationList = substationDAO.selectSubstation();
			try {
				if (substationList != null) {
					dg.setRows(substationList);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->selectAllSubstation=" + e);
		}
		return rs;
	}

	@Override
	public List<SubstationDTO> selectBySubstationId(int substationId) {
		return substationDAO.querySubstationById(substationId);
	}

	@Override
	public ExecuteResult<Boolean> isRepeat(SubstationDTO substationDTO) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {

			if (!("").equals(substationDTO.getName())) {
				List<SubstationDTO> substationList = substationDAO.isRepeat(substationDTO);
				if (substationList.size() > 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该名称已经存在，请重新输入！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("该名称可以使用！");
				}
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->isRepeat=" + e);
		}
		return rs;
	}

	public List<ProvinceDTO> getProvinceList() {
		List<AddressInfo> dto = baseAddressService.getAddressList(null);
		List<ProvinceDTO> pList = new ArrayList<ProvinceDTO>();
		for (AddressInfo addressInfo : dto) {
			ProvinceDTO pd = new ProvinceDTO();
			pd.setCode(addressInfo.getCode());
			pd.setName(addressInfo.getName());
			pList.add(pd);
		}
		return pList;
	}

	public ProvinceDTO getProvince(String code) {
		BaseAddressDTO dto = baseAddressService.queryBaseAddressByCode(code).getResult();
		if (dto == null) {
			return null;
		}
		ProvinceDTO pd = new ProvinceDTO();
		pd.setCode(dto.getCode());
		pd.setName(dto.getName());
		return pd;
	}

	public CityDTO getCityName(String cityCode) {
		BaseAddressDTO dto = baseAddressService.queryBaseAddressByCode(cityCode).getResult();
		if (dto == null) {
			return null;
		}
		CityDTO pd = new CityDTO();
		pd.setCode(dto.getCode());
		pd.setName(dto.getName());
		return pd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.SubstationService#selectSubstationByCityCode()
	 */
	@Override
	public ExecuteResult<DataGrid<SubstationDTO>> selectSubstationByCityCode(String code) {
		ExecuteResult<DataGrid<SubstationDTO>> rs = new ExecuteResult<DataGrid<SubstationDTO>>();
		try {
			// code null是全国
			if (code == null) {
				rs.setResult(null);
				rs.setResultMessage("success");
				return rs;
			}
			DataGrid<SubstationDTO> dg = new DataGrid<SubstationDTO>();
			List<SubstationDTO> substationList = substationDAO.selectSubstationByCityCode(code);
			try {
				if (substationList != null) {
					dg.setRows(substationList);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->selectAllSubstation=" + e);
		}
		return rs;
	}
}
