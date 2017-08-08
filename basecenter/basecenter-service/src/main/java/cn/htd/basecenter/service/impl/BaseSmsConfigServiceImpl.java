package cn.htd.basecenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.dao.BaseSmsConfigDAO;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.ValidSmsConfigDTO;
import cn.htd.basecenter.enums.SmsEmailTypeEnum;
import cn.htd.basecenter.service.BaseSmsConfigService;
import cn.htd.common.ExecuteResult;

/**
 * <p>
 * Description: [本类用于操作短信和邮箱的设置以及邮件、短信的发送功能]
 * </p>
 */
@Service("baseSmsConfigService")
public class BaseSmsConfigServiceImpl implements BaseSmsConfigService {
	private static final Logger logger = LoggerFactory.getLogger(BaseSmsConfigServiceImpl.class);

	@Resource
	private BaseSmsConfigDAO baseSmsConfigDAO;

	/**
	 * 启用短信配置信息
	 */
	@Override
	public ExecuteResult<String> updateSMSConfigValid(ValidSmsConfigDTO inDTO) {
		logger.info("\n 方法[{}]，入参：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfig", JSONObject.toJSONString(inDTO));
		ExecuteResult<String> result = new ExecuteResult<String>();
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		BaseSmsConfigDTO targetObj = null;
		List<BaseSmsConfigDTO> validSmsConfigList = null;
		Long id = inDTO.getId();
		try {
			targetObj = baseSmsConfigDAO.queryById(id);
			if (targetObj == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_SMS_CONFIG_ERROR, "启用的短信通道配置信息不存在");
			}
			configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
			configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			validSmsConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
			if (validSmsConfigList != null && !validSmsConfigList.isEmpty()) {
				for (BaseSmsConfigDTO smsConfig : validSmsConfigList) {
					smsConfig.setUsedFlag(YesNoEnum.NO.getValue());
					smsConfig.setModifyId(inDTO.getOperatorId());
					smsConfig.setModifyName(inDTO.getOperatorName());
					baseSmsConfigDAO.updateSmsConfigUsedFlag(smsConfig);
				}
			}
			configCondition.setId(id);
			configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			configCondition.setModifyId(inDTO.getOperatorId());
			configCondition.setModifyName(inDTO.getOperatorName());
			baseSmsConfigDAO.updateSmsConfigUsedFlag(configCondition);
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfig", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "BaseSmsConfigServiceImpl-updateSMSConfig",
					JSONObject.toJSONString(result));
		}
		return result;
	}

}