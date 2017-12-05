package cn.htd.basecenter.service.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import cn.htd.basecenter.common.enums.MengWangErrorEnum;
import cn.htd.basecenter.common.exception.BaseCenterBusinessException;
import cn.htd.basecenter.common.utils.ExceptionUtils;
import cn.htd.basecenter.dto.BaseSmsConfigDTO;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service("mengWangSmsClient")
public class MengWangSmsClient extends BaseSmsClient {

    private String msgPszsubport = "";

    @Override
    protected void initClient(BaseSmsConfigDTO config) throws Exception {
        super.initClient(config);
        this.msgPszsubport = config.getMsgPszsubport();
    }

    @Override
    protected String clientSendSms(String phoneNum, String content) throws BaseCenterBusinessException, Exception {
        String[] arr = phoneNum.split(",");
        String msgId = String.valueOf(System.currentTimeMillis()) + String.valueOf(new Random().nextInt(100));

        Map<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("userid", sn);
        postParams.put("password", pwd);
        postParams.put("pszMobis", phoneNum);
        postParams.put("pszMsg", content);
        postParams.put("iMobiCount", arr.length);
        postParams.put("pszSubPort", msgPszsubport);
        postParams.put("msgId", msgId);
        return post(serviceURL, postParams);
    }

    private String post(String url, Map<String, Object> postParams) throws IOException, DocumentException {
        String returnMsg = "";
        String tmpMsg = "";
        // 1.构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setContentCharset("UTF-8");
        // 2.构造PostMethod的实例
        PostMethod postMethod = new PostMethod(url);
        if (postParams != null) {
            Iterator<Map.Entry<String, Object>> iterator = postParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                postMethod.addParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        try {
            // 4.执行postMethod,调用http接口
            int status = httpClient.executeMethod(postMethod);// 200
            logger.info("【梦网短信】发送状态结果：" + status);
            if (status != 200) {
                returnMsg = "发送失败";
            }
            // 5.读取内容
            String resBodyAsStr = postMethod.getResponseBodyAsString();
            logger.info("【梦网短信】请求结果：" + resBodyAsStr);
            returnMsg = getResultOfSMS(resBodyAsStr);
            tmpMsg =MengWangErrorEnum.getMengWangErrorContent(returnMsg);
            if (!StringUtils.isEmpty(tmpMsg)) {
                returnMsg = tmpMsg;
            }
        } catch (Exception e) {
            logger.error("【梦网短信】发送异常：" + ExceptionUtils.getStackTraceAsString(e));
            throw e;
        } finally {
            // 7.释放连接
            postMethod.releaseConnection();
        }
        return returnMsg;
    }

    private String getResultOfSMS(String xml) throws DocumentException {

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        String returnMsg = root.getTextTrim();
        return returnMsg;
    }

	@Override
	protected String queryBalance() throws BaseCenterBusinessException, Exception {
		return null;
	}


}
