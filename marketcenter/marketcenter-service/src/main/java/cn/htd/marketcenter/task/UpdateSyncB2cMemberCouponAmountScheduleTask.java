package cn.htd.marketcenter.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.constant.RedisConst;
import cn.htd.marketcenter.common.utils.CalculateUtils;
import cn.htd.marketcenter.common.utils.GeneratorUtils;
import cn.htd.marketcenter.common.utils.MarketCenterRedisDB;
import cn.htd.marketcenter.dao.B2cCouponUseLogSyncHistoryDAO;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.dao.PromotionInfoDAO;
import cn.htd.marketcenter.dmo.B2cCouponUseLogSyncDMO;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import cn.htd.marketcenter.dto.PromotionDiscountInfoDTO;
import cn.htd.marketcenter.dto.PromotionInfoDTO;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 无敌券 定时任务从b2c_coupon_use_log_sync_history表里取出deal_flag=0的记录，将对应的金额增加|
 * 减少到会员店对应的优惠券上
 *
 * @author 张丁
 */
public class UpdateSyncB2cMemberCouponAmountScheduleTask implements IScheduleTaskDealMulti<B2cCouponUseLogSyncDMO> {

    protected static transient Logger logger =
            LoggerFactory.getLogger(UpdateSyncB2cMemberCouponAmountScheduleTask.class);

    @Resource
    private B2cCouponUseLogSyncHistoryDAO b2cCouponUseLogSyncHistoryDAO;

    @Resource
    private BuyerCouponInfoDAO buyerCouponInfoDAO;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private MarketCenterRedisDB marketRedisDB;

    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private DictionaryUtils dictionary;

    private final static int START_LINE = 0;

    private final static int END_LINE = 10000;

    private static final int zero = 0;

    private static final String ON_LINE_ORDER = "1"; // 在线支付订单

    private static final String CASH_ON_DELIVERY_ORDER_SURE = "2"; // 货到付款订单确认

    private static final String ON_LINE_SURE_CANCLE_ORDER = "3"; // 在线支付订单取消确认

