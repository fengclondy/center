package cn.htd.searchcenter.dump.job;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import cn.htd.searchcenter.domain.ShopDTO;
import cn.htd.searchcenter.domain.SyncTime;
import cn.htd.searchcenter.dump.job.assembling.DumpConstant;
import cn.htd.searchcenter.dump.job.assembling.ShopDumpJobAssembling;
import cn.htd.searchcenter.service.RunFlagService;
import cn.htd.searchcenter.service.ShopExportService;
import cn.htd.searchcenter.service.SyncTimeService;

public class ShopDumpJob {

	private static final Logger LOG = Logger.getLogger(ShopDumpJob.class
			.getName());
	/**
	 * @Resource private CloudSolrClient cloudSolrClient;
	 * @Resource private CloudSolrClient cloudSolrSearchClient;
	 */

	@Resource
	private ShopExportService shopExportService;
	@Resource
	private SyncTimeService syncTimeService;
	@Resource
	private RunFlagService runFlagService;
	@Resource
	private ShopDumpJobAssembling shopDumpJobAssembling;

	public void dump() {
		boolean timeFlag = true;
		String runFlag = runFlagService
				.queryJobRunFlagByType(DumpConstant.SHOP_RUN_TYPE);
		if (null == runFlag || "1".equals(runFlag)) {
			return;
		}
		runFlagService.updateRunFlagById(DumpConstant.SHOP_RUN_TYPE, "1");
		SyncTime syncTime = null;
		Date beginDate = new Date();

		try {
			LOG.info("店铺任务开始");
			syncTime = syncTimeService
					.getSyncTimeByType(DumpConstant.SHOP_SYNC_TYPE);
			Date lastSyncTime = syncTime.getLastSyncTime();
			// 获取店铺信息
			int countShop = shopExportService
					.queryShopInfoCountBySyncTime(lastSyncTime);
			if (countShop > 0) {
				int surplus = countShop / DumpConstant.ITEM_IMPORT_COUNT;
				if (surplus > 0) {
					surplus = surplus + 1;
					for (int i = 0; i < surplus; i++) {
						List<ShopDTO> list = shopExportService
								.queryShopInfoBySyncTime(lastSyncTime, i
										* DumpConstant.ITEM_IMPORT_COUNT,
										DumpConstant.ITEM_IMPORT_COUNT);
						shopDumpJobAssembling
								.solrDataImport(list, lastSyncTime);
					}
				} else {
					List<ShopDTO> list = shopExportService
							.queryShopInfoBySyncTime(lastSyncTime, 0,
									DumpConstant.ITEM_IMPORT_COUNT);
					shopDumpJobAssembling.solrDataImport(list, lastSyncTime);
				}
			}
		} catch (Exception e) {
			timeFlag = false;
			LOG.error("shop job is error", e);
		} finally {
			runFlagService.updateRunFlagById(DumpConstant.SHOP_RUN_TYPE, "0");
		}

		if (timeFlag) {
			syncTime.setLastSyncTime(beginDate);
			syncTimeService.updateById(syncTime);
		}
		Date endDate = new Date();
		LOG.info("店铺任务结束");
		LOG.info("本次导入总耗时：" + (endDate.getTime() - beginDate.getTime()) / 1000
				+ "秒");
	}
}
