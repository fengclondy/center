package cn.htd.searchcenter.dump.job.assembling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

@Service("itemAttrDumpJobAssembling")
public class ItemAttrDumpJonAssembling {

	@Resource(name = "itemAttrMasterHttpSolrServer")
	private HttpSolrClient cloudSolrClient;
	
	private static final Logger LOG = Logger
			.getLogger(ItemAttrDumpJonAssembling.class.getName());
	
	public void solrDataImport(Map<String, String> attrMap) throws SolrServerException, IOException{
		if(null != attrMap && !attrMap.isEmpty()){
			LOG.info("导入商品属性信息");
			List<SolrInputDocument> itemAttrSolrList = new ArrayList<SolrInputDocument>();
			for (String key : attrMap.keySet()) {
				if(StringUtils.isNotEmpty(key) && null != attrMap.get(key) && !attrMap.get(key).isEmpty()){
					LOG.info("cid : " + key);
					SolrInputDocument doc = new SolrInputDocument();
					doc.addField("cid", key);
					doc.addField("attrAll", attrMap.get(key));
					itemAttrSolrList.add(doc);
				}
			}
			flush(itemAttrSolrList);
		}
	}
	
	public boolean removeSolrData(String cid)
			throws SolrServerException, IOException {
		boolean ret = true;
		if (StringUtils.isNotEmpty(cid)) {
			cloudSolrClient.deleteByQuery("cid:" + cid);
			cloudSolrClient.commit();
		} else {
			ret = false;
		}
		return ret;
	}
	
	public void flush(List<SolrInputDocument> itemAttrSolrList)
			throws SolrServerException, IOException {
		if (itemAttrSolrList.size() > 0) {
			UpdateRequest req = new UpdateRequest();
			req.add(itemAttrSolrList);
			req.setCommitWithin(10000);
			req.process(cloudSolrClient);
		}
	}
	
}
