package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.basecenter.common.constant.Constants;
import cn.htd.basecenter.domain.ErpArea;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.mq.MQSendUtil;
import cn.htd.common.util.DictionaryUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.common.utils.ValidateResult;
import cn.htd.basecenter.common.utils.ValidationUtils;
import cn.htd.basecenter.dao.BaseAddressDAO;
import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.dto.AddressInfo;
import cn.htd.basecenter.dto.BaseAddressDTO;
import cn.htd.basecenter.dto.BaseAddressErpResult;
import cn.htd.basecenter.enums.AddressLevelEnum;
import cn.htd.basecenter.enums.ErpStatusEnum;
import cn.htd.basecenter.service.BaseAddressService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("baseAddressService")
public class BaseAddressServiceImpl implements BaseAddressService {
	private final static Logger logger = LoggerFactory.getLogger(BaseAddressServiceImpl.class);

	@Resource
	private BaseAddressDAO baseAddressDAO;

	@Resource
	private DictionaryUtils dictionary;
	@Resource
	private AmqpTemplate amqpTemplate;

	@Override
	public String getAddressName(String addressCode) {
		String name = "";
		BaseAddress addressObj = null;

		addressObj = baseAddressDAO.queryBaseAddressByCode(addressCode);
		if (addressObj != null) {
			name = addressObj.getName();
		}
		return name;
	}

	@Override
	public List<AddressInfo> getAddressList(String parentCode) {
		List<AddressInfo> addressList = new ArrayList<AddressInfo>();
		List<BaseAddressDTO> addressDTOList = queryBaseAddressList(parentCode);
		AddressInfo addressInfo = null;
		if (addressDTOList != null) {
			for (BaseAddressDTO dto : addressDTOList) {
				addressInfo = new AddressInfo();
				addressInfo.setCode(dto.getCode());
				addressInfo.setName(dto.getName());
				addressInfo.setLevel(dto.getLevel());
				addressList.add(addressInfo);
			}
		}
		return addressList;
	}

	@Override
	public List<BaseAddressDTO> queryBaseAddressList(String parentCode) {
		DataGrid<BaseAddressDTO> retDataGrid = new DataGrid<BaseAddressDTO>();
		retDataGrid = queryBaseAddressList(parentCode, null);
		return retDataGrid.getRows();
	}

