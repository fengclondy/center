package cn.htd.membercenter.service.listenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.alibaba.fastjson.JSONObject;
import com.yiji.openapi.tool.YijifuGateway;
import com.yiji.openapi.tool.YijipayConstants;
import com.yiji.openapi.tool.util.Ids;

import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.common.util.HttpUtils;
import cn.htd.membercenter.dto.ErpSellerupDTO;
import cn.htd.membercenter.dto.LegalPerson;
import cn.htd.membercenter.dto.YijifuCorporateDTO;
import cn.htd.membercenter.service.ErpService;

public class ErpSellerupListenter implements MessageListener {
	protected static transient Logger logger = LoggerFactory.getLogger(ErpSellerupListenter.class);

	@Resource
	ErpService erpService;

	@Override
	public void onMessage(Message message) {
		logger.info("内部供应商上行同步,传入参数:" + message.toString());
		boolean result = false;
		String errMsg = "";
		String erpLogStatus = "";
		ErpSellerupDTO dto = JSONObject.parseObject(new String(message.getBody()), ErpSellerupDTO.class);
		/**
		 * ErpSellerupDTO dto = new ErpSellerupDTO();
		 * dto.setBusinessAddressCity("12345");
		 * dto.setBusinessAddressCounty("12345");
		 * dto.setBusinessAddressDetailAddress("12345");
		 * dto.setCompanyLeagalPersion("12345"); dto.setContactMobile("12345");
		 * dto.setContactEmail("12345"); dto.setContactName("12345");
		 * dto.setCreateTime("12345"); dto.setDepositBank("12345");
		 * dto.setDepositBank("12345"); dto.setFinancialAccount("12345");
		 * dto.setIsNormalTaxpayer("12345"); dto.setMemberCode("12345");
		 * dto.setRegisteredAddressProvince("12345");
		 * dto.setTaxpayerIDnumber("12345"); dto.setUseOtherChannels("12345");
		 * dto.setVendorName("12345"); dto.setVendorCode("12345");
		 **/
		if (null != dto.getContactMobile() && dto.getContactMobile().length() != 11) {
			dto.setContactMobile("");
		}

		try {
			erpService.saveErpSellerup(dto);
			result = true;
			erpLogStatus = "3";
		} catch (Exception e) {
			result = false;
			errMsg = "上行失败";
			erpLogStatus = "4";
			logger.info("内部供应商上行同步失败,同步失败的数据为:" + message.toString() + e);
		}

		dto.setStatus(erpLogStatus);
		dto.setErrMsg(errMsg);
		// erpService.saveErpUpLog(dto);

		try {
			String tokenResponse = HttpUtils.httpGet(StaticProperty.TOKEN_URL);
			JSONObject tokenResponseJSON = JSONObject.parseObject(tokenResponse);
			StringBuffer buffer = new StringBuffer();
			buffer.append("?");
			buffer.append("vendorCode=" + dto.getVendorCode());
			buffer.append("&supplierCode=" + dto.getMemberCenterCode());
			buffer.append("&flag=" + result);
			buffer.append("&errormessage=" + errMsg);
			if (tokenResponseJSON.getInteger("code") == 1) {
				buffer.append("&token=" + tokenResponseJSON.getString("data"));
				String urlParam = buffer.toString();
				String msg = HttpUtils.httpGet(StaticProperty.ERP_SELLERUP_CALLBACK_URL + urlParam);
				logger.info(msg);
			}
		} catch (Exception e) {
			logger.error("ErpSellerupListenter-onMessage方法执行失败" + e);
		}

	}

	private static String partnerId = "2014*************750";
	private static String key = "55013b3352*************b24ed4303";
	private static String url = "https://openapi.yijifu.net/gateway.html";

	public static void main(String[] args) {
		erpupTest();
	}

	public static void yijifuTest() {
		YijifuCorporateDTO dto = new YijifuCorporateDTO();
		dto.setComName("dsadad");
		dto.setCorporateUserType("dsadad");
		dto.setEmail("123213131");
		LegalPerson legalPerson = new LegalPerson();
		legalPerson.setAddress("\rDASD");
		legalPerson.setRealName("张三");
		legalPerson.setCertNo("32131");
		legalPerson.setEmail("32131");
		legalPerson.setMobileNo("32131");
		dto.setLegalPerson(legalPerson);
		dto.setLicenceNo("dsadad");
		dto.setOrganizationCode("dsadad");
		dto.setOrganizationCode("dsadad");
		dto.setOutUserId("dsadad");
		dto.setTaxAuthorityNo("\r//dsadad");
		dto.setVerifyRealName("dsadad");
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
		map.put(YijipayConstants.PARTNER_ID, partnerId);

		try {
			// 集成了签名和验签
			String responseStr = YijifuGateway.getOpenApiClientService().doPost(url, map, key);
			System.out.println("响应报文:" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void erpupTest() {

	}
}
