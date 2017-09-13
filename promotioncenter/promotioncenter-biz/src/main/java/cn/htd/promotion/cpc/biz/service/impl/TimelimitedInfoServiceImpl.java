package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSellerDetailDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionSellerRuleDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuPictureDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionInfoDMO;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.PromotionValidDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuDescribeResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuPictureResDTO;

import com.alibaba.fastjson.JSON;

@Service("timelimitedInfoService")
public class TimelimitedInfoServiceImpl implements TimelimitedInfoService {

    private static final Logger logger = LoggerFactory.getLogger(TimelimitedInfoServiceImpl.class);

    @Resource
    private TimelimitedInfoDAO timelimitedInfoDAO;

    @Resource
    private TimelimitedSkuPictureDAO timelimitedSkuPictureDAO;

    @Resource
    private TimelimitedSkuDescribeDAO timelimitedSkuDescribeDAO;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private PromotionInfoDAO promotionInfoDAO;

    @Resource
    private DictionaryUtils dictionary;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
    
    @Resource
    private PromotionSellerDetailDAO promotionSellerDetailDAO;

    @Resource
    private PromotionSellerRuleDAO promotionSellerRuleDAO;
    

    @Override
    public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

        // 当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        try {

            // 添加促销活动信息
            PromotionExtendInfoDTO promotionExtendInfoDTO = timelimitedInfoReqDTO.getPromotionExtendInfoDTO();
            PromotionExtendInfoDTO promotionExtendInfoReturn = baseService.insertPromotionInfo(promotionExtendInfoDTO);
            if (null == promotionExtendInfoReturn || null == promotionExtendInfoReturn.getPromotionId() || ""
                    .equals(promotionExtendInfoReturn.getPromotionId().trim())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "新建秒杀促销活动失败！");
            }

            // 添加秒杀活动履历
            addPromotionStatusHistory(promotionExtendInfoReturn, timelimitedInfoReqDTO);

            // 设置活动编码
            timelimitedInfoReqDTO.setPromotionId(promotionExtendInfoReturn.getPromotionId());
            // 设置层级编码
            List<? extends PromotionAccumulatyDTO> promotionAccumulatyDTOList =
                    promotionExtendInfoReturn.getPromotionAccumulatyList();
            if (null != promotionAccumulatyDTOList && promotionAccumulatyDTOList.size() == 1) {
                timelimitedInfoReqDTO.setLevelCode(promotionAccumulatyDTOList.get(0).getLevelCode());
            } else {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
                        "新建秒杀促销活动层级失败！");
            }

            // 添加商品图片,返回商品主图
//            String firstPictureUrl = 
            addTimelimitedSkuPictureList(timelimitedInfoReqDTO, currentTime);
            
            // 添加秒杀商品
