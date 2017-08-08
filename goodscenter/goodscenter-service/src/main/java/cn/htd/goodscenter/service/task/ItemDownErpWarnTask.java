package cn.htd.goodscenter.service.task;


import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dto.ItemWaringOutDTO;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;

import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

/**
 * Created by zhangxiaolong on 17/3/31.
 */
public class ItemDownErpWarnTask implements IScheduleTaskDealMulti<ItemWaringOutDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ItemDownErpWarnTask.class);

    @Resource
    private ItemMybatisDAO itemMybatisDAO;
    @Resource
    private SendSmsEmailService sendSmsEmailService;

    @Override
    public boolean execute(ItemWaringOutDTO[] itemWaringOutDTOS, String s) throws Exception {


        return false;
    }

    @Override
    public List<ItemWaringOutDTO> selectTasks(String s, String s1, int i, List<TaskItemDefine> list, int i1) throws Exception {
    	logger.info("ItemDownErpWarnTask::selectTasks start...");
    	Long totalCount=itemMybatisDAO.queryFailedDownErpCount();
    	logger.info("ItemDownErpWarnTask::selectTasks count:"+totalCount);
        if(totalCount==null||totalCount<=0){
            return null;
        }

        List<ItemWaringOutDTO> resultList=itemMybatisDAO.queryFailedDownErpList(0,totalCount.intValue());

        if(CollectionUtils.isEmpty(resultList)){
            return null;
        }
        //发送邮件
        MailWarnInDTO mailWarnInDTO=new MailWarnInDTO();
        mailWarnInDTO.setEmailTitle("商品下行失败告警邮件");
        mailWarnInDTO.setRecevierMail("error_item@htd.cn");
        List<MailWarnRow> rowList=Lists.newArrayList();
        MailWarnRow rowHead=new MailWarnRow();
        
        List<MailWarnColumn> mailWarnColumnList=Lists.newArrayList();
        
        MailWarnColumn mailWarnColumn_col1=new MailWarnColumn();
        mailWarnColumn_col1.setIndex(1);
        mailWarnColumn_col1.setValue("商品编码");
        mailWarnColumnList.add(mailWarnColumn_col1);
        MailWarnColumn mailWarnColumn_col2=new MailWarnColumn();
        mailWarnColumn_col2.setIndex(2);
        mailWarnColumnList.add(mailWarnColumn_col2);
        mailWarnColumn_col2.setValue("错误时间");
        MailWarnColumn mailWarnColumn_col3=new MailWarnColumn();
        mailWarnColumn_col3.setIndex(3);
        mailWarnColumnList.add(mailWarnColumn_col3);
        mailWarnColumn_col3.setValue("下行状态");
        MailWarnColumn mailWarnColumn_col4=new MailWarnColumn();
        mailWarnColumn_col4.setIndex(4);
        mailWarnColumn_col4.setValue("异常原因");
        mailWarnColumnList.add(mailWarnColumn_col4);
        rowHead.setMailWarnColumnList(mailWarnColumnList);
        //首行
        rowList.add(rowHead);
        
        for(ItemWaringOutDTO itemWaringOutDTO:resultList){
        	 MailWarnRow row=new MailWarnRow();
        	 
        	 List<MailWarnColumn> warnColList=Lists.newArrayList();
        	//1
        	  MailWarnColumn mailWarnColumn1=new MailWarnColumn();
        	  mailWarnColumn1.setIndex(1);
        	  mailWarnColumn1.setValue(itemWaringOutDTO.getItemCode());
        	  warnColList.add(mailWarnColumn1);
        	//2
        	  MailWarnColumn mailWarnColumn2=new MailWarnColumn();
        	  mailWarnColumn2.setIndex(2);
        	  if(itemWaringOutDTO.getFailTime()!=null){
        		  SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	  mailWarnColumn2.setValue(f.format(itemWaringOutDTO.getFailTime()));
        	  }else{
        		  mailWarnColumn2.setValue("");
        	  }
        	  warnColList.add(mailWarnColumn2);
        	//3
        	  MailWarnColumn mailWarnColumn3=new MailWarnColumn();
        	  mailWarnColumn3.setIndex(3);
        	  if(StringUtils.isNotEmpty(itemWaringOutDTO.getDownErpStatus())){
        		  ItemErpStatusEnum itemErpStatusEnum=ItemErpStatusEnum.getEnumBycode(itemWaringOutDTO.getDownErpStatus());
        		  mailWarnColumn3.setValue(itemErpStatusEnum==null?"":itemErpStatusEnum.getLabel());
        	  }else{
        		  mailWarnColumn3.setValue("");
        	  }
        	 
        	  warnColList.add(mailWarnColumn3);
        	//4
        	  MailWarnColumn mailWarnColumn4=new MailWarnColumn();
        	  mailWarnColumn4.setIndex(4);
        	  mailWarnColumn4.setValue(itemWaringOutDTO.getFailureReason());
        	  warnColList.add(mailWarnColumn4);
        	  row.setMailWarnColumnList(warnColList);
        	  rowList.add(row);
        }
        mailWarnInDTO.setRowList(rowList);
        mailWarnInDTO.setSenderMail("it_warning@htd.cn");
        sendSmsEmailService.doSendEmailByTemplate(mailWarnInDTO);
        
        logger.info("ItemDownErpWarnTask::selectTasks end...");

        return null;
    }

    @Override
    public Comparator<ItemWaringOutDTO> getComparator() {
        return new Comparator<ItemWaringOutDTO>() {
            @Override
            public int compare(ItemWaringOutDTO itemWaringOutDTO1, ItemWaringOutDTO itemWaringOutDTO2) {
                boolean comparaeResult=itemWaringOutDTO1.getFailTime().before(itemWaringOutDTO2.getFailTime());
                return comparaeResult ? 1 : 0;
            }
        };
    }
}
