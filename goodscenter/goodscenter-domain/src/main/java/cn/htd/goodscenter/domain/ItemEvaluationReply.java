package cn.htd.goodscenter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <p>
 * Description: [描述该类概要功能介绍:商品评价回复domain类]
 * </p>
 */
public class ItemEvaluationReply implements Serializable {

	private static final long serialVersionUID = -5384707102805105545L;
	private Long id;// id
	private Long evaluation_id;// 评价id
	private String content;// 评价内容
	private Date createTime;// 创建时间
	private Date modifyTime;// 编辑时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEvaluation_id() {
		return evaluation_id;
	}

	public void setEvaluation_id(Long evaluation_id) {
		this.evaluation_id = evaluation_id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
