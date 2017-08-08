package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDEditDetailInfoDTO</p>
* @author root
* @date 2017年1月16日
* <p>Description: 
*		更改记录
* </p>
 */
public class HTDEditDetailInfoDTO implements Serializable{
	private static final long serialVersionUID = 4513915038157456341L;
	private Long id  ;//'ID',
	private String modify_type ;// '业务类型，
	private Long record_id   ;// '记录ID',
	private String content_name ;// '内容名',
	private String change_table_id ;//'修改表ID',
	private String change_field_id ;//'修改字段ID',
	private String before_change ;// '修改前的内容',
	private String after_change  ;// '修改后的内容',
	private Long operator_id  ;// '操作人ID',
	private String operator_name    ;// '操作人名称',
	private Date operate_time  ;// '操作时间',
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getModify_type() {
		return modify_type;
	}
	public void setModify_type(String modify_type) {
		this.modify_type = modify_type;
	}
	public Long getRecord_id() {
		return record_id;
	}
	public void setRecord_id(Long record_id) {
		this.record_id = record_id;
	}
	public String getContent_name() {
		return content_name;
	}
	public void setContent_name(String content_name) {
		this.content_name = content_name;
	}
	public String getChange_table_id() {
		return change_table_id;
	}
	public void setChange_table_id(String change_table_id) {
		this.change_table_id = change_table_id;
	}
	public String getChange_field_id() {
		return change_field_id;
	}
	public void setChange_field_id(String change_field_id) {
		this.change_field_id = change_field_id;
	}
	public String getBefore_change() {
		return before_change;
	}
	public void setBefore_change(String before_change) {
		this.before_change = before_change;
	}
	public String getAfter_change() {
		return after_change;
	}
	public void setAfter_change(String after_change) {
		this.after_change = after_change;
	}
	public Long getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(Long operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_name() {
		return operator_name;
	}
	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}
	public Date getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}
	
	
}
