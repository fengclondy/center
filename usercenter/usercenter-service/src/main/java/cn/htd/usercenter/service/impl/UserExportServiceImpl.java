package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.common.util.DigestCalculator;
import cn.htd.usercenter.common.util.IdGen;
import cn.htd.usercenter.common.util.Md5Utils;
import cn.htd.usercenter.common.util.SaltEncodingPolicy;
import cn.htd.usercenter.dao.CustomerDAO;
import cn.htd.usercenter.dao.EmployeeDAO;
import cn.htd.usercenter.dao.HTDCompanyDAO;
import cn.htd.usercenter.dao.RoleDAO;
import cn.htd.usercenter.dao.UserDAO;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.FunctionDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.LoginLogDTO;
import cn.htd.usercenter.dto.LoginParamDTO;
import cn.htd.usercenter.dto.LoginResDTO;
import cn.htd.usercenter.dto.MenuDTO;
import cn.htd.usercenter.dto.PermissionDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.dto.VmsmenuDTO;
import cn.htd.usercenter.enums.PermissionEnum;
import cn.htd.usercenter.enums.UserEnums.EmployeeCompanyType;
import cn.htd.usercenter.enums.UserEnums.EmployeeStatus;
import cn.htd.usercenter.service.UserExportService;

@Service("userExportService")
public class UserExportServiceImpl implements UserExportService {

	private final static Logger logger = LoggerFactory.getLogger(UserExportServiceImpl.class);
	@Resource
	private UserDAO userDAO;

	@Resource
	private HTDCompanyDAO htdCompanyDAO;

	@Resource
	private RedisDB ucredisDB;

	@Resource
	private RoleDAO roleDao;

	@Resource
	private EmployeeDAO employeeDAO;

	@Resource
	private CustomerDAO customerDAO;

	@Override
	public ExecuteResult<LoginResDTO> employeeLogin(LoginParamDTO paramDTO) {
		return userLogin(paramDTO, true);
	}

	@Override
	public ExecuteResult<LoginResDTO> customerLogin(LoginParamDTO paramDTO) {
		return userLogin(paramDTO, false);
	}

