
package cn.htd.marketcenter.task;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.common.utils.ExceptionUtils;
import cn.htd.marketcenter.dao.BuyerCouponInfoDAO;
import cn.htd.marketcenter.domain.BuyerCouponCondition;
import cn.htd.marketcenter.dto.BuyerCouponInfoDTO;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 根据优惠券有效时间更新会员优惠券的状态 清除过期优惠券
 */
public class UpdateExpiredBuyerCouponScheduleTask implements IScheduleTaskDealMulti<BuyerCouponInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(UpdateExpiredBuyerCouponScheduleTask.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private BuyerCouponInfoDAO buyerCouponInfoDAO;

	@Override
	public Comparator<BuyerCouponInfoDTO> getComparator() {
		return new Comparator<BuyerCouponInfoDTO>() {
			public int compare(BuyerCouponInfoDTO o1, BuyerCouponInfoDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	/**
	 * 根据条件,查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskQueueList
	 *            当前调度服务器,分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BuyerCouponInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}][{}][{}][{}]", "UpdateExpiredBuyerCouponScheduleTask-selectTasks",
				"taskParameter:" + taskParameter, "ownSign:" + ownSign, "taskQueueNum:" + taskQueueNum,
				JSONObject.toJSONString(taskQueueList), "eachFetchDataNum:" + eachFetchDataNum);
		BuyerCouponCondition condition = new BuyerCouponCondition();
		Pager<BuyerCouponInfoDTO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<BuyerCouponInfoDTO> buyerCouponInfoDTOList = null;

		if (eachFetchDataNum > 0) {
			pager = new Pager<BuyerCouponInfoDTO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskQueueList != null && taskQueueList.size() > 0) {
				for (TaskItemDefine taskItem : taskQueueList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS,
						DictionaryConst.OPT_COUPON_STATUS_UNUSED));
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				buyerCouponInfoDTOList = buyerCouponInfoDAO.queryBuyerCoupon4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateExpiredBuyerCouponScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateExpiredBuyerCouponScheduleTask-selectTasks",
					JSONObject.toJSONString(buyerCouponInfoDTOList));
		}
		return buyerCouponInfoDTOList;
	}

	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组,只能传递OBJECT[]
	 * 
	 * @param tasks
	 *            任务数组
	 * @param ownSign
	 *            当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(BuyerCouponInfoDTO[] tasks, String ownSign) throws Exception {
		logger.info("\n 方法:[{}],入参:[{}][{}]", "UpdateExpiredBuyerCouponScheduleTask-execute",
				JSONObject.toJSONString(tasks), "ownSign:" + ownSign);
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				for (BuyerCouponInfoDTO buyerCouponInfoDTO : tasks) {
					updateExpiredBuyerCouponStatus(buyerCouponInfoDTO);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "UpdateExpiredBuyerCouponScheduleTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "UpdateExpiredBuyerCouponScheduleTask-execute",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 更新过期优惠券状态
	 * 
	 * @param buyerCouponInfoDTO
	 * @throws Exception
	 */
	public void updateExpiredBuyerCouponStatus(BuyerCouponInfoDTO buyerCouponInfoDTO) throws Exception {
		BuyerCouponInfoDTO updateTargetInfo = buyerCouponInfoDAO.queryById(buyerCouponInfoDTO.getId());
		if (dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS, DictionaryConst.OPT_COUPON_STATUS_UNUSED)
				.equals(updateTargetInfo.getStatus())) {
			buyerCouponInfoDTO.setCouponLeftAmount(null);
			buyerCouponInfoDTO.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_COUPON_STATUS,
					DictionaryConst.OPT_COUPON_STATUS_EXPIRE));
			buyerCouponInfoDTO.setModifyId(0L);
			buyerCouponInfoDTO.setModifyName("sys");
			buyerCouponInfoDAO.updateBuyerCouponUseInfo(buyerCouponInfoDTO);
		}
	}
}
