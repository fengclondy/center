package cn.htd.goodscenter.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ItemDraftDescribeMapper;
import cn.htd.goodscenter.dao.ItemDraftMapper;
import cn.htd.goodscenter.dao.ItemDraftPictureMapper;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ItemSkuDAO;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ItemSku;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ItemErpStatusEnum;
import cn.htd.goodscenter.dto.middleware.indto.DownErpCallbackInDTO;
import cn.htd.goodscenter.service.HtdDownErpCallbackService;
import cn.htd.pricecenter.common.constants.ErrorCodes;

import com.google.common.collect.Lists;

@Service("htdDownErpCallbackServiceImpl")
public class HtdDownErpCallbackServiceImpl implements HtdDownErpCallbackService{
	@Resource
	private ItemMybatisDAO itemMybatisDAO;
	@Resource
	private ItemBrandDAO itemBrandDAO;
	@Resource
	private ItemDraftMapper itemDraftMapper;
	@Resource
	private ItemSkuDAO itemSkuDAO;
	@Resource
	private ItemDraftPictureMapper itemDraftPictureMapper;
	@Resource
	private ItemPictureDAO itemPictureDAO;
	@Resource
	private ItemDescribeDAO itemDescribeDAO;
	@Resource
	private ItemDraftDescribeMapper itemDraftDescribeMapper;
	@Resource
	private ItemSpuMapper itemSpuMapper;

	private Logger logger = LoggerFactory.getLogger(HtdDownErpCallbackServiceImpl.class);
	
