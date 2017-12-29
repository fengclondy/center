package cn.htd.basecenter.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import cn.htd.basecenter.service.sms.MengWangSmsClient;
import cn.htd.common.util.SysProperties;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyun.oss.common.utils.DateUtil;
import com.google.common.collect.Lists;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseSendMessageDAO;
import cn.htd.basecenter.dao.BaseSmsClientDAO;
import cn.htd.basecenter.dao.BaseSmsConfigDAO;
import cn.htd.basecenter.domain.BaseSmsClient;
import cn.htd.basecenter.dto.BaseSendMessageDTO;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.dto.SendEmailDTO;
import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.basecenter.enums.EmailMimeTypeEnum;
import cn.htd.basecenter.enums.EmailTypeEnum;
import cn.htd.basecenter.enums.SmsChannelTypeEnum;
import cn.htd.basecenter.enums.SmsEmailTypeEnum;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.basecenter.service.email.SendMailClient;
import cn.htd.basecenter.service.sms.ManDaoSmsClient;
import cn.htd.basecenter.service.sms.TianXunTongSmsClient;
import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;

@Service("sendSmsEmailService")
public class SendSmsEmailServiceImpl implements SendSmsEmailService {
	private static final Logger logger = LoggerFactory.getLogger(SendSmsEmailServiceImpl.class);

	/**
	 * 读取配置文件是否发送邮件标识
	 */
	private static final String IS_SEND_EMAIL_FLAG = "send.email.flag";
	/**
	 * 读取配置文件是否发送短信标识
	 */
	private static final String IS_SEND_SMS_FLAG = "send.sms.flag";
    /**
     * 读取配置文件预警上限数
     */
    private static final String NOTICE_SMS_BALANCE = "notice_sms_balance";
    
    /**
     * 累计1小时内短信超过限制标识
     */
    private static final String CODE_CUMULATIVE_EXCEED_LIMIT = "CUMULATIVE_EXCEED_LIMIT";
    
	@Resource
	private BaseSmsConfigDAO baseSmsConfigDAO;

	@Resource
	private BaseSmsClientDAO baseSmsClientDAO;

	@Resource
	private BaseSendMessageDAO baseSendMessageDAO;

	@Resource
	private ManDaoSmsClient manDaoSmsClient;

	@Resource
	private TianXunTongSmsClient tianXunTongSmsClient;

	@Resource
	private MengWangSmsClient mengWangSmsClient;

	@Resource
	private SendMailClient sendMailClient;
	
	@Resource
	private RedisDB redisDB;

