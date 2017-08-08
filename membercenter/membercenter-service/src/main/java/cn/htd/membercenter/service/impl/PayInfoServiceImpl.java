package cn.htd.membercenter.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yiji.openapi.tool.YijifuGateway;
import com.yiji.openapi.tool.YijipayConstants;
import com.yiji.openapi.tool.util.Ids;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.DateUtils;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.dao.PayInfoDAO;
import cn.htd.membercenter.dto.BindingBankCardCallbackDTO;
import cn.htd.membercenter.dto.BindingBankCardDTO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberOutsideSupplierCompanyDTO;
import cn.htd.membercenter.dto.YijifuCorporateCallBackDTO;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.dto.YijifuCorporateModifyDTO;
import cn.htd.membercenter.service.PayInfoService;

@Service("payInfoService")
public class PayInfoServiceImpl implements PayInfoService {
	private final static Logger logger = LoggerFactory.getLogger(PayInfoServiceImpl.class);

	@Resource
	PayInfoDAO payInfoDAO;

	@Override
	public ExecuteResult<YijifuCorporateCallBackDTO> realNameSaveVerify(YijifuCorporateDTO dto) {
		logger.info("PayInfoServiceImpl-realNameVerify服务执行,传入参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<YijifuCorporateCallBackDTO> rs = new ExecuteResult<YijifuCorporateCallBackDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getLegalPerson()) && !StringUtils.isEmpty(dto.getLegalPerson().getCertNo())) {// 身份证统一转成大写
				dto.getLegalPerson().setCertNo(dto.getLegalPerson().getCertNo().toUpperCase());
			}
			Map<String, String> map = new HashMap<String, String>();
			JSONObject json = (JSONObject) JSONObject.toJSON(dto);
			Set<String> set = json.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String val = json.getString(key);
				map.put(key, val);
			}
			map.put(YijipayConstants.REQUEST_NO, Ids.oid());
			map.put("protocol", "HTTP_FORM_JSON");
			map.put("service", "corporateRegister");
			map.put("signType", "MD5");
			map.put(YijipayConstants.PARTNER_ID, StaticProperty.YIJIFU_CORPORATE_DOWN_PARENT_ID);
			// 集成了签名和验签
			String responseStr = YijifuGateway.getOpenApiClientService()
					.doPost(StaticProperty.YIJIFU_CORPORATE_DOWN_URL, map, StaticProperty.YIJIFU_CORPORATE_DOWN_KEY);
			logger.info("URL:" + StaticProperty.YIJIFU_CORPORATE_DOWN_URL + "请求响应参数:" + responseStr);
			YijifuCorporateCallBackDTO callBack = JSONObject.parseObject(responseStr, YijifuCorporateCallBackDTO.class);
			rs.setResult(callBack);
			rs.setResultMessage("success");
			logger.info("PayInfoServiceImpl-realNameVerify服务执行成功,结果参数:" + responseStr);
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("实名认证注册执行失败:" + e);
			logger.error("PayInfoServiceImpl-realNameVerify服务执行失败:" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<YijifuCorporateCallBackDTO> realNameModifyVerify(YijifuCorporateModifyDTO dto) {
		logger.info("PayInfoServiceImpl-realNameModifyVerify服务执行,传入参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<YijifuCorporateCallBackDTO> rs = new ExecuteResult<YijifuCorporateCallBackDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getLegalPersonCertNo())) {// 身份证统一转成大写
				dto.setLegalPersonCertNo(dto.getLegalPersonCertNo().toUpperCase());
			}
			Map<String, String> map = new HashMap<String, String>();
			JSONObject json = (JSONObject) JSONObject.toJSON(dto);
			Set<String> set = json.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String val = json.getString(key);
				map.put(key, val);
			}
			map.put(YijipayConstants.REQUEST_NO, Ids.oid());
			map.put(YijipayConstants.PARTNER_ID, StaticProperty.YIJIFU_CORPORATE_DOWN_PARENT_ID);
			map.put("protocol", "HTTP_FORM_JSON");
			map.put("service", "enterpriseAuthModify");
			// 集成了签名和验签
			String responseStr = YijifuGateway.getOpenApiClientService()
					.doPost(StaticProperty.YIJIFU_CORPORATE_DOWN_URL, map, StaticProperty.YIJIFU_CORPORATE_DOWN_KEY);
			logger.info("URL:" + StaticProperty.YIJIFU_CORPORATE_DOWN_URL + "请求响应参数:" + responseStr);
			YijifuCorporateCallBackDTO callBack = JSONObject.parseObject(responseStr, YijifuCorporateCallBackDTO.class);
			rs.setResult(callBack);
			rs.setResultMessage("success");
			logger.info("PayInfoServiceImpl-realNameModifyVerify服务执行成功,结果参数:" + responseStr);
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("实名认证注修改册执行失败:" + e);
			logger.error("PayInfoServiceImpl-realNameVerify服务执行失败:" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<BindingBankCardCallbackDTO> bindingBankCard(BindingBankCardDTO dto) {
		logger.info("PayInfoServiceImpl-bindingBankCard服务执行,传入参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<BindingBankCardCallbackDTO> rs = new ExecuteResult<BindingBankCardCallbackDTO>();
		try {
			if (!StringUtils.isEmpty(dto.getCertNo())) {// 身份证统一转成大写
				dto.setCertNo(dto.getCertNo().toUpperCase());
			}
			Map<String, String> map = new HashMap<String, String>();
			JSONObject json = (JSONObject) JSONObject.toJSON(dto);
			Set<String> set = json.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String val = json.getString(key);
				map.put(key, val);
			}
			map.put(YijipayConstants.REQUEST_NO, Ids.oid());
			map.put(YijipayConstants.PARTNER_ID, StaticProperty.YIJIFU_CORPORATE_DOWN_PARENT_ID);
			map.put("protocol", "HTTP_FORM_JSON");
			map.put("service", "cardbindWithAuth");
			map.put("notifyUrl", StaticProperty.YIJIFU_CALLBACK_URL);

			// 集成了签名和验签
			String responseStr = YijifuGateway.getOpenApiClientService()
					.doPost(StaticProperty.YIJIFU_CORPORATE_DOWN_URL, map, StaticProperty.YIJIFU_CORPORATE_DOWN_KEY);
			logger.info("URL:" + StaticProperty.YIJIFU_CORPORATE_DOWN_URL + "请求响应参数:" + responseStr);
			BindingBankCardCallbackDTO callBack = JSONObject.parseObject(responseStr, BindingBankCardCallbackDTO.class);
			rs.setResult(callBack);
			rs.setResultMessage("success");
			logger.info("PayInfoServiceImpl-bindingBankCard服务执行成功,结果参数:" + responseStr);
		} catch (Exception e) {
			rs.setResultMessage("fail");
			rs.addErrorMessage("绑卡执行失败:" + e);
			logger.error("PayInfoServiceImpl-bindingBankCard服务执行失败:" + e);
		}
		return rs;
	}

	@Override
	public Boolean yijiRealNameModify(String accountNo, String notifyTypeEnum) {
		logger.info("PayInfoServiceImpl-yijiRealNameModify服务执行,传入参数:" + accountNo);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(YijipayConstants.REQUEST_NO, Ids.oid());
			map.put(YijipayConstants.PARTNER_ID, StaticProperty.YIJIFU_CORPORATE_DOWN_PARENT_ID);
			map.put("protocol", "HTTP_FORM_JSON");
			map.put("service", "customerQuery");
			map.put("userId", accountNo);

			// 集成了签名和验签
			String responseStr = YijifuGateway.getOpenApiClientService()
					.doPost(StaticProperty.YIJIFU_CORPORATE_DOWN_URL, map, StaticProperty.YIJIFU_CORPORATE_DOWN_KEY);
			logger.info("URL:" + StaticProperty.YIJIFU_CORPORATE_DOWN_URL + "请求响应参数:" + responseStr);
			JSONObject obj = JSONObject.parseObject(responseStr);

			Boolean isSuccess = obj.getBoolean("success");
			if (isSuccess) {
				String certNo = obj.getString("certNo");
				MemberBaseInfoDTO dto = new MemberBaseInfoDTO();
				dto.setAccountNo(accountNo);
				dto.setArtificialPersonIdcard(certNo);
				Date tranceDate = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
						"yyyy-MM-dd HH:mm:ss");
				dto.setModifyTime(tranceDate);
				if ("MOBILE_AUTH".equals(notifyTypeEnum)) {// 手机认证
					dto.setIsPhoneAuthenticated(GlobalConstant.FLAG_YES);
					payInfoDAO.updateMemberPhoneVerify(dto);
				} else {// 实名认证
					String realNameStatus = obj.getString("realNameAuth");
					String type = obj.getString("type");
					if ("BUSINESS".equals(type)) {
						dto.setBuyerSellerType(GlobalConstant.IS_SELLER);
					} else {
						dto.setBuyerSellerType(GlobalConstant.IS_BUYER);
					}
					if ("AUTH_OK".equals(realNameStatus)) {
						dto.setRealNameStatus("3");
					} else if ("AUTH_NO".equals(realNameStatus)) {
						dto.setRealNameStatus("1");
					} else if ("AUTH_ING".equals(realNameStatus)) {
						dto.setRealNameStatus("2");
					} else {
						dto.setRealNameStatus("4");
					}
					payInfoDAO.updateMemberCertInfo(dto);
				}

			}
			logger.info("PayInfoServiceImpl-yijiRealNameModify服务执行成功,结果参数:" + responseStr);
		} catch (Exception e) {
			logger.error("支付实名修改同步处理失败" + e);
			return false;
		}
		return true;
	}

