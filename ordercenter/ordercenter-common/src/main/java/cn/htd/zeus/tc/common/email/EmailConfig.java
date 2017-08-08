package cn.htd.zeus.tc.common.email;

public class EmailConfig {
	
	/*
	 * 发件人
	 */
	private String recevierMail;
    
	/*
	 * 收件人
	 */
	private String senderMail;

	public String getRecevierMail() {
		return recevierMail;
	}

	public void setRecevierMail(String recevierMail) {
		this.recevierMail = recevierMail;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}
}
