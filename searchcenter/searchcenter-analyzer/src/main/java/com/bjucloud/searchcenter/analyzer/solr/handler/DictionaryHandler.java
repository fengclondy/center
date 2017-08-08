package com.bjucloud.searchcenter.analyzer.solr.handler;

import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

import com.bjucloud.searchcenter.analyzer.service.DictionaryService;
import com.bjucloud.searchcenter.analyzer.service.DictionaryServiceImpl;

public class DictionaryHandler extends RequestHandlerBase {
	
	private DictionaryService ds = new DictionaryServiceImpl();

	@Override
	public String getDescription() {
		return "分词字典操作句柄";
	}

	@Override
	public void handleRequestBody(SolrQueryRequest arg0, SolrQueryResponse arg1) throws Exception {
		String operType = arg0.getParams().get(DictionaryService.OPER_TYPE);//操作类型
		String dictType = arg0.getParams().get(DictionaryService.DICT_TYPE);//字典类型
		String word = arg0.getParams().get("word");//分词
		ds.dealDict(operType, dictType, word);
	}

}