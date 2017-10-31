package cn.htd.membercenter.service.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.common.constant.GlobalConstant;
import cn.htd.membercenter.dao.BelongRelationshipDAO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.SellerBelongRelationDTO;

public class LoadBelongRelationInRedisTask implements IScheduleTaskDealMulti<SellerBelongRelationDTO> {
	protected static transient Logger logger = LoggerFactory.getLogger(LoadBelongRelationInRedisTask.class);

	@Resource
	private RedisDB redisDB;

	@Resource
	BelongRelationshipDAO belongRelationshipDao;

	@Override
	public Comparator<SellerBelongRelationDTO> getComparator() {
		return new Comparator<SellerBelongRelationDTO>() {
			@Override
			public int compare(SellerBelongRelationDTO o1, SellerBelongRelationDTO o2) {
				Long id1 = o1.getMemberId();
				Long id2 = o2.getMemberId();
				return id1.compareTo(id2);
			}
		};
	}

	/**
	 * 根据条件，查询当前调度服务器可处理的任务
	 * 
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务类型的任务队列数量
	 * @param taskQueueList
	 *            当前调度服务器，分配到的可处理队列
	 * @param eachFetchDataNum
	 *            每次获取数据的数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SellerBelongRelationDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "LoadBelongRelationInRedisTask", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		Date lasttime = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (redisDB.existsHash(GlobalConstant.BELONG_RELATION_INFO, "LASTTIME")) {
			String lasttimeStr = redisDB.getHash(GlobalConstant.BELONG_RELATION_INFO, "LASTTIME");
			try {
				lasttime = format.parse(lasttimeStr);
				Calendar ca = Calendar.getInstance();
				ca.setTime(lasttime);
				ca.add(Calendar.MINUTE, -30);
				lasttime = ca.getTime();
			} catch (Exception e) {
			}
		}

		MemberDownCondition condition = new MemberDownCondition();
		List<SellerBelongRelationDTO> brList = new ArrayList<SellerBelongRelationDTO>();
		List<String> taskIdList = new ArrayList<String>();

		@SuppressWarnings("rawtypes")
		Pager pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<MemberDownDTO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}
		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setLastDate(lasttime);
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				brList = belongRelationshipDao.queryBelongRelation4Task(condition, pager);
			}
			if (brList == null || brList.size() == 0) {
				redisDB.setHash(GlobalConstant.BELONG_RELATION_INFO, "LASTTIME", format.format(new Date()));
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "LoadBelongRelationInRedisTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "LoadBelongRelationInRedisTask-selectTasks",
					JSONObject.toJSONString(brList));
		}
		return brList;
	}

	@Override
	public boolean execute(SellerBelongRelationDTO[] tasks, String ownSign) throws Exception {
		int len = tasks.length;
		SellerBelongRelationDTO sellerBelongRelationDTO = null;
		for (int i = 0; i < len; i++) {
			sellerBelongRelationDTO = tasks[i];
			redisDB.setHash(GlobalConstant.BELONG_RELATION_INFO, sellerBelongRelationDTO.getMemberCode(),
					JSON.toJSONString(sellerBelongRelationDTO));
		}
		return true;
	}

}
