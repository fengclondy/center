package com.bjucloud.searchcenter.analyzer;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void suggest(){
		System.out.println((new Date()).getTime());
		String zkHostString = "10.2.0.13:2181,10.2.0.13:2182,10.2.0.13:2183";
		SolrClient solr = new CloudSolrClient(zkHostString);
		SolrQuery parameters = new SolrQuery();
		parameters.setRequestHandler("/suggest");
		parameters.set("suggest.q", "尚");
		parameters.set("suggest", true);
		parameters.set("suggest.build", true);
		parameters.set("suggest.dictionary", "mySuggester");
		parameters.setStart(0);
		parameters.setRows(10);
		
		try {
			QueryResponse response = solr.query("suggest",parameters);
			System.out.println(response.getResponse());
			solr.close();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println((new Date()).getTime());
	}
}