	@Transactional
	@Override
	public ExecuteResult<String> itemDownErpCallback(List<DownErpCallbackInDTO> downErpCallbackList) {
		this.logger.info("商品下行回调, 回调信息 : {}", JSONArray.fromObject(downErpCallbackList));
		ExecuteResult<String> result=new ExecuteResult<String>();
		
		if(CollectionUtils.isEmpty(downErpCallbackList)){
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("downErpCallbackInDTO")));
			return result;
		}
		
		for(DownErpCallbackInDTO downErpCallbackInDTO:downErpCallbackList){
			if(downErpCallbackInDTO==null||StringUtils.isEmpty(downErpCallbackInDTO.getErpStatus())){
				logger.error("HtdDownErpCallbackServiceImpl::itemDownErpCallback is null");
				continue;
			}
			
			if(StringUtils.isEmpty(downErpCallbackInDTO.getMiddleGroundCode())){
				logger.error("HtdDownErpCallbackServiceImpl::itemDownErpCallback:MiddleGroundCode is null");
				continue;
			}
			
			if(downErpCallbackInDTO.getItemId()==null){
				logger.error("HtdDownErpCallbackServiceImpl::itemDownErpCallback:ItemId() is nul");
				continue;
			}
			
			Integer itemStatus="1".equals(downErpCallbackInDTO.getErpStatus())?HtdItemStatusEnum.PASS.getCode():null;
			
			ItemDTO dbItem = this.itemMybatisDAO.getItemDTOById(downErpCallbackInDTO.getItemId());
			//已上架的则状态不做改变
			if(dbItem.getItemStatus()==HtdItemStatusEnum.SHELVED.getCode()){
				itemStatus=HtdItemStatusEnum.SHELVED.getCode();
			}
			
			String  erpStatus="1".equals(downErpCallbackInDTO.getErpStatus())?ItemErpStatusEnum.DOWNED.getCode():
				ItemErpStatusEnum.FAIL.getCode();
			//执行更新
			itemMybatisDAO.updateItemAuditStatusByPk(Long.valueOf(downErpCallbackInDTO.getItemId()), itemStatus, 0L,"system",
					erpStatus,downErpCallbackInDTO.getErpErrorMsg(),downErpCallbackInDTO.getErpCode());
			
			if("1".equals(downErpCallbackInDTO.getErpStatus())){
				try{
					dbItem = this.itemMybatisDAO.getItemDTOById(downErpCallbackInDTO.getItemId());
					ItemDraft itemDraft=itemDraftMapper.selectByItemId(downErpCallbackInDTO.getItemId());
					syncDraft2Item(itemDraft,dbItem);
					//修改草稿的状态
		            if(itemDraft !=null){
		                itemDraft.setStatus(1);
		                itemDraftMapper.updateByPrimaryKeySelective(itemDraft);
		            }
		            //同步图片
		            List<ItemPicture> picList=itemDraftPictureMapper.queryItemDraftPicsByItemId(dbItem.getItemId());
		            if(CollectionUtils.isNotEmpty(picList)){
		            	 itemPictureDAO.updateDeleteFlagByItemId(dbItem.getItemId());
				         itemPictureDAO.batchInsert(picList);
		            }
		            //同步描述
		            ItemDescribe describe=itemDraftDescribeMapper.selectByItemId(dbItem.getItemId());
		            ItemDescribe dbDescribe=itemDescribeDAO.getDescByItemId(dbItem.getItemId());
		            if(dbDescribe!=null){
		            	dbDescribe.setDescribeContent(describe==null?dbDescribe.getDescribeContent():describe.getDescribeContent());
		            	itemDescribeDAO.updateByPrimaryKeySelective(dbDescribe);
		            } else {
						dbDescribe = new ItemDescribe();
						dbDescribe.setItemId(dbItem.getItemId());
						dbDescribe.setDescribeContent(describe==null?"":describe.getDescribeContent());
						dbDescribe.setCreateId(0L);
						dbDescribe.setCreateName("CALL_BAKC");
						dbDescribe.setCreateTime(new Date());
						dbDescribe.setModifyId(0L);
						dbDescribe.setModifyName("CALL_BAKC");
						dbDescribe.setModifyTime(new Date());
		            	itemDescribeDAO.insertSelective(dbDescribe);
		            }
		            if(dbItem.getItemSpuId()!=null){
						ItemSpu itemSpu=itemSpuMapper.selectByPrimaryKey(Long.valueOf(dbItem.getItemSpuId()));
						if(itemSpu!=null){
							//只有erpcode为空时才处理start
							if(StringUtils.isEmpty(itemSpu.getErpCode())||"0".equals(itemSpu.getErpCode())){
								logger.info("只有erpcode为空时才处理start:"+itemSpu.getErpCode()+",itemId="+dbItem.getItemId());
								if(dbItem.getBrand()!=null){
									itemSpu.setBrandId(dbItem.getBrand());
								}
								
								if(dbItem.getCid()!=null){
									itemSpu.setCategoryId(dbItem.getCid());
								}
								
								if(StringUtils.isNotEmpty(dbItem.getWeightUnit())){
									itemSpu.setUnit(dbItem.getWeightUnit());
								}
								
								if(StringUtils.isNotEmpty(dbItem.getModelType())){
									itemSpu.setModelType(dbItem.getModelType());
								}
								
								if(StringUtils.isNotEmpty(dbItem.getItemQualification())){
									itemSpu.setItemQualification(dbItem.getItemQualification());
								}
								
								if(dbItem.getTaxRate()!=null){
									itemSpu.setTaxRate(dbItem.getTaxRate());
								}
								
							}
							//只有erpcode为空时才处理end
							itemSpu.setErpCode(downErpCallbackInDTO.getErpCode());
							if(StringUtils.isNotEmpty(dbItem.getItemName())){
								itemSpu.setSpuName(dbItem.getItemName());
							}
							itemSpu.setModifyTime(new Date());
							itemSpu.setModifyId(0L);
							itemSpu.setModifyName("system");
							itemSpuMapper.updateBySpuIdSelective(itemSpu);
						}
					}

				}catch(Exception e){
					e.printStackTrace();
					logger.error("itemDownErpCallback error:", e);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}
		}
		result.setCode(ErrorCodes.SUCCESS.name());
		return result;
	}

	@Override
	public ExecuteResult<String> brandDownErpCallback(DownErpCallbackInDTO downErpCallbackInDTO) {
		this.logger.info("品牌下行回调, 回调信息 : {}", JSONObject.fromObject(downErpCallbackInDTO));
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			ItemBrand itemBrand = new ItemBrand();
			itemBrand.setBrandId(Long.valueOf(downErpCallbackInDTO.getMiddleGroundCode()));
			String erpStatus;
			if ("0".equals(downErpCallbackInDTO.getErpStatus())) { // 0 : 失败
				erpStatus = ItemErpStatusEnum.FAIL.getCode();
			} else if ("1".equals(downErpCallbackInDTO.getErpStatus())) { // 0 : 成功
				erpStatus = ItemErpStatusEnum.DOWNED.getCode();
			} else {
				throw new RuntimeException("illegal erpStatus : " + downErpCallbackInDTO.getErpStatus());
			}
			itemBrand.setErpStatus(erpStatus);
			itemBrand.setErpErrorMsg(downErpCallbackInDTO.getErpErrorMsg());
			itemBrand.setModifyId(0L);
			itemBrand.setModifyName("middleWear");
			this.itemBrandDAO.update(itemBrand);
		} catch (Exception e) {
			logger.error("品牌下行回调出错, 错误信息:", e);
			result.addErrorMessage(e.getMessage());
		} finally {
			logger.info("品牌下行回调出结束");
		}
		return result;
	}
	
    private void syncDraft2Item(ItemDraft itemDraft,ItemDTO itemDTO){
        if(itemDTO==null||itemDTO.getItemId()==null||itemDTO.getItemId()<=0){
            return;
        }
        if(itemDraft==null){
            return;
        }
        itemDTO.setAd(itemDraft.getAd());
        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(itemDraft.getAttributes()))){
            itemDTO.setAttributesStr(itemDraft.getAttributes());
        }
        //处理sku 颜色字段 放到sku表的销售属性字段上去
        List<ItemSku> itemSkuList=itemSkuDAO.queryByItemId(itemDraft.getItemId());

        if(CollectionUtils.isNotEmpty(itemSkuList)){
            ItemSku itemSku=itemSkuList.get(0);
            itemSku.setAttributes(itemDraft.getAttrSale());
            itemSku.setModified(new Date());
            itemSku.setModifyId(itemDraft.getModifyId());
            itemSku.setModifyName(itemDraft.getModifyName());
            itemSkuDAO.update(itemSku);
        }

        if(itemDraft.getCid()!=null){
            itemDTO.setCid(itemDraft.getCid());
        }
        
        if(itemDraft.getBrand()!=null){
        	itemDTO.setBrand(itemDraft.getBrand());
        }
        if(StringUtils.isNotEmpty(StringUtils.trimToEmpty(itemDraft.getEanCode()))){
            itemDTO.setEanCode(itemDraft.getEanCode());
        }

//        if(itemDraft.getIsSpu()!=null&&itemDraft.getIsSpu().equals(1)){
//            itemDTO.setSpu(Boolean.TRUE);
//        }
        itemDTO.setSpu(Boolean.TRUE);
        
        if(StringUtils.isNotEmpty(itemDraft.getItemName())){
            itemDTO.setItemName(itemDraft.getItemName());
        }
        if(itemDraft.getItemSpuId()!=null){
            itemDTO.setItemSpuId(itemDraft.getItemSpuId().intValue());
        }
        itemDTO.setHeight(itemDraft.getHeight()==null?"0":itemDraft.getHeight().toPlainString());
        itemDTO.setLength(itemDraft.getLength()==null?"0":itemDraft.getLength().toPlainString());
        itemDTO.setWidth(itemDraft.getWidth()==null?"0":itemDraft.getWidth().toPlainString());
        if(StringUtils.isNotEmpty(itemDraft.getModelType())){
        	itemDTO.setModelType(itemDraft.getModelType());
        }
        if(itemDraft.getNetWeight()!=null){
        	itemDTO.setNetWeight(itemDraft.getNetWeight());
        }
        if(StringUtils.isNotEmpty(itemDraft.getOrigin())){
        	itemDTO.setOrigin(itemDraft.getOrigin());
        }
        if(itemDraft.getTaxRate()!=null){
            itemDTO.setTaxRate(itemDraft.getTaxRate());
        }
        if(StringUtils.isNotEmpty(itemDraft.getAttributes())){
        	itemDTO.setAttributesStr(itemDraft.getAttributes());
        }
        if(StringUtils.isNotEmpty(itemDraft.getAttrSale())){
        	itemDTO.setAttrSaleStr(itemDraft.getAttrSale());
        }
       
        itemDTO.setWeight(itemDraft.getWeight()==null?new BigDecimal(0):itemDraft.getWeight());
        
        if(StringUtils.isNotEmpty(itemDraft.getWeightUnit())){
        	itemDTO.setWeightUnit(itemDraft.getWeightUnit());
        }
        
        itemMybatisDAO.updateItem(itemDTO);
    }

}
