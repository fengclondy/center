package com.bjucloud.searchcenter.analyzer.dic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjucloud.searchcenter.analyzer.cfg.Configuration;
import com.bjucloud.searchcenter.analyzer.cfg.DefaultConfig;
import com.bjucloud.searchcenter.analyzer.util.IOUtils;

/**
 * 词典管理类,单子模式
 */
public class Dictionary {

	private static final Logger log = LoggerFactory.getLogger(Dictionary.class);

	/** 词典单子实例 */
	private static Dictionary singleton;
	/** 主词典对象 */
	private DictSegment _MainDict;
	/** 停止词词典 */
	private DictSegment _StopWordDict;
	/** 量词词典 */
	private DictSegment _QuantifierDict;
	/** 配置对象 */
	private Configuration cfg;

	private Dictionary(Configuration cfg) {
		this.cfg = cfg;
		this.loadMainDict();
		this.loadStopWordDict();
		this.loadQuantifierDict();
	}

	/**
	 * 词典初始化 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
	 * 只有当Dictionary类被实际调用时，才会开始载入词典， 这将延长首次分词操作的时间 该方法提供了一个在应用加载阶段就初始化字典的手段
	 * 
	 * @return Dictionary
	 */
	public static Dictionary initial(Configuration cfg) {
		if (singleton == null) {
			synchronized (Dictionary.class) {
				if (singleton == null) {
					singleton = new Dictionary(cfg);
					return singleton;
				}
			}
		}
		return singleton;
	}

	/**
	 * 获取词典单子实例
	 * 
	 * @return Dictionary 单例对象
	 */
	public static Dictionary getSingleton() {
		if (singleton == null) {
			initial(DefaultConfig.getInstance());
		}
		return singleton;
	}

	/**
	 * 向主词典中添加单个分词
	 */
	public void addMainWord(String word, boolean isSaveToDictionaryFile) throws Exception {
		if (StringUtils.isNotBlank(word)) {
			singleton._MainDict.fillSegment(word.trim().toLowerCase().toCharArray());
		}

		if (isSaveToDictionaryFile) {// 保存至文件中
			FileUtils.writeStringToFile(new File(cfg.getExtDictionary()), word, true);
		}
	}

	/**
	 * 向主词典中添加分词列表
	 */
	public void addMainWords(List<String> words, boolean isSaveToDictionaryFile) throws Exception {
		if (CollectionUtils.isNotEmpty(words)) {
			for (String word : words) {
				if (StringUtils.isNotBlank(word)) {
					// 批量加载词条到主内存词典中
					singleton._MainDict.fillSegment(word.trim().toLowerCase().toCharArray());
				}
				if (isSaveToDictionaryFile) {// 保存至文件中
					FileUtils.writeStringToFile(new File(cfg.getExtDictionary()), word, true);
				}
			}
		}
	}

