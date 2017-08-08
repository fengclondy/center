package cn.htd.usercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.UserMallResourceDTO;

import java.util.List;

/**
 * Created by thinkpad on 2017/2/6.
 */
public interface UserStorePermissionExportService {

    /**
     *
     * <p>
     * Discription:[查询所有父级资源]
     * </p>
     *
     * @param type
     *            用户类型：1-买家/2-卖家/3-平台
     * @param modularType
     *            模块类型 ：1买家中心 2卖家中心
     */
    public ExecuteResult<List<UserMallResourceDTO>> queryParentResourceList(Integer type, Integer modularType);

    /**
     * 根据id查询usermallresource信息
     * @param id
     * @return
     */
    public ExecuteResult<UserMallResourceDTO> queryResourceNameById(Long id);
}
