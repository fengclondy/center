package cn.htd.membercenter.domain;

import java.util.List;

import cn.htd.membercenter.domain.HelpDocCategory;
import cn.htd.membercenter.domain.HelpDocTopic;

public class HelpDocSecondCategory extends HelpDocCategory{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4569980615851943114L;
	
	
	private List<HelpDocTopic> helpDocTopicList;


	public List<HelpDocTopic> getHelpDocTopicList() {
		return helpDocTopicList;
	}

	public void setHelpDocTopicList(List<HelpDocTopic> helpDocTopicList) {
		this.helpDocTopicList = helpDocTopicList;
	}

	

}
