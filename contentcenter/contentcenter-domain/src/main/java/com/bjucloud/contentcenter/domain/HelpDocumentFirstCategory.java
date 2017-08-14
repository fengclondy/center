package com.bjucloud.contentcenter.domain;

import java.util.List;

public class HelpDocumentFirstCategory extends HelpDocCategory{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8147781211499213915L;
	
	
	private List<HelpDocSecondCategory> secondCategoryList;
	
	private HelpDocTopic currentHelpDocTopic;

	public List<HelpDocSecondCategory> getSecondCategoryList() {
		return secondCategoryList;
	}

	public void setSecondCategoryList(List<HelpDocSecondCategory> secondCategoryList) {
		this.secondCategoryList = secondCategoryList;
	}

	public HelpDocTopic getCurrentHelpDocTopic() {
		return currentHelpDocTopic;
	}

	public void setCurrentHelpDocTopic(HelpDocTopic currentHelpDocTopic) {
		this.currentHelpDocTopic = currentHelpDocTopic;
	}
	


	
	
	

}
