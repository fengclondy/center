package com.bjucloud.contentcenter.domain;

import java.io.Serializable;

public class MallSpecialSubject implements Serializable{
	
	private static final long serialVersionUID = 5747671908789307424L;
	private long id;//id
	private String sub_title;//专题标题
	private String image;//图片
	private String begin_date;//开始时间
	private String end_date;//结束时间
	private String create_user_name;//专题创建人
	private long create_user_id;//专题创建人id
	private String update_user_name;//专题修改人
	private long update_user_id;//专题修改人id
	private String sec_title;//副文本
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public long getCreate_user_id() {
		return create_user_id;
	}
	public void setCreate_user_id(long create_user_id) {
		this.create_user_id = create_user_id;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public long getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(long update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getSec_title() {
		return sec_title;
	}
	public void setSec_title(String sec_title) {
		this.sec_title = sec_title;
	}
	
}
