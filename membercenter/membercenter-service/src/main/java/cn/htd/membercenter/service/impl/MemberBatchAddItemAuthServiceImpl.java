package cn.htd.membercenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberBatchAddItemAuthMapper;
import cn.htd.membercenter.domain.MemberBatchAddItemAuth;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthInDTO;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthOutDTO;
import cn.htd.membercenter.service.MemberBatchAddItemAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("memberBatchAddItemAuthService")
public class MemberBatchAddItemAuthServiceImpl implements MemberBatchAddItemAuthService {

    @Resource
    private MemberBatchAddItemAuthMapper MemberBatchAddItemAuthMapper;

    @Override
    public ExecuteResult<Boolean> isAuthBatchAddItem(Long sellerId) {
        ExecuteResult<Boolean> executeResult = new ExecuteResult<Boolean>();
        try {
            MemberBatchAddItemAuth result = this.MemberBatchAddItemAuthMapper.selectBysellerId(sellerId);
            if (result == null) {
                executeResult.setCode("000001");
                executeResult.setResultMessage("没有权限");
                executeResult.setResult(false);
                return executeResult;
            }
            executeResult.setCode("00000");
            executeResult.setResultMessage("有权限");
            executeResult.setResult(true);
            return executeResult;
        } catch (Exception e) {
            executeResult.setCode("99999");
            executeResult.addErrorMessage(e.getMessage());
            executeResult.setResult(false);
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<String> openAuthBatchAddItem(QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO, Long operateId, String operateName) {
        ExecuteResult<String> executeResult = new ExecuteResult<String>();
        try {
            MemberBatchAddItemAuth memberBatchAddItemAuth = new MemberBatchAddItemAuth();
            memberBatchAddItemAuth.setIsOpen(1);
            memberBatchAddItemAuth.setStartTime(queryBatchAddItemAuthInDTO.getStartTime());
            memberBatchAddItemAuth.setEndTime(queryBatchAddItemAuthInDTO.getEndTime());
            memberBatchAddItemAuth.setSellerId(Long.valueOf(queryBatchAddItemAuthInDTO.getSellerId()));
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
    public ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>> handleAndQueryBatchAddItemAuthList(QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO, Pager pager) {
        ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>> executeResult = new ExecuteResult<DataGrid<QueryBatchAddItemAuthOutDTO>>();
        DataGrid<QueryBatchAddItemAuthOutDTO> dtoDataGrid = new DataGrid<QueryBatchAddItemAuthOutDTO>();
        try {
            // 删除已过期的数据
            this.MemberBatchAddItemAuthMapper.deleteExpireDate();
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
