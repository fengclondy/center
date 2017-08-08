package com.bjucloud.contentcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: HTDAdvertisementDTO</p>
* @author root
* @date 2017年1月12日
* <p>Description: 
*		顶通广告栏
* </p>
 */
public class HTDAdvertisementDTO implements Serializable{

	private static final long serialVersionUID = -8316215311712436660L;
	private Long  id   ;// 'ID',
	private String sub_id;//城市站
	private String  pic_url  ;//'图片地址',
	private String  link_url ;//'指向连接地址',
	private Date  start_time ;// '生效开始时间',
	private Date  end_time  ;// '生效结束时间',
	private String startTimetemp;//生效开始时间  接收页面传值使用
	private String endTimetemp;//生效结束时间     接收页面传值使用
	private Long sort_num;//显示顺序
	private String  status  ;// '状态  1:上架、0:下架',
	private String is_handoff;//是否手动下架  0初始  1手动下架   2手动上架
	private String type;//类型   1 大轮播
	private Long  create_id  ;//'创建人ID',
	private String  create_name ;// '创建人名称',
	private Date  create_time ;//'创建时间',
	private Long  modify_id  ;// '更新人ID',
	private String  modify_name ;// '更新人名称',
	private String  modify_time  ;// '更新时间',
	private Integer isEditPage;//是否是编辑页 1：是 0：否

	/**
	 * 当前任务类型的任务队列数量
	 */
	private int taskQueueNum;
	/**
	 * 当前调度服务器，分配到的可处理队列
	 */
	private List<String> taskIdList;
	
	
	public String getSub_id() {
		return sub_id;
	}
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}
	public String getIs_handoff() {
		return is_handoff;
	}
	public void setIs_handoff(String is_handoff) {
		this.is_handoff = is_handoff;
	}
	public Long getSort_num() {
		return sort_num;
	}
	public void setSort_num(Long sort_num) {
		this.sort_num = sort_num;
	}
	public String getStartTimetemp() {
		return startTimetemp;
	}
	public void setStartTimetemp(String startTimetemp) {
		this.startTimetemp = startTimetemp;
	}
	public String getEndTimetemp() {
		return endTimetemp;
	}
	public void setEndTimetemp(String endTimetemp) {
		this.endTimetemp = endTimetemp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getCreate_id() {
		return create_id;
	}
	public void setCreate_id(Long create_id) {
		this.create_id = create_id;
	}
	public String getCreate_name() {
		return create_name;
	}
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Long getModify_id() {
		return modify_id;
	}
	public void setModify_id(Long modify_id) {
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

	public Integer getIsEditPage() {
		return isEditPage;
	}

	public void setIsEditPage(Integer isEditPage) {
		this.isEditPage = isEditPage;
	}

	public int getTaskQueueNum() {
		return taskQueueNum;
	}

	public void setTaskQueueNum(int taskQueueNum) {
		this.taskQueueNum = taskQueueNum;
	}

	public List<String> getTaskIdList() {
		return taskIdList;
	}

	public void setTaskIdList(List<String> taskIdList) {
		this.taskIdList = taskIdList;
	}
}
