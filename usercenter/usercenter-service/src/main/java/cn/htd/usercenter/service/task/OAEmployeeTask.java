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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.com.weaver.Htd_super.webservices.AnyType2AnyTypeMapEntry;
import cn.com.weaver.Htd_super.webservices.Htd_superServicePortTypeProxy;
import cn.htd.common.ExecuteResult;
import cn.htd.usercenter.dto.OAWorkerDTO;
import cn.htd.usercenter.service.UserSyncService;

public class OAEmployeeTask implements IScheduleTaskDealMulti<Long> {

	protected static transient Logger logger = LoggerFactory.getLogger(OAEmployeeTask.class);

	// 查询OA员工数据SQL
	//private static final String SQL_QUERY_OA_USER = "SELECT WORKCODE, LASTNAME, CASE WHEN FBXX01 IS NOT NULL THEN FBXX01 ELSE SUBCOMPANYCODE END SUBCOMPANYCODE, PASSWORD, EMAIL, MOBILE, SUPERIOR, ISCM, STATUS FROM HTD_ECOLOGY.V_HTD_SUPER ORDER BY WORKCODE ASC";

	// @Resource(name = "oaJdbcTemplate")
	// private JdbcTemplate template;

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
		logger.info("\n 方法[{}]，入参：[{}]", "OAEmployeeTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskItemList), JSONObject.toJSONString(eachFetchDataNum));

		logger.info("开始同步OA员工信息 ...");

//		ALTER TABLE `employee`
//		ADD COLUMN `gw` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '岗位' AFTER `last_updated_time`;
		// template.query(SQL_QUERY_OA_USER, new RowCallbackHandler() {
		// public void processRow(ResultSet rs) throws SQLException {
		// 调用webservices
		Htd_superServicePortTypeProxy proxy = new Htd_superServicePortTypeProxy();
		AnyType2AnyTypeMapEntry[][] getdata = proxy.getdata("");
		logger.info("OA Htd_superService:"+JSONObject.toJSONString(getdata));
		for (AnyType2AnyTypeMapEntry[] anyType2AnyTypeMap : getdata) {
			OAWorkerDTO workerDTO = new OAWorkerDTO();
			for (AnyType2AnyTypeMapEntry entry : anyType2AnyTypeMap) {
				if (entry.getKey().equals("workcode")) {
					workerDTO.setWorkCode(entry.getValue().toString());
				}
				if (entry.getKey().equals("superior")) {
					workerDTO.setSuperior(entry.getValue().toString());
				}
				if (entry.getKey().equals("status")) {
					workerDTO.setStatus(Integer.parseInt(entry.getValue().toString()));
				}
				if (entry.getKey().equals("email")) {
					workerDTO.setEmail(entry.getValue().toString());
				}
				if (entry.getKey().equals("subcompanycode")) {
					workerDTO.setSubCompanyCode(entry.getValue().toString());
				}
				if (entry.getKey().equals("iscm")) {
					workerDTO.setIsCM(Integer.parseInt(entry.getValue().toString()));
				}
				if (entry.getKey().equals("lastname")) {
					workerDTO.setLastName(entry.getValue().toString());
				}
				if (entry.getKey().equals("fbxx01")) {
					if (!StringUtils.isEmpty(entry.getValue())) {
						workerDTO.setSubCompanyCode(entry.getValue().toString());
					}
				}
				if (entry.getKey().equals("password")) {
					workerDTO.setPassword(entry.getValue().toString());
				}
				if (entry.getKey().equals("mobile")) {
					workerDTO.setMobile(entry.getValue().toString());
				}
				if (entry.getKey().equals("position")) {
					workerDTO.setGw(entry.getValue().toString());
				}
			}
			// // 工号
			// workerDTO.setWorkCode(rs.getString("WORKCODE"));
			// // 姓名
			// workerDTO.setLastName(rs.getString("LASTNAME"));
			// // 公司编码
			// workerDTO.setSubCompanyCode(rs.getString("SUBCOMPANYCODE"));
			// // 密码
			// workerDTO.setPassword(rs.getString("PASSWORD"));
			// // 邮件地址
			// workerDTO.setEmail(rs.getString("EMAIL"));
			// // 手机号
			// workerDTO.setMobile(rs.getString("MOBILE"));
			// // 上级工号
			// workerDTO.setSuperior(rs.getString("SUPERIOR"));
			// // 是否客户经理
			// workerDTO.setIsCM(rs.getInt("ISCM"));
			// // 员工状态
			// workerDTO.setStatus(rs.getInt("STATUS"));

			// 验证数据有效性
			if (isValidWorker(workerDTO)) {
				// 同步一个员工数据
				ExecuteResult<Boolean> result = userSyncService.syncOAWorker(workerDTO);
				if (!result.isSuccess()) {
					logger.error(MessageFormat.format("同步OA员工({0})信息出错({1})", workerDTO.getWorkCode(),
							result.getErrorMessages().get(0)));
				} else {
					if (result.getResult() != null && result.getResult().booleanValue()) {
						logger.info(MessageFormat.format("同步OA员工({0})信息成功", workerDTO.getWorkCode()));
					} else {
						logger.error(MessageFormat.format("同步OA员工({0})信息出错({1})", workerDTO.getWorkCode(),
								result.getResultMessage()));
					}
				}
			}
		}

		// }
		// });

		// 维护员工直接上级
		ExecuteResult<Boolean> result = userSyncService.updateEmployeeSuperior();
		if (!result.isSuccess()) {
			logger.error(MessageFormat.format("维护员工直接上级出错({0})", result.getErrorMessages().get(0)));
		} else {
			if (result.getResult() != null && result.getResult().booleanValue()) {
				logger.info("维护员工直接上级成功");
			} else {
				logger.error(MessageFormat.format("维护员工直接上级出错({0})", result.getResultMessage()));
			}
		}

		logger.info("同步OA员工信息完成。");

		ArrayList<Long> userFinanceHisList = new ArrayList<Long>();

		return userFinanceHisList;
	}

