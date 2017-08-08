package com.bjucloud.contentcenter.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.common.utils.DateDealUtils;
import com.bjucloud.contentcenter.dao.MallAdCountDAO;
import com.bjucloud.contentcenter.dao.MallBannerDAO;
import com.bjucloud.contentcenter.domain.MallBanner;
import com.bjucloud.contentcenter.dto.AdReportInDTO;
import com.bjucloud.contentcenter.dto.AdReportOutDTO;
import com.bjucloud.contentcenter.dto.MallBannerDTO;
import com.bjucloud.contentcenter.dto.MallBannerInDTO;
import com.bjucloud.contentcenter.service.MallBannerExportService;

@Service("mallBannerExportService")
public class MallBannerExportServiceImpl implements MallBannerExportService {

	private static final Logger logger = LoggerFactory.getLogger(MallBannerExportServiceImpl.class);

	@Resource
	private MallBannerDAO mallBannerDAO;

	@Resource
	private MallAdCountDAO mallAdCountDAO;

	public DataGrid<MallBannerDTO> queryMallBannerList(String publishFlag, Pager page) {
		logger.debug("------------------前台轮播列表查询接口------------------");
		DataGrid<MallBannerDTO> dg = new DataGrid<MallBannerDTO>();
		try {
			/**
			 * publishFlag 展示标记默认为0 传入参数不正确也默认为0
			 */
			logger.debug("------------------前台轮播列表查询接口,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page));
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(date);
			List<MallBannerDTO> list = this.mallBannerDAO.queryListDTO(nowTime, publishFlag, page);
			dg.setTotal(this.mallBannerDAO.queryCountDTO(nowTime, publishFlag));
			dg.setRows(list);
			logger.debug("------------------前台轮播列表查询接口，返回结果：" + JSON.toJSONString(dg));
		} catch (Exception e) {
			logger.error("执行方法【queryMallBannerList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	public void delectMallBanner(Long id) {
		mallBannerDAO.delete(id);
	}

	public DataGrid<MallBannerDTO> queryMallBannerList(MallBannerDTO mallBannerDTO, String publishFlag, Pager page) {
		logger.debug("------------------后台轮播列表查询接口------------------");
		DataGrid<MallBannerDTO> dg = new DataGrid<MallBannerDTO>();
		try {
			/**
			 * publishFlag 展示标记默认为0 显示未生效数据 1 显示前台展示的数据 传入参数不正确也默认为0
			 */
			logger.debug("------------------后台轮播列表查询接口,传入参数publishFlag=" + publishFlag + ",page=" + JSON.toJSONString(page) + ",对象MallBannerDTO==" + JSON.toJSONString(mallBannerDTO));
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(date);
			List<MallBannerDTO> list = this.mallBannerDAO.queryListDTOToAdmin(mallBannerDTO, nowTime, publishFlag, page);
			dg.setTotal(this.mallBannerDAO.queryCountDTOToAdmin(mallBannerDTO, nowTime, publishFlag));
			dg.setRows(list);
			logger.debug("------------------后台轮播列表查询接口，返回结果：" + JSON.toJSONString(dg));
		} catch (Exception e) {
			logger.error("执行方法【queryMallBannerList】报错！{}", e);
			throw new RuntimeException(e);
		}
		return dg;
	}

	public MallBannerDTO getMallBannerById(long id) {
		MallBannerDTO mallBannerDTO = new MallBannerDTO();
		try {
			logger.debug("------------------轮播详情查询接口------------------");
			mallBannerDTO = this.mallBannerDAO.queryById(id);
		} catch (Exception e) {
			logger.error("执行方法【getMallBannerById】报错！{}", e);
			throw new RuntimeException(e);
		}
		return mallBannerDTO;
	}

	public ExecuteResult<String> addMallBanner(MallBannerInDTO mallBannerInDTO) {
		logger.debug("------------------轮播添加架接口------------------");
		ExecuteResult<String> re = checkDTO(mallBannerInDTO);
		logger.debug("------------------轮播添加架接口，对传入对象MallBannerInDTO的校验结果：" + JSON.toJSONString(re.getErrorMessages()));
		if (re.getErrorMessages() != null && !re.getErrorMessages().isEmpty()) {
			re.setResult("false");
			re.setResultMessage("修改失败");
			return re;
		}
		try {
			MallBanner mallBanner = new MallBanner();
			mallBanner.setBannerLink(mallBannerInDTO.getBannerLink());
			mallBanner.setBannerUrl(mallBannerInDTO.getBannerUrl());
			mallBanner.setTitle(mallBannerInDTO.getTitle());
			mallBanner.setSortNumber(mallBannerInDTO.getSortNumber());
			mallBanner.setType(Integer.valueOf(mallBannerInDTO.getType()));
			mallBanner.setBgimgUrl(mallBannerInDTO.getBgimgUrl());
			mallBanner.setThemeId(mallBannerInDTO.getThemeId());
			mallBanner.setPlatformId(mallBannerInDTO.getPlatformId());
			String timeFlag = mallBannerInDTO.getTimeFlag();
			if (timeFlag != null && "0".equals(timeFlag)) {
				mallBanner.setStartTime(mallBannerInDTO.getStartTime());
				mallBanner.setEndTime(mallBannerInDTO.getEndTime());
				mallBanner.setStatus("0");
			} else {
				mallBanner.setStatus("1");
			}

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(date);
			mallBanner.setCreated(nowTime);
			mallBanner.setModified(nowTime);
			logger.debug("------------------轮播添加架接口，传入对象MallBannerInDTO转换成MallBanner对象结果：" + JSON.toJSONString(mallBanner));
			this.mallBannerDAO.add(mallBanner);
			re.setResult("true");
			re.setResultMessage("保存成功");
		} catch (Exception e) {
			logger.error("执行方法【addMallBanner】报错！{}", e);
			re.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		logger.debug("------------------轮播详情查询接口，返回结果：" + JSON.toJSONString(re));
		return re;
	}

	public ExecuteResult<String> modifyMallBanner(MallBannerInDTO mallBannerInDTO) {
		logger.debug("------------------轮播修改架接口------------------");
		ExecuteResult<String> re = checkDTO(mallBannerInDTO);
		logger.debug("------------------轮播修改架接口，对传入对象MallBannerInDTO的校验结果：" + JSON.toJSONString(re.getErrorMessages()));
		if (re.getErrorMessages() != null && !re.getErrorMessages().isEmpty()) {
			re.setResult("false");
			re.setResultMessage("修改失败");
			return re;
		}
		try {
			MallBanner mallBanner = new MallBanner();
			mallBanner.setId(mallBannerInDTO.getId());
			mallBanner.setBannerLink(mallBannerInDTO.getBannerLink());
			mallBanner.setBannerUrl(mallBannerInDTO.getBannerUrl());
			mallBanner.setTitle(mallBannerInDTO.getTitle());
			mallBanner.setSortNumber(mallBannerInDTO.getSortNumber());
			mallBanner.setType(Integer.valueOf(mallBannerInDTO.getType()));
			mallBanner.setBgimgUrl(mallBannerInDTO.getBgimgUrl());
			mallBanner.setThemeId(mallBannerInDTO.getThemeId());
			String timeFlag = mallBannerInDTO.getTimeFlag();
			if (timeFlag != null && "0".equals(timeFlag)) {
				if (mallBannerInDTO.getStartTime() != null && mallBannerInDTO.getStartTime().toString().length() > 0) {
					mallBanner.setStartTime(mallBannerInDTO.getStartTime());
				} else {
					mallBanner.setStartTime(null);
				}
				if (mallBannerInDTO.getEndTime() != null && mallBannerInDTO.getEndTime().toString().length() > 0) {
					mallBanner.setEndTime(mallBannerInDTO.getEndTime());
				} else {
					mallBanner.setEndTime(null);
				}
			} else {
				mallBanner.setStartTime(null);
				mallBanner.setEndTime(null);
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(date);
			mallBanner.setModified(nowTime);
			logger.debug("------------------轮播修改架接口，传入对象MallBannerInDTO转换成MallBanner对象：" + JSON.toJSONString(mallBanner));
			Integer num = this.mallBannerDAO.update(mallBanner);
			if (num > 0) {
				re.setResult("true");
				re.setResultMessage("修改成功");
			} else {
				re.setResult("false");
				re.setResultMessage("修改失败");
			}
		} catch (Exception e) {
			logger.error("执行方法【modifyMallBanner】报错！{}", e);
			re.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		logger.debug("------------------轮播修改架接口，返回结果：" + JSON.toJSONString(re));
		return re;
	}

	public ExecuteResult<String> motifyMallBannerStatus(Long id, String publishFlag) {
		logger.debug("------------------轮播上下架接口------------------");
		ExecuteResult<String> re = new ExecuteResult<String>();
		try {
			boolean bl = checkPublishFlag(publishFlag);
			logger.debug("------------------轮播上下架接口,参数校验结果：" + bl);
			if (bl) {
				Integer num = this.mallBannerDAO.updateStatusById(id, publishFlag);
				if (num > 0) {
					re.setResult("true");
					re.setResultMessage("修改成功");
				} else {
					re.setResult("false");
					re.setResultMessage("修改失败");
				}
			} else {
				List<String> errorMessages = new ArrayList<String>();
				errorMessages.add("上下架标记不合法");
				re.setErrorMessages(errorMessages);
			}
			logger.debug("------------------轮播上下架接口，返回结果：" + JSON.toJSONString(re));
		} catch (Exception e) {
			logger.error("执行方法【motifyMallBannerStatus】报错！{}", e);
			re.getErrorMessages().add(e.getMessage());
		}
		return re;
	}

	public ExecuteResult<String> modifyMallBannerSort(Long id, Integer sortNum) {
		logger.debug("------------------轮播排序架接口------------------");
		ExecuteResult<String> re = new ExecuteResult<String>();
		try {
			List<String> errorMessages = new ArrayList<String>();
			logger.debug("------------------轮播排序架接口,传入参数id=" + id + ",sortNum=" + sortNum);
			if (sortNum == null) {
				re.setResult("false");
				errorMessages.add("排序号能为空！");
				re.setErrorMessages(errorMessages);
				return re;
			} else if (sortNum < 0) {
				re.setResult("false");
				errorMessages.add("排序号不合法，必须是大于0的正整数！");
				re.setErrorMessages(errorMessages);
				return re;
			}
			Integer num = this.mallBannerDAO.updateSortNumberById(id, sortNum);
			if (num > 0) {
				re.setResult("true");
				re.setResultMessage("修改成功");
			} else {
				re.setResult("false");
				re.setResultMessage("修改失败");
			}
			logger.debug("------------------轮播上下架接口，返回结果：" + JSON.toJSONString(re));
		} catch (Exception e) {
			logger.error("执行方法【modifyMallBannerSort】报错！{}", e);
			re.getErrorMessages().add(e.getMessage());
		}
		return re;
	}

	private ExecuteResult<String> checkDTO(MallBannerInDTO mallBannerInDTO) {
		ExecuteResult<String> re = new ExecuteResult<String>();
		List<String> errorMessages = new ArrayList<String>();
		if (StringUtils.isEmpty(mallBannerInDTO.getBannerUrl())) {
			errorMessages.add("图片URL不能为空！");
		}
		if (StringUtils.isEmpty(mallBannerInDTO.getBannerLink())) {
			errorMessages.add("跳转链接不能为空！");
		}
		if (StringUtils.isEmpty(mallBannerInDTO.getTitle())) {
			errorMessages.add("轮播图名称不能为空！");
		} else if (mallBannerInDTO.getTitle() != null && mallBannerInDTO.getTitle().length() > 30) {
			errorMessages.add("轮播图名称不能超过30字！");
		}
		Integer sortNumber = mallBannerInDTO.getSortNumber();
		if (sortNumber == null) {
			errorMessages.add("排序号能为空！");
		} else if (sortNumber < 0 || sortNumber > 1000) {
			errorMessages.add("排序号不合法，必须是大于0小于1000的整数！");
		}
		if (StringUtils.isEmpty(mallBannerInDTO.getTimeFlag())) {
			errorMessages.add("时间标记不能为空！");
		}
		re.setErrorMessages(errorMessages);
		return re;
	}

	private boolean checkPublishFlag(String publishFlag) {
		if (StringUtils.isEmpty(publishFlag)) {
			logger.error("轮播列表查询,传入的参数展示标记publishFlag为空");
			return false;
		}
		if (!"0".equals(publishFlag) && !"1".equals(publishFlag)) {
			logger.error("轮播列表查询,传入的参数展示标记publishFlag不合法");
			return false;
		}
		return true;
	}

	@Override
	public DataGrid<AdReportOutDTO> queryReportList(AdReportInDTO adReportInDto, Pager pager) {
		// 去掉日期格式
		adReportInDto.setClickDate(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDate()));
		adReportInDto.setClickDateBegin(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDateBegin()));
		adReportInDto.setClickDateEnd(DateDealUtils.dateWithoutFormat(adReportInDto.getClickDateEnd()));
		DataGrid<AdReportOutDTO> dtos = new DataGrid<AdReportOutDTO>();
		List<AdReportOutDTO> res = mallAdCountDAO.queryBannerReportList(adReportInDto, pager);
		long count = mallAdCountDAO.queryBannerReportCount(adReportInDto);
		if (adReportInDto.getDateFormat() != null && adReportInDto.getDateFormat().length() > 0) {
			for (AdReportOutDTO dto : res) {
				dto.setClickDate(
						DateDealUtils.getDateStrToStr(dto.getClickDate(), "yyyyMMdd", adReportInDto.getDateFormat()));
			}
		}
		dtos.setRows(res);
		dtos.setTotal(count);
		return dtos;
	}

	/**
	 * 
	 * <p>
	 * Discription:[查询定时发布轮播图]
	 * </p>
	 */
	@Override
	public List<MallBannerDTO> queryTimeListDTO() {
		return mallBannerDAO.queryTimeListDTO();
	}

	@Override
	public DataGrid<MallBannerDTO>  queryMobileShopBannerList(MallBannerDTO mallBannerDTO,Pager pager) {
		DataGrid<MallBannerDTO> dg = new DataGrid<MallBannerDTO>();
		List<MallBannerDTO> mallBanners =mallBannerDAO.queryMobileShopBannerList(mallBannerDTO,pager);
		dg.setTotal(this.mallBannerDAO.queryMobileShopBannerCount(mallBannerDTO));
		dg.setRows(mallBanners);
		return dg;
	}

	@Override
	public MallBannerDTO queryMobileShopBannerById(Long id) {
		return mallBannerDAO.queryMobileShopBannerById(id);
	}

	@Override
	public ExecuteResult<String> modifyMobileShopMallBanner(MallBannerDTO mallBannerDTO) {
		logger.debug("------------------轮播修改架接口------------------");
		ExecuteResult<String> re = new ExecuteResult<String>();
		try {
			MallBanner mallBanner = new MallBanner();
			mallBanner.setId(mallBannerDTO.getId());
			mallBanner.setStatus(mallBannerDTO.getStatus());
			mallBanner.setBannerLink(mallBannerDTO.getBannerLink());
			mallBanner.setBannerUrl(mallBannerDTO.getBannerUrl());
			mallBanner.setTitle(mallBannerDTO.getTitle());
			mallBanner.setSortNumber(mallBannerDTO.getSortNumber());
			mallBanner.setType(Integer.valueOf(mallBannerDTO.getType()));
			mallBanner.setBgimgUrl(mallBannerDTO.getBgimgUrl());
			mallBanner.setThemeId(mallBannerDTO.getThemeId());
			mallBanner.setCreated(mallBanner.getCreated());
			if(mallBannerDTO.getPlatformId()!=null){
				mallBanner.setPlatformId(Double.parseDouble(mallBannerDTO.getPlatformId().toString()));
			}

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = format.format(date);
			mallBanner.setModified(nowTime);
			logger.debug("------------------轮播修改架接口，传入对象MallBannerInDTO转换成MallBanner对象：" + JSON.toJSONString(mallBanner));
			Integer num = this.mallBannerDAO.update(mallBanner);
			if (num > 0) {
				re.setResult("true");
				re.setResultMessage("修改成功");
			} else {
				re.setResult("false");
				re.setResultMessage("修改失败");
			}
		} catch (Exception e) {
			logger.error("执行方法【modifyMallBanner】报错！{}", e);
			re.getErrorMessages().add(e.getMessage());
			throw new RuntimeException(e);
		}
		logger.debug("------------------轮播修改架接口，返回结果：" + JSON.toJSONString(re));
		return re;
	}


}