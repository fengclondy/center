package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.ExecuteResult;
import com.bjucloud.contentcenter.dao.HelpDocumentCategoryDAO;
import com.bjucloud.contentcenter.dao.HelpDocumentTopicDAO;
import com.bjucloud.contentcenter.domain.*;

import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;
import com.bjucloud.contentcenter.service.HelpDocToMallService;
import com.bjucloud.contentcenter.common.constants.HelpDocConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Purpose 帮助中心service
 * @author zf.zhang
 * @since 2017-3-30 19:16
 * 
 */
@Service("helpDocToMallService")
public class HelpDocToMallServiceImpl implements HelpDocToMallService {
	
	private final static Logger logger = LoggerFactory.getLogger(HelpDocToMallServiceImpl.class);
	
	@Resource
	private HelpDocumentCategoryDAO helpDocumentCategoryDAO;
	
	@Resource
	private HelpDocumentTopicDAO helpDocumentTopicDAO;


	@Override
	public ExecuteResult<List<HelpDocCategory>> getCategoriesByParentCategoryCode(String parentCategoryCode) {
		
		logger.info("HelpDocumentServiceImpl--->getCategoriesByParentCategoryCode--->parentCategoryCode:" + parentCategoryCode);
		
		ExecuteResult<List<HelpDocCategory>> result = new ExecuteResult<List<HelpDocCategory>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setParentCategoryCode(parentCategoryCode);
			List<HelpDocCategory> helpDocCategoryList = helpDocumentCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			result.setResult(helpDocCategoryList);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getCategoriesByParentCategoryCode", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}
	

	@Override
	public ExecuteResult<List<HelpDocTopic>> getTopicsByCategoryCode(String categoryCode) {
		
		logger.info("HelpDocumentServiceImpl--->getTopicsByCategoryCode--->categoryCode:" + categoryCode);
		
		ExecuteResult<List<HelpDocTopic>> result = new ExecuteResult<List<HelpDocTopic>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocTopicDTO helpDocTopicDTO = new HelpDocTopicDTO();
//			helpDocTopicDTO.setCategoryCode(categoryCode);
			helpDocTopicDTO.setSecondCategoryCode(categoryCode);;
			List<HelpDocTopic> helpDocTopicList = helpDocumentTopicDAO.getHelpDocTopicsByParams(helpDocTopicDTO);
			result.setResult(helpDocTopicList);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getTopicsByCategoryCode", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}


	@Override
	public ExecuteResult<HelpDocTopic> getTopicByTopictCode(String topictCode) {
		
		logger.info("HelpDocumentServiceImpl--->getTopicsByTopictCode--->topictCode:" + topictCode);
		
		ExecuteResult<HelpDocTopic> result = new ExecuteResult<HelpDocTopic>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocTopicDTO helpDocTopicDTO = new HelpDocTopicDTO();
			helpDocTopicDTO.setTopictCode(topictCode);
			List<HelpDocTopic> helpDocTopicList = helpDocumentTopicDAO.getHelpDocTopicsByParams(helpDocTopicDTO);
			if(null != helpDocTopicList && helpDocTopicList.size() == 1){
				result.setResult(helpDocTopicList.get(0));
			}else{
				logger.info("There are no eligible topics , helpDocTopicList:" + helpDocTopicList);
				result.setResult(null);
			}
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getTopicsByTopictCode", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}
	
	@Override
	public ExecuteResult<HelpDocumentFirstCategory> getFirstCategoriesByFirstCategory(String firstCategory) {
		
		logger.info("HelpDocumentServiceImpl--->getFirstCategoriesByFirstCategory--->firstCategory:" + firstCategory);

        HelpDocumentFirstCategory helpDocFirstCategory = new HelpDocumentFirstCategory();
		
		HelpDocTopic currentHelpDocTopic = new  HelpDocTopic();
		
		ExecuteResult<HelpDocumentFirstCategory> result = new ExecuteResult<HelpDocumentFirstCategory>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setCategoryCode(firstCategory);
			//当前分类
			HelpDocCategory currentHelpDocCategory = helpDocumentCategoryDAO.getSingleHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == currentHelpDocCategory){
				logger.info("===>currentHelpDocCategory:" + currentHelpDocCategory);
				result.setResult(null);
				return result;
			}
			// 设置一级分类
			helpDocFirstCategory.setCategoryCode(currentHelpDocCategory.getCategoryCode());
			helpDocFirstCategory.setCategoryName(currentHelpDocCategory.getCategoryName());
			
			helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setParentCategoryCode(firstCategory);
			//查询二级分类
			List<HelpDocCategory> secondCategoryList = helpDocumentCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == secondCategoryList || secondCategoryList.size() < 1){
				logger.info("===>secondCategoryList:" + secondCategoryList);
				result.setResult(helpDocFirstCategory);
				return result;
			}
			
			List<HelpDocSecondCategory> helpDocSecondCategoryList = new ArrayList<HelpDocSecondCategory>();
			HelpDocSecondCategory helpDocSecondCategory = null;
			for(int i=0;i<secondCategoryList.size();i++){
				helpDocSecondCategory = new HelpDocSecondCategory();
				// 设置二级分类
				helpDocSecondCategory.setCategoryCode(secondCategoryList.get(i).getCategoryCode());
				helpDocSecondCategory.setCategoryName(secondCategoryList.get(i).getCategoryName());
				
				HelpDocTopicDTO helpDocTopicDTO = new HelpDocTopicDTO();
				helpDocTopicDTO.setSecondCategoryCode(secondCategoryList.get(i).getCategoryCode());;
				List<HelpDocTopic> helpDocTopicList = helpDocumentTopicDAO.getNoContentFieldOfTopicsByParams(helpDocTopicDTO);
				// 设置二级分类主题
				helpDocSecondCategory.setHelpDocTopicList(helpDocTopicList);
				
				// 设置一个默认主题
				if(i==0){
	
					if(null != helpDocTopicList && helpDocTopicList.size() > 0){
						
						String currentTopicCode = helpDocTopicList.get(0).getTopictCode();
						//查询当前主题
						HelpDocTopic currentTopic = helpDocumentTopicDAO.getHelpDocTopicsByTopicCode(currentTopicCode);
						//设置当前主题
						currentHelpDocTopic.setTopictCode(currentTopic.getTopictCode());
						currentHelpDocTopic.setTopictName(currentTopic.getTopictName());
						currentHelpDocTopic.setCategoryCode(currentTopic.getCategoryCode());
						currentHelpDocTopic.setCategoryName(currentTopic.getCategoryName());
						currentHelpDocTopic.setSecondCategoryCode(currentTopic.getSecondCategoryCode());
						currentHelpDocTopic.setSecondCategoryName(currentTopic.getSecondCategoryName());
						//设置当前内容
						currentHelpDocTopic.setContent(currentTopic.getContent());
						currentHelpDocTopic.setVisitUrl(currentTopic.getVisitUrl());
					}
					
				}
				//添加二级分类
				helpDocSecondCategoryList.add(helpDocSecondCategory);
			}
			
			//设置二级分类集合
			helpDocFirstCategory.setSecondCategoryList(helpDocSecondCategoryList);
			//设置当前选中主题
			helpDocFirstCategory.setCurrentHelpDocTopic(currentHelpDocTopic);
			
			
			result.setResult(helpDocFirstCategory);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getFirstCategoriesByFirstCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}
	
	@Override
    public ExecuteResult<HelpDocumentFirstCategory> getSecondCategoriesByFirstCategory(String secondCategory) {
		
		logger.info("HelpDocumentServiceImpl--->getSecondCategoriesByFirstCategory--->secondCategory:" + secondCategory);

        HelpDocumentFirstCategory helpDocFirstCategory = new HelpDocumentFirstCategory();
		
		HelpDocTopic currentHelpDocTopic = new  HelpDocTopic();
		
		ExecuteResult<HelpDocumentFirstCategory> result = new ExecuteResult<HelpDocumentFirstCategory>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setCategoryCode(secondCategory);;
			//当前分类
			HelpDocCategory currentHelpDocCategory = helpDocumentCategoryDAO.getSingleHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == currentHelpDocCategory){
				logger.info("===>firstHelpDocCategory:" + currentHelpDocCategory);
				result.setResult(null);
				return result;
			}
			// 设置一级分类
			helpDocFirstCategory.setCategoryCode(currentHelpDocCategory.getParentCategoryCode());
			helpDocFirstCategory.setCategoryName(currentHelpDocCategory.getParentCategoryName());
			
			helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setParentCategoryCode(currentHelpDocCategory.getParentCategoryCode());
			//查询二级分类
			List<HelpDocCategory> secondCategoryList = helpDocumentCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == secondCategoryList || secondCategoryList.size() < 1){
				logger.info("===>secondCategoryList:" + secondCategoryList);
				result.setResult(null);
				return result;
			}
			
