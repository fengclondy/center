package cn.htd.basecenter.service.task;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dao.BaseAddressDAO;
import cn.htd.basecenter.domain.BaseAddress;
import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.enums.ErpStatusEnum;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.ExecuteResult;
import cn.htd.common.util.SysProperties;
import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 发送邮件-地址信息下行erp异常的
 *
 * @author chenkang
 */
public class MailErpExceptionAddressScheduleTask implements IScheduleTaskDealMulti<BaseAddress> {

    protected static transient Logger logger = LoggerFactory.getLogger(MailErpExceptionAddressScheduleTask.class);

    @Autowired
    private SendSmsEmailService sendSmsEmailService;

    @Resource
    private BaseAddressDAO baseAddressDAO;

    @Override
    public Comparator<BaseAddress> getComparator() {
        return new Comparator<BaseAddress>() {
            public int compare(BaseAddress o1, BaseAddress o2) {
                Long id1 = o1.getId();
                Long id2 = o2.getId();
                return id1.compareTo(id2);
            }
        };
    }

    /**
     * 根据条件查询当前调度服务器可处理的任务
     *
     * @param taskParameter    任务的自定义参数
     * @param ownSign          当前环境名称
     * @param taskQueueNum     当前任务类型的任务队列数量
     * @param taskItemList    当前调度服务器，分配到的可处理队列
     * @param eachFetchDataNum 每次获取数据的数量
     * @return
     * @throws Exception
     */
    @Override
    public List<BaseAddress> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
                                         List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("邮件发送【下行基础数据异常】定时任务开始,方法[{}]，入参：[{}][{}][{}][{}][{}]", "MailErpExceptionAddressScheduleTask-selectTasks",
                "taskParameter=" + taskParameter, "ownSign=" + ownSign, "taskQueueNum=" + taskQueueNum,
                JSONObject.toJSONString(taskItemList), "eachFetchDataNum=" + eachFetchDataNum);
        try {
            // 查询下行中和下行失败的数据
            BaseAddress condition = new BaseAddress();
            condition.setErpStatus(ErpStatusEnum.DOWNING.getValue());
            condition.setErpStatus1(ErpStatusEnum.FAILURE.getValue());
            List<BaseAddress> dealAddressList = baseAddressDAO.queryAddressList4ErpException(condition, null);

            // 准备邮件内容
            MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
            String recevierMail = SysProperties.getProperty("basic_recevier_mail");
            String senderMail = SysProperties.getProperty("sender_mail");
            mailWarnInDTO.setRecevierMail(recevierMail);
            mailWarnInDTO.setSenderMail(senderMail);
            mailWarnInDTO.setEmailTitle("城市数据下行异常信息");
            List<MailWarnRow> rowList = new ArrayList<>();
            // 表头
            if (dealAddressList != null && dealAddressList.size() > 0) {
                MailWarnRow mailWarnRowHead = new MailWarnRow();
                List<MailWarnColumn> mailWarnColumnHeadList = new ArrayList<>();
                MailWarnColumn mailWarnHeadCol1=new MailWarnColumn();
                mailWarnHeadCol1.setIndex(1);
                mailWarnHeadCol1.setValue("错误类型");
                mailWarnColumnHeadList.add(mailWarnHeadCol1);
                MailWarnColumn mailWarnHeadCol2=new MailWarnColumn();
                mailWarnHeadCol2.setIndex(2);
                mailWarnHeadCol2.setValue("错误时间");
                mailWarnColumnHeadList.add(mailWarnHeadCol2);
                MailWarnColumn mailWarnHeadCol3=new MailWarnColumn();
                mailWarnHeadCol3.setIndex(3);
                mailWarnHeadCol3.setValue("异常原因");
                mailWarnColumnHeadList.add(mailWarnHeadCol3);
                mailWarnRowHead.setMailWarnColumnList(mailWarnColumnHeadList);
                rowList.add(mailWarnRowHead);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (BaseAddress baseAddress : dealAddressList) {
                    MailWarnRow mailWarnRow = new MailWarnRow();
                    List<MailWarnColumn> mailWarnColumnList = new ArrayList<>();
                    MailWarnColumn mailWarnCol1=new MailWarnColumn();
                    mailWarnCol1.setIndex(1);
                    mailWarnCol1.setValue("城市数据下行异常");
                    mailWarnColumnList.add(mailWarnCol1);
                    MailWarnColumn mailWarnCol2=new MailWarnColumn();
                    mailWarnCol2.setIndex(2);
                    mailWarnCol2.setValue(simpleDateFormat.format(baseAddress.getErpDownTime()));
                    mailWarnColumnList.add(mailWarnCol2);
                    MailWarnColumn mailWarnCol3=new MailWarnColumn();
                    mailWarnCol3.setIndex(3);
                    mailWarnCol3.setValue(baseAddress.getName() + "下行基础数据异常");
                    mailWarnColumnList.add(mailWarnCol3);
                    mailWarnRow.setMailWarnColumnList(mailWarnColumnList);
                    rowList.add(mailWarnRow);
                }
                mailWarnInDTO.setRowList(rowList);
                ExecuteResult<String> executeResult1 = this.sendSmsEmailService.doSendEmailByTemplate(mailWarnInDTO);
                if (!executeResult1.isSuccess()) {
                    throw new Exception("邮件发送错误:" + executeResult1.getErrorMessages());
                };
            }
        } catch (Exception e) {
            logger.error("\n 方法[{}]，异常：[{}]", "MailErpExceptionAddressScheduleTask-selectTasks",
                    ExceptionUtils.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 执行给定的任务数组。因为泛型不支持new 数组，只能传递OBJECT[]
     *
     * @param tasks   任务数组
     * @param ownSign 当前环境名称
     * @return
     * @throws Exception
     */
    @Override
    public boolean execute(BaseAddress[] tasks, String ownSign) throws Exception {
        return true;
    }
}
