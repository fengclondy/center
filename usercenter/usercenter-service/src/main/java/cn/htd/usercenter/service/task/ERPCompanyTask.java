/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	HTDUserGradeDailyTask.java
 * Author:   	jiangt
 * Date:     	2017年01月12日
 * Description: 会员等级日计算
 * History: 	
 * <author>		<time>      	<version>	<desc>
 */
package cn.htd.usercenter.service.task;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.com.weaver.Htd_userlist.webservices.Htd_userlistPortTypeProxy;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.common.constant.StaticProperty;
import cn.htd.usercenter.dto.HTDCompanyDTO;
import cn.htd.usercenter.service.UserSyncService;

public class ERPCompanyTask implements IScheduleTaskDealMulti<Long> {

	protected static transient Logger logger = LoggerFactory.getLogger(ERPCompanyTask.class);

	public static final String TYPE_BRANCH = "1";
	public static final String STATUS_OK = "1";
	static HashMap<String, String> areamap = new HashMap<String, String>();

	@Resource
	private UserSyncService userSyncService;

	@Override
	public Comparator<Long> getComparator() {
		return new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return o1.compareTo(o2);
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
	public List<Long> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "ERPCompanyTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));

		logger.info("开始同步ERP公司信息 ...");
		ArrayList<Long> userFinanceHisList = new ArrayList<Long>();
		try {
			HttpClient client = new HttpClient();
			InputStream inputStream = null;

			PostMethod postMethod = new PostMethod(StaticProperty.ERP_URL);
			postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			// 根据参数生成调用请求体
			NameValuePair[] params = new NameValuePair[2];
			params[0] = new NameValuePair("UserName", StaticProperty.ERP_USERNAME);
			params[1] = new NameValuePair("PassWord", StaticProperty.ERP_PASSWORD);
			postMethod.setRequestBody(params);

			// 调用接口
			client.executeMethod(postMethod);

			int statusCode = postMethod.getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				inputStream = postMethod.getResponseBodyAsStream();
			} else {
				logger.error(MessageFormat.format("通信错误(状态编码:{0})", statusCode));
				return userFinanceHisList;
			}

			Map<String, Object> map = JSON.parse(new InputStreamReader(inputStream, "UTF-8"), Map.class);
			String state = String.valueOf(map.get("state"));
			if (!STATUS_OK.equals(state)) {
				logger.error(MessageFormat.format("取得ERP组织数据出错({0})", map.get("error")));
				return userFinanceHisList;
			}
			
			Htd_userlistPortTypeProxy proxy = new Htd_userlistPortTypeProxy();
			try {
				String getdata = proxy.getdata();
				logger.info("OA Htd_userlist:"+JSONObject.toJSONString(getdata));
				JSONObject msgjson = JSONObject.parseObject(getdata);
				JSONArray s = (JSONArray) msgjson.get("data");
				for (int i = 0; i < s.size(); i++) {
					JSONObject json = (JSONObject) s.get(i);
					areamap.put(json.getString("subcompanycode"), json.getString("area"));
				}
				//System.out.println(areamap);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Map<String, Object> data = (Map<String, Object>) map.get("data");

			// 同步总部公司数据
			syncCompanyData(data, "");

			logger.info("同步ERP公司信息完成。");


			
			
		} catch (Exception e) {
			logger.error("【同步ERP组织数据】出现异常！", e);
		}

		return userFinanceHisList;
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
	public boolean execute(Long[] tasks, String ownSign) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 同步一家公司及其子公司数据
	 * 
	 * @param data
	 *            公司数据
	 * @param parentCode
	 *            所属公司编码
	 */
	@SuppressWarnings("unchecked")
	private void syncCompanyData(Map<String, Object> data, String parentCode) {
		String code = (String) data.get("code");
		String name = (String) data.get("name");
		String type = (String) data.get("type");

		HTDCompanyDTO dto = new HTDCompanyDTO();
		dto.setCompanyId(code);
		// 分部的时候，过滤掉开头多余的部分（"1：全资分部" > "全资分部")
		if (TYPE_BRANCH.equals(type) && name.indexOf("：") > 0) {
			dto.setName(name.substring(name.indexOf("：") + 1));
		} else {
			dto.setName(name);
		}
		dto.setType(type);
		dto.setParentCompanyId(parentCode);
		// TODO 地址取得方法待定
		dto.setAddressProvince("");
		dto.setAddressCity("");
		dto.setAddressDistrict("");
		dto.setAddressTown("");
		dto.setArea(areamap.get(code));

		ExecuteResult<Boolean> result = userSyncService.syncHTDCompany(dto);
		if (!result.isSuccess()) {
			logger.error(MessageFormat.format("同步ERP公司({0})信息出错({1})", code, result.getErrorMessages().get(0)));
		} else {
			if (result.getResult() != null && result.getResult().booleanValue()) {
				logger.info(MessageFormat.format("同步ERP公司({0})信息成功", code));
			} else {
				logger.error(MessageFormat.format("同步ERP公司({0})信息出错({1})", code, result.getResultMessage()));
			}
		}

		List<Map<String, Object>> subCompanys = (List<Map<String, Object>>) data.get("subCompanys");
		if (subCompanys != null) {
			for (Map<String, Object> subCompany : subCompanys) {
				syncCompanyData(subCompany, code);
			}
		}
	}
	
	public static void main(String[] args) {
//		
//		ALTER TABLE `htd_company`
//		ADD COLUMN `area` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '所属区域' AFTER `address_town`;
		Htd_userlistPortTypeProxy proxy = new Htd_userlistPortTypeProxy("http://oa.htd.cn//services/Htd_userlist");
		try {
			String getdata = proxy.getdata();
			JSONObject msgjson = JSONObject.parseObject(getdata);
			JSONArray s = (JSONArray) msgjson.get("data");
			for (int i = 0; i < s.size(); i++) {
				JSONObject json = (JSONObject) s.get(i);
				areamap.put(json.getString("subcompanycode"), json.getString("area"));
			}
			System.out.println(JSONObject.toJSONString(getdata));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ERPCompanyTask task = new ERPCompanyTask();
//		try {
//			task.selectTasks("", "", 0, null, 0);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