			List<HelpDocSecondCategory> helpDocSecondCategoryList = new ArrayList<HelpDocSecondCategory>();
			HelpDocSecondCategory helpDocSecondCategory = null;
			for(int i=0;i<secondCategoryList.size();i++){
				helpDocSecondCategory = new HelpDocSecondCategory();
				// 设置二级分类
				helpDocSecondCategory.setCategoryCode(secondCategoryList.get(i).getCategoryCode());
				helpDocSecondCategory.setCategoryName(secondCategoryList.get(i).getCategoryName());
				
				HelpDocTopicDTO helpDocTopicDTO = new HelpDocTopicDTO();
				helpDocTopicDTO.setSecondCategoryCode(secondCategoryList.get(i).getCategoryCode());;
				List<HelpDocTopic> helpDocTopicList = helpDocumentTopicDAO.getNoContentFieldOfTopicsByParams(helpDocTopicDTO);
				// 设置二级分类主题
				helpDocSecondCategory.setHelpDocTopicList(helpDocTopicList);
				
				// 设置一个当前主题
				if(secondCategoryList.get(i).getCategoryCode().equals(currentHelpDocCategory.getCategoryCode())){
				
					if(null != helpDocTopicList && helpDocTopicList.size() > 0){
						
						String currentTopicCode = helpDocTopicList.get(0).getTopictCode();
						//查询当前主题
						HelpDocTopic currentTopic = helpDocumentTopicDAO.getHelpDocTopicsByTopicCode(currentTopicCode);
						//设置当前主题
						currentHelpDocTopic.setTopictCode(currentTopic.getTopictCode());
						currentHelpDocTopic.setTopictName(currentTopic.getTopictName());
						currentHelpDocTopic.setCategoryCode(currentTopic.getCategoryCode());
						currentHelpDocTopic.setCategoryName(currentTopic.getCategoryName());
						currentHelpDocTopic.setSecondCategoryCode(currentTopic.getSecondCategoryCode());
						currentHelpDocTopic.setSecondCategoryName(currentTopic.getSecondCategoryName());
						//设置当前内容
						currentHelpDocTopic.setContent(currentTopic.getContent());
						currentHelpDocTopic.setVisitUrl(currentTopic.getVisitUrl());
					}
					
				}
				
				//添加二级分类
				helpDocSecondCategoryList.add(helpDocSecondCategory);
			}
			
