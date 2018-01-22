package cn.htd.common.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.SpringApplicationContextHolder;


/**
 * 与中间件接口交互辅助类
 * 
 * @author zhangxiaolong
 *
 */
public class MiddlewareInterfaceUtil {
	// Redis中间件key数据
	private static final String MIDDLEWARE_ACCESS_TOKEN_KEY = "zhongtai_middleware_access_token_key";
	
	private static final Logger logger = LoggerFactory.getLogger(MiddlewareInterfaceUtil.class);

	/**
	 * 获取访问TOKEN
	 * 
	 * @return
	 */
	public static String getAccessToken() {
		logger.info("MiddlewareInterfaceUtil::getAccessToken:开始查询token");
		RedisDB redisClient = SpringApplicationContextHolder.getBean(RedisDB.class);
		
		String cacheToken = redisClient.get(MIDDLEWARE_ACCESS_TOKEN_KEY);
		
		if(StringUtils.isNotEmpty(cacheToken)){
			logger.info("MiddlewareInterfaceUtil::getAccessToken:redis 缓存命中 token");
			return cacheToken;
		}
		
		String responseJson = httpGet(SpringApplicationContextHolder.getBean("middlewarePath") + "/middleware-erp/token/"
				+ MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID, Boolean.TRUE);

		if (StringUtils.isEmpty(responseJson)) {
			return null;
		}
		
		logger.info("MiddlewareInterfaceUtil::getAccessToken:接口查询 token "+ responseJson);

		JSONObject resultMap = JSONObject.parseObject(responseJson);
		if (MapUtils.isNotEmpty(resultMap)
				&& MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(resultMap.getString("code") + "")) {
			if (resultMap.getString("data") != null) {
				redisClient.setAndExpire(MIDDLEWARE_ACCESS_TOKEN_KEY, resultMap.getString("data"), 1 * 60 * 60);
				return resultMap.getString("data");
			}

		}
		return null;
	}
	
	public static void refreshAcceccToken(){
		RedisDB redisClient = SpringApplicationContextHolder.getBean(RedisDB.class);
		
		String responseJson = httpGet(SpringApplicationContextHolder.getBean("middlewarePath") + "/middleware-erp/token/"
				+ MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID, Boolean.TRUE);

		if (StringUtils.isEmpty(responseJson)) {
			return;
		}
		
		logger.info("MiddlewareInterfaceUtil::getAccessToken:接口查询 token "+ responseJson);

		JSONObject resultMap = JSONObject.parseObject(responseJson);
		if (MapUtils.isNotEmpty(resultMap)
				&& MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(resultMap.getString("code") + "")) {
			if (resultMap.getString("data") != null) {
				redisClient.setAndExpire(MIDDLEWARE_ACCESS_TOKEN_KEY, resultMap.getString("data"), 1 * 60 * 60);
			}

		}
	}

	public static CloseableHttpClient getHttpClient(boolean isHttps) {

		if (!isHttps) {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			return httpClient;
		}

		SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(ssf).build();
		return httpClient;
	}

	public static String httpPost(String url, String jsonParam, boolean isHttps) {
		logger.info("MiddlewareInterfaceUtil::httpPost url={},param={}", url, jsonParam);
		StringEntity entity = null;
		if (StringUtils.isNotEmpty(jsonParam)) {
			entity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
		}
        String result = httpPost(url, entity, isHttps);
		logger.info("httpPost end, result : {}", result);
		return result;
	}

    public static String httpPost(String url, Map<String, String> paramMap, boolean isHttps) {
        logger.info("MiddlewareInterfaceUtil::httpPost url={},paramMap={}", url,
                JSONObject.toJSONString(paramMap));
        StringEntity entity = null;
        if (paramMap != null && !paramMap.isEmpty()) {
            StringBuilder param = new StringBuilder();
            for (String key : paramMap.keySet()) {
                param.append(MessageFormat.format("&{0}={1}", key, paramMap.get(key)));
            }
            entity = new StringEntity(param.substring(1), ContentType.APPLICATION_FORM_URLENCODED);
        }
        String result = httpPost(url, entity, isHttps);
        logger.info("httpPost end, result : {}", result);
        return result;
    }

	private static String httpPost(String url, StringEntity se, boolean isHttps) {
        final HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()  
                .setConnectTimeout(2000)//设置连接超时时间，单位毫秒
                .setConnectionRequestTimeout(1000)//设置从connect Manager获取Connection 超时时间
                .setSocketTimeout(5000).build();//请求获取数据的超时时间
        post.setConfig(requestConfig);
        CloseableHttpClient httpClient = getHttpClient(isHttps);
        if (se != null) {
            post.setEntity(se);
        }

        CloseableHttpResponse httpResponse = null;
        StringBuilder resultSb = new StringBuilder();
        try {
            httpResponse = httpClient.execute(post);
            final HttpEntity resultEntity = httpResponse.getEntity();
            if (null != resultEntity) {
                String line;
                BufferedReader bufferedReader;
                InputStreamReader streamReader = null;

                streamReader = new InputStreamReader(resultEntity.getContent(), "UTF-8");

                if (streamReader != null) {
                    bufferedReader = new BufferedReader(streamReader);
                    while ((line = bufferedReader.readLine()) != null) {
                        resultSb.append(line);
                    }
                    streamReader.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try{
            JSONObject map = JSONObject.parseObject(resultSb.toString());
            
            if(map.getString("code")!=null&&String.valueOf(map.getString("code")).equals("2001")){
                //try again
                refreshAcceccToken();
            }
            
        }catch(Exception e){
            
        }
        
        return resultSb.toString();
    }
	
	/**
	 * 
	 * @param url
	 * @param jsonParam
	 * @return
	 * @throws KeyManagementException
	 */
	public static String httpGet(String url, boolean isHttps) {
		logger.info("httpGet start, url : {}, isHttps : {}", url, isHttps);
		final HttpGet get = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(2000)//设置连接超时时间，单位毫秒
		        .setConnectionRequestTimeout(1000)//设置从connect Manager获取Connection 超时时间
		        .setSocketTimeout(5000).build();//请求获取数据的超时时间
		get.setConfig(requestConfig);
		CloseableHttpClient httpClient = getHttpClient(isHttps);
		CloseableHttpResponse httpResponse = null;
		StringBuilder resultSb = new StringBuilder();
		try {
			httpResponse = httpClient.execute(get);
			final HttpEntity resultEntity = httpResponse.getEntity();
			if (null != resultEntity) {
				String line;
				BufferedReader bufferedReader;
				InputStreamReader streamReader = null;

				streamReader = new InputStreamReader(resultEntity.getContent(), "UTF-8");

				if (streamReader != null) {
					bufferedReader = new BufferedReader(streamReader);
					while ((line = bufferedReader.readLine()) != null) {
						resultSb.append(line);
					}
					streamReader.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("httpGet end, result : {}", resultSb.toString());
		try{
		    JSONObject map = JSONObject.parseObject(resultSb.toString());
			
			if(map.getString("code")!=null&&String.valueOf(map.getString("code")).equals("2001")){
				//try again
				refreshAcceccToken();
			}
			
		}catch(Exception e){
			
		}
		return resultSb.toString();
	}

	public static <T> T httpPostAndGetResponseObject(String url, String jsonParam, Class<T> clazz) {
		String responseJson = httpPost(url, jsonParam, Boolean.TRUE);
		if (StringUtils.isEmpty(responseJson)) {
			return null;
		}

		try{
			JSONObject middlewareResponseObj = JSONObject.parseObject(responseJson);
			String code = middlewareResponseObj.getString("code");
			if (MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(code)) {
				String dataMap = middlewareResponseObj.getString("data");
				if (StringUtils.isNotEmpty(dataMap)) {
					return JSONObject.parseObject(dataMap, clazz);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	public static ItemStockResponseDTO getSingleItemStock(String supplyierCode, String spuCode) {
		String param = "?supplierCode=" + supplyierCode + "&productCodes=" + spuCode + "&token="
				+ MiddlewareInterfaceUtil.getAccessToken();
		try{
			String responseJson = MiddlewareInterfaceUtil
					.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_ITEM_STOCK_URL + param, Boolean.TRUE);
			JSONObject map = JSONObject.parseObject(responseJson);
			List<ItemStockResponseDTO> list = JSONArray.parseArray(map.getString("data"), ItemStockResponseDTO.class);
			ItemStockResponseDTO itemStockResponse = null;
			if (CollectionUtils.isNotEmpty(list)) {
				itemStockResponse = list.get(0);
			}
			return itemStockResponse;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static List<ItemStockResponseDTO> getBatchItemStock(String supplyierCode, List<String> spuCodeList) {
		StringBuilder productCodes = new StringBuilder();
		int i = 0;
		for (String productCode : spuCodeList) {
			productCodes.append(productCode);
			if (i != spuCodeList.size() - 1) {
				productCodes.append(",");
			}
			i++;
		}
		String param = "?supplierCode=" + supplyierCode + "&productCodes="+ productCodes.toString() + "&token="
				+ MiddlewareInterfaceUtil.getAccessToken();
		try{
			String responseJson = MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_ITEM_STOCK_URL + param, Boolean.TRUE);
			JSONObject map = JSONObject.parseObject(responseJson);
			List<ItemStockResponseDTO> list =JSONArray.parseArray(map.getString("data"),ItemStockResponseDTO.class);
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static List<ItemStockResponseDTO> getItemStockList(String supplyierCode, List<String> spuCodeList) {
		if(StringUtils.isEmpty(supplyierCode)||CollectionUtils.isEmpty(spuCodeList)){
			return null;
		}
		
		try{
			String accessToken = MiddlewareInterfaceUtil.getAccessToken();
			String itemCodeStr = Joiner.on(",").join(spuCodeList);
			String param = "?supplierCode=" + supplyierCode + "&productCodes=" + itemCodeStr + "&token=" + accessToken;
			//缓存2分钟处理 start
			RedisDB redisClient = SpringApplicationContextHolder.getBean(RedisDB.class);
			
			String responseJson= redisClient.get(param);
			
			if(StringUtils.isNotEmpty(responseJson)){
				logger.info("MiddlewareInterfaceUtil::getItemStockList:redis 缓存命中 stock");
			}else{
				//缓存2分钟处理 end
				logger.info("MiddlewareInterfaceUtil::getItemStockList:redis 缓存未命中 stock");
				responseJson=MiddlewareInterfaceUtil.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_ITEM_STOCK_URL + param, Boolean.TRUE);
				
				redisClient.setAndExpire(param, responseJson, 2 * 60 * 60);
			}
			
			if(StringUtils.isEmpty(responseJson)){
				return null;
			}
			
			JSONObject map = JSONObject.parseObject(responseJson);
			
			if(map.get("code")==null||!String.valueOf(map.get("code")).equals("1")){
				return null;
			}
			
			if(map.get("data")==null){
				return null;
			}
			return JSONArray.parseArray(map.getString("data"),ItemStockResponseDTO.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String findItemFloorPrice(String supplierCode,String itemSpuCode){
		
		if(StringUtils.isEmpty(supplierCode)||StringUtils.isEmpty(itemSpuCode)){
			return null;
		}
		
		try{
			String accessToken = MiddlewareInterfaceUtil.getAccessToken();
			String param = "?supplierCode=" + supplierCode + "&productCode=" + itemSpuCode + "&token=" + accessToken;
			String responseJson = MiddlewareInterfaceUtil
					.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_PROD_PRICE_URL + param, Boolean.TRUE);
			
			if(StringUtils.isEmpty(responseJson)){
				return null;
			}
			
			JSONObject map = JSONObject.parseObject(responseJson);
			
			if(map.getString("code")==null||!String.valueOf(map.getString("code")).equals("1")){
				return null;
			}
			
			if(map.getJSONObject("data")==null){
				return null;
			}
			
			JSONObject dataMap = map.getJSONObject("data");
			
			if(dataMap != null && dataMap.getString("floorPrice")!=null){
				return dataMap.getString("floorPrice");	
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
    public static Map findItemERPPrice(String supplierCode,String itemSpuCode){
		if(StringUtils.isEmpty(supplierCode)||StringUtils.isEmpty(itemSpuCode)){
			return null;
		}
		try{
			String accessToken = MiddlewareInterfaceUtil.getAccessToken();
			String param = "?supplierCode=" + supplierCode + "&productCode=" + itemSpuCode + "&token=" + accessToken;
			String responseJson = MiddlewareInterfaceUtil
					.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_PROD_PRICE_URL + param, Boolean.TRUE);

			if(StringUtils.isEmpty(responseJson)){
				return null;
			}
			JSONObject map = JSONObject.parseObject(responseJson);
			if(map.getString("code")==null||!String.valueOf(map.getString("code")).equals("1")){
				return null;
			}
			if(map.getJSONObject("data")==null){
				return null;
			}
			Map dataMap = JSONObject.toJavaObject(map.getJSONObject("data"), Map.class);
			return dataMap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static JdItemPriceResponseDTO getJdItemRealPrice(String jdSkuId){
		if(StringUtils.isEmpty(jdSkuId)){
			return null;
		}
		
		JdItemPriceResponseDTO jdItemPriceResponseDTO=null;
		try{
				String accessToken = MiddlewareInterfaceUtil.getAccessToken();
				String param = "?skuIds=" + jdSkuId + "&token=" + accessToken;
				String responseJson = MiddlewareInterfaceUtil
						.httpGet(MiddlewareInterfaceConstant.MIDDLEWARE_GET_JD_PRICE_URL + param, Boolean.TRUE);
				
				if(StringUtils.isEmpty(responseJson)){
					return null;
				}
				
				JSONObject map = JSONObject.parseObject(responseJson);
				
				if(map.getString("code")==null||!String.valueOf(map.getString("code")).equals("1")){
					return null;
				}
				
				if(map.getString("data")==null){
					return null;
				}
				
				if(map.getString("data")!=null){
					
					List<JdItemPriceResponseDTO> jdItemPriceList= JSONArray.parseArray(map.getString("data"), JdItemPriceResponseDTO.class);
					if(CollectionUtils.isNotEmpty(jdItemPriceList)){
						jdItemPriceResponseDTO=jdItemPriceList.get(0);
					}
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return jdItemPriceResponseDTO;
	}

}
