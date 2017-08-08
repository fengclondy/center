package cn.htd.usercenter.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.common.constant.GlobalConstant;
import cn.htd.usercenter.dao.CustomerDAO;
import cn.htd.usercenter.dao.PermissionDAO;
import cn.htd.usercenter.dto.CustomerDTO;
import cn.htd.usercenter.dto.PermissionDTO;
import cn.htd.usercenter.dto.RoleDTO;
import cn.htd.usercenter.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	private final static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	private static final String VMSproductId = "VMS";

	@Resource
	private PermissionDAO permissionDao;

	@Resource
	private CustomerDAO customerDAO;

	@Override
	public ExecuteResult<Boolean> addPermission(PermissionDTO permissionDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = permissionDTO.getProductId();
			String name = permissionDTO.getName();
			String permissionId = permissionDTO.getPermissionId();
			String pageUrl = "";
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)
					&& StringUtils.isNotBlank(permissionId)) {
				if (StringUtils.isNotBlank(permissionDTO.getFunctionSymbol())) {
					PermissionDTO permissionSearch = new PermissionDTO();
					permissionSearch.setPermissionId(permissionDTO.getParentId());
					permissionSearch.setProductId(permissionDTO.getProductId());
					// 根据父节点编码以及产品编码查询父节点权限项目信息
					ExecuteResult<List<PermissionDTO>> permission = this
							.queryPermissionByParentIdAndType(permissionSearch);
					if (CollectionUtils.isNotEmpty(permission.getResult())) {
						pageUrl = permission.getResult().get(0).getPageUrl();
						// 父节点的pageUrl设置到子节点的技能相关中
						permissionDTO.setPageUrl(pageUrl);
						permissionDTO.setFunctionSymbol(pageUrl + "/" + permissionDTO.getFunctionSymbol());
					}
				}
				permissionDao.addPermission(permissionDTO, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("保存成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写表单信息！");
			}
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->addPermission=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<PermissionDTO>> queryPermissionByParentIdAndType(PermissionDTO permissionDTO) {
		ExecuteResult<List<PermissionDTO>> rs = new ExecuteResult<List<PermissionDTO>>();
		try {
			List<PermissionDTO> permissionList = permissionDao.queryPermissionByParentIdAndType(permissionDTO);
			rs.setResult(permissionList);
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->queryPermissionByIdAndType=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> updatePermission(PermissionDTO permissionDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String productId = permissionDTO.getProductId();
			String name = permissionDTO.getName();
			String permissionId = permissionDTO.getPermissionId();
			String pageUrl = "";
			if (StringUtils.isNotBlank(productId) && StringUtils.isNotBlank(name)
					&& StringUtils.isNotBlank(permissionId)) {
				if (StringUtils.isNotBlank(permissionDTO.getFunctionSymbol())) {
					PermissionDTO permissionSearch = new PermissionDTO();
					permissionSearch.setPermissionId(permissionDTO.getParentId());
					permissionSearch.setProductId(permissionDTO.getProductId());
					// 根据父节点编码以及产品编码查询父节点权限项目信息
					ExecuteResult<List<PermissionDTO>> permission = this
							.queryPermissionByParentIdAndType(permissionSearch);
					if (CollectionUtils.isNotEmpty(permission.getResult())) {
						pageUrl = permission.getResult().get(0).getPageUrl();
						// 父节点的pageUrl设置到子节点的技能相关中
						permissionDTO.setPageUrl(pageUrl);
						permissionDTO.setFunctionSymbol(pageUrl + "/" + permissionDTO.getFunctionSymbol());
					}
				}
				permissionDao.updatePermission(permissionDTO, userId);
				rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
				rs.setResultMessage("更新成功！");
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请填写表单信息！");
			}
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->updatePermission=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<PermissionDTO>> queryPermissionCheckedByRoleId(RoleDTO roleDTO) {
		ExecuteResult<List<PermissionDTO>> rs = new ExecuteResult<List<PermissionDTO>>();
		try {
			List<PermissionDTO> permissionList = permissionDao.queryPermissionCheckedByRoleId(roleDTO);
			rs.setResult(permissionList);
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->queryPermissionCheckedByRoleId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public ExecuteResult<Boolean> deletePermission(PermissionDTO permissionDTO, String userId) {
		ExecuteResult<Boolean> rs = new ExecuteResult<Boolean>();
		try {
			String permissionId = permissionDTO.getPermissionId();
			permissionDTO.setDeletedFlag(GlobalConstant.DELETED_FLAG_YES);
			if (StringUtils.isNotBlank(permissionId)) {
				int count = permissionDao.queryCount4PermissionUsedByRole(permissionDTO);
				if (count == 0) {
					permissionDao.deletePermission(permissionDTO, userId);
					rs.setResult(GlobalConstant.OPERATE_FLAG_SUCCESS);
					rs.setResultMessage("删除成功！");
				} else {
					rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
					rs.addErrorMessage("该权限已经被角色绑定，请先删除对应角色权限信息");
				}
			} else {
				rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
				rs.addErrorMessage("请选择要删除的权限！");
			}
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->deletePermission=" + e);
			rs.setResult(GlobalConstant.OPERATE_FLAG_FAIL);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	@Override
	public boolean checkPermissionId(PermissionDTO permissionDTO) {
		boolean flag = false;
		int count = permissionDao.checkPermissionId(permissionDTO);
		if (count == 0) {
			flag = true;
		}
		return flag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.PermissionService#queryPermissionByUserId(long)
	 */
	@Override
	public ExecuteResult<List<PermissionDTO>> queryPermissionByUserId(long userid) {
		ExecuteResult<List<PermissionDTO>> rs = new ExecuteResult<List<PermissionDTO>>();
		try {
			PermissionDTO permissionDTO = new PermissionDTO();
			permissionDTO.setProductId(VMSproductId);
			List<PermissionDTO> permissionList = permissionDao.queryPermissionByParentIdAndType(permissionDTO);

			List<PermissionDTO> permissionnewList = new ArrayList<PermissionDTO>();

			CustomerDTO crs = customerDAO.getCustomerInfo(userid);
			String[] vplist = null;
			if (crs != null && crs.getVmsPermissions() != null) {
				String vp = crs.getVmsPermissions();
				vplist = vp.split(",");

			}
			for (PermissionDTO permissionDTO2 : permissionList) {
				for (String idstr : vplist) {
					if (idstr != null && !idstr.trim().equals("")) {
						if (permissionDTO2.getPermissionId().equals(idstr)) {
							permissionnewList.add(permissionDTO2);
						}
					}
				}
			}

			rs.setResult(permissionnewList);
			rs.setResultMessage("查询用户权限成功");
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->queryPermissionByUserId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.htd.usercenter.service.PermissionService#queryAllPermissionByUserId(
	 * long)
	 */
	@Override
	public ExecuteResult<List<PermissionDTO>> queryAllPermissionByUserId(long userid) {
		ExecuteResult<List<PermissionDTO>> rs = new ExecuteResult<List<PermissionDTO>>();
		try {
			PermissionDTO permissionDTO = new PermissionDTO();
			permissionDTO.setProductId(VMSproductId);
			List<PermissionDTO> permissionList = permissionDao.queryPermissionByParentIdAndType(permissionDTO);
			rs.setResult(permissionList);
			rs.setResultMessage("查询权限成功");
		} catch (Exception e) {
			logger.error("PermissionExportServiceImpl----->queryAllPermissionByUserId=" + e);
			rs.addErrorMessage(MessageFormat.format("系统异常，请联系系统管理员！", e.getMessage()));
		}
		return rs;
	}

}
