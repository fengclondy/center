package com.bjucloud.contentcenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bjucloud.contentcenter.domain.Floor;
import com.bjucloud.contentcenter.domain.HeadAd;
import com.bjucloud.contentcenter.domain.HotWord;
import com.bjucloud.contentcenter.domain.LoginPage;
import com.bjucloud.contentcenter.domain.MallChannels;
import com.bjucloud.contentcenter.domain.MallType;
import com.bjucloud.contentcenter.domain.MallTypeSub;
import com.bjucloud.contentcenter.domain.SiteLogo;
import com.bjucloud.contentcenter.domain.SiteSlogan;
import com.bjucloud.contentcenter.domain.StaticAd;
import com.bjucloud.contentcenter.domain.SubAd;
import com.bjucloud.contentcenter.domain.SubCarouselAd;
import com.bjucloud.contentcenter.domain.TopAd;

public interface ContentCenterDAO {
	List<HotWord> selectHotWord();

	List<MallChannels> selectMallChannels();

	List<TopAd> selectTopAd();

	List<SiteSlogan> selectSiteSlogan();

	List<SiteLogo> selectSiteLogo();

	List<MallTypeSub> selectMallTypeSub();

	List<MallType> selectMallType();

	/* 取得轮播广告位列表(包括总部和城市站) */
	List<SubCarouselAd> selectSubCarouselAd(@Param("subId") String subId);

	List<HeadAd> selectHeadAd();

	/* 取得静态广告位列表 */

	List<StaticAd> selectStaticAd();

	/* 取得城市站广告位（广告详情列表） */

	List<SubAd> selectSubAd(@Param("subId") String subId);

	/* 取得城市站广告位（店铺列表） */

	List<SubAd> selectStoreSubAd(@Param("subId") String subId);

	/* 取得楼层广告位列表（包括楼层导航、广告详情） */
	List<Floor> selectFloor();

	/* 取得登录页logo */
	List<LoginPage> selectLoginPage();
}
