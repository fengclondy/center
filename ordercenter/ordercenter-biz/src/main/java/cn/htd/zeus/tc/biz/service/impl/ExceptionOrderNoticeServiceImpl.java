package cn.htd.zeus.tc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.basecenter.dto.MailWarnColumn;
import cn.htd.basecenter.dto.MailWarnInDTO;
import cn.htd.basecenter.dto.MailWarnRow;
import cn.htd.basecenter.service.SendSmsEmailService;
import cn.htd.common.ExecuteResult;
import cn.htd.zeus.tc.biz.dao.JDOrderInfoDAO;
import cn.htd.zeus.tc.biz.dao.OrderCompensateERPDAO;
import cn.htd.zeus.tc.biz.dao.PayOrderInfoDAO;
import cn.htd.zeus.tc.biz.dmo.JDOrderInfoDMO;
import cn.htd.zeus.tc.biz.dmo.OrderCompensateERPDMO;
import cn.htd.zeus.tc.biz.dmo.PayOrderInfoDMO;
import cn.htd.zeus.tc.biz.service.ExceptionOrderNoticeService;
import cn.htd.zeus.tc.common.email.EmailConfig;
import cn.htd.zeus.tc.common.enums.OrderStatusEnum;
import cn.htd.zeus.tc.common.util.DateUtil;

