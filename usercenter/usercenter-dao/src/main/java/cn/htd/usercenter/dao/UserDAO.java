package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.FunctionDTO;
import cn.htd.usercenter.dto.LoginLogDTO;
import cn.htd.usercenter.dto.PermissionDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.dto.VmsmenuDTO;

public interface UserDAO extends BaseDAO<UserDTO> {

	public UserDTO queryUserByLoginId(@Param("loginId") String loginId, @Param("isEmployee") Boolean isEmployee);

	public int updateLastLoginTime(@Param("uid") Long uid);

	public int addFailedLoginCount(@Param("uid") Long uid, @Param("failedLoginCount") int failedLoginCount,
			@Param("validFlag") Integer validFlag);

	public int emptyFailedLoginCount(@Param("uid") Long uid);

	public List<PermissionDTO> queryMenuPermissionByUserId(@Param("userId") Long userId,
			@Param("productId") String productId);

	public List<FunctionDTO> queryPageFunctionPermissionByUserId(@Param("userId") Long userId,
			@Param("productId") String productId, @Param("permissionId") String permissionId);

	public PermissionDTO queryPermissionByUrl(@Param("productId") String productId,
			@Param("processUrl") String processUrl);

	public PermissionDTO queryUserPermissionByPermissionId(@Param("userId") Long userId,
			@Param("productId") String productId, @Param("permissionId") String permissionId);

	public int insertLoginLog(@Param("dto") LoginLogDTO dto);

	public EmployeeDTO queryEmployeeByEmpNo(@Param("empNo") String empNo);

	public int insertUser(@Param("dto") UserDTO dto);

	public int updateUser(@Param("dto") UserDTO dto);

	public int updateUserMobile(@Param("dto") UserDTO dto);

	public int insertEmployee(@Param("dto") EmployeeDTO dto);

	public int updateEmployee(@Param("dto") EmployeeDTO dto);

	public int updateEmployeeSuperior();

	public UserDTO queryEmployeeByUserId(@Param("userId") Long userId);

	public int updateSubstationMan(@Param("userid") Long userid, @Param("userId") Long userId);

	public UserDTO queryUserById(@Param("userId") Long userId);

	/**
	 * @param id
	 * @param valid
	 * @param userId
	 */
	public void updateUserValidFlagByLoginId(@Param("id") Long id, @Param("valid") boolean valid,
			@Param("userId") Long userId);

	/**
	 * @param userid
	 * @return
	 */
	public LoginLogDTO queryLastLoginLog(@Param("userid") Long userid);

	public UserDTO queryUserByUserId(@Param("userId") Long userId, @Param("isEmployee") Boolean isEmployee);

	/**
	 * @return
	 */
	public List<VmsmenuDTO> queryVmsmenu();

	/**
	 * @param mobile
	 * @return
	 */
	public UserDTO queryUserByMobile(@Param("mobile") String mobile);

	public int updateUserAvatar(@Param("dto") UserDTO dto);

	public UserDTO queryUserByMemberId(@Param("memberId") Long memberId);

	/**
	 * @param user
	 */
	public void updateUserName(@Param("dto") UserDTO user);
}
