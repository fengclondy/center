package com.bjucloud.searchcenter.analyzer.service;

import com.bjucloud.searchcenter.analyzer.dic.Dictionary;

public class DictionaryServiceImpl implements DictionaryService {

	
	public void dealDict(String operType, String dictType, String word) throws Exception {
		Dictionary dt = Dictionary.getSingleton();
		if (ADD_WORD.equals(operType)) {
			if (STOPWORD_DICT.equals(dictType)) {
				dt.addStopWord(word, true);
			}
			if (MAIN_DICT.equals(dictType)) {
				dt.addMainWord(word, true);
			}
		}
		if (DELETE_WORD.equals(operType)) {
			if (STOPWORD_DICT.equals(dictType)) {
				dt.deleteStopWord(word);
			}
			if (MAIN_DICT.equals(dictType)) {
				dt.deleteMainWord(word);
			}
		}
	}

}
