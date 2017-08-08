package cn.htd.membercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.membercenter.domain.Floor;
import cn.htd.membercenter.domain.FloorBrandDO;
import cn.htd.membercenter.domain.FloorInfo;
import cn.htd.membercenter.domain.FloorInfoDO;
import cn.htd.membercenter.domain.FloorNav;
import cn.htd.membercenter.domain.FloorNavDO;
import cn.htd.membercenter.domain.FloorPicDO;
import cn.htd.membercenter.domain.HeadAd;
import cn.htd.membercenter.domain.HotWord;
import cn.htd.membercenter.domain.LoginPage;
import cn.htd.membercenter.domain.MallChannels;
import cn.htd.membercenter.domain.MallType;
import cn.htd.membercenter.domain.MallTypeSub;
import cn.htd.membercenter.domain.SiteLogo;
import cn.htd.membercenter.domain.SiteSlogan;
import cn.htd.membercenter.domain.StaticAd;
import cn.htd.membercenter.domain.SubAd;
import cn.htd.membercenter.domain.SubCarouselAd;
import cn.htd.membercenter.domain.TopAd;

public interface ContentCenterDAO {
	List<HotWord> selectHotWord();

	List<MallChannels> selectMallChannels();

	List<TopAd> selectTopAd();

	List<SiteSlogan> selectSiteSlogan();

	List<SiteLogo> selectSiteLogo();

	List<MallTypeSub> selectMallTypeSub();

	List<MallType> selectMallType();

	/** 取得轮播广告位列表(包括总部和城市站) */
	List<SubCarouselAd> selectSubCarouselAd(@Param("subId") String subId);

	List<HeadAd> selectHeadAd();

	/** 取得静态广告位列表 */

	List<StaticAd> selectStaticAd();

	/** 取得城市站广告位（广告详情列表） */

	List<SubAd> selectSubAd(@Param("subId") String subId);

	/** 取得城市站广告位（店铺列表） */

	List<SubAd> selectStoreSubAd(@Param("subId") String subId);

	/** 取得楼层广告位列表（广告详情） */
	List<Floor> selectFloor(@Param("navId") Long navId);
	
	/**
	 * 取得楼层广告位列表(品牌列表)*/
	List<Floor> selectFloorContentSub(@Param("navId") Long navId); 
	
	/**
	 * 取得楼层基本信息列表
	 * @return
	 */
	List<FloorInfo> selectFloorInfo();
	
	/**
	 * 取得楼层导航列表
	 */
	List<FloorNav> selectFloorNav(@Param("floorId") Long floorId);
	

	/** 取得登录页logo */
	List<LoginPage> selectLoginPage();

	List<MallTypeSub> selectMallTypeSubById(Long id);
	
	
	/**
	 * 查询所有楼层
	 * @return
	 */
	List<FloorInfoDO> getBaseAllFloors();
	
	/**
	 * 根据楼层ID查询所有楼层导航
	 * @param floorId
	 * @return
	 */
	List<FloorNavDO> getBaseFloorNavsByFloorId(@Param("floorId") Long floorId);
	
	/**
	 * 根据楼层ID查询所有品牌
	 * @param floorId
	 * @return
	 */
	List<FloorBrandDO> getBaseFloorBrandsByFloorId(@Param("floorId") Long floorId);
	
	/**
	 * 根据楼层导航查询所有图片
	 * @param floorNavId
	 * @return
	 */
	List<FloorPicDO> getBaseFloorPicsByFloorNavId(@Param("floorNavId") Long floorNavId);
	
	
}