//            timelimitedInfoReqDTO.setSkuPicUrl(firstPictureUrl);
            timelimitedInfoReqDTO.setCreateTime(currentTime);
            timelimitedInfoReqDTO.setModifyTime(currentTime);
            timelimitedInfoDAO.insert(timelimitedInfoReqDTO);

            // 添加商品详情
            addTimelimitedSkuDescribeList(timelimitedInfoReqDTO, currentTime);
            
            // 处理促销活动供应商规则详情
            handlePromotionSellerDetail(timelimitedInfoReqDTO,TimelimitedConstants.SELLERDETAIL_OPERATETYPE_ADD);

            // 异步初始化秒杀活动的Redis数据
            TimelimitedInfoResDTO timelimitedInfoResDTO =
                    getSingleFullTimelimitedInfoByPromotionId(promotionExtendInfoReturn.getPromotionId(),TimelimitedConstants.TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT, messageId);
            initTimelimitedInfoRedisInfoWithThread(timelimitedInfoResDTO);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【addTimelimitedInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

        try {

            TimelimitedInfoResDTO timelimitedInfoRes_check =
                    getSingleTimelimitedInfoByPromotionId(timelimitedInfoReqDTO.getPromotionId(), messageId);
            if (null == timelimitedInfoRes_check) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.NORESULT.getCode(), "秒杀促销活动不存在！");
            }

            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();

            // 修改促销活动信息
            PromotionExtendInfoDTO promotionExtendInfoDTO = timelimitedInfoReqDTO.getPromotionExtendInfoDTO();
            PromotionExtendInfoDTO promotionExtendInfoReturn = baseService.updatePromotionInfo(promotionExtendInfoDTO);
            if (null == promotionExtendInfoReturn || null == promotionExtendInfoReturn.getPromotionId() || ""
                    .equals(promotionExtendInfoReturn.getPromotionId().trim())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "修改秒杀促销活动失败！");
            }

            // 添加秒杀活动履历
            addPromotionStatusHistory(promotionExtendInfoReturn, timelimitedInfoReqDTO);

            // 设置层级编码
            List<? extends PromotionAccumulatyDTO> promotionAccumulatyDTOList =
                    promotionExtendInfoReturn.getPromotionAccumulatyList();
            if (null != promotionAccumulatyDTOList && promotionAccumulatyDTOList.size() == 1) {
                timelimitedInfoReqDTO.setLevelCode(promotionAccumulatyDTOList.get(0).getLevelCode());
            } else {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(),
                        "查询秒杀促销活动层级失败！");
            }

            // 伪删除商品活动的所有图片
            TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO_delete = new TimelimitedSkuPictureReqDTO();
            timelimitedSkuPictureReqDTO_delete.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
            timelimitedSkuPictureReqDTO_delete.setDeleteFlag(Boolean.TRUE);
            timelimitedSkuPictureReqDTO_delete.setModifyId(timelimitedInfoReqDTO.getModifyId());
            timelimitedSkuPictureReqDTO_delete.setModifyName(timelimitedInfoReqDTO.getModifyName());
            timelimitedSkuPictureReqDTO_delete.setModifyTime(currentTime);
            timelimitedSkuPictureDAO.pseudoDelete(timelimitedSkuPictureReqDTO_delete);
            // 添加商品图片,返回商品主图
            addTimelimitedSkuPictureList(timelimitedInfoReqDTO, currentTime);
            
            // 修改秒杀商品
            timelimitedInfoReqDTO.setModifyTime(currentTime);
            timelimitedInfoDAO.updateTimelimitedInfoByPromotionId(timelimitedInfoReqDTO);

            // 伪删除商品详情的所有图片
            TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO_delete = new TimelimitedSkuDescribeReqDTO();
            timelimitedSkuDescribeReqDTO_delete.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
            timelimitedSkuDescribeReqDTO_delete.setDeleteFlag(Boolean.TRUE);
            timelimitedSkuDescribeReqDTO_delete.setModifyId(timelimitedInfoReqDTO.getModifyId());
            timelimitedSkuDescribeReqDTO_delete.setModifyName(timelimitedInfoReqDTO.getModifyName());
            timelimitedSkuDescribeReqDTO_delete.setModifyTime(currentTime);
            timelimitedSkuDescribeDAO.pseudoDelete(timelimitedSkuDescribeReqDTO_delete);
            // 添加商品详情
            addTimelimitedSkuDescribeList(timelimitedInfoReqDTO, currentTime);
            
            // 处理促销活动供应商规则详情
            handlePromotionSellerDetail(timelimitedInfoReqDTO,TimelimitedConstants.SELLERDETAIL_OPERATETYPE_UPDATE);

            // 异步初始化秒杀活动的Redis数据
            TimelimitedInfoResDTO timelimitedInfoResDTO =
                    getSingleFullTimelimitedInfoByPromotionId(timelimitedInfoReqDTO.getPromotionId(),TimelimitedConstants.TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT, messageId);
            initTimelimitedInfoRedisInfoWithThread(timelimitedInfoResDTO);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【updateTimelimitedInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initTimelimitedInfoRedisInfoWithThread(final TimelimitedInfoResDTO timelimitedInfoResDTO) {
        initTimelimitedInfoRedisInfo(timelimitedInfoResDTO);
    }

    @Override
    public void initTimelimitedInfoRedisInfo(TimelimitedInfoResDTO timelimitedInfoResDTO) {

        PromotionInfoDMO promotionInfoDMO = new PromotionInfoDMO();
        promotionInfoDMO.setPromotionId(timelimitedInfoResDTO.getPromotionExtendInfoDTO().getPromotionId());
        try {
            setTimelimitedInfo2Redis(timelimitedInfoResDTO);
            setTimelimitedReserveQueue(timelimitedInfoResDTO);

            promotionInfoDMO.setDealFlag(YesNoEnum.NO.getValue());
        } catch (Exception e) {
            promotionInfoDMO.setDealFlag(YesNoEnum.YES.getValue());

            logger.info("初始化秒杀活动Redis数据TimelimitedInfoServiceImpl-initTimelimitedInfoRedisInfo:入参:{},异常:{}",
                    JSON.toJSONString(timelimitedInfoResDTO), ExceptionUtils.getFullStackTrace(e));
            throw new RuntimeException(e);
        } finally {
            promotionInfoDAO.updatePromotionDealFlag(promotionInfoDMO);
        }
    }

    @Override
    public TimelimitedInfoResDTO getSingleFullTimelimitedInfoByPromotionId(String promotionId,String type, String messageId) {

//    	type 1.数据库商品库存,2.redis商品真实库存 
    	
        TimelimitedInfoResDTO timelimitedInfoResDTO = null;

        try {

            if (null == promotionId || "".equals(promotionId.trim())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动编号不能为空！");
            }

            // 查询活动信息
            timelimitedInfoResDTO = timelimitedInfoDAO.selectByPromotionId(promotionId);
            
            if(null == type || "".equals(type.trim())){
            	throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动获取库存类型不能为空！");
            }
            
            boolean typeFlag = TimelimitedConstants.TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT.equals(type.trim()) || TimelimitedConstants.TYPE_REDIS_TIMELIMITED_REAL_REMAIN_COUNT.equals(type.trim());
            if(!typeFlag){
            	throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动库存类型错误！");
            }
            
            if(TimelimitedConstants.TYPE_REDIS_TIMELIMITED_REAL_REMAIN_COUNT.equals(type.trim())){//获取redis里的真实库存
        		   // Redis真实秒杀商品数量
       		    String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
       		    String remainCountStr = promotionRedisDB.getHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT);
       		    if(null == remainCountStr || "".equals(remainCountStr.trim())){
       			  throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀商品实际库存读取异常！");
       		    }
       		    if(!StringUtils.isNumeric(remainCountStr)){
       			  throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀商品实际库存不能为非数字！");
       		    }
       		    Integer timelimitedSkuCount = Integer.valueOf(remainCountStr);
       		    if(timelimitedSkuCount < 0){
       			   throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀商品实际库存不能为负整数！");
       		    }
       		    //设置真实库存
       		    timelimitedInfoResDTO.setTimelimitedSkuCount(timelimitedSkuCount);
            }

   		     
   		 
            // 查询图片信息
            List<TimelimitedSkuPictureResDTO> timelimitedSkuPictureResDTOList = timelimitedSkuPictureDAO.selectByPromotionId(promotionId);
            // 查询详情信息
            List<TimelimitedSkuDescribeResDTO> timelimitedSkuDescribeResDTOList = timelimitedSkuDescribeDAO.selectByPromotionId(promotionId);

            timelimitedInfoResDTO.setTimelimitedSkuPictureList(timelimitedSkuPictureResDTOList);
            timelimitedInfoResDTO.setTimelimitedSkuDescribeList(timelimitedSkuDescribeResDTOList);

            // 查询销活动信息
            PromotionExtendInfoDTO promotionExtendInfoDTO = baseService.queryPromotionInfo(promotionId);
            if (null == promotionExtendInfoDTO) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "查询秒杀促销活动失败！");
            }
            timelimitedInfoResDTO.setPromotionExtendInfoDTO(promotionExtendInfoDTO);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【getSingleTimelimitedInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

        return timelimitedInfoResDTO;

    }

    @Override
    public TimelimitedInfoResDTO getSingleTimelimitedInfoByPromotionId(String promotionId, String messageId) {

        TimelimitedInfoResDTO timelimitedInfoResDTO = null;

        try {

            if (null == promotionId) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动编号不能为空！");
            }

            // 查询活动信息
            timelimitedInfoResDTO = timelimitedInfoDAO.selectByPromotionId(promotionId);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【getSingleTimelimitedInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

        return timelimitedInfoResDTO;

    }

    @Override
    public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
            TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

        DataGrid<TimelimitedInfoResDTO> dataGrid = null;
        try {
            if (null == timelimitedInfoReqDTO) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "秒杀促销活动参数不能为空！");
            }

            dataGrid = new DataGrid<TimelimitedInfoResDTO>();
            List<TimelimitedInfoResDTO> timelimitedInfoResDTOList =
                    timelimitedInfoDAO.getTimelimitedInfosForPage(page, timelimitedInfoReqDTO);
            int count = timelimitedInfoDAO.getTimelimitedInfosCount(timelimitedInfoReqDTO);
            dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
            dataGrid.setRows(timelimitedInfoResDTOList);
        } catch (Exception e) {
            logger.error("messageId{}:执行方法【getTimelimitedInfosForPage】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }
    
    @Override
    public String updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId){
    	// 0.成功,1.参数为空,2.活动编码为空,3.上下架为空,4.上下架状态不正确,5.秒杀活动不存在,6.秒杀活动已经上架,
    	// 7.下架状态的秒杀商品库存小于1,8.秒杀开始时间小于或等于当前时间,9.秒杀结束时间小于或等于当前时间,10.秒杀开始时间大于或等于结束时间,11.活动已经处于下架状态
    	//-1 系统异常
    	String status = TimelimitedConstants.UPDOWN_SHELVES_STATUS_SUCCESS;//0.成功
    	
    	try {
    		
  		if (null == timelimitedInfoReqDTO) {
  			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_1;//1.参数为空
		}
		
  		String promotionId = timelimitedInfoReqDTO.getPromotionId();
    	String showStatus = timelimitedInfoReqDTO.getShowStatus();
    	
		if (null == promotionId || "".equals(promotionId.trim())) {
			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_2;//2.活动编码为空
		}
		
		if (null == showStatus || "".equals(showStatus.trim())) {
			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_3;//3.上下架为空
		}
		
		boolean isShowStatus= showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.VALID.key()) || showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.INVALID.key());
		if(!isShowStatus){
			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_4; //4.上下架状态不正确
		}
    	
    	PromotionInfoDTO promotionInfoDTO = promotionInfoDAO.queryById(promotionId);
    	if(null == promotionInfoDTO){
    		return TimelimitedConstants.UPDOWN_SHELVES_STATUS_5; //5.秒杀活动不存在
    	}
    	
    	if(showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.VALID.key())){//上架
    		if(promotionInfoDTO.getShowStatus().equals(showStatus)){//活动已经处于上架状态
    			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_6; // 6.秒杀活动已经上架
    		}
    		
    		 // Redis真实秒杀商品数量
    		 String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
    		 String remainCountStr = promotionRedisDB.getHash(timelimitedResultKey, RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT);
    		 if(!StringUtils.isNumeric(remainCountStr)){
    			 return TimelimitedConstants.UPDOWN_SHELVES_STATUS_7; // 7.下架状态的秒杀商品库存小于1
    		 }
    		 if(Integer.valueOf(remainCountStr) < 1){
    			 return TimelimitedConstants.UPDOWN_SHELVES_STATUS_7; // 7.下架状态的秒杀商品库存小于1
    		 }
    		 
    		//秒杀开始时间和结束时间均大于当前时间，且开始时间早于结束时间；
    		//秒杀开始时间
    		Date effectiveTime = promotionInfoDTO.getEffectiveTime();
    		//秒杀结束时间
    		Date invalidTime = promotionInfoDTO.getEffectiveTime();
    		
    		Calendar calender = Calendar.getInstance();
    		Date currentTime = calender.getTime();//当前时间
    		if(effectiveTime.getTime() <= currentTime.getTime()){//秒杀开始时间 <= 当前时间
    			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_8; //8.秒杀开始时间小于或等于当前时间
    		}
    		if(invalidTime.getTime() <= currentTime.getTime()){//秒杀结束时间 <= 当前时间
    			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_9; //9.秒杀结束时间小于或等于当前时间
    		}
    		if(effectiveTime.getTime() >= invalidTime.getTime()){//秒杀开始时间 >= 结束时间
    			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_10; //10.秒杀开始时间大于或等于结束时间
    		}
    	}else{//下架
    		if(promotionInfoDTO.getShowStatus().equals(showStatus)){//活动已经处于下架状态
    			return TimelimitedConstants.UPDOWN_SHELVES_STATUS_11; //11.活动已经处于下架状态
    		}
    	}
    	
    	PromotionValidDTO promotionValidDTO = new PromotionValidDTO();
		promotionValidDTO.setPromotionId(promotionId);
		promotionValidDTO.setShowStatus(showStatus);
		promotionValidDTO.setOperatorId(timelimitedInfoReqDTO.getModifyId());
		promotionValidDTO.setOperatorName(timelimitedInfoReqDTO.getModifyName());
		promotionInfoDAO.upDownShelvesTimelimitedInfo(promotionValidDTO);
		
         } catch (Exception e) {
        	 status = TimelimitedConstants.UPDOWN_SHELVES_STATUS_ERROR;//-1 系统异常
             logger.error("messageId{}:执行方法【updateShowStatusByPromotionId】报错：{}", messageId, e.toString());
             throw new RuntimeException(e);
         }
    	
    	return status;
    }

    /**
     * 批量添加秒杀商品图片
     *
     * @param timelimitedSkuDescribeList
     */
    private String addTimelimitedSkuPictureList(TimelimitedInfoReqDTO timelimitedInfoReqDTO, Date currentTime) {

    	String firstPictureUrl = null;
        // 添加商品活动图片
        List<TimelimitedSkuPictureReqDTO> timelimitedSkuDescribeList = timelimitedInfoReqDTO.getTimelimitedSkuPictureReqDTOList();
        if (null != timelimitedSkuDescribeList && timelimitedSkuDescribeList.size() > 0) {
            TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = null;
            for (int i = 0; i < timelimitedSkuDescribeList.size(); i++) {
                timelimitedSkuPictureReqDTO = timelimitedSkuDescribeList.get(i);
                timelimitedSkuPictureReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
                timelimitedSkuPictureReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
                // 由客户端设置（改）
//                if (i == 0) {// 第一张图设置为主图
//                    timelimitedSkuPictureReqDTO.setIsFirst(Boolean.TRUE);
//                    firstPictureUrl = timelimitedSkuPictureReqDTO.getPictureUrl();
//                } else {
//                    timelimitedSkuPictureReqDTO.setIsFirst(Boolean.FALSE);
//                }
                timelimitedSkuPictureReqDTO.setSortNum(i + 1);
                timelimitedSkuPictureReqDTO.setDeleteFlag(Boolean.FALSE);
                timelimitedSkuPictureReqDTO.setCreateId(timelimitedInfoReqDTO.getModifyId());
                timelimitedSkuPictureReqDTO.setCreateName(timelimitedInfoReqDTO.getModifyName());
                timelimitedSkuPictureReqDTO.setCreateTime(currentTime);
                timelimitedSkuPictureReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
                timelimitedSkuPictureReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
                timelimitedSkuPictureReqDTO.setModifyTime(currentTime);
                timelimitedSkuPictureDAO.insert(timelimitedSkuPictureReqDTO);
            }
        }
        
        return firstPictureUrl;//返回商品主图
    }

    /**
     * 批量添加添加商品详情图片
     *
     * @param timelimitedInfoReqDTO
     * @param currentTime
     */
    private void addTimelimitedSkuDescribeList(TimelimitedInfoReqDTO timelimitedInfoReqDTO, Date currentTime) {

        List<TimelimitedSkuDescribeReqDTO> timelimitedSkuDescribeReqDTOList =
                timelimitedInfoReqDTO.getTimelimitedSkuDescribeReqDTOList();
        if (null != timelimitedSkuDescribeReqDTOList && timelimitedSkuDescribeReqDTOList.size() > 0) {
            TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = null;
            for (int i = 0; i < timelimitedSkuDescribeReqDTOList.size(); i++) {
                timelimitedSkuDescribeReqDTO = timelimitedSkuDescribeReqDTOList.get(i);
                timelimitedSkuDescribeReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
                timelimitedSkuDescribeReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
                timelimitedSkuDescribeReqDTO.setDeleteFlag(Boolean.FALSE);
                timelimitedSkuDescribeReqDTO.setPictureUrl(timelimitedSkuDescribeReqDTO.getPictureUrl());
                timelimitedSkuDescribeReqDTO.setSortNum(i + 1);
                timelimitedSkuDescribeReqDTO.setCreateId(timelimitedInfoReqDTO.getModifyId());
                timelimitedSkuDescribeReqDTO.setCreateName(timelimitedInfoReqDTO.getModifyName());
                timelimitedSkuDescribeReqDTO.setCreateTime(currentTime);
                timelimitedSkuDescribeReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
                timelimitedSkuDescribeReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
                timelimitedSkuDescribeReqDTO.setModifyTime(currentTime);
                timelimitedSkuDescribeDAO.insert(timelimitedSkuDescribeReqDTO);
            }
        }

    }
    
    /**
     * 处理促销活动供应商规则详情
     * @param timelimitedInfoReqDTO
     * @param currentTime
     */
    private void handlePromotionSellerDetail(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String methodType) {
    	
    	String promotionId = timelimitedInfoReqDTO.getPromotionId();

        PromotionSellerRuleDTO psr = timelimitedInfoReqDTO.getSellerRuleDTO();
        if (psr != null) {
        	
        	if(TimelimitedConstants.SELLERDETAIL_OPERATETYPE_ADD.equals(methodType)){
                psr.setPromotionId(promotionId);
                psr.setDeleteFlag(YesNoEnum.NO.getValue());
                psr.setCreateId(timelimitedInfoReqDTO.getCreateId());
                psr.setCreateName(timelimitedInfoReqDTO.getCreateName());
                psr.setModifyId(timelimitedInfoReqDTO.getCreateId());
                psr.setModifyName(timelimitedInfoReqDTO.getCreateName());
                promotionSellerRuleDAO.add(psr);
        	}
        	
            List<PromotionSellerDetailDTO> sellerlist = psr.getSellerDetailList();
            if (null != sellerlist && sellerlist.size() > 0) {
                for (PromotionSellerDetailDTO psd : sellerlist) {
                	 psd.setPromotionId(promotionId);
                	//操作类型 (0 新增 1删除 2 修改）
                	String type = psd.getOperateType();
                	if(TimelimitedConstants.SELLERDETAIL_OPERATETYPE_ADD.equals(type)){//新增
                        psd.setCreateId(timelimitedInfoReqDTO.getCreateId());
                        psd.setCreateName(timelimitedInfoReqDTO.getCreateName());
                        psd.setModifyId(timelimitedInfoReqDTO.getCreateId());
                        psd.setModifyName(timelimitedInfoReqDTO.getCreateName());
                        psd.setDeleteFlag(YesNoEnum.NO.getValue());
                        promotionSellerDetailDAO.add(psd);
                	}else if(TimelimitedConstants.SELLERDETAIL_OPERATETYPE_DELETE.equals(type)){//删除
                        promotionSellerDetailDAO.deleteTimelimitedSellerDetail(psd);
                	}else if(TimelimitedConstants.SELLERDETAIL_OPERATETYPE_UPDATE.equals(type)){//修改
                        psd.setModifyId(timelimitedInfoReqDTO.getCreateId());
                        psd.setModifyName(timelimitedInfoReqDTO.getCreateName());
                        promotionSellerDetailDAO.updateTimelimitedSellerDetail(psd);
                	}
 
                }
            }
        }

    }

    /**
     * 添加秒杀活动履历
     *
     * @param promotionExtendInfo
     * @param timelimitedInfoReqDTO
     */
    private void addPromotionStatusHistory(PromotionExtendInfoDTO promotionExtendInfo,
            TimelimitedInfoReqDTO timelimitedInfoReqDTO) {

        // 状态履历   状态 1：活动未开始，2：活动进行中，3：活动已结束，9：已删除
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        historyDTO.setPromotionId(promotionExtendInfo.getPromotionId());
        historyDTO.setPromotionStatus(
                dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, promotionExtendInfo.getStatus()));
        historyDTO.setPromotionStatusText(
                dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, promotionExtendInfo.getStatus()));
        historyDTO.setCreateId(timelimitedInfoReqDTO.getModifyId());
        historyDTO.setCreateName(timelimitedInfoReqDTO.getModifyName());
        promotionStatusHistoryDAO.add(historyDTO);

        // 促销活动展示状态履历   状态   1：待审核，2：审核通过，3：审核被驳回，4：启用，5：不启用
        historyDTO = new PromotionStatusHistoryDTO();
        historyDTO.setPromotionId(promotionExtendInfo.getPromotionId());
        historyDTO.setPromotionStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, promotionExtendInfo.getShowStatus()));
        historyDTO.setPromotionStatusText(dictionary
                .getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, promotionExtendInfo.getShowStatus()));
        historyDTO.setCreateId(timelimitedInfoReqDTO.getModifyId());
        historyDTO.setCreateName(timelimitedInfoReqDTO.getModifyName());
        promotionStatusHistoryDAO.add(historyDTO);
    }

    @Override
    public void setTimelimitedInfo2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception {
        if (timelimitedInfoResDTO != null) {
            String promotionId = timelimitedInfoResDTO.getPromotionId();
            String jsonObj = JSON.toJSONString(timelimitedInfoResDTO);
            promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId, jsonObj);
            // 设置秒杀活动具体数量
            this.addTimelimitedResult2Redis(timelimitedInfoResDTO);
        }
    }

    /**
     * 保存秒杀活动信息进Redis
     *
     * @param timelimitedInfo
     */
    private void addTimelimitedResult2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String promotionId = timelimitedInfoResDTO.getPromotionId();
        String timelimitedResultKey = RedisConst.PROMOTION_REDIS_TIMELIMITED_RESULT + "_" + promotionId;
        String timelimitedResultStr = promotionRedisDB.get(timelimitedResultKey);
        if (StringUtils.isNotBlank(timelimitedResultStr)) {
            promotionRedisDB.del(timelimitedResultKey);
        }
        resultMap.put(RedisConst.PROMOTION_REDIS_TIMELIMITED_TOTAL_COUNT,
                String.valueOf(timelimitedInfoResDTO.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_REMAIN_COUNT,
                String.valueOf(timelimitedInfoResDTO.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.PROMOTION_REDIS_TIMELIMITED_SHOW_ACTOR_COUNT, "0");
        resultMap.put(RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_REMAIN_COUNT,
                String.valueOf(timelimitedInfoResDTO.getTimelimitedSkuCount()));
        resultMap.put(RedisConst.PROMOTION_REDIS_TIMELIMITED_REAL_ACTOR_COUNT, "0");
        promotionRedisDB.setHash(timelimitedResultKey, resultMap);
    }

    @Override
    public void setTimelimitedReserveQueue(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception {
        String promotionId = timelimitedInfoResDTO.getPromotionId();
        String timeLimitedQueueKey = RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_QUEUE + "_" + promotionId;
        Long length = promotionRedisDB.getLlen(timeLimitedQueueKey);
        if (length > 0) {
            promotionRedisDB.del(timeLimitedQueueKey);
        }
        int timelimitedSkuCount = timelimitedInfoResDTO.getTimelimitedSkuCount();
        for (int i = 0; i < timelimitedSkuCount; i++) {
            promotionRedisDB.rpush(timeLimitedQueueKey, promotionId);
        }
    }

    @Override
    public TimelimitedInfoResDTO getTimelimitedInfo(String promotionId) throws Exception {
        String timelimitedStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
        if (StringUtils.isBlank(timelimitedStr)) {
            throw new PromotionCenterBusinessException(PromotionCenterConst.PROMOTION_NOT_EXIST, "查询不到该秒杀活动");
        }
        TimelimitedInfoResDTO timelimitedInfo = JSON.parseObject(timelimitedStr, TimelimitedInfoResDTO.class);
        return timelimitedInfo;
    }

    @Override
    public void saveOrUpdateTimelimitedOperlog(BuyerUseTimelimitedLogDMO buyerUseTimelimitedLogDMO) {
        String promotionId = buyerUseTimelimitedLogDMO.getPromotionId();
        String buyerCode = buyerUseTimelimitedLogDMO.getBuyerCode();
        String key = buyerCode + "&" + promotionId;
        promotionRedisDB.setHash(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_USELOG, key,
                JSON.toJSONString(buyerUseTimelimitedLogDMO));
        promotionRedisDB.tailPush(RedisConst.PROMOTION_REDIS_BUYER_TIMELIMITED_NEED_SAVE_USELOG,
                JSON.toJSONString(buyerUseTimelimitedLogDMO));
    }

}
