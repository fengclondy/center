package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dao.RoleDAO;
import cn.htd.usercenter.dto.RoleDTO;
import cn.htd.usercenter.dto.UserRoleDTO;
import cn.htd.usercenter.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Resource
	private RoleDAO roleDao;

	@Override
	public ExecuteResult<Boolean> addRole(RoleDTO roleDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = roleDTO.getProductId();
			String name = roleDTO.getName();
			String rolePermissions = roleDTO.getAddPermissions();
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)) {
				roleDao.addRole(roleDTO, userId);
				String[] permissions = rolePermissions.split(",");
				for (String permission : permissions) {
					roleDao.addRolePermissionRelation(productId, roleDTO.getRoleId(), permission);
				}
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("保存成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写角色编码和角色名称信息！");
			}
		} catch (Exception e) {
			logger.error("RoleExportServiceImpl----->addRole=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<RoleDTO>> queryRoleByIdAndName(RoleDTO role, Pager<RoleDTO> pager) {
		ExecuteResult<DataGrid<RoleDTO>> rs = new ExecuteResult<DataGrid<RoleDTO>>();
		try {
			DataGrid<RoleDTO> dg = new DataGrid<RoleDTO>();
			List<RoleDTO> roleList = roleDao.queryRoleByIdAndName(role, pager);
			long count = roleDao.queryRoleByIdAndNameCount(role);
			try {
				if (roleList != null) {
					dg.setRows(roleList);
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

			rs.setResult(dg);
		} catch (Exception e) {
			logger.error("RoleExportServiceImpl----->queryRoleByIdAndName=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateRole(RoleDTO roleDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = roleDTO.getProductId();
			String name = roleDTO.getName();
			if (StringUtils.isNotBlank(name)) {
				roleDao.updateRole(roleDTO, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写角色名称");
			}
		} catch (Exception e) {
			logger.error("RoleExportServiceImpl----->updateRole=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updateRolePermission(RoleDTO roleDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = roleDTO.getProductId();
			String addPermissions = roleDTO.getAddPermissions();
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(addPermissions)) {
				roleDao.deleteRolePermissionRelation(roleDTO);
				String[] addPermissionArr = addPermissions.split(",");
				for (String permission : addPermissionArr) {
					roleDao.addRolePermissionRelation(productId, roleDTO.getRoleId(), permission);
				}
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("填写信息不全，请补齐");
			}
		} catch (Exception e) {
			logger.error("RoleExportServiceImpl----->updateRole=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<RoleDTO>> queryRoleByProductId(RoleDTO role) {
		ExecuteResult<List<RoleDTO>> rs = new ExecuteResult<List<RoleDTO>>();
		try {
			List<RoleDTO> roleList = roleDao.queryRoleByProductId(role);
			rs.setResult(roleList);
		} catch (Exception e) {
			logger.error("ProductExportServiceImpl----->queryPermissionCheckedByRoleId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> deleteRole(RoleDTO roleDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String roleId = roleDTO.getRoleId();
			roleDTO.setDeletedFlag(GlobalConstant.DELETED_FLAG_YES);
			if (StringUtils.isNotBlank(roleId)) {
				int count = roleDao.queryCount4RoleUsedByUser(roleDTO);
				if (count == 0) {
					roleDao.deleteRole(roleDTO, userId);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("删除成功！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该角色已经绑定权限，请先删除对应角色权限信息");
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请选择要删除的角色");
			}
		} catch (Exception e) {
			logger.error("RoleExportServiceImpl----->deleteRole=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("用户权限操作失败", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> excuteUserRolePermission(UserRoleDTO userRoleDTO) {
		ExecuteResult<Boolean> res = new ExecuteResult<Boolean>();
		String[] roleId = userRoleDTO.getRoleId();
		String userId = userRoleDTO.getUserId();
		try {
			if (roleId.length > 0) {
				roleDao.deleteUserRolePermissions(userRoleDTO);
				for (int i = 0; i < roleId.length; i++) {
					roleDao.addUserRolePermission(roleId[i], userId);
				}
				res.setResult(true);
			}
		} catch (Exception e) {
			res.setResult(false);
			res.addErrorMessage(MessageFormat.format("用户权限操作失败", e.getMessage()));
			logger.error("UserExportServiceImpl+excuteUserRolePermission======" + e);
		}
		return res;
	}

	@Override
	public ExecuteResult<List<RoleDTO>> queryTotalUserRolePermission(String userId) {
		ExecuteResult<List<RoleDTO>> res = new ExecuteResult<List<RoleDTO>>();
		try {
			List<RoleDTO> roleList = roleDao.queryTotalUserRolePermission(userId);
			res.setResult(roleList);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("查询用户权限信息失败", e.getMessage()));
			logger.error("UserExportServiceImpl+queryUserRolePermission======" + e);
		}
		return res;
	}

	@Override
	public ExecuteResult<List<RoleDTO>> queryBranchUserRolePermission(String userId) {
		ExecuteResult<List<RoleDTO>> res = new ExecuteResult<List<RoleDTO>>();
		try {
			List<RoleDTO> roleList = roleDao.queryBranchUserRolePermission(userId);
			res.setResult(roleList);
		} catch (Exception e) {
			res.addErrorMessage(MessageFormat.format("查询用户权限信息失败", e.getMessage()));
			logger.error("UserExportServiceImpl+queryUserRolePermission======" + e);
		}
		return res;
	}
}
