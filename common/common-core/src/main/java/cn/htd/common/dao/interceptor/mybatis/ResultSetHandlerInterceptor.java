package cn.htd.common.dao.interceptor.mybatis;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.htd.common.dao.util.CamelCaseUtils;
import cn.htd.common.util.DateUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
public class ResultSetHandlerInterceptor implements Interceptor {
	private Logger logger = LoggerFactory.getLogger(ResultSetHandlerInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
		MappedStatement ms = (MappedStatement) ReflectUtil.getFieldValue(resultSetHandler, "mappedStatement");

		List<ResultMap> rms = ms.getResultMaps();
		ResultMap rm = rms != null && rms.size() > 0 ? rms.get(0) : null;

		String type = rm != null && rm.getType() != null ? rm.getType().getSimpleName().toLowerCase() : "";

		if ("map".equals(type)) {
			// 取得方法的参数Statement
			Statement stmt = (Statement) invocation.getArgs()[0];
			ResultSet rs = stmt.getResultSet();
			ResultSetMetaData rsm = rs.getMetaData();
			String tmpStr = "";
			String tmpDateStr = "";

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					String name = rsm.getColumnLabel(i);// 获取列别名
					Object value = rs.getObject(name);
					map.put(CamelCaseUtils.toCamelCase(name), value);
					if (value instanceof String) {
						tmpStr = StringUtils.trimToEmpty((String) value);
						map.put(CamelCaseUtils.toCamelCase(name), tmpStr);
					} else if (value instanceof Date) {
						tmpDateStr = DateUtils.format((Date) value, "yyyyMMddHHmmss");
						if (Long.parseLong(tmpDateStr) == 0) {
							map.put(CamelCaseUtils.toCamelCase(name), null);
						}
					}
				}
				result.add(map);
			}
			return result;
		} else {
			return invocation.proceed();
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String prop1 = properties.getProperty("prop1");
		String prop2 = properties.getProperty("prop2");
		logger.debug("ResultSetHandlerInteceptor properties::" + prop1 + "------" + prop2);
	}
}