			//设置二级分类集合
			helpDocFirstCategory.setSecondCategoryList(helpDocSecondCategoryList);
			//设置当前选中主题
			helpDocFirstCategory.setCurrentHelpDocTopic(currentHelpDocTopic);
			
			
			result.setResult(helpDocFirstCategory);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getSecondCategoriesByFirstCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}

	@Override
    public ExecuteResult<HelpDocumentFirstCategory> getHelpDocTopicByTopictCode(String topictCode) {
		
		logger.info("HelpDocumentServiceImpl--->getHelpDocTopicByTopictCode--->topictCode:" + topictCode);

        HelpDocumentFirstCategory helpDocFirstCategory = new HelpDocumentFirstCategory();
		
		HelpDocTopic currentHelpDocTopic = new  HelpDocTopic();
		
		ExecuteResult<HelpDocumentFirstCategory> result = new ExecuteResult<HelpDocumentFirstCategory>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			//查询当前主题
			HelpDocTopic currentTopic = helpDocumentTopicDAO.getHelpDocTopicsByTopicCode(topictCode);
			if(null == currentTopic){
				logger.info("===>currentTopic:" + currentTopic);
				result.setResult(null);
				return result;
			}
			
			//设置当前主题
			currentHelpDocTopic.setTopictCode(currentTopic.getTopictCode());
			currentHelpDocTopic.setTopictName(currentTopic.getTopictName());
			currentHelpDocTopic.setCategoryCode(currentTopic.getCategoryCode());
			currentHelpDocTopic.setCategoryName(currentTopic.getCategoryName());
			currentHelpDocTopic.setSecondCategoryCode(currentTopic.getSecondCategoryCode());
			currentHelpDocTopic.setSecondCategoryName(currentTopic.getSecondCategoryName());
			//设置当前内容
			currentHelpDocTopic.setContent(currentTopic.getContent());
			currentHelpDocTopic.setVisitUrl(currentTopic.getVisitUrl());
			
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setCategoryCode(currentTopic.getCategoryCode());
			//一级分类
			HelpDocCategory firstHelpDocCategory = helpDocumentCategoryDAO.getSingleHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == firstHelpDocCategory){
				logger.info("===>firstHelpDocCategory:" + firstHelpDocCategory);
				result.setResult(null);
				return result;
			}
			// 设置一级分类
			helpDocFirstCategory.setCategoryCode(firstHelpDocCategory.getCategoryCode());
			helpDocFirstCategory.setCategoryName(firstHelpDocCategory.getCategoryName());
			
			helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setParentCategoryCode(firstHelpDocCategory.getCategoryCode());
			//查询二级分类
			List<HelpDocCategory> secondCategoryList = helpDocumentCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == secondCategoryList || secondCategoryList.size() < 1){
				logger.info("===>secondCategoryList:" + secondCategoryList);
				result.setResult(null);
				return result;
			}
			
			List<HelpDocSecondCategory> helpDocSecondCategoryList = new ArrayList<HelpDocSecondCategory>();
			HelpDocSecondCategory helpDocSecondCategory = null;
			for(int i=0;i<secondCategoryList.size();i++){
				helpDocSecondCategory = new HelpDocSecondCategory();
				 //二级分类
				helpDocSecondCategory.setCategoryCode(secondCategoryList.get(i).getCategoryCode());
				helpDocSecondCategory.setCategoryName(secondCategoryList.get(i).getCategoryName());
				
					HelpDocTopicDTO helpDocTopicDTO = new HelpDocTopicDTO();
					helpDocTopicDTO.setSecondCategoryCode(secondCategoryList.get(i).getCategoryCode());;
					List<HelpDocTopic> helpDocTopicList = helpDocumentTopicDAO.getNoContentFieldOfTopicsByParams(helpDocTopicDTO);
					if(null != helpDocTopicList && helpDocTopicList.size() > 0){
						// 设置二级分类主题
						helpDocSecondCategory.setHelpDocTopicList(helpDocTopicList);
					}
				
				//添加二级分类
				helpDocSecondCategoryList.add(helpDocSecondCategory);
			}
			
			//设置二级分类集合
			helpDocFirstCategory.setSecondCategoryList(helpDocSecondCategoryList);
			//设置当前选中主题
			helpDocFirstCategory.setCurrentHelpDocTopic(currentHelpDocTopic);
			
			
			result.setResult(helpDocFirstCategory);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getHelpDocTopicByTopictCode", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}


}
