package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.dao.IllegalCharacterDAO;
import cn.htd.basecenter.domain.IllegalCharacter;
import cn.htd.basecenter.dto.IllegalCharacterDTO;
import cn.htd.basecenter.service.IllegalCharacterService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: [非法字符实现类]
 * </p>
 */
@Service("illegalCharacterService")
public class IllegalCharacterServiceImpl implements IllegalCharacterService {

	private final static Logger logger = LoggerFactory.getLogger(IllegalCharacterServiceImpl.class);
	@Resource
	private IllegalCharacterDAO illegalCharacterDAO;

	@Override
	public ExecuteResult<IllegalCharacterDTO> addIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacter",
				JSONObject.toJSONString(illegalCharacterDTO));
		ExecuteResult<IllegalCharacterDTO> result = new ExecuteResult<IllegalCharacterDTO>();
		IllegalCharacter parameter = new IllegalCharacter();
		try {
			parameter.setType(illegalCharacterDTO.getType());
			parameter.setContent(illegalCharacterDTO.getContent());
			parameter.setCreateId(illegalCharacterDTO.getCreateId());
			parameter.setCreateName(illegalCharacterDTO.getCreateName());
			illegalCharacterDAO.add(parameter);
			result.setResultMessage("增加成功！");
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacter", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacter",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<IllegalCharacterDTO> addIllegalCharacterList(List<IllegalCharacterDTO> list) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacterList",
				JSONObject.toJSONString(list));
		ExecuteResult<IllegalCharacterDTO> result = new ExecuteResult<IllegalCharacterDTO>();
		try {
			illegalCharacterDAO.addIllegalCharacterList(list);
			result.setResultMessage("批量增加成功！");
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacterList", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-addIllegalCharacterList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<IllegalCharacterDTO> updateIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-updateIllegalCharacter",
				JSONObject.toJSONString(illegalCharacterDTO));
		ExecuteResult<IllegalCharacterDTO> result = new ExecuteResult<IllegalCharacterDTO>();
		IllegalCharacter parameter = new IllegalCharacter();
		int updateNum = 0;
		try {
			parameter.setId(illegalCharacterDTO.getId());
			parameter.setType(illegalCharacterDTO.getType());
			parameter.setContent(illegalCharacterDTO.getContent());
			parameter.setDeleteFlag(illegalCharacterDTO.getDeleteFlag());
			parameter.setModifyId(illegalCharacterDTO.getModifyId());
			parameter.setModifyName(illegalCharacterDTO.getModifyName());
			updateNum = illegalCharacterDAO.update(parameter);
			if (updateNum == 0) {
				result.addErrorMessage("修改失败！不存在ID=" + parameter.getId() + "的数据");
			} else {
				result.setResultMessage("修改成功！");
			}
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "IllegalCharacterServiceImpl-updateIllegalCharacter", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-updateIllegalCharacter",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<IllegalCharacterDTO> deleteIllegalCharacter(IllegalCharacterDTO illegalCharacterDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-deleteIllegalCharacter",
				JSONObject.toJSONString(illegalCharacterDTO));
		ExecuteResult<IllegalCharacterDTO> result = new ExecuteResult<IllegalCharacterDTO>();
		IllegalCharacter parameter = new IllegalCharacter();
		int deleteNum = 0;
		try {
			parameter.setId(illegalCharacterDTO.getId());
			parameter.setDeleteFlag(YesNoEnum.YES.getValue());
			parameter.setModifyId(illegalCharacterDTO.getModifyId());
			parameter.setModifyName(illegalCharacterDTO.getModifyName());
			deleteNum = illegalCharacterDAO.update(parameter);
			if (deleteNum == 0) {
				result.addErrorMessage("删除失败！不存在ID=" + parameter.getId() + "的数据");
			} else {
				result.setResultMessage("删除成功！");
			}
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "IllegalCharacterServiceImpl-deleteIllegalCharacter", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-deleteIllegalCharacter",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public ExecuteResult<IllegalCharacterDTO> queryIllegalCharacterById(Long id) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-queryIllegalCharacterById",
				JSONObject.toJSONString(id));
		ExecuteResult<IllegalCharacterDTO> result = new ExecuteResult<IllegalCharacterDTO>();
		IllegalCharacter illegalCharacter = null;
		IllegalCharacterDTO illegalCharacterDTO = null;
		try {
			illegalCharacter = illegalCharacterDAO.queryById(id);
			if (illegalCharacter != null) {
				illegalCharacterDTO = new IllegalCharacterDTO();
				illegalCharacterDTO.setId(illegalCharacter.getId());
				illegalCharacterDTO.setType(illegalCharacter.getType());
				illegalCharacterDTO.setContent(illegalCharacter.getContent());
				illegalCharacterDTO.setDeleteFlag(illegalCharacter.getDeleteFlag());
				illegalCharacterDTO.setCreateId(illegalCharacter.getCreateId());
				illegalCharacterDTO.setCreateName(illegalCharacter.getCreateName());
				illegalCharacterDTO.setCreateTime(illegalCharacter.getCreateTime());
				illegalCharacterDTO.setModifyId(illegalCharacter.getModifyId());
				illegalCharacterDTO.setModifyName(illegalCharacter.getModifyName());
				illegalCharacterDTO.setModifyTime(illegalCharacter.getModifyTime());
				result.setResult(illegalCharacterDTO);
			}
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "IllegalCharacterServiceImpl-queryIllegalCharacterById", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-queryIllegalCharacterById",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public DataGrid<IllegalCharacterDTO> queryIllegalCharacterList(IllegalCharacterDTO illegalCharacterDTO,
			Pager<IllegalCharacterDTO> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "IllegalCharacterServiceImpl-queryIllegalCharacterList",
				JSONObject.toJSONString(illegalCharacterDTO), JSONObject.toJSONString(pager));
		DataGrid<IllegalCharacterDTO> result = new DataGrid<IllegalCharacterDTO>();
		IllegalCharacter parameter = new IllegalCharacter();
		List<IllegalCharacter> queryResultList = null;
		List<IllegalCharacterDTO> resultList = new ArrayList<IllegalCharacterDTO>();
		IllegalCharacterDTO queryResultDTO = null;
		try {
			parameter.setType(illegalCharacterDTO.getType());
			parameter.setContent(illegalCharacterDTO.getContent());
			parameter.setDeleteFlag(illegalCharacterDTO.getDeleteFlag());
			long count = illegalCharacterDAO.queryCount(parameter);
			if (count > 0) {
				queryResultList = illegalCharacterDAO.queryList(parameter, pager);
				for (IllegalCharacter obj : queryResultList) {
					queryResultDTO = new IllegalCharacterDTO();
					queryResultDTO.setId(obj.getId());
					queryResultDTO.setType(obj.getType());
					queryResultDTO.setContent(obj.getContent());
					queryResultDTO.setDeleteFlag(obj.getDeleteFlag());
					queryResultDTO.setCreateId(obj.getCreateId());
					queryResultDTO.setCreateName(obj.getCreateName());
					queryResultDTO.setCreateTime(obj.getCreateTime());
					queryResultDTO.setModifyId(obj.getModifyId());
					queryResultDTO.setModifyName(obj.getModifyName());
					queryResultDTO.setModifyTime(obj.getModifyTime());
					resultList.add(queryResultDTO);
				}
				result.setRows(resultList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "IllegalCharacterServiceImpl-queryIllegalCharacterList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	@Override
	public boolean verifyIllegalCharacter(Long id, String content) {
		return illegalCharacterDAO.verifyIllegalCharacterExistence(id, content) > 0 ? true : false;
	}
}
