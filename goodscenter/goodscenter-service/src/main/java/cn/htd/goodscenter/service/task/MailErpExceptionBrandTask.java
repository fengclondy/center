package cn.htd.goodscenter.service.task;

import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.util.SysProperties;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.indto.QueryBrandErpExceptionInDto;
import cn.htd.goodscenter.service.ItemBrandExportService;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 邮件发送。每15分钟触发一次
 */
public class MailErpExceptionBrandTask implements IScheduleTaskDealMulti<ItemBrand> {

    private static final Logger logger = LoggerFactory.getLogger(ItemBrandDownErpSupplyTask.class);

    @Autowired
    private ItemBrandExportService itemBrandExportService;

    @Autowired
    private SendSmsEmailService sendSmsEmailService;

    @Override
    public List<ItemBrand> selectTasks(String taskParameter, String ownSign, int taskQueueNum, List<TaskItemDefine> taskItemList, int eachFetchDataNum) throws Exception {
        logger.info("发送邮件定时任务【品牌下行异常数据】开始, taskParameter : {}", taskParameter);
        try {
            QueryBrandErpExceptionInDto queryBrandErpExceptionInDto = new QueryBrandErpExceptionInDto();
            Integer period = null; // 间隔时间： 例：15分钟之前的异常数据
            if (StringUtils.isEmpty(taskParameter)) { // taskParameter 配置多少分之前的异常数据
                period = Integer.valueOf(taskParameter);
            }
            queryBrandErpExceptionInDto.setPeriod(period == null ? 15 : period);
            ExecuteResult<DataGrid<ItemBrand>> executeResult = this.itemBrandExportService.queryErpExceptionBrandList(null, queryBrandErpExceptionInDto);
            DataGrid<ItemBrand> dataGrid = executeResult.getResult();
            List<ItemBrand> itemBrands = dataGrid.getRows();
            // 准备邮件内容
            MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
            String recevierMail = SysProperties.getProperty("basic_recevier_mail");
            String senderMail = SysProperties.getProperty("sender_mail");
            mailWarnInDTO.setRecevierMail(recevierMail);
            mailWarnInDTO.setSenderMail(senderMail);
            mailWarnInDTO.setEmailTitle("品牌下行异常信息");
            List<MailWarnRow> rowList = new ArrayList<>();
            // 表头
            if (itemBrands != null && itemBrands.size() > 0) {
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
                for (ItemBrand itemBrand : itemBrands) {
                    MailWarnRow mailWarnRow = new MailWarnRow();
                    List<MailWarnColumn> mailWarnColumnList = new ArrayList<>();
                    MailWarnColumn mailWarnCol1=new MailWarnColumn();
                    mailWarnCol1.setIndex(1);
                    mailWarnCol1.setValue("品牌数据下行异常");
                    mailWarnColumnList.add(mailWarnCol1);
                    MailWarnColumn mailWarnCol2=new MailWarnColumn();
                    mailWarnCol2.setIndex(2);
                    mailWarnCol2.setValue(simpleDateFormat.format(itemBrand.getErpDownTime()));
                    mailWarnColumnList.add(mailWarnCol2);
                    MailWarnColumn mailWarnCol3=new MailWarnColumn();
                    mailWarnCol3.setIndex(3);
                    mailWarnCol3.setValue(itemBrand.getBrandName() + "品牌下行异常");
                    mailWarnColumnList.add(mailWarnCol3);
                    mailWarnRow.setMailWarnColumnList(mailWarnColumnList);
                    rowList.add(mailWarnRow);
                }
                mailWarnInDTO.setRowList(rowList);
                ExecuteResult<String> executeResult1 = this.sendSmsEmailService.doSendEmailByTemplate(mailWarnInDTO);
                if (!executeResult1.isSuccess()) {
                    throw new Exception("邮件发送错误:" + executeResult1.getErrorMessages());
                }
            }
        } catch (Exception e) {
            logger.error("发送邮件定时任务【品牌下行异常数据】异常，错误信息：", e);
        }
        return null;
    }

    @Override
    public boolean execute(ItemBrand[] itemBrands, String s) throws Exception {
        return true;
    }


    @Override
    public Comparator<ItemBrand> getComparator() {
        return new Comparator<ItemBrand>() {
            public int compare(ItemBrand o1, ItemBrand o2) {
                Long id1 = o1.getBrandId();
                Long id2 = o2.getBrandId();
                return id1.compareTo(id2);
            }
        };
    }
}
