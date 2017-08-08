package cn.htd.searchcenter.dump.job.load;

import java.util.Calendar;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import cn.htd.searchcenter.service.ItemDictionaryService;
import cn.htd.searchcenter.service.RunFlagService;
import cn.htd.searchcenter.service.SyncTimeService;

public class UpdateRunFlagLoad implements
		ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOG = Logger
			.getLogger(UpdateRunFlagLoad.class.getName());
	@Resource
	private RunFlagService runFlagService;
	@Resource
	private SyncTimeService syncTimeService;
	@Resource
	private ItemDictionaryService itemDictionaryService;
	@Resource(name = "itemMasterHttpSolrServer")
	private HttpSolrClient itemSolrClient;
	@Resource(name = "shopMasterHttpSolrServer")
	private HttpSolrClient shopSolrClient;
	@Resource(name = "itemAttrMasterHttpSolrServer")
	private HttpSolrClient itemAttrSolrClient;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		try {
			itemSolrClient.deleteByQuery("*:*");
			itemSolrClient.commit();
			shopSolrClient.deleteByQuery("*:*");
			shopSolrClient.commit();
			itemAttrSolrClient.deleteByQuery("*:*");
			itemAttrSolrClient.commit();
			runFlagService.updateAllRunFlag("0");
			Calendar c = Calendar.getInstance();
	        c.add(Calendar.YEAR, -4);
			syncTimeService.updateSyncTime(c.getTime());
		} catch (Exception e) {
			LOG.error("UpdateRunFlagLoad is error", e);
			e.printStackTrace();
		}
	}
}