	/**
	 * 删除主词典分词列表
	 */
	public void deleteMainWords(List<String> words) {
		if (CollectionUtils.isNotEmpty(words)) {
			for (String word : words) {
				if (StringUtils.isNotBlank(word)) {
					// 批量屏蔽词条
					singleton._MainDict.disableSegment(word.trim().toLowerCase().toCharArray());
					try {
						IOUtils.deleteWordFromFile(cfg.getExtDictionary(), word);
					} catch (Exception e) {
						log.error("delete word error! dictpath:" + cfg.getExtDictionary() + "  word:" + word);
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 删除主词典分词
	 */
	public void deleteMainWord(String word) {
		if (StringUtils.isNotBlank(word)) {
			// 屏蔽词条
			singleton._MainDict.disableSegment(word.trim().toLowerCase().toCharArray());
			try {
				IOUtils.deleteWordFromFile(cfg.getExtDictionary(), word);
			} catch (Exception e) {
				log.error("delete word error! dictpath:" + cfg.getExtDictionary() + "  word:" + word);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向停用词典中添加单个分词
	 */
	public void addStopWord(String word, boolean isSaveToDictionaryFile) throws Exception {
		if (StringUtils.isNotBlank(word)) {
			singleton._StopWordDict.fillSegment(word.trim().toLowerCase().toCharArray());
		}

		if (isSaveToDictionaryFile) {// 保存至文件中
			FileUtils.writeStringToFile(new File(cfg.getStopWordDictionary()), word, true);
		}
	}

	/**
	 * 向停用词典中添加分词列表
	 */
	public void addStopWords(List<String> words, boolean isSaveToDictionaryFile) throws Exception {
		if (CollectionUtils.isNotEmpty(words)) {
			for (String word : words) {
				if (StringUtils.isNotBlank(word)) {
					// 批量加载词条到主内存词典中
					singleton._StopWordDict.fillSegment(word.trim().toLowerCase().toCharArray());
				}
				if (isSaveToDictionaryFile) {// 保存至文件中
					FileUtils.writeStringToFile(new File(cfg.getStopWordDictionary()), word, true);
				}
			}
		}
	}

	/**
	 * 删除停用词典分词列表
	 */
	public void deleteStopWords(List<String> words) {
		if (CollectionUtils.isNotEmpty(words)) {
			for (String word : words) {
				if (StringUtils.isNotBlank(word)) {
					// 批量屏蔽词条
					singleton._StopWordDict.disableSegment(word.trim().toLowerCase().toCharArray());
					try {
						IOUtils.deleteWordFromFile(cfg.getStopWordDictionary(), word);
					} catch (Exception e) {
						log.error("delete word error! dictpath:" + cfg.getStopWordDictionary() + "  word:" + word);
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 删除停用词典分词
	 */
	public void deleteStopWord(String word) {
		if (StringUtils.isNotBlank(word)) {
			// 屏蔽词条
			singleton._StopWordDict.disableSegment(word.trim().toLowerCase().toCharArray());
			try {
				IOUtils.deleteWordFromFile(cfg.getStopWordDictionary(), word);
			} catch (Exception e) {
				log.error("delete word error! dictpath:" + cfg.getStopWordDictionary() + "  word:" + word);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检索匹配主词典
	 * 
	 * @param charArray
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray) {
		return singleton._MainDict.match(charArray);
	}

	/**
	 * 检索匹配主词典
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray, int begin, int length) {
		return singleton._MainDict.match(charArray, begin, length);
	}

	/**
	 * 检索匹配量词词典
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInQuantifierDict(char[] charArray, int begin, int length) {
		return singleton._QuantifierDict.match(charArray, begin, length);
	}

	/**
	 * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
	 * 
	 * @param charArray
	 * @param currentIndex
	 * @param matchedHit
	 * @return Hit
	 */
	public Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1, matchedHit);
	}

	/**
	 * 判断是否是停止词
	 * 
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return boolean
	 */
	public boolean isStopWord(char[] charArray, int begin, int length) {
		return singleton._StopWordDict.match(charArray, begin, length).isMatch();
	}

	/**
	 * 加载主词典及扩展词典
	 */
	private void loadMainDict() {
		// 建立一个主词典实例
		_MainDict = new DictSegment((char) 0);
		loadDictionary(_MainDict, cfg.getMainDictionary());
		// 加载扩展词典
		this.loadExtDict();
	}

	/**
	 * 加载用户配置的扩展词典到主词库表
	 */
	private void loadExtDict() {
		// 加载扩展词典配置
		loadDictionary(_MainDict, cfg.getExtDictionary());
	}

	/**
	 * 加载用户扩展的停止词词典
	 */
	private void loadStopWordDict() {
		// 建立一个主词典实例
		_StopWordDict = new DictSegment((char) 0);
		// 加载扩展停止词典
		loadDictionary(_StopWordDict, cfg.getStopWordDictionary());
	}

	/**
	 * 加载量词词典
	 */
	private void loadQuantifierDict() {
		// 建立一个量词典实例
		_QuantifierDict = new DictSegment((char) 0);
		// 读取量词词典文件
		loadDictionary(_QuantifierDict, cfg.getQuantifierDicionary());
	}

	/**
	 * 加载字典
	 */
	private void loadDictionary(DictSegment segment, String dictFileName) {
		log.info("Dictionary loading " + dictFileName);
		// 读词典文件
		InputStream is = null;
		try {
			is = FileUtils.openInputStream(new File(dictFileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (ObjectUtils.equals(is, null)) {
			log.info("Dictionary not found " + dictFileName);
			return;
		}
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				log.debug("Dictionary word is " + theWord);
				if (StringUtils.isNotBlank(theWord)) {
					segment.fillSegment(theWord.trim().toLowerCase().toCharArray());
				}
			} while (theWord != null);
		} catch (IOException ioe) {
			log.error("Dictionary loading exception.");
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Configuration getCfg() {
		return cfg;
	}

	public void setCfg(Configuration cfg) {
		this.cfg = cfg;
	}

}
