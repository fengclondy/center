package cn.htd.goodscenter.service.impl.promotionstock;

import java.util.List;
import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.github.pagehelper.StringUtil;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.common.utils.RedissonClientUtil;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoHistoryMapper;
import cn.htd.goodscenter.dao.ItemSkuPublishInfoMapper;
import cn.htd.goodscenter.domain.ItemSkuPublishInfo;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;
import cn.htd.goodscenter.service.impl.stock.exception.StockInParamIllegalException;
import cn.htd.goodscenter.service.impl.stock.exception.StockNotEnoughAvailableStockException;
import cn.htd.goodscenter.service.impl.stock.exception.StockPublishInfoIsNullException;
import cn.htd.goodscenter.service.promotionstock.PromotionSkuStockChangeExportService;
import net.sf.json.JSONArray;

@Service("promotionSkuStockChangeExportService")
public class PromotionSkuStockChangeExportServiceImpl implements PromotionSkuStockChangeExportService{

    private Logger logger = LoggerFactory.getLogger(PromotionSkuStockChangeExportServiceImpl.class);
    @Resource
    private ItemSkuDAO itemSkuDAO;
    @Resource
    private ItemMybatisDAO itemMybatisDAO;
    @Resource
    protected ItemSkuPublishInfoMapper itemSkuPublishInfoMapper;
    @Resource
    private ItemSkuPublishInfoHistoryMapper itemSkuPublishInfoHistoryMapper;
    @Autowired
    private RedissonClientUtil redissonClientUtil;

    @Transactional
	@Override
	public ExecuteResult<String> batchReduceStock(List<PromotionStockChangeDTO> promotionStockChangeDTOs) {
		logger.info("批量扣减库存, 参数 : {}", JSONArray.fromObject(promotionStockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
            for (PromotionStockChangeDTO promotionStockChangeDTO : promotionStockChangeDTOs) {
            	verificationPromotionParam(promotionStockChangeDTO);
            	long stockId = selectStockId(promotionStockChangeDTO);
            	RLock rLock = null;
            	try {
                    RedissonClient redissonClient = redissonClientUtil.getInstance();
                    String lockKey = Constants.REDIS_KEY_PREFIX_STOCK + String.valueOf(stockId); // 竞争资源标志
                    rLock = redissonClient.getLock(lockKey);
                	/** 上锁 **/
                    rLock.lock();
                	int quantity = promotionStockChangeDTO.getQuantity().intValue();
                    ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
                    int displayQuantity = ((itemSkuPublishInfo.getDisplayQuantity() - itemSkuPublishInfo.getReserveQuantity()) - quantity);
                    if(displayQuantity < 0){
                    	// 可卖库存小于下单商品数量
                        throw new StockNotEnoughAvailableStockException("可卖库存不足, 详细 : "
                                + formatExceptionMessage(itemSkuPublishInfo, promotionStockChangeDTO));
                    }
                    itemSkuPublishInfo.setDisplayQuantity(displayQuantity);
                    // 更新库存信息
                    itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
				} finally{
					 rLock.unlock();
				}
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, promotionStockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
	}
	
	@Override
	public ExecuteResult<String> batchReleaseStock(List<PromotionStockChangeDTO> promotionStockChangeDTOs) {
		logger.info("批量释放库存, 参数 : {}", JSONArray.fromObject(promotionStockChangeDTOs));
        ExecuteResult<String> executeResult = new ExecuteResult<>();
        try {
        	for (PromotionStockChangeDTO promotionStockChangeDTO : promotionStockChangeDTOs) {
            	verificationPromotionParam(promotionStockChangeDTO);
            	long stockId = selectStockId(promotionStockChangeDTO);
            	int quantity = promotionStockChangeDTO.getQuantity().intValue();
                ItemSkuPublishInfo itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectByPrimaryKey(stockId);
                int displayQuantity = itemSkuPublishInfo.getDisplayQuantity() + quantity;
                itemSkuPublishInfo.setDisplayQuantity(displayQuantity);
                // 更新库存信息
                itemSkuPublishInfoMapper.updateByPrimaryKeySelective(itemSkuPublishInfo);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            executeResult = this.handleException(e, promotionStockChangeDTOs);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeResult;
	}
	
	private void verificationPromotionParam(PromotionStockChangeDTO promotionStockChangeDTO) 
			throws StockInParamIllegalException{
		if(null == promotionStockChangeDTO){
			 throw new StockInParamIllegalException("入参校验错误, promotionStockChangeDTO为空");
		}
		if(StringUtil.isEmpty(promotionStockChangeDTO.getSkuCode())){
			 throw new StockInParamIllegalException("入参校验错误, skuCode为空");
		}
		if(null == promotionStockChangeDTO.getQuantity() || promotionStockChangeDTO.getQuantity().intValue() < 0){
			throw new StockInParamIllegalException("入参校验错误, 商品数量不合法");
		}
		if(promotionStockChangeDTO.getQuantity().intValue() == 0){
			return;
		}
	}
	

	private long selectStockId(PromotionStockChangeDTO promotionStockChangeDTO) throws StockPublishInfoIsNullException {
		String skuCode = promotionStockChangeDTO.getSkuCode();
        // 查询STOCK_ID
        ItemSkuPublishInfo itemSkuPublishInfo = null;
        try {
            itemSkuPublishInfo = this.itemSkuPublishInfoMapper.selectBySkuCodeAndShelfType(skuCode, 0);
        } catch (Exception e) {
            logger.error("查询库存失败, 错误信息 : ", e);
        }
        if (itemSkuPublishInfo == null) {
            throw new StockPublishInfoIsNullException("操作库存失败-查询库存失败, 商品SKU编码 : " + skuCode);
        }
        return itemSkuPublishInfo.getId();
	}
	
	/**
     * 异常处理，转换返回码和返回消息
     * @param e
     * @return
     */
    @SuppressWarnings("rawtypes")
	private ExecuteResult handleException(Exception e, Object promotion) {
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
        } else {
            resultCode = ResultCodeEnum.ERROR.getCode();
            resultMessage = ResultCodeEnum.ERROR.getMessage();
            executeResult.addErrorMessage(e.getMessage());
            logger.error("库存修改系统异常, 活动信息 : {}, 错误信息 ：", JSONArray.fromObject(promotion), e);
        }
        executeResult.setCode(resultCode);
        executeResult.setResultMessage(resultMessage);
        return executeResult;
    }
    
    protected String formatExceptionMessage(ItemSkuPublishInfo itemSkuPublishInfo, PromotionStockChangeDTO promotionStockChangeDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("可见库存数: ");
        stringBuilder.append(itemSkuPublishInfo.getDisplayQuantity());
        stringBuilder.append(",活动商品数量: ");
        stringBuilder.append(promotionStockChangeDTO.getQuantity());
        stringBuilder.append(",活动商品SKU_CODE: ");
        stringBuilder.append(promotionStockChangeDTO.getSkuCode());
        return stringBuilder.toString();
    }
}
