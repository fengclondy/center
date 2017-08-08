/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	HTDUserGradeDailyTask.java
 * Author:   	jiangt
 * Date:     	2017年01月12日
 * Description: 会员等级日计算
 * History: 	
 * <author>		<time>      	<version>	<desc>
 */
package cn.htd.membercenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.membercenter.dao.MemberGradeDAO;
import cn.htd.membercenter.dto.BuyerGradeInfoDTO;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.service.MemberGradeService;

public class HTDUserGradeYearlyTask implements IScheduleTaskDealMulti<MemberImportSuccInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(HTDUserGradeYearlyTask.class);

	@Resource
	private MemberGradeService memberGradeService;

	@Resource
	private MemberGradeDAO memberGradeDAO;

	@Override
	public Comparator<MemberImportSuccInfoDTO> getComparator() {
		return new Comparator<MemberImportSuccInfoDTO>() {
			@Override
			public int compare(MemberImportSuccInfoDTO o1, MemberImportSuccInfoDTO o2) {
				String id1 = o1.getMemberId();
				String id2 = o2.getMemberId();
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
	public List<MemberImportSuccInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "HTDUserGradeYearlyTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		int memberCnt = 0;
		List<MemberImportSuccInfoDTO> list = memberGradeDAO.getHTDAllMemberCnt();
		for (MemberImportSuccInfoDTO memberImportSuccInfoDTO : list) {
			BuyerGradeInfoDTO memberGradeModel = memberGradeDAO
					.getHTDMemberGrade(new Long(memberImportSuccInfoDTO.getMemberId()));
			if (memberGradeModel != null) {
				memberGradeService.upgradeHTDUserGradeYearly(memberImportSuccInfoDTO, memberGradeModel);
			}
		}
		//int onum = memberGradeDAO.getYearCnt();
		//if (onum == 0) {
			list = new ArrayList<MemberImportSuccInfoDTO>();
		//}
		return list;
	}

	/**
	 * 执行给定的任务数组。因为泛型不支持new 数组，只能传递OBJECT[]
	 * 
	 * @param tasks
	 *            任务数组
	 * @param ownSign
	 *            当前环境名称
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean execute(MemberImportSuccInfoDTO[] tasks, String ownSign) throws Exception {

//		int len = tasks.length;
//		MemberImportSuccInfoDTO memberImportSuccInfoDTO = null;
//		for (int i = 0; i < len; i++) {
//			memberImportSuccInfoDTO = tasks[i];
//
//			BuyerGradeInfoDTO memberGradeModel = memberGradeDAO
//					.getHTDMemberGrade(new Long(memberImportSuccInfoDTO.getMemberId()));
//			if (memberGradeModel != null) {
//				memberGradeService.upgradeHTDUserGradeYearly(memberImportSuccInfoDTO, memberGradeModel);
//			}
//		}
		return false;
	}
}
