package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.dao.BaseTypeDAO;
import cn.htd.basecenter.domain.BaseType;
import cn.htd.basecenter.dto.BaseTypeDTO;
import cn.htd.basecenter.service.BaseTypeService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

@Service("baseTypeService")
public class BaseTypeServiceImpl implements BaseTypeService {
	private final static Logger logger = LoggerFactory.getLogger(BaseTypeServiceImpl.class);

	@Resource
	private BaseTypeDAO baseTypeDAO;

	@Override
	public List<BaseTypeDTO> queryBaseTypeList() {
		DataGrid<BaseTypeDTO> retDataGrid = new DataGrid<BaseTypeDTO>();
		BaseTypeDTO typeDTO = new BaseTypeDTO();
		typeDTO.setStatus(YesNoEnum.YES.getValue());
		retDataGrid = queryBaseTypeList(null, null);
		return retDataGrid.getRows();
	}

	@Override
	public DataGrid<BaseTypeDTO> queryBaseTypeList(BaseTypeDTO typeDTO, Pager<BaseTypeDTO> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-queryBaseTypeList", JSONObject.toJSONString(typeDTO),
				JSONObject.toJSONString(pager));
		DataGrid<BaseTypeDTO> result = new DataGrid<BaseTypeDTO>();
		BaseType parameter = new BaseType();
		List<BaseType> typeList = null;
		long count = 0;
		BaseTypeDTO baseTypeDTO = null;
		List<BaseTypeDTO> baseTypeDtoList = new ArrayList<BaseTypeDTO>();
		try {
			parameter.setType(typeDTO.getType());
			parameter.setName(typeDTO.getName());
			parameter.setStatus(typeDTO.getStatus());
			count = baseTypeDAO.queryCount(parameter);
			if (count > 0) {
				typeList = baseTypeDAO.queryList(parameter, pager);
				for (BaseType obj : typeList) {
					baseTypeDTO = new BaseTypeDTO();
					baseTypeDTO.setId(obj.getId());
					baseTypeDTO.setType(obj.getType());
					baseTypeDTO.setName(obj.getName());
					baseTypeDTO.setStatus(obj.getStatus());
					baseTypeDTO.setCreateId(obj.getCreateId());
					baseTypeDTO.setCreateName(obj.getCreateName());
					baseTypeDTO.setCreatetime(obj.getCreateTime());
					baseTypeDTO.setModifyId(obj.getModifyId());
					baseTypeDTO.setModifyName(obj.getModifyName());
					baseTypeDTO.setModifyTime(obj.getModifyTime());
					baseTypeDtoList.add(baseTypeDTO);
				}
				result.setRows(baseTypeDtoList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-queryBaseTypeList", JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseTypeDTO> queryBaseTypeById(Long id) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-queryBaseTypeById", JSONObject.toJSONString(id));
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		BaseType baseType = null;
		BaseTypeDTO baseTypeDTO = null;
		try {
			baseTypeDTO = new BaseTypeDTO();
			baseType = baseTypeDAO.queryById(id);
			baseTypeDTO.setId(baseType.getId());
			baseTypeDTO.setType(baseType.getType());
			baseTypeDTO.setName(baseType.getName());
			baseTypeDTO.setStatus(baseType.getStatus());
			baseTypeDTO.setCreateId(baseType.getCreateId());
			baseTypeDTO.setCreateName(baseType.getCreateName());
			baseTypeDTO.setCreatetime(baseType.getCreateTime());
			baseTypeDTO.setModifyId(baseType.getModifyId());
			baseTypeDTO.setModifyName(baseType.getModifyName());
			baseTypeDTO.setModifyTime(baseType.getModifyTime());
			result.setResult(baseTypeDTO);
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseTypeServiceImpl-queryBaseTypeById", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-queryBaseTypeById", JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseTypeDTO> addBaseType(BaseTypeDTO typeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-addBaseType", JSONObject.toJSONString(typeDTO));
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		BaseTypeDTO tmpTypeDTO = new BaseTypeDTO();
		BaseType parameter = new BaseType();
		try {
			checkInputParameter("add", typeDTO);
			tmpTypeDTO.setType(typeDTO.getType());
			if (!checkBaseTypeUniqueness(tmpTypeDTO)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.TYPE_HOMONYMY_ERROR,
						"已存在类型=" + typeDTO.getType() + "同名数据");
			}
			parameter.setType(typeDTO.getType());
			parameter.setName(typeDTO.getName());
			parameter.setStatus(typeDTO.getStatus());
			parameter.setCreateId(typeDTO.getCreateId());
			parameter.setCreateName(typeDTO.getCreateName());
			baseTypeDAO.add(parameter);
			result.setResultMessage("增加成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseTypeServiceImpl-addBaseType", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-addBaseType", JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseTypeDTO> updateBaseType(BaseTypeDTO typeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-updateBaseType", JSONObject.toJSONString(typeDTO));
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		BaseTypeDTO tmpTypeDTO = new BaseTypeDTO();
		BaseType parameter = new BaseType();
		try {
			checkInputParameter("update", typeDTO);
			tmpTypeDTO.setId(typeDTO.getId());
			tmpTypeDTO.setType(typeDTO.getType());
			if (!checkBaseTypeUniqueness(tmpTypeDTO)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.TYPE_HOMONYMY_ERROR,
						"已存在类型=" + typeDTO.getType() + "同名数据");
			}
			parameter.setId(typeDTO.getId());
			parameter.setType(typeDTO.getType());
			parameter.setName(typeDTO.getName());
			parameter.setStatus(typeDTO.getStatus());
			parameter.setModifyId(typeDTO.getModifyId());
			parameter.setModifyName(typeDTO.getModifyName());
			baseTypeDAO.update(parameter);
			result.setResultMessage("修改成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseTypeServiceImpl-updateBaseType", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-updateBaseType", JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseTypeDTO> enableBaseType(BaseTypeDTO typeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-enableBaseType", JSONObject.toJSONString(typeDTO));
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		try {
			typeDTO.setStatus(YesNoEnum.YES.getValue());
			result = updateBaseTypeStatus(typeDTO);
			result.setResultMessage("启用成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseTypeServiceImpl-enableBaseType", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-enableBaseType", JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseTypeDTO> disableBaseType(BaseTypeDTO typeDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseTypeServiceImpl-disableBaseType", JSONObject.toJSONString(typeDTO));
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		try {
			typeDTO.setStatus(YesNoEnum.NO.getValue());
			result = updateBaseTypeStatus(typeDTO);
			result.setResultMessage("禁用成功！");
		} catch (BaseCenterBusinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseTypeServiceImpl-disableBaseType", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseTypeServiceImpl-disableBaseType", JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新类型的状态
	 * 
	 * @param typeDTO
	 * @return
	 * @throws BaseCenterBusinessException
	 */
	private ExecuteResult<BaseTypeDTO> updateBaseTypeStatus(BaseTypeDTO typeDTO) throws BaseCenterBusinessException {
		ExecuteResult<BaseTypeDTO> result = new ExecuteResult<BaseTypeDTO>();
		BaseType parameter = new BaseType();
		checkInputParameter("status", typeDTO);
		parameter.setId(typeDTO.getId());
		parameter.setStatus(typeDTO.getStatus());
		parameter.setModifyId(typeDTO.getModifyId());
		parameter.setModifyName(typeDTO.getModifyName());
		baseTypeDAO.update(parameter);
		return result;
	}

	/**
	 * 根据类型检查类型内容是否唯一
	 * 
	 * @param typeDTO
	 * @return
	 * @throws BaseCenterBusinessException
	 */
	private boolean checkBaseTypeUniqueness(BaseTypeDTO typeDTO) throws BaseCenterBusinessException {
		long cnt = 0;
		if (StringUtils.isBlank(typeDTO.getType())) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "输入数据的类型没有内容");
		}
		cnt = baseTypeDAO.checkBaseTypeUniqueness(typeDTO);
		return cnt > 0 ? false : true;
	}

	/**
	 * 检查输入对象
	 * 
	 * @param baseTypeDTO
	 * @throws BaseCenterBusinessException
	 */
	private void checkInputParameter(String type, BaseTypeDTO baseTypeDTO) throws BaseCenterBusinessException {
		if ("add".equals(type)) {
			if (StringUtils.isBlank(baseTypeDTO.getType())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息类型不能是空白");
			}
			if (StringUtils.isBlank(baseTypeDTO.getName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息名称不能是空白");
			}
			if (baseTypeDTO.getCreateId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息创建人ID不能是空白");
			}
			if (StringUtils.isBlank(baseTypeDTO.getCreateName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息创建人名称不能是空白");
			}
		} else {
			if (baseTypeDTO.getId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息ID不能是空白");
			}
			if (baseTypeDTO.getModifyId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息更新人ID不能是空白");
			}
			if (StringUtils.isBlank(baseTypeDTO.getModifyName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息更新人名称不能是空白");
			}
			if ("update".equals(type)) {
				if (StringUtils.isBlank(baseTypeDTO.getType())) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息类型不能是空白");
				}
				if (StringUtils.isBlank(baseTypeDTO.getName())) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "类型信息名称不能是空白");
				}
			}
		}
	}
}
