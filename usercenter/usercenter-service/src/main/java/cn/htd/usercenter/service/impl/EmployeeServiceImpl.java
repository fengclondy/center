package cn.htd.usercenter.service.impl;

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
import cn.htd.usercenter.dao.EmployeeDAO;
import cn.htd.usercenter.dao.SubstationDAO;
import cn.htd.usercenter.dao.UserDAO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.SubstationDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.service.EmployeeService;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Resource
	private EmployeeDAO employeeDAO;
	@Resource
	private UserDAO userDAO;
	@Resource
	private SubstationDAO substationDAO;

	@Override
	public ExecuteResult<Boolean> addSubstationMan(EmployeeDTO employeeDTO, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (employeeDTO != null) {
				String loginId = employeeDTO.getLoginId();
				List<EmployeeDTO> employeeList = employeeDAO.isRepeatLoginId(loginId);
				if (employeeList.size() > 0) {// 判断用户名是都重复
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该昵称已经存在，请重新输入！");
				} else {
					UserDTO dto = new UserDTO();
					dto.setName(employeeDTO.getName());
					dto.setLoginId(employeeDTO.getLoginId());
					dto.setMobile(employeeDTO.getMobile());
					dto.setPassword(Md5Utils.getMd5(employeeDTO.getPassword()));
					dto.setEmail("");
					dto.setCreateId(userId);
					dto.setLastUpdateId(userId);
					userDAO.insertUser(dto);
					// 添加员工
					employeeDTO.setUserId(dto.getId());
					employeeDTO.setType("02");
					employeeDTO.setCreateId(userId);
					employeeDTO.setLastUpdateId(userId);
					employeeDAO.addEmployee(employeeDTO);
					if (!("").equals(employeeDTO.getSubstationId())) {
						// 将该用户变更为管理员
						substationDAO.updateManger(dto.getId(), employeeDTO.getSubstationId());
					}
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("保存成功！");
				}

			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->addSubstationMan=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<EmployeeDTO>> selectSubstationMan(String name, int page, int rows, long userId) {
		ExecuteResult<DataGrid<EmployeeDTO>> rs = new ExecuteResult<DataGrid<EmployeeDTO>>();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		try {
			DataGrid<EmployeeDTO> dg = new DataGrid<EmployeeDTO>();
			// 根据userId获取员工信息
			EmployeeDTO employeeDTO = employeeDAO.getEmployeeInfo(userId);
			int substationId = employeeDTO.getSubstationId();
			List<EmployeeDTO> employeeList = employeeDAO.selectSubstationMan(substationId, name, pager);
			long count = employeeDAO.selectSubstationManCount(substationId, name);
			try {
				if (employeeList != null) {
					dg.setRows(employeeList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->selectSubstationMan=" + e);
		}
		return rs;
	}

	/**
	 * 编辑分站人员
	 */
	@Override
	public ExecuteResult<Boolean> editSubstationMan(EmployeeDTO employeeDTO, long userid, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (employeeDTO != null) {
				String loginId = employeeDTO.getLoginId();
				List<EmployeeDTO> employeeList = employeeDAO.isRepeatLoginId(loginId);
				if (employeeList.size() > 0) {// 判断用户名是都重复
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该昵称已经存在，请重新输入！");
				} else {
					EmployeeDTO employeeDTO2 = employeeDAO.getEmployeeInfo(userid);
					UserDTO dto = new UserDTO();
					dto.setId(employeeDTO.getUserId());
					dto.setName(employeeDTO.getName());
					dto.setLoginId(employeeDTO.getLoginId());
					dto.setMobile(employeeDTO.getMobile());
					// 判断是否更改密码
					if (!("").equals(employeeDTO.getPassword())) {
						dto.setPassword(Md5Utils.getMd5(employeeDTO.getPassword()));
					} else {
						dto.setPassword(employeeDTO2.getPassword());
					}
					dto.setEmail("");
					dto.setCreateId(userId);
					dto.setLastUpdateId(userId);
					userDAO.updateUser(dto);
					// 更新员工
					employeeDTO2.setUserId(userid);
					employeeDTO2.setLastUpdateId(userId);
					employeeDTO2.setSubstationId(employeeDTO.getSubstationId());
					employeeDAO.editEmployee(employeeDTO2);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("更新成功！");
				}

			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->editSubstationMan=" + e);
		}
		return rs;
	}

	@Override
	public EmployeeDTO getEmployeeInfo(long userId) {
		return employeeDAO.getEmployeeInfo(userId);
	}

	@Override
	public ExecuteResult<DataGrid<EmployeeDTO>> selectEmployeeBySubId(int substationId, String name, int page,
			int rows) {
		ExecuteResult<DataGrid<EmployeeDTO>> rs = new ExecuteResult<DataGrid<EmployeeDTO>>();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		try {
			DataGrid<EmployeeDTO> dg = new DataGrid<EmployeeDTO>();
			List<EmployeeDTO> employeeList = employeeDAO.selectEmployeeBySubId(substationId, name, pager);
			long count = employeeDAO.selectEmployeeBySubIdCount(substationId, name);
			try {
				if (employeeList != null) {
					dg.setRows(employeeList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->selectEmployeeBySubId=" + e);
		}
		return rs;
	}

	/**
	 * 编辑分站管理员
	 */
	@Override
	public ExecuteResult<Boolean> editSubstationAdmin(int substationId, long manager, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (!("").equals(substationId) && !("").equals(manager)) {
				substationDAO.updateManger(manager, substationId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("更换管理员失败！");
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->editSubstationAdmin=" + e);
		}
		return rs;
	}

	/**
	 * 查询总部所有的人员
	 */
	@Override
	public ExecuteResult<DataGrid<EmployeeDTO>> selectAllMan(EmployeeDTO employeeDTO, HTDCompanyDTO htdCompanyDTO,
			int page, int rows) {
		ExecuteResult<DataGrid<EmployeeDTO>> rs = new ExecuteResult<DataGrid<EmployeeDTO>>();
		@SuppressWarnings("rawtypes")
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		try {
			DataGrid<EmployeeDTO> dg = new DataGrid<EmployeeDTO>();
			List<EmployeeDTO> employeeList = employeeDAO.selectAllMan(employeeDTO, htdCompanyDTO, pager);
			long count = employeeDAO.selectAllManCount(employeeDTO, htdCompanyDTO);
			try {
				if (employeeList != null) {
					dg.setRows(employeeList);
					dg.setTotal(count);
					rs.setResult(dg);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->selectAllMan=" + e);
		}
		return rs;
	}

	/**
	 * 修改总部人员数字权限
	 */
	@Override
	public ExecuteResult<Boolean> updateTotalNumPower(String inchargeCompanies, long userid, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			if (!("").equals(userid)) {
				employeeDAO.updateTotalNumPower(inchargeCompanies, userid, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("更换总部人员数字权限失败！");
			}
		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->updateTotalNumPower=" + e);
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> isRepeatLoginId(String loginId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {

			if (!("").equals(loginId)) {
				List<EmployeeDTO> employeeList = employeeDAO.isRepeatLoginId(loginId);
				if (employeeList.size() > 0) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该昵称已经存在，请重新输入！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("该昵称可以使用！");
				}
			}
		} catch (Exception e) {
			logger.error("SubstationExportServiceImpl----->isRepeatLoginId=" + e);
		}
		return rs;
	}

	/**
	 * 删除分站人员(管理员操作可以删除所有的人员)
	 */
	@Override
	public ExecuteResult<Boolean> deleteSubstationMan(long userid, long userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			// 判断操作人员是分站管理员还是总部管理员
			List<SubstationDTO> substationList = substationDAO.selectSubstationByManger(userId);
			// 操作对象是否为管理员
			List<SubstationDTO> substationList1 = substationDAO.selectSubstationByManger(userid);
			if (substationList.size() == 0) {// 是总部管理员
				if (("").equals(userid)) {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("删除人员失败！");
				} else {
					// 判断删除的人员是否为管理员
					if (substationList1.size() == 0) {// 不是管理员
						System.out.println("+++++++++++++++++++++++++++++此人不是管理员");
						// 逻辑删除
						employeeDAO.deleteSubstationMan(userid, userId);
						userDAO.updateSubstationMan(userid, userId);
					} else {
						// 逻辑删除
						employeeDAO.deleteSubstationMan(userid, userId);
						userDAO.updateSubstationMan(userid, userId);
						substationDAO.updateMangerIsNull(substationList1.get(0).getSubstationId(), userId);
					}
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("删除成功！");
				}
			} else {// 是分站管理员
				if (substationList1.size() != 0) {// 删除的是管理员
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("您没有权限删除管理员！");
				} else {
					// 逻辑删除
					employeeDAO.deleteSubstationMan(userid, userId);
					userDAO.updateSubstationMan(userid, userId);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("删除成功！");
				}
			}

		} catch (Exception e) {
			logger.error("EmployeeExportServiceImpl----->deleteSubstationMan=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.usercenter.service.EmployeeService#queryCustomerJLList(long)
	 */
	@Override
	public ExecuteResult<List<EmployeeDTO>> queryCustomerJLList(String companyId) {
		ExecuteResult<List<EmployeeDTO>> rs = new ExecuteResult<List<EmployeeDTO>>();
		try {
			List<EmployeeDTO> customerList = employeeDAO.queryJlList(companyId);
			try {
				if (customerList != null) {
					rs.setResult(customerList);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("EmployeeServiceImpl----->queryCustomerJLList=" + e);
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.EmployeeService#getEmployeeInfoByMobile(java.
	 * lang.String)
	 */
	@Override
	public EmployeeDTO getEmployeeInfoByMobile(String mobile) {
		return employeeDAO.getEmployeeInfoByMobile(mobile);
	}

	@Override
	public EmployeeDTO queryEmployeeByEmpNo(String empNo) {
		return userDAO.queryEmployeeByEmpNo(empNo);
	}

	@Override
	public ExecuteResult<List<String>> getAllGw() {
		ExecuteResult<List<String>> rs = new ExecuteResult<List<String>>();
		try {
			List<String> gwList = employeeDAO.getAllGw();
			try {
				if (gwList != null) {
					rs.setResult(gwList);
				} else {
					rs.setResultMessage("要查询的数据不存在");
				}

				rs.setResultMessage("success");
			} catch (Exception e) {
				rs.setResultMessage("error");
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			logger.error("EmployeeServiceImpl----->queryCustomerJLList=" + e);
		}
		return rs;
	}
}
