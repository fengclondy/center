/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MemberImportInfo</p>
* @author jiangt
* @date 2016年12月1日
* <p>Description: 
*			会员供应商导入
* </p>
 */
package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class BoxRelationImportDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int succCount = 0;// 成功条数
	private List<ApplyBusiRelationDTO> succInfoList;// 成功详细信息集合
	private int failCount = 0;// 失败条数
	private List<ApplyBusiRelationDTO> failInfoList;// 失败详细信息集合
	private List<Long> boxIds;// 包厢关系id
	
	

	public List<Long> getBoxIds() {
		return boxIds;
	}

	public void setBoxIds(List<Long> boxIds) {
		this.boxIds = boxIds;
	}

	public int getSuccCount() {
		return succCount;
	}

	public void setSuccCount(int succCount) {
		this.succCount = succCount;
	}

	
	public int getFailCount() {
		return failCount;
	}

	public List<ApplyBusiRelationDTO> getSuccInfoList() {
		return succInfoList;
	}

	public void setSuccInfoList(List<ApplyBusiRelationDTO> succInfoList) {
		this.succInfoList = succInfoList;
	}

	public void setFailInfoList(List<ApplyBusiRelationDTO> failInfoList) {
		this.failInfoList = failInfoList;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public List<ApplyBusiRelationDTO> getFailInfoList() {
		return failInfoList;
	}


}