	@Override
	public Boolean cardUnsign(BindingBankCardDTO dto) {
		logger.info("PayInfoServiceImpl-cardUnsign服务执行,传入参数:" + JSONObject.toJSONString(dto));
		Map<String, String> map = new HashMap<String, String>();
		map.put(YijipayConstants.REQUEST_NO, Ids.oid());
		map.put(YijipayConstants.PARTNER_ID, StaticProperty.YIJIFU_CORPORATE_DOWN_PARENT_ID);
		map.put("protocol", "HTTP_FORM_JSON");
		map.put("service", "cardUnsign");
		map.put("userId", dto.getUserId());
		map.put("bindId", dto.getBindId());
		// 集成了签名和验签
		String responseStr = YijifuGateway.getOpenApiClientService().doPost(StaticProperty.YIJIFU_CORPORATE_DOWN_URL,
				map, StaticProperty.YIJIFU_CORPORATE_DOWN_KEY);
		logger.info("URL:" + StaticProperty.YIJIFU_CORPORATE_DOWN_URL + "请求响应参数:" + responseStr);
		logger.info("PayInfoServiceImpl-cardUnsign服务执行成功,返回参数:" + JSONObject.toJSONString(responseStr));
		JSONObject obj = JSONObject.parseObject(responseStr);
		String rs = obj.getString("resultCode");
		if ("EXECUTE_SUCCESS".equals(rs)) {
			return true;
		}
		return false;

	}

	@Override
	public ExecuteResult<Boolean> bindCardBack(BindingBankCardCallbackDTO dto) {
		logger.info("PayInfoServiceImpl-bindCardBack服务执行,传入参数:" + JSONObject.toJSONString(dto));
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			MemberOutsideSupplierCompanyDTO sellDto = new MemberOutsideSupplierCompanyDTO();
			sellDto.setBindId(dto.getBindId());
			sellDto.setAccountNo(dto.getUserId());
			if ("ENABLE".equals(dto.getPactStatus())) {
				sellDto.setCardBindStatus("3");
			} else {
				sellDto.setCardBindStatus("4");
			}
			payInfoDAO.updateMemberBankInfo(sellDto);
			rs.setResult(true);
			rs.setResultMessage("success");
			logger.info("PayInfoServiceImpl-bindCardBack服务执行成功");
		} catch (Exception e) {
			logger.info("PayInfoServiceImpl-bindCardBack服务执行失败");
			rs.setResult(false);
			rs.setResultMessage("fail");
		}
		return rs;
	}

}
