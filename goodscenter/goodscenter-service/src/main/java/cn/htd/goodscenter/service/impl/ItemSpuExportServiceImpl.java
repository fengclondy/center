package cn.htd.goodscenter.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemDescribeDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.dao.ItemPictureDAO;
import cn.htd.goodscenter.dao.ModifyDetailInfoMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuDescribeMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuMapper;
import cn.htd.goodscenter.dao.spu.ItemSpuPictureMapper;
import cn.htd.goodscenter.domain.Item;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.domain.ItemDescribe;
import cn.htd.goodscenter.domain.ItemPicture;
import cn.htd.goodscenter.domain.ModifyDetailInfo;
import cn.htd.goodscenter.domain.spu.ItemSpu;
import cn.htd.goodscenter.domain.spu.ItemSpuDescribe;
import cn.htd.goodscenter.domain.spu.ItemSpuPicture;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemSpuPictureDTO;
import cn.htd.goodscenter.dto.SpuInfoDTO;
import cn.htd.goodscenter.dto.enums.HtdItemStatusEnum;
import cn.htd.goodscenter.dto.enums.ShopModifyColumn;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoDetailOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemSpuInfoListOutDTO;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.goodscenter.service.ItemSpuExportService;
import cn.htd.goodscenter.service.utils.ItemCodeGenerator;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

/**
 * Created by lh on 2016/11/26.
 */