	/**
	 * 验证OA员工数据有效性
	 * 
	 * @param workerDTO
	 *            OA员工数据
	 * @return 验证结果(true:有效，false:无效)
	 */
	private boolean isValidWorker(OAWorkerDTO workerDTO) {

		// 工号为空的员工数据为无效数据
		if (StringUtils.isEmpty(workerDTO.getWorkCode()) || StringUtils.isEmpty(workerDTO.getWorkCode().trim())) {
			return false;

			// 名称以"($工号)"结尾的员工数据为无效数据
		} else if (workerDTO.getLastName().endsWith(MessageFormat.format("({0})", workerDTO.getWorkCode()))) {
			return false;
		}

		return true;
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
	public static void main(String[] args) {
		Htd_superServicePortTypeProxy proxy = new Htd_superServicePortTypeProxy();
		try {
			AnyType2AnyTypeMapEntry[][] getdata = proxy.getdata("");
			for (AnyType2AnyTypeMapEntry[] anyType2AnyTypeMap : getdata) {
				OAWorkerDTO workerDTO = new OAWorkerDTO();
				for (AnyType2AnyTypeMapEntry entry : anyType2AnyTypeMap) {
					if (entry.getKey().equals("workcode")) {
						workerDTO.setWorkCode(entry.getValue().toString());
					}
					if (entry.getKey().equals("superior")) {
						workerDTO.setSuperior(entry.getValue().toString());
					}
					if (entry.getKey().equals("status")) {
						workerDTO.setStatus(Integer.parseInt(entry.getValue().toString()));
					}
					if (entry.getKey().equals("email")) {
						workerDTO.setEmail(entry.getValue().toString());
					}
					if (entry.getKey().equals("subcompanycode")) {
						workerDTO.setSubCompanyCode(entry.getValue().toString());
					}
					if (entry.getKey().equals("iscm")) {
						workerDTO.setIsCM(Integer.parseInt(entry.getValue().toString()));
					}
					if (entry.getKey().equals("lastname")) {
						workerDTO.setLastName(entry.getValue().toString());
					}
					if (entry.getKey().equals("fbxx01")) {
						if (!StringUtils.isEmpty(entry.getValue())) {
							workerDTO.setSubCompanyCode(entry.getValue().toString());
						}
					}
					if (entry.getKey().equals("password")) {
						workerDTO.setPassword(entry.getValue().toString());
					}
					if (entry.getKey().equals("mobile")) {
						workerDTO.setMobile(entry.getValue().toString());
					}
					//TODO
					if (entry.getKey().equals("position")) {
						workerDTO.setGw(entry.getValue().toString());
					}
				}			
				if(workerDTO.getWorkCode().equals("08010501")){
					System.out.println(JSONObject.toJSONString(workerDTO));
					
				}

			}
			System.out.println(JSONObject.toJSONString(getdata));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
