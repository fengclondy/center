package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.common.util.Md5Utils;
import cn.htd.usercenter.dao.CustomerDAO;
import cn.htd.usercenter.dao.UserDAO;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Resource
	private CustomerDAO customerDAO;
	@Resource
	private UserDAO userDAO;

	@Override
	public ExecuteResult<Boolean> addCustomer(CustomerDTO customerDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null) {
				String mobile = customerDTO.getMobile();
				if (mobile == null || mobile.trim().equals("")) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("请输入手机号！");
				} else {
					List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, -1l);
					if (customerList != null && customerList.size() > 0) {// 判断手机号重复
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage("该手机号已经存在，请重新输入！");
					} else {
						if (customerDTO.getDefaultContact().intValue() == 1) {
							customerList = customerDAO.isRepeatdc(-1l, customerDTO.getCompanyId());
							if (customerList != null) {
								for (CustomerDTO customerDTO2 : customerList) {
									customerDTO2.setDefaultContact(0);
									customerDAO.updateByUid(customerDTO2);
								}
							}
						}
						UserDTO dto = new UserDTO();
						dto.setName(customerDTO.getName());
						String VMSMemberCode = genVMSMemberCode(customerDAO.genVMSMemberCode());
						if (customerDTO.getLoginId() == null || customerDTO.getLoginId().equals("")) {
							customerDTO.setLoginId(VMSMemberCode);
						} else {
							dto.setLoginId(customerDTO.getLoginId());
						}
						dto.setMobile(customerDTO.getMobile());
						dto.setPassword(Md5Utils.getMd5(customerDTO.getPassword()));
						dto.setEmail("");
						dto.setCreateId(userId);
						dto.setLastUpdateId(userId);
						userDAO.insertUser(dto);
						// 添加
						customerDTO.setUserId(dto.getId());
						customerDTO.setCreatedId(userId);
						customerDTO.setDeletedFlag(false);
						customerDTO.setLastUpdatedId(userId);
						customerDAO.addCustomer(customerDTO);
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
						rs.setResultMessage("保存成功！");

					}
				}
			}
		} catch (Exception e) {
			logger.error("CustomerExportServiceImpl----->addSubstationMan=" + e);
		}
		return rs;
	}

	/**
	 * 会员编码
	 * 
	 * @param vmsMemberCode
	 * @return
	 */
	private String genVMSMemberCode(String vmsMemberCode) {
		String tmpC = GlobalConstant.HTD2;
		String tmpCODE = "00000000" + vmsMemberCode;
		return tmpC.concat(tmpCODE.substring(tmpCODE.length() - 7, tmpCODE.length()));
	}

	/**
	 * 供应商编码
	 * 
	 * @param vmsMemberCode
	 * @return
	 */
	private String genVMSPTMemberCode(String vmsMemberCode) {
		String tmpC = GlobalConstant.HTD3;
		String tmpCODE = "00000000" + vmsMemberCode;
		return tmpC.concat(tmpCODE.substring(tmpCODE.length() - 7, tmpCODE.length()));
	}

	/**
	 * 外部供应商编码
	 * 
	 * @param vmsMemberCode
	 * @return
	 */
	private String genVMSOUTMemberCode(String vmsMemberCode) {
		String tmpC = GlobalConstant.HTD4;
		String tmpCODE = "00000000" + vmsMemberCode;
		return tmpC.concat(tmpCODE.substring(tmpCODE.length() - 7, tmpCODE.length()));
	}

	@Override
	public ExecuteResult<Boolean> isRepeatMobile(String mobile) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {

			if (!("").equals(mobile)) {
				List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, -1l);
				if (customerList.size() > 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该手机号已经存在，请重新输入！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("该手机号可以使用！");
				}
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->isRepeatLoginId=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#selectCustomer(java.lang.
	 * String, int, int, long)
	 */
	@Override
	public ExecuteResult<DataGrid<CustomerDTO>> selectCustomerList(String name, int page, int rows, long companyId) {
		ExecuteResult<DataGrid<CustomerDTO>> rs = new ExecuteResult<DataGrid<CustomerDTO>>();
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		try {
			DataGrid<CustomerDTO> dg = new DataGrid<CustomerDTO>();
			List<CustomerDTO> customerList = customerDAO.selectList(name, companyId, pager);
			long count = customerDAO.selectAllCount(name, companyId);
			try {
				if (customerList != null) {
					dg.setRows(customerList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.addErrorMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("CustomerExportServiceImpl----->selectAllMan=" + e);
		}
		return rs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#editCustomer(cn.htd.usercenter.
	 * dto.CustomerDTO, long, long)
	 */
	@Override
	public ExecuteResult<Boolean> editCustomer(CustomerDTO customerDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null && customerDTO.getUserId() != null && customerDTO.getUserId().intValue() > 0) {
				String mobile = customerDTO.getMobile();
				if (mobile == null || mobile.trim().equals("")) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("请输入手机号！");
				} else {
					List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, customerDTO.getUserId());
					if (customerList.size() > 0) {// 判断手机号重复
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage("该手机号已经存在，请重新输入！");
					} else {
						if (customerDTO.getDefaultContact().intValue() == 1) {
							customerList = customerDAO.isRepeatdc(customerDTO.getUserId(), customerDTO.getCompanyId());
							if (customerList != null) {
								for (CustomerDTO customerDTO2 : customerList) {
									customerDTO2.setDefaultContact(0);
									customerDAO.updateByUid(customerDTO2);
								}
							}
						}
						CustomerDTO oldcustomerDTO = customerDAO.getCustomerInfo(customerDTO.getUserId());
						if (oldcustomerDTO != null) {
							UserDTO dto = userDAO.queryUserByUserId(oldcustomerDTO.getUserId(), false);
							dto.setId(oldcustomerDTO.getUserId());
							dto.setName(customerDTO.getName());
							dto.setMobile(customerDTO.getMobile());
							dto.setLoginId(dto.getLoginId());
							// 判断是否更改密码
							if (customerDTO.getPassword() == null || ("").equals(customerDTO.getPassword())) {
								dto.setPassword(dto.getPassword());
							} else {
								dto.setPassword(Md5Utils.getMd5(customerDTO.getPassword()));
							}
							dto.setEmail("");
							// dto.setCreateId(userId);
							dto.setLastUpdateId(userId);
							userDAO.updateUser(dto);

							// 更新员工
							customerDTO.setUserId(customerDTO.getUserId());
							customerDTO.setLastUpdatedId(userId);
							customerDAO.updateByUid(customerDTO);
							rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
							rs.setResultMessage("更新成功！");
						} else {
							rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
							rs.addErrorMessage("未找到客户信息！");
						}

					}
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("未找到客户信息！");
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->editSubstationMan=" + e);
		}
		return rs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#deleteCustomer(long, long)
	 */
	@Override
	public ExecuteResult<Boolean> deleteCustomer(long userid, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (userId == userid) {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("删除人员失败，不能删除当前用户！");
			}
			customerDAO.deleteCustomer(userid, userId);
			userDAO.updateSubstationMan(userid, userId);
			rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
			rs.setResultMessage("删除成功！");
		} catch (Exception e) {
			logger.error("customerServiceImpl----->deleteCustomer=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#getCustomerInfo(long)
	 */
	@Override
	public CustomerDTO getCustomerInfo(long userId) {
		CustomerDTO cd = customerDAO.getCustomerInfo(userId);
		UserDTO dto = userDAO.queryUserByUserId(userId, false);
		cd.setName(dto.getName());
		cd.setMobile(dto.getMobile());
		cd.setLoginId(dto.getLoginId());
		return cd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#updateCustomerPerm(cn.htd.
	 * usercenter.dto.CustomerDTO, long, long)
	 */
	@Override
	public ExecuteResult<Boolean> updateCustomerPerm(CustomerDTO customerDTO, long userid, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null) {
				CustomerDTO oldcustomerDTO = customerDAO.getCustomerInfo(userid);
				if (oldcustomerDTO != null) {
					// 更新员工
					customerDTO.setUserId(userid);
					customerDTO.setLastUpdatedId(userId);
					customerDAO.updatePermByUid(customerDTO);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("更新成功！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("未找到客户信息！");
				}
			}
		} catch (Exception e) {
			logger.error("CustomerServiceImpl----->updateCustomerPerm=" + e);
		}
		return rs;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#validCustomer(java.lang.String)
	 */
	@Override
	public ExecuteResult<Boolean> validCustomer(String memberCode) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.queryUserByLoginId(memberCode, false);
			if (userDTO != null) {
				res.setResultMessage("验证会员成功！");
				res.setResult(true);
			} else {
				res.addErrorMessage("该账户不存在，请重新输入！");
				res.setResult(false);
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证会员失败！({0})", e.getMessage()));
			logger.error("【验证会员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#editValidCustomer(java.lang.
	 * String, boolean)
	 */
	@Override
	public ExecuteResult<Boolean> editValidCustomer(String memberCode, boolean valid, Long userId) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		UserDTO userDTO = null;
		try {
			userDTO = userDAO.queryUserByLoginId(memberCode, false);
			if (userDTO != null) {
				userDAO.updateUserValidFlagByLoginId(userDTO.getId(), valid, userId);
				res.setResultMessage("验证会员成功！");
				res.setResult(true);
			} else {
				res.addErrorMessage("验证会员失败！");
				res.setResult(false);
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("修改会员有效性失败！({0})", e.getMessage()));
			logger.error("【修改会员有效性】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#addOUTSeller(cn.htd.usercenter.
	 * dto.CustomerDTO, long)
	 */
	@Override
	public ExecuteResult<Boolean> addOUTSeller(CustomerDTO customerDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null) {
				String mobile = customerDTO.getMobile();
				if (mobile == null || mobile.trim().equals("")) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("请输入手机号！");
				} else {
//					List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, -1l);
//					if (customerList.size() > 0) {// 判断手机号重复
//						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
//						rs.addErrorMessage("该手机号已经存在，请重新输入！");
//					} else {
						UserDTO dto = new UserDTO();
						dto.setName(customerDTO.getName());
						String VMSMemberCode = genVMSOUTMemberCode(customerDAO.genVMSOUTMemberCode());
						if (customerDTO.getLoginId() == null || customerDTO.getLoginId().equals("")) {
							customerDTO.setLoginId(VMSMemberCode);
						} else {
							dto.setLoginId(customerDTO.getLoginId());
						}
						dto.setMobile(customerDTO.getMobile());
						dto.setPassword(Md5Utils.getMd5(customerDTO.getPassword()));
						dto.setEmail("");
						dto.setCreateId(userId);
						dto.setLastUpdateId(userId);
						userDAO.insertUser(dto);
						// 添加
						customerDTO.setUserId(dto.getId());
						customerDTO.setCreatedId(userId);
						customerDTO.setDeletedFlag(false);
						customerDTO.setLastUpdatedId(userId);
						customerDAO.addCustomer(customerDTO);
						rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
						rs.setResultMessage("保存成功！");
					}
//				}
			}
		} catch (Exception e) {
			logger.error("CustomerExportServiceImpl----->addOUTSeller=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#addSeller(cn.htd.usercenter.dto
	 * .CustomerDTO, long)
	 */
	@Override
	public ExecuteResult<Boolean> addSeller(CustomerDTO customerDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null) {
				String mobile = customerDTO.getMobile();
				if (mobile == null || mobile.trim().equals("")) {
					// rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					// rs.addErrorMessage("请输入手机号！");
					customerDTO.setMobile("");
					customerDTO.setPassword(customerDTO.getLoginId());
				}
				// 供应商不用手机号唯一性判断
				// List<CustomerDTO> customerList =
				// customerDAO.isRepeatMobile(mobile, -1l);
				// if (customerList.size() > 0) {// 判断手机号重复
				// rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				// rs.addErrorMessage("该手机号已经存在，请重新输入！");
				// } else {
				UserDTO dto = new UserDTO();
				dto.setName(customerDTO.getName());
				String VMSMemberCode = genVMSPTMemberCode(customerDAO.genVMSPTMemberCode());
				if (customerDTO.getLoginId() == null || customerDTO.getLoginId().equals("")) {
					customerDTO.setLoginId(VMSMemberCode);
				} else {
					dto.setLoginId(customerDTO.getLoginId());
				}
				dto.setMobile(customerDTO.getMobile());
				dto.setPassword(Md5Utils.getMd5(customerDTO.getPassword()));
				dto.setEmail("");
				dto.setCreateId(userId);
				dto.setLastUpdateId(userId);
				userDAO.insertUser(dto);
				// 添加
				customerDTO.setUserId(dto.getId());
				customerDTO.setCreatedId(userId);
				customerDTO.setDeletedFlag(false);
				customerDTO.setLastUpdatedId(userId);
				customerDAO.addCustomer(customerDTO);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("保存成功！");

				// }

			}
		} catch (Exception e) {
			logger.error("CustomerExportServiceImpl----->addSeller=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#validVmsCustomer(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public ExecuteResult<String> validVmsCustomer(String memberCode, String memberName) {
		ExecuteResult<String> res = new ExecuteResult<String>();
		UserDTO userDTO = null;
		try {
			if (memberCode == null || memberName == null) {
				res.addErrorMessage("参数错误！");
				res.setResult("fail");
			} else {
				userDTO = userDAO.queryUserByLoginId(memberCode, false);
				if (userDTO != null) {
					if (userDTO.getName() != null && userDTO.getName().equals(memberName)) {
						res.setResultMessage("验证会员成功！");
						res.setResult(userDTO.getMobile() + "," + userDTO.getId());
					} else {
						res.addErrorMessage("公司名称错误，请重新输入！");
						res.setResult("fail");
					}
				} else {
					res.addErrorMessage("该账户不存在，请重新输入！");
					res.setResult("fail");
				}
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证会员失败！({0})", e.getMessage()));
			logger.error("【验证会员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#getOUTSellerCode()
	 */
	@Override
	public ExecuteResult<String> genOUTSellerCode() {
		ExecuteResult<String> res = new ExecuteResult<String>();
		try {
			String VMSMemberCode = genVMSOUTMemberCode(customerDAO.genVMSOUTMemberCode());
			res.setResultMessage("生成外部供应商编码成功！");
			res.setResult(VMSMemberCode);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("生成外部供应商编码失败", e.getMessage()));
			logger.error("【生成外部供应商编码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#getMemberCode()
	 */
	@Override
	public ExecuteResult<String> genMemberCode() {
		ExecuteResult<String> res = new ExecuteResult<String>();
		try {
			String VMSMemberCode = genVMSMemberCode(customerDAO.genVMSMemberCode());
			res.setResultMessage("生成会员编码！");
			res.setResult(VMSMemberCode);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("生成会员编码失败", e.getMessage()));
			logger.error("【生成会员编码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#getSellerCode()
	 */
	@Override
	public ExecuteResult<String> genSellerCode() {
		ExecuteResult<String> res = new ExecuteResult<String>();
		try {
			String VMSMemberCode = genVMSPTMemberCode(customerDAO.genVMSPTMemberCode());
			res.setResultMessage("生成供应商编码！");
			res.setResult(VMSMemberCode);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("生成供应商编码失败", e.getMessage()));
			logger.error("【生成供应商编码】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#validCustomerMobile(java.lang.
	 * String)
	 */
	@Override
	public ExecuteResult<Long> validCustomerMobile(String mobile) {
		ExecuteResult<Long> res = new ExecuteResult<Long>();
		UserDTO userDTO = null;
		try {
			if (mobile == null || mobile.trim().equals("")) {
				res.addErrorMessage("手机号不能为空，请重新输入！");
				return res;
			}
			userDTO = userDAO.queryUserByMobile(mobile);
			if (userDTO != null) {
				res.setResultMessage("验证会员成功！");
				res.setResult(userDTO.getId());
			} else {
				res.addErrorMessage("该账户不存在，请重新输入！");
			}
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("验证会员失败！({0})", e.getMessage()));
			logger.error("【验证会员】出现异常！", e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.CustomerService#isRepeatMobileByUserId(java.
	 * lang.String, long)
	 */
	@Override
	public ExecuteResult<Boolean> isRepeatMobileByUserId(String mobile, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {

			if (!("").equals(mobile)) {
				List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, userId);
				if (customerList.size() > 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该手机号已经存在，请重新输入！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("该手机号可以使用！");
				}
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->isRepeatLoginId=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#editCustomerMobile(cn.htd.
	 * usercenter.dto.CustomerDTO, long)
	 */
	@Override
	public ExecuteResult<Boolean> editCustomerMobile(CustomerDTO customerDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (customerDTO != null && customerDTO.getUserId() != null && customerDTO.getUserId().intValue() > 0) {
				String mobile = customerDTO.getMobile();
				if (mobile == null || mobile.trim().equals("")) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("请输入手机号！");
				} else {
					List<CustomerDTO> customerList = customerDAO.isRepeatMobile(mobile, customerDTO.getUserId());
					if (customerList.size() > 0) {// 判断手机号重复
						rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
						rs.addErrorMessage("该手机号已经存在，请重新输入！");
					} else {
						CustomerDTO oldcustomerDTO = customerDAO.getCustomerInfo(customerDTO.getUserId());
						if (oldcustomerDTO != null) {
							UserDTO dto = userDAO.queryUserByUserId(oldcustomerDTO.getUserId(), false);
							dto.setId(oldcustomerDTO.getUserId());
							dto.setName(customerDTO.getName());
							dto.setMobile(customerDTO.getMobile());
							dto.setLoginId(dto.getLoginId());
							dto.setLastUpdateId(userId);
							userDAO.updateUserMobile(dto);
							rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
							rs.setResultMessage("更新成功！");
						} else {
							rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
							rs.addErrorMessage("未找到客户信息！");
						}

					}
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("未找到客户信息！");
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->editCustomerMobile=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.CustomerService#editCustomerMobile(cn.htd.
	 * usercenter.dto.CustomerDTO, long)
	 */
	@Override
	public ExecuteResult<Boolean> editCustomerAvatar(UserDTO userDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (userDTO != null && userDTO.getId() != null && userDTO.getId().intValue() > 0) {
				UserDTO dto = userDAO.queryUserById(userDTO.getId());
				if (dto != null) {
					userDTO.setLastUpdateId(userId);
					userDAO.updateUserAvatar(userDTO);
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
			logger.error("EmployeeExportServiceImpl----->editCustomerAvatar=" + e);
		}
		return rs;
	}
}
