package cn.htd.searchcenter.dump.job;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import cn.htd.searchcenter.domain.SyncTime;
import cn.htd.searchcenter.dump.job.assembling.DumpConstant;
import cn.htd.searchcenter.service.RunFlagService;
import cn.htd.searchcenter.service.SyncTimeService;

public class ItemAllDumpJob {

	private static final Logger LOG = Logger
			.getLogger(ItemAllDumpJob.class.getName());
	
	@Resource
	private RunFlagService runFlagService;
	@Resource
	private SyncTimeService syncTimeService;
	@Resource(name = "itemMasterHttpSolrServer")
	private HttpSolrClient itemSolrClient;
	@Resource(name = "shopMasterHttpSolrServer")
	private HttpSolrClient shopSolrClient;
	@Resource(name = "itemAttrMasterHttpSolrServer")
	private HttpSolrClient itemAttrSolrClient;
	
	public void dump() {
		boolean timeFlag = true;
		Date beginDate = new Date();
		SyncTime syncTime = null;
		Integer updateFlag = runFlagService.updateAllRunFlag("1");
		LOG.info("全量任务开始");
		if(null == updateFlag || updateFlag.intValue() == 0){
			return;
		}
		try {
			LOG.info("全量导入商品任务开始");
			syncTime = syncTimeService.getSyncTimeByType(DumpConstant.ITEM_ALL_SYNC_TYPE);
			if(null != updateFlag && updateFlag.intValue() > 0){
				itemSolrClient.deleteByQuery("*:*");
				itemSolrClient.commit();
				shopSolrClient.deleteByQuery("*:*");
				shopSolrClient.commit();
				itemAttrSolrClient.deleteByQuery("*:*");
				itemAttrSolrClient.commit();
//				runFlagService.updateAllRunFlag("1");
				Calendar c = Calendar.getInstance();
		        c.add(Calendar.YEAR, -4);
				syncTimeService.updateSyncTime(c.getTime());
			}
		} catch (Exception e) {
			LOG.error("全量导入异常!", e);
		}finally{
			runFlagService.updateAllRunFlagForce("0");
		}
		if (timeFlag) {
			syncTime.setLastSyncTime(beginDate);
			syncTimeService.updateById(syncTime);
		}
		LOG.info("全量导入商品任务结束");
	}
}