	private ExecuteResult<LoginResDTO> userLogin(LoginParamDTO paramDTO, boolean isEmployee) {
		ExecuteResult<LoginResDTO> res = new ExecuteResult<LoginResDTO>();
		LoginResDTO loginResDTO = null;

		try {
			UserDTO userDTO = null;
			if (paramDTO != null && paramDTO.getUsername() != null && paramDTO.getPassword() != null) {
				userDTO = userDAO.queryUserByLoginId(paramDTO.getUsername(), isEmployee);
			}

			// 验证用户是否存在
			if (userDTO == null) {
				res.addErrorMessage("用户名或密码不正确！");

			} else {

				// 验证账号是否有效
				if ((null != userDTO.getDeletedFlag() && userDTO.getDeletedFlag() == GlobalConstant.DELETED_FLAG_YES)
						|| (null != userDTO.getValidFlag() && userDTO.getValidFlag() == GlobalConstant.VALID_FLAG_NO)
						|| (null != userDTO.getEmpStatus()
						&& !(EmployeeStatus.PROBATION.getCode().equals(userDTO.getEmpStatus())
						|| EmployeeStatus.REGULAR.getCode().equals(userDTO.getEmpStatus())) ) ) {
					res.addErrorMessage("账号已被冻结");

					// 验证VMS有效性
				} else if (!paramDTO.isVMS() && null != userDTO.getIsVMSInnerUser()
						&& userDTO.getIsVMSInnerUser() == GlobalConstant.IS_VMS_INNER_USER_YES) {
					res.addErrorMessage("无效用户");

				} else {
					// 取得密码的MD5值
					String md5Password = Md5Utils.getMd5(paramDTO.getPassword());
					// 兼容Hybris密码
					if (!md5Password.equalsIgnoreCase(userDTO.getPassword())) {
						String hpass = getHybrisPassWordMD5(userDTO.getLoginId(), paramDTO.getPassword());
						if (hpass.equalsIgnoreCase(userDTO.getPassword())) {
							md5Password = hpass;
						}
					}

					// 验证密码是否正确(不区分大小写)
					if (!md5Password.equalsIgnoreCase(userDTO.getPassword())) {
						// 取得登录失败次数
						int failedLoginCount = userDTO.getFailedLoginCount() != null ? userDTO.getFailedLoginCount()
								: 0;

						// 验证是否超过最大失败次数
						Integer validFlag = null;
						if(isEmployee){
							if (failedLoginCount >= GlobalConstant.MAX_LOGIN_FAILED_COUNT - 1) {
								validFlag = GlobalConstant.VALID_FLAG_NO;
								res.addErrorMessage("用户名或密码不正确（账号已被冻结）");
							} else {
								res.addErrorMessage(MessageFormat.format("用户名或密码不正确（还有{0}次机会）",
										GlobalConstant.MAX_LOGIN_FAILED_COUNT - 1 - failedLoginCount));
							}
							// 增加登录失败次数
							userDAO.addFailedLoginCount(userDTO.getId(), failedLoginCount, validFlag);
						}else{
							res.addErrorMessage("用户名或密码不正确！");
						}



						// 登录成功
					} else {
						loginResDTO = new LoginResDTO();
						// 登录ID
						loginResDTO.setLoginId(userDTO.getLoginId());
						// 用户ID
						loginResDTO.setUid(userDTO.getId());
						// 用户名
						loginResDTO.setUname(userDTO.getName());
						// 昵称
						loginResDTO.setNickname(userDTO.getNickname());
						// 邮件地址
						loginResDTO.setEmail(userDTO.getEmail());
						// 手机号码
						loginResDTO.setMobile(userDTO.getMobile());
						// 头像
						loginResDTO.setAvatar(userDTO.getAvatar());
						// 上次登录时间
						loginResDTO.setLastLoginTime(userDTO.getLastLoginTime());

						// 员工编号
						loginResDTO.setEmpNo(userDTO.getEmpNo());
						// 员工所属公司
						loginResDTO.setEmpCompanyId(userDTO.getEmpCompanyId());
						// 员工所属公司名
						loginResDTO.setEmpCompanyName(userDTO.getEmpCompanyName());
						// 员工所属公司类型
						loginResDTO.setEmpCompanyType(EmployeeCompanyType.getEnumByCode(userDTO.getEmpCompanyType()));
						// 是否客户经理
						loginResDTO.setIsCustomerManager(userDTO.getIsCustomerManager() != null
								&& userDTO.getIsCustomerManager() == GlobalConstant.IS_CUSTOMER_MANAGER_YES);

						// 客户所属公司ID
						loginResDTO.setCusCompanyId(userDTO.getCusCompanyId());

						if (paramDTO.isVMS()) {
							CustomerDTO customerinfo = customerDAO.getCustomerInfo(userDTO.getId());
							loginResDTO.setVmsPermissions(customerinfo.getVmsPermissions());
							loginResDTO.setIsLowFlag(customerinfo.getIsLowFlag());
							loginResDTO.setIsVmsInnerUser(customerinfo.getIsVmsInnerUser());
							loginResDTO.setDefaultContact(customerinfo.getDefaultContact());
						}

						String ticket = IdGen.uuid();
						loginResDTO.setTicket(ticket);
						logger.info("ticket is : " + ticket);

						ucredisDB.setAndExpire(ticket, JSON.toJSONString(loginResDTO), 60 * GlobalConstant.TICKET_TIMEOUT_MINUTES);

						// 更新上次登录时间
						userDAO.updateLastLoginTime(loginResDTO.getUid());

						// 插入登录日志
						try {
							LoginLogDTO logDTO = new LoginLogDTO();
							logDTO.setLoginUserId(userDTO.getId());
							logDTO.setLoginUserName(userDTO.getName());
							logDTO.setLoginProductName(paramDTO.getAppName());
							logDTO.setClientIPAddr(paramDTO.getClientIPAddr());
							userDAO.insertLoginLog(logDTO);

							logger.debug("logId:" + logDTO.getId());
						} catch (Exception e) {
							// 无视登录日志的异常
							logger.error("【插入登录日志】出现异常！", e);
						}
					}
				}
			}

			res.setResult(loginResDTO);
		} catch (Exception e) {
			res.addErrorMessage("用户登录失败！");
			logger.error(e.getMessage());
			logger.error("【用户登录】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<Boolean> logout(String ticket) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			// 清除缓存数据
			ucredisDB.del(ticket);
			res.setResult(true);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("登出失败！({0})", e.getMessage()));
			logger.error("【登出】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<Boolean> activeEmployee(String loginId) {
		return activeUser(loginId, true);
	}

	@Override
	public ExecuteResult<Boolean> activeCustomer(String loginId) {
		return activeUser(loginId, false);
	}

	private ExecuteResult<Boolean> activeUser(String loginId, boolean isEmployee) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;
			if (loginId != null) {
				userDTO = userDAO.queryUserByLoginId(loginId, isEmployee);
			}

			if (userDTO != null) {
				userDAO.emptyFailedLoginCount(userDTO.getId());
				res.setResultMessage("重置登录失败次数成功！");
				res.setResult(true);
			} else {
				res.setResultMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("重置登录失败次数失败！({0})", e.getMessage()));
			logger.error("【重置登录失败次数】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<LoginResDTO> validTicket(String ticket) {
		ExecuteResult<LoginResDTO> res = new ExecuteResult<LoginResDTO>();
		String loginResStr = "";

		LoginResDTO loginResDTO =null;
		try {
			loginResStr = ucredisDB.get(ticket);
			if (!StringUtils.isEmpty(loginResStr)) {
				loginResDTO = JSON.parseObject(loginResStr,LoginResDTO.class);
			}
			if (loginResDTO != null) {
				res.setResult(loginResDTO);
				// 延迟有效时间（秒）
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + GlobalConstant.TICKET_TIMEOUT_MINUTES);
				ucredisDB.setExpire(ticket, cal.getTime());
			} else {
				res.addErrorMessage("无效的Ticket，请重新登录");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证Ticket失败！({0})", e.getMessage()));
			logger.error("【验证Ticket】出现异常！", e);
		}

		return res;
	}

	private static ObjectMapper mapper = new ObjectMapper();

	private static String toJson(Object obj) {
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(obj);
		} catch (Exception e) {
		}
		return jsonString;
	}

	@Override
	public ExecuteResult<List<MenuDTO>> getMenusByUser(Long userId, String productId) {
		ExecuteResult<List<MenuDTO>> res = new ExecuteResult<List<MenuDTO>>();
		try {

			List<MenuDTO> menus = new ArrayList<MenuDTO>();
			res.setResult(menus);

			if (userId != null && productId != null) {
				List<PermissionDTO> permissions = userDAO.queryMenuPermissionByUserId(userId, productId);

				Map<String, MenuDTO> menuMap = new HashMap<String, MenuDTO>();
				for (PermissionDTO permission : permissions) {
					MenuDTO menu = new MenuDTO();
					menu.setName(permission.getName());
					menu.setType(PermissionEnum.getEnumByCode(permission.getType()));
					if (menu.getType() == PermissionEnum.PAGE) {
						menu.setPageUrl(permission.getPageUrl());
					}
					menuMap.put(permission.getPermissionId(), menu);

					if (permission.getLevel() == 0) {
						menus.add(menu);
					} else {
						MenuDTO parentMenu = menuMap.get(permission.getParentId());
						if (parentMenu != null) {
							if (parentMenu.getSubMenus() == null) {
								parentMenu.setSubMenus(new ArrayList<MenuDTO>());
							}
							parentMenu.getSubMenus().add(menu);
						}
					}
				}
			}

			if (menus.size() == 0) {
				res.addErrorMessage("没有菜单权限");
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得用户菜单权限失败！({0})", e.getMessage()));
			logger.error("【取得用户菜单权限】出现异常！", e);
		}

		return res;
	}

	@Override
	public ExecuteResult<List<FunctionDTO>> getPageFunctionsByUser(Long userId, String productId, String permissionId) {
		ExecuteResult<List<FunctionDTO>> res = new ExecuteResult<List<FunctionDTO>>();
		try {
			List<FunctionDTO> functions = null;

			if (userId != null && productId != null && permissionId != null) {
				functions = userDAO.queryPageFunctionPermissionByUserId(userId, productId, permissionId);
			}

			if (functions == null || functions.size() == 0) {
				res.addErrorMessage("没有页面机能权限");
			} else {
				res.setResult(functions);
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得用户页面机能权限失败！({0})", e.getMessage()));
			logger.error("【取得用户页面机能权限】出现异常！", e);
		}

		return res;
	}

	@Override
	public ExecuteResult<List<String>> getInchargeCompanies(Long userId) {
		ExecuteResult<List<String>> res = new ExecuteResult<List<String>>();
		List<String> inchargeCompanies = new ArrayList<String>();

		try {
			UserDTO userDTO = userDAO.queryEmployeeByUserId(userId);
			if (userDTO != null) {
				// 总部人员
				if (EmployeeCompanyType.HEADQUARTERS.getCode().equals(userDTO.getEmpCompanyType())) {
					logger.debug("总部人员：" + userDTO.getEmpInchargeCompanies());
					if (!StringUtils.isEmpty(userDTO.getEmpInchargeCompanies())) {
						String[] companies = userDTO.getEmpInchargeCompanies().split(",");
						// 检索所有分部的下级平台公司
						List<HTDCompanyDTO> subCompanyList = htdCompanyDAO.querySubCompanies(companies);
						for (HTDCompanyDTO companyDTO : subCompanyList) {
							inchargeCompanies.add(companyDTO.getCompanyId());
						}
						for (String branchId : companies) {
							// 分部是"本部"的时候，添加公海会员的平台公司ID"0801"
							if ("F000".equals(branchId)) {
								inchargeCompanies.add("0801");
								break;
							}
						}
					}

					// 分部人员
				} else if (EmployeeCompanyType.BRANCH.getCode().equals(userDTO.getEmpCompanyType())) {
					logger.debug("分部人员：" + userDTO.getEmpInchargeCompanies());
					if (!StringUtils.isEmpty(userDTO.getEmpInchargeCompanies())) {
						String[] companies = userDTO.getEmpInchargeCompanies().split(",");
						for (String companyId : companies) {
							inchargeCompanies.add(companyId);
						}
					}

					// 平台公司人员
				} else {
					logger.debug("平台公司人员");
					inchargeCompanies.add(userDTO.getEmpCompanyId());
				}
			}
			res.setResult(inchargeCompanies);

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得员工负责平台公司失败！({0})", e.getMessage()));
			logger.error("【取得员工负责平台公司】出现异常！", e);
		}

		return res;
	}

	@Override
	public ExecuteResult<Boolean> validProcessPermission(Long userId, String productId, String processUrl) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			if (userId == null || productId == null || processUrl == null) {
				res.setResultMessage("请确认调用参数！");
				res.setResult(false);

			} else {
				// 验证该处理URL是否权限验证对象
				PermissionDTO permissionDTO = userDAO.queryPermissionByUrl(productId, processUrl);
				if (permissionDTO != null) {

					// 验证该用户是否拥有该处理权限
					PermissionDTO userPermissionDTO = userDAO.queryUserPermissionByPermissionId(userId,
							permissionDTO.getProductId(), permissionDTO.getPermissionId());

					if (userPermissionDTO != null) {
						res.setResultMessage("拥有权限，可以操作！");
						res.setResult(true);
					} else {
						res.setResultMessage("没有权限，不可操作！");
						res.setResult(false);
					}
				} else {
					res.setResultMessage("非权限验证对象，可以操作！");
					res.setResult(true);
				}
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证用户操作权限失败！({0})", e.getMessage()));
			logger.error("【验证用户操作权限】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<List<EmployeeDTO>> getEmployeeByCompany(String companyId) {
		ExecuteResult<List<EmployeeDTO>> res = new ExecuteResult<List<EmployeeDTO>>();
		try {
			List<EmployeeDTO> employee = employeeDAO.selectEmployeeByCompanyId(companyId);
			res.setResult(employee);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得指定公司下的所有员工数据失败！({0})", e.getMessage()));
			logger.error("【取得指定公司下的所有员工数据】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<List<HTDCompanyDTO>> getCompanysByParentCompany(String parentCompanyId, String name) {
		ExecuteResult<List<HTDCompanyDTO>> res = new ExecuteResult<List<HTDCompanyDTO>>();
		try {
			List<HTDCompanyDTO> companys = htdCompanyDAO.querySubCompaniesByParentIdAndName(parentCompanyId, name);
			res.setResult(companys);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得指定公司下的下级公司数据失败！({0})", e.getMessage()));
			logger.error("【取得指定公司下的下级公司数据】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#passwdreset(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public ExecuteResult<Boolean> passwdReset(Long userid, String pwd, Long userId) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;
			if (userid != null) {
				userDTO = userDAO.queryUserById(userid);
			}

			if (userDTO != null) {
				userDTO.setPassword(Md5Utils.getMd5(pwd));
				userDTO.setLastUpdateId(userId);
				userDAO.updateUser(userDTO);
				userDAO.updateUserTime(userDTO);

				res.setResultMessage("重置密码成功！");
				res.setResult(true);
			} else {
				res.addErrorMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("重置密码失败！({0})", e.getMessage()));
			logger.error("【重置密码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#queryLastLoginLog(java.lang.
	 * Long)
	 */
	@Override
	public ExecuteResult<LoginLogDTO> queryLastLoginLog(Long userid) {
		ExecuteResult<LoginLogDTO> res = new ExecuteResult<LoginLogDTO>();
		try {
			UserDTO userDTO = null;
			if (userid != null) {
				userDTO = userDAO.queryUserById(userid);
			}

			if (userDTO != null) {
				LoginLogDTO lld = userDAO.queryLastLoginLog(userid);
				res.setResultMessage("查询登录信息成功！");
				res.setResult(lld);

			} else {
				res.setResultMessage("用户不存在！");
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("查询登录日志失败！({0})", e.getMessage()));
			logger.error("【查询登录日志】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<UserDTO> queryUserByUserId(Long userId, boolean isEmployee) {
		ExecuteResult<UserDTO> res = new ExecuteResult<UserDTO>();
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.queryUserByUserId(userId, false);
			if (userDTO != null) {
				res.setResultMessage("取得人员成功！");
				res.setResult(userDTO);
			} else {
				res.setResultMessage("取得人员失败！");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得人员失败！({0})", e.getMessage()));
			logger.error("【取得人员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#getVmsmenusByUser(java.lang.
	 * Long)
	 */
	@Override
	public ExecuteResult<List<VmsmenuDTO>> getVmsmenusByUser(Long userId) {
		ExecuteResult<List<VmsmenuDTO>> res = new ExecuteResult<List<VmsmenuDTO>>();
		try {

			List<VmsmenuDTO> menus = new ArrayList<VmsmenuDTO>();
			res.setResult(menus);

			if (userId != null) {
				List<VmsmenuDTO> vmsmenus = userDAO.queryVmsmenu();

				List<VmsmenuDTO> vmsmenusnewList = new ArrayList<VmsmenuDTO>();

				CustomerDTO crs = customerDAO.getCustomerInfo(userId);
				String[] vplist = null;
				if (crs != null && crs.getVmsPermissions() != null) {
					String vp = crs.getVmsPermissions();
					vplist = vp.split(",");
				}
				for (VmsmenuDTO vmsmenu : vmsmenus) {
					if (vmsmenu.getLevel() == 0) {
						vmsmenusnewList.add(vmsmenu);
					}
					for (String idstr : vplist) {
						if (idstr != null && !idstr.trim().equals("")) {
							if (vmsmenu.getId().equals(idstr)) {
								vmsmenusnewList.add(vmsmenu);
							}
						}
					}
				}

				Map<String, VmsmenuDTO> menuMap = new HashMap<String, VmsmenuDTO>();
				for (VmsmenuDTO permission : vmsmenusnewList) {
					menuMap.put(permission.getId(), permission);

					if (permission.getLevel() == 0) {
						menus.add(permission);
					} else {
						VmsmenuDTO parentMenu = menuMap.get(permission.getParentId());
						if (parentMenu != null) {
							if (parentMenu.getSubMenus() == null) {
								parentMenu.setSubMenus(new ArrayList<VmsmenuDTO>());
							}
							parentMenu.getSubMenus().add(permission);
						}
					}
				}
			}

			if (menus.size() == 0) {
				res.addErrorMessage("没有VMS菜单权限");
			} else {
				res.setResultMessage("VMS菜单权限取得成功");
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得用户VMS菜单权限失败！({0})", e.getMessage()));
			logger.error("【取得用户VMS菜单权限】出现异常！", e);
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.UserExportService#getAllVmsmenus()
	 */
	@Override
	public ExecuteResult<List<VmsmenuDTO>> getAllVmsmenus() {
		ExecuteResult<List<VmsmenuDTO>> res = new ExecuteResult<List<VmsmenuDTO>>();
		try {

			List<VmsmenuDTO> menus = new ArrayList<VmsmenuDTO>();
			res.setResult(menus);

			List<VmsmenuDTO> vmsmenusnewList = userDAO.queryVmsmenu();

			Map<String, VmsmenuDTO> menuMap = new HashMap<String, VmsmenuDTO>();
			for (VmsmenuDTO permission : vmsmenusnewList) {
				menuMap.put(permission.getId(), permission);

				if (permission.getLevel() == 0) {
					menus.add(permission);
				} else {
					VmsmenuDTO parentMenu = menuMap.get(permission.getParentId());
					if (parentMenu != null) {
						if (parentMenu.getSubMenus() == null) {
							parentMenu.setSubMenus(new ArrayList<VmsmenuDTO>());
						}
						parentMenu.getSubMenus().add(permission);
					}
				}
			}

			if (menus.size() == 0) {
				res.addErrorMessage("没有VMS菜单权限");
			} else {
				res.setResultMessage("VMS菜单权限取得成功");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得VMS菜单失败！({0})", e.getMessage()));
			logger.error("【取得VMS菜单】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#passwdEdit(java.lang.Long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public ExecuteResult<Boolean> passwdEdit(Long userid, String pwd, String oldpwd, Long userId) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;
			if (userid != null) {
				userDTO = userDAO.queryUserById(userid);
			}

			if (userDTO != null) {
				String pwdStr = Md5Utils.getMd5(pwd);
				String oldpwdStr = Md5Utils.getMd5(oldpwd);
				// 兼容Hybris密码
				if (!oldpwdStr.equalsIgnoreCase(userDTO.getPassword())) {
					String hpass = getHybrisPassWordMD5(userDTO.getLoginId(), oldpwd);
					if (hpass.equalsIgnoreCase(userDTO.getPassword())) {
						oldpwdStr = hpass;
					}
				}

				if (!userDTO.getPassword().equalsIgnoreCase(oldpwdStr)) {
					res.addErrorMessage("原密码错误，请重新输入");
					res.setResult(false);
				} else {
					if (pwdStr.equalsIgnoreCase(userDTO.getPassword())) {
						res.addErrorMessage("新密码不能与原密码相同！");
						res.setResult(false);
					} else {
						userDTO.setPassword(Md5Utils.getMd5(pwd));
						userDTO.setLastUpdateId(userId);
						userDAO.updateUser(userDTO);
						userDAO.updateUserTime(userDTO);

						res.setResultMessage("修改密码成功！");
						res.setResult(true);
					}
				}
			} else {
				res.setResultMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("修改密码失败！({0})", e.getMessage()));
			logger.error("【修改密码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#memberPasswdReset(java.lang.
	 * String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ExecuteResult<Boolean> memberPasswdReset(String memberCode, String pwd, Long userId) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;
			if (memberCode != null && !memberCode.equals("")) {
				userDTO = userDAO.queryUserByLoginId(memberCode, false);
			}

			if (userDTO != null) {
				userDTO.setPassword(Md5Utils.getMd5(pwd));
				userDTO.setLastUpdateId(userId);
				userDAO.updateUser(userDTO);
				userDAO.updateUserTime(userDTO);
				res.setResultMessage("重置密码成功！");
				res.setResult(true);
			} else {
				res.addErrorMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("重置密码失败！({0})", e.getMessage()));
			logger.error("【重置密码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.UserExportService#getUserTicket(java.lang.
	 * String)
	 */
	@Override
	public ExecuteResult<String> getUserTicket(String appId, String loginId) {
		ExecuteResult<String> res = new ExecuteResult<String>();
		String ticket = IdGen.uuid();
		try {
			if (ucredisDB.exists("outterloginid" + loginId)) {
				ticket = ucredisDB.get("outterloginid" + loginId);
				res.setResult(ticket);
				res.setResultMessage("ok");
			} else {
				logger.info("appId is :" + appId + " and oginId is: " + loginId + " and ticket is : " + ticket);
				ucredisDB.setAndExpire("outterloginid" + loginId, ticket, 3 * 60);
				res.setResult(ticket);
				res.setResultMessage("ok");
			}

		} catch (Exception e) {
			res.addErrorMessage("error for getUserTicket");
			logger.error("error for getUserTicket appId is :" + appId + " and loginId is: " + loginId
					+ " and ticket is : " + ticket);
		}

		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#outerLogin(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public ExecuteResult<LoginResDTO> outerLogin(String appId, String loginId) {

		ExecuteResult<LoginResDTO> res = new ExecuteResult<LoginResDTO>();
		LoginResDTO loginResDTO = null;
		LoginParamDTO paramDTO = new LoginParamDTO();
		Boolean isEmployee = false;
		paramDTO.setUsername(loginId);
		paramDTO.setVMS(true);
		paramDTO.setAppName(appId);

		try {
			UserDTO userDTO = null;
			if (paramDTO != null && paramDTO.getUsername() != null) {
				userDTO = userDAO.queryUserByLoginId(paramDTO.getUsername(), isEmployee);
			}

			// 验证用户是否存在
			if (userDTO == null) {
				res.addErrorMessage("用户名或密码不正确");

			} else {

				// 验证账号是否有效
				if ((null != userDTO.getDeletedFlag() && userDTO.getDeletedFlag() == GlobalConstant.DELETED_FLAG_YES)
						|| (null != userDTO.getValidFlag() && userDTO.getValidFlag() == GlobalConstant.VALID_FLAG_NO)
						|| (null != userDTO.getEmpStatus()
						&& EmployeeStatus.INVALID.getCode().equals(userDTO.getEmpStatus()))) {
					res.addErrorMessage("账号已被冻结");

					// 验证VMS有效性
				} else if (!paramDTO.isVMS() && null != userDTO.getIsVMSInnerUser()
						&& userDTO.getIsVMSInnerUser() == GlobalConstant.IS_VMS_INNER_USER_YES) {
					res.addErrorMessage("无效用户");

				} else {
					loginResDTO = new LoginResDTO();
					loginResDTO.setLoginId(paramDTO.getUsername());
					// 用户ID
					loginResDTO.setUid(userDTO.getId());
					// 用户名
					loginResDTO.setUname(userDTO.getName());
					// 昵称
					loginResDTO.setNickname(userDTO.getNickname());
					// 邮件地址
					loginResDTO.setEmail(userDTO.getEmail());
					// 手机号码
					loginResDTO.setMobile(userDTO.getMobile());
					// 头像
					loginResDTO.setAvatar(userDTO.getAvatar());
					// 上次登录时间
					loginResDTO.setLastLoginTime(userDTO.getLastLoginTime());

					// 员工编号
					loginResDTO.setEmpNo(userDTO.getEmpNo());
					// 员工所属公司
					loginResDTO.setEmpCompanyId(userDTO.getEmpCompanyId());
					// 员工所属公司名
					loginResDTO.setEmpCompanyName(userDTO.getEmpCompanyName());
					// 员工所属公司类型
					loginResDTO.setEmpCompanyType(EmployeeCompanyType.getEnumByCode(userDTO.getEmpCompanyType()));
					// 是否客户经理
					loginResDTO.setIsCustomerManager(userDTO.getIsCustomerManager() != null
							&& userDTO.getIsCustomerManager() == GlobalConstant.IS_CUSTOMER_MANAGER_YES);

					// 客户所属公司ID
					loginResDTO.setCusCompanyId(userDTO.getCusCompanyId());

					if (paramDTO.isVMS()) {
						CustomerDTO customerinfo = customerDAO.getCustomerInfo(userDTO.getId());
						loginResDTO.setVmsPermissions(customerinfo.getVmsPermissions());
						loginResDTO.setIsLowFlag(customerinfo.getIsLowFlag());
						loginResDTO.setIsVmsInnerUser(customerinfo.getIsVmsInnerUser());
						loginResDTO.setDefaultContact(customerinfo.getDefaultContact());
					}

					String ticket = IdGen.uuid();
					loginResDTO.setTicket(ticket);
					logger.info("ticket is : " + ticket);
					ucredisDB.setAndExpire(ticket, JSON.toJSONString(loginResDTO), 60 * GlobalConstant.TICKET_TIMEOUT_MINUTES);

					// 更新上次登录时间
					userDAO.updateLastLoginTime(loginResDTO.getUid());

					// 插入登录日志
					try {
						LoginLogDTO logDTO = new LoginLogDTO();
						logDTO.setLoginUserId(userDTO.getId());
						logDTO.setLoginUserName(userDTO.getName());
						logDTO.setLoginProductName(paramDTO.getAppName());
						logDTO.setClientIPAddr(paramDTO.getClientIPAddr());
						userDAO.insertLoginLog(logDTO);

						logger.debug("logId:" + logDTO.getId());
					} catch (Exception e) {
						// 无视登录日志的异常
						logger.error("【插入登录日志】出现异常！", e);
					}
				}
			}

			res.setResult(loginResDTO);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("用户登录失败！({0})", e.getMessage()));
			logger.error("【用户登录】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#queryUserByLoginId(java.lang.
	 * String)
	 */
	@Override
	public ExecuteResult<UserDTO> queryUserByLoginId(String loginId) {
		ExecuteResult<UserDTO> res = new ExecuteResult<UserDTO>();
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.queryUserByLoginId(loginId, null);
			if (userDTO != null) {
				res.setResultMessage("取得人员成功！");
				res.setResult(userDTO);
			} else {
				res.setResultMessage("取得人员失败！");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得人员失败！({0})", e.getMessage()));
			logger.error("【取得人员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#passwdEditCheck(java.lang.
	 * Long, java.lang.String)
	 */
	@Override
	public ExecuteResult<Boolean> passwdEditCheck(Long userid, String oldpwd) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;
			if (userid != null) {
				userDTO = userDAO.queryUserById(userid);
			}

			if (userDTO != null) {
				String oldpwdStr = Md5Utils.getMd5(oldpwd);

				// 兼容Hybris密码
				if (!oldpwdStr.equalsIgnoreCase(userDTO.getPassword())) {
					String hpass = getHybrisPassWordMD5(userDTO.getLoginId(), oldpwd);
					if (hpass.equalsIgnoreCase(userDTO.getPassword())) {
						oldpwdStr = hpass;
					}
				}

				if (userDTO.getPassword().equalsIgnoreCase(oldpwdStr)) {
					res.setResultMessage("验证密码成功！");
					res.setResult(true);
				} else {
					res.addErrorMessage("原密码错误，请重新输入");
					res.setResult(false);
				}
			} else {
				res.addErrorMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证修改密码失败！({0})", e.getMessage()));
			logger.error("【修改密码】出现异常！", e);
		}
		return res;
	}

	public static void main(String[] args) {
		String pass = getHybrisPassWordMD5("htd1046000", "123456");
		System.out.println(pass);
		// System.out.println(DigestCalculator.getInstance("MD5").calculateDigest(pass));
	}

	/**
	 *
	 */
	private static String getHybrisPassWordMD5(String loginId, String password) {
		String pass = new SaltEncodingPolicy().saltify(loginId, password);
		// System.out.println(pass);
		return DigestCalculator.getInstance("MD5").calculateDigest(pass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.UserExportService#queryUserByMemberId(java.lang
	 * .Long)
	 */
	@Override
	public ExecuteResult<UserDTO> queryUserByMemberId(Long memberId) {
		ExecuteResult<UserDTO> res = new ExecuteResult<UserDTO>();
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.queryUserByMemberId(memberId);
			if (userDTO != null) {
				res.setResultMessage("取得人员成功！");
				res.setResult(userDTO);
			} else {
				res.setResultMessage("取得人员失败！");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得人员失败！({0})", e.getMessage()));
			logger.error("【取得人员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.UserExportService#updateUserName(cn.htd.
	 * usercenter.dto.UserDTO)
	 */
	@Override
	public ExecuteResult<Boolean> updateUserName(UserDTO user) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (user != null && user.getId() != null && user.getId().intValue() > 0) {
				UserDTO dto = userDAO.queryUserById(user.getId());
				if (dto != null) {
					userDAO.updateUserName(user);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("更新成功！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("未找到用户信息！");
				}

			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("未找到用户信息！");
			}
		} catch (Exception e) {
			logger.error("UserExportService----->updateUserName=" + e);
		}
		return rs;

	}

	/* (non-Javadoc)
	 * @see cn.htd.usercenter.service.UserExportService#passwdEditNomd5(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public ExecuteResult<Boolean> passwdEditMd5(String membercode, String pwd, String oldpwd) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		try {
			UserDTO userDTO = null;

			if (StringUtils.isNotBlank(membercode)) {
				userDTO = queryUserByLoginId(membercode).getResult();
			}

			if (userDTO != null) {
				String pwdStr = pwd;
				String oldpwdStr = oldpwd;

				if (!userDTO.getPassword().equalsIgnoreCase(oldpwdStr)) {
					res.addErrorMessage("原密码错误，请重新输入");
					res.setResult(false);
				} else {
					if (pwdStr.equalsIgnoreCase(userDTO.getPassword())) {
						res.addErrorMessage("新密码不能与原密码相同！");
						res.setResult(false);
					} else {
						userDTO.setPassword(pwd);
						userDTO.setLastUpdateId(0l);
						userDAO.updateUser(userDTO);
						userDAO.updateUserTime(userDTO);

						res.setResultMessage("修改密码成功！");
						res.setResult(true);
					}
				}
			} else {
				res.setResultMessage("用户不存在！");
				res.setResult(false);
			}

		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("修改密码失败！({0})", e.getMessage()));
			logger.error("【修改密码】出现异常！", e);
		}
		return res;
	}

	@Override
	public ExecuteResult<List<EmployeeDTO>> getEmployeeByName(String name) {
		ExecuteResult<List<EmployeeDTO>> res = new ExecuteResult<List<EmployeeDTO>>();
		try {
			List<EmployeeDTO> employee = employeeDAO.getEmployeeByName(name);
			res.setResult(employee);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("取得所有员工数据byName数据失败！({0})", e.getMessage()));
			logger.error("【取得所有员工数据byName】出现异常！", e);
		}
		return res;
	}
}
