package cn.htd.goodscenter.service.impl.stock;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.service.impl.stock.exception.*;
import cn.htd.goodscenter.service.impl.stock.handler.StockChangeAble;
import cn.htd.goodscenter.service.stock.SkuStockChangeExportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * 库存变动实现类
 * @author chenakng
 * @date 2016/11/23.
 */
@Service("skuStockChangeExportService")
public class SkuStockChangeExportServiceImpl implements SkuStockChangeExportService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SkuStockChangeExportServiceImpl.class);

    @Autowired
    private StockChangeAble skuStockReduceImpl;

    @Autowired
    private StockChangeAble skuStockReleaseImpl;

    @Autowired
    private StockChangeAble skuStockReserveImpl;

    @Autowired
    private StockChangeAble skuStockRollbackImpl;

    @Transactional
    public ExecuteResult<String> reduceStock(Order4StockChangeDTO order4StockChangeDTO) {
        logger.info("扣减库存, 参数 : {}", JSONObject.fromObject(order4StockChangeDTO));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            skuStockReduceImpl.changeStock(order4StockChangeDTO);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> releaseStock(Order4StockChangeDTO order4StockChangeDTO) {
        logger.info("释放库存, 参数 : {}", JSONObject.fromObject(order4StockChangeDTO));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            skuStockReleaseImpl.changeStock(order4StockChangeDTO);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> reserveStock(Order4StockChangeDTO order4StockChangeDTO) {
        logger.info("锁定库存, 参数 : {}", JSONObject.fromObject(order4StockChangeDTO));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            skuStockReserveImpl.changeStock(order4StockChangeDTO);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> rollbackStock(Order4StockChangeDTO order4StockChangeDTO) {
        logger.info("回滚库存, 参数 : {}", JSONObject.fromObject(order4StockChangeDTO));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            skuStockRollbackImpl.changeStock(order4StockChangeDTO);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> batchReduceStock(List<Order4StockChangeDTO> order4StockChangeDTOs) {
        logger.info("批量扣减库存, 参数 : {}", JSONArray.fromObject(order4StockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            for (Order4StockChangeDTO order4StockChangeDTO : order4StockChangeDTOs) {
                skuStockReduceImpl.changeStock(order4StockChangeDTO);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> batchReleaseStock(List<Order4StockChangeDTO> order4StockChangeDTOs) {
        logger.info("批量释放库存, 参数 : {}", JSONArray.fromObject(order4StockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            for (Order4StockChangeDTO order4StockChangeDTO : order4StockChangeDTOs) {
                skuStockReleaseImpl.changeStock(order4StockChangeDTO);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> batchReserveStock(List<Order4StockChangeDTO> order4StockChangeDTOs) {
        logger.info("批量锁定库存, 参数 : {}", JSONArray.fromObject(order4StockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            for (Order4StockChangeDTO order4StockChangeDTO : order4StockChangeDTOs) {
                skuStockReserveImpl.changeStock(order4StockChangeDTO);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    @Transactional
    @Override
    public ExecuteResult<String> batchRollbackStock(List<Order4StockChangeDTO> order4StockChangeDTOs) {
        logger.info("批量回滚库存, 参数 : {}", JSONArray.fromObject(order4StockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            for (Order4StockChangeDTO order4StockChangeDTO : order4StockChangeDTOs) {
                skuStockRollbackImpl.changeStock(order4StockChangeDTO);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    /**
     * 组合操作库存
     * @param order4StockChangeDTO
     * @return
     */
    @Transactional
    @Override
    public ExecuteResult<String> comboChangeStock(Order4StockChangeDTO order4StockChangeDTO) {
        logger.info("comboChangeStock entrance, order4StockChangeDTO : {}", order4StockChangeDTO);
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            // 检验
            String orderNo = order4StockChangeDTO.getOrderNo();
            String orderResource = order4StockChangeDTO.getOrderResource();
            String messageId = order4StockChangeDTO.getMessageId();
            if (StringUtils.isEmpty(orderNo)) {
                throw new StockInParamIllegalException("入参校验错误, orderNo为空");
            }
            if (StringUtils.isEmpty(messageId)) {
                throw new StockInParamIllegalException("入参校验错误, messageId为空");
            }
            if (StringUtils.isEmpty(orderResource)) {
                throw new StockInParamIllegalException("入参校验错误, orderResource为空");
            }
            List<Order4StockEntryDTO> orderEntries = order4StockChangeDTO.getOrderEntries();
            // 针对订单行进行库存修改
            for (Order4StockEntryDTO order4StockEntryDTO : orderEntries) {
                // 冗余字段，方便传递参数
                order4StockEntryDTO.setOrderNo(orderNo);
                order4StockEntryDTO.setOrderResource(orderResource);
                order4StockEntryDTO.setMessageId(messageId);
                if (order4StockEntryDTO.getStockTypeEnum() == null) {
                    throw new StockInParamIllegalException("入参校验错误, stockTypeEnum为空");
                }
                StockTypeEnum stockTypeEnum = order4StockEntryDTO.getStockTypeEnum();
                if (stockTypeEnum.equals(StockTypeEnum.RESERVE)) {
                    this.skuStockReserveImpl.changeEntryStock(order4StockEntryDTO, true);
                } else if (stockTypeEnum.equals(StockTypeEnum.RELEASE)) {
                    this.skuStockReleaseImpl.changeEntryStock(order4StockEntryDTO, true);
                } else if (stockTypeEnum.equals(StockTypeEnum.REDUCE)) {
                    this.skuStockReduceImpl.changeEntryStock(order4StockEntryDTO, true);
                } else if (stockTypeEnum.equals(StockTypeEnum.ROLLBACK)) {
                    this.skuStockRollbackImpl.changeEntryStock(order4StockEntryDTO, true);
                } else {
                    throw new StockInParamIllegalException("库存修改类型非法, stockTypeEnum : " + stockTypeEnum + ", order4StockEntryDTO : " + order4StockEntryDTO);
                }
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, order4StockChangeDTO);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
    }

    /**
     * 异常处理，转换返回码和返回消息
     * @param e
     * @return
     */
    private ExecuteResult handleException(Exception e, Object orderInfo) {
        ExecuteResult executeResult = new ExecuteResult();
        String resultCode;
        String resultMessage;
        if (e instanceof StockPublishInfoIsNullException) { // 入参为空
            resultCode = ResultCodeEnum.STOCK_PUBLISH_INFO_IS_NULL.getCode();
            resultMessage = ResultCodeEnum.STOCK_PUBLISH_INFO_IS_NULL.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockInParamIllegalException) { // 入参非法
            resultCode = ResultCodeEnum.STOCK_IN_PARAM_IS_ILLEGAL.getCode();
            resultMessage = ResultCodeEnum.STOCK_IN_PARAM_IS_ILLEGAL.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockNotEnoughAvailableStockException) { // 可卖库存不足
            resultCode = ResultCodeEnum.STOCK_AVAILABLE_STOCK_NOT_ENOUGH.getCode();
            resultMessage = ResultCodeEnum.STOCK_AVAILABLE_STOCK_NOT_ENOUGH.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockNotEnoughDisplayQuantityException) { // 可见存不足
            resultCode = ResultCodeEnum.STOCK_DISPLAY_STOCK_NOT_ENOUGH.getCode();
            resultMessage = ResultCodeEnum.STOCK_DISPLAY_STOCK_NOT_ENOUGH.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockNotEnoughReserveQuantityException) { // 锁定存不足
            resultCode = ResultCodeEnum.STOCK_RESERVE_STOCK_NOT_ENOUGH.getCode();
            resultMessage = ResultCodeEnum.STOCK_RESERVE_STOCK_NOT_ENOUGH.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockNoReserveRecordException) { // 之前没有锁定
            resultCode = ResultCodeEnum.STOCK_NO_RESVER_REOCRD.getCode();
            resultMessage = ResultCodeEnum.STOCK_NO_RESVER_REOCRD.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else if (e instanceof StockNoReduceRecordException) { // 之前没有扣减
            resultCode = ResultCodeEnum.STOCK_NO_REDUCE_REOCRD.getCode();
            resultMessage = ResultCodeEnum.STOCK_NO_REDUCE_REOCRD.getMessage();
            executeResult.addErrorMessage(e.getMessage());
        } else {
            resultCode = ResultCodeEnum.ERROR.getCode();
            resultMessage = ResultCodeEnum.ERROR.getMessage();
            executeResult.addErrorMessage(e.getMessage());
            logger.error("库存修改系统异常, 订单信息 : {}, 错误信息 ：", JSONArray.fromObject(orderInfo), e);
        }
        executeResult.setCode(resultCode);
        executeResult.setResultMessage(resultMessage);
        return executeResult;
    }
}
