package cn.htd.basecenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseSmsConfigDAO;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.BaseSmsConfigShowDTO;
import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.basecenter.enums.SmsEmailTypeEnum;
import cn.htd.basecenter.service.BaseSmsConfigService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Description: [本类用于操作短信和邮箱的设置以及邮件、短信的发送功能]
 * </p>
 */
@Service("baseSmsConfigService")
public class BaseSmsConfigServiceImpl implements BaseSmsConfigService {
	private static final Logger logger = LoggerFactory.getLogger(BaseSmsConfigServiceImpl.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private BaseSmsConfigDAO baseSmsConfigDAO;

	/**
	 * 查询短信通道的启用列表
	 */
	@Override
	public ExecuteResult<DataGrid<BaseSmsConfigShowDTO>> querySMSConfigStatusList(Pager<BaseSmsConfigDTO> pager) {
		logger.info("\n 方法[{}]，入参：[]", "BaseSmsConfigServiceImpl-querySMSConfigStatusList");
		ExecuteResult<DataGrid<BaseSmsConfigShowDTO>> result = new ExecuteResult<DataGrid<BaseSmsConfigShowDTO>>();
		DataGrid<BaseSmsConfigShowDTO> dataGrid = new DataGrid<BaseSmsConfigShowDTO>();
		List<BaseSmsConfigShowDTO> configShowList = new ArrayList<BaseSmsConfigShowDTO>();
		BaseSmsConfigShowDTO showDTO = null;
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		long count = 0;
		List<BaseSmsConfigDTO> configList = null;
		try {
			configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
			count = baseSmsConfigDAO.queryCount(configCondition);
			if (count > 0) {
				configList = baseSmsConfigDAO.queryList(configCondition, pager);
				for (BaseSmsConfigDTO configDTO : configList) {
					showDTO = new BaseSmsConfigShowDTO();
					showDTO.setChannelCode(configDTO.getChannelCode());
					showDTO.setChannelName(
							dictionary.getNameByValue(DictionaryConst.TYPE_SMS_CHANNEL, showDTO.getChannelCode()));
					showDTO.setUsedFlag(configDTO.getUsedFlag());
					showDTO.setOperatorId(configDTO.getModifyId());
					showDTO.setOperatorName(configDTO.getModifyName());
					showDTO.setOperatorTime(configDTO.getModifyTime());
					configShowList.add(showDTO);
				}
				dataGrid.setRows(configShowList);
			}
			dataGrid.setTotal(count);
			result.setResult(dataGrid);
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfigValid",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfigValid",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 启用短信配置信息
	 */
	@Override
	public ExecuteResult<String> updateSMSConfigValid(ValidSmsConfigDTO inDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfigValid", JSONObject.toJSONString(inDTO));
		ExecuteResult<String> result = new ExecuteResult<String>();
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		BaseSmsConfigDTO targetObj = null;
		List<BaseSmsConfigDTO> validSmsConfigList = null;
		try {
			configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
			configCondition.setChannelCode(inDTO.getChannelCode());
			targetObj = baseSmsConfigDAO.queryByChannelCode(configCondition);
			if (targetObj == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_SMS_CONFIG_ERROR, "启用的短信通道配置信息不存在");
			}
			BaseSmsConfigDTO paramDTO = new BaseSmsConfigDTO();
			paramDTO.setUsedFlag(YesNoEnum.YES.getValue());
			paramDTO.setType(SmsEmailTypeEnum.SMS.getCode());
			validSmsConfigList = baseSmsConfigDAO.queryByTypeCode(paramDTO);
			if (validSmsConfigList != null && !validSmsConfigList.isEmpty()) {
				for (BaseSmsConfigDTO smsConfig : validSmsConfigList) {
					smsConfig.setUsedFlag(YesNoEnum.NO.getValue());
					smsConfig.setModifyId(inDTO.getOperatorId());
					smsConfig.setModifyName(inDTO.getOperatorName());
					baseSmsConfigDAO.updateSmsConfigUsedFlag(smsConfig);
				}
			}
			configCondition.setModifyId(inDTO.getOperatorId());
			configCondition.setModifyName(inDTO.getOperatorName());
			baseSmsConfigDAO.updateSmsConfigUsedFlag(configCondition);
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfigValid",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfigValid",
					JSONObject.toJSONString(result));
		}
		return result;
	}

}