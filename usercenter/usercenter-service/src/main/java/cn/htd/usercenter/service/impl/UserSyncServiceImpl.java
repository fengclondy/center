package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dao.HTDCompanyDAO;
import cn.htd.usercenter.dao.UserDAO;
import cn.htd.usercenter.dto.EmployeeDTO;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.OAWorkerDTO;
import cn.htd.usercenter.dto.UserDTO;
import cn.htd.usercenter.enums.UserEnums.EmployeeType;
import cn.htd.usercenter.service.UserSyncService;

@Service("userSyncService")
public class UserSyncServiceImpl implements UserSyncService {

    private final static Logger logger = LoggerFactory.getLogger(UserSyncServiceImpl.class);

    @Resource
    private HTDCompanyDAO htdCompanyDAO;

    @Resource
    private UserDAO userDAO;

    @Override
    public ExecuteResult<Boolean> syncHTDCompany(HTDCompanyDTO dto) {
        ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();

        try {
            // 同步HTD公司信息
            HTDCompanyDTO oldDto = htdCompanyDAO.queryHTDCompanyById(dto.getCompanyId());
            if (oldDto == null) {
                htdCompanyDAO.insertHTDCompany(dto);
            } else {
                // 公司信息发生变动时，更新公司信息
                if (!oldDto.equals(dto)) {
                    htdCompanyDAO.updateHTDCompany(dto);
                }
            }
            res.setResult(true);
        } catch (Exception e) {
            res.addErrorMessage(MessageFormat.format("同步ERP组织数据失败！({0})", e.getMessage()));
            logger.error("【同步ERP组织数据】出现异常！", e);
            return res;
        }

        return res;
    }

    @Override
    public ExecuteResult<Boolean> syncOAWorker(OAWorkerDTO workerDTO) {
        ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();

        try {
            // 判断员工是否已经存在
            EmployeeDTO oldEmp = userDAO.queryEmployeeByEmpNo(workerDTO.getWorkCode());

            if (oldEmp == null) {
                UserDTO user = new UserDTO();

            	UserDTO userDTO = userDAO.queryUserByLoginId(workerDTO.getWorkCode(), null);
            	if(userDTO!=null){
            		user.setId(userDTO.getId());
            	}else{
                    // 添加用户
                    user.setName(workerDTO.getLastName());
                    user.setLoginId(workerDTO.getWorkCode());
                    user.setPassword(workerDTO.getPassword());
                    user.setEmail(workerDTO.getEmail());
                    user.setMobile(workerDTO.getMobile());
                    user.setCreateId(GlobalConstant.ADMIN_USER_ID);
                    user.setLastUpdateId(GlobalConstant.ADMIN_USER_ID);
                    userDAO.insertUser(user);
            	}


                // 添加员工
                EmployeeDTO emp = new EmployeeDTO();
                emp.setUserId(user.getId());
                emp.setType(EmployeeType.OA_EMP.getCode());
                emp.setEmpNo(workerDTO.getWorkCode());
                // TODO 部门级别的分部人员所属公司需要等待OA调整
                emp.setCompanyId(workerDTO.getSubCompanyCode());
                emp.setSuperiorEmpNo(workerDTO.getSuperior());
                emp.setIsCustomerManager(workerDTO.getIsCM());
                emp.setStatus(workerDTO.getStatus());
                emp.setCreateId(GlobalConstant.ADMIN_USER_ID);
                emp.setLastUpdateId(GlobalConstant.ADMIN_USER_ID);
                if(workerDTO.getGw()==null){
                    emp.setGw("");                	
                }else{
                    emp.setGw(workerDTO.getGw());
                }
                userDAO.insertEmployee(emp);

            } else {
                UserDTO user = new UserDTO();
            	UserDTO userDTO = userDAO.queryUserByLoginId(workerDTO.getWorkCode(), null);
            	if(userDTO!=null){
            		// 更新用户
                    user.setId(oldEmp.getUserId());
                    user.setName(workerDTO.getLastName());
                    user.setLoginId(workerDTO.getWorkCode());
                    user.setPassword(workerDTO.getPassword());
                    user.setEmail(workerDTO.getEmail());
                    user.setMobile(workerDTO.getMobile());
                    user.setLastUpdateId(GlobalConstant.ADMIN_USER_ID);
                    userDAO.updateUser(user);
            	}else{
            		// 添加用户
                    user.setName(workerDTO.getLastName());
                    user.setLoginId(workerDTO.getWorkCode());
                    user.setPassword(workerDTO.getPassword());
                    user.setEmail(workerDTO.getEmail());
                    user.setMobile(workerDTO.getMobile());
                    user.setCreateId(GlobalConstant.ADMIN_USER_ID);
                    user.setLastUpdateId(GlobalConstant.ADMIN_USER_ID);
                    userDAO.insertUser(user);
            	}
                

                // 更新员工
                EmployeeDTO emp = new EmployeeDTO();
                emp.setUserId(user.getId());
                emp.setType(EmployeeType.OA_EMP.getCode());
                emp.setEmpNo(workerDTO.getWorkCode());
                emp.setCompanyId(workerDTO.getSubCompanyCode());
                emp.setSuperiorEmpNo(workerDTO.getSuperior());
                emp.setIsCustomerManager(workerDTO.getIsCM());
                emp.setStatus(workerDTO.getStatus());
                emp.setLastUpdateId(GlobalConstant.ADMIN_USER_ID);
                if(workerDTO.getGw()==null){
                    emp.setGw("");
                }else{
                    emp.setGw(workerDTO.getGw());
                }
                userDAO.updateEmployee(emp);
            }
            res.setResult(true);
        } catch (Exception e) {
            res.addErrorMessage(MessageFormat.format("同步OA员工数据失败！({0})", e.getMessage()));
            logger.error("【同步OA员工数据】出现异常！", e);
            return res;
        }

        return res;
    }

    @Override
    public ExecuteResult<Boolean> updateEmployeeSuperior() {
        ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();

        try {
            // 维护员工直接上级的用户ID
            userDAO.updateEmployeeSuperior();
            res.setResult(true);

        } catch (Exception e) {
            res.addErrorMessage(MessageFormat.format("同步OA员工数据失败！({0})", e.getMessage()));
            logger.error("【同步OA员工数据】出现异常！", e);
            return res;
        }

        return res;
    }
}
