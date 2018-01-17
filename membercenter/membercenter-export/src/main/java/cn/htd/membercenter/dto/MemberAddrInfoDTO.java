package cn.htd.membercenter.dto;

import java.io.Serializable;

/** 
 * <Description> <br> 
 *  
 * @author zhoutong <br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年1月16日 <br>
 */

public class MemberAddrInfoDTO extends MemberBaseDTO implements Serializable {

	/**
	 * 拼接好的地址 <br>
	 */
	private String locationAddr;

	/** 
	 * get locationAddr
	 * @return Returns the locationAddr.<br> 
	 */
	public String getLocationAddr() {
		return locationAddr;
	}

	/** 
	 * set locationAddr
	 * @param locationAddr The locationAddr to set. <br>
	 */
	public void setLocationAddr(String locationAddr) {
		this.locationAddr = locationAddr;
	}
	
}
