package cn.htd.goodscenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.spu.ItemSpuPicture;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.indto.ItemSpuInfoListInDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoDetailOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoListOutDTO;

/**
 * Created by lih on 2016/11/26.
 * 商品模板接口
 */
public interface ItemSpuExportService {

    /**
     * 根据查询条件查询商品模板列表
     * @param spuInfoDTO
     * @return
     */
    ExecuteResult<DataGrid<SpuInfoDTO>> queryByCondition(SpuInfoDTO spuInfoDTO, Pager page);

    /**
     * 根据商品模板ID查询商品模板详细信息
     * @param spuId
     * @return
     */
    ExecuteResult<SpuInfoDTO> getItemSpuBySpuId(Long spuId);

    /**
     * 根据商品模板ID保存修改后的商品模版信息
     * @param spuInfoDTO
     * @return
     */
    public ExecuteResult<SpuInfoDTO> modifyItemSpuBySpuId(SpuInfoDTO spuInfoDTO);

    /**
     * 根据商品模版ID更新删除标记
     * @param spuInfoDTO
     * @return
     */
    public ExecuteResult<SpuInfoDTO> updateDeleteFlag(SpuInfoDTO spuInfoDTO);

    /**
     * 推入商品模板库
     * @param spuInfoDTO
     * @return
     */
    public ExecuteResult<SpuInfoDTO> addItemSpuInfo(SpuInfoDTO spuInfoDTO);
  
    /**
     * 根据名称查询商品模板信息
     * @param spuName
     * @return
     */
    public ExecuteResult<SpuInfoDTO> queryItemSpuByName(String spuName);

    public ExecuteResult<List<SpuInfoDTO>> getSpuInfoListByItemId(Long itemId);
    
    /**
     * 根据ERP_CODE查询spu的图片
     * 
     * @param erpCode
     * @return
     */
    public ExecuteResult<List<ItemSpuPicture>> queryItemSpuPicsByErpcode(String erpCode);

    /**
     * 查询模板列表 - for 超级老板
     * itemSpuInfoListInDTO 和 page 不能为null
     * itemSpuInfoListInDTO在中可以按照模板名称模糊查询
     * @return
     */
    public ExecuteResult<DataGrid<ItemSpuInfoListOutDTO>> queryItemSpuList4SupperBoss(String param, Pager page);

    /**
     * 查询模板详情 - 根据spuCode
     * @param spuCode
     * @return
     */
    public ExecuteResult<ItemSpuInfoDetailOutDTO> queryItemSpuDetialBySpuCode(String spuCode);
}
