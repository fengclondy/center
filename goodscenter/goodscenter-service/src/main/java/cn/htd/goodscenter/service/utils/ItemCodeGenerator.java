package cn.htd.goodscenter.service.utils;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.SpringApplicationContextHolder;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 商品编码生成器
 * @author zhangxiaolong
 *
 */
public class ItemCodeGenerator {
	/**
	 * redis key : item_code
	 */
	private static final String REDIS_ITEM_CODE = "B2B_MIDDLE_PRODUCT_ITEM_CODE_SEQ";

	private static final String REDIS_SKU_CODE = "B2B_MIDDLE_PRODUCT_SKU_CODE_SEQ";

	private static final String REDIS_SPU_CODE = "B2B_MIDDLE_PRODUCT_SPU_CODE_SEQ";

	private static final String INIT_ITEM_CODE = "10000000";

	private static final String INIT_SKU_CODE = "1000000000";

	private static final String INIT_SPU_CODE = "20000000";

	/**
	 * ItemCode的生成
	 * @return
	 */
	public static String generateItemCode(){
		RedisDB redisDB = SpringApplicationContextHolder.getBean(RedisDB.class);
		ItemMybatisDAO itemMybatisDAO = SpringApplicationContextHolder.getBean(ItemMybatisDAO.class);
		if (!redisDB.exists(REDIS_ITEM_CODE)) { // redis不存在
			String dbMaxCode = itemMybatisDAO.queryMaxItemCode();
			if (StringUtils.isEmpty(dbMaxCode)) {
				dbMaxCode = INIT_ITEM_CODE;
			}
			redisDB.set(REDIS_ITEM_CODE, dbMaxCode);
		}
		return String.valueOf(redisDB.incr(REDIS_ITEM_CODE));
	}
	/**
	 * SkuCode的生成
	 * @return
	 */
	public static String generateSkuCode(){
		RedisDB redisDB = SpringApplicationContextHolder.getBean(RedisDB.class);
		ItemMybatisDAO itemMybatisDAO = SpringApplicationContextHolder.getBean(ItemMybatisDAO.class);
		if (!redisDB.exists(REDIS_SKU_CODE)) { // redis不存在
			String dbMaxCode = itemMybatisDAO.queryMaxSkuCode();
			if (StringUtils.isEmpty(dbMaxCode)) {
				dbMaxCode = INIT_SKU_CODE;
			}
			redisDB.set(REDIS_SKU_CODE, dbMaxCode);
		}
		return String.valueOf(redisDB.incr(REDIS_SKU_CODE));
	}
	
	public static String generateSpuCode(){
		RedisDB redisDB = SpringApplicationContextHolder.getBean(RedisDB.class);
		ItemMybatisDAO itemMybatisDAO = SpringApplicationContextHolder.getBean(ItemMybatisDAO.class);
		if (!redisDB.exists(REDIS_SPU_CODE)) { // redis不存在
			String dbMaxCode = itemMybatisDAO.queryMaxSpuCode();
			if (StringUtils.isEmpty(dbMaxCode)) {
				dbMaxCode = INIT_SPU_CODE;
			}
			redisDB.set(REDIS_SPU_CODE, dbMaxCode);
		}
		return String.valueOf(redisDB.incr(REDIS_SPU_CODE));
	}
}
