package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价回复DTO类]
 * </p>
 */
public class ItemEvaluationReplyDTO implements Serializable {

	private static final long serialVersionUID = 580712480302604048L;
	private Long id;// id
	private Long evaluationId;// 评价id
	private String content;// 评价内容
	private Date createTime;// 创建时间
	private Date modifyTime;// 编辑时间

	public Long getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Long evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
