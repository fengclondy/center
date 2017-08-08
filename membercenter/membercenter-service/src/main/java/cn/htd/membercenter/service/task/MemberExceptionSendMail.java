/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	MemberDownErpTask.java
 * Author:   	bs.xu
 * Date:     	2016年12月14日
 * Description: 会员下行
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * bs.xu		2016年12月14日	1.0			创建
 */
package cn.htd.membercenter.service.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.Pager;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.MemberErpDTO;

public class MemberExceptionSendMail implements IScheduleTaskDealMulti<MemberErpDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(DataSysToYijifuTask.class);

	@Resource
	private MemberTaskDAO memberTaskDAO;

	@Autowired
	private SendSmsEmailService sendSmsEmailService;

	@Override
	public Comparator<MemberErpDTO> getComparator() {
		return new Comparator<MemberErpDTO>() {
			@Override
			public int compare(MemberErpDTO o1, MemberErpDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
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
	public List<MemberErpDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberExceptionSendMail-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<MemberErpDTO> resMemberList = new ArrayList<MemberErpDTO>();
		@SuppressWarnings("rawtypes")
		Pager pager = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<MemberDownDTO>();
			pager.setPageOffset(0);
			int data = eachFetchDataNum / 3;
			pager.setRows(data);
		}
		try {
			if (taskItemList != null && taskItemList.size() > 0) {
				for (TaskItemDefine taskItem : taskItemList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				List<MemberErpDTO> erpList1 = memberTaskDAO.selectErpDownListType1(condition, pager);
				int size1 = erpList1.size();
				for (int i = 0; i < size1; i++) {
					erpList1.get(i).setErpDownType("1");
				}
				resMemberList.addAll(erpList1);

				List<MemberErpDTO> erpList2 = memberTaskDAO.selectErpDownListType2(condition, pager);
				int size2 = erpList2.size();
				for (int i = 0; i < size2; i++) {
					erpList2.get(i).setErpDownType("2");
				}
				resMemberList.addAll(erpList2);

				List<MemberErpDTO> erpList3 = memberTaskDAO.selectErpDownListType3(condition, pager);
				int size3 = erpList3.size();
				for (int i = 0; i < size3; i++) {
					erpList3.get(i).setErpDownType("3");
				}
				resMemberList.addAll(erpList3);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "MemberExceptionSendMail-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberExceptionSendMail-selectTasks",
					JSONObject.toJSONString(resMemberList));
		}
		return resMemberList;
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
	public boolean execute(MemberErpDTO[] tasks, String ownSign) throws Exception {
		logger.info("会员数据发邮件开始,邮件数据" + JSONObject.toJSONString(tasks));
		// 发送邮件
		MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
		mailWarnInDTO.setEmailTitle("会员下行异常告警邮件");
		mailWarnInDTO.setRecevierMail("error_member@htd.cn");
		List<MailWarnRow> rowList = Lists.newArrayList();
		MailWarnRow rowHead = new MailWarnRow();
		List<MailWarnColumn> mailWarnColumnList = Lists.newArrayList();
		MailWarnColumn mailWarnColumn_col1 = new MailWarnColumn();
		mailWarnColumn_col1.setIndex(1);
		mailWarnColumn_col1.setValue("会员编码");
		mailWarnColumnList.add(mailWarnColumn_col1);
		MailWarnColumn mailWarnColumn_col2 = new MailWarnColumn();
		mailWarnColumn_col2.setIndex(2);
		mailWarnColumnList.add(mailWarnColumn_col2);
		mailWarnColumn_col2.setValue("错误时间");
		MailWarnColumn mailWarnColumn_col3 = new MailWarnColumn();
		mailWarnColumn_col3.setIndex(3);
		mailWarnColumnList.add(mailWarnColumn_col3);
		mailWarnColumn_col3.setValue("下行状态");
		MailWarnColumn mailWarnColumn_col4 = new MailWarnColumn();
		mailWarnColumn_col4.setIndex(4);
		mailWarnColumn_col4.setValue("异常类型");
		mailWarnColumnList.add(mailWarnColumn_col4);
		MailWarnColumn mailWarnColumn_col5 = new MailWarnColumn();
		mailWarnColumn_col5.setIndex(5);
		mailWarnColumn_col5.setValue("异常原因");
		mailWarnColumnList.add(mailWarnColumn_col5);
		rowHead.setMailWarnColumnList(mailWarnColumnList);
		// 首行
		rowList.add(rowHead);

		int len = tasks.length;
		for (int i = 0; i < len; i++) {
			MemberErpDTO dto = tasks[i];
			MailWarnRow row = new MailWarnRow();
			List<MailWarnColumn> warnColList = Lists.newArrayList();
			// 1
			MailWarnColumn mailWarnColumn1 = new MailWarnColumn();
			mailWarnColumn1.setIndex(1);
			mailWarnColumn1.setValue(dto.getMemberCode());
			warnColList.add(mailWarnColumn1);

			// 2
			MailWarnColumn mailWarnColumn2 = new MailWarnColumn();
			mailWarnColumn2.setIndex(2);
			if (dto.getTime() != null) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				mailWarnColumn2.setValue(f.format(dto.getTime()));
			} else {
				mailWarnColumn2.setValue("");
			}
			warnColList.add(mailWarnColumn2);

			// 3
			MailWarnColumn mailWarnColumn3 = new MailWarnColumn();
			mailWarnColumn3.setIndex(3);
			if ("3".equals(dto.getErpStatus())) {
				mailWarnColumn3.setValue(("已进行下行操作,但还未下行到ERP"));
			} else if ("9".equals(dto.getErpStatus())) {
				mailWarnColumn3.setValue(("已进行下行失败"));
			}
			warnColList.add(mailWarnColumn3);

			// 4
			MailWarnColumn mailWarnColumn4 = new MailWarnColumn();
			mailWarnColumn4.setIndex(4);
			if ("1".equals(dto.getErpDownType())) {
				mailWarnColumn4.setValue("会员下行异常");
			} else if ("2".equals(dto.getErpDownType())) {
				mailWarnColumn4.setValue("单位往来关系下行异常");
			} else if ("3".equals(dto.getErpDownType())) {
				mailWarnColumn4.setValue("客商业务员下行异常");
			}
			warnColList.add(mailWarnColumn4);

			// 5
			MailWarnColumn mailWarnColumn5 = new MailWarnColumn();
			mailWarnColumn5.setIndex(6);
			mailWarnColumn5.setValue(dto.getSyncErrorMsg());
			warnColList.add(mailWarnColumn5);
			row.setMailWarnColumnList(warnColList);
			rowList.add(row);
		}

		mailWarnInDTO.setRowList(rowList);
		mailWarnInDTO.setSenderMail("it_warning@htd.cn");
		sendSmsEmailService.doSendEmailByTemplate(mailWarnInDTO);
		logger.info("会员数据发邮件结束,邮件数据" + JSONObject.toJSONString(tasks));
		return false;
	}
}
