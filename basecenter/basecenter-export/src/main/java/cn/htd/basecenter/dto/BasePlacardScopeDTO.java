package cn.htd.basecenter.dto;

import java.io.Serializable;
import java.util.List;

public class BasePlacardScopeDTO implements Serializable {

	private static final long serialVersionUID = 64128201427896223L;

	/**
	 * 公告范围：1:内部供应商，2:外部供应商，3:全部会员，4:部分会员
	 */
	private String scopeType = "";

	/**
	 * 公告范围对象为部分会员时，会员等级对象列表
	 */
	private List<String> targetBuyerGradeList;

	/**
	 * 公告范围对象为部分会员时，会员分组对象列表
	 */
	private List<Long> targetBuyerGroupList;

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public List<String> getTargetBuyerGradeList() {
		return targetBuyerGradeList;
	}

	public void setTargetBuyerGradeList(List<String> targetBuyerGradeList) {
		this.targetBuyerGradeList = targetBuyerGradeList;
	}

	public List<Long> getTargetBuyerGroupList() {
		return targetBuyerGroupList;
	}

	public void setTargetBuyerGroupList(List<Long> targetBuyerGroupList) {
		this.targetBuyerGroupList = targetBuyerGroupList;
	}

}