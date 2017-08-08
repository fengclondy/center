package cn.htd.membercenter.domain;

import java.io.Serializable;

/**
 * Created by thinkpad on 2016/11/15.
 */
public class MemberStatus implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status_id;
	private int member_id;
	private String info_type;
	private String verify_status;
	private String verify_time;
	private int verify_id;
	private String sync_error_msg;
	private int create_id;
	private String create_name;
	private String create_time;
	private int modify_id;
	private String modify_name;
	private String modify_time;

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getInfo_type() {
		return info_type;
	}

	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}

	public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getVerify_time() {
		return verify_time;
	}

	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}

	public int getVerify_id() {
		return verify_id;
	}

	public void setVerify_id(int verify_id) {
		this.verify_id = verify_id;
	}

	public String getSync_error_msg() {
		return sync_error_msg;
	}

	public void setSync_error_msg(String sync_error_msg) {
		this.sync_error_msg = sync_error_msg;
	}

	public int getCreate_id() {
		return create_id;
	}

	public void setCreate_id(int create_id) {
		this.create_id = create_id;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getModify_id() {
		return modify_id;
	}

	public void setModify_id(int modify_id) {
		this.modify_id = modify_id;
	}

	public String getModify_name() {
		return modify_name;
	}

	public void setModify_name(String modify_name) {
		this.modify_name = modify_name;
	}

	public String getModify_time() {
		return modify_time;
	}

	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
}
