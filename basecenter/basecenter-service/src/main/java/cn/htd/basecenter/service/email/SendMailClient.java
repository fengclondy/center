package cn.htd.basecenter.service.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.common.constant.ReturnCodeConst;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;

/**
 * 发送邮件类
 * 
 * @author jiangkun
 */
@Service("sendMailClient")
public class SendMailClient {

	private final static String charset = "GBK";

	private final static String defaultMimeType = "text/plain";

	// 发件人名称
	private String sender;
	// 发件人地址
	private String sendAddress;
	// 服务器类型
	private String emailType;
	// 发送服务器
	private String sendServer;
	// 发送服务器端口
	private String sendServerPort;
	// 邮箱发送账号
	private String username;
	// 邮箱发送密码
	private String password;
	// 是否使用SMTP认证 0：不使用 1：使用
	private String needSmtpAuth;

	/**
	 * 发送邮件
	 * 
	 * @param receivers
	 *            收件人
	 * @param subject
	 *            标题
	 * @param mailContent
	 *            邮件内容
	 * @param attachements
	 *            附件
	 * @param mimetype
	 *            内容类型 默认为text/plain,如果要发送HTML内容,应设置为text/html
	 */
	public void send(String[] receivers, String subject, String mailContent, File[] attachements, String mimetype)
			throws BaseCenterBusinessException {
		Properties props = new Properties();
		props.put("mail.transport.protocol", emailType);
		props.put("mail.smtp.host", sendServer);// smtp服务器地址
		props.put("mail.smtp.auth", needSmtpAuth);// 需要校验

		final String account = this.getUsername();
		final String pwd = this.getPassword();

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, pwd);// 登录用户名/密码
			}
		});
		session.setDebug(true);
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(account));// 发件人邮件

			InternetAddress[] toAddress = new InternetAddress[receivers.length];
			for (int i = 0; i < receivers.length; i++) {
				toAddress[i] = new InternetAddress(receivers[i]);
			}
			mimeMessage.setRecipients(Message.RecipientType.TO, toAddress);// 收件人邮件
			mimeMessage.setSubject(subject, charset);

			Multipart multipart = new MimeMultipart();
			// 正文
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(mailContent,
					(StringUtils.isNotEmpty(mimetype) ? mimetype : defaultMimeType) + ";charset=" + charset);
			multipart.addBodyPart(body);// 发件内容
			// 附件
			if (attachements != null) {
				for (File attachement : attachements) {
					MimeBodyPart attache = new MimeBodyPart();
					attache.setDataHandler(new DataHandler(new FileDataSource(attachement)));
					String fileName = getLastName(attachement.getName());
					attache.setFileName(MimeUtility.encodeText(fileName, charset, null));
					multipart.addBodyPart(attache);
				}
			}
			mimeMessage.setContent(multipart);
			mimeMessage.setSentDate(new Date());
			Transport.send(mimeMessage);
		} catch (Exception e) {
			throw new BaseCenterBusinessException(ReturnCodeConst.EMAIL_SEND_ERROR,
					"邮件主题为【" + subject + "】的邮件发送失败：" + e);
		}
	}

	private static String getLastName(String fileName) {
		int pos = fileName.lastIndexOf("\\");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		pos = fileName.lastIndexOf("/");
		if (pos > -1) {
			fileName = fileName.substring(pos + 1);
		}
		return fileName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getSendServer() {
		return sendServer;
	}

	public void setSendServer(String sendServer) {
		this.sendServer = sendServer;
	}

	public String getSendServerPort() {
		return sendServerPort;
	}

	public void setSendServerPort(String sendServerPort) {
		this.sendServerPort = sendServerPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNeedSmtpAuth() {
		return needSmtpAuth;
	}

	public void setNeedSmtpAuth(String needSmtpAuth) {
		this.needSmtpAuth = needSmtpAuth;
	}

}
