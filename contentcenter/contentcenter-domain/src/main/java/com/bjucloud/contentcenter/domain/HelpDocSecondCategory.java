package com.bjucloud.contentcenter.domain;

import java.util.List;

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
