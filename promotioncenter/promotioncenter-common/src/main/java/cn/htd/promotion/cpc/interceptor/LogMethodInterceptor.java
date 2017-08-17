package cn.htd.promotion.cpc.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.UUID;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.apache.bcel.classfile.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.AfterReturningAdvice;

import cn.htd.promotion.cpc.common.util.StringUtilHelper;

import com.alibaba.fastjson.JSON;

/**
 * 方法拦截器<br> 
 * 〈功能详细描述〉
 *
 * @author 梁晔
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class LogMethodInterceptor implements MethodInterceptor ,AfterReturningAdvice{

    private static final Logger LOGGER = LoggerFactory.getLogger(LogMethodInterceptor.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
    	long startTime = System.currentTimeMillis();
    	String invokeNo = MDC.get("invokeNo");
        if (invokeNo == null) {
            MDC.put("invokeNo", UUID.randomUUID().toString().replace("-", ""));
        }
        LOGGER.info(
        		invocation.getMethod().getDeclaringClass().getName()+"."+invocation.getMethod().getName()+"-input：{}",
                (invocation.getArguments() != null && invocation.getArguments().length > 0) ? JSON
                        .toJSONString(invocation.getArguments()[0]) : "NULL");
        Method method = invocation.getMethod();
        Object target = invocation.getThis();
        Object result = null;
        String methodName = target.getClass().getSimpleName() + "." + method.getName();
        try {
            result = invocation.proceed();
        }catch (RuntimeException t) {
            LOGGER.error("服务拦截器调用" + methodName + "异常！", t);
            throw t;
        } finally {
        	long endTime = System.currentTimeMillis();
        	String className = invocation.getMethod().getDeclaringClass().getName();
            LOGGER.info(invocation.getMethod().getDeclaringClass().getName()+"."+invocation.getMethod().getName()+"-output：{}"+"|调用耗时"+(endTime-startTime)+"ms", result != null ? JSON.toJSONString(result) : "NULL");
        }
        return result;
    }

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		try {
			MDC.remove("invokeNo");
		} catch (Exception e) {
			StringWriter w = new StringWriter();
		    e.printStackTrace(new PrintWriter(w));
		    LOGGER.error("清空MDC时候发生异常:" + w.toString());
		}
	}
}