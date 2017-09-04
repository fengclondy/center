package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.TimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuPictureDAO;
import cn.htd.promotion.cpc.biz.exception.PromotionCenterException;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuDescribeResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuPictureResDTO;

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

	@Override
	public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

		// 当前时间
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();

		try {
			// 添加秒杀商品
			timelimitedInfoReqDTO.setCreateTime(currentTime);
			timelimitedInfoReqDTO.setModifyTime(currentTime);
			timelimitedInfoDAO.insert(timelimitedInfoReqDTO);

			// 添加商品图片
			addTimelimitedSkuPictureList(timelimitedInfoReqDTO, currentTime);

			// 添加商品详情
			addTimelimitedSkuDescribe(timelimitedInfoReqDTO, currentTime);

			// 添加促销活动信息
			// PromotionInfoEditReqDTO PromotionInfoEditReqDTO =
			// timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
			// baseService.addPromotionInfo(PromotionInfoEditReqDTO);

		} catch (Exception e) {
			logger.error("messageId{}:执行方法【addTimelimitedInfo】报错：{}", messageId, e.toString());
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

		try {
			Calendar calendar = Calendar.getInstance();
			Date currentTime = calendar.getTime();

			// 修改秒杀商品
			timelimitedInfoReqDTO.setModifyTime(currentTime);
			timelimitedInfoDAO.updateByPrimaryKeySelective(timelimitedInfoReqDTO);

			// 伪删除商品活动的所有图片
			TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO_delete = new TimelimitedSkuPictureReqDTO();
			timelimitedSkuPictureReqDTO_delete.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
			timelimitedSkuPictureReqDTO_delete.setDeleteFlag(Boolean.TRUE);
			timelimitedSkuPictureReqDTO_delete.setModifyId(timelimitedInfoReqDTO.getModifyId());
			timelimitedSkuPictureReqDTO_delete.setModifyName(timelimitedInfoReqDTO.getModifyName());
			timelimitedSkuPictureReqDTO_delete.setModifyTime(currentTime);
			timelimitedSkuPictureDAO.pseudoDelete(timelimitedSkuPictureReqDTO_delete);

			// 添加商品活动图片
			addTimelimitedSkuPictureList(timelimitedInfoReqDTO, currentTime);

			// 修改商品详情
			updateTimelimitedSkuDescribe(timelimitedInfoReqDTO, currentTime);

			// //修改促销活动信息
			// PromotionInfoEditReqDTO PromotionInfoEditReqDTO =
			// timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
			// baseService.editPromotionInfo(PromotionInfoEditReqDTO);

		} catch (Exception e) {
			logger.error("messageId{}:执行方法【updateTimelimitedInfo】报错：{}", messageId, e.toString());
			throw new RuntimeException(e);
		}

	}

	@Override
	public TimelimitedInfoResDTO getSingleTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,
			String messageId) {

		TimelimitedInfoResDTO timelimitedInfoResDTO = null;

		try {

			String promotionId = timelimitedInfoReqDTO.getPromotionId();
			// 查询活动信息
			timelimitedInfoResDTO = timelimitedInfoDAO.selectByPromotionId(timelimitedInfoReqDTO.getPromotionId());
			// 查询图片信息
			List<TimelimitedSkuPictureResDTO> timelimitedSkuPictureResDTOList = timelimitedSkuPictureDAO
					.selectByPromotionId(promotionId);
			// 查询详情信息
			TimelimitedSkuDescribeResDTO timelimitedSkuDescribeResDTO = timelimitedSkuDescribeDAO
					.selectByPromotionId(promotionId);

			timelimitedInfoResDTO.setTimelimitedSkuPictureList(timelimitedSkuPictureResDTOList);
			timelimitedInfoResDTO.setTimelimitedSkuDescribe(timelimitedSkuDescribeResDTO);

			// //修改促销活动信息
			// PromotionInfoEditReqDTO PromotionInfoEditReqDTO =
			// timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
			// baseService.editPromotionInfo(PromotionInfoEditReqDTO);

		} catch (Exception e) {
			logger.error("messageId{}:执行方法【getSingleTimelimitedInfo】报错：{}", messageId, e.toString());
			throw new RuntimeException(e);
		}

		return timelimitedInfoResDTO;

	}

	@Override
	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {

		logger.info(
				"TimelimitedInfoServiceImpl--->getTimelimitedInfosForPage--->page:" + JSONObject.toJSONString(page));
		logger.info("TimelimitedInfoServiceImpl--->getTimelimitedInfosForPage--->timelimitedInfoReqDTO:"
				+ JSONObject.toJSONString(timelimitedInfoReqDTO));

		DataGrid<TimelimitedInfoResDTO> dataGrid = null;
		try {
			dataGrid = new DataGrid<TimelimitedInfoResDTO>();
			List<TimelimitedInfoResDTO> helpDocCategoryList = timelimitedInfoDAO.getTimelimitedInfosForPage(page,
					timelimitedInfoReqDTO);
			int count = timelimitedInfoDAO.getTimelimitedInfosCount(timelimitedInfoReqDTO);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(helpDocCategoryList);
		} catch (Exception e) {
			logger.error("messageId{}:执行方法【getTimelimitedInfosForPage】报错：{}", messageId, e.toString());
			throw new RuntimeException(e);
		}
		return dataGrid;
	}

	/**
	 * 批量添加秒杀商品图片
	 * 
	 * @param timelimitedSkuDescribeList
	 */
	private void addTimelimitedSkuPictureList(TimelimitedInfoReqDTO timelimitedInfoReqDTO, Date currentTime) {

		// 添加商品活动图片
		List<TimelimitedSkuPictureReqDTO> timelimitedSkuDescribeList = timelimitedInfoReqDTO
				.getTimelimitedSkuPictureReqDTOList();
		if (null != timelimitedSkuDescribeList && timelimitedSkuDescribeList.size() > 0) {
			TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = null;
			for (int i = 0; i < timelimitedSkuDescribeList.size(); i++) {
				timelimitedSkuPictureReqDTO = timelimitedSkuDescribeList.get(i);
				timelimitedSkuPictureReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
				timelimitedSkuPictureReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
				if (i == 0) {// 第一张图设置为主图
					timelimitedSkuPictureReqDTO.setIsFirst(Boolean.TRUE);
				} else {
					timelimitedSkuPictureReqDTO.setIsFirst(Boolean.FALSE);
				}
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
	}

	/**
	 * 添加商品详情
	 * 
	 * @param timelimitedInfoReqDTO
	 * @param currentTime
	 */
	public void addTimelimitedSkuDescribe(TimelimitedInfoReqDTO timelimitedInfoReqDTO, Date currentTime) {
		TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = timelimitedInfoReqDTO
				.getTimelimitedSkuDescribeReqDTO();

		timelimitedSkuDescribeReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
		timelimitedSkuDescribeReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
		timelimitedSkuDescribeReqDTO.setDeleteFlag(Boolean.FALSE);
		timelimitedSkuDescribeReqDTO.setCreateId(timelimitedInfoReqDTO.getCreateId());
		timelimitedSkuDescribeReqDTO.setCreateName(timelimitedInfoReqDTO.getCreateName());
		timelimitedSkuDescribeReqDTO.setCreateTime(currentTime);
		timelimitedSkuDescribeReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
		timelimitedSkuDescribeReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
		timelimitedSkuDescribeReqDTO.setModifyTime(currentTime);

		timelimitedSkuDescribeDAO.insert(timelimitedSkuDescribeReqDTO);
	}

	/**
	 * 修改商品详情
	 * 
	 * @param timelimitedInfoReqDTO
	 * @param currentTime
	 */
	public void updateTimelimitedSkuDescribe(TimelimitedInfoReqDTO timelimitedInfoReqDTO, Date currentTime) {
		TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = timelimitedInfoReqDTO
				.getTimelimitedSkuDescribeReqDTO();

		timelimitedSkuDescribeReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
		timelimitedSkuDescribeReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
		timelimitedSkuDescribeReqDTO.setModifyTime(currentTime);

		timelimitedSkuDescribeDAO.updateByPrimaryKeySelective(timelimitedSkuDescribeReqDTO);
	}

	@Override
	public void setTimelimitedInfo2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception {
		if (timelimitedInfoResDTO != null) {
			String promotionId = timelimitedInfoResDTO.getPromotionId();
			String jsonObj = JSON.toJSONString(timelimitedInfoResDTO);
			// 设置秒杀活动到redis 如果是update则删除原有活动插入新活动
			String timelimitedStr = promotionRedisDB.getHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
			if (StringUtils.isNotBlank(timelimitedStr)) {
				promotionRedisDB.delHash(RedisConst.PROMOTION_REDIS_TIMELIMITED, promotionId);
			}
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
			throw new PromotionCenterException(PromotionCenterConst.PROMOTION_NOT_EXIST, "查询不到该秒杀活动");
		}
		TimelimitedInfoResDTO timelimitedInfo = JSON.parseObject(timelimitedStr, TimelimitedInfoResDTO.class);
		return timelimitedInfo;
	}

}
