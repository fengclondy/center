package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseSmsConfigDTO implements Serializable {

	private static final long serialVersionUID = 6008185440283522692L;

	private Long id;

	private String type;

	private String channelCode;

	private String sendName;

	private String sendAddress;

	private String emailType;

	private String sendServer;

	private Integer sendServerPort;

	private String receiveServer;

	private Integer receiveServerPort;

	private int isUseSmtpAuth;

	private String loginEmail;

	private String loginPassword;

	private String msgUrl;

	private String msgHost;

	private String msgAccount;

	private String msgPassword;

	private String msgPszsubport;

	private String msgSa;

	private String msgSoapaddress;

	private int usedFlag;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode == null ? null : channelCode.trim();
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName == null ? null : sendName.trim();
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress == null ? null : sendAddress.trim();
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType == null ? null : emailType.trim();
	}

	public String getSendServer() {
		return sendServer;
	}

	public void setSendServer(String sendServer) {
		this.sendServer = sendServer == null ? null : sendServer.trim();
	}

	public Integer getSendServerPort() {
		return sendServerPort;
	}

	public void setSendServerPort(Integer sendServerPort) {
		this.sendServerPort = sendServerPort;
	}

	public String getReceiveServer() {
		return receiveServer;
	}

	public void setReceiveServer(String receiveServer) {
		this.receiveServer = receiveServer == null ? null : receiveServer.trim();
	}

	public Integer getReceiveServerPort() {
		return receiveServerPort;
	}

	public void setReceiveServerPort(Integer receiveServerPort) {
		this.receiveServerPort = receiveServerPort;
	}

	public int getIsUseSmtpAuth() {
		return isUseSmtpAuth;
	}

	public void setIsUseSmtpAuth(int isUseSmtpAuth) {
		this.isUseSmtpAuth = isUseSmtpAuth;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail == null ? null : loginEmail.trim();
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword == null ? null : loginPassword.trim();
	}

	public String getMsgUrl() {
		return msgUrl;
	}

	public void setMsgUrl(String msgUrl) {
		this.msgUrl = msgUrl == null ? null : msgUrl.trim();
	}

	public String getMsgHost() {
		return msgHost;
	}

	public void setMsgHost(String msgHost) {
		this.msgHost = msgHost == null ? null : msgHost.trim();
	}

	public String getMsgAccount() {
		return msgAccount;
	}

	public void setMsgAccount(String msgAccount) {
		this.msgAccount = msgAccount == null ? null : msgAccount.trim();
	}

	public String getMsgPassword() {
		return msgPassword;
	}

	public void setMsgPassword(String msgPassword) {
		this.msgPassword = msgPassword == null ? null : msgPassword.trim();
	}

	public String getMsgPszsubport() {
		return msgPszsubport;
	}

	public void setMsgPszsubport(String msgPszsubport) {
		this.msgPszsubport = msgPszsubport == null ? null : msgPszsubport.trim();
	}

	public String getMsgSa() {
		return msgSa;
	}

	public void setMsgSa(String msgSa) {
		this.msgSa = msgSa == null ? null : msgSa.trim();
	}

	public String getMsgSoapaddress() {
		return msgSoapaddress;
	}

	public void setMsgSoapaddress(String msgSoapaddress) {
		this.msgSoapaddress = msgSoapaddress == null ? null : msgSoapaddress.trim();
	}

	public int getUsedFlag() {
		return usedFlag;
	}

	public void setUsedFlag(int usedFlag) {
		this.usedFlag = usedFlag;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName == null ? null : createName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName == null ? null : modifyName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}