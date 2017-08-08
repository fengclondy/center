package cn.htd.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.usercenter.dto.RoleDTO;
import cn.htd.usercenter.dto.UserRoleDTO;

public interface RoleDAO extends BaseDAO<RoleDTO> {

	public void addRole(@Param("roleDTO") RoleDTO roleDTO, @Param("userId") String userId);

	public void updateRole(@Param("roleDTO") RoleDTO roleDTO, @Param("userId") String userId);

	public List<RoleDTO> queryRoleByIdAndName(@Param("roleDTO") RoleDTO roleDTO, @Param("pager") Pager pager);

	public long queryRoleByIdAndNameCount(@Param("roleDTO") RoleDTO roleDTO);

	public void addRolePermissionRelation(@Param("productId") String productId, @Param("roleId") String roleId,
			@Param("permissionId") String permissionId);

	public void deleteRolePermissionRelation(@Param("roleDTO") RoleDTO roleDTO);

	public List<RoleDTO> queryRoleByProductId(@Param("role") RoleDTO role);

	public void deleteUserRolePermissions(@Param("userRoleDTO") UserRoleDTO userRoleDTO);

	public void addUserRolePermission(@Param("roleId") String roleId, @Param("userId") String userId);

	public List<RoleDTO> queryTotalUserRolePermission(@Param("userId") String userId);

	public List<RoleDTO> queryBranchUserRolePermission(@Param("userId") String userId);

	public int queryCount4RoleUsedByUser(@Param("roleDTO") RoleDTO roleDTO);

	public void deleteRole(@Param("roleDTO") RoleDTO roleDTO, @Param("userId") String userId);
}