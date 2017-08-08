package cn.htd.searchcenter.dump.job.assembling;
import java.util.HashMap;
import java.util.Map;

public class DumpConstant {

	// 商品导入数量
	public static final int ITEM_IMPORT_COUNT = 5000;
	
	// 内部大厅更新标识
	public static final int HTDPUBLIC_ITEM_SYNC_TYPE = 1;

	// 内部包厢更新标识
	public static final int HTDBOX_ITEM_SYNC_TYPE = 2;

	// 京东+商品更新标识
	public static final int JD_ITEM_SYNC_TYPE = 3;

	// 外部商品更新标识
	public static final int EXTERNAL_ITEM_SYNC_TYPE = 4;

	// 秒杀商品更新标识
	public static final int SECKILL_ITEM_SYNC_TYPE = 5;

	// 店铺更新标识
	public static final int SHOP_SYNC_TYPE = 6;
	
	//全量更新标识
	public static final int ITEM_ALL_SYNC_TYPE = 7;
	
	//商品属性更新标识
	public static final int ITEM_ATTR_SYNC_TYPE = 8;

	// 内部大厅商品job启动标识
	public static final int HTDPUBLIC_ITEM_RUN_TYPE = 1;

	// 内部包厢商品job启动标识
	public static final int HTDBOX_ITEM_RUN_TYPE = 2;
	
	// 京东+商品商品job启动标识
	public static final int JD_ITEM_RUN_TYPE = 3;
	
	// 外部商品job启动标识
	public static final int EXTERNAL_ITEM_RUN_TYPE = 4;
	
	// 秒杀商品job启动标识
	public static final int SECKILL_ITEM_RUN_TYPE = 5;
	
	// 店铺job启动标识
	public static final int SHOP_RUN_TYPE = 6;
	
	//全量job启动标识
	public static final int ITEM_ALL_RUN_TYPE= 7;
	
	//商品属性更新标识
	public static final int ITEM_ATTR_RUN_TYPE = 8;
	
	public static Map<String, String> AREAMAP = new HashMap<String, String>();
	
	public static Map<String, String> ATTRMAP =  new HashMap<String, String>();
}
