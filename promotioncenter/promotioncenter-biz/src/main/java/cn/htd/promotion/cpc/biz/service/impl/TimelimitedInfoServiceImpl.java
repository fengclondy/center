package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dao.TimelimitedInfoDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.TimelimitedSkuPictureDAO;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.TimelimitedInfoService;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuDescribeResDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedSkuPictureResDTO;

import com.alibaba.fastjson.JSONObject;

@Service("timelimitedInfoService")
public class TimelimitedInfoServiceImpl implements TimelimitedInfoService{

    private static final Logger logger = LoggerFactory.getLogger(TimelimitedInfoServiceImpl.class);
	
	@Resource
	private TimelimitedInfoDAO timelimitedInfoDAO;
	
	@Resource
	private TimelimitedSkuPictureDAO timelimitedSkuPictureDAO;
	
	@Resource
	private TimelimitedSkuDescribeDAO timelimitedSkuDescribeDAO;
	
    @Resource
    private PromotionBaseService baseService;
	
	@Override
	public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId) {
		
		//当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        
		try {
			//添加秒杀商品
			timelimitedInfoReqDTO.setCreateTime(currentTime);
			timelimitedInfoReqDTO.setModifyTime(currentTime);
			timelimitedInfoDAO.insert(timelimitedInfoReqDTO);
			
			//添加商品图片
			addTimelimitedSkuPictureList(timelimitedInfoReqDTO,currentTime);
			
			//添加商品详情
			addTimelimitedSkuDescribe(timelimitedInfoReqDTO,currentTime);
			
			//添加促销活动信息
//			PromotionInfoEditReqDTO PromotionInfoEditReqDTO = timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
//			baseService.addPromotionInfo(PromotionInfoEditReqDTO);
			
		} catch (Exception e) {
            logger.error("messageId{}:执行方法【addTimelimitedInfo】报错：{}", messageId ,e.toString());
            throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId) {
		
		try {
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();
            
			//修改秒杀商品
			timelimitedInfoReqDTO.setModifyTime(currentTime);
			timelimitedInfoDAO.updateByPrimaryKeySelective(timelimitedInfoReqDTO);
			
			//伪删除商品活动的所有图片
			TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO_delete = new TimelimitedSkuPictureReqDTO();
			timelimitedSkuPictureReqDTO_delete.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
			timelimitedSkuPictureReqDTO_delete.setDeleteFlag(Boolean.TRUE);
			timelimitedSkuPictureReqDTO_delete.setModifyId(timelimitedInfoReqDTO.getModifyId());
			timelimitedSkuPictureReqDTO_delete.setModifyName(timelimitedInfoReqDTO.getModifyName());
			timelimitedSkuPictureReqDTO_delete.setModifyTime(currentTime);
			timelimitedSkuPictureDAO.pseudoDelete(timelimitedSkuPictureReqDTO_delete);
			
			//添加商品活动图片
			addTimelimitedSkuPictureList(timelimitedInfoReqDTO,currentTime);
			
			//修改商品详情
			 updateTimelimitedSkuDescribe(timelimitedInfoReqDTO,currentTime);
			
//			//修改促销活动信息
//			PromotionInfoEditReqDTO PromotionInfoEditReqDTO = timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
//			baseService.editPromotionInfo(PromotionInfoEditReqDTO);
			
		} catch (Exception e) {
            logger.error("messageId{}:执行方法【updateTimelimitedInfo】报错：{}", messageId ,e.toString());
            throw new RuntimeException(e);
		}
		
	}
	
	
	@Override
	public TimelimitedInfoResDTO getSingleTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
		TimelimitedInfoResDTO timelimitedInfoResDTO = null;
		
		try {

			String promotionId = timelimitedInfoReqDTO.getPromotionId();
			//查询活动信息
			timelimitedInfoResDTO = timelimitedInfoDAO.selectByPromotionId(timelimitedInfoReqDTO.getPromotionId());
			//查询图片信息
			List<TimelimitedSkuPictureResDTO> timelimitedSkuPictureResDTOList = timelimitedSkuPictureDAO.selectByPromotionId(promotionId);
			//查询详情信息
			TimelimitedSkuDescribeResDTO timelimitedSkuDescribeResDTO = timelimitedSkuDescribeDAO.selectByPromotionId(promotionId);
			
			timelimitedInfoResDTO.setTimelimitedSkuPictureList(timelimitedSkuPictureResDTOList);
			timelimitedInfoResDTO.setTimelimitedSkuDescribe(timelimitedSkuDescribeResDTO);
			
//			//修改促销活动信息
//			PromotionInfoEditReqDTO PromotionInfoEditReqDTO = timelimitedInfoReqDTO.getPromotionInfoEditReqDTO();
//			baseService.editPromotionInfo(PromotionInfoEditReqDTO);
			
		} catch (Exception e) {
            logger.error("messageId{}:执行方法【getSingleTimelimitedInfo】报错：{}", messageId ,e.toString());
            throw new RuntimeException(e);
		}
		
		return timelimitedInfoResDTO;
		
	}
	
	@Override
	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId) {
		
		 logger.info("TimelimitedInfoServiceImpl--->getTimelimitedInfosForPage--->page:" + JSONObject.toJSONString(page));
		 logger.info("TimelimitedInfoServiceImpl--->getTimelimitedInfosForPage--->timelimitedInfoReqDTO:" + JSONObject.toJSONString(timelimitedInfoReqDTO));
		
		DataGrid<TimelimitedInfoResDTO> dataGrid = null;
		try {
			dataGrid = new DataGrid<TimelimitedInfoResDTO>();
			List<TimelimitedInfoResDTO> helpDocCategoryList = timelimitedInfoDAO.getTimelimitedInfosForPage(page, timelimitedInfoReqDTO);
			int count = timelimitedInfoDAO.getTimelimitedInfosCount(timelimitedInfoReqDTO);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(helpDocCategoryList);
		} catch (Exception e) {
			logger.error("messageId{}:执行方法【getTimelimitedInfosForPage】报错：{}", messageId ,e.toString());
			throw new RuntimeException(e);
		}
		return dataGrid;
	}
	
	
	/**
	 * 批量添加秒杀商品图片
	 * @param timelimitedSkuDescribeList
	 */
	private void addTimelimitedSkuPictureList(TimelimitedInfoReqDTO timelimitedInfoReqDTO,Date currentTime){
		
		//添加商品活动图片
		List<TimelimitedSkuPictureReqDTO> timelimitedSkuDescribeList = timelimitedInfoReqDTO.getTimelimitedSkuPictureReqDTOList();
		if(null != timelimitedSkuDescribeList && timelimitedSkuDescribeList.size() > 0){
			TimelimitedSkuPictureReqDTO timelimitedSkuPictureReqDTO = null;
			for(int i=0;i<timelimitedSkuDescribeList.size();i++){
				timelimitedSkuPictureReqDTO = timelimitedSkuDescribeList.get(i);
	        	timelimitedSkuPictureReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
	        	timelimitedSkuPictureReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
	        	if(i==0){//第一张图设置为主图
	        		timelimitedSkuPictureReqDTO.setIsFirst(Boolean.TRUE);
	        	}else{
	        		timelimitedSkuPictureReqDTO.setIsFirst(Boolean.FALSE);
	        	}
	        	timelimitedSkuPictureReqDTO.setSortNum(i+1);
	        	timelimitedSkuPictureReqDTO.setDeleteFlag(Boolean.FALSE);
	        	timelimitedSkuPictureReqDTO.setCreateId(timelimitedInfoReqDTO.getModifyId());
	        	timelimitedSkuPictureReqDTO.setCreateName(timelimitedInfoReqDTO.getModifyName());
	        	timelimitedSkuPictureReqDTO.setCreateTime(currentTime);
	        	timelimitedSkuPictureReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
	        	timelimitedSkuPictureReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
	        	timelimitedSkuPictureReqDTO.setModifyTime(currentTime);
				timelimitedSkuPictureDAO.insert(timelimitedSkuPictureReqDTO);
			}
		}
	}

	/**
	 * 添加商品详情
	 * @param timelimitedInfoReqDTO
	 * @param currentTime
	 */
	public void addTimelimitedSkuDescribe(TimelimitedInfoReqDTO timelimitedInfoReqDTO,Date currentTime){
		TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = timelimitedInfoReqDTO.getTimelimitedSkuDescribeReqDTO();
		
    	timelimitedSkuDescribeReqDTO.setPromotionId(timelimitedInfoReqDTO.getPromotionId());
    	timelimitedSkuDescribeReqDTO.setLevelCode(timelimitedInfoReqDTO.getLevelCode());
    	timelimitedSkuDescribeReqDTO.setDeleteFlag(Boolean.FALSE);
    	timelimitedSkuDescribeReqDTO.setCreateId(timelimitedInfoReqDTO.getCreateId());
    	timelimitedSkuDescribeReqDTO.setCreateName(timelimitedInfoReqDTO.getCreateName());
    	timelimitedSkuDescribeReqDTO.setCreateTime(currentTime);
    	timelimitedSkuDescribeReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
    	timelimitedSkuDescribeReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
    	timelimitedSkuDescribeReqDTO.setModifyTime(currentTime);
    	
		timelimitedSkuDescribeDAO.insert(timelimitedSkuDescribeReqDTO);
	}
	
	/**
	 * 修改商品详情
	 * @param timelimitedInfoReqDTO
	 * @param currentTime
	 */
	public void updateTimelimitedSkuDescribe(TimelimitedInfoReqDTO timelimitedInfoReqDTO,Date currentTime){
		TimelimitedSkuDescribeReqDTO timelimitedSkuDescribeReqDTO = timelimitedInfoReqDTO.getTimelimitedSkuDescribeReqDTO();
		
    	timelimitedSkuDescribeReqDTO.setModifyId(timelimitedInfoReqDTO.getModifyId());
    	timelimitedSkuDescribeReqDTO.setModifyName(timelimitedInfoReqDTO.getModifyName());
    	timelimitedSkuDescribeReqDTO.setModifyTime(currentTime);
    	
    	timelimitedSkuDescribeDAO.updateByPrimaryKeySelective(timelimitedSkuDescribeReqDTO);
	}


	
	
	
}
