/**
 * 
 */
package cn.htd.zeus.tc.biz.dmo;

import java.io.Serializable;

/**
 * @author ly
 *
 */
public class GenericDMO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1096913421514694475L;

	/**
	 * 业务返回码
	 */
	private String resultCode = null;
	
	/**
	 * 业务返回描述
	 */
	private String resultMsg = null;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
