package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthInDTO;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthOutDTO;

/**
 * 批量新增商品权限控制
 */
public interface MemberBatchAddItemAuthService {

    /**
     * 是否有权限批量新增商品
     * @param sellerId
     * @return
     */
    ExecuteResult<String> isAuthBatchAddItem(Long sellerId);

    /**
     * 开通批量权限
     * @param sellerId
     * @return
     */
    ExecuteResult<String> openAuthBatchAddItem(Long sellerId, Long operateId, String operateName);

    /**
     * 关闭批量权限
     * @param sellerId
     * @return
     */
    ExecuteResult<String> closeAuthBatchAddItem(Long sellerId);

    /**
     * 查询列表
     * @param queryBatchAddItemAuthInDTO
     * @return
     */
    ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>> queryBatchAddItemAuthList(QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO, Pager pager);
}
