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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.membercenter.common.constant.StaticProperty;
import cn.htd.membercenter.common.util.HttpUtils;
import cn.htd.membercenter.dao.MemberTaskDAO;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.dto.MemberDownCondition;
import cn.htd.membercenter.dto.MemberDownDTO;
import cn.htd.membercenter.dto.SalemanDTO;

public class MemberCustomerManagerTask implements IScheduleTaskDealMulti<MemberBaseInfoDTO> {

	protected static transient Logger logger = LoggerFactory.getLogger(MemberCustomerManagerTask.class);

	public static final String REDIS_CUSTOMER_MANAGER_INFO = "REDIS_CUSTOMER_MANAGER_INFO";
	public static final String REDIS_CUSTOMER_MANAGER_CODE = "REDIS_CUSTOMER_MANAGER_CODE";
	public static final String REDIS_CUSTOMER_MANAGER_NAME = "REDIS_CUSTOMER_MANAGER_NAME";
	@Resource
	private MemberTaskDAO memberTaskDAO;
	@Resource
	private RedisDB redisDB;

	@Override
	public Comparator<MemberBaseInfoDTO> getComparator() {
		return new Comparator<MemberBaseInfoDTO>() {
			@Override
			public int compare(MemberBaseInfoDTO o1, MemberBaseInfoDTO o2) {
				String id1 = o1.getMemberCode();
				String id2 = o2.getMemberCode();
				return id1.compareTo(id2);
			}
		};
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<MemberBaseInfoDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCustomerManagerask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));
		MemberDownCondition condition = new MemberDownCondition();
		List<String> taskIdList = new ArrayList<String>();
		List<MemberBaseInfoDTO> memberBaseInfoList = null;
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
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				memberBaseInfoList = memberTaskDAO.selectCompanyInfo(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "MemberCustomerManagerTask-selectTasks", e);
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCustomerManagerTask-selectTasks",
					JSONObject.toJSONString(memberBaseInfoList));
		}
		return memberBaseInfoList;
	}

	@Override
	public boolean execute(MemberBaseInfoDTO[] tasks, String ownSign) throws Exception {
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				Map<String, String> resultMap = null;
				resultMap = redisDB.getHashOperations(REDIS_CUSTOMER_MANAGER_INFO);
				if (resultMap == null || resultMap.isEmpty()) {
					resultMap = new HashMap<String, String>();
				}
				for (MemberBaseInfoDTO memberBaseInfo : tasks) {
					List<SalemanDTO> salesMans = getManagerList(memberBaseInfo.getCompanyCode()).getResult();
					if (null != salesMans && salesMans.size() > 0) {
						for (SalemanDTO saleman : salesMans) {
							String key = REDIS_CUSTOMER_MANAGER_CODE + "_" + saleman.getCustomerManagerCode();
							String value = REDIS_CUSTOMER_MANAGER_NAME + "_" + saleman.getCustomerManagerCode();
							if (resultMap.get(key) != null
									&& resultMap.get(key).contains(saleman.getCustomerManagerCode())
									&& resultMap.get(value).contains(saleman.getCustomerManagerName())) {
								continue;
							} else {
								if (StringUtils.isNotEmpty(saleman.getCustomerManagerName())) {
									resultMap.put(key, saleman.getCustomerManagerCode());
									resultMap.put(value, saleman.getCustomerManagerName());
								}
							}
						}
					}
				}
				if (resultMap != null && !resultMap.isEmpty()) {
					redisDB.setHash(REDIS_CUSTOMER_MANAGER_INFO, resultMap);
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "MemberCustomerManagerTask-execute", e);
		}
		return result;
	}

	public ExecuteResult<List<SalemanDTO>> getManagerList(String memberCode) {
		ExecuteResult<List<SalemanDTO>> rs = new ExecuteResult<List<SalemanDTO>>();
		List<SalemanDTO> list = new ArrayList<SalemanDTO>();
		try {
			String tokenResponse = HttpUtils.httpGet(StaticProperty.TOKEN_URL);
			JSONObject tokenResponseJSON = JSONObject.parseObject(tokenResponse);
			StringBuffer buffer = new StringBuffer();
			buffer.append("?");
			if (StringUtils.isNotEmpty(memberCode)) {
				buffer.append("supplierCode=" + memberCode);
				String msg = "";
				if (tokenResponseJSON.getInteger("code") == 1) {
					buffer.append("&token=" + tokenResponseJSON.getString("data"));
					String urlParam = buffer.toString();
					msg = HttpUtils.httpGet(StaticProperty.MIDDLEWAREERP_URL + "member/getSaleman" + urlParam);
					logger.info(msg);
				}
				JSONObject msgjson = JSONObject.parseObject(msg);
				if (tokenResponseJSON.getInteger("code") == 1) {
					JSONArray s = (JSONArray) msgjson.get("data");
					String code = "";
					String name = "";
					for (int i = 0; i < s.size(); i++) {
						JSONObject json = (JSONObject) s.get(i);
						code = json.getString("salemanCode");
						name = json.getString("salemanName");
						SalemanDTO e = new SalemanDTO();
						e.setCustomerManagerCode(code);
						e.setCustomerManagerName(name);
						list.add(e);
					}
					rs.setResult(list);
				}
			}
		} catch (Exception e) {
			logger.error("getManagerList error" + e);
			rs.addErrorMessage("error");
		}
		return rs;
	}

}
