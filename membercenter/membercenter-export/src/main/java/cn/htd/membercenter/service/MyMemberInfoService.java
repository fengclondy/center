package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MyMemberDTO;
import cn.htd.membercenter.dto.MyNoMemberDTO;

/**
 * 
* <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
* <p>Title: MyMemberInfoService</p>
* @author thinkpad
* @date 2016年11月26日
* <p>Description: 查询我的会员/担保会员/非会员详细信息接口
*
* </p>
 */
public interface MyMemberInfoService {
	
	/**
	 * 查询我的会员/担保会员详细信息
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MyMemberDTO> selectMyMemberInfo(Long memberId);

	/**
	 * 查询非会员详细信息
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<MyNoMemberDTO> selectNoMemberInfo(Long memberId);

}
