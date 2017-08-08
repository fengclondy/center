package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.FunctionDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.LoginLogDTO;
import cn.htd.usercenter.dto.LoginParamDTO;
import cn.htd.usercenter.dto.LoginResDTO;
import cn.htd.usercenter.dto.MenuDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.dto.VmsmenuDTO;

/**
 * 用户的处理接口类
 */
public interface UserExportService {

	/**
	 * 员工登录
	 *
	 * @param paramDTO
	 *            登录参数DTO
	 */
	public ExecuteResult<LoginResDTO> employeeLogin(LoginParamDTO paramDTO);

	/**
	 * 客户登录
	 *
	 * @param paramDTO
	 *            登录参数DTO
	 */
	public ExecuteResult<LoginResDTO> customerLogin(LoginParamDTO paramDTO);

	/**
	 * 登出
	 *
	 * @param ticket
	 *            SSO用Ticket
	 */
	public ExecuteResult<Boolean> logout(String ticket);

	/**
	 * 重新激活员工（清零用户失败登陆次数）
	 *
	 * @param loginId
	 *            登录ID
	 */
	public ExecuteResult<Boolean> activeEmployee(String loginId);

	/**
	 * 重新激活客户（清零用户失败登陆次数）
	 *
	 * @param loginId
	 *            登录ID
	 */
	public ExecuteResult<Boolean> activeCustomer(String loginId);

	/**
	 * 验证用户登录Ticket
	 *
	 * @param ticket
	 *            SSO用Ticket
	 */
	public ExecuteResult<LoginResDTO> validTicket(String ticket);

	/**
	 * 取得用户菜单权限
	 *
	 * @param userId
	 *            用户ID
	 * @param productId
	 *            产品ID
	 */
	public ExecuteResult<List<MenuDTO>> getMenusByUser(Long userId, String productId);

	/**
	 * 取得用户页面机能权限
	 *
	 * @param userId
	 *            用户ID
	 * @param productId
	 *            产品ID
	 * @param permissionId
	 *            权限项目ID(页面)
	 */
	public ExecuteResult<List<FunctionDTO>> getPageFunctionsByUser(Long userId, String productId, String permissionId);

	/**
	 * 取得员工负责平台公司
	 *
	 * @param userId
	 *            员工的用户ID
	 * @return 负责平台公司
	 */
	public ExecuteResult<List<String>> getInchargeCompanies(Long userId);

	/**
	 * 验证用户操作权限
	 *
	 * @param userId
	 *            用户ID
	 * @param productId
	 *            产品ID
	 * @param processUrl
	 *            操作URL
	 */
	public ExecuteResult<Boolean> validProcessPermission(Long userId, String productId, String processUrl);

	/**
	 * 取得指定公司下的所有员工数据
	 *
	 * @param companyId
	 *            公司ID
	 * @return 指定公司下的所有员工数据
	 */
	public ExecuteResult<List<EmployeeDTO>> getEmployeeByCompany(String companyId);

	/**
	 * 取得指定公司下的下级公司数据
	 *
	 * @param parentCompanyId
	 *            父公司ID
	 * @param name
	 *            公司名(模糊查询)
	 * @return 指定父公司下的下级公司数据
	 */
	public ExecuteResult<List<HTDCompanyDTO>> getCompanysByParentCompany(String parentCompanyId, String name);

	/**
	 * 密码重置
	 *
	 * @param userId
	 *            用户ID
	 * @param pwd
	 *            新密码
	 * @param userId
	 *            操作人id
	 * @return
	 */
	public ExecuteResult<Boolean> passwdReset(Long userid, String pwd, Long userId);

	public ExecuteResult<LoginLogDTO> queryLastLoginLog(Long userid);

	/**
	 * 取得人员
	 *
	 * @param userId
	 * @param isEmployee
	 * @return
	 */
	public ExecuteResult<UserDTO> queryUserByUserId(Long userId, boolean isEmployee);

	/**
	 * 取得人员
	 *
	 * @param userId
	 * @param isEmployee
	 * @return
	 */
	public ExecuteResult<UserDTO> queryUserByLoginId(String loginId);

	/**
	 * 取得用户菜单权限
	 *
	 * @param userId
	 *            用户ID
	 * @param productId
	 *            产品ID
	 */
	public ExecuteResult<List<VmsmenuDTO>> getVmsmenusByUser(Long userId);

	/**
	 * 取得所有菜单
	 *
	 */
	public ExecuteResult<List<VmsmenuDTO>> getAllVmsmenus();

	/**
	 * 密码修改
	 *
	 * @param userId
	 *            用户ID
	 * @param pwd
	 *            新密码
	 * @param oldpwd
	 *            旧密码
	 * @param userId
	 *            操作人id
	 */
	public ExecuteResult<Boolean> passwdEdit(Long userid, String pwd, String oldpwd, Long userId);

	/**
	 * 密码修改验证
	 *
	 * @param userId
	 *            用户ID
	 * @param oldpwd
	 *            旧密码
	 * @param userId
	 *            操作人id
	 */
	public ExecuteResult<Boolean> passwdEditCheck(Long userid, String oldpwd);

	/**
	 * @param memberCode
	 * @param password
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> memberPasswdReset(String memberCode, String password, Long userId);

	/**
	 * 根据用户id生成登录用的ticket
	 *
	 * @param appId
	 * @param loginId
	 * @return
	 */
	public ExecuteResult<String> getUserTicket(String appId, String loginId);

	/**
	 * 外部系统登录
	 *
	 * @param appId
	 * @param loginId
	 * @param saltmd5
	 * @param ticket
	 * @return
	 */
	public ExecuteResult<LoginResDTO> outerLogin(String appId, String loginId);

	public ExecuteResult<UserDTO> queryUserByMemberId(Long memberId);

	/**
	 * @param user
	 */
	public ExecuteResult<Boolean> updateUserName(UserDTO user);

	/**
	 * 密码修改
	 *
	 * @param membercode
	 *            登录id
	 * @param md5pwd
	 *            新密码
	 * @param md5oldpwd
	 *            旧密码
	 */
	public ExecuteResult<Boolean> passwdEditMd5(String membercode, String md5pwd, String md5oldpwd);
	
	/**
	 * 取得所有员工数据byName
	 *
	 * @param companyId
	 *            公司ID
	 * @return 取得所有员工数据byName
	 */
	public ExecuteResult<List<EmployeeDTO>> getEmployeeByName(String name);
}