@Service
public class ExceptionOrderNoticeServiceImpl implements ExceptionOrderNoticeService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExceptionOrderNoticeServiceImpl.class);

	@Autowired
	private SendSmsEmailService sendSmsEmailService;

	@Autowired
	private OrderCompensateERPDAO tradeOrderErpDistributionDao;
	
	@Autowired
	private JDOrderInfoDAO jdOrderInfoDao;
	
	@Autowired
	private PayOrderInfoDAO payOrderInfoDAO;

	@Autowired
	private EmailConfig emailConfig;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * 五合一异常订单查询
	 */
	@Override
	public List<OrderCompensateERPDMO> selectErpDistributionExceptionOrdersList(Map paramMap) {
		return tradeOrderErpDistributionDao.selectErpDistributionExceptionOrdersList(paramMap);
	}

	/*
	 * 处理五合一异常订单逻辑
	 */
	@Override
	public void executeFiveAndOneExceptionOrder(List<OrderCompensateERPDMO> tasks) {
		try {
			MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
			mailWarnInDTO.setRecevierMail(emailConfig.getRecevierMail());
			mailWarnInDTO.setSenderMail(emailConfig.getSenderMail());
			mailWarnInDTO.setEmailTitle("五合一下行异常信息");

			List<MailWarnRow> rowList = new ArrayList<>();
			MailWarnRow mailWarnRowHead = new MailWarnRow();
			List<MailWarnColumn> mailWarnColumnHeadList = new ArrayList<>();
			MailWarnColumn mailWarnHeadCol1 = new MailWarnColumn();
			mailWarnHeadCol1.setIndex(1);
			mailWarnHeadCol1.setValue("错误订单号");
			mailWarnColumnHeadList.add(mailWarnHeadCol1);
			MailWarnColumn mailWarnHeadCol2 = new MailWarnColumn();
			mailWarnHeadCol2.setIndex(2);
			mailWarnHeadCol2.setValue("错误时间");
			mailWarnColumnHeadList.add(mailWarnHeadCol2);
			MailWarnColumn mailWarnHeadCol3 = new MailWarnColumn();
			mailWarnHeadCol3.setIndex(3);
			mailWarnHeadCol3.setValue("异常原因");
			mailWarnColumnHeadList.add(mailWarnHeadCol3);
			mailWarnRowHead.setMailWarnColumnList(mailWarnColumnHeadList);
			rowList.add(mailWarnRowHead);
			for (OrderCompensateERPDMO requestInfo : tasks) {
				
				 MailWarnRow mailWarnRow = new MailWarnRow();
                 List<MailWarnColumn> mailWarnColumnList = new ArrayList<>();
                 MailWarnColumn mailWarnCol1=new MailWarnColumn();
                 mailWarnCol1.setIndex(1);
                 mailWarnCol1.setValue(requestInfo.getOrderNo());
                 mailWarnColumnList.add(mailWarnCol1);
                 MailWarnColumn mailWarnCol2=new MailWarnColumn();
                 mailWarnCol2.setIndex(2);
                 mailWarnCol2.setValue(simpleDateFormat.format(requestInfo.getModifyTime()));
                 mailWarnColumnList.add(mailWarnCol2);
                 MailWarnColumn mailWarnCol3=new MailWarnColumn();
                 mailWarnCol3.setIndex(3);
                 String errorMsg = "";
                 if(requestInfo.getErpStatus().equals(OrderStatusEnum.ERP_DOWN_LINK.getCode())){
                	 errorMsg = "分销单已经下行-但没有返回执行结果";
                 }else{
                	 errorMsg = "分销单已下行回调结果:erp处理失败";
                 }
                 mailWarnCol3.setValue(errorMsg);
                 mailWarnColumnList.add(mailWarnCol3);
                 mailWarnRow.setMailWarnColumnList(mailWarnColumnList);
                 rowList.add(mailWarnRow);
			}
			mailWarnInDTO.setRowList(rowList);
			ExecuteResult<String> executeResult1 = this.sendSmsEmailService
					.doSendEmailByTemplate(mailWarnInDTO);
			if (!executeResult1.isSuccess()) {
				LOGGER.error("分销单邮件发送错误:", executeResult1.getErrorMessages());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法ExceptionOrderNoticeServiceImpl.executeFiveAndOneExceptionOrder出现异常{}",
					w.toString());
		}
	}

	@Override
	public void executePostStrikeaExceptionOrder(List<PayOrderInfoDMO> tasks) {
		try {
			MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
			mailWarnInDTO.setRecevierMail(emailConfig.getRecevierMail());
			mailWarnInDTO.setSenderMail(emailConfig.getSenderMail());
			mailWarnInDTO.setEmailTitle("收付款下行异常信息");

			List<MailWarnRow> rowList = new ArrayList<>();
			MailWarnRow mailWarnRowHead = new MailWarnRow();
			List<MailWarnColumn> mailWarnColumnHeadList = new ArrayList<>();
			MailWarnColumn mailWarnHeadCol1 = new MailWarnColumn();
			mailWarnHeadCol1.setIndex(1);
			mailWarnHeadCol1.setValue("错误订单号");
			mailWarnColumnHeadList.add(mailWarnHeadCol1);
			MailWarnColumn mailWarnHeadCol2 = new MailWarnColumn();
			mailWarnHeadCol2.setIndex(2);
			mailWarnHeadCol2.setValue("错误时间");
			mailWarnColumnHeadList.add(mailWarnHeadCol2);
			MailWarnColumn mailWarnHeadCol3 = new MailWarnColumn();
			mailWarnHeadCol3.setIndex(3);
			mailWarnHeadCol3.setValue("异常原因");
			mailWarnColumnHeadList.add(mailWarnHeadCol3);
			mailWarnRowHead.setMailWarnColumnList(mailWarnColumnHeadList);
			rowList.add(mailWarnRowHead);
			for (PayOrderInfoDMO requestInfo : tasks) {
				
				 MailWarnRow mailWarnRow = new MailWarnRow();
                 List<MailWarnColumn> mailWarnColumnList = new ArrayList<>();
                 MailWarnColumn mailWarnCol1=new MailWarnColumn();
                 mailWarnCol1.setIndex(1);
                 mailWarnCol1.setValue(requestInfo.getOrderNo());
                 mailWarnColumnList.add(mailWarnCol1);
                 MailWarnColumn mailWarnCol2=new MailWarnColumn();
                 mailWarnCol2.setIndex(2);
                 mailWarnCol2.setValue(simpleDateFormat.format(requestInfo.getModifyTime()));
                 mailWarnColumnList.add(mailWarnCol2);
                 MailWarnColumn mailWarnCol3=new MailWarnColumn();
                 mailWarnCol3.setIndex(3);
                 String errorMsg = "";
                 if(requestInfo.getPayResultStatus().toString().equals(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN.getCode())){
                	 errorMsg = "收付款已经下行-但没有返回执行结果";
                 }else{
                	 errorMsg = "收付款已下行回调结果:erp处理失败";
                 }
                 mailWarnCol3.setValue(errorMsg);
                 mailWarnColumnList.add(mailWarnCol3);
                 mailWarnRow.setMailWarnColumnList(mailWarnColumnList);
                 rowList.add(mailWarnRow);
			}
			mailWarnInDTO.setRowList(rowList);
			ExecuteResult<String> executeResult1 = this.sendSmsEmailService
					.doSendEmailByTemplate(mailWarnInDTO);
			if (!executeResult1.isSuccess()) {
				LOGGER.error("收付款邮件发送错误:", executeResult1.getErrorMessages());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法ExceptionOrderNoticeServiceImpl.executePostStrikeaExceptionOrder出现异常{}",
					w.toString());
		}
	}

	@Override
	public List<PayOrderInfoDMO> selectPostStrikeaExceptionOrdersList(Map paramMap) {
		return payOrderInfoDAO.selectPostStrikeaExceptionOrdersList(paramMap);
	}

	@Override
	public void executePreSalesOrderExceptionOrder(List<JDOrderInfoDMO> tasks) {
		try {
			MailWarnInDTO mailWarnInDTO = new MailWarnInDTO();
			mailWarnInDTO.setRecevierMail(emailConfig.getRecevierMail());
			mailWarnInDTO.setSenderMail(emailConfig.getSenderMail());
			mailWarnInDTO.setEmailTitle("预售下行异常信息");

			List<MailWarnRow> rowList = new ArrayList<>();
			MailWarnRow mailWarnRowHead = new MailWarnRow();
			List<MailWarnColumn> mailWarnColumnHeadList = new ArrayList<>();
			MailWarnColumn mailWarnHeadCol1 = new MailWarnColumn();
			mailWarnHeadCol1.setIndex(1);
			mailWarnHeadCol1.setValue("错误订单号");
			mailWarnColumnHeadList.add(mailWarnHeadCol1);
			MailWarnColumn mailWarnHeadCol2 = new MailWarnColumn();
			mailWarnHeadCol2.setIndex(2);
			mailWarnHeadCol2.setValue("错误时间");
			mailWarnColumnHeadList.add(mailWarnHeadCol2);
			MailWarnColumn mailWarnHeadCol3 = new MailWarnColumn();
			mailWarnHeadCol3.setIndex(3);
			mailWarnHeadCol3.setValue("异常原因");
			mailWarnColumnHeadList.add(mailWarnHeadCol3);
			mailWarnRowHead.setMailWarnColumnList(mailWarnColumnHeadList);
			rowList.add(mailWarnRowHead);
			for (JDOrderInfoDMO requestInfo : tasks) {
				
				 MailWarnRow mailWarnRow = new MailWarnRow();
                 List<MailWarnColumn> mailWarnColumnList = new ArrayList<>();
                 MailWarnColumn mailWarnCol1=new MailWarnColumn();
                 mailWarnCol1.setIndex(1);
                 mailWarnCol1.setValue(requestInfo.getOrderNo());
                 mailWarnColumnList.add(mailWarnCol1);
                 MailWarnColumn mailWarnCol2=new MailWarnColumn();
               
                 String errorMsg = "";
                 Date errorTime = DateUtil.getSystemTime();
                 if(requestInfo.getErpResultStatus().toString().equals(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN.getCode())){
                	 errorMsg = "预售已经下行-但ERP没有返回执行结果";
                	 errorTime = requestInfo.getErpLastSendTime();
                 }else if(requestInfo.getErpResultStatus().toString().equals(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_RETURN_FAIL.getCode())){
                	 errorMsg = "预售已下行回调结果:erp处理失败";
                	 errorTime = requestInfo.getErpLastSendTime();
                 }else if(requestInfo.getJdResultStatus().toString().equals(OrderStatusEnum.JD_RESULT_STATUS_HAVE_DOWN_MQ_NOT_RETURN.getCode())){
                	 errorMsg = "预售已经下行-但JD没有返回执行结果";
                	 errorTime = requestInfo.getJdLastSendTime();
                 }else{
                	 errorMsg = "预售已下行回调结果:JD处理失败";
                	 errorTime = requestInfo.getJdLastSendTime();
                 }
                 
                 mailWarnCol2.setIndex(2);
                 mailWarnCol2.setValue(simpleDateFormat.format(errorTime));
                 mailWarnColumnList.add(mailWarnCol2);
                 MailWarnColumn mailWarnCol3=new MailWarnColumn();
                 mailWarnCol3.setIndex(3);
                 mailWarnCol3.setValue(errorMsg);
                 mailWarnColumnList.add(mailWarnCol3);
                 mailWarnRow.setMailWarnColumnList(mailWarnColumnList);
                 rowList.add(mailWarnRow);
			}
			mailWarnInDTO.setRowList(rowList);
			ExecuteResult<String> executeResult1 = this.sendSmsEmailService
					.doSendEmailByTemplate(mailWarnInDTO);
			if (!executeResult1.isSuccess()) {
				LOGGER.error("预售邮件发送错误:", executeResult1.getErrorMessages());
			}
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("调用方法ExceptionOrderNoticeServiceImpl.executePreSalesOrderExceptionOrder出现异常{}",
					w.toString());
		}
	}

	@Override
	public List<JDOrderInfoDMO> selectPreSalesOrderExceptionOrdersList(Map paramMap) {
		return jdOrderInfoDao.selectPreSalesOrderExceptionOrdersList(paramMap);
	}

}