    @Override
    public Comparator<B2cCouponUseLogSyncDMO> getComparator() {
        return new Comparator<B2cCouponUseLogSyncDMO>() {
            public int compare(B2cCouponUseLogSyncDMO o1, B2cCouponUseLogSyncDMO o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 根据条件查询当前调度服务器可处理的任务
     *
     * @param taskParameter    任务的自定义参数
     * @param ownSign          当前环境名称
     * @param taskQueueNum     当前任务类型的任务队列数量
     * @param taskItemList     当前调度服务器，分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<B2cCouponUseLogSyncDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
            List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info(
                "【selectTasks】【无敌券-更新会员店优惠券金额--开始执行】【请求参数：】【taskParameter:{},ownSign:{},taskQueueNum:{},taskItemList:{},eachFetchDataNum:{}】",
                new Object[]{
                        JSONObject.toJSONString(taskParameter), JSONObject.toJSONString(ownSign),
                        JSONObject.toJSONString(taskQueueNum), JSONObject.toJSONString(taskItemList),
                        JSONObject.toJSONString(eachFetchDataNum)});
        List<String> taskIdList = new ArrayList<String>();
        List<B2cCouponUseLogSyncDMO> queryB2cCouponUseLogSyncHistoryList = null;
        Map paramMap = new HashMap();
        paramMap.put("startLine", START_LINE);
        if (eachFetchDataNum > 0) {
            paramMap.put("endLine", eachFetchDataNum);
        } else {
            paramMap.put("endLine", END_LINE);
        }

        try {
            if (taskItemList != null && taskItemList.size() > 0) {
                for (TaskItemDefine taskItem : taskItemList) {
                    taskIdList.add(taskItem.getTaskItemId());
                }
                paramMap.put("taskQueueNum", taskQueueNum);
                paramMap.put("taskIdList", taskIdList);
                queryB2cCouponUseLogSyncHistoryList =
                        b2cCouponUseLogSyncHistoryDAO.queryB2cCouponUseLogSyncHistoryList(paramMap);
            }
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("无敌券-调用更新会员店优惠券金额时候发生异常:" + w.toString());
        } finally {

        }
        return queryB2cCouponUseLogSyncHistoryList;
    }

    @Override
    public boolean execute(B2cCouponUseLogSyncDMO[] tasks, String ownSign) throws Exception {
        logger.info("【execute】【无敌券-更新会员店优惠券金额--开始执行】【请求参数：】【tasks:{},ownSign:{}】", new Object[]{
                JSONObject.toJSONString(tasks), JSONObject.toJSONString(ownSign)});
        boolean result = true;
        try {
            if (tasks != null && tasks.length > 0) {
                for (B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO : tasks) {
                    String useType = b2cCouponUseLogSyncDMO.getUseType();
                    /*
                     * 校验 如果useType是在线支付订单取消确认,数据库里必须存在在 线支付订单|货到付款订单确认 的记录
					 * 如果不存在，将该条记录记为deal_flag=2(处理失败)
					 */
                    if (useType.equals(ON_LINE_SURE_CANCLE_ORDER)) {
                        Long useCouponCount = b2cCouponUseLogSyncHistoryDAO
                                .queryB2cCouponUseLogSyncHistoryUseCouponCount(b2cCouponUseLogSyncDMO);
                        if (null == useCouponCount || useCouponCount.intValue() == 0) {
                            B2cCouponUseLogSyncDMO record = new B2cCouponUseLogSyncDMO();
                            String dealFailReason = "该条数据非法--会员店的优惠券金额并未做过累加";
                            record.setDealFailReason(dealFailReason);
                            record.setDealFlag(2);
                            record.setUseType(ON_LINE_SURE_CANCLE_ORDER);
                            int updateResult = updateB2cCouponUseLogSyncHistory(b2cCouponUseLogSyncDMO, record);
                            logger.warn(dealFailReason + "参数:{},跟新数据库结果{}",
                                    JSONObject.toJSONString(b2cCouponUseLogSyncDMO), updateResult);
                            continue;
                        }
                    }
                    //判断是否在活动期间，查不到数据continue
                    boolean validateEffective = validatePromotionEffective(b2cCouponUseLogSyncDMO);
                    if (!validateEffective) {
                        continue;
                    }
                    String b2cActivityCode = b2cCouponUseLogSyncDMO.getB2cActivityCode();
                    PromotionDiscountInfoDTO promotionDiscountInfoDTO =
                            JSON.parse(marketRedisDB.getHash(RedisConst.REDIS_COUPON_TRIGGER, b2cActivityCode),
                                    PromotionDiscountInfoDTO.class);
                    if (promotionDiscountInfoDTO == null) {
                        logger.warn("不存在该促销活动数据b2cActivityCode:{}", b2cActivityCode);
                        continue;
                    }

                    String promotionId = promotionDiscountInfoDTO.getPromotionId();
                    String validStatus = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                            DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
                    String promotionStatus = marketRedisDB.getHash(RedisConst.REDIS_COUPON_VALID, promotionId);
                    if (!validStatus.equals(promotionStatus)) {
                        logger.warn("该活动已经失效promotionId:{}", promotionId);
                        continue;
                    }

                    String buyerCode = b2cCouponUseLogSyncDMO.getB2cSellerCode();
                    // 查询该会员获取优惠券数量,如果为空就做券，如果不为空就加金额
                    String count = marketRedisDB
                            .getHash(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCode + "&" + promotionId);
                    BigDecimal couponAmount = b2cCouponUseLogSyncDMO.getB2cCouponUsedAmount();
                    if (StringUtils.isEmpty(count)) {
                        // 做券
                        doCoupon(useType, promotionDiscountInfoDTO, buyerCode, b2cCouponUseLogSyncDMO, promotionId,
                                couponAmount);
                    } else {
                        // 增加或者扣减金额
                        BuyerCouponInfoDTO couponInfo = new BuyerCouponInfoDTO();
                        couponInfo.setBuyerCode(buyerCode);
                        couponInfo.setPromotionId(promotionId);
                        BuyerCouponInfoDTO couponInfoDaoRes =
                                buyerCouponInfoDAO.queryBuyerCouponByBuyerCodeAndPomotionId(couponInfo);
                        if (null == couponInfoDaoRes) {
                            logger.warn("没有从会员优惠券表里查到记录BuyerCouponInfoDTO:{}", JSON.json(couponInfo));
                            continue;
                        }
                        String buyerCouponCode = couponInfoDaoRes.getBuyerCouponCode();
                        // 更新会员店优惠券金额
                        updateCouponAmt(useType, buyerCode, buyerCouponCode, promotionId, couponAmount);
                    }
                    B2cCouponUseLogSyncDMO record = new B2cCouponUseLogSyncDMO();
                    record.setDealFlag(1);
                    record.setUseType(useType);
                    updateB2cCouponUseLogSyncHistory(b2cCouponUseLogSyncDMO, record);
                }
            }
        } catch (Exception e) {
            result = false;
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("无敌券-更新会员店优惠券金额-调用execute方法时候发生异常" + w.toString());
        } finally {
        }
        return result;
    }

    /**
     * 更新用券信息表
     *
     * @param b2cCouponUseLogSyncDMO
     * @param record
     * @return
     */
    public int updateB2cCouponUseLogSyncHistory(B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO,
            B2cCouponUseLogSyncDMO record) {
        record.setB2cOrderNo(b2cCouponUseLogSyncDMO.getB2cOrderNo());
        record.setB2cBuyerCouponCode(b2cCouponUseLogSyncDMO.getB2cBuyerCouponCode());
        record.setModifyId(0L);
        record.setModifyName("sys");
        record.setModifyTime(new Date());
        int updateResult = b2cCouponUseLogSyncHistoryDAO.updateB2cCouponUseLogSyncHistory(record);
        return updateResult;
    }

    /**
     * 校验是否在商城活动已经开始之前用券或者取消券
     *
     * @param b2cCouponUseLogSyncDMO
     * @return
     */
    public boolean validatePromotionEffective(B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO) {
        boolean flag = false;
        PromotionInfoDTO promotionInfoDTO = new PromotionInfoDTO();
        promotionInfoDTO.setB2cActivityCode(b2cCouponUseLogSyncDMO.getB2cActivityCode());
		
		/*promotionInfoDTO.setShowStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
		List<String> statusList = new ArrayList<String>();
		statusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                DictionaryConst.OPT_PROMOTION_STATUS_NO_START));
		statusList.add(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                DictionaryConst.OPT_PROMOTION_STATUS_START));
		promotionInfoDTO.setStatusList(statusList);*/

        PromotionInfoDTO promotionInfoDaoRes = promotionInfoDAO.queryPromotionInfoByParam(promotionInfoDTO);
        if (null == promotionInfoDaoRes) {
            logger.warn("根据参数:{}没有查到相关促销数据", JSONObject.toJSONString(promotionInfoDTO));
            return flag;
        }
        String showStatus = promotionInfoDaoRes.getShowStatus();
        if (StringUtils.isEmpty(showStatus) || !showStatus.equals(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)) || !showStatus.equals(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS))) {
            logger.warn("促销活动：{}审核未通过,或者未启用", JSONObject.toJSONString(promotionInfoDaoRes));
            return flag;
        }
		/*String status = promotionInfoDaoRes.getStatus();
		if(StringUtils.isEmpty(status) || !status.equals(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                DictionaryConst.OPT_PROMOTION_STATUS_NO_START))
                || !status.equals(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                DictionaryConst.OPT_PROMOTION_STATUS_START))){
			 logger.warn("促销活动：{}状态不是未开始或者不是进行中",JSONObject.toJSONString(promotionInfoDaoRes));
			 return flag = false;
		}*/
        Date effectiveTime = promotionInfoDaoRes.getEffectiveTime();
        Date creteTime = b2cCouponUseLogSyncDMO.getCreateTime();
        if (creteTime.after(effectiveTime)) {
            B2cCouponUseLogSyncDMO record = new B2cCouponUseLogSyncDMO();
            String dealFailReason = "商城活动已经开始,不支持用券和取消券";
            record.setDealFailReason(dealFailReason);
            record.setDealFlag(2);
            record.setUseType(b2cCouponUseLogSyncDMO.getUseType());
            updateB2cCouponUseLogSyncHistory(b2cCouponUseLogSyncDMO, record);
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 给会员店做券
     *
     * @param useType
     * @param promotionDiscountInfoDTO
     * @param buyerCode
     * @param b2cCouponUseLogSyncDMO
     * @param promotionId
     * @param couponAmount
     * @throws ParseException
     */
    public void doCoupon(String useType, PromotionDiscountInfoDTO promotionDiscountInfoDTO, String buyerCode,
            B2cCouponUseLogSyncDMO b2cCouponUseLogSyncDMO, String promotionId, BigDecimal couponAmount)
            throws ParseException {
        BuyerCouponInfoDTO couponInfo = new BuyerCouponInfoDTO();
        couponInfo.setBuyerCode(buyerCode);
        couponInfo.setBuyerName(b2cCouponUseLogSyncDMO.getB2cSellerName());
        String couponType = promotionDiscountInfoDTO.getCouponKind();
        String buyerCouponCode = noGenerator.generateCouponCode(couponType);
        couponInfo.setBuyerCouponCode(buyerCouponCode);
        couponInfo.setPromotionId(promotionId);
        couponInfo.setLevelCode(promotionDiscountInfoDTO.getLevelCode());
        couponInfo.setPromotionProviderType(promotionDiscountInfoDTO.getPromotionProviderType());
        couponInfo.setPromotionProviderSellerCode(b2cCouponUseLogSyncDMO.getBelongSuperiorCode());
        couponInfo.setCouponUseRang(b2cCouponUseLogSyncDMO.getBelongSuperiorName() + "专用");
        couponInfo.setCouponName(promotionDiscountInfoDTO.getPromotionName());
        couponInfo.setCouponType(couponType);
        couponInfo.setGetCouponTime(new Date());
        couponInfo.setCouponStartTime(promotionDiscountInfoDTO.getEffectiveStartTime());
        couponInfo.setCouponEndTime(promotionDiscountInfoDTO.getEffectiveEndTime());
        couponInfo.setDiscountThreshold(promotionDiscountInfoDTO.getDiscountThreshold());
        couponInfo.setDiscountPercent(promotionDiscountInfoDTO.getDiscountPercent());
        couponInfo.setCouponAmount(new BigDecimal(0));
        couponInfo.setCouponLeftAmount(couponAmount);
        String status =
                dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_UNUSED);
        couponInfo.setStatus(status);
        couponInfo.setCreateId(0L);
        couponInfo.setCreateName("sys");
        int insertResult = buyerCouponInfoDAO.addBuyerCouponInfo(couponInfo);
        if (insertResult == 1) {
            saveBuyerCoupon2RedisAfter2DB(couponInfo);
        }
    }

    /**
     * 插入DB后再插入redis
     *
     * @param couponInfo
     */
    public void saveBuyerCoupon2RedisAfter2DB(BuyerCouponInfoDTO couponInfo) {
        String buyerCode = couponInfo.getBuyerCode();
        String buyerCouponCode = couponInfo.getBuyerCouponCode();
        String promotionId = couponInfo.getPromotionId();
        String buyerCouponRedisKey = RedisConst.REDIS_BUYER_COUPON + "_" + buyerCode;
        String couponJsonStr = com.alibaba.fastjson.JSON.toJSONString(couponInfo);
        marketRedisDB.setHash(buyerCouponRedisKey, buyerCouponCode, couponJsonStr);
        long redisAmount = CalculateUtils.multiply(couponInfo.getCouponLeftAmount(), new BigDecimal(100)).longValue();
        marketRedisDB.setHash(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode,
                String.valueOf(redisAmount));
        String buyerCouponReceiveKey = buyerCode + "&" + promotionId;
        marketRedisDB.incrHash(RedisConst.REDIS_BUYER_COUPON_RECEIVE_COUNT, buyerCouponReceiveKey);
        marketRedisDB
                .tailPush(RedisConst.REDIS_COUPON_SEND_LIST + "_" + promotionId, buyerCode + "&" + buyerCouponCode);
        marketRedisDB.incrHash(RedisConst.REDIS_COUPON_RECEIVE_COUNT, promotionId);
    }

    /**
     * 更新会员店优惠券金额
     *
     * @param useType
     * @param buyerCode
     * @param buyerCouponCode
     * @param promotionId
     * @param couponAmount
     */
    private void updateCouponAmt(final String useType, final String buyerCode, final String buyerCouponCode,
            final String promotionId, final BigDecimal couponAmount) {
        long redisCouponAmount = 0;
        if (ON_LINE_ORDER.equals(useType) || CASH_ON_DELIVERY_ORDER_SURE.equals(useType)) {
            redisCouponAmount = couponAmount.multiply(new BigDecimal(100)).longValue();
        } else if (ON_LINE_SURE_CANCLE_ORDER.equals(useType)) {
            redisCouponAmount = -couponAmount.multiply(new BigDecimal(100)).longValue();
        }
        Long buyerCouponLeftAmount = marketRedisDB
                .incrHashBy(RedisConst.REDIS_BUYER_COUPON_AMOUNT, buyerCode + "&" + buyerCouponCode, redisCouponAmount);
        buyerCouponLeftAmount = null == buyerCouponLeftAmount ? 0L : buyerCouponLeftAmount;
        BuyerCouponInfoDTO couponInfo = new BuyerCouponInfoDTO();
        couponInfo
                .setCouponLeftAmount(CalculateUtils.divide(new BigDecimal(buyerCouponLeftAmount), new BigDecimal(100)));
        couponInfo.setModifyId(0L);
        couponInfo.setModifyName("sys");
        couponInfo.setBuyerCode(buyerCode);
        couponInfo.setBuyerCouponCode(buyerCouponCode);
        couponInfo.setStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_UNUSED));
        //只需要push到指定的redis对列里就行了
        marketRedisDB.tailPush(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST + "_" + buyerCode + "_" + buyerCouponCode,
                com.alibaba.fastjson.JSON.toJSONString(couponInfo));
        marketRedisDB.addSet(RedisConst.REDIS_BUYER_COUPON_NEED_UPDATE_LIST, buyerCode + "_" + buyerCouponCode);
    }
}
