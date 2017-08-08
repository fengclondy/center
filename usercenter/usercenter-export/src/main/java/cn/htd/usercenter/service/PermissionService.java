package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.PermissionDTO;
import cn.htd.usercenter.dto.RoleDTO;

public interface PermissionService {
	/**
	 * 添加权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<Boolean> addPermission(PermissionDTO permissionDTO, String userId);

	/**
	 * 根据权限项目编码和权限项目类型查询权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<List<PermissionDTO>> queryPermissionByParentIdAndType(PermissionDTO permissionDTO);

	/**
	 * 根据权限项目编码更新权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @param description
	 * @param userId
	 * @return
	 */
	public ExecuteResult<Boolean> updatePermission(PermissionDTO permissionDTO, String userId);

	/**
	 * 根据角色查询权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<List<PermissionDTO>> queryPermissionCheckedByRoleId(RoleDTO roleDTO);

	/**
	 * 删除权限项目信息
	 * 
	 * @param permissionId
	 * @param name
	 * @return
	 */
	public ExecuteResult<Boolean> deletePermission(PermissionDTO permissionDTO, String userId);

	/**
	 * 校验权限项目编码是否被使用
	 * 
	 * @param permissionDTO
	 * @return
	 */
	public boolean checkPermissionId(PermissionDTO permissionDTO);

	/**
	 * 根据客户查询权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<List<PermissionDTO>> queryPermissionByUserId(long userid);

	/**
	 * 根据客户查询权限项目信息
	 * 
	 * @param productId
	 * @param name
	 * @return
	 */
	public ExecuteResult<List<PermissionDTO>> queryAllPermissionByUserId(long userid);
}
