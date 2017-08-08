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

public class MemberImportInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int succCount = 0;// 成功条数
	private List<MemberImportSuccInfoDTO> succInfoList;// 成功详细信息集合
	private int failCount = 0;// 失败条数
	private List<MemberImportFailInfoDTO> failInfoList;// 失败详细信息集合

	/**
	 * @return the succCount
	 */
	public int getSuccCount() {
		return succCount;
	}

	/**
	 * @param succCount
	 *            the succCount to set
	 */
	public void setSuccCount(int succCount) {
		this.succCount = succCount;
	}

	/**
	 * @return the succInfoList
	 */
	public List<MemberImportSuccInfoDTO> getSuccInfoList() {
		return succInfoList;
	}

	/**
	 * @param succInfoList
	 *            the succInfoList to set
	 */
	public void setSuccInfoList(List<MemberImportSuccInfoDTO> succInfoList) {
		this.succInfoList = succInfoList;
	}

	/**
	 * @return the failCount
	 */
	public int getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount
	 *            the failCount to set
	 */
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the failInfoList
	 */
	public List<MemberImportFailInfoDTO> getFailInfoList() {
		return failInfoList;
	}

	/**
	 * @param failInfoList
	 *            the failInfoList to set
	 */
	public void setFailInfoList(List<MemberImportFailInfoDTO> failInfoList) {
		this.failInfoList = failInfoList;
	}

}
