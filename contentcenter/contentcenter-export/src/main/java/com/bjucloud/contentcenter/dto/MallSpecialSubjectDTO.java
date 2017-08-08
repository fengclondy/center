package com.bjucloud.contentcenter.dto;

import java.io.Serializable;

public class MallSpecialSubjectDTO implements Serializable{

	private static final long serialVersionUID = 513830594094267856L;
	private Long id;//id
	private String sub_title;//专题标题
	private String image_url;//图片链接
	private String bgimgUrl;//背景色
	private String subject_url;//专题链接
	private String begin_date;//开始时间
	private String end_date;//结束时间
	private String create_user_name;//专题创建人
	private Long create_user_id;//专题创建人id
	private String update_user_name;//专题修改人
	private Long update_user_id;//专题修改人id
	private String sec_title;//副文本
	
	public String getBgimgUrl() {
		return bgimgUrl;
	}
	public void setBgimgUrl(String bgimgUrl) {
		this.bgimgUrl = bgimgUrl;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getSubject_url() {
		return subject_url;
	}
	public void setSubject_url(String subject_url) {
		this.subject_url = subject_url;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public Long getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(Long create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public Long getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(Long update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getSec_title() {
		return sec_title;
	}
	public void setSec_title(String sec_title) {
		this.sec_title = sec_title;
	}
	
}
