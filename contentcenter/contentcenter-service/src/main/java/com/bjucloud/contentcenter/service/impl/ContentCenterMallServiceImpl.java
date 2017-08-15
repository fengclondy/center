package com.bjucloud.contentcenter.service.impl;


import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.domain.ItemBrand;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.service.ItemBrandExportService;
import cn.htd.goodscenter.service.ItemCategoryService;
import cn.htd.membercenter.domain.*;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
import cn.htd.membercenter.service.MemberBaseService;

import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.dao.ContentCenterDAO;
import com.bjucloud.contentcenter.service.ContentCenterMallService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

@Service("contentCenterMallService")
public class ContentCenterMallServiceImpl implements ContentCenterMallService {

    private final static Logger logger = LoggerFactory
            .getLogger(ContentCenterMallServiceImpl.class);
    @Resource
    ContentCenterDAO contentCenterDAO;
    @Autowired
    MemberBaseInfoService memberBaseInfoService;
    @Autowired
    ItemBrandExportService itemBrandExportService;
    @Resource
    MemberBaseService memberBaseService;
    @Resource
    private ItemCategoryService itemCategoryService;

    @Override
    public ExecuteResult<List<HotWord>> selectHotWord() {
        logger.info("ContentCenterServiceImpl-selectHotWord服务执行开始,参数:");
        ExecuteResult<List<HotWord>> rs = new ExecuteResult<List<HotWord>>();
        try {
            List<HotWord> list = contentCenterDAO.selectHotWord();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectHotWord服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectHotWord服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<MallChannels>> selectMallChannels() {
        logger.info("ContentCenterServiceImpl-selectMallChannels服务执行开始,参数:");
        ExecuteResult<List<MallChannels>> rs = new ExecuteResult<List<MallChannels>>();
        try {
            List<MallChannels> list = contentCenterDAO.selectMallChannels();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallChannels服务执行异常:"
                    + e);
        }
        logger.info("ContentCenterServiceImpl-selectMallChannels服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<TopAd>> selectTopAd() {
        logger.info("ContentCenterServiceImpl-selectHotWord服务执行开始,参数:");
        ExecuteResult<List<TopAd>> rs = new ExecuteResult<List<TopAd>>();
        try {
            List<TopAd> list = contentCenterDAO.selectTopAd();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectTopAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectTopAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SiteSlogan>> selectSiteSlogan() {
        logger.info("ContentCenterServiceImpl-selectSiteSlogan服务执行开始,参数:");
        ExecuteResult<List<SiteSlogan>> rs = new ExecuteResult<List<SiteSlogan>>();
        try {
            List<SiteSlogan> list = contentCenterDAO.selectSiteSlogan();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectSiteSlogan服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectSiteSlogan服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SiteLogo>> selectSiteLogo() {
        logger.info("ContentCenterServiceImpl-selectSiteLogo服务执行开始,参数:");
        ExecuteResult<List<SiteLogo>> rs = new ExecuteResult<List<SiteLogo>>();

        try {
            List<SiteLogo> list = contentCenterDAO.selectSiteLogo();
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectSiteLogo服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectSiteLogo服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<MallTypeSub>> selectMallTypeSub() {
        logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行开始,参数:");
        ExecuteResult<List<MallTypeSub>> rs = new ExecuteResult<List<MallTypeSub>>();
        try {
            List<MallTypeSub> list = contentCenterDAO.selectMallTypeSub();
            if (list.size() > 0) {
                /**
                 * for (int i = 0; i < size; i++) { mallTypeSub = list.get(i);
                 * if (0 == mallTypeSub.getTypeId()) { List <MallTypeSub>
                 * resList1 = new ArrayList<MallTypeSub>(); for (int j = 0; j <
                 * size; j++) { mallTypeSub1 = list.get(j); if
                 * (mallTypeSub1.getTypeId() == mallTypeSub.getId()) { List
                 * <MallTypeSub> resList2 = new ArrayList<MallTypeSub>(); for
                 * (int j2 = 0; j2 < size; j2++) { mallTypeSub2 = list.get(j2);
                 * if (mallTypeSub2.getTypeId() == mallTypeSub1.getId()) {
                 * resList2.add(mallTypeSub2); } }
                 * mallTypeSub1.setMallTypeSubList(resList2); }
                 * mallTypeSub.setMallTypeSubList(resList1); }
                 * resList.add(mallTypeSub1); }
                 *
                 * }
                 **/
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallTypeSub服务执行异常:"
                    + e);
        }
        logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<MallTypeSub>> selectMallTypeSubById(Long id) {
        logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行开始,参数:");
        ExecuteResult<List<MallTypeSub>> rs = new ExecuteResult<List<MallTypeSub>>();
        try {
            List<MallTypeSub> list = contentCenterDAO.selectMallTypeSubById(id);
            List<MallTypeSub> resList = new ArrayList<MallTypeSub>();
            List<String> type2s = new ArrayList<String>();
            int size = list.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    int type2Size = type2s.size();
                    boolean isExit = false;// 二级类目值是否存在
                    for (int j = 0; j < type2Size; j++) {
                        String type2 = type2s.get(j);
                        if (type2.equals(list.get(i).getType2())) {
                            isExit = true;
                            break;
                        }
                    }
                    if (!isExit) {
                        type2s.add(list.get(i).getType2());
                    }
                }

                int type2Size = type2s.size();
                for (int i = 0; i < type2Size; i++) {
                    MallTypeSub mallTypeSub = null;
                    String type2 = type2s.get(i);
                    List<MallTypeSub> type3List = new ArrayList<MallTypeSub>();
                    for (int j = 0; j < size; j++) {
                        if (type2.equals(list.get(j).getType2())) {
                            mallTypeSub = list.get(j);
                            type3List.add(list.get(j));
                        }
                    }
                    if (null != mallTypeSub) {
                        mallTypeSub.setMallTypeSubList(type3List);
                        resList.add(mallTypeSub);
                    }
                }

                rs.setResult(resList);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallTypeSub服务执行异常:"
                    + e);
        }
        logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<MallType>> selectMallType() {
        logger.info("ContentCenterServiceImpl-selectMallType服务执行开始,参数:");
        ExecuteResult<List<MallType>> rs = new ExecuteResult<List<MallType>>();
        try {
            List<MallType> list = contentCenterDAO.selectMallType();
            int size = list.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    MallType mallType = list.get(i);
                    List<MallTypeSub> subList = contentCenterDAO.selectMallTypeSubById(mallType.getId());
                    mallType.setMallTypeSub(subList);
                }
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallType服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectMallType服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SubCarouselAd>> selectChannelAd() {
        logger.info("ContentCenterServiceImpl-selectChannelAd服务执行开始,参数:");
        ExecuteResult<List<SubCarouselAd>> rs = new ExecuteResult<List<SubCarouselAd>>();
        try {
            List<SubCarouselAd> list = contentCenterDAO
                    .selectSubCarouselAd(null);
            List<HeadAd> headList = contentCenterDAO.selectHeadAd();
            int headSize = headList.size();
            SubCarouselAd ad = null;
            for (int i = 0; i < headSize; i++) {
                ad = new SubCarouselAd();
                BeanUtils.copyProperties(headList.get(i), ad);
                list.add(ad);
            }
            if (list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectChannelAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectChannelAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<StaticAd>> selectStaticAd() {
        logger.info("ContentCenterServiceImpl-selectStaticAd服务执行开始,参数:");
        ExecuteResult<List<StaticAd>> rs = new ExecuteResult<List<StaticAd>>();
        try {
            List<StaticAd> list = contentCenterDAO.selectStaticAd();
            if (list != null && list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectStaticAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectStaticAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<LoginPage> selectLoginPage() {
        logger.info("ContentCenterServiceImpl-selectLoginPage服务执行开始,参数:");
        ExecuteResult<LoginPage> rs = new ExecuteResult<LoginPage>();
        try {
            List<LoginPage> list = contentCenterDAO.selectLoginPage();
            if (list != null && list.size() > 0) {
                rs.setResult(list.get(0));
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectLoginPage服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectLoginPage服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<FloorInfo>> selectFloorList() {
        logger.info("ContentCenterServiceImpl-selectFloor服务执行开始,参数:");
        ExecuteResult<List<FloorInfo>> rs = new ExecuteResult<List<FloorInfo>>();
        try {
            //获取楼层列表
            List<FloorInfo> floorInfoList = contentCenterDAO.selectFloorInfo();
            int floorInfoSize = floorInfoList.size();
            if(floorInfoSize>0){
                for(int i=0;i<floorInfoSize;i++){
                    //获取导航栏列表
                    List<FloorNav> floorNavList = contentCenterDAO.selectFloorNav(floorInfoList.get(i).getId());
                    int floorNavSize = floorNavList.size();
                    if(floorNavSize>0){
                        for(int j=0;j<floorNavSize;j++){
                            //获取楼层内容列表
                            List<Floor> floorList = contentCenterDAO.selectFloor(floorNavList.get(j).getId());
                            //获取楼层品牌列表
                            List<Floor> contentSubList = contentCenterDAO.selectFloorContentSub(floorNavList.get(j).getId());
                            int contSubSize = contentSubList.size();
                            if(contSubSize>0 && contentSubList!=null){
                                for(int k=0;k<contSubSize;k++){
                                    ExecuteResult<ItemBrand> result = itemBrandExportService.queryItemBrandById(contentSubList.get(k).getBrandId());
                                    if(result!=null){
                                        contentSubList.get(k).setBrandLogoUrl(result.getResult().getBrandLogoUrl());
                                    }
                                }
                            }
                            floorNavList.get(j).setFloorList(floorList);
                            floorNavList.get(j).setContentSubList(contentSubList);
                        }
                    }
                    floorInfoList.get(i).setFloorNavList(floorNavList);
                }
                rs.setResult(floorInfoList);
                rs.setResultMessage("success");
            }else{
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("error");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectFloor服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectFloor服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<HeadAd>> selectHeadAd() {
        logger.info("ContentCenterServiceImpl-selectHeadAd服务执行开始,参数:");
        ExecuteResult<List<HeadAd>> rs = new ExecuteResult<List<HeadAd>>();
        try {
            List<HeadAd> list = contentCenterDAO.selectHeadAd();
            if (list != null && list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectHeadAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectHeadAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SubCarouselAd>> selectSubCarouselAd(String subId) {
        logger.info("ContentCenterServiceImpl-selectSubCarouselAd服务执行开始,参数:");
        ExecuteResult<List<SubCarouselAd>> rs = new ExecuteResult<List<SubCarouselAd>>();
        try {
            List<SubCarouselAd> list = contentCenterDAO
                    .selectSubCarouselAd(subId);
            if (list != null && list.size() > 0) {
                rs.setResult(list);
                rs.setResultMessage("success");
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("查询结果为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectSubCarouselAd服务执行异常:"
                    + e);
        }
        logger.info("ContentCenterServiceImpl-selectSubCarouselAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SubAd>> selectSubAd(String subId) {
        logger.info("ContentCenterServiceImpl-selectSubAd服务执行开始,参数:");
        ExecuteResult<List<SubAd>> rs = new ExecuteResult<List<SubAd>>();
        try {
            if (subId != null) {
                List<SubAd> list = contentCenterDAO.selectSubAd(subId);
                if (list != null && list.size() > 0) {
                    rs.setResult(list);
                    rs.setResultMessage("success");
                } else {
                    rs.setResultMessage("fail");
                    rs.addErrorMessage("查询结果为空");
                }
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("城市站不能为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectSubAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectSubAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<SubAd>> selectStoreSubAd(String subId, int sort) {
        logger.info("ContentCenterServiceImpl-selectStoreSubAd服务执行开始,参数:");
        ExecuteResult<List<SubAd>> rs = new ExecuteResult<List<SubAd>>();
        try {
            if (subId != null) {
                List<SubAd> list = contentCenterDAO.selectStoreSubAd(subId);
                int size = list.size();
                if (list != null && size > 0) {
                    // 判断给定条件个数与数据库查询的个数比较
                    if (sort > size) {
                        sort = size;
                    }
                    for (int i = 0; i < sort; i++) {
                        // 查询会员ID
                        ExecuteResult<MemberBaseInfoDTO> result = memberBaseInfoService
                                .queryMemberBaseInfoByMemberCode(list.get(i)
                                        .getSellerCode());
                        if (result.getResult() != null) {
                            Long memberId = result.getResult().getId();
                            MemberExtendInfo memberExtendInfo = memberBaseService
                                    .queryMemberExtendInfoById(memberId);
                            if (memberExtendInfo != null) {
                                list.get(i).setBusinessScope(
                                        memberExtendInfo.getBusinessScope());
                                list.get(i).setBusinessCategory(
                                        memberExtendInfo.getBusinessCategory());
                                list.get(i).setMajorBusinessCategory(
                                        memberExtendInfo
                                                .getMajorBusinessCategory());
                                list.get(i).setBusinessBrand(
                                        memberExtendInfo.getBusinessBrand());
                                list.get(i).setOperatingLife(
                                        memberExtendInfo.getOperatingLife());
                                list.get(i).setMemberId(memberId);
                            } else {
                                rs.setResultMessage("未查询到主营类目数据");
                            }
                        } else {
                            rs.setResultMessage("未查询到会员ID数据");
                        }
                    }
                    rs.setResult(list);
                    rs.setResultMessage("success");
                } else {
                    rs.setResultMessage("fail");
                    rs.addErrorMessage("查询结果为空");
                }
            } else {
                rs.setResultMessage("fail");
                rs.addErrorMessage("城市站不能为空");
            }
        } catch (Exception e) {
            rs.setResultMessage("fail");
            rs.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectStoreSubAd服务执行异常:" + e);
        }
        logger.info("ContentCenterServiceImpl-selectStoreSubAd服务执行结束,结果:"
                + JSONObject.toJSONString(rs));
        return rs;
    }

    @Override
    public ExecuteResult<List<FloorInfoDO>> getAllFloors() {

        logger.info("ContentCenterServiceImpl-getAllFloors服务执行开始......");

        // 返回状态 ：0.失败  1.成功

        ExecuteResult<List<FloorInfoDO>> result = new ExecuteResult<List<FloorInfoDO>>();

        try {
            //获取所有楼层列表
            List<FloorInfoDO> floorInfoList = contentCenterDAO.getBaseAllFloors();
            if(null == floorInfoList || floorInfoList.size() < 1){
                result.setCode("1");
                result.setResultMessage("成功");
                result.setResult(null);
                return result;
            }
            //循环处理所有楼层
            for(FloorInfoDO floorInfo : floorInfoList){

                //[start] ----------------- 处理楼层导航 --------------------------------------
                //获取当前楼层的所有楼层导航
                List<FloorNavDO>   floorNavList = contentCenterDAO.getBaseFloorNavsByFloorId(floorInfo.getId());
                //如果为空跳出当前循环
                if(null == floorNavList || floorNavList.size() < 1){
                    continue;
                }

                //循环当前楼层的所有楼层导航
                for(FloorNavDO floorNav : floorNavList){
                    //获取当前楼层导航的所有图片
                    List<FloorPicDO>  floorPicList = contentCenterDAO.getBaseFloorPicsByFloorNavId(floorNav.getId());
                    //如果为空跳出当前循环
                    if(null == floorPicList || floorPicList.size() < 1){
                        continue;
                    }
                    //设置当前楼层导航的所有图片
                    floorNav.setFloorPicList(floorPicList);
                }
                //设置当前楼层的所有楼层导航
                floorInfo.setFloorNavList(floorNavList);
                //[end] ----------------- 处理楼层导航 --------------------------------------


                //[start] ----------------- 处理楼层品牌 --------------------------------------
                //获取当前楼层的所有品牌
                List<FloorBrandDO> floorBrandList = contentCenterDAO.getBaseFloorBrandsByFloorId(floorInfo.getId());
                //如果为空跳出当前循环
                if(null == floorBrandList || floorBrandList.size() < 1){
                    continue;
                }
                //循环当前楼层的所有品牌
                for(FloorBrandDO floorBrand : floorBrandList){
                    ExecuteResult<ItemBrand> itemBrands = itemBrandExportService.queryItemBrandById(floorBrand.getBrandId());
                    if(null != itemBrands){
                        floorBrand.setBrandLogoUrl(itemBrands.getResult().getBrandLogoUrl());
                    }
                }

                //设置当前楼层的所有品牌
                floorInfo.setFloorBrandList(floorBrandList);
                //[end] ----------------- 处理楼层品牌 --------------------------------------

            }

            result.setCode("1");
            result.setResultMessage("成功");
            result.setResult(floorInfoList);

        } catch (Exception e) {
            logger.error("获取楼层数据异常！！！",e);

            result.setCode("0");
            result.setResultMessage("失败");
            result.addErrorMessage(e.toString());
            result.setResult(null);
        }

        logger.info("ContentCenterServiceImpl-getAllFloors服务执行结束,结果:" + JSONObject.toJSONString(result));

        return result;
    }

    @Override
    public ExecuteResult<List<MobileMallCategory>> queryMobileMallFirstCategoryList() {
        ExecuteResult<List<MobileMallCategory>> executeResult = new ExecuteResult<List<MobileMallCategory>>();
        try {
            List<MobileMallCategory> mobileMallCategoryList = new ArrayList<MobileMallCategory>();
            List<MallType> list = contentCenterDAO.selectMallType();
            for (MallType mallType : list) {
                MobileMallCategory mobileMallCategory = new MobileMallCategory();
                mobileMallCategory.setId(String.valueOf(mallType.getId()));
                mobileMallCategory.setName(mallType.getName());
                mobileMallCategoryList.add(mobileMallCategory);
            }
            executeResult.setResult(mobileMallCategoryList);
            executeResult.setResultMessage("success");
        } catch (Exception e) {
            executeResult.setResultMessage("fail");
            executeResult.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallType服务执行异常:" + e);
        }
        return executeResult;
    }

    @Override
    public ExecuteResult<List<MobileMallCategory>> queryMobileMallChildCategoryListByTypeId(Long id) {
        ExecuteResult<List<MobileMallCategory>> executeResult = new ExecuteResult<List<MobileMallCategory>>();
        try {
            List<MallTypeSub> mallTypeSubList = contentCenterDAO.selectMallTypeSubById(id);
            // 查询类目图片
            List<Long> cidList = new ArrayList<Long>();
            for (MallTypeSub mallTypeSub : mallTypeSubList) {
                cidList.add(Long.valueOf(mallTypeSub.getType3()));
            }
            ExecuteResult<List<ItemCategoryDTO>> executeResult1 = this.itemCategoryService.getCategoryListByCids(cidList);
            if (executeResult1 != null && executeResult1.isSuccess()) {
                List<ItemCategoryDTO> itemCategoryDTOList = executeResult1.getResult();
                for (MallTypeSub mallTypeSub : mallTypeSubList) {
                    Long cid = Long.valueOf(mallTypeSub.getType3());
                    for (ItemCategoryDTO itemCategoryDTO : itemCategoryDTOList) {
                        if (cid.equals(itemCategoryDTO.getCategoryCid())) {
                            mallTypeSub.setPicUrl(itemCategoryDTO.getCategoryPicUrl());
                        }
                    }
                }
            } else {
                logger.error("批量查询商品中心后台类目出错, 错误信息: {}", executeResult1.getErrorMessages());
            }
            // 封装二级类目
            List<MobileMallCategory> secondCategoryList = new ArrayList<MobileMallCategory>();
            Set<String> handledSecondCategorySet = new HashSet<String>(); // 已经处理的二级类目ID集合
            for (MallTypeSub mallTypeSub : mallTypeSubList) {
                if (handledSecondCategorySet.contains(mallTypeSub.getType2())) { // 已经添加到集合中，continue
                    continue;
                }
                MobileMallCategory mobileMallCategory = new MobileMallCategory();
                mobileMallCategory.setId(mallTypeSub.getType2());
                mobileMallCategory.setName(mallTypeSub.getType2Name());
                secondCategoryList.add(mobileMallCategory);
                handledSecondCategorySet.add(mallTypeSub.getType2());
            }
            // 提取三级类目到二级类目中； 数据结构 {二级类目ID : 三级类目集合}
            Map<String, List<MobileMallCategory>> thirdCategoryMap = new HashMap<String, List<MobileMallCategory>>();
            for (MallTypeSub mallTypeSub : mallTypeSubList) {
                for (MobileMallCategory mobileMallCategory : secondCategoryList) {
                    if (mobileMallCategory.getId().equals(mallTypeSub.getType2())) {
                        List<MobileMallCategory> thirdCategoryList = thirdCategoryMap.get(mobileMallCategory.getId());
                        if (thirdCategoryList == null) {
                            thirdCategoryList = new ArrayList<MobileMallCategory>();
                            thirdCategoryMap.put(mobileMallCategory.getId(), thirdCategoryList);
                        }
                        MobileMallCategory thirdCategory = new MobileMallCategory();
                        thirdCategory.setId(mallTypeSub.getType3());
                        thirdCategory.setName(mallTypeSub.getType3Name());
                        thirdCategory.setPictrueUrl(mallTypeSub.getPicUrl());
                        thirdCategoryList.add(thirdCategory);
                    }
                }
            }
            // 合并三级到二级
            Iterator iterator = thirdCategoryMap.keySet().iterator();
            while (iterator.hasNext()) {
                String secondCategoryId = (String) iterator.next();
                for (MobileMallCategory mobileMallCategory : secondCategoryList) {
                    if (mobileMallCategory.getId().equals(secondCategoryId)) {
                        mobileMallCategory.setChildCategory(thirdCategoryMap.get(secondCategoryId));
                    }
                }
            }
            executeResult.setResult(secondCategoryList);
        } catch (Exception e) {
            executeResult.setResultMessage("fail");
            executeResult.addErrorMessage("查询异常");
            logger.error("ContentCenterServiceImpl-selectMallTypeSub服务执行异常:", e);
        }
        return executeResult;
    }

}
