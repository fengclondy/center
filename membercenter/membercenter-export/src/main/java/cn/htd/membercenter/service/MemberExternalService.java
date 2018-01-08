package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberExternalDTO;

public interface MemberExternalService {

	/**
	 * 根据会员店编码-查询带客下单地址信息 - 收货人、收货电话、收货地址
	 * memberCode :htd123456
	 * @return
	 */
	public ExecuteResult<MemberExternalDTO> queryMemberAddressInfo(String memberCode);
	
	/**
	 * 根据会员店编码-查询带客下单地址信息 - 收货人、收货电话、收货地址
	 * MemberExternalDTO :会员地址相关信息
	 * flag:新增修改标志 1 新增 0 修改
	 * @return
	 */
	public ExecuteResult<Boolean> addUpdateAddressInfo(MemberExternalDTO dto);
	
}
