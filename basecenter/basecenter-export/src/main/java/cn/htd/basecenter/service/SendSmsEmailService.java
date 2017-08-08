package cn.htd.basecenter.service;

import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.SendEmailDTO;
import cn.htd.basecenter.dto.SendSmsDTO;
import cn.htd.common.ExecuteResult;

/**
 * Created by lh on 2016/11/1.
 */
public interface SendSmsEmailService {

	/**
	 * 发送短信
	 * 
	 * @param sendSmsDTO
	 * @return
	 */
	public ExecuteResult<String> sendSms(SendSmsDTO sendSmsDTO);

	/**
	 * 发送邮件
	 * 
	 * @param sendSmsDTO
	 * @return
	 */
	public ExecuteResult<String> sendEmail(SendEmailDTO sendEmailDTO);
	
	public ExecuteResult<String> doSendEmailByTemplate(MailWarnInDTO mailWarnInDTO);

}
