package cn.htd.goodscenter.dao.spu;

import java.util.List;

import cn.htd.goodscenter.dto.outdto.ItemSpuInfoDetailOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoListOutDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.venus.indto.VenusItemMainDataInDTO;
import cn.htd.goodscenter.dto.venus.outdto.VenusItemSpuDataOutDTO;

public interface ItemSpuMapper {
    int deleteByPrimaryKey(Long spuId);

    /**
     * 推入商品模板库
     * @param record
     * @return
     */
    int add(ItemSpu record);

    int insertSelective(ItemSpu record);

    ItemSpu selectByPrimaryKey(Long spuId);

    int updateBySpuIdSelective(ItemSpu record);

    int updateByPrimaryKey(ItemSpu record);

    /**
     * 根据条件查询SPU商品模板
     * @param spuInfoDTO
     * @param page
     * @return
     */
    public List<SpuInfoDTO> queryByCondition(@Param("entity") SpuInfoDTO spuInfoDTO, @Param("page") Pager page) ;
    /**
     * 统计商品模板列表总数
     * @param spuInfoDTO
     * @return
     */
    public Long queryCount(@Param("entity") SpuInfoDTO spuInfoDTO);

    /**
     * 根据商品模版ID更新删除标记
     * @param spuInfoDTO
     * @return
     */
    public int updateDeleteFlag(@Param("entity") SpuInfoDTO spuInfoDTO);
    
    /**
     * 根据名称查询商品模板
     * 
     * @param spuName
     * @return
     */
    ItemSpu queryItemSpuByName(String spuName);
    
    String querySpuCodeSeq();

    List<SpuInfoDTO> getSpuInfoListByItemId(Long itemId);
    
    ItemSpu queryItemSpuBySpuCode(String spuCode);

    ItemSpu selectById(Long spuId);
    
    List<ItemSpu> queryItemSpuList(List<Long> spuIdList);
    
    ItemSpu queryItemSpuByErpCode(@Param("erpCode") String erpCode);
    
    //add by zhangxiaolong
    
    List<VenusItemSpuDataOutDTO> queryItemSpuDataList(VenusItemMainDataInDTO venusItemMainDataInDTO);
    
    Long queryItemSpuDataCount(VenusItemMainDataInDTO venusItemMainDataInDTO);

    /**
     * 查询商品模板数量- 超级老板
     * @return
     */
    Long queryItemSpu4SupperBossListCount(@Param("param") String param);

    /**
     * 查询商品模板列表
     * @param page
     * @return
     */
    List<ItemSpuInfoListOutDTO> queryItemSpu4SupperBossList(@Param("param")String param, @Param("page")Pager page);

    /**
     * 查询商品模板详情
     * @param spuCode
     * @return
     */
    ItemSpuInfoDetailOutDTO getItemSpuDetailBySpuCode(String spuCode);
}