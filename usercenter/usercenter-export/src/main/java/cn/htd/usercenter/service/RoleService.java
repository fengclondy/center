package cn.htd.usercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.usercenter.dto.RoleDTO;
import cn.htd.usercenter.dto.UserRoleDTO;

public interface RoleService {

    /**
     * 添加角色信息
     * 
     * @param RoleId
     * @param name
     * @return
     */
    public ExecuteResult<Boolean> addRole(RoleDTO roleDTO, String userId);

    /**
     * 根据角色编码和角色名称查询角色信息
     * 
     * @param RoleId
     * @param name
     * @return
     */
    public ExecuteResult<DataGrid<RoleDTO>> queryRoleByIdAndName(RoleDTO role, Pager<RoleDTO> pager);

    /**
     * 根据角色编码更新角色信息
     * 
     * @param RoleId
     * @param name
     * @param description
     * @param userId
     * @return
     */
    public ExecuteResult<Boolean> updateRole(RoleDTO roleDTO, String userId);

    /**
     * 根据角色编码更新角色信息
     * 
     * @param RoleId
     * @param name
     * @param description
     * @param userId
     * @return
     */
    public ExecuteResult<Boolean> updateRolePermission(RoleDTO roleDTO, String userId);

    /**
     * 根据角色编码删除角色信息
     * 
     * @param RoleId
     * @param name
     * @param description
     * @param userId
     * @return
     */
    public ExecuteResult<Boolean> deleteRole(RoleDTO roleDTO, String userId);

    /**
     * 根据产品编码查询角色信息
     * 
     * @param RoleId
     * @param name
     * @return
     */
    public ExecuteResult<List<RoleDTO>> queryRoleByProductId(RoleDTO role);

    /**
     * 查询当前用户的系统权限
     * 
     * @param userId
     * @return
     */
    public ExecuteResult<List<RoleDTO>> queryTotalUserRolePermission(String userId);

    /**
     * 查询当前用户的系统权限
     * 
     * @param userId
     * @return
     */
    public ExecuteResult<List<RoleDTO>> queryBranchUserRolePermission(String userId);

    /**
     * 操作用户角色权限
     * 
     * @param userRoleDTO
     * @return
     */
    public ExecuteResult<Boolean> excuteUserRolePermission(UserRoleDTO userRoleDTO);
}