@Service("itemSpuExportService") public class ItemSpuExportServiceImpl implements ItemSpuExportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemSpuExportServiceImpl.class);

    @Resource private ItemSpuMapper itemSpuMapper;
    @Resource private ItemSpuPictureMapper itemSpuPictureMapper;
    @Resource private ItemSpuDescribeMapper itemSpuDescribeMapper;
    @Resource private ItemMybatisDAO itemMybatisDAO;
    @Resource private ItemPictureDAO itemPictureDAO;
    @Resource private ItemDescribeDAO itemDescribeDAO;
    @Resource private ModifyDetailInfoMapper modifyDetailInfoMapper;
    @Resource
    private ItemBrandDAO itemBrandDAO;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Resource
    private DictionaryUtils dictionaryUtils;

    @Override public ExecuteResult<DataGrid<SpuInfoDTO>> queryByCondition(SpuInfoDTO spuInfoDTO, Pager page) {
        ExecuteResult<DataGrid<SpuInfoDTO>> result = new ExecuteResult<DataGrid<SpuInfoDTO>>();
        DataGrid<SpuInfoDTO> dataGrid = new DataGrid<SpuInfoDTO>();
        try {
            List<SpuInfoDTO> spuInfoDTOList = new ArrayList<SpuInfoDTO>();
            Long count = itemSpuMapper.queryCount(spuInfoDTO);
            if (count > 0) {
                spuInfoDTOList = itemSpuMapper.queryByCondition(spuInfoDTO, page);
            }
            dataGrid.setRows(spuInfoDTOList);
            dataGrid.setTotal(count);
            result.setResult(dataGrid);
            result.setResultMessage("success");
        } catch (Exception e) {
            LOGGER.error("查询商品模板列表失败, 错误信息：", e);
            result.setResultMessage("查询商品模板列表失败! ");
            result.addErrorMessage(e.getMessage());
            return result;
        }
        return result;
    }

    @Override public ExecuteResult<SpuInfoDTO> getItemSpuBySpuId(Long spuId) {
        ExecuteResult<SpuInfoDTO> result = new ExecuteResult<SpuInfoDTO>();
        SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
        if (null == spuId) {
            result.setResultMessage("查询参数不能为空");
            return result;
        }
        //查询商品模板信息
        ItemSpu itemSpu = itemSpuMapper.selectByPrimaryKey(spuId);
        
        LOGGER.error("getItemSpuBySpuId::{}",JSON.toJSON(itemSpu));
        if (itemSpu == null) {
        	LOGGER.error("getItemSpuBySpuId is null...");
        	 
            result.addErrorMessage("没有该商品模板信息！");
            return result;
        }
        BeanUtils.copyProperties(itemSpu, spuInfoDTO);
        //查询商品模板图片信息
        List<ItemSpuPictureDTO> itemSpuPictureDTOList = itemSpuPictureMapper.queryBySpuId(spuId);
        if (itemSpuPictureDTOList == null) {
            result.addErrorMessage("没有商品模板图片信息! ");
            return result;
        }
        spuInfoDTO.setItemSpuPictureDTOList(itemSpuPictureDTOList);
        //查询商品模板详情
        ItemSpuDescribe itemSpuDescribe = itemSpuDescribeMapper.queryBySpuId(spuId);
        if (itemSpuDescribe == null) {
        	 spuInfoDTO.setSpuDesc("");
        }else{
        	 spuInfoDTO.setSpuDesc(itemSpuDescribe.getSpuDesc());
        }

        result.setResult(spuInfoDTO);
        return result;
    }

    @Override public ExecuteResult<SpuInfoDTO> modifyItemSpuBySpuId(SpuInfoDTO dto) {
        LOGGER.info("=============开始修改商品模板信息=================");
        ExecuteResult<SpuInfoDTO> result = new ExecuteResult<SpuInfoDTO>();
        try {
            // 校验空值
            if (dto == null) {
                result.addErrorMessage("参数为空！");
                return result;
            }
            ItemSpu itemSpu = this.itemSpuMapper.selectByPrimaryKey(dto.getSpuId());
            if (itemSpu == null) {// 商品信息不能为空
                result.addErrorMessage("没有查询到该商品模板信息！");
                return result;
            }

            //更新记录
//            if(itemSpu !=null){
//                saveChangedRecord(dto,itemSpu);
//            }

            // 更新商品模板
            ItemSpu spu = new ItemSpu();
            BeanUtils.copyProperties(dto, spu);
            itemSpuMapper.updateBySpuIdSelective(spu);
            // 更新商品模板照片:先删除之前的图片再插入新的图片(逻辑删除)
            List<ItemSpuPictureDTO> pictureList = dto.getItemSpuPictureDTOList();
            //删除之前的图片
            SpuInfoDTO spuInfoDTO = new SpuInfoDTO();
            spuInfoDTO.setDeleteFlag(Byte.valueOf("1"));
            spuInfoDTO.setSpuId(dto.getSpuId());
            itemSpuPictureMapper.updateDeleteFlag(spuInfoDTO);
            //新增新的图片
            for (int i = 0; i < pictureList.size(); i++) {
                ItemSpuPicture itemSpuPicture = new ItemSpuPicture();
                BeanUtils.copyProperties(pictureList.get(i), itemSpuPicture);
                itemSpuPicture.setSpuId(itemSpu.getSpuId());
                itemSpuPictureMapper.add(itemSpuPicture);
            }

            //ItemSpuPicture itemSpuPicture = new ItemSpuPicture();
            //BeanUtils.copyProperties(dto, itemSpuPicture);
            //itemSpuPictureMapper.updateBySpuIdSelective(itemSpuPicture);
            // 更新商品模板详情
            ItemSpuDescribe itemSpuDescribe = new ItemSpuDescribe();
            BeanUtils.copyProperties(dto, itemSpuDescribe);
            itemSpuDescribeMapper.updateBySpuIdSelective(itemSpuDescribe);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("执行方法【modifyItemSpuBySpuId】报错:{}", e);
            result.setResultMessage(e.getMessage());
            result.getErrorMessages().add(e.getMessage());
            return result;
        } finally {
            LOGGER.info("=============结束修改商品信息=================");
        }
        return result;
    }

    public void saveChangedRecord(SpuInfoDTO dto,ItemSpu itemSpu){
        ModifyDetailInfo mdInfo = new ModifyDetailInfo();
        //品牌编码
        if(this.validaIsNotSameBoforeAndAfter(dto.getBrandId(),itemSpu.getBrandId())){
            mdInfo.setModifyRecordType(ShopModifyColumn.brand_id.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getBrandId().toString());
            mdInfo.setAfterChange(dto.getBrandId().toString());
            mdInfo.setModifyFieldId(ShopModifyColumn.brand_id.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //平台三级类目
        if(this.validaIsNotSameBoforeAndAfter(dto.getCategoryId(),itemSpu.getCategoryId())){
            mdInfo.setModifyRecordType(ShopModifyColumn.category_id.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getCategoryId().toString());
            mdInfo.setAfterChange(dto.getCategoryId().toString());
            mdInfo.setModifyFieldId(ShopModifyColumn.category_id.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }

        //商品毛重
        if(this.validaIsNotSameBoforeAndAfter(dto.getGrossWeight(),itemSpu.getGrossWeight())){
            mdInfo.setModifyRecordType(ShopModifyColumn.gross_weight.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getGrossWeight() + "");
            mdInfo.setAfterChange(dto.getGrossWeight() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.gross_weight.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品净重
        if(this.validaIsNotSameBoforeAndAfter(dto.getNetWeight(),itemSpu.getNetWeight())){
            mdInfo.setModifyRecordType(ShopModifyColumn.net_weight.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getNetWeight() + "");
            mdInfo.setAfterChange(dto.getNetWeight() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.net_weight.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品高
        if(this.validaIsNotSameBoforeAndAfter(dto.getHigh(),itemSpu.getHigh())){
            mdInfo.setModifyRecordType(ShopModifyColumn.height.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getHigh() + "");
            mdInfo.setAfterChange(dto.getHigh() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.height.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品型号
        if(this.validaIsNotSameBoforeAndAfter(dto.getModelType(),itemSpu.getModelType())){
            mdInfo.setModifyRecordType(ShopModifyColumn.model_type.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getModelType() + "");
            mdInfo.setAfterChange(dto.getModelType() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.model_type.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品单位
        if(this.validaIsNotSameBoforeAndAfter(dto.getUnit(),itemSpu.getUnit())){
            mdInfo.setModifyRecordType(ShopModifyColumn.unit.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getUnit());
            mdInfo.setAfterChange(dto.getUnit());
            mdInfo.setModifyFieldId(ShopModifyColumn.unit.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }

        //商品产地
        if(this.validaIsNotSameBoforeAndAfter(dto.getOrigin(),itemSpu.getOrigin())){
            mdInfo.setModifyRecordType(ShopModifyColumn.origin.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getOrigin() + "");
            mdInfo.setAfterChange(dto.getOrigin() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.origin.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品参数
        if(this.validaIsNotSameBoforeAndAfter(dto.getItemQualification(),itemSpu.getItemQualification())){
            mdInfo.setModifyRecordType(ShopModifyColumn.item_qualification.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getItemQualification());
            mdInfo.setAfterChange(dto.getItemQualification());
            mdInfo.setModifyFieldId(ShopModifyColumn.item_qualification.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //ERP一级类目编码
        if(this.validaIsNotSameBoforeAndAfter(dto.getErpFirstCategoryCode(),itemSpu.getErpFirstCategoryCode())){
            mdInfo.setModifyRecordType(ShopModifyColumn.erp_first_category_code.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getErpFirstCategoryCode() + "");
            mdInfo.setAfterChange(dto.getErpFirstCategoryCode() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.erp_first_category_code.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //ERP五级类目编码
        if(this.validaIsNotSameBoforeAndAfter(dto.getErpFiveCategoryCode(),itemSpu.getErpFiveCategoryCode())){
            mdInfo.setModifyRecordType(ShopModifyColumn.erp_five_category_code.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getErpFiveCategoryCode() + "");
            mdInfo.setAfterChange(dto.getErpFiveCategoryCode() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.erp_five_category_code.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品长
        if(this.validaIsNotSameBoforeAndAfter(dto.getLength(),itemSpu.getLength())){
            mdInfo.setModifyRecordType(ShopModifyColumn.length.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getLength() + "");
            mdInfo.setAfterChange(dto.getLength() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.length.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品宽
        if(this.validaIsNotSameBoforeAndAfter(dto.getWide(),itemSpu.getWide())){
            mdInfo.setModifyRecordType(ShopModifyColumn.width.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getWide() + "");
            mdInfo.setAfterChange(dto.getWide() + "");
            mdInfo.setModifyFieldId(ShopModifyColumn.width.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品销售属性
        if(this.validaIsNotSameBoforeAndAfter(dto.getCategoryAttributes(),itemSpu.getCategoryAttributes())){
            mdInfo.setModifyRecordType(ShopModifyColumn.category_attributes.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getCategoryAttributes());
            mdInfo.setAfterChange(dto.getCategoryAttributes());
            mdInfo.setModifyFieldId(ShopModifyColumn.category_attributes.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //装箱清单
        if(this.validaIsNotSameBoforeAndAfter(dto.getPackingList(),itemSpu.getPackingList())){
            mdInfo.setModifyRecordType(ShopModifyColumn.packing_list.getLabel());
            mdInfo.setModifyRecordId(itemSpu.getSpuId());
            mdInfo.setBeforeChange(itemSpu.getPackingList());
            mdInfo.setAfterChange(dto.getPackingList());
            mdInfo.setModifyFieldId(ShopModifyColumn.packing_list.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
        //商品描述
        ItemSpuDescribe describe = itemSpuDescribeMapper.queryBySpuId(itemSpu.getSpuId());
        if(this.validaIsNotSameBoforeAndAfter(dto.getSpuDesc(),describe.getSpuDesc())){
            mdInfo.setModifyRecordType(ShopModifyColumn.spu_desc.getLabel());
            mdInfo.setModifyRecordId(describe.getDesId());
            mdInfo.setBeforeChange(describe.getSpuDesc());
            mdInfo.setAfterChange(dto.getSpuDesc());
            mdInfo.setModifyFieldId(ShopModifyColumn.spu_desc.name());
            mdInfo.setModifyTableId(ShopModifyColumn.item_spu_describe.name());
            mdInfo.setCreateId(dto.getModifyId());
            mdInfo.setCreateName(dto.getModifyName());
            mdInfo.setCreateTime(new Date());
            modifyDetailInfoMapper.insert(mdInfo);
        }
    }

    private boolean validaIsNotSameBoforeAndAfter(Object before, Object after) {

        if (before == null && after != null) {
            return true;
        } else if (before != null && after != null && !before.equals(after)) {
            return true;
        } else {
            return false;
        }
    }

    @Override public ExecuteResult<SpuInfoDTO> updateDeleteFlag(SpuInfoDTO spuInfoDTO) {
        ExecuteResult<SpuInfoDTO> result = new ExecuteResult<SpuInfoDTO>();
        spuInfoDTO.setDeleteFlag(Byte.valueOf("1"));
        try {
            if (null == spuInfoDTO.getSpuId()) {
                result.setResultMessage("参数不能为空");
                return result;
            }
            itemSpuMapper.updateDeleteFlag(spuInfoDTO);
            itemSpuDescribeMapper.updateDeleteFlag(spuInfoDTO);
            itemSpuPictureMapper.updateDeleteFlag(spuInfoDTO);
        } catch (Exception e) {
            result.setResultMessage("更新商品模板删除状态失败! ");
            result.getErrorMessages().add("更新商品模板删除状态失败! ");
            //            throw new RuntimeException(e);
            return result;
        }
        return result;
    }


    @Override public ExecuteResult<SpuInfoDTO> addItemSpuInfo(SpuInfoDTO spuInfoDTO) {
        LOGGER.info("=============开始推入商品模板====================");
        ExecuteResult<SpuInfoDTO> result = new ExecuteResult<SpuInfoDTO>();
        try {
            if (spuInfoDTO.getItemId() == null) {
                result.addErrorMessage("参数不能为空! ");
                return result;
            }

            ItemDTO itemDTO = itemMybatisDAO.getItemDTOById(spuInfoDTO.getItemId());
            if (itemDTO == null){
                result.addErrorMessage("该商品不存在! ");
                return result;
            }
            ItemSpu itemSpu = itemSpuMapper.queryItemSpuByName(itemDTO.getItemName());
            if (itemSpu != null){
                result.addErrorMessage("该商品模板已存在! ");
                return result;
            }
            // 保存商品模板
            itemSpu = new ItemSpu();
          

            String itemQualification = itemDTO.getItemQualification();
            if (StringUtils.isBlank(itemQualification)){
                itemSpu.setItemQualification("");// 商品参数
            }else {
                itemSpu.setItemQualification(itemQualification);
            }
            itemSpu.setCategoryId(itemDTO.getCid());// 品类ID
            itemSpu.setBrandId(itemDTO.getBrand());// 品牌ID
            itemSpu.setCategoryAttributes(itemDTO.getAttributesStr());// 类目属性keyId:valueId
            itemSpu.setUnit(itemDTO.getWeightUnit());// 单位
            itemSpu.setGrossWeight(itemDTO.getWeight());// 毛重
            // TODO START 商品模板审核状态--(第一期)不审核,(默认审核通过)
            itemSpu.setStatus(HtdItemStatusEnum.PASS.getCode() + "");
            if(spuInfoDTO.getTaxRate()!=null){
            	itemSpu.setTaxRate(spuInfoDTO.getTaxRate());
            }
            // TODO END
            BeanUtils.copyProperties(itemDTO, itemSpu);
            String spuCode = ItemCodeGenerator.generateSpuCode();
            itemSpu.setSpuCode(spuCode);
            itemSpu.setSpuName(itemDTO.getItemName());// 模板名称

            Integer high = new BigDecimal(itemDTO.getHeight()).intValue();
            Integer wide = new BigDecimal(itemDTO.getWidth()).intValue();
            Integer length = new BigDecimal(itemDTO.getLength()).intValue();
            high = high == null ? 0 : high;
            wide = wide == null ? 0 : wide;
            length = length == null ? 0 : length;
            itemSpu.setHigh(high);
            itemSpu.setWide(wide);
            itemSpu.setLength(length);
            BigDecimal volume = BigDecimal.valueOf(high * wide * length);
            itemSpu.setVolume(volume);
            itemSpu.setCreateId(spuInfoDTO.getCreateId());
            itemSpu.setCreateName(spuInfoDTO.getCreateName());
            itemSpu.setCreateTime(spuInfoDTO.getCreateTime());
            itemSpu.setModifyId(spuInfoDTO.getCreateId());
            itemSpu.setModifyName(spuInfoDTO.getCreateName());
            itemSpu.setModifyTime(spuInfoDTO.getModifyTime());
            itemSpuMapper.add(itemSpu);

            // 根据商品ID 获取商品图片List
            List<ItemPicture> itemPictureList = itemPictureDAO.queryItemPicsById(spuInfoDTO.getItemId());
            // 保存商品模板图片
            for(ItemPicture itemPicture : itemPictureList){
                ItemSpuPicture itemSpuPicture = new ItemSpuPicture();
                BeanUtils.copyProperties(itemPicture, itemSpuPicture);
                // TODO item_picture 表中没有 picture_size这个字段 start
                itemSpuPicture.setPictureSize("");
                // TODO item_picture 表中没有 picture_size这个字段 end
                Integer sortNum = itemPicture.getSortNumber()==null ? 0 : itemPicture.getSortNumber();
                Integer deleteFlag = itemPicture.getDeleteFlag()== null ? 0 : itemPicture.getDeleteFlag();
                itemSpuPicture.setSortNum(sortNum);
                itemSpuPicture.setDeleteFlag(deleteFlag.byteValue());
                itemSpuPicture.setSpuId(itemSpu.getSpuId());
                itemSpuPicture.setCreateId(spuInfoDTO.getCreateId());
                itemSpuPicture.setCreateTime(spuInfoDTO.getCreateTime());
                itemSpuPicture.setCreateName(spuInfoDTO.getCreateName());
                itemSpuPicture.setModifyId(spuInfoDTO.getCreateId());
                itemSpuPicture.setModifyTime(spuInfoDTO.getCreateTime());
                itemSpuPicture.setModifyName(spuInfoDTO.getCreateName());
                itemSpuPictureMapper.add(itemSpuPicture);
            }
            // 保存商品模板详情
            ItemDescribe itemDescribe = itemDescribeDAO.getDescByItemId(spuInfoDTO.getItemId());
         /*   if (itemDescribe == null){
                result.addErrorMessage("商品描述信息不存在! ");
                return result;
            }*/
            ItemSpuDescribe itemSpuDescribe = new ItemSpuDescribe();
            itemSpuDescribe.setSpuId(itemSpu.getSpuId());
            String describeContent = itemDescribe.getDescribeContent() == null ? "" : itemDescribe.getDescribeContent();
            //Long desId = itemDescribe.getDesId() == null ? 0L : itemDescribe.getDesId();
            itemSpuDescribe.setSpuDesc(describeContent);
            //itemSpuDescribe.setDesId(desId);
            itemSpuDescribe.setDeleteFlag(Byte.valueOf("0"));
            itemSpuDescribe.setCreateId(spuInfoDTO.getCreateId());
            itemSpuDescribe.setCreateName(spuInfoDTO.getCreateName());
            itemSpuDescribe.setCreateTime(spuInfoDTO.getCreateTime());
            itemSpuDescribeMapper.add(itemSpuDescribe);
            // 更新item的is_spu和item_spu_id
            Item item = new Item();
            item.setItemId(itemDTO.getItemId());
            item.setIsSpu(1);
            item.setItemSpuId(itemSpu.getSpuId());
            this.itemMybatisDAO.updateByPrimaryKeySelective(item);
        } catch (Exception e) {
            LOGGER.error("执行方法【addItemSpuInfo】报错:", e);
            result.addErrorMessage("执行方法【addItemSpuInfo】报错: " + e.getMessage());
            return result;
        } finally {
            LOGGER.info("=============结束推入商品模板====================");
        }
        return result;
    }

	@Override
	public ExecuteResult<SpuInfoDTO> queryItemSpuByName(String spuName) {
		ExecuteResult<SpuInfoDTO> executeResult=new ExecuteResult<SpuInfoDTO>();
		if(StringUtils.isEmpty(spuName)){
			executeResult.setCode(ErrorCodes.E10000.name());
			executeResult.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("spuName")));
			return executeResult;
		}
		ItemSpu itemSpu=itemSpuMapper.queryItemSpuByName(spuName);
		if(itemSpu==null || itemSpu.getDeleteFlag() == 1){
			executeResult.setCode(ErrorCodes.E10000.name());
			executeResult.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("ItemSpu")));
			return executeResult;
		}
		SpuInfoDTO spuInfoDTO =new SpuInfoDTO();
		BeanUtils.copyProperties(itemSpu, spuInfoDTO);
        //查询商品模板图片信息
        List<ItemSpuPictureDTO> itemSpuPictureDTOList = itemSpuPictureMapper.queryBySpuId(spuInfoDTO.getSpuId());
        spuInfoDTO.setItemSpuPictureDTOList(itemSpuPictureDTOList);
        //查询商品模板详情
        ItemSpuDescribe itemSpuDescribe = itemSpuDescribeMapper.queryBySpuId(spuInfoDTO.getSpuId());
        if(itemSpuDescribe!=null){
        	 spuInfoDTO.setSpuDesc(itemSpuDescribe.getSpuDesc());
        }
        ItemBrand brand=itemBrandDAO.queryById(spuInfoDTO.getBrandId());
        if(brand!=null){
        	spuInfoDTO.setBrandName(brand.getBrandName());
        }
        Long cid = itemSpu.getCategoryId();
        ExecuteResult<Map<String, Object>> categoryResult = this.itemCategoryService.queryItemOneTwoThreeCategoryName(cid, ">");
        if (categoryResult != null && categoryResult.isSuccess()) {
            Map<String, Object> categoryMap = categoryResult.getResult();
            spuInfoDTO.setFirstCategoryId((Long) categoryMap.get("firstCategoryId"));
            spuInfoDTO.setFirstCategoryName((String) categoryMap.get("firstCategoryName"));
            spuInfoDTO.setSecondCategoryId((Long) categoryMap.get("secondCategoryId"));
            spuInfoDTO.setSecondCategoryName((String) categoryMap.get("secondCategoryName"));
            spuInfoDTO.setCategoryId((Long) categoryMap.get("thirdCategoryId"));
            spuInfoDTO.setCategoryName((String) categoryMap.get("thirdCategoryName"));
        }
		executeResult.setCode(ErrorCodes.SUCCESS.name());
		executeResult.setResult(spuInfoDTO);
		return executeResult;
	}

  @Override
  public ExecuteResult<List<SpuInfoDTO>> getSpuInfoListByItemId(Long itemId){
      ExecuteResult<List<SpuInfoDTO>> spuResult = new ExecuteResult<List<SpuInfoDTO>>();
      List<SpuInfoDTO> spuList= itemSpuMapper.getSpuInfoListByItemId(itemId);
      spuResult.setResult(spuList);
      return spuResult;
  }

	@Override
	public ExecuteResult<List<ItemSpuPicture>> queryItemSpuPicsByErpcode(
			String erpCode) {
		ExecuteResult<List<ItemSpuPicture>> result=new ExecuteResult<List<ItemSpuPicture>>();
		if(StringUtils.isEmpty(erpCode)){
			result.setCode(ErrorCodes.E10000.name());
			result.setErrorMessages(Lists.newArrayList(ErrorCodes.E10000.getErrorMsg("erpCode")));
			return result;
		}
		
		List<ItemSpuPicture> spuPicList=itemSpuPictureMapper.queryItemSpuPicsByErpcode(erpCode);
		result.setResult(spuPicList);
		return result;
	}

    /**
     * 查询模板列表 - for 超级老板
     * @return
     */
    @Override
    public ExecuteResult<DataGrid<ItemSpuInfoListOutDTO>> queryItemSpuList4SupperBoss(String param, Pager page) {
        ExecuteResult<DataGrid<ItemSpuInfoListOutDTO>> executeResult = new ExecuteResult();
        DataGrid<ItemSpuInfoListOutDTO> dtoDataGrid = new DataGrid<>();
        try {
            if (page == null) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.addErrorMessage("page入参为空");
                return executeResult;
            }
            Long count = this.itemSpuMapper.queryItemSpu4SupperBossListCount(param);
            if (count > 0) {
                List<ItemSpuInfoListOutDTO> list = this.itemSpuMapper.queryItemSpu4SupperBossList(param, page);
                dtoDataGrid.setRows(list);
                dtoDataGrid.setTotal(count);
            }
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
            executeResult.setResult(dtoDataGrid);
        } catch (Exception e) {
            LOGGER.error("查询商品模板列表-超级老板出错, 错误信息:", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 查询模板详情 - 根据spuCode
     * @param spuCode
     * @return
     */
    @Override
    public ExecuteResult<ItemSpuInfoDetailOutDTO> queryItemSpuDetialBySpuCode(String spuCode) {
        ExecuteResult<ItemSpuInfoDetailOutDTO> executeResult = new ExecuteResult();
        try {
            if (StringUtils.isEmpty(spuCode)) {
                executeResult.setCode(ResultCodeEnum.INPUT_PARAM_IS_NULL.getCode());
                executeResult.addErrorMessage("入参为空");
                return executeResult;
            }
            ItemSpuInfoDetailOutDTO itemSpuInfoDetailOutDTO = this.itemSpuMapper.getItemSpuDetailBySpuCode(spuCode);
            if (itemSpuInfoDetailOutDTO == null) {
                executeResult.setCode(ResultCodeEnum.OUTPUT_IS_NULL.getCode());
                executeResult.addErrorMessage("查询对象为null");
                return executeResult;
            }
            // 查询图片
            List<ItemSpuPictureDTO> pictureDTOList = this.itemSpuPictureMapper.queryBySpuId(itemSpuInfoDetailOutDTO.getSpuId());
            itemSpuInfoDetailOutDTO.setItemSpuPictureList(pictureDTOList);
            // 解析图文详情中的url
            itemSpuInfoDetailOutDTO.setItemSpuDescPictureList(parseSpuDesc2PicUrl(itemSpuInfoDetailOutDTO.getSpuDesc()));
            // 设置一级到三级类目
            ExecuteResult<Map<String, Object>> executeResultCategory = this.itemCategoryService.queryItemOneTwoThreeCategoryName(itemSpuInfoDetailOutDTO.getCid(), ">>");
            if (executeResultCategory.isSuccess()) {
                Map<String, Object> map = executeResultCategory.getResult();
                itemSpuInfoDetailOutDTO.setFirstCid((Long) map.get("firstCategoryId"));
                itemSpuInfoDetailOutDTO.setFirstCName((String) map.get("firstCategoryName"));
                itemSpuInfoDetailOutDTO.setSecondCid((Long) map.get("secondCategoryId"));
                itemSpuInfoDetailOutDTO.setSecondCName((String) map.get("secondCategoryName"));
            }
            // 商品单位转码
            String nativeUnit = itemSpuInfoDetailOutDTO.getUnit();
            String unitName = dictionaryUtils.getNameByValue(DictionaryConst.TYPE_ITEM_UNIT, nativeUnit);
            itemSpuInfoDetailOutDTO.setUnit(unitName);
            executeResult.setCode(ResultCodeEnum.SUCCESS.getCode());
            executeResult.setResultMessage(ResultCodeEnum.SUCCESS.getMessage());
            executeResult.setResult(itemSpuInfoDetailOutDTO);
        } catch (Exception e) {
            LOGGER.error("查询商品模板详细, 错误信息:", e);
            executeResult.setCode(ResultCodeEnum.ERROR.getCode());
            executeResult.setResultMessage(ResultCodeEnum.ERROR.getMessage());
            executeResult.addErrorMessage(e.getMessage());
        }
        return executeResult;
    }

    /**
     * 从图文中解析图片url
     * @param spuDesc 图文详情
     * @return
     */
    private static List<String> parseSpuDesc2PicUrl(String spuDesc) {
        List<String> picList = new ArrayList<>();
        try {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(spuDesc)) {
                // 过滤图片
                //目前img标签标示有3种表达式
                //<img alt="" src="1.jpg"/>
                //开始匹配content中的<img />标签
                Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
                Matcher m_img = p_img.matcher(spuDesc);
                boolean result_img = m_img.find();
                if (result_img) {
                    while (result_img) {
                        //获取到匹配的<img />标签中的内容
                        String str_img = m_img.group(2);
                        //开始匹配<img />标签中的src
                        Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                        Matcher m_src = p_src.matcher(str_img);
                        if (m_src.find()) {
                            String str_src = m_src.group(3);
                            picList.add(str_src);
                        }
                        //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                        result_img = m_img.find();
                    }
                }
                return picList;
            }
        } catch (Exception e) {
            LOGGER.error("从图文中解析图片url出错, 错误信息 :", e);
        }
        return picList;
    }
}
