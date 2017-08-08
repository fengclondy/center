package com.bjucloud.searchcenter.analyzer;

import org.junit.Assert;
import org.junit.Test;

import com.bjucloud.searchcenter.analyzer.cfg.Configuration;
import com.bjucloud.searchcenter.analyzer.cfg.DefaultConfig;
import com.bjucloud.searchcenter.analyzer.dic.Dictionary;

public class DictionaryTest {

	@Test
	public void getInstance() {
		Configuration conf = DefaultConfig.getInstance();
		System.out.println(conf.getMainDictionary());
		Dictionary dt = Dictionary.initial(conf);

		Assert.assertTrue(dt != null);
	}
}
