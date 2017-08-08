package cn.htd.goodscenter.dao;

import java.util.List;

import cn.htd.goodscenter.domain.ItemDraft;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemListStyleOutDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSkuDetailOutDTO;
import cn.htd.goodscenter.dto.venus.po.QueryVenusItemListParamDTO;

public interface ItemDraftMapper {
    int deleteByPrimaryKey(Long itemDraftId);

    int insert(ItemDraft record);

    int insertSelective(ItemDraft record);

    ItemDraft selectByPrimaryKey(Long itemDraftId);

    int updateByPrimaryKeySelective(ItemDraft record);
    
    int updateItemDraftVerifyStatusByPrimaryKey(ItemDraft record);

    int updateByPrimaryKey(ItemDraft record);
    
    ItemDraft selectByItemId(Long itemId);
    
    Long queryDraftItemSkuListCount(QueryVenusItemListParamDTO queryVenusItemListParamDTO);
	
	List<VenusItemListStyleOutDTO> queryDraftItemSkuList(QueryVenusItemListParamDTO queryVenusItemListParamDTO);
	
	VenusItemSkuDetailOutDTO queryItemSkuDraftDetail(Long skuId);
}