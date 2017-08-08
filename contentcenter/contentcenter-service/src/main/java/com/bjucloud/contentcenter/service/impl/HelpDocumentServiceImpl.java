package com.bjucloud.contentcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;

import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.common.constants.HelpDocConstants;
import com.bjucloud.contentcenter.common.utils.HelpDocumentUtils;
import com.bjucloud.contentcenter.dao.HelpDocCategoryDAO;
import com.bjucloud.contentcenter.dao.HelpDocTopicDAO;
import com.bjucloud.contentcenter.domain.HelpDocCategory;
import com.bjucloud.contentcenter.domain.HelpDocFirstCategory;
import com.bjucloud.contentcenter.domain.HelpDocTopic;
import com.bjucloud.contentcenter.dto.HelpDocCategoryDTO;
import com.bjucloud.contentcenter.dto.HelpDocTopicDTO;
import com.bjucloud.contentcenter.service.HelpDocumentService;

/**
 * @Purpose 帮助中心service
 * @author zf.zhang
 * @since 2017-3-30 19:16
 * 
 */
@Service("helpDocumentService")
public class HelpDocumentServiceImpl implements HelpDocumentService {
	
	private final static Logger logger = LoggerFactory.getLogger(HelpDocumentServiceImpl.class);
	
	@Resource
	private HelpDocCategoryDAO helpDocCategoryDAO;
	
	@Resource
	private HelpDocTopicDAO helpDocTopicDAO;

	@Resource
	private HelpDocumentUtils helpDocumentUtils;

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<?> addHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO) {
		
        logger.info("HelpDocumentServiceImpl--->addHelpDocCategory--->parmas:" + JSONObject.toJSONString(helpDocCategoryDTO));
		
		// 返回状态 ：0.失败  1.成功  2.分类已经存在
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
//			HelpDocCategoryDTO helpDocCategoryDTO_check = new HelpDocCategoryDTO();
//			helpDocCategoryDTO_check.setCategoryName(helpDocCategoryDTO.getCategoryName());
//			HelpDocCategory helpDocCategory = helpDocCategoryDAO.getSingleHelpDocCategoryByParams(helpDocCategoryDTO_check);
//			//二级分类名称已经存在
//			if(null != helpDocCategory){
//				result.setCode(HelpDocConstants.EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS);
//				result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS_TEXT);
//				result.setResult(null);
//				return result;
//			}
			
			HelpDocCategoryDTO helpDocCategoryDTO_sortNum = new HelpDocCategoryDTO();
			helpDocCategoryDTO_sortNum.setLevel(HelpDocConstants.HELP_DOC_SECOND_CATEGORY);
			helpDocCategoryDTO_sortNum.setSortNum(helpDocCategoryDTO.getSortNum());
			helpDocCategoryDTO_sortNum.setParentCategoryCode(helpDocCategoryDTO.getParentCategoryCode());
			HelpDocCategory helpDocCategory_sortNum = helpDocCategoryDAO.getSingleHelpDocCategoryByParams(helpDocCategoryDTO_sortNum);
			//二级分类排序号已经存在
			if(null != helpDocCategory_sortNum){
				result.setCode(HelpDocConstants.EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS);
				result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS_TEXT);
				result.setResult(null);
				return result;
			}

			String secondCategoryCode = helpDocumentUtils.generateSecondCategoryCode(helpDocCategoryDTO.getParentCategoryCode());
			helpDocCategoryDTO.setCategoryCode(secondCategoryCode);
			helpDocCategoryDTO.setLevel(HelpDocConstants.HELP_DOC_SECOND_CATEGORY);
			helpDocCategoryDTO.setIsPublish(Boolean.TRUE);

			helpDocCategoryDAO.addHelpDocCategory(helpDocCategoryDTO);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-addHelpDocCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<?> deleteHelpDocCategoryByCode(String code) {
		
        logger.info("HelpDocumentServiceImpl--->deleteHelpDocCategoryByParams--->code:" + code);
		
		// 返回状态 ：0.失败  1.成功
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			//删除二级分类下的所有主题
			helpDocCategoryDAO.deleteAllTopicsByCategoryCode(code);
			//删除二级分类
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setCategoryCode(code);
			helpDocCategoryDAO.deleteHelpDocCategoryByParams(helpDocCategoryDTO);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-deleteHelpDocCategoryByParams", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ExecuteResult<?> updateHelpDocCategory(HelpDocCategoryDTO helpDocCategoryDTO) {
		
        logger.info("HelpDocumentServiceImpl--->updateHelpDocCategory--->parmas:" + JSONObject.toJSONString(helpDocCategoryDTO));
		
		// 返回状态 ：0.失败  1.成功
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			//修改操作
			if(HelpDocConstants.CATEGORY_OPERATE_TYPE_UPDATE.equals(helpDocCategoryDTO.getOperateType())){
//				HelpDocCategoryDTO helpDocCategoryDTO_check = new HelpDocCategoryDTO();
//				helpDocCategoryDTO_check.setCategoryName(helpDocCategoryDTO.getCategoryName());
//				HelpDocCategory helpDocCategory = helpDocCategoryDAO.getSingleHelpDocCategoryByParams(helpDocCategoryDTO_check);
//				
//				if(null != helpDocCategory){
//					//二级分类名称已经存在
//					if(!helpDocCategoryDTO.getCategoryCode().equals(helpDocCategory.getCategoryCode())){
//						result.setCode(HelpDocConstants.EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS);
//						result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_CATEGORY_ALREADY_EXISTS_TEXT);
//						result.setResult(null);
//						return result;
//					}
//				}
				
				HelpDocCategoryDTO helpDocCategoryDTO_sortNum = new HelpDocCategoryDTO();
				helpDocCategoryDTO_sortNum.setLevel(HelpDocConstants.HELP_DOC_SECOND_CATEGORY);
				helpDocCategoryDTO_sortNum.setSortNum(helpDocCategoryDTO.getSortNum());
				helpDocCategoryDTO_sortNum.setParentCategoryCode(helpDocCategoryDTO.getParentCategoryCode());
				HelpDocCategory helpDocCategory_sortNum = helpDocCategoryDAO.getSingleHelpDocCategoryByParams(helpDocCategoryDTO_sortNum);
				
				if(null != helpDocCategory_sortNum){
					//二级分类排序号已经存在
					if(!helpDocCategoryDTO.getCategoryCode().equals(helpDocCategory_sortNum.getCategoryCode())){
						result.setCode(HelpDocConstants.EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS);
						result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SORTNUM_ALREADY_EXISTS_TEXT);
						result.setResult(null);
						return result;
					}
	
				}
			}
			
			helpDocCategoryDAO.updateHelpDocCategory(helpDocCategoryDTO);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-updateHelpDocCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}


	@Override
	public ExecuteResult<DataGrid<HelpDocCategory>> getHelpDocCategoriesForPage(Pager<HelpDocCategoryDTO> page,HelpDocCategoryDTO helpDocCategoryDTO) {
		
		 logger.info("HelpDocumentServiceImpl--->getHelpDocCategoriesForPage--->page:" + JSONObject.toJSONString(page));
		 logger.info("HelpDocumentServiceImpl--->getHelpDocCategoriesForPage--->helpDocCategoryDTO:" + JSONObject.toJSONString(helpDocCategoryDTO));
		
		ExecuteResult<DataGrid<HelpDocCategory>> result = new ExecuteResult<DataGrid<HelpDocCategory>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			DataGrid<HelpDocCategory> dataGrid = new DataGrid<HelpDocCategory>();
			List<HelpDocCategory> helpDocCategoryList = helpDocCategoryDAO.getHelpDocCategoriesForPage(page, helpDocCategoryDTO);
			int count = helpDocCategoryDAO.getHelpDocCategoriesCount(helpDocCategoryDTO);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(helpDocCategoryList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getHelpDocCategoriesForPage", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<HelpDocCategory> getSingleHelpDocCategoryByCode(String code) {
		
        logger.info("HelpDocumentServiceImpl--->getSingleHelpDocCategoryByParams--->code:" + code);
		
		// 返回状态 ：0.失败  1.成功
		ExecuteResult<HelpDocCategory> result = new ExecuteResult<HelpDocCategory>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setCategoryCode(code);
			HelpDocCategory helpDocCategory = helpDocCategoryDAO.getSingleHelpDocCategoryByParams(helpDocCategoryDTO);
			result.setResult(helpDocCategory);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getSingleHelpDocCategoryByParams", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		
		return result;
	}

	@Override
	public ExecuteResult<List<HelpDocFirstCategory>> getFirstCmptHelpDocCategory() {
		logger.info("HelpDocumentServiceImpl--->getFirstCmptHelpDocCategory: 查询所有完成的一级分类(包含发布的二级分类)" );

		ExecuteResult<List<HelpDocFirstCategory>> result = new ExecuteResult<List<HelpDocFirstCategory>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);

		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setLevel(HelpDocConstants.HELP_DOC_FIRST_CATEGORY);
			//查询一级分类
			List<HelpDocCategory> helpDocCategoryList = helpDocCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			if(null == helpDocCategoryList || helpDocCategoryList.size() < 1){
				result.setResult(null);
				return result;
			}
			
			List<HelpDocFirstCategory> helpDocFirstCategoryList = new ArrayList<HelpDocFirstCategory>();
			HelpDocFirstCategory helpDocFirstCategory = null;
			for(HelpDocCategory helpDocCategory : helpDocCategoryList){
				helpDocFirstCategory = new HelpDocFirstCategory();
				helpDocFirstCategory.setCategoryCode(helpDocCategory.getCategoryCode());
				helpDocFirstCategory.setCategoryName(helpDocCategory.getCategoryName());
				helpDocFirstCategory.setIsPublish(helpDocCategory.getIsPublish());
				helpDocFirstCategory.setLevel(helpDocCategory.getLevel());
				helpDocFirstCategory.setSortNum(helpDocCategory.getSortNum());
				
				HelpDocCategoryDTO fhelpDocCategoryDTO = new HelpDocCategoryDTO();
				fhelpDocCategoryDTO.setLevel(HelpDocConstants.HELP_DOC_SECOND_CATEGORY);
				fhelpDocCategoryDTO.setIsPublish(Boolean.TRUE);
				fhelpDocCategoryDTO.setParentCategoryCode(helpDocCategory.getCategoryCode());
				//查询发布的二级分类
				List<HelpDocCategory> secondHelpDocCategoryList = helpDocCategoryDAO.getHelpDocCategoriesByParams(fhelpDocCategoryDTO);
				if(null != secondHelpDocCategoryList && secondHelpDocCategoryList.size() > 0){
					helpDocFirstCategory.setSecondCategoryList(secondHelpDocCategoryList);
				}
				
				helpDocFirstCategoryList.add(helpDocFirstCategory);
				
			}
			
			result.setResult(helpDocFirstCategoryList);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getFirstCmptHelpDocCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}
	
	
	@Override
	public ExecuteResult<List<HelpDocCategory>> getTopHelpDocCategory() {
		logger.info("HelpDocumentServiceImpl--->getTopHelpDocCategory: 查询所有一级分类" );

		ExecuteResult<List<HelpDocCategory>> result = new ExecuteResult<List<HelpDocCategory>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);

		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setLevel(HelpDocConstants.HELP_DOC_FIRST_CATEGORY);
			List<HelpDocCategory> helpDocCategoryList = helpDocCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			result.setResult(helpDocCategoryList);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getTopHelpDocCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}
	
	@Override
	public ExecuteResult<List<HelpDocCategory>> getSecondHelpDocCategory(String firstCategoryCode){
		logger.info("HelpDocumentServiceImpl--->getSecondHelpDocCategory--->firstCategoryCode: 查询所有二级分类" );

		ExecuteResult<List<HelpDocCategory>> result = new ExecuteResult<List<HelpDocCategory>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);

		try {
			HelpDocCategoryDTO helpDocCategoryDTO = new HelpDocCategoryDTO();
			helpDocCategoryDTO.setLevel(HelpDocConstants.HELP_DOC_SECOND_CATEGORY);
			helpDocCategoryDTO.setParentCategoryCode(firstCategoryCode);
			helpDocCategoryDTO.setIsPublish(Boolean.TRUE);
			List<HelpDocCategory> helpDocCategoryList = helpDocCategoryDAO.getHelpDocCategoriesByParams(helpDocCategoryDTO);
			result.setResult(helpDocCategoryList);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getSecondHelpDocCategory", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<HelpDocTopic>> getHelpDocForPage(Pager<HelpDocTopicDTO> page, HelpDocTopicDTO helpDocTopicDTO)  {

		logger.info("HelpDocumentServiceImpl--->getHelpDocForPage--->page:" + JSONObject.toJSONString(page));
		logger.info("HelpDocumentServiceImpl--->getHelpDocForPage--->helpDocCategoryDTO:" + JSONObject.toJSONString(helpDocTopicDTO));

		ExecuteResult<DataGrid<HelpDocTopic>>result = new ExecuteResult<DataGrid<HelpDocTopic>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);

		try {
			DataGrid<HelpDocTopic> dataGrid = new DataGrid<HelpDocTopic>();
			List<HelpDocTopic> helpDocCategoryList = helpDocTopicDAO.getHelpDocTopicsForPage(page, helpDocTopicDTO);
			int count = helpDocTopicDAO.getHelpDocTopicsCount(helpDocTopicDTO);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(helpDocCategoryList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-getHelpDocForPage", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}


	@Override
	public ExecuteResult<?> addHelpDocument(HelpDocTopicDTO helpDocTopicDTO) {
		logger.info("HelpDocumentServiceImpl--->addHelpDocument--->parmas:" + JSONObject.toJSONString(helpDocTopicDTO));

		// 返回状态 ：0.失败  1.成功
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		String topicCode = helpDocumentUtils.generateTopicCode(helpDocTopicDTO.getSecondCategoryCode());
		helpDocTopicDTO.setTopictCode(topicCode);
		logger.info("生成帮助文档topicCode:"+topicCode);
		try {
			//判断主题是否重复
			int count =helpDocTopicDAO.queryByCount(helpDocTopicDTO.getSortNum(),helpDocTopicDTO.getSecondCategoryCode());
			if(count != 0)
			{
				result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
				result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
				result.addErrorMessage("序号["+helpDocTopicDTO.getSortNum()+"]已添加！");
				result.setResult(null);
				logger.error("topicCode:"+topicCode+" 序号["+helpDocTopicDTO.getSortNum()+"]已添加！");
				return result;
			}else{
				//添加文档记录
				helpDocTopicDAO.addHelpDocTopic(helpDocTopicDTO);
			}
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-addHelpDocument", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}

		return result;
	}

	@Override
	public ExecuteResult<?> editHelpDocTopic(HelpDocTopicDTO doc) {
		logger.info("HelpDocumentServiceImpl--->editHelpDocTopic--->parmas:" + JSONObject.toJSONString(doc));
		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			
			helpDocTopicDAO.editHelpDocTopic(doc);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-editHelpDocTopic", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<?> updateIsPublis(HelpDocTopicDTO doc) {
		logger.info("HelpDocumentServiceImpl--->updateIsPublise--->parmas:" + JSONObject.toJSONString(doc));

		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			
			helpDocTopicDAO.updateHelpDocTopic(doc);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-editHelpDocTopic", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<?> deleteHelpDocTopic(String topictCode) {
		logger.info("HelpDocumentServiceImpl--->deleteHelpDocTopic--->topictCode:" + topictCode);

		ExecuteResult<?> result = new ExecuteResult();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		try {
			
			helpDocTopicDAO.deleteHelpDocTopic(topictCode);
			
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-deleteHelpDocTopic", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<HelpDocTopic> queryHelpDocByTopicCode(String topictCode) {
		 logger.info("HelpDocumentServiceImpl--->queryHelpDocByTopicCode--->topictCode:" + topictCode);
			
		ExecuteResult<HelpDocTopic> result = new ExecuteResult<HelpDocTopic>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			HelpDocTopic dataGrid = helpDocTopicDAO.queryHelpDocByTopicCode(topictCode);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-queryHelpDocByTopicCode", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}

	@Override
	public ExecuteResult<DataGrid<HelpDocTopic>> queryHelpDocTopicForPage(Pager<HelpDocTopicDTO> page, HelpDocTopicDTO dto) {
		ExecuteResult<DataGrid<HelpDocTopic>> result = new ExecuteResult<DataGrid<HelpDocTopic>>();
		result.setCode(HelpDocConstants.EXECUTE_RESULT_SUCCESS);
		result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_SUCCESS_TEXT);
		
		try {
			DataGrid<HelpDocTopic> dataGrid = new DataGrid<HelpDocTopic>();
			List<HelpDocTopic> helpDocTopicList = helpDocTopicDAO.queryHelpDocTopicForPage(page, dto);
			int count = helpDocTopicDAO.getHelpDocTopicsCount(dto);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(helpDocTopicList);
			result.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("\n 方法[{}]，异常：[{}]", "HelpDocumentServiceImpl-queryHelpDocTopicForPage", e.toString());
			result.setCode(HelpDocConstants.EXECUTE_RESULT_FAILED);
			result.setResultMessage(HelpDocConstants.EXECUTE_RESULT_FAILED_TEXT);
			result.addErrorMessage(e.toString());
			result.setResult(null);
		}
		return result;
	}


}
