package cn.htd.basecenter.service.task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.StringUtil;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import cn.htd.basecenter.common.enums.YesNoEnum;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseSmsClientDAO;
import cn.htd.basecenter.dao.BaseSmsConfigDAO;
import cn.htd.basecenter.dao.BaseSmsNoticeDAO;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import cn.htd.basecenter.dto.BaseSmsNoticeDTO;
import cn.htd.basecenter.enums.SmsChannelTypeEnum;
import cn.htd.basecenter.enums.SmsEmailTypeEnum;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.basecenter.service.sms.ManDaoSmsClient;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.SysProperties;

public class NoticeSmsBalanceScheduleTask implements IScheduleTaskDealMulti<BaseSmsConfigDTO> {
	
    protected static transient Logger logger = LoggerFactory.getLogger(NoticeSmsBalanceScheduleTask.class);
    
    /**
     * 读取配置文件预警上限数
     */
    private static final String NOTICE_SMS_BALANCE = "notice_sms_balance";
    
    /**
	 * 读取配置文件是否发送短信标识
	 */
	private static final String IS_SEND_SMS_FLAG = "send.sms.flag";
	
	/**
	 * 标记预警短信已发送标识
	 */
	private static final String REDIS_NOTICE_SMS_BALANCE = "NOTICE_SMS_BALANCE";

	@Resource
	private BaseSmsConfigDAO baseSmsConfigDAO;
	@Resource
	private BaseSmsClientDAO baseSmsClientDAO;
	@Resource
	private SendSmsEmailService sendSmsEmailService;
	@Resource
	private BaseSmsNoticeDAO baseSmsNoticeDAO;
	@Resource
	private ManDaoSmsClient manDaoSmsClient;
	@Resource
	private RedisDB redisDB;
	
	@Override
	public Comparator<BaseSmsConfigDTO> getComparator() {
		return new Comparator<BaseSmsConfigDTO>() {
			public int compare(BaseSmsConfigDTO o1, BaseSmsConfigDTO o2) {
				Long id1 = o1.getId();
				Long id2 = o2.getId();
				return id1.compareTo(id2);
			}
		};
	}

	@Override
	public List<BaseSmsConfigDTO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskItemList, int eachFetchDataNum)
			throws Exception {
		logger.info("\n 方法[{}]，入参：[{}][{}][{}][{}][{}]", "NoticeSmsBalanceScheduleTask-selectTasks",
				"taskParameter=" + taskParameter, "ownSign=" + ownSign, "taskQueueNum=" + taskQueueNum,
				JSONObject.toJSONString(taskItemList), "eachFetchDataNum=" + eachFetchDataNum);
		List<BaseSmsConfigDTO> resultList = new ArrayList<BaseSmsConfigDTO>();
		String phoneStr = "";
		try {
			String balanceLimitStr = SysProperties.getProperty(NOTICE_SMS_BALANCE);
			if(StringUtil.isEmpty(balanceLimitStr)){
				logger.info("NoticeSmsBalanceScheduleTask notice_sms_balance is null");
				return null;
			}
			//查询配置信息
			BaseSmsConfigDTO configCondition = new BaseSmsConfigDTO();
			List<BaseSmsConfigDTO> validSmsConfigList = null;
			configCondition.setType(SmsEmailTypeEnum.SMS.getCode());
			//configCondition.setUsedFlag(YesNoEnum.YES.getValue());
			validSmsConfigList = baseSmsConfigDAO.queryByTypeCode(configCondition);
			if (validSmsConfigList == null || validSmsConfigList.size() == 0) {
				logger.info("NoticeSmsBalanceScheduleTask 启用的短信通道配置信息不存在");
				return null;
			}
			BaseSmsConfigDTO targetObj = validSmsConfigList.get(0);
			ExecuteResult<String> result = sendSmsEmailService.queryBalance();
			if(result.isSuccess() && !StringUtil.isEmpty(result.getResult())){
				int balance = Integer.parseInt(result.getResult());
				int balanceLimit = Integer.parseInt(balanceLimitStr);
				if(balance <= balanceLimit){
					String redisNotice = redisDB.get(REDIS_NOTICE_SMS_BALANCE);
					if("YES".equals(redisNotice)){
						logger.info("NoticeSmsBalanceScheduleTask notice_sms_balance 预警短信已发送过");
						return null;
					}
					Pager<BaseSmsNoticeDTO> pager = new Pager<BaseSmsNoticeDTO>();
					pager.setRows(100);
					List<BaseSmsNoticeDTO> baseSmsNoticeList = baseSmsNoticeDAO.queryBaseSmsNotice(null, pager);
					if(null != baseSmsNoticeList && !baseSmsNoticeList.isEmpty()){
						for (BaseSmsNoticeDTO dto : baseSmsNoticeList) {
							if(StringUtil.isNotEmpty(dto.getNoticePhone())){
								phoneStr += dto.getNoticePhone() + ",";
							}
						}
					}
					redisDB.set(REDIS_NOTICE_SMS_BALANCE, "YES");
				}else{
					redisDB.set(REDIS_NOTICE_SMS_BALANCE, "NO");
				}
			}
			if(!"".equals(phoneStr)){
				String content = "【汇通达】";
				if (String.valueOf(YesNoEnum.YES.getValue()).equals(SysProperties.getProperty(IS_SEND_SMS_FLAG))) {
					if (SmsChannelTypeEnum.MANDAO.getCode().equals(targetObj.getChannelCode())) {
						content = content + "漫道短信通道短信余额已不足" + balanceLimitStr + "条，请尽快充值";
						String smsResult = manDaoSmsClient.sendSms(targetObj, phoneStr, content);
						logger.info("NoticeSmsBalanceScheduleTask " + smsResult);
					}
				}
			}
		} catch (Exception e) {
			redisDB.set(REDIS_NOTICE_SMS_BALANCE, "NO");
			logger.error("\n 方法[{}]，异常：[{}]", "NoticeSmsBalanceScheduleTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		}
		return resultList;
	}

	@Override
	public boolean execute(BaseSmsConfigDTO[] arg0, String arg1) throws Exception {
		 return true;
	}

}
