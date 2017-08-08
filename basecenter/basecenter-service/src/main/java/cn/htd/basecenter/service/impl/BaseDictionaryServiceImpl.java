package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseDictionaryDAO;
import cn.htd.basecenter.domain.BaseDictionary;
import cn.htd.basecenter.dto.BaseDictionaryDTO;
import cn.htd.basecenter.dto.DictionaryInfo;
import cn.htd.basecenter.service.BaseDictionaryService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.util.Pinyin4j;

@Service("baseDictionaryService")
public class BaseDictionaryServiceImpl implements BaseDictionaryService {
	private final static Logger logger = LoggerFactory.getLogger(BaseDictionaryServiceImpl.class);

	@Resource
	private BaseDictionaryDAO baseDictionaryDAO;

	@Override
	public String getNameByValue(String parentCode, String value) throws Exception {
		List<DictionaryInfo> dictList = null;
		String name = "";
		if (StringUtils.isBlank(parentCode)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息上级编码不能是空白");
		}
		if (StringUtils.isBlank(value)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息值不能是空白");
		}
		dictList = getDictionaryList(parentCode);
		if (dictList != null && dictList.size() > 0) {
			for (DictionaryInfo dict : dictList) {
				if (value.equals(dict.getValue())) {
					name = dict.getName();
					break;
				}
			}
		}
		return name;
	}

	@Override
	public String getCodeByValue(String parentCode, String value) throws Exception {
		List<DictionaryInfo> dictList = null;
		String code = "";
		if (StringUtils.isBlank(parentCode)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息上级编码不能是空白");
		}
		if (StringUtils.isBlank(value)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息值不能是空白");
		}
		dictList = getDictionaryList(parentCode);
		if (dictList != null && dictList.size() > 0) {
			for (DictionaryInfo dict : dictList) {
				if (value.equals(dict.getValue())) {
					code = dict.getCode();
					break;
				}
			}
		}
		return code;
	}

	@Override
	public String getValueByCode(String parentCode, String code) throws Exception {
		List<DictionaryInfo> dictList = null;
		String value = "";
		if (StringUtils.isBlank(parentCode)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息上级编码不能是空白");
		}
		if (StringUtils.isBlank(code)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息编码不能是空白");
		}
		dictList = getDictionaryList(parentCode);
		if (dictList != null && dictList.size() > 0) {
			for (DictionaryInfo dict : dictList) {
				if (code.equals(dict.getCode())) {
					value = dict.getValue();
					break;
				}
			}
		}
		return value;
	}

	@Override
	public List<DictionaryInfo> getDictionaryList(String parentCode) throws Exception {
		List<BaseDictionary> dictionaryList = null;
		List<DictionaryInfo> resultList = new ArrayList<DictionaryInfo>();
		DictionaryInfo dict = null;
		BaseDictionaryDTO dictDTO = new BaseDictionaryDTO();
		if (StringUtils.isBlank(parentCode)) {
			throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息上级编码不能是空白");
		}
		dictDTO.setParentCode(parentCode);
		dictDTO.setStatus(YesNoEnum.YES.getValue());
		dictionaryList = baseDictionaryDAO.queryBaseDictionaryList(dictDTO);
		if (dictionaryList != null && dictionaryList.size() > 0) {
			for (BaseDictionary obj : dictionaryList) {
				dict = new DictionaryInfo();
				dict.setCode(obj.getCode());
				dict.setName(obj.getName());
				dict.setValue(obj.getValue());
				resultList.add(dict);
			}
		}
		return resultList;
	}

