package cn.htd.zeus.tc.api;

import cn.htd.zeus.tc.dto.response.JDCreateOrderCallBackResDTO;
import cn.htd.zeus.tc.dto.resquest.JDCreateOrderCallBackReqDTO;

/*
 * 京东抛单回调接口
 */
public interface JDCreateOrderCallBackAPI {
	
	/*
	 * 京东抛单回调方法
	 */
	public JDCreateOrderCallBackResDTO jdCreateOrderCallBack(JDCreateOrderCallBackReqDTO jdCreateOrderCallBackReqDTO);
}
