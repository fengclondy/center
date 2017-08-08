package cn.htd.storecenter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.htd.common.ExecuteResult;
import cn.htd.common.dao.util.RedisDB;
import cn.htd.storecenter.dto.ShopFrameDTO;
import cn.htd.storecenter.dto.ShopGridDTO;
import cn.htd.storecenter.dto.ShopLayoutDTO;
import cn.htd.storecenter.dto.ShopModuleDTO;
import cn.htd.storecenter.dto.ShopWidgetDTO;
import cn.htd.storecenter.dto.ShopWidgetSheetDTO;
import cn.htd.storecenter.service.ShopDecorationService;
import cn.htd.storecenter.service.ShopFrameBackupService;
import cn.htd.storecenter.service.ShopFrameService;
import cn.htd.storecenter.service.ShopWidgetService;

@Service("shopDecorationService")
public class ShopDecorationServiceImpl implements ShopDecorationService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 店铺frame在Redis中的名称前缀(装修时)
	private static final String REDIS_SHOP_DECORATION_FRAME_PREFIX = "B2B_MIDDLE_SHOP_DECORATION_FRAME";
	// 店铺widget在Redis中的名称前缀(装修时)
	private static final String REDIS_SHOP_DECORATION_WIDGET_PREFIX = "B2B_MIDDLE_SHOP_DECORATION_WIDGET";
	// 店铺frame在Redis中的名称前缀(发布的)
	private static final String REDIS_SHOP_PUBLISH_FRAME_PREFIX = "B2B_MIDDLE_SHOP_PUBLISH_FRAME";
	// 店铺widget在Redis中的名称前缀(发布的)
	private static final String REDIS_SHOP_PUBLISH_WIDGET_PREFIX = "B2B_MIDDLE_SHOP_PUBLISH_WIDGET";

	private static String DEFAULT_JSON_LAYOUT_HEAD = "[{\"widgetId\": \"banner\",\"mId\": \"1000\",\"mName\": \"导航\",\"mType\": \"5e\",\"mWidth\": \"5e\"}]";
	private static String DEFAULT_JSON_LAYOUT_BODY = "[{\"lType\": \"5e\",\"grids\":[{\"gType\": \"5e\",\"modules\":[{\"widgetId\": \"-1\",\"mId\": \"-1\",\"mName\": \"请拖入模块\",\"mType\": \"\",\"mWidth\": \"5e\"}]}]}]";
	private static Map<String, ShopWidgetDTO> DEFAULT_WIDGET_DATA = new HashMap<String, ShopWidgetDTO>();

	@Resource
	private RedisDB redisDB;
	@Resource
	private ShopFrameService shopFrameService;
	@Resource
	private ShopWidgetService shopWidgetService;
	@Resource
	private ShopFrameBackupService shopFrameBackupService;

	@Override
	public ShopFrameDTO findPublishFrameByShopId(long shopId) {
		// 先在缓存中查找， 没有的话去数据库查询，再没有就用默认的
		ShopFrameDTO frame = JSON.parseObject(redisDB.get(REDIS_SHOP_PUBLISH_FRAME_PREFIX + "_" + shopId),
				ShopFrameDTO.class);
		if (frame == null) {
			ShopFrameDTO searchDto = new ShopFrameDTO();
			searchDto.setShopId(shopId);
			searchDto.setVersionType(0);
			ExecuteResult<List<ShopFrameDTO>> result = shopFrameService.findFrame(searchDto);
			if (result.getResult() != null && result.getResult().size() == 1) {
				frame = result.getResult().get(0);

				// 需要把widget数据也查出来放到redis中
				ShopWidgetDTO widgetSearchDTO = new ShopWidgetDTO();
				widgetSearchDTO.setFrameId(frame.getId());
				ExecuteResult<List<ShopWidgetDTO>> widgetResult = shopWidgetService.findWidget(widgetSearchDTO);
				if (widgetResult.getResult() != null) {
					Map<String, ShopWidgetDTO> widgetMap = new HashMap<String, ShopWidgetDTO>();
					for (ShopWidgetDTO widget : widgetResult.getResult()) {
						widgetMap.put(widget.getWidgetId(), widget);
					}
					redisDB.del(REDIS_SHOP_PUBLISH_WIDGET_PREFIX + "_" + shopId);
					redisDB.set(REDIS_SHOP_PUBLISH_WIDGET_PREFIX + "_" + shopId, JSON.toJSONString(widgetMap));
				}
				return frame;
			}
			frame = new ShopFrameDTO();
			frame.setHeadModules(
					JSONObject.parseObject(DEFAULT_JSON_LAYOUT_HEAD, new TypeReference<List<ShopModuleDTO>>() {
					}));
			frame.setBodyLayouts(
					JSONObject.parseObject(DEFAULT_JSON_LAYOUT_BODY, new TypeReference<List<ShopLayoutDTO>>() {
					}));
			frame.setColor("#2eabe5");
		}
		return frame;
	}

	@Override
	public ShopFrameDTO findDecorationFrameByShopId(long shopId) {
		// 先在缓存中查找， 没有的话去数据库查询，再没有就用默认的
		ShopFrameDTO frame = JSON.parseObject(redisDB.get(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId),
				ShopFrameDTO.class);
		if (frame == null) {
			ShopFrameDTO searchDto = new ShopFrameDTO();
			searchDto.setShopId(shopId);
			searchDto.setVersionType(1);
			ExecuteResult<List<ShopFrameDTO>> result = shopFrameService.findFrame(searchDto);
			if (result.getResult() != null && result.getResult().size() == 1) {
				frame = result.getResult().get(0);
				frame.setHeadModules(
						JSONObject.parseObject(frame.getHeadModulesJson(), new TypeReference<List<ShopModuleDTO>>() {
						}));
				frame.setBodyLayouts(
						JSONObject.parseObject(frame.getBodyLayoutsJson(), new TypeReference<List<ShopLayoutDTO>>() {
						}));
				// 需要把widget数据也查出来放到redis中
				ShopWidgetDTO widgetSearchDTO = new ShopWidgetDTO();
				widgetSearchDTO.setFrameId(frame.getId());
				ExecuteResult<List<ShopWidgetDTO>> widgetResult = shopWidgetService.findWidget(widgetSearchDTO);
				if (widgetResult.getResult() != null) {
					Map<String, ShopWidgetDTO> widgetMap = new HashMap<String, ShopWidgetDTO>();
					for (ShopWidgetDTO widget : widgetResult.getResult()) {
						widgetMap.put(widget.getWidgetId(), widget);
					}
					redisDB.del(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId);
					redisDB.set(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId, JSON.toJSONString(widgetMap));
				}
				return frame;
			}
			frame = new ShopFrameDTO();
			frame.setHeadModulesJson(DEFAULT_JSON_LAYOUT_HEAD);
			frame.setBodyLayoutsJson(DEFAULT_JSON_LAYOUT_BODY);
			frame.setHeadModules(
					JSONObject.parseObject(DEFAULT_JSON_LAYOUT_HEAD, new TypeReference<List<ShopModuleDTO>>() {
					}));
			frame.setBodyLayouts(
					JSONObject.parseObject(DEFAULT_JSON_LAYOUT_BODY, new TypeReference<List<ShopLayoutDTO>>() {
					}));
			frame.setColor("#2eabe5");
		}
		return frame;
	}

	@Override
	public void updateLayout(long shopId, String headModulesJson, String bodyLayoutsJson, String footerModulesJson) {
		ShopFrameDTO frame = this.findDecorationFrameByShopId(shopId);
		frame.setHeadModulesJson(headModulesJson);
		frame.setBodyLayoutsJson(bodyLayoutsJson);
		frame.setFooterModulesJson(footerModulesJson);
		List<ShopModuleDTO> headModules = JSONObject.parseObject(headModulesJson,
				new TypeReference<List<ShopModuleDTO>>() {
				});
		List<ShopLayoutDTO> bodyLayouts = JSONObject.parseObject(bodyLayoutsJson,
				new TypeReference<List<ShopLayoutDTO>>() {
				});

		frame.setHeadModules(headModules);
		frame.setBodyLayouts(bodyLayouts);
		redisDB.set(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId, JSON.toJSONString(frame));
	}

	@Override
	public void updateColor(long shopId, String color) {
		ShopFrameDTO frame = this.findDecorationFrameByShopId(shopId);
		frame.setColor(color);
		redisDB.set(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId, JSON.toJSONString(frame));
	}

	@Override
	public void updateBackground(long shopId, String bgColor, boolean showBgColor, String bgImgUrl, String bgRepeat,
			String bgAlign) {
		ShopFrameDTO frame = this.findDecorationFrameByShopId(shopId);
		frame.setBgColor(bgColor);
		frame.setShowBgColor(showBgColor);
		frame.setBgImgUrl(bgImgUrl);
		frame.setBgRepeat(bgRepeat);
		frame.setBgAlign(bgAlign);
		redisDB.set(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId, JSON.toJSONString(frame));
	}

	@Override
	public ShopModuleDTO findModuleByWidgetId(long shopId, String widgetId, boolean isPublish) {
		ShopFrameDTO frame = null;
		if (isPublish) {
			frame = this.findPublishFrameByShopId(shopId);
		} else {
			frame = this.findDecorationFrameByShopId(shopId);
		}

		List<ShopLayoutDTO> bdLayout = frame.getBodyLayouts();
		for (ShopLayoutDTO layout : bdLayout) {
			for (ShopGridDTO grid : layout.getGrids()) {
				for (ShopModuleDTO module : grid.getModules()) {
					if (module.getWidgetId().equals(widgetId)) {
						return module;
					}
				}
			}
		}
		List<ShopModuleDTO> head = frame.getHeadModules();
		for (ShopModuleDTO module : head) {
			if (module.getWidgetId().equals(widgetId)) {
				return module;
			}
		}
		return null;
	}

	@Override
	public ShopWidgetDTO findWidget(long shopId, String widgetId, boolean isPublish) {
		ShopWidgetDTO widgetDate = null;
		Map<String, ShopWidgetDTO> widgetMap = null;
		if (isPublish) {
			widgetMap = JSON.parseObject(redisDB.get(REDIS_SHOP_PUBLISH_WIDGET_PREFIX + "_" + shopId),
					new TypeReference<HashMap<String, ShopWidgetDTO>>() {
					});
		} else {
			widgetMap = JSON.parseObject(redisDB.get(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId),
					new TypeReference<HashMap<String, ShopWidgetDTO>>() {
					});
		}
		if (widgetMap.containsKey(widgetId)) {
			widgetDate = widgetMap.get(widgetId);
		}
		if (widgetDate == null) {
			widgetDate = DEFAULT_WIDGET_DATA.get(widgetId);
			if (widgetDate == null) {
				widgetDate = new ShopWidgetDTO(widgetId, new ArrayList<ShopWidgetSheetDTO>());
			}
		}
		return widgetDate;
	}

	@Override
	public void setWidget(long shopId, String widgetId, ShopWidgetDTO widget) {
		Map<String, ShopWidgetDTO> widgetMap = null;
		widgetMap = JSON.parseObject(redisDB.get(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId),
				new TypeReference<HashMap<String, ShopWidgetDTO>>() {
				});
		if (widgetMap == null) {
			widgetMap = new HashMap<String, ShopWidgetDTO>();
		}
		widgetMap.put(widgetId, widget);
		redisDB.set(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId, JSON.toJSONString(widgetMap));
	}

	@Override
	public void delWidget(long shopId, String[] widgetIds) {
		Map<String, ShopWidgetDTO> widgetMap = null;
		widgetMap = JSON.parseObject(redisDB.get(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId),
				new TypeReference<HashMap<String, ShopWidgetDTO>>() {
				});
		if (widgetMap != null) {
			for (String id : widgetIds) {
				widgetMap.remove(id);
			}
			redisDB.set(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId, JSON.toJSONString(widgetMap));
		}
	}

	@Override
	public boolean decoration2Publish(long shopId) {
		try {
			// 先保存数据库
			this.decoration2Db(shopId, 0);

			// 更新redis
			ShopFrameDTO frame = this.findDecorationFrameByShopId(shopId);
			redisDB.set(REDIS_SHOP_PUBLISH_FRAME_PREFIX + "_" + shopId, JSON.toJSONString(frame));
			redisDB.del(REDIS_SHOP_PUBLISH_WIDGET_PREFIX + "_" + shopId);
			redisDB.set(REDIS_SHOP_PUBLISH_WIDGET_PREFIX + "_" + shopId,
					redisDB.get(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public ExecuteResult<ShopFrameDTO> decoration2Db(long shopId, int versionType) {
		ExecuteResult<ShopFrameDTO> er = new ExecuteResult<ShopFrameDTO>();
		try {
			// 如果保存为发布版或编辑版,为保证数据正确，查询出来先删
			if (versionType == 0 || versionType == 1) {
				ShopFrameDTO searchDto = new ShopFrameDTO();
				searchDto.setShopId(shopId);
				searchDto.setVersionType(versionType);
				ExecuteResult<List<ShopFrameDTO>> result = shopFrameService.findFrame(searchDto);
				if (result.getResult() != null) {
					for (ShopFrameDTO dto : result.getResult()) {
						shopFrameService.delFrameById(dto.getId());
					}
				}
			}

			ShopFrameDTO frameDTO = this.findDecorationFrameByShopId(shopId);
			frameDTO.setVersionType(versionType);
			frameDTO.setShopId(shopId);
			frameDTO = shopFrameService.addFrame(frameDTO).getResult();
			if (frameDTO != null) {
				Map<Object, Object> map = JSON.parseObject(
						redisDB.get(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId),
						new TypeReference<HashMap<Object, Object>>() {
						});
				for (Object object : map.values()) {
					ShopWidgetDTO widgetDTO = (ShopWidgetDTO) object;
					widgetDTO.setFrameId(frameDTO.getId());
					shopWidgetService.saveOrUpdateWidget(widgetDTO);
				}
				er.setResult(frameDTO);
			}

		} catch (Exception e) {
			logger.error("店铺装修信息保存数据库错误！", e);
			er.addErrorMessage(e.getMessage());
			throw new RuntimeException(e);
		}

		return er;
	}

	@Override
	public ExecuteResult<Boolean> db2decoration(long frameId, long shopId) {
		ExecuteResult<Boolean> er = new ExecuteResult<Boolean>();
		er.setResult(false);
		ExecuteResult<ShopFrameDTO> frameResult = shopFrameService.findFrameById(frameId);
		if (frameResult != null) {
			ShopFrameDTO frame = frameResult.getResult();
			frame.setShopId(shopId);
			List<ShopModuleDTO> headModules = JSONObject.parseObject(frame.getHeadModulesJson(),
					new TypeReference<List<ShopModuleDTO>>() {
					});
			List<ShopLayoutDTO> bodyLayouts = JSONObject.parseObject(frame.getBodyLayoutsJson(),
					new TypeReference<List<ShopLayoutDTO>>() {
					});
			frame.setHeadModules(headModules);
			frame.setBodyLayouts(bodyLayouts);

			redisDB.set(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId, JSON.toJSONString(frame));

			// 需要把widget数据也查出来放到redis中
			ShopWidgetDTO widgetSearchDTO = new ShopWidgetDTO();
			widgetSearchDTO.setFrameId(frame.getId());
			ExecuteResult<List<ShopWidgetDTO>> widgetResult = shopWidgetService.findWidget(widgetSearchDTO);
			if (widgetResult.getResult() != null) {
				Map<String, ShopWidgetDTO> widgetMap = new HashMap<String, ShopWidgetDTO>();
				for (ShopWidgetDTO widget : widgetResult.getResult()) {
					widgetMap.put(widget.getWidgetId(), widget);
				}
				redisDB.del(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId);
				redisDB.set(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId, JSON.toJSONString(widgetMap));
			}
			er.setResult(true);
		}
		return er;
	}

	@Override
	public void delDecoration(long shopId) {
		redisDB.del(REDIS_SHOP_DECORATION_FRAME_PREFIX + "_" + shopId);
		redisDB.del(REDIS_SHOP_DECORATION_WIDGET_PREFIX + "_" + shopId);
	}
}
