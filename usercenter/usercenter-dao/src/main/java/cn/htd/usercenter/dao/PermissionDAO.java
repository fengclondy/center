package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.PermissionDTO;
import cn.htd.usercenter.dto.RoleDTO;
import cn.htd.usercenter.dto.UserDTO;

public interface PermissionDAO extends BaseDAO<UserDTO> {

	public void addPermission(@Param("permissionDTO") PermissionDTO permissionDTO, @Param("userId") String userId);

	public void updatePermission(@Param("permissionDTO") PermissionDTO permissionDTO, @Param("userId") String userId);

	public List<PermissionDTO> queryPermissionByParentIdAndType(@Param("permissionDTO") PermissionDTO permissionDTO);

	public List<PermissionDTO> queryPermissionCheckedByRoleId(@Param("roleDTO") RoleDTO roleDTO);

	public int queryCount4PermissionUsedByRole(@Param("permissionDTO") PermissionDTO permissionDTO);

	public void deletePermission(@Param("permissionDTO") PermissionDTO permissionDTO, @Param("userId") String userId);

	public int checkPermissionId(@Param("permissionDTO") PermissionDTO permissionDTO);

}
