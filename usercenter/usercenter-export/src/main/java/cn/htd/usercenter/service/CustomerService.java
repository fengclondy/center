package cn.htd.usercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.UserDTO;

public interface CustomerService {

	/**
	 * 添加客户
	 * 
	 * @param customerDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> addCustomer(CustomerDTO customerDTO, long userId);

	/**
	 * 添加外部供应商
	 * 
	 * @param customerDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> addOUTSeller(CustomerDTO customerDTO, long userId);

	/**
	 * 添加供应商
	 * 
	 * @param customerDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> addSeller(CustomerDTO customerDTO, long userId);

	/**
	 * 根据名称查询
	 * 
	 * @param name
	 * @param page
	 * @param rows
	 * @param companyId
	 * @return
	 */
	public ExecuteResult<DataGrid<CustomerDTO>> selectCustomerList(String name, int page, int rows, long companyId);

	/**
	 * 更新客户信息
	 * 
	 * @param customerDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> editCustomer(CustomerDTO customerDTO, long userId);

	/**
	 * 更新客户手机信息
	 * 
	 * @param customerDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> editCustomerMobile(CustomerDTO customerDTO, long userId);

	/**
	 * 判断手机是否重复
	 * 
	 * @param mobile
	 * @return
	 */
	public ExecuteResult<Boolean> isRepeatMobile(String mobile);

	/**
	 * 判断手机是否重复
	 * 
	 * @param mobile
	 * @return
	 */
	public ExecuteResult<Boolean> isRepeatMobileByUserId(String mobile, long userId);

	/**
	 * 删除
	 * 
	 * @param userid
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> deleteCustomer(long userid, long userId);

	/**
	 * 根据userid获取相关信息
	 * 
	 * @param userId
	 * @return
	 */
	public CustomerDTO getCustomerInfo(long userId);

	/**
	 * 更新权限信息
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> updateCustomerPerm(CustomerDTO customerDTO, long userid, long userId);

	/**
	 * 验证会员有效性
	 * 
	 * @param memberCode
	 *            会员编码
	 * @return
	 */
	public ExecuteResult<Boolean> validCustomer(String memberCode);

	/**
	 * 验证会员有效性
	 * 
	 * @param memberCode
	 *            会员编码
	 * @param memberName
	 *            会员名称
	 * @return 手机号
	 */
	public ExecuteResult<String> validVmsCustomer(String memberCode, String memberName);

	/**
	 * 修改会员有效性
	 * 
	 * @param memberCode
	 *            会员编码
	 * @param valid
	 * @return
	 */
	public ExecuteResult<Boolean> editValidCustomer(String memberCode, boolean valid, Long userId);

	/**
	 * 生成外部供应商编码
	 * 
	 * @return 外部供应商编码
	 */
	public ExecuteResult<String> genOUTSellerCode();

	/**
	 * 生成会员编码
	 * 
	 * @return 会员编码
	 */
	public ExecuteResult<String> genMemberCode();

	/**
	 * 生成供应商编码
	 * 
	 * @return 供应商编码
	 */
	public ExecuteResult<String> genSellerCode();

	/**
	 * 验证会员有效性
	 * 
	 * @param memberCode
	 *            会员手机号
	 * @return
	 */
	public ExecuteResult<Long> validCustomerMobile(String mobile);

	/**
	 * @param userDTO
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> editCustomerAvatar(UserDTO userDTO, long userId);
}
