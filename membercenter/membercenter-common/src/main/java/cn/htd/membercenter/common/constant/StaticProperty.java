package cn.htd.membercenter.common.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticProperty {
	private final static Logger logger = LoggerFactory.getLogger(StaticProperty.class);
	public static String YIJIFU_CORPORATE_DOWN_URL;
	public static String YIJIFU_CORPORATE_DOWN_KEY;
	public static String YIJIFU_CORPORATE_DOWN_PARENT_ID;
	public static String ERP_SELLERUP_CALLBACK_URL;
	public static String toERPIPAddress;
	public static String toJSYHIPAddress;
	public static String toHNDIPAddress;
	public static String TOKEN_URL;
	public static String MIDDLEWAREERP_URL;
	public static String YIJIFU_CALLBACK_URL;

	static {
		logger.info("class:StaticProperty初始化开始");
		Properties p = new Properties();
		try {
			InputStream in = StaticProperty.class.getResourceAsStream("/membercenter.properties");
			p.load(in);
			in.close();
			YIJIFU_CORPORATE_DOWN_URL = p.getProperty("yijifuCorporateDownUrl");
			YIJIFU_CORPORATE_DOWN_KEY = p.getProperty("yijifuCorporateDownKey");
			YIJIFU_CORPORATE_DOWN_PARENT_ID = p.getProperty("yijifuCorporateDownParentId");
			toERPIPAddress = p.getProperty("toERPIPAddress");
			toJSYHIPAddress = p.getProperty("toJSYHIPAddress");
			toHNDIPAddress = p.getProperty("toHNDIPAddress");
			MIDDLEWAREERP_URL = p.getProperty("middlewareerp.url");
			ERP_SELLERUP_CALLBACK_URL = p.getProperty("erpSellerupCallbackUrl");
			TOKEN_URL = p.getProperty("tokenUrl");
			YIJIFU_CALLBACK_URL = p.getProperty("yijifuCallbackUrl");
			logger.info("class:StaticProperty初始化结束");
		} catch (IOException e) {
			logger.error("class:StaticProperty初始化异常" + e);
		}

	}
}