	@Override
	public DataGrid<BaseAddressDTO> queryBaseAddressList(String parentCode, Pager<BaseAddressDTO> pager) {
		DataGrid<BaseAddressDTO> result = new DataGrid<BaseAddressDTO>();
		BaseAddress parameter = new BaseAddress();
		List<BaseAddress> addressList = null;
		long count = 0;
		BaseAddressDTO addressDTO = null;
		List<BaseAddressDTO> addressDtoList = new ArrayList<BaseAddressDTO>();
		parameter.setDeleteFlag(YesNoEnum.NO.getValue());
		if (StringUtils.isBlank(parentCode)) {
			parameter.setLevel(AddressLevelEnum.PROVINCE.getValue());
		} else {
			parameter.setParentCode(parentCode);
			parameter.setLevel(0);
		}
		count = baseAddressDAO.queryCount(parameter);
		if (count > 0) {
			addressList = baseAddressDAO.queryList(parameter, pager);
			for (BaseAddress obj : addressList) {
				addressDTO = new BaseAddressDTO();
				addressDTO.setId(obj.getId());
				addressDTO.setCode(obj.getCode());
				addressDTO.setParentCode(obj.getParentCode());
				addressDTO.setName(obj.getName());
				addressDTO.setLevel(obj.getLevel());
				addressDTO.setDeleteFlag(obj.getDeleteFlag());
				addressDTO.setErpStatus(obj.getErpStatus());
				addressDTO.setErpDownTime(obj.getErpDownTime());
				addressDTO.setErpErrorMsg(obj.getErpErrorMsg());
				addressDTO.setCreateId(obj.getCreateId());
				addressDTO.setCreateName(obj.getCreateName());
				addressDTO.setCreateTime(obj.getCreateTime());
				addressDTO.setModifyId(obj.getModifyId());
				addressDTO.setModifyName(obj.getModifyName());
				addressDTO.setModifyTime(obj.getModifyTime());
				addressDtoList.add(addressDTO);
			}
			result.setRows(addressDtoList);
			result.setTotal(count);
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseAddressDTO> queryBaseAddressById(Long id) {
		ExecuteResult<BaseAddressDTO> result = new ExecuteResult<BaseAddressDTO>();
		BaseAddress baseAddress = null;
		BaseAddressDTO baseAddressDTO = null;
		List<BaseAddressDTO> parentBaseAddressList = new ArrayList<BaseAddressDTO>();
		try {
			baseAddress = verifyExistenceById(id);
			baseAddressDTO = new BaseAddressDTO();
			baseAddressDTO.setId(baseAddress.getId());
			baseAddressDTO.setCode(baseAddress.getCode());
			baseAddressDTO.setParentCode(baseAddress.getParentCode());
			baseAddressDTO.setName(baseAddress.getName());
			baseAddressDTO.setLevel(baseAddress.getLevel());
			baseAddressDTO.setDeleteFlag(baseAddress.getDeleteFlag());
			baseAddressDTO.setErpStatus(baseAddress.getErpStatus());
			baseAddressDTO.setErpDownTime(baseAddress.getErpDownTime());
			baseAddressDTO.setErpErrorMsg(baseAddress.getErpErrorMsg());
			baseAddressDTO.setCreateId(baseAddress.getCreateId());
			baseAddressDTO.setCreateName(baseAddress.getCreateName());
			baseAddressDTO.setCreateTime(baseAddress.getCreateTime());
			baseAddressDTO.setModifyId(baseAddress.getModifyId());
			baseAddressDTO.setModifyName(baseAddress.getModifyName());
			baseAddressDTO.setModifyTime(baseAddress.getModifyTime());
			parentBaseAddressList = queryParentBaseAddressList(baseAddressDTO);
			baseAddressDTO.setParentBaseAddressDTO(parentBaseAddressList);
			result.setResult(baseAddressDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseAddressDTO> queryBaseAddressByCode(String code) {
		ExecuteResult<BaseAddressDTO> result = new ExecuteResult<BaseAddressDTO>();
		BaseAddress baseAddress = null;
		BaseAddressDTO baseAddressDTO = null;
		List<BaseAddressDTO> parentBaseAddressList = new ArrayList<BaseAddressDTO>();
		try {
			baseAddress = baseAddressDAO.queryBaseAddressByCode(code);
			if (baseAddress == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_ADDRESS_ERROR, "不存在编码=" + code + "的地址信息");
			}
			baseAddressDTO = new BaseAddressDTO();
			baseAddressDTO.setId(baseAddress.getId());
			baseAddressDTO.setCode(baseAddress.getCode());
			baseAddressDTO.setParentCode(baseAddress.getParentCode());
			baseAddressDTO.setName(baseAddress.getName());
			baseAddressDTO.setLevel(baseAddress.getLevel());
			baseAddressDTO.setDeleteFlag(baseAddress.getDeleteFlag());
			baseAddressDTO.setErpStatus(baseAddress.getErpStatus());
			baseAddressDTO.setErpDownTime(baseAddress.getErpDownTime());
			baseAddressDTO.setErpErrorMsg(baseAddress.getErpErrorMsg());
			baseAddressDTO.setCreateId(baseAddress.getCreateId());
			baseAddressDTO.setCreateName(baseAddress.getCreateName());
			baseAddressDTO.setCreateTime(baseAddress.getCreateTime());
			baseAddressDTO.setModifyId(baseAddress.getModifyId());
			baseAddressDTO.setModifyName(baseAddress.getModifyName());
			baseAddressDTO.setModifyTime(baseAddress.getModifyTime());
			parentBaseAddressList = queryParentBaseAddressList(baseAddressDTO);
			baseAddressDTO.setParentBaseAddressDTO(parentBaseAddressList);
			result.setResult(baseAddressDTO);
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseAddressDTO> addBaseAddress(BaseAddressDTO baseAddressDTO) {
		ExecuteResult<BaseAddressDTO> result = new ExecuteResult<BaseAddressDTO>();
		BaseAddress parameter = new BaseAddress();
		BaseAddress checkRst = null;
		BaseAddress parentBaseAddress = null;
		String code = "";
		String parentCode = "";
		int level = 0;
		try {
			checkInputParameter("add", baseAddressDTO);
			code = baseAddressDTO.getCode();
			parentCode = baseAddressDTO.getParentCode();
			checkRst = baseAddressDAO.queryBaseAddressByCode(code);
			if (checkRst != null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_HAS_REPEATED,
						"已存在编码=" + code + "，名称=" + checkRst.getName() + "的地址信息");
			}
			parameter.setCode(baseAddressDTO.getCode());
			parameter.setParentCode(parentCode);
			parameter.setName(baseAddressDTO.getName());
			parameter.setCreateId(baseAddressDTO.getCreateId());
			parameter.setCreateName(baseAddressDTO.getCreateName());
			if (StringUtils.isBlank(parentCode)) {
				if (AddressLevelEnum.PROVINCE.getValue() != baseAddressDTO.getLevel()) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息即没有指定上级地址编码，也没有指定地址级别");
				}
				parameter.setLevel(AddressLevelEnum.PROVINCE.getValue());
			} else {
				parentBaseAddress = baseAddressDAO.queryBaseAddressByCode(parentCode);
				if (parentBaseAddress == null) {
					throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_NO_PARENT,
							"地址信息指定上级编码=" + parentCode + "不存在");
				}
				level = parentBaseAddress.getLevel() + 1;
				if (StringUtils.isBlank(AddressLevelEnum.getAddressLevelName(level))) {
					throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_IS_LEAF_ERROR,
							AddressLevelEnum.TOWN.getName() + "以下不能再添加地址信息");
				}
				parameter.setLevel(level);
			}
			parameter.setErpStatus(ErpStatusEnum.PENDING.getValue());
			baseAddressDAO.add(parameter);
			result.setResultMessage("增加成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseAddressDTO> updateBaseAddress(BaseAddressDTO baseAddressDTO) {
		ExecuteResult<BaseAddressDTO> result = new ExecuteResult<BaseAddressDTO>();
		BaseAddress parameter = new BaseAddress();
		try {
			checkInputParameter("update", baseAddressDTO);
			verifyExistenceById(baseAddressDTO.getId());
			parameter.setId(baseAddressDTO.getId());
			parameter.setName(baseAddressDTO.getName());
			parameter.setErpStatus(ErpStatusEnum.PENDING.getValue());
			parameter.setModifyId(baseAddressDTO.getModifyId());
			parameter.setModifyName(baseAddressDTO.getModifyName());
			baseAddressDAO.update(parameter);
			result.setResultMessage("修改成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseAddressDTO> deleteBaseAddress(BaseAddressDTO baseAddressDTO) {
		ExecuteResult<BaseAddressDTO> result = new ExecuteResult<BaseAddressDTO>();
		BaseAddress parameter = new BaseAddress();
		BaseAddress checkRst = null;
		List<BaseAddress> baseAddressList = null;
		String code = "";
		try {
			checkInputParameter("delete", baseAddressDTO);
			checkRst = verifyExistenceById(baseAddressDTO.getId());
			code = checkRst.getCode();
			baseAddressList = baseAddressDAO.queryBaseAddressListByParentCode(code);
			if (baseAddressList != null && baseAddressList.size() > 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_CANNOT_DELETE, "删除失败！该地址还存在下级地址信息");
			}
			parameter.setDeleteFlag(YesNoEnum.YES.getValue());
			parameter.setId(baseAddressDTO.getId());
			parameter.setModifyId(baseAddressDTO.getModifyId());
			parameter.setModifyName(baseAddressDTO.getModifyName());
			baseAddressDAO.update(parameter);
			result.setResultMessage("删除成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.setCode(bcbe.getCode());
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 根据当前基础地址信息取得上级直到省的基础地址信息
	 * 
	 * @param baseAddress
	 * @return
	 * @throws BaseCenterBusinessException
	 */
	private List<BaseAddressDTO> queryParentBaseAddressList(BaseAddressDTO baseAddressDTO)
			throws BaseCenterBusinessException {
		BaseAddress parentBaseAddress = null;
		BaseAddressDTO parentBaseAddressDTO = null;
		List<BaseAddressDTO> parentBaseAddressList = new ArrayList<BaseAddressDTO>();
		int parentLevel = baseAddressDTO.getLevel() - 1;
		String parentCode = baseAddressDTO.getParentCode();
		while (AddressLevelEnum.PROVINCE.getValue() <= parentLevel) {
			parentBaseAddress = baseAddressDAO.queryBaseAddressByCode(parentCode);
			if (parentBaseAddress == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_NO_PARENT,
						"该地址不存在" + AddressLevelEnum.getAddressLevelName(parentLevel) + "级地址信息");
			}
			parentBaseAddressDTO = new BaseAddressDTO();
			parentBaseAddressDTO.setId(parentBaseAddress.getId());
			parentBaseAddressDTO.setCode(parentBaseAddress.getCode());
			parentBaseAddressDTO.setParentCode(parentBaseAddress.getParentCode());
			parentBaseAddressDTO.setName(parentBaseAddress.getName());
			parentBaseAddressDTO.setLevel(parentBaseAddress.getLevel());
			parentBaseAddressList.add(0, parentBaseAddressDTO);
			parentLevel = parentBaseAddress.getLevel() - 1;
			parentCode = parentBaseAddress.getParentCode();
		}
		return parentBaseAddressList;
	}

	/**
	 * 根据ID验证基础地址数据是否存在
	 * 
	 * @param id
	 * @return
	 * @throws BaseCenterBusinessException
	 */
	private BaseAddress verifyExistenceById(Long id) throws BaseCenterBusinessException {

		BaseAddress baseAddress = null;
		baseAddress = baseAddressDAO.queryById(id);
		if (baseAddress == null) {
			throw new BaseCenterBusinessException(ReturnCodeConst.NO_ADDRESS_ERROR, "不存在ID=" + id + "的地址信息");
		}
		if (baseAddress.getDeleteFlag() == YesNoEnum.YES.getValue()) {
			throw new BaseCenterBusinessException(ReturnCodeConst.ADDRESS_HAS_DELETED, "该地址信息已经删除");
		}
		return baseAddress;
	}

	/**
	 * 检查输入对象
	 * 
	 * @param baseAddressDTO
	 * @throws BaseCenterBusinessException
	 */
	private void checkInputParameter(String type, BaseAddressDTO baseAddressDTO) throws BaseCenterBusinessException {
		if ("add".equals(type)) {
			if (StringUtils.isBlank(baseAddressDTO.getCode())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息编码不能是空白");
			}
			if (StringUtils.isBlank(baseAddressDTO.getName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息名称不能是空白");
			}
			if (baseAddressDTO.getCreateId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息创建人ID不能是空白");
			}
			if (StringUtils.isBlank(baseAddressDTO.getCreateName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息创建人名称不能是空白");
			}
		} else if ("update".equals(type) || "delete".equals(type)) {
			if (baseAddressDTO.getId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息ID不能是空白");
			}
			if (baseAddressDTO.getModifyId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息更新人ID不能是空白");
			}
			if (StringUtils.isBlank(baseAddressDTO.getModifyName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息更新人名称不能是空白");
			}
			if ("update".equals(type)) {
				if (StringUtils.isBlank(baseAddressDTO.getName())) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "地址信息名称不能是空白");
				}
			}
		}
	}

	@Override
	public ExecuteResult<String> callbackBaseAddress2Erp(BaseAddressErpResult erpResult) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		result.setCode("1");
		// 输入DTO的验证
		ValidateResult validateResult = ValidationUtils.validateEntity(erpResult);
		// 有错误信息时返回错误信息
		if (validateResult.isHasErrors()) {
			result.setCode("0002");
			result.addErrorMessage("回调接口参数错误:" + validateResult.getErrorMsg());
		}
		String areaCode = erpResult.getAreaCode();
		BaseAddress dealAddress = baseAddressDAO.queryBaseAddressByCode(areaCode);
		if (dealAddress == null) {
			result.setCode("0001");
			result.addErrorMessage("地址编码:" + areaCode + " 在地址库中不存在");
			return result;
		}
		if (ErpStatusEnum.DOWNING.getValue().equals(dealAddress.getErpStatus())
				|| ErpStatusEnum.FAILURE.getValue().equals(dealAddress.getErpStatus())) {
			dealAddress.setErpStatus(YesNoEnum.YES.getValue() == erpResult.getResult()
					? ErpStatusEnum.SUCCESS.getValue() : ErpStatusEnum.FAILURE.getValue());
			dealAddress.setErpErrorMsg(erpResult.getErrorMessage());
			updateAddressErpCallbackStatus(dealAddress);
		}
		return result;
	}

	/**
	 * 更新ERP回调结果
	 * 
	 * @param dealAddress
	 */
	public void updateAddressErpCallbackStatus(BaseAddress dealAddress) {
		dealAddress.setErpDownTime(null);
		baseAddressDAO.updateAddressStatusById(dealAddress);
	}

	/**
	 * 查询下行erp异常的基础地质列表
	 * @param pager
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<BaseAddressDTO>> queryBaseAddress2ErpException(Pager pager) {
		ExecuteResult<DataGrid<BaseAddressDTO>> executeResult = new ExecuteResult<>();
		DataGrid<BaseAddressDTO> dtoDataGrid = new DataGrid<>();
		try {

			// 查询参数; 下行中和下行失败的
			BaseAddress condition = new BaseAddress();
			condition.setErpStatus(ErpStatusEnum.DOWNING.getValue());
			condition.setErpStatus1(ErpStatusEnum.FAILURE.getValue());
			Long count = this.baseAddressDAO.queryAddressCount4ErpException(condition);
			List<BaseAddressDTO> addressDTOList = new ArrayList<>();
			if (count > 0) {
				List<BaseAddress> addressList = baseAddressDAO.queryAddressList4ErpException(condition, pager);
				for (BaseAddress baseAddress : addressList) {
					BaseAddressDTO baseAddressDTO = new BaseAddressDTO();
					baseAddressDTO.setId(baseAddress.getId());
					baseAddressDTO.setCode(baseAddress.getCode());
					baseAddressDTO.setParentCode(baseAddress.getParentCode());
					baseAddressDTO.setName(baseAddress.getName());
					baseAddressDTO.setLevel(baseAddress.getLevel());
					baseAddressDTO.setDeleteFlag(baseAddress.getDeleteFlag());
					baseAddressDTO.setErpStatus(baseAddress.getErpStatus());
					baseAddressDTO.setErpDownTime(baseAddress.getErpDownTime());
					baseAddressDTO.setErpErrorMsg(baseAddress.getErpErrorMsg());
					baseAddressDTO.setCreateId(baseAddress.getCreateId());
					baseAddressDTO.setCreateName(baseAddress.getCreateName());
					baseAddressDTO.setCreateTime(baseAddress.getCreateTime());
					baseAddressDTO.setModifyId(baseAddress.getModifyId());
					baseAddressDTO.setModifyName(baseAddress.getModifyName());
					baseAddressDTO.setModifyTime(baseAddress.getModifyTime());
					addressDTOList.add(baseAddressDTO);
				}
			}
			dtoDataGrid.setTotal(count);
			dtoDataGrid.setRows(addressDTOList);
			executeResult.setResult(dtoDataGrid);
		} catch (Exception e) {
			logger.error("查询下行erp异常的基础地址列表失败, 错误信息：", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 下行城市基本信息到erp
	 * @param baseAddressDTO
	 * @return
	 */
	public ExecuteResult<String> downBaseAddress2Erp(BaseAddressDTO baseAddressDTO) {
		ExecuteResult<String> executeResult = new ExecuteResult<>();
		try {
			if (baseAddressDTO == null) {
				executeResult.addErrorMessage("baseAddressDTO is null");
				return executeResult;
			}
			if (baseAddressDTO.getId() == null) {
				executeResult.addErrorMessage("Id is null");
				return executeResult;
			}
			if (baseAddressDTO.getErpDownTime() == null) {
				executeResult.addErrorMessage("ErpDownTime is null");
				return executeResult;
			}
			if (StringUtils.isEmpty(baseAddressDTO.getCode())) {
				executeResult.addErrorMessage("Code is null");
				return executeResult;
			}
			if (StringUtils.isEmpty(baseAddressDTO.getName())) {
				executeResult.addErrorMessage("Name is null");
				return executeResult;
			}
			// 更新为下行中
			BaseAddress address = new BaseAddress();
			address.setId(baseAddressDTO.getId());
			address.setErpStatus(ErpStatusEnum.DOWNING.getValue());
			address.setErpDownTime(new Date());
			address.setErpErrorMsg("");
			baseAddressDAO.updateAddressStatusById(address);
			ErpArea erpDownObj = new ErpArea();
			int isLastLevel = dictionary.getValueByCode(DictionaryConst.TYPE_ADDRESS_LEVEL, DictionaryConst.OPT_ADDRESS_LEVEL_DISTRICT)
					.equals(String.valueOf(baseAddressDTO.getLevel())) ? YesNoEnum.YES.getValue() : YesNoEnum.NO.getValue();
			Date erpDownTime = baseAddressDTO.getErpDownTime();
			erpDownObj.setAreaCode(baseAddressDTO.getCode());
			erpDownObj.setAreaName(baseAddressDTO.getName());
			erpDownObj.setIsLastLevel(String.valueOf(isLastLevel));
			erpDownObj.setAreaFatherCode(org.apache.commons.lang3.StringUtils.isEmpty(baseAddressDTO.getParentCode()) ? baseAddressDTO.getCode() : baseAddressDTO.getParentCode());
			erpDownObj.setIsUpdateFlag(erpDownTime == null ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue());
			logger.info("手工触发城市基础数据下行开始, erpDownObj : {}", JSON.toJSONString(erpDownObj));
			MQSendUtil mqSender = new MQSendUtil();
			mqSender.setAmqpTemplate(amqpTemplate);
			mqSender.sendToMQWithRoutingKey(erpDownObj, Constants.ADDRESS_DOWN_ERP_ROUTING_KEY);
		} catch (Exception e) {
			logger.error("下行城市基本信息到ERP出错，错误信息：", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public List<AddressInfo> getAddressListByName(String name, int level) {
		List<AddressInfo> addressInfoList = new ArrayList<>();
		try {
			BaseAddress condition = new BaseAddress();
			condition.setName(StringUtils.isEmpty(name) ? null : name);
			condition.setLevel(level);
			condition.setDeleteFlag(0);
			List<BaseAddress> addresses = this.baseAddressDAO.queryList(condition, null);
			for (BaseAddress baseAddress : addresses) {
				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCode(baseAddress.getCode());
				addressInfo.setLevel(baseAddress.getLevel());
				addressInfo.setName(baseAddress.getName());
				addressInfoList.add(addressInfo);
			}
		} catch (Exception e) {
			logger.error("下行城市基本信息到ERP出错，错误信息：", e);
		}
		return addressInfoList;
	}
}