	@Override
	public ExecuteResult<String> sendEmail(SendEmailDTO sendEmailDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		BaseSmsClient templateCondition = new BaseSmsClient();
		List<BaseSmsConfigDTO> validEmailConfigList = null;
		List<BaseSmsClient> emailTemplateList = null;
		BaseSmsClient emailTemplate = null;
		BaseSmsConfigDTO targetObj = null;
		String title = "";
		String content = "";
		List<String> parameterList = null;
		String sendResult = "";
		try {
			configCondition.setType(SmsEmailTypeEnum.EMAIL.getCode());
			configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			validEmailConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
			if (validEmailConfigList == null || validEmailConfigList.size() == 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_EMAIL_CONFIG_ERROR, "启用的邮件服务器配置信息不存在");
			}
			targetObj = validEmailConfigList.get(0);
			templateCondition.setTemplateCode(sendEmailDTO.getEmailType());
			templateCondition.setDeleteFlag(YesNoEnum.NO.getValue());
			emailTemplateList = baseSmsClientDAO.queryTemplateByCode(templateCondition);
			if (CollectionUtils.isEmpty(emailTemplateList)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_EMAIL_TEMPLATE_ERROR, "未设置发送邮件模版信息");
			}
			emailTemplate = emailTemplateList.get(0);
			title = emailTemplate.getName();
			content = emailTemplate.getContent();
			if (sendEmailDTO.getParameterList() != null && sendEmailDTO.getParameterList().size() > 0) {
				parameterList = sendEmailDTO.getParameterList();
				for (int i = 0; i < parameterList.size(); i++) {
					content = content.replace("{" + i + "}", parameterList.get(i));
				}
			}
			sendResult = sendEmail(targetObj, sendEmailDTO.getEmail(), title, content, emailTemplate.getTemplateType());
			result.setResult(sendResult);
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
	public ExecuteResult<String> sendSms(SendSmsDTO sendSmsDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		BaseSmsClient templateCondition = new BaseSmsClient();
		List<BaseSmsConfigDTO> validSmsConfigList = null;
		List<BaseSmsClient> smsTemplateList = null;
		BaseSmsClient smsTemplate = null;
		BaseSmsConfigDTO targetObj = null;
		String content = "";
		List<String> parameterList = null;
		String sendResult = "";
		try {
			configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
			configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			validSmsConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
			if (validSmsConfigList == null || validSmsConfigList.size() == 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_SMS_CONFIG_ERROR, "启用的短信通道配置信息不存在");
			}
			targetObj = validSmsConfigList.get(0);
			templateCondition.setTemplateCode(sendSmsDTO.getSmsType());
			templateCondition.setDeleteFlag(YesNoEnum.NO.getValue());
			smsTemplateList = baseSmsClientDAO.queryTemplateByCode(templateCondition);
			if (smsTemplateList == null || smsTemplateList.size() == 0) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_SMS_TEMPLATE_ERROR, "未设置发送短信模版信息");
			}
			smsTemplate = smsTemplateList.get(0);
			content = smsTemplate.getContent();
			if (sendSmsDTO.getParameterList() != null && sendSmsDTO.getParameterList().size() > 0) {
				parameterList = sendSmsDTO.getParameterList();
				for (int i = 0; i < parameterList.size(); i++) {
					content = content.replace("{" + i + "}", parameterList.get(i));
				}
			}
			sendResult = sendSmsByChannel(targetObj, sendSmsDTO.getPhone(), content);
			result.setResult(sendResult);
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
	 * 根据通道发送短信
	 * 
	 * @param config
	 * @param phoneNum
	 * @param content
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	private String sendSmsByChannel(BaseSmsConfigDTO config, String phoneNum, String content)
			throws BaseCenterBusinessException, Exception {
		String result = "";
		boolean hasError = false;
		BaseSendMessageDTO message = new BaseSendMessageDTO();
		try {
			if (String.valueOf(YesNoEnum.YES.getValue()).equals(SysProperties.getProperty(IS_SEND_SMS_FLAG))) {
				if (SmsChannelTypeEnum.MANDAO.getCode().equals(config.getChannelCode())) {
					//xmz for 2017-12-12 start
					String balanceResult =  queryBalanceByChannel(config);
					if(StringUtils.isNotEmpty(balanceResult)){
						int balance = Integer.parseInt(balanceResult);
						int balanceLimit = Integer.parseInt(SysProperties.getProperty(NOTICE_SMS_BALANCE));
						if(balance <= balanceLimit){
							config.setUsedFlag(0);
							baseSmsConfigDAO.update(config);
							BaseSmsConfigDTO configWMSelect = new BaseSmsConfigDTO();
							configWMSelect.setChannelCode(SmsChannelTypeEnum.MENGWANG.getCode());
							List<BaseSmsConfigDTO> configMWList = baseSmsConfigDAO.queryByTypeCode(configWMSelect);
							BaseSmsConfigDTO configMW = configMWList.get(0);
							configMW.setUsedFlag(1);
							baseSmsConfigDAO.update(configMW);
							result = mengWangSmsClient.sendSms(configMW, phoneNum, content);
						}else{
							result = manDaoSmsClient.sendSms(config, phoneNum, content);
						}
					}
					//xmz for 2017-12-12 end
				} else if (SmsChannelTypeEnum.TIANXUNTONG.getCode().equals(config.getChannelCode())) {
					result = tianXunTongSmsClient.sendSms(config, phoneNum, content);
				} else if (SmsChannelTypeEnum.MENGWANG.getCode().equals(config.getChannelCode())) {
					result = mengWangSmsClient.sendSms(config, phoneNum, content);
				}
				//在一个小时之内，统计发送短信的条数，超过10万条则关闭短信通道
				isCumulativeExceedLimit(phoneNum, config);
			}
		} catch (BaseCenterBusinessException bcbe) {
			hasError = true;
			result = bcbe.getMessage();
			throw bcbe;
		} catch (Exception e) {
			hasError = true;
			result = e.getMessage();
			throw e;
		} finally {
			message.setAddress(phoneNum);
			message.setContent(content);
			message.setType(SmsEmailTypeEnum.SMS.getCode());
			message.setIsSend(hasError ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue());
			message.setSendResult(result);
			baseSendMessageDAO.add(message);
		}
		return result;
	}
	
	private void isCumulativeExceedLimit(String phoneNum, BaseSmsConfigDTO config){
		String[] phoneNumSplit = phoneNum.split(",");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, +1);
		//String afterDate = DateUtil.
		//Long cumulativeLimit = redisDB.incrBy(CODE_CUMULATIVE_EXCEED_LIMIT, phoneNumSplit.length);
		
	}

	/**
	 * 发送邮件
	 * 
	 * @param config
	 * @param address
	 * @param title
	 * @param content
	 * @param mailType
	 * @return
	 * @throws Exception
	 */
	private String sendEmail(BaseSmsConfigDTO config, String address, String title, String content, String mailType)
			throws Exception {
		String result = "";
		boolean hasError = false;
		BaseSendMessageDTO message = new BaseSendMessageDTO();
		try {
			if (String.valueOf(YesNoEnum.YES.getValue()).equals(SysProperties.getProperty(IS_SEND_EMAIL_FLAG))) {
				sendMailClient.setSender(config.getSendName());
				sendMailClient.setSendAddress(config.getSendAddress());
				sendMailClient.setEmailType(EmailTypeEnum.getName(config.getEmailType()));
				sendMailClient.setSendServer(config.getSendServer());
				sendMailClient.setUsername(config.getLoginEmail());
				sendMailClient.setPassword(config.getLoginPassword());
				if (EmailTypeEnum.SMTP.getName().equals(EmailTypeEnum.getName(config.getEmailType()))) {
					sendMailClient.setNeedSmtpAuth(YesNoEnum.YES.getValue() == config.getIsUseSmtpAuth() ? "true" : "false");
				}
				sendMailClient.send(new String[]{address}, title, content, null, mailType);
			}
		} catch (BaseCenterBusinessException bcbe) {
			hasError = true;
			result = bcbe.getMessage();
			throw bcbe;
		} catch (Exception e) {
			hasError = true;
			result = e.getMessage();
			throw e;
		} finally {
			message.setAddress(address);
			message.setContent(StringUtils.substring(content, 0, 500));
			message.setType(SmsEmailTypeEnum.EMAIL.getCode());
			message.setIsSend(hasError ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue());
			message.setSendResult(result);
			baseSendMessageDAO.add(message);
		}
		return result;
	}
	public static void main(String[] args) {
		String mm="{0} sdfdsfds";
		mm=mm.replace("{0}","hihi");
		
		System.out.println(mm);
	}
	
	private String fetchMailTemplateContent(String fileNmae){
		URL url=SendSmsEmailServiceImpl.class.getClassLoader().getResource("mail_template/"+fileNmae);
		
		String content=null;
		if(url==null){
			return content;
		}
		
		try {
			 content=convertStreamToString(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
		
	}
	
	public static String convertStreamToString(InputStream is) {      
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
		StringBuilder sb = new StringBuilder();      
		String line = null;      
		try {      
		     while ((line = reader.readLine()) != null) {      
		        sb.append(line + "\n");      
		      }      
		} catch (IOException e) {      
		              e.printStackTrace();      
		} finally {      
		            try {      
		                is.close();      
		             } catch (IOException e) {      
		                  e.printStackTrace();      
		             }      
		 }    
		return sb.toString();      
	}

	@Override
	public ExecuteResult<String> doSendEmailByTemplate(MailWarnInDTO mailWarnInDTO) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
		String sendResult=null;
		try {
			configCondition.setType(SmsEmailTypeEnum.EMAIL.getCode());
			configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			List<BaseSmsConfigDTO>	emailConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
			
			if (CollectionUtils.isEmpty(emailConfigList)) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_EMAIL_CONFIG_ERROR, "启用的邮件服务器配置信息不存在");
			}
			
			String mimeType=StringUtils.isEmpty(mailWarnInDTO.getEmailMimeType()) ? EmailMimeTypeEnum.HTML.getValue(): mailWarnInDTO.getEmailMimeType();
			
			String mailContent=fetchMailTemplateContent("erp_down_warn.ftl");
			
			if(StringUtils.isEmpty(mailContent)){
				result.addErrorMessage("mailContent 为空");
				return result;
			}
			
			String showingTable=buildShowingTable(mailWarnInDTO.getRowList());
			
			if(StringUtils.isEmpty(showingTable)){
				result.addErrorMessage("showingTable 为空");
				return result;
			}
			
			//做参数替换
			mailContent = mailContent.replace("{0}",showingTable);
			
			emailConfigList.get(0).setSendName("zeus");
			emailConfigList.get(0).setSendAddress(mailWarnInDTO.getSenderMail());

			sendResult = sendEmail(emailConfigList.get(0), mailWarnInDTO.getRecevierMail(), mailWarnInDTO.getEmailTitle(), mailContent,mimeType);
			result.setResult(sendResult);
		} catch (Exception e) {
			result.setCode(ReturnCodeConst.SYSTEM_ERROR);
			result.addErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
	
	private String buildShowingTable(List<MailWarnRow> mailWarnRowList){
		
		if(CollectionUtils.isEmpty(mailWarnRowList)){
			return null;
		}
		
		
		//做排序
		sortMailWarnRow(mailWarnRowList);
		
		StringBuilder showingTableHtml=new StringBuilder();
		
		for(MailWarnRow row:mailWarnRowList){

			showingTableHtml.append("<tr>");
			
			for(MailWarnColumn mailWarnColumn:row.getMailWarnColumnList()){
				showingTableHtml.append("<td>");
				showingTableHtml.append(mailWarnColumn.getValue());
				showingTableHtml.append("</td>");
			}
			
			showingTableHtml.append("</tr>");
		}
		
		
		return showingTableHtml.toString();
	}
	
	
	private List<MailWarnRow> sortMailWarnRow(List<MailWarnRow> mailWarnRowList){
		if(CollectionUtils.isEmpty(mailWarnRowList)){
			return null;
		}
		
	    for(MailWarnRow mailWarnRow:mailWarnRowList){
	    	List<MailWarnColumn> mailWarnColList=mailWarnRow.getMailWarnColumnList();
	    	
	    	Collections.sort(mailWarnColList,new Comparator<MailWarnColumn>() {
	    		@Override
	    		public int compare(MailWarnColumn o1, MailWarnColumn o2) {
	    			return o1.getIndex()-o2.getIndex();
	    		}
			});
	    	
		}
	    
	    return mailWarnRowList;
	}

	@Override
	public ExecuteResult<String> queryBalance(BaseSmsConfigDTO targetObj) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		String queryResult = "";
		try {
			if (targetObj == null) {
				throw new BaseCenterBusinessException(ReturnCodeConst.NO_SMS_CONFIG_ERROR, "启用的短信通道配置信息不存在");
			}
			if(SmsChannelTypeEnum.MANDAO.getCode().equals(targetObj.getChannelCode())){
				queryResult = queryBalanceByChannel(targetObj);
				result.setResult(queryResult);
			}
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
	 * 根据通道查询通道余额
	 * 
	 * @param config
	 * @param phoneNum
	 * @param content
	 * @return
	 * @throws BaseCenterBusinessException
	 * @throws Exception
	 */
	private String queryBalanceByChannel(BaseSmsConfigDTO config)
			throws BaseCenterBusinessException, Exception {
		String result = "";
		try {
			if (String.valueOf(YesNoEnum.YES.getValue()).equals(SysProperties.getProperty(IS_SEND_SMS_FLAG))) {
				if (SmsChannelTypeEnum.MANDAO.getCode().equals(config.getChannelCode())) {
					result = manDaoSmsClient.queryBalanceInit(config) + "";
				}
			}
		} catch (BaseCenterBusinessException bcbe) {
			result = bcbe.getMessage();
			throw bcbe;
		} catch (Exception e) {
			result = e.getMessage();
			throw e;
		}
		return result;
	}
	
	
}