	@Override
	public ExecuteResult<BaseDictionaryDTO> queryBaseDictionaryById(Long id) {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		BaseDictionary dictionary = null;
		BaseDictionaryDTO dictDTO = null;
		try {
			dictionary = baseDictionaryDAO.queryById(id);
			if (dictionary == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_DICTIONARY_ERROR, "不存在ID=" + id + "的字典信息");
			}
			dictDTO = new BaseDictionaryDTO();
			dictDTO.setId(dictionary.getId());
			dictDTO.setCode(dictionary.getCode());
			dictDTO.setName(dictionary.getName());
			dictDTO.setValue(dictionary.getValue());
			dictDTO.setType(dictionary.getType());
			dictDTO.setParentCode(dictionary.getParentCode());
			dictDTO.setRemark(dictionary.getRemark());
			dictDTO.setStatus(dictionary.getStatus());
			dictDTO.setSortNum(dictionary.getSortNum());
			dictDTO.setCreateId(dictionary.getCreateId());
			dictDTO.setCreateName(dictionary.getCreateName());
			dictDTO.setCreateTime(dictionary.getCreateTime());
			dictDTO.setModifyId(dictionary.getModifyId());
			dictDTO.setModifyName(dictionary.getModifyName());
			dictDTO.setModifyTime(dictionary.getModifyTime());
			result.setResult(dictDTO);
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
	public DataGrid<BaseDictionaryDTO> queryBaseDictionaryListByCondition(BaseDictionaryDTO dictionaryDTO,
			Pager<BaseDictionaryDTO> pager) {
		DataGrid<BaseDictionaryDTO> result = new DataGrid<BaseDictionaryDTO>();
		BaseDictionary parameter = new BaseDictionary();
		List<BaseDictionary> dictList = null;
		long count = 0;
		BaseDictionaryDTO dictDTO = null;
		List<BaseDictionaryDTO> dictDtoList = new ArrayList<BaseDictionaryDTO>();
		parameter.setCode(dictionaryDTO.getCode());
		parameter.setName(dictionaryDTO.getName());
		parameter.setParentCode(dictionaryDTO.getParentCode());
		parameter.setParentName(dictionaryDTO.getParentName());
		parameter.setType(dictionaryDTO.getType());
		parameter.setRemark(dictionaryDTO.getRemark());
		parameter.setStatus(dictionaryDTO.getStatus());
		count = baseDictionaryDAO.queryCount(parameter);
		if (count > 0) {
			dictList = baseDictionaryDAO.queryList(parameter, pager);
			for (BaseDictionary obj : dictList) {
				dictDTO = new BaseDictionaryDTO();
				dictDTO.setId(obj.getId());
				dictDTO.setCode(obj.getCode());
				dictDTO.setName(obj.getName());
				dictDTO.setValue(obj.getValue());
				dictDTO.setType(obj.getType());
				dictDTO.setTypeName(obj.getTypeName());
				dictDTO.setParentCode(obj.getParentCode());
				dictDTO.setParentName(obj.getParentName());
				dictDTO.setRemark(obj.getRemark());
				dictDTO.setStatus(obj.getStatus());
				dictDTO.setSortNum(obj.getSortNum());
				dictDTO.setCreateId(obj.getCreateId());
				dictDTO.setCreateName(obj.getCreateName());
				dictDTO.setCreateTime(obj.getCreateTime());
				dictDTO.setModifyId(obj.getModifyId());
				dictDTO.setModifyName(obj.getModifyName());
				dictDTO.setModifyTime(obj.getModifyTime());
				dictDtoList.add(dictDTO);
			}
			result.setRows(dictDtoList);
			result.setTotal(count);
		}
		return result;
	}

	@Override
	public ExecuteResult<BaseDictionaryDTO> addBaseDictionary(BaseDictionaryDTO dictionaryDTO) {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		BaseDictionary parameter = new BaseDictionary();
		try {
			checkInputParameter("add", dictionaryDTO);
			checkBaseDictionaryValid(dictionaryDTO);
			dictionaryDTO.setCode(parameter.getCode());
			dictionaryDTO.setName(parameter.getName());
			dictionaryDTO.setValue(parameter.getValue());
			dictionaryDTO.setType(parameter.getType());
			dictionaryDTO.setParentCode(parameter.getParentCode());
			dictionaryDTO.setRemark(parameter.getRemark());
			dictionaryDTO.setStatus(parameter.getStatus());
			dictionaryDTO.setSortNum(parameter.getSortNum());
			dictionaryDTO.setCreateId(parameter.getCreateId());
			dictionaryDTO.setCreateName(parameter.getCreateName());
			baseDictionaryDAO.add(parameter);
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
	public ExecuteResult<BaseDictionaryDTO> updateBaseDictionary(BaseDictionaryDTO dictionaryDTO) {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		BaseDictionary parameter = new BaseDictionary();
		try {
			checkInputParameter("update", dictionaryDTO);
			checkBaseDictionaryValid(dictionaryDTO);
			parameter.setId(dictionaryDTO.getId());
			parameter.setCode(dictionaryDTO.getCode());
			parameter.setName(dictionaryDTO.getName());
			parameter.setValue(dictionaryDTO.getValue());
			parameter.setType(dictionaryDTO.getType());
			parameter.setParentCode(dictionaryDTO.getParentCode());
			parameter.setRemark(dictionaryDTO.getRemark());
			parameter.setStatus(dictionaryDTO.getStatus());
			parameter.setSortNum(dictionaryDTO.getSortNum());
			parameter.setModifyId(dictionaryDTO.getModifyId());
			parameter.setModifyName(dictionaryDTO.getModifyName());
			baseDictionaryDAO.update(parameter);
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
	public ExecuteResult<BaseDictionaryDTO> enableBaseDictionary(BaseDictionaryDTO dictionaryDTO) {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		try {
			dictionaryDTO.setStatus(YesNoEnum.YES.getValue());
			updateBaseDictionaryStatus(dictionaryDTO);
			result.setResultMessage("启用成功！");
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
	public ExecuteResult<BaseDictionaryDTO> disableBaseDictionary(BaseDictionaryDTO dictionaryDTO) {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		try {
			dictionaryDTO.setStatus(YesNoEnum.NO.getValue());
			updateBaseDictionaryStatus(dictionaryDTO);
			result.setResultMessage("禁用成功！");
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
	 * 更新字典信息的状态
	 * 
	 * @param dictionaryDTO
	 * @return
	 * @throws BaseCenterBusinessException
	 */
	private ExecuteResult<BaseDictionaryDTO> updateBaseDictionaryStatus(BaseDictionaryDTO dictionaryDTO)
			throws BaseCenterBusinessException {
		ExecuteResult<BaseDictionaryDTO> result = new ExecuteResult<BaseDictionaryDTO>();
		BaseDictionary parameter = new BaseDictionary();
		checkInputParameter("status", dictionaryDTO);
		parameter.setId(dictionaryDTO.getId());
		parameter.setStatus(dictionaryDTO.getStatus());
		parameter.setModifyId(dictionaryDTO.getModifyId());
		parameter.setModifyName(dictionaryDTO.getModifyName());
		baseDictionaryDAO.update(parameter);
		return result;
	}

	/**
	 * 检查字典信息合法性
	 * 
	 * @param baseDictionaryDTO
	 * @throws BaseCenterBusinessException
	 */
	private void checkBaseDictionaryValid(BaseDictionaryDTO dictionaryDTO) throws BaseCenterBusinessException {
		long cnt = 0;
		String parentCode = dictionaryDTO.getParentCode();
		BaseDictionaryDTO chkParameter = new BaseDictionaryDTO();
		BaseDictionary checkRst = null;

		cnt = baseDictionaryDAO.checkBaseDictionaryUniqueness(dictionaryDTO);
		if (cnt > 0) {
			if (StringUtils.isBlank(parentCode)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.DICTIONARY_HAS_REPEATED,
						"已存在编码=" + dictionaryDTO.getCode() + "的字典信息");
			} else {
				throw new BaseCenterBusinessException(ReturnCodeConst.DICTIONARY_HAS_REPEATED,
						"已存在编码=" + dictionaryDTO.getCode() + "或值=" + dictionaryDTO.getValue() + "的字典信息");
			}
		}
		if (!StringUtils.isBlank(parentCode)) {
			chkParameter.setCode(parentCode);
			checkRst = baseDictionaryDAO.queryBaseDictionaryByCode(chkParameter);
			if (checkRst != null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_PARENT_DICTIONARY_ERROR,
						"不存在编码=" + parentCode + "的上级字典信息");
			}
		}
	}

	/**
	 * 检查输入对象
	 * 
	 * @param baseDictionaryDTO
	 * @throws BaseCenterBusinessException
	 */
	private void checkInputParameter(String type, BaseDictionaryDTO baseDictionaryDTO)
			throws BaseCenterBusinessException {
		if ("add".equals(type)) {
			if (baseDictionaryDTO.getType() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息类型不能是空白");
			}
			if (StringUtils.isBlank(baseDictionaryDTO.getCode())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息编码不能是空白");
			}
			if (StringUtils.isBlank(baseDictionaryDTO.getName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息名称不能是空白");
			}
			if (!StringUtils.isBlank(baseDictionaryDTO.getParentCode())
					&& StringUtils.isBlank(baseDictionaryDTO.getValue())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息选择上级字典时值不能是空白");
			}
			if (baseDictionaryDTO.getCreateId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息创建人ID不能是空白");
			}
			if (StringUtils.isBlank(baseDictionaryDTO.getCreateName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息创建人名称不能是空白");
			}
		} else {
			if (baseDictionaryDTO.getId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息ID不能是空白");
			}
			if (baseDictionaryDTO.getModifyId() == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息更新人ID不能是空白");
			}
			if (StringUtils.isBlank(baseDictionaryDTO.getModifyName())) {
				throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息更新人名称不能是空白");
			}
			if ("update".equals(type)) {
				if (baseDictionaryDTO.getType() == null) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息类型不能是空白");
				}
				if (StringUtils.isBlank(baseDictionaryDTO.getName())) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息名称不能是空白");
				}
				if (!StringUtils.isBlank(baseDictionaryDTO.getParentCode())
						&& StringUtils.isBlank(baseDictionaryDTO.getValue())) {
					throw new BaseCenterBusinessException(ReturnCodeConst.PARAMETER_ERROR, "字典信息选择上级字典时值不能是空白");
				}
			}
		}
	}

	@Override
	public ExecuteResult<String> addSingleItemUnit(String unitName,Long createId,String createName) {
		ExecuteResult<String> result=new ExecuteResult<String>();
		if(StringUtils.isEmpty(unitName)){
			result.setCode(ReturnCodeConst.PARAMETER_ERROR);
			result.setResultMessage("单位名称为空");
			return result;
		}
		if(StringUtils.length(unitName) > 8){
			result.setCode(ReturnCodeConst.PARAMETER_ERROR);
			result.setResultMessage("超出限制字符数");
			return result;
		}
		if(StringUtils.isEmpty(createName)||createId==null){
			result.setCode(ReturnCodeConst.PARAMETER_ERROR);
			result.setResultMessage("创建人信息不完整");
			return result;
		}
		
		BaseDictionary bd=baseDictionaryDAO.queryItemUnitByName(unitName);
		
		if(bd!=null){
			result.setCode(ReturnCodeConst.DICTIONARY_HAS_REPEATED);
			result.setResultMessage("该商品单位名称已经存在");
			return result;
		}
		
		BaseDictionary dictionaryDTO = new BaseDictionary();
		
		BaseDictionary maxItemUnit=baseDictionaryDAO.queryMaxItemUnit();
		
		int index=1;
		
		if(maxItemUnit!=null&&StringUtils.isNotEmpty(maxItemUnit.getCode())){
			String[] componentArr=StringUtils.split(maxItemUnit.getCode(),"_");
			if(componentArr!=null&&componentArr.length>=3&&StringUtils.isNumeric(componentArr[2])){
				index=Integer.parseInt(componentArr[2])+1;
			}
		}
		
		dictionaryDTO.setCode("ITEM_UNIT_"+index);
		dictionaryDTO.setName(unitName);
		
		String unitValue = calcItemUnitValue(unitName);
		
		dictionaryDTO.setValue(unitValue);
		dictionaryDTO.setType(0L);
		dictionaryDTO.setParentCode("ITEM_UNIT");
		dictionaryDTO.setRemark("");
	    dictionaryDTO.setStatus(1);
		dictionaryDTO.setSortNum(1);
		dictionaryDTO.setCreateId(createId);
		dictionaryDTO.setCreateName(createName);
		baseDictionaryDAO.add(dictionaryDTO);
		result.setResultMessage("增加成功！");
		
		return result;
	}

	private String calcItemUnitValue(String unitName) {
		
		Set<String> pinyinSet=Pinyin4j.getPinyin(unitName);
		if(CollectionUtils.isEmpty(pinyinSet)){
			return "";
		}
		
		String unitValue=pinyinSet.iterator().next();
		
		if(StringUtils.isEmpty(unitValue)){
			return "";
		}
		
		if(StringUtils.length(unitValue)>5){
			unitValue=StringUtils.substring(unitValue, 0, 5);
		}
		
		try {
			int sameUnitValueCount=0;
			
			BaseDictionary sameValueDic=baseDictionaryDAO.queryByValue(unitValue);
			
			while(sameValueDic!=null){
				sameUnitValueCount++;
				sameValueDic=baseDictionaryDAO.queryByValue(unitValue+sameUnitValueCount);
			}
			
			if(sameUnitValueCount>0){
				unitValue=unitValue+sameUnitValueCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("BaseDictionaryServiceImpl::calcItemUnitValue:",e);
		}
		return unitValue;
	}
}