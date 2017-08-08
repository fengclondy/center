package cn.htd.zeus.tc.common.middleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import cn.htd.zeus.tc.common.util.SysProperties;


/**
 * 与中间件接口交互辅助类
 * 
 * @author zhangxiaolong
 *
 */
public class MiddlewareInterfaceUtil {
	/**
	 * 获取访问TOKEN
	 * @return
	 */
	public static String getAccessToken(){
		//TODO:先从redis缓存中查询，如果有则直接返回
		String responseJson=httpGet(SysProperties.getMiddlewarePath()+"/token/"+MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID,Boolean.TRUE);
		
		if(StringUtils.isEmpty(responseJson)){
			return null;
		}
		
		MiddlewareResponseObject middlewareResponseObj=(MiddlewareResponseObject) 
				JSONObject.toBean(JSONObject.fromObject(responseJson),MiddlewareResponseObject.class);
		String code=middlewareResponseObj.getCode();
		if(MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(code)){
			Map<String,Object> dataMap= middlewareResponseObj.getData();
			if(MapUtils.isNotEmpty(dataMap)){
				return (String)dataMap.get("token");
			}
			
		}
		return null;
	}
	
	public static CloseableHttpClient getHttpClient(boolean isHttps){
		
		if(!isHttps){
			CloseableHttpClient httpClient=HttpClients.createDefault();
			return httpClient;
		}
		
		SSLContext ctx=null;
		try {
		      	ctx = SSLContext.getInstance("TLS");
		        X509TrustManager tm = new X509TrustManager() {  
		 	            public X509Certificate[] getAcceptedIssuers() {  
		 	                return null;  
		 	            }  
		 	  
		 	            public void checkClientTrusted(X509Certificate[] arg0,  
		 	                    String arg1) throws CertificateException {  
		 	            }  
		 	  
		 	            public void checkServerTrusted(X509Certificate[] arg0,  
		 	                    String arg1) throws CertificateException {  
		 	            }  
		 	        };  
					ctx.init(null, new TrustManager[] { tm }, null);
			} catch (Exception e1) {
					e1.printStackTrace();
			}  
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx,NoopHostnameVerifier.INSTANCE);  
	        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(ssf).build(); 
	        return httpClient;
	}
	
	public static String httpPost(String url,String jsonParam,boolean isHttps){
		final HttpPost post = new HttpPost(url);
        CloseableHttpClient httpClient = getHttpClient(isHttps); 
		if(StringUtils.isNotEmpty(jsonParam)){
			StringEntity entity=new StringEntity(jsonParam,ContentType.APPLICATION_JSON);
			post.setEntity(entity);
		}
      
		CloseableHttpResponse httpResponse = null;
		StringBuilder resultSb=new StringBuilder();
		try
		{
			httpResponse = httpClient.execute(post);
			final HttpEntity resultEntity = httpResponse.getEntity();
			if (null != resultEntity)
			{
				String line;
				BufferedReader bufferedReader;
				InputStreamReader streamReader = null;
				
				streamReader = new InputStreamReader(resultEntity.getContent(), "UTF-8");

				if (streamReader != null)
				{
					bufferedReader = new BufferedReader(streamReader);
					while ((line = bufferedReader.readLine()) != null)
					{
						resultSb.append(line);
					}
					streamReader.close();
				}
			

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			if(httpResponse!=null){
				try{
					httpResponse.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
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
	public static String httpGet(String url,boolean isHttps){
		final HttpGet post = new HttpGet(url);
        CloseableHttpClient httpClient = getHttpClient(isHttps);
		CloseableHttpResponse httpResponse = null;
		StringBuilder resultSb=new StringBuilder();
		try
		{
			httpResponse = httpClient.execute(post);
			final HttpEntity resultEntity = httpResponse.getEntity();
			if (null != resultEntity)
			{
				String line;
				BufferedReader bufferedReader;
				InputStreamReader streamReader = null;
				
				streamReader = new InputStreamReader(resultEntity.getContent(), "UTF-8");

				if (streamReader != null)
				{
					bufferedReader = new BufferedReader(streamReader);
					while ((line = bufferedReader.readLine()) != null)
					{
						resultSb.append(line);
					}
					streamReader.close();
				}
			

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			if(httpResponse!=null){
				try{
					httpResponse.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return resultSb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T httpPostAndGetResponseObject(String url,String jsonParam,Class clazz){
		String responseJson=httpPost(url,jsonParam,Boolean.TRUE);
		if(StringUtils.isEmpty(responseJson)){
			return null;
		}
		
		MiddlewareResponseObject middlewareResponseObj=(MiddlewareResponseObject) 
				JSONObject.toBean(JSONObject.fromObject(responseJson),MiddlewareResponseObject.class);
		String code=middlewareResponseObj.getCode();
		if(MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(code)){
			Map<String,Object> dataMap= middlewareResponseObj.getData();
			if(MapUtils.isNotEmpty(dataMap)){
				return (T)JSONObject.toBean(JSONObject.fromObject(dataMap),clazz);
			}
			
		}
		 
		return null;
	}
	
}
