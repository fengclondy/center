package cn.htd.basecenter.domain;

/**
 * <p>
 * Description: [站内信]
 * </p>
 */
public class BaseWebsiteMessage {

	private Long id;// 主键

	private Long wmFromUserid;// 发信人用户userid

	private Long wmToUserid;// 收信人用户id

	private String wmToUsername;// 收信人用户名

	private String wmFromUsername;// 发信人用户名

	private String wmContext;// 站内信内容

	private java.util.Date wmCreated;// 发信时间

	private Integer wmRead;// 是否已读（1：未读，2：已读）

	private java.util.Date modified;//

	private Integer type;// 消息类型（1、系统消息）
	
	
	
	

	public BaseWebsiteMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseWebsiteMessage(Long id, Integer wmRead) {
		super();
		this.id = id;
		this.wmRead = wmRead;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWmFromUserid() {
		return wmFromUserid;
	}

	public void setWmFromUserid(Long wmFromUserid) {
		this.wmFromUserid = wmFromUserid;
	}

	public Long getWmToUserid() {
		return wmToUserid;
	}

	public void setWmToUserid(Long wmToUserid) {
		this.wmToUserid = wmToUserid;
	}

	public String getWmToUsername() {
		return wmToUsername;
	}

	public void setWmToUsername(String wmToUsername) {
		this.wmToUsername = wmToUsername;
	}

	public String getWmContext() {
		return wmContext;
	}

	public void setWmContext(String wmContext) {
		this.wmContext = wmContext;
	}

	public java.util.Date getWmCreated() {
		return wmCreated;
	}

	public void setWmCreated(java.util.Date wmCreated) {
		this.wmCreated = wmCreated;
	}

	public Integer getWmRead() {
		return wmRead;
	}

	public void setWmRead(Integer wmRead) {
		this.wmRead = wmRead;
	}

	public java.util.Date getModified() {
		return modified;
	}

	public void setModified(java.util.Date modified) {
		this.modified = modified;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWmFromUsername() {
		return wmFromUsername;
	}

	public void setWmFromUsername(String wmFromUsername) {
		this.wmFromUsername = wmFromUsername;
	}

}
