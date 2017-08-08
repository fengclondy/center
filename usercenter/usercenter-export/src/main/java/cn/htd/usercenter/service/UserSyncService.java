package cn.htd.usercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.dto.OAWorkerDTO;

/**
 * 用户相关数据同步接口类。
 */
public interface UserSyncService {

    /**
     * 同步HTD公司数据
     */
    public ExecuteResult<Boolean> syncHTDCompany(HTDCompanyDTO dto);

    /**
     * 同步OA员工数据
     */
    public ExecuteResult<Boolean> syncOAWorker(OAWorkerDTO workerDTO);

    /**
     * 维护员工直接上级的用户ID
     */
    public ExecuteResult<Boolean> updateEmployeeSuperior();
}
