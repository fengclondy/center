/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	PromotionAddDailyTask.java
 * Author:   	jiangt
 * Date:     	2017年01月12日
 * Description: 会员等级日计算
 * History: 	
 * <author>		<time>      	<version>	<desc>
 */
package cn.htd.promotion.cpc.biz.task;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;

import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.dao.BuyerWinningRecordDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.biz.handle.HttpProtocolHandler;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.dto.request.PromotionAwardReqDTO;

/**
 * 话费支付 汇金币充值
 * 
 * @author admin
 *
 */
public class PromotionSyncDbAwardTask implements IScheduleTaskDealMulti<BuyerWinningRecordDMO> {

	protected static transient Logger logger = LoggerFactory.getLogger(PromotionSyncDbAwardTask.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	public BuyerWinningRecordDAO buyerWinningRecordDAO;

	@Override
	public Comparator<BuyerWinningRecordDMO> getComparator() {
		return new Comparator<BuyerWinningRecordDMO>() {
			@Override
			public int compare(BuyerWinningRecordDMO o1, BuyerWinningRecordDMO o2) {
				return o1.getId().compareTo(o2.getId());
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
	public List<BuyerWinningRecordDMO> selectTasks(String taskParameter, String ownSign, int taskQueueNum,
			List<TaskItemDefine> taskQueueList, int eachFetchDataNum) throws Exception {
		logger.info("\n 方法[{}]，入参：[{}]", "PromotionSyncDbAwardTask-selectTasks", JSONObject.toJSONString(taskParameter),
				JSONObject.toJSONString(ownSign), JSONObject.toJSONString(taskQueueNum),
				JSONObject.toJSONString(taskQueueList), JSONObject.toJSONString(eachFetchDataNum));
		Date jobDate = new Date();
		BuyerWinningRecordDMO condition = new BuyerWinningRecordDMO();
		Pager<BuyerWinningRecordDMO> pager = null;
		List<String> taskIdList = new ArrayList<String>();
		List<BuyerWinningRecordDMO> promotionAwardDTOList = null;
		if (eachFetchDataNum > 0) {
			pager = new Pager<BuyerWinningRecordDMO>();
			pager.setPageOffset(0);
			pager.setRows(eachFetchDataNum);
		}

		try {
			if (taskQueueList != null && taskQueueList.size() > 0) {
				for (TaskItemDefine taskItem : taskQueueList) {
					taskIdList.add(taskItem.getTaskItemId());
				}
				condition.setTaskQueueNum(taskQueueNum);
				condition.setTaskIdList(taskIdList);
				// condition.setPromotionType("21");
				promotionAwardDTOList = buyerWinningRecordDAO.query4Task(condition, pager);
			}
		} catch (Exception e) {
			logger.error("\n 方法:[{}],异常:[{}]", "PromotionSyncDbAwardTask-selectTasks",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "PromotionSyncDbAwardTask-selectTasks",
					JSONObject.toJSONString(promotionAwardDTOList));
		}
		return promotionAwardDTOList;
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
	public boolean execute(BuyerWinningRecordDMO[] tasks, String ownSign) throws Exception {
		boolean result = true;
		try {
			if (tasks != null && tasks.length > 0) {
				for (BuyerWinningRecordDMO promotionAwardDTO : tasks) {
					if (promotionAwardDTO != null && !StringUtils.isEmpty(promotionAwardDTO.getRewardType())) {
						logger.info("\n 方法[{}]，入参：[{}]", "PromotionSyncDbAwardTask-execute",
								JSONObject.toJSONString(promotionAwardDTO));

						if (promotionAwardDTO.getRewardType().equals("3")) {
							if (!StringUtils.isEmpty(promotionAwardDTO.getChargeTelephone())) {
								
								if(SysProperties.getProperty("HTDHL_RECHARGE_TYPE").equals("of")) {
									String rt = excuteRecharge(promotionAwardDTO);
									if (StringUtils.isEmpty(rt)) {
										// 出错
									} else if (rt.equals("1")) {
										// 充好了
										promotionAwardDTO.setDealFlag(0);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("01");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									} else if (rt.equals("0")) {
										// 正在充
										promotionAwardDTO.setDealFlag(2);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("00");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									} else {
										// 其他
										promotionAwardDTO.setDealFlag(2);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("00");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									}
								}else if(SysProperties.getProperty("HTDHL_RECHARGE_TYPE").equals("pp")) {
									String rt = excuteRechargeProphet(promotionAwardDTO);
									if (StringUtils.isEmpty(rt)) {
										// 出错
									} else if (rt.equals("1")) {
										// 充好了
										promotionAwardDTO.setDealFlag(0);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("01");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									} else if (rt.equals("2")) {
										//  正在充
										promotionAwardDTO.setDealFlag(2);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("00");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									} else {
										// 其他
										promotionAwardDTO.setDealFlag(2);
										buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
										PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
										dto.setId(promotionAwardDTO.getId());
										dto.setModifyId(0l);
										dto.setModifyName("sys");
										dto.setModifyTime(new Date());
										dto.setLogisticsStatus("00");
										buyerWinningRecordDAO.updateLogisticsInfo(dto);
									}
								}
								
								
								
							}
						} else if (promotionAwardDTO.getRewardType().equals("4")) {
							if (!StringUtils.isEmpty(promotionAwardDTO.getBuyerCode())) {
								String rt = addGold(promotionAwardDTO);
								if (!StringUtils.isEmpty(rt) && rt.equals("ok")) {
									promotionAwardDTO.setDealFlag(0);
									buyerWinningRecordDAO.updateDealFlag(promotionAwardDTO);
									PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
									dto.setId(promotionAwardDTO.getId());
									dto.setModifyId(0l);
									dto.setModifyName("sys");
									dto.setModifyTime(new Date());
									dto.setLogisticsStatus("01");
									buyerWinningRecordDAO.updateLogisticsInfo(dto);
								} else {
									PromotionAwardReqDTO dto = new PromotionAwardReqDTO();
									dto.setId(promotionAwardDTO.getId());
									dto.setModifyId(0l);
									dto.setModifyName("sys");
									dto.setModifyTime(new Date());
									dto.setLogisticsStatus("00");
									buyerWinningRecordDAO.updateLogisticsInfo(dto);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			result = false;
			logger.error("\n 方法:[{}],异常:[{}]", "PromotionSyncDbAwardTask-execute",
					ExceptionUtils.getStackTraceAsString(e));
		} finally {
			logger.info("\n 方法:[{}],出参:[{}]", "PromotionSyncDbAwardTask-execute", JSONObject.toJSONString(result));
		}
		return result;
	}

	private synchronized String addGold(BuyerWinningRecordDMO promotionAwardDTO) {
		// GoldRecordEntity goldRecordEntity = new GoldRecordEntity();
		// goldRecordEntity.setMemberno(promotionAwardDTO.getBuyerCode());
		// if(!StringUtils.isEmpty(promotionAwardDTO.getAwardValue())){
		// goldRecordEntity.setGold(new Integer(promotionAwardDTO.getAwardValue()));
		// }else{
		// goldRecordEntity.setGold(0);
		// }
		// goldRecordEntity.setBptype("1");
		// goldRecordEntity.setDescribe("扭蛋机活动加金币");
		// goldRecordEntity.setRemark(promotionAwardDTO.getPromotionId()+":"+promotionAwardDTO.getId());
		// goldRecordEntity.setOperatorid("sys");
		// boolean rt = goldService.addGoldByPromotion(goldRecordEntity );
		String responseMsg = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// 1.构造HttpClient的实例
		CloseableHttpClient httpClient = httpClientBuilder.build();

		String url = SysProperties.getProperty("HTDHL_ADDRESS") + "/JuheRecharge/updateGoldState.htm";

		// 2.构造PostMethod的实例
		HttpPost httppost = new HttpPost(url);

		// 3.把参数值放入到PostMethod对象中
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("memberno", promotionAwardDTO.getBuyerCode()));
		formparams.add(new BasicNameValuePair("gold", promotionAwardDTO.getAwardValue()));
		formparams.add(new BasicNameValuePair("promotionId", promotionAwardDTO.getPromotionId()));
		formparams.add(new BasicNameValuePair("id", promotionAwardDTO.getId() + ""));
		formparams.add(new BasicNameValuePair("describe",
				dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_TYPE, promotionAwardDTO.getPromotionType())
						+ "活动加金币"));

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			// 4.执行postMethod,调用http接口
			httppost.setEntity(uefEntity);

			// 5.读取内容
			CloseableHttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			responseMsg = EntityUtils.toString(entity, "UTF-8").trim();
			// 6.处理返回的内容
			logger.info("gold结果-->" + responseMsg);
			// System.out.println(responseMsg);
			if (!StringUtils.isEmpty(responseMsg) && responseMsg.indexOf("status=ok") > 0) {
				return "ok";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static void main(String[] args) {
		BuyerWinningRecordDMO s = new BuyerWinningRecordDMO();
		s.setId(3l);
		s.setPromotionId("3");
		s.setAwardValue("100");
		s.setChargeTelephone("13913999999");
		try {
			// BuyerWinningRecordDMO promotionAwardDTO = new BuyerWinningRecordDMO();
			// promotionAwardDTO.setBuyerCode("1");
			// promotionAwardDTO.setAwardValue("0");

			//excuteRechargeProphet(s);
			// addGold(promotionAwardDTO );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized String excuteRecharge(BuyerWinningRecordDMO promotionAwardDTO)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String responseMsg = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// 1.构造HttpClient的实例
		CloseableHttpClient httpClient = httpClientBuilder.build();

		String url = "http://api2.ofpay.com/onlineorder.do";

		// 2.构造PostMethod的实例
		HttpPost httppost = new HttpPost(url);

		// 3.把参数值放入到PostMethod对象中
		String userid = SysProperties.getProperty("HTDHL_OFPAY_USERID");// "A1307228";
		String userpws = encoderByMd5(SysProperties.getProperty("HTDHL_OFPAY_PWD"));// "huilin123");
		String cardid = "140101";
		String ret_url = SysProperties.getProperty("HTDHL_ADDRESS") + "/JuheRecharge/updateLotteryState.htm";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		String cardnum = promotionAwardDTO.getAwardValue();

		// String sporder_id = UUID.randomUUID().toString().replaceAll("-", "");

		String sporder_time = sdf.format(new Date());

		String game_userid = promotionAwardDTO.getChargeTelephone();

		String orderid = promotionAwardDTO.getPromotionId() + ":" + promotionAwardDTO.getId();
		// 包体=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid
		// String md5_str = StringHelper.encoderByMd5(userid + userpws + cardid
		// + cardnum + orderid + sporder_time+
		// phoneAndPriceAndFrom.split(",")[0]+ "hupo918oubf").toUpperCase();
		String md5_str = encoderByMd5(
				userid + userpws + cardid + cardnum + orderid + sporder_time + game_userid + "OFCARD").toUpperCase();

		String version = "6.0";

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("userid", userid));
		formparams.add(new BasicNameValuePair("userpws", userpws));
		formparams.add(new BasicNameValuePair("cardid", cardid));
		formparams.add(new BasicNameValuePair("cardnum", cardnum));
		formparams.add(new BasicNameValuePair("sporder_id", orderid));
		formparams.add(new BasicNameValuePair("sporder_time", sporder_time));
		formparams.add(new BasicNameValuePair("game_userid", game_userid));
		formparams.add(new BasicNameValuePair("ret_url", ret_url));
		formparams.add(new BasicNameValuePair("md5_str", md5_str));
		formparams.add(new BasicNameValuePair("version", version));
		// postMethod.addParameter("userid", userid);
		// postMethod.addParameter("userpws", userpws);
		// postMethod.addParameter("cardid", cardid);
		// postMethod.addParameter("cardnum", cardnum);
		// postMethod.addParameter("sporder_id", orderid);
		// postMethod.addParameter("sporder_time", sporder_time);
		// postMethod.addParameter("game_userid", game_userid);
		// postMethod.addParameter("ret_url", ret_url);
		// postMethod.addParameter("md5_str", md5_str);
		// postMethod.addParameter("version", version);

		UrlEncodedFormEntity uefEntity;
		uefEntity = new UrlEncodedFormEntity(formparams, "GBK");
		try {
			// 4.执行postMethod,调用http接口
			httppost.setEntity(uefEntity);

			// 5.读取内容
			CloseableHttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			responseMsg = EntityUtils.toString(entity, "UTF-8").trim();
			Map<String, Object> responseMsgMap = jdomParseXml(responseMsg);
			// 6.处理返回的内容
			logger.info("手机话费充值推送返回结果-->" + responseMsg);
			// System.out.println(responseMsg);
			if (responseMsgMap.get("retcode") != null) {
				return (String) responseMsgMap.get("retcode");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			// 关闭连接,释放资源
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public static String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] unencodedPassword = str.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return str;
		}
		md.reset();
		md.update(unencodedPassword);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 3、JDOM解析XML 解析的时候自动去掉CDMA
	 * 
	 * @param xml
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jdomParseXml(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			org.jdom.Document doc;
			doc = (org.jdom.Document) sb.build(source);

			org.jdom.Element root = doc.getRootElement();// 指向根节点
			List<org.jdom.Element> list = root.getChildren();
			if (list != null && list.size() > 0) {
				for (org.jdom.Element element : list) {
					System.out.println("key是：" + element.getName() + "，值是：" + element.getText());
					map.put(element.getName(), element.getText());
					/*
					 * try{ methodName = element.getName(); Method m = v.getClass().getMethod("set"
					 * + methodName, new Class[] { String.class }); if(parseInt(methodName)){
					 * m.invoke(v, new Object[] { Integer.parseInt(element.getText()) }); }else{
					 * m.invoke(v, new Object[] { element.getText() }); } }catch(Exception ex){
					 * ex.printStackTrace(); }
					 */

				}
			}

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private synchronized String excuteRechargeProphet(BuyerWinningRecordDMO promotionAwardDTO) {

		Map<String, String> parameterMap = new HashMap<String, String>();

		// 接口编码
		parameterMap.put("interfacecode", "N2002");
		String channelorderid = promotionAwardDTO.getPromotionId() + ":" + promotionAwardDTO.getId();
		// 合作方订单号
		parameterMap.put("channelorderid", channelorderid);
		// 产品模式，1-按产品编号，2- 按产品类型+产品面额
		parameterMap.put("modeltype", "2");
		String billid = promotionAwardDTO.getChargeTelephone();
		// 手机号码
		parameterMap.put("billid", billid);
		// 产品类型编码(产品模式为2时必填)（1话费，2流量，4后项流量）
		parameterMap.put("prodtype", "1");
		String proddenominationprice = "0";
		try {
			proddenominationprice = new Integer(promotionAwardDTO.getAwardValue()) * 100 + "";
		} catch (Exception e) {
			logger.info("proddenominationprice error:" + e);
		}
		// 面额，单位为分或者M(产品模式为2时必填)
		parameterMap.put("proddenominationprice", proddenominationprice);
		// 充值类型，1话费，2流量，4后项流量
		parameterMap.put("billtype", "1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 请求时间(格式:yyyy-MM-dd hh:mm:ss)
		parameterMap.put("requestdate", sdf.format(new Date()));
		// 订单描述
		parameterMap.put("content", "");
		// 回调的接口地址(合作方提供)支持HTTP/HTTPS协议
		String ret_url = //"https://testhl.htd.cn/JuheRecharge/updateLotteryStateProphet.htm";//
				SysProperties.getProperty("HTDHL_ADDRESS") + "/JuheRecharge/updateLotteryStateProphet.htm";
		parameterMap.put("notifyurl", ret_url);
		// 回调请求参数 1、该参数信息在充值接口中传送； 2、 在回调接口中原参数返回；
		parameterMap.put("callreqparam", "");
		// 扩展参数
		parameterMap.put("extparam", "");
		String data = "";
		String secretKey = //"aHRkd2xnZnl4Z3MxNTExMTYyMTU5NjA4MTUxMTE2MjE1OTYxOA==";//
				SysProperties.getProperty("HTDHL_PROPHET_SECRETKEY");
		String partid = //"1000000000000177";//
				SysProperties.getProperty("HTDHL_PROPHET_PARTID");
		Map<String, String> requestMap = new HashMap<String, String>();
		try {
			data = aesEncrypt(JSONObject.toJSONString(parameterMap), secretKey);
			// 时间戳
			String time = String.valueOf(System.currentTimeMillis());
			// 签名
			String sign = getSignAndMD5(partid, data, time);
			requestMap.put("partid", partid);
			requestMap.put("data", data);
			requestMap.put("sign", sign);
			requestMap.put("time", time);
			logger.info("[BluePlusSubmit][buildRequest]请求参数为:" + requestMap.toString());

			String BLUEPLUS_GATEWAY_URL = //"https://sandboxmorp.blueplus.cc/cgi_bin/bcmdp/interServer/interface";//
					SysProperties.getProperty("HTDHL_PROPHET_GATEWAY_URL");
			String rspContent = HttpProtocolHandler.httpsRequest(BLUEPLUS_GATEWAY_URL, "POST",
					JSONObject.toJSONString(requestMap));
			logger.info("[BluePlusSubmit][buildRequest]应答报文为:" + rspContent);

			JSONObject rspJsonObject = JSONObject.parseObject(rspContent);
			int result = rspJsonObject.getIntValue("result");
			return result + "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * AES加密
	 * 
	 * @param content
	 *            被加密内容
	 * @param password
	 *            秘钥
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String password) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		kgen.init(128, random);

		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] byteContent = content.getBytes("utf-8");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(byteContent);

		if ((result != null) && (result.length > 0)) {
			return Base64.encodeBase64URLSafeString(result);
		}
		return null;
	}

	/**
	 * MD5加密调用
	 * 
	 * @param params
	 * @return
	 */
	public static String getSignAndMD5(String... params) {
		String sign = getSign(params);
		return strToMd5(sign, "utf-8");
	}

	/**
	 * MD5加密
	 * 
	 * @param content
	 *            被加密字符串
	 * @param charSet
	 *            字符集
	 * @return
	 */
	private static String strToMd5(String content, String charSet) {
		String md5Str = null;
		if (content != null && content.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(content.getBytes(charSet));
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				md5Str = buf.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return md5Str;
	}

	/**
	 * 按照字典序逆序拼接参数
	 * 
	 * @param params
	 * @return
	 */
	private static String getSign(String... params) {
		List<String> srcList = new ArrayList<String>();
		for (String param : params) {
			srcList.add(param);
		}
		// 按照字典序逆序拼接参数
		Arrays.sort(params);
		Collections.sort(srcList, String.CASE_INSENSITIVE_ORDER);
		Collections.reverse(srcList);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < srcList.size(); i++) {
			sb.append(srcList.get(i));
		}
		return sb.toString();
	}

}
