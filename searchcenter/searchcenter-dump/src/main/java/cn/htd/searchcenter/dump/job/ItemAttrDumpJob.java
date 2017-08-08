package cn.htd.searchcenter.dump.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import cn.htd.searchcenter.domain.SyncTime;
import cn.htd.searchcenter.dump.job.assembling.DumpConstant;
import cn.htd.searchcenter.dump.job.assembling.ItemAttrDumpJonAssembling;
import cn.htd.searchcenter.service.ItemDictionaryService;
import cn.htd.searchcenter.service.RunFlagService;
import cn.htd.searchcenter.service.SyncTimeService;

public class ItemAttrDumpJob {
	private static final Logger LOG = Logger.getLogger(ItemAttrDumpJob.class
			.getName());

	@Resource
	private RunFlagService runFlagService;
	@Resource
	private SyncTimeService syncTimeService;
	@Resource
	private ItemDictionaryService itemDictionaryService;
	@Resource
	private ItemAttrDumpJonAssembling itemAttrDumpJobAssembling;

	public void dump() {
		boolean timeFlag = true;
		String runFlag = runFlagService
				.queryJobRunFlagByType(DumpConstant.ITEM_ATTR_RUN_TYPE);
		if (null == runFlag || "1".equals(runFlag)) {
			return;
		}
		runFlagService.updateRunFlagById(DumpConstant.ITEM_ATTR_SYNC_TYPE, "1");
		SyncTime syncTime = null;
		Date beginDate = new Date();
		try {
			LOG.info("商品属性任务开始");
			syncTime = syncTimeService
					.getSyncTimeByType(DumpConstant.ITEM_ATTR_SYNC_TYPE);
			Date lastSyncTime = syncTime.getLastSyncTime();
			// 获取商品信息
			List<String> cidList = itemDictionaryService
					.queryItemCategoryCidBySyncTime(lastSyncTime);
			if (null != cidList && cidList.size() > 0) {
				Map<String, String> attrMap = itemDictionaryService.queryItemAttrByCid(cidList);
				itemAttrDumpJobAssembling.solrDataImport(attrMap);
			}
		} catch (Exception e) {
			timeFlag = false;
			LOG.error("itemAttr job is error", e);
		} finally {
			runFlagService.updateRunFlagById(DumpConstant.ITEM_ATTR_RUN_TYPE,
					"0");
		}

		if (timeFlag) {
			syncTime.setLastSyncTime(beginDate);
			syncTimeService.updateById(syncTime);
		}
		Date endDate = new Date();
		LOG.info("商品属性任务结束");
		LOG.info("商品属性本次导入总耗时：" + (endDate.getTime() - beginDate.getTime())
				/ 1000 + "秒");
	}
}
