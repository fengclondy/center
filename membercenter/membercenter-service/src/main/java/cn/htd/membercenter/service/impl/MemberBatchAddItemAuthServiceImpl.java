package cn.htd.membercenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberBatchAddItemAuthMapper;
import cn.htd.membercenter.domain.MemberBatchAddItemAuth;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthInDTO;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthOutDTO;
import cn.htd.membercenter.service.MemberBatchAddItemAuthService;

import javax.annotation.Resource;
import java.util.List;

public class MemberBatchAddItemAuthServiceImpl implements MemberBatchAddItemAuthService {

    @Resource
    private MemberBatchAddItemAuthMapper MemberBatchAddItemAuthMapper;

    @Override
    public ExecuteResult<String> isAuthBatchAddItem(Long sellerId) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        try {
            MemberBatchAddItemAuth result = this.MemberBatchAddItemAuthMapper.selectBysellerId(sellerId);
            if (result == null) {
                executeResult.setCode("000001");
                executeResult.setResultMessage("没有权限");
                return executeResult;
            }
            executeResult.setCode("00000");
            executeResult.setResultMessage("有权限");
            return executeResult;
        } catch (Exception e) {
            executeResult.setCode("99999");
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> openAuthBatchAddItem(Long sellerId, Long operateId, String operateName) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        try {
            MemberBatchAddItemAuth memberBatchAddItemAuth = new MemberBatchAddItemAuth();
            memberBatchAddItemAuth.setIsOpen(1);
            memberBatchAddItemAuth.setSellerId(sellerId);
            memberBatchAddItemAuth.setCreateId(operateId);
            memberBatchAddItemAuth.setCreateName(operateName);
            memberBatchAddItemAuth.setModifyId(operateId);
            memberBatchAddItemAuth.setModifyName(operateName);
            int result = this.MemberBatchAddItemAuthMapper.insert(memberBatchAddItemAuth);
            if (result == 0) {
                executeResult.setCode("000001");
                executeResult.setResultMessage("开通失败");
                return executeResult;
            }
            executeResult.setCode("00000");
            executeResult.setResultMessage("开通成功");
            return executeResult;
        } catch (Exception e) {
            executeResult.setCode("99999");
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> closeAuthBatchAddItem(Long sellerId) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        try {
            int result = this.MemberBatchAddItemAuthMapper.deleteBySellerId(sellerId);
            if (result == 0) {
                executeResult.setCode("000001");
                executeResult.setResultMessage("关闭失败");
                return executeResult;
            }
            executeResult.setCode("00000");
            executeResult.setResultMessage("关闭成功");
            return executeResult;
        } catch (Exception e) {
            executeResult.setCode("99999");
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>> queryBatchAddItemAuthList(QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO, Pager pager) {
        ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>> executeResult = new ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>>();
        DataGrid<QueryBatchAddItemAuthOutDTO> dtoDataGrid = new DataGrid<QueryBatchAddItemAuthOutDTO>();
        try {
            Long count = this.MemberBatchAddItemAuthMapper.queryBatchAddItemAuthListCount(queryBatchAddItemAuthInDTO);
            if (count > 0) {
                List<QueryBatchAddItemAuthOutDTO> queryBatchAddItemAuthOutDTOList = this.MemberBatchAddItemAuthMapper.queryBatchAddItemAuthList(queryBatchAddItemAuthInDTO, pager);
                dtoDataGrid.setRows(queryBatchAddItemAuthOutDTOList);
                dtoDataGrid.setTotal(count);
            }
            executeResult.setResult(dtoDataGrid);
            executeResult.setCode("00000");
        } catch (Exception e) {
            executeResult.setCode("99999");
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }
}
