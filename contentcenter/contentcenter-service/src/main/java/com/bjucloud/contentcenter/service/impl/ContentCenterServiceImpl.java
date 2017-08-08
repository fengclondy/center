package com.bjucloud.contentcenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bjucloud.contentcenter.dao.ContentCenterDAO;
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
import com.bjucloud.contentcenter.service.ContentCenterService;

import cn.htd.common.ExecuteResult;

@Service("contentCenterService")
public class ContentCenterServiceImpl implements ContentCenterService {
	private final static Logger logger = LoggerFactory.getLogger(ContentCenterServiceImpl.class);
	@Resource
	ContentCenterDAO contentCenterDAO;

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
		logger.info("ContentCenterServiceImpl-selectHotWord服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
			logger.error("ContentCenterServiceImpl-selectMallChannels服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectMallChannels服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectTopAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectSiteSlogan服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectSiteLogo服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<MallTypeSub>> selectMallTypeSub() {
		logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行开始,参数:");
		ExecuteResult<List<MallTypeSub>> rs = new ExecuteResult<List<MallTypeSub>>();
		try {
			List<MallTypeSub> list = contentCenterDAO.selectMallTypeSub();
			List<MallTypeSub> resList = new ArrayList<MallTypeSub>();
			int size = list.size();
			MallTypeSub mallTypeSub = null;
			MallTypeSub mallTypeSub1 = null;
			MallTypeSub mallTypeSub2 = null;
			if (list.size() > 0) {
				for (int i = 0; i < size; i++) {
					mallTypeSub = list.get(i);
					if (0 == mallTypeSub.getTypeId()) {
						List<MallTypeSub> resList1 = new ArrayList<MallTypeSub>();
						for (int j = 0; j < size; j++) {
							mallTypeSub1 = list.get(j);
							if (mallTypeSub1.getTypeId() == mallTypeSub.getId()) {
								List<MallTypeSub> resList2 = new ArrayList<MallTypeSub>();
								for (int j2 = 0; j2 < size; j2++) {
									mallTypeSub2 = list.get(j2);
									if (mallTypeSub2.getTypeId() == mallTypeSub1.getId()) {
										resList2.add(mallTypeSub2);
									}
								}
								mallTypeSub1.setMallTypeSubList(resList2);
							}
							mallTypeSub.setMallTypeSubList(resList1);
						}
						resList.add(mallTypeSub1);
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
			logger.error("ContentCenterServiceImpl-selectMallTypeSub服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectMallTypeSub服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<MallType>> selectMallType() {
		logger.info("ContentCenterServiceImpl-selectMallType服务执行开始,参数:");
		ExecuteResult<List<MallType>> rs = new ExecuteResult<List<MallType>>();
		try {
			List<MallType> list = contentCenterDAO.selectMallType();
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
			logger.error("ContentCenterServiceImpl-selectMallType服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectMallType服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<SubCarouselAd>> selectChannelAd() {
		logger.info("ContentCenterServiceImpl-selectChannelAd服务执行开始,参数:");
		ExecuteResult<List<SubCarouselAd>> rs = new ExecuteResult<List<SubCarouselAd>>();
		try {
			List<SubCarouselAd> list = contentCenterDAO.selectSubCarouselAd(null);
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
		logger.info("ContentCenterServiceImpl-selectChannelAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectStaticAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectLoginPage服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<Floor>> selectFloor() {
		logger.info("ContentCenterServiceImpl-selectFloor服务执行开始,参数:");
		ExecuteResult<List<Floor>> rs = new ExecuteResult<List<Floor>>();
		try {
			List<Floor> list = contentCenterDAO.selectFloor();
			if (list != null && list.size() > 0) {
				rs.setResult(list);
				rs.setResultMessage("success");
			} else {
				rs.setResultMessage("fail");
				rs.addErrorMessage("查询结果为空");
			}
		} catch (Exception e) {
			rs.setResultMessage("error");
			rs.addErrorMessage("查询异常");
			logger.error("ContentCenterServiceImpl-selectFloor服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectFloor服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectHeadAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<SubCarouselAd>> selectSubCarouselAd(String subId) {
		logger.info("ContentCenterServiceImpl-selectSubCarouselAd服务执行开始,参数:");
		ExecuteResult<List<SubCarouselAd>> rs = new ExecuteResult<List<SubCarouselAd>>();
		try {
			List<SubCarouselAd> list = contentCenterDAO.selectSubCarouselAd(subId);
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
			logger.error("ContentCenterServiceImpl-selectSubCarouselAd服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectSubCarouselAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
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
		logger.info("ContentCenterServiceImpl-selectSubAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

	@Override
	public ExecuteResult<List<SubAd>> selectStoreSubAd(String subId) {
		logger.info("ContentCenterServiceImpl-selectStoreSubAd服务执行开始,参数:");
		ExecuteResult<List<SubAd>> rs = new ExecuteResult<List<SubAd>>();
		try {
			if (subId != null) {
				List<SubAd> list = contentCenterDAO.selectStoreSubAd(subId);
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
			logger.error("ContentCenterServiceImpl-selectStoreSubAd服务执行异常:" + e);
		}
		logger.info("ContentCenterServiceImpl-selectStoreSubAd服务执行结束,结果:" + JSONObject.toJSONString(rs));
		return rs;
	}

}
