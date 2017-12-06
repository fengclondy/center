package cn.htd.goodscenter.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.common.constants.Constants;
import cn.htd.goodscenter.common.constants.ErrorCodes;
import cn.htd.goodscenter.common.utils.DTOValidateUtil;
import cn.htd.goodscenter.common.utils.ValidateResult;
import cn.htd.goodscenter.dao.CategoryAttrDAO;
import cn.htd.goodscenter.dao.ItemAttributeDAO;
import cn.htd.goodscenter.dao.ItemAttributeValueDAO;
import cn.htd.goodscenter.dao.ItemBrandDAO;
import cn.htd.goodscenter.dao.ItemCategoryDAO;
import cn.htd.goodscenter.dao.ItemMybatisDAO;
import cn.htd.goodscenter.domain.CategoryAttrImport;
import cn.htd.goodscenter.domain.ItemAttrBean;
import cn.htd.goodscenter.domain.ItemAttrSeller;
import cn.htd.goodscenter.domain.ItemAttrValueBean;
import cn.htd.goodscenter.domain.ItemAttrValueSeller;
import cn.htd.goodscenter.domain.ItemCategoryCascade;
import cn.htd.goodscenter.dto.BrandOfShopDTO;
import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import cn.htd.goodscenter.dto.CategoryAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemBrandDTO;
import cn.htd.goodscenter.dto.ItemCatCascadeDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.ItemQueryInDTO;
import cn.htd.goodscenter.dto.ItemQueryOutDTO;
import cn.htd.goodscenter.dto.constants.CategoryAttrConst;
import cn.htd.goodscenter.dto.constants.CategoryConst;
import cn.htd.goodscenter.dto.indto.QueryItemCategoryInDTO;
import cn.htd.goodscenter.service.ItemCategoryService;

/**
 * 类目接口实现
 * 
 * @author chenakng
 */
@Service("itemCategoryService")
public class ItemCategoryServiceImpl implements ItemCategoryService {
	/**
	 * 日志
	 */
	private final static Logger logger = LoggerFactory.getLogger(ItemCategoryServiceImpl.class);
	/**
	 * 类目持久化
	 */
	@Resource
	private ItemCategoryDAO itemCategoryDAO;
	/**
	 * 类目属性持久化
	 */
	@Resource
	private CategoryAttrDAO categoryAttrDAO;
	/**
	 * 属性持久化
	 */
	@Resource
	private ItemAttributeDAO itemAttributeDAO;
	/**
	 * 属性值持久化
	 */
	@Resource
	private ItemAttributeValueDAO itemAttributeValueDAO;

	@Resource
	private ItemMybatisDAO itemMybatisDAO;

	@Resource
	private ItemBrandDAO itemBrandDAO;

	/****** 1.类目 ******/
	/**
	 * 类目添加
	 * 
	 * @support 提供给前台
	 */
	@Override
	public ExecuteResult<String> addItemCategory(ItemCategoryDTO itemCategoryDTO) {
		ExecuteResult<String> result = new ExecuteResult();
		try {
			if (itemCategoryDTO == null) {
				result.getErrorMessages().add(ErrorCodes.E10000.getErrorMsg("itemCategoryDTO"));
				return result;
			}
			ValidateResult validateResult = DTOValidateUtil.validate(itemCategoryDTO);
			if (!validateResult.isPass()) {
				result.addErrorMessage(validateResult.getMessage());
				return result;
			}
			// 类目名称是否可以重复
			ItemCategoryDTO itemCategoryDTOValidate = new ItemCategoryDTO();
			itemCategoryDTOValidate.setCategoryCName(itemCategoryDTO.getCategoryCName());
			List list = this.itemCategoryDAO.queryItemCategoryAllList(itemCategoryDTOValidate, null);
			if (list.size() > 0) {
				result.addErrorMessage("类目名称重复");
				return result;
			}
			// 保存类目信息
			itemCategoryDAO.add(itemCategoryDTO);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询类目列表
	 * 
	 * @param Pager
	 *            分页信息系
	 * @support 提供给前台
	 * @return 类目列表
	 */
	@Override
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryAllList(Pager Pager) {
		ExecuteResult<DataGrid<ItemCategoryDTO>> executeResult = new ExecuteResult();
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid();
		try {
			List<ItemCategoryDTO> list = itemCategoryDAO.queryItemCategoryAllList(null, Pager);
			Long count = itemCategoryDAO.queryCount(null);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			executeResult.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			executeResult.getErrorMessages().add(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 删除类目
	 *
	 * @param itemCategoryDTO@support
	 *            提供给前台
	 * @return 删除结果
	 */
	@Override
	public ExecuteResult<String> deleteItemCategory(ItemCategoryDTO itemCategoryDTO) {
		ExecuteResult<String> result = new ExecuteResult();
		if (itemCategoryDTO == null) {
			result.addErrorMessage("itemCategoryDTO is null");
			return result;
		}
		if (itemCategoryDTO.getCategoryCid() == null || itemCategoryDTO.getCategoryCid() <= 0) {
			result.addErrorMessage("itemCategoryDTO is null");
			return result;
		}
		if (itemCategoryDTO.getCategoryModifyId() == null || itemCategoryDTO.getCategoryModifyId() <= 0) {
			result.addErrorMessage("ModifyId is empty");
			return result;
		}
		if (StringUtils.isEmpty(itemCategoryDTO.getCategoryModifyName())) {
			result.addErrorMessage("modifyName is empty");
			return result;
		}
		Long cid = itemCategoryDTO.getCategoryCid();
		try {
			ItemCategoryDTO itemCategoryDTODB = this.itemCategoryDAO.getCategoryByChildCid(cid);
			if (itemCategoryDTODB == null) {
				result.getErrorMessages().add(ErrorCodes.E10103.getErrorMsg());
				return result;
			}
			// 判断删除的类目是否存在子类目
			ExecuteResult<DataGrid<ItemCategoryDTO>> executeResult = this.queryItemCategoryList(cid);
			DataGrid<ItemCategoryDTO> childCat = null;
			if (executeResult.isSuccess()) {
				childCat = executeResult.getResult();
			}
			if (childCat != null && childCat.getRows() != null && childCat.getRows().size() > 0) {
				result.getErrorMessages().add(ErrorCodes.E10101.getErrorMsg());
				return result;
			}
			// 判断删除的是不是三级类目
			if (itemCategoryDTODB.getCategoryLev().equals(CategoryConst.CATEGORY_LEV_3)) {
				// 三级类目类目下有品牌的不能删除
				BrandOfShopDTO brandOfShopDTO = new BrandOfShopDTO();
				brandOfShopDTO.setThirdCid(cid);
				List<ItemBrandDTO> brands = this.itemBrandDAO.queryBrandListByCategoryId(brandOfShopDTO);
				if (brands != null && brands.size() > 0) {
					result.getErrorMessages().add(ErrorCodes.E10102.getErrorMsg());
					return result;
				}
				// 三级类目有商品不能删除
				ItemQueryInDTO itemInDTO = new ItemQueryInDTO();
				itemInDTO.setCid(cid);
				List<ItemQueryOutDTO> items = this.itemMybatisDAO.queryItemList(itemInDTO, null); // TODO
																									// :
																									// 商品
				if (items != null && items.size() > 0) {
					result.getErrorMessages().add(ErrorCodes.E10104.getErrorMsg());
					return result;
				}
			}
			// 开始删除类目
			ItemCategoryDTO itemCategoryDTOParam = new ItemCategoryDTO();
			itemCategoryDTOParam.setCategoryCid(cid);
			itemCategoryDTOParam.setCategoryStatus(CategoryConst.CATEGORY_STATUS_INVALID);
			itemCategoryDTOParam.setCategoryModifyId(itemCategoryDTO.getCategoryModifyId());
			itemCategoryDTOParam.setCategoryModifyName(itemCategoryDTO.getCategoryModifyName());
			this.itemCategoryDAO.updateBySelect(itemCategoryDTOParam);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			e.printStackTrace();
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 修改类目名称
	 * 
	 * @param itemCategoryDTO
	 * @support 提供给前台
	 * @return 修改结果
	 * @support
	 */
	@Override
	public ExecuteResult<String> updateCategory(ItemCategoryDTO itemCategoryDTO) {
		logger.debug("updateCategory enter, param : {}.", itemCategoryDTO);
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			// 输入参数为null
			if (itemCategoryDTO == null) {
				result.addErrorMessage(ErrorCodes.E10000.getErrorMsg("itemCategoryDTO"));
				return result;
			}
			// 校验必填参数
			if (itemCategoryDTO.getCategoryCid() == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("CategoryCid"));
				return result;
			}
			if (itemCategoryDTO.getCategoryCName() == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("CategoryCName"));
				return result;
			}
			if (itemCategoryDTO.getCategoryModifyId() == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyId"));
				return result;
			}
			if (itemCategoryDTO.getCategoryModifyName() == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("ModifyName"));
				return result;
			}
			if (!itemCategoryDTO.isFlag()) {
				// 类目名称是否可以重复
				ItemCategoryDTO itemCategoryDTOValidate = new ItemCategoryDTO();
				itemCategoryDTOValidate.setCategoryCName(itemCategoryDTO.getCategoryCName());
				List<?> list = this.itemCategoryDAO.queryItemCategoryAllList(itemCategoryDTOValidate, null);
				if (list.size() > 0) {
					result.addErrorMessage("类目名称重复");
					return result;
				}
			}
			// 校验结束，开始更新
			itemCategoryDAO.updateBySelect(itemCategoryDTO);
		} catch (Exception e) {
			logger.error("updateCategory error.", e);
			result.getErrorMessages().add(e.getMessage());
		}
		return result;
	}

	/**
	 * 根据一级目录查二级目录，根据二级目录查三级目录
	 * 
	 * @param parentCid
	 *            父级类目ID
	 * @support
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryList(Long parentCid) {
		ExecuteResult<DataGrid<ItemCategoryDTO>> executeResult = new ExecuteResult<>();
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid();
		try {
			ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
			itemCategoryDTO.setCategoryParentCid(parentCid);
			List<ItemCategoryDTO> list = itemCategoryDAO.queryItemCategoryAllList(itemCategoryDTO, null);
			Long count = itemCategoryDAO.queryCount(itemCategoryDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			executeResult.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("queryItemCategoryList error.", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 查询类目树
	 * 
	 * @return
	 */
	@Override
	public ExecuteResult<List<ItemCatCascadeDTO>> queryItemCategoryTree() {
		ExecuteResult<List<ItemCatCascadeDTO>> executeResult = new ExecuteResult();
		try {
			ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
			itemCategoryDTO.setCategoryParentCid(0L);
			List<ItemCatCascadeDTO> treeList = this.recurseQuery(itemCategoryDTO);
			executeResult.setResult(treeList);
		} catch (Exception e) {
			logger.error("queryItemCategoryTree error:", e);
		}
		return executeResult;
	}

	private List<ItemCatCascadeDTO> recurseQuery(ItemCategoryDTO itemCategoryDTO) {
		List<ItemCatCascadeDTO> treeList = new ArrayList();
		List<ItemCategoryDTO> firstCategoryList = itemCategoryDAO.queryItemCategoryAllList(itemCategoryDTO, null);
		for (ItemCategoryDTO itemCategoryDTO1 : firstCategoryList) {
			ItemCatCascadeDTO itemCatCascadeDTO1 = new ItemCatCascadeDTO();
			itemCatCascadeDTO1.setCid(itemCategoryDTO1.getCategoryCid());
			itemCatCascadeDTO1.setCname(itemCategoryDTO1.getCategoryCName());
			ItemCategoryDTO itemCategoryDTO2 = new ItemCategoryDTO();
			itemCategoryDTO2.setCategoryParentCid(itemCategoryDTO1.getCategoryCid());
			itemCatCascadeDTO1.setChildCats(this.recurseQuery(itemCategoryDTO2));
			treeList.add(itemCatCascadeDTO1);
		}
		return treeList;
	}

	public ExecuteResult<List<ItemCatCascadeDTO>> queryItemCategoryTreeByThirdCid(List<Long> thirdCidList) {
		ExecuteResult<List<ItemCatCascadeDTO>> executeResult = new ExecuteResult<>();
		try {
			if (thirdCidList == null || thirdCidList.size() == 0) {
				executeResult.setResult(new ArrayList<ItemCatCascadeDTO>());
				return executeResult;
			}
			executeResult = this.queryParentCategoryList(3, thirdCidList.toArray(new Long[thirdCidList.size()]));
		} catch (Exception e) {
			logger.error("根据三级类目查询类目树出错, 错误信息 ：", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 根据三级类目查询一级二级三级类目，按照分隔符组装 例：一级类目>>二级类目>>三级类目
	 * 
	 * @param cid
	 * @param separator
	 * @return
	 */
	@Override
	public ExecuteResult<Map<String, Object>> queryItemOneTwoThreeCategoryName(Long cid, String separator) {
		ExecuteResult<Map<String, Object>> executeResult = new ExecuteResult<>();
		Map<String, Object> map = new HashMap();
		try {
			// 查询父级类目
			ExecuteResult<List<ItemCatCascadeDTO>> executeResultCatCascade = this.queryParentCategoryList(cid);
			String result;
			String categoryOne = " ";
			Long firstCategoryId = null;
			String categoryTwo = " ";
			Long secondCategoryId = null;
			String categoryThree = " ";
			Long thirdCategoryId = null;
			if (executeResultCatCascade.isSuccess()) {
				List<ItemCatCascadeDTO> itemCatCascadeDTOs = executeResultCatCascade.getResult();
				// 三级类目只能查到一条父级的记录
				if (itemCatCascadeDTOs != null && itemCatCascadeDTOs.size() > 0) {
					// 一级
					ItemCatCascadeDTO itemCatCascadeDTO = itemCatCascadeDTOs.get(0);
					categoryOne = itemCatCascadeDTO.getCname();
					firstCategoryId = itemCatCascadeDTO.getCid();
					// 二级
					List<ItemCatCascadeDTO> itemCatCascadeDTOTwos = itemCatCascadeDTO.getChildCats();
					if (itemCatCascadeDTOTwos.size() > 0) {
						ItemCatCascadeDTO itemCatCascadeDTOTwo = itemCatCascadeDTOTwos.get(0);
						categoryTwo = itemCatCascadeDTOTwo.getCname();
						secondCategoryId = itemCatCascadeDTOTwo.getCid();
						List<ItemCatCascadeDTO> itemCatCascadeDTOThrees = itemCatCascadeDTOTwo.getChildCats();
						if (itemCatCascadeDTOThrees.size() > 0) {
							ItemCatCascadeDTO itemCatCascadeDTOThree = itemCatCascadeDTOThrees.get(0);
							categoryThree = itemCatCascadeDTOThree.getCname();
							thirdCategoryId = itemCatCascadeDTOThree.getCid();
						}
					}
				}
			}
			result = categoryOne + separator + categoryTwo + separator + categoryThree;
			map.put("firstCategoryId", firstCategoryId);
			map.put("firstCategoryName", categoryOne);
			map.put("secondCategoryId", secondCategoryId);
			map.put("secondCategoryName", categoryTwo);
			map.put("thirdCategoryId", thirdCategoryId);
			map.put("thirdCategoryName", categoryThree);
			map.put("categoryName", result);
			executeResult.setResult(map);
		} catch (Exception e) {
			logger.error("Error,", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * 查询所有的三级类目，根据一级、二级或者三级其中一个
	 * 
	 * @param firstCategoryId
	 * @param secondCategoryId
	 * @param thirdCategoryId
	 * @return
	 */
	public Long[] getAllThirdCategoryByCategoryId(Long firstCategoryId, Long secondCategoryId, Long thirdCategoryId) {
		// 根据一级，二级，三级类目查询所有的三级类目
		// 如果是以映射，需要解析类目条件
		Long[] categoryParam = null;
		if (thirdCategoryId != null && thirdCategoryId > 0) { // 前台传入了三级类目
			categoryParam = new Long[] { thirdCategoryId };
		} else { // 否则需要根据父级类目查询所有的三级
			Long parentId = 0l;
			if (secondCategoryId != null && secondCategoryId > 0) { // 前台传入了二级类目
				parentId = secondCategoryId;
			} else if (firstCategoryId != null && firstCategoryId > 0) {// 前台传入了三级类目
				parentId = firstCategoryId;
			}
			if (parentId > 0) {
				ExecuteResult<List<ItemCategoryDTO>> executeResultThirdCategoryList = this
						.queryThirdCategoryByParentId(parentId);
				if (executeResultThirdCategoryList.isSuccess()) {
					List<ItemCategoryDTO> itemCategoryDTOList = executeResultThirdCategoryList.getResult();
					if (itemCategoryDTOList.size() > 0) {
						categoryParam = new Long[itemCategoryDTOList.size()];
						int i = 0;
						for (ItemCategoryDTO categoryDTO : itemCategoryDTOList) {
							categoryParam[i++] = categoryDTO.getCategoryCid();
						}
					} else {
						categoryParam = new Long[] { -1L };
					}
				} else {
					throw new RuntimeException("queryThirdCategoryByParentId error.");
				}
			}
		}
		return categoryParam;
	}

	@Override
	public ExecuteResult<List<ItemCategoryDTO>> queryThirdCategoryByParentId(Long parentCid) {
		ExecuteResult<List<ItemCategoryDTO>> executeResult = new ExecuteResult();
		List<ItemCategoryDTO> thirdCategoryList = new ArrayList();
		try {
			ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
			itemCategoryDTO.setCategoryParentCid(parentCid);
			List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryDAO.queryItemCategoryAllList(itemCategoryDTO, null);
			for (ItemCategoryDTO itemCategoryDTODb : itemCategoryDTOList) {
				if (itemCategoryDTODb.getCategoryLev() == CategoryConst.CATEGORY_LEV_3) { // 参数是2级查询3级的
					// 根据2级查到了所有的3级
					thirdCategoryList.add(itemCategoryDTODb);
				} else if (itemCategoryDTODb.getCategoryLev() == CategoryConst.CATEGORY_LEV_2) { // 参数是1级查询3级的，还需要从2级往下查
					ItemCategoryDTO itemCategoryDTOSecond = new ItemCategoryDTO();
					itemCategoryDTOSecond.setCategoryParentCid(itemCategoryDTODb.getCategoryCid());
					List<ItemCategoryDTO> itemCategoryDTOThirdList = itemCategoryDAO
							.queryItemCategoryAllList(itemCategoryDTOSecond, null);
					thirdCategoryList.addAll(itemCategoryDTOThirdList);
				}
			}
			executeResult.setResult(thirdCategoryList);
		} catch (Exception e) {
			logger.error("queryThirdCategoryByParentId error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	/**
	 * @Deprecated
	 * @param categoryLev
	 * @return
	 */
	@Override
	public DataGrid<ItemCategoryDTO> queryItemByCategoryLev(Integer categoryLev) {
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid<ItemCategoryDTO>();
		try {
			List<ItemCategoryDTO> list = itemCategoryDAO.queryItemByCategoryLev(categoryLev);
			dataGrid.setRows(list);
			dataGrid.setTotal(Long.decode((null != list ? list.size() : 0) + ""));
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			throw new RuntimeException(e);
		}
		return dataGrid;

	}

	/****** 2.属性 ******/
	/**
	 * 添加类目属性
	 * 
	 * @param cid
	 *            类目id
	 * @param attrName
	 *            属性名称
	 * @param attrType
	 *            属性类型:1:销售属性;2:非销售属性
	 * @param sortNumber
	 *            排序号
	 * @param createId
	 * @param createName
	 * @support
	 * @return
	 */
	@Transactional
	@Override
	public ExecuteResult<Long> addCategoryAttr(Long cid, String attrName, Integer attrType, Integer sortNumber,
			Long createId, String createName) {
		ExecuteResult<Long> result = new ExecuteResult();
		CategoryAttrDTO categoryAttrDTO = new CategoryAttrDTO();
		categoryAttrDTO.setAttrCreateId(createId);
		categoryAttrDTO.setAttrCreateName(createName);
		try {
			// 校验
			CategoryAttrDTO dto = new CategoryAttrDTO();
			dto.setAttrCid(cid);
			dto.setAttrAttrType(attrType);
			dto.setAttrAttrName(attrName);
			List<CategoryAttrDTO> categoryAttrDTOList = categoryAttrDAO.queryAttrNameList(dto);
			if (categoryAttrDTOList.size() > 0) {
				result.addErrorMessage("该类目下属性名称已经存在");
				result.setResult(categoryAttrDTOList.get(0).getAttrAttrId());
				result.setCode(ErrorCodes.E10207.name());
				return result;
			}
			// 属性表添加
			categoryAttrDTO.setAttrAttrName(attrName); // 属性名称
			categoryAttrDAO.addAttr(categoryAttrDTO);
			// 类目属性表添加
			categoryAttrDTO.setAttrSortNumber(sortNumber);
			categoryAttrDTO.setAttrCid(cid); // 品类ID
			categoryAttrDTO.setAttrAttrType(attrType); // 品类属性类型
			categoryAttrDAO.add(categoryAttrDTO);
			result.setResult(categoryAttrDTO.getAttrAttrId());
			result.setResultMessage("success");
			// 【搜索引擎需要】：更新类目更新时间
			this.updateCategoryModifyTime(cid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	/**
	 * 更新类目修改时间
	 * 
	 * @param cid
	 */
	private void updateCategoryModifyTime(Long cid) {
		logger.info("【搜索引擎需要】操作属性时更新类目更新时间, cid : {}", cid);
		try {
			ItemCategoryDTO itemCategory = new ItemCategoryDTO();
			itemCategory.setCategoryCid(cid);
			itemCategory.setCategoryModifyId(Constants.SYSTEM_CREATE_ID);
			itemCategory.setCategoryModifyName(Constants.SYSTEM_CREATE_NAME);
			this.itemCategoryDAO.updateBySelect(itemCategory);
		} catch (Exception e) {
			logger.error("【搜索引擎需要】操作属性时更新类目更新时间出错:", e);
		}
	}

	/**
	 * 添加类目属性值
	 *
	 * @param cid
	 *            类目ID
	 * @param attrId
	 *            属性ID
	 * @param valueName
	 *            属性值名称
	 * @param sortNumber
	 *            排序号
	 * @support
	 * @return
	 */
	@Override
	public ExecuteResult<Long> addCategoryAttrValue(Long cid, Long attrId, String valueName, Integer sortNumber,
			Long createId, String createName) {
		ExecuteResult<Long> result = new ExecuteResult();
		CategoryAttrDTO categoryAttrDTO = new CategoryAttrDTO();
		categoryAttrDTO.setAttrCreateId(createId);
		categoryAttrDTO.setAttrCreateName(createName);
		try {
			// 校验
			CategoryAttrDTO categoryAttrDTOParam = new CategoryAttrDTO();
			categoryAttrDTOParam.setAttrAttrId(attrId);
			categoryAttrDTOParam.setAttrCid(cid);
			categoryAttrDTOParam.setAttrValueName(valueName);
			List<CategoryAttrDTO> existList = this.categoryAttrDAO.queryValueNameList(categoryAttrDTOParam);
			if (existList != null && existList.size() > 0) {
				result.addErrorMessage("该属性下属性值名已经存在");
				result.setCode(ErrorCodes.E10208.name());
				return result;
			}
			// 添加属性值
			categoryAttrDTO.setAttrValueName(valueName);
			categoryAttrDTO.setAttrAttrId(attrId);
			categoryAttrDTO.setAttrIndexKey(" ");
			categoryAttrDAO.addAttrValue(categoryAttrDTO);
			// 添加类目属性值
			categoryAttrDTO.setAttrCid(cid);
			categoryAttrDTO.setAttrAttrId(attrId);
			categoryAttrDTO.setAttrSortNumber(sortNumber);
			categoryAttrDAO.addCategoryAttrValue(categoryAttrDTO);
			result.setResult(categoryAttrDTO.getAttrValueId());
			// 【搜索引擎需求】 添加类目属性值时，更新类目更新时间
			this.updateCategoryModifyTime(cid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 删除类目属性
	 * 
	 * @param cid
	 *            类目id
	 * @param attr_id
	 *            属性id
	 * @param attrType
	 *            属性类别
	 * @return
	 */
	@Override
	public ExecuteResult<String> deleteCategoryAttr(Long cid, Long attr_id, Integer attrType) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (cid == null) {
				result.getErrorMessages().add(ErrorCodes.E10001.getErrorMsg("类目ID"));
				return result;
			}
			if (attr_id == null) {
				result.getErrorMessages().add(ErrorCodes.E10001.getErrorMsg("属性ID"));
				return result;
			}
			if (attrType != CategoryAttrConst.ATTR_TYPE_CATE && attrType != CategoryAttrConst.ATTR_TYPE_SALE) {
				result.getErrorMessages().add(ErrorCodes.E10005.getErrorMsg("属性类型"));
				return result;
			}
			// 删除品类属性值表 该属性下的所有属性值
			categoryAttrDAO.deleteCategoryAttrValueByAttrId(attr_id);
			// 删除品类属性
			categoryAttrDAO.deleteCategoryAttrByAttrId(attr_id);
			// 删除属性值
			itemAttributeValueDAO.deleteByAttrId(attr_id);
			// 删除属性
			itemAttributeDAO.delete(attr_id);
			// 【搜索引擎需要】删除属性时更新类目修改时间
			this.updateCategoryModifyTime(cid);
		} catch (Exception e) {
			logger.error("deleteCategoryAttr error::", e);
			result.getErrorMessages().add(e.getMessage());
		}
		return result;
	}

	/**
	 * 删除类目属性值
	 * 
	 * @param cid
	 *            类目id
	 * @param attr_id
	 *            属性id
	 * @param value_id
	 *            属性值id
	 * @param attrType
	 *            属性类别
	 * @return
	 */
	@Override
	public ExecuteResult<String> deleteCategoryAttrValue(Long cid, Long attr_id, Long value_id, Integer attrType) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			if (cid == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("类目ID"));
				return result;
			}
			if (attr_id == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("属性ID"));
				return result;
			}
			if (value_id == null) {
				result.addErrorMessage(ErrorCodes.E10001.getErrorMsg("属性值ID"));
				return result;
			}
			if (attrType != CategoryAttrConst.ATTR_TYPE_CATE && attrType != CategoryAttrConst.ATTR_TYPE_SALE) {
				result.addErrorMessage(ErrorCodes.E10005.getErrorMsg("属性类型"));
				return result;
			}
			// 删除品类属性值
			categoryAttrDAO.deleteCategoryAttrValueByValueId(value_id);
			// 删除属性值
			itemAttributeValueDAO.delete(value_id);
			// 【搜索引擎需要】删除属性时更新类目修改时间
			this.updateCategoryModifyTime(cid);
		} catch (Exception e) {
			logger.error("deleteCategoryAttr error::", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 查询类目属性-属性值列表
	 * 
	 * @param cid
	 * @param attrType
	 *            属性类型:1:销售属性;2:非销售属性
	 * @support
	 * @return
	 */
	@Override
	public ExecuteResult<DataGrid<CategoryAttrDTO>> queryCategoryAttrList(Long cid, Integer attrType) {
		ExecuteResult<DataGrid<CategoryAttrDTO>> executeResult = new ExecuteResult();
		DataGrid<CategoryAttrDTO> dataGrid = new DataGrid();
		try {
			CategoryAttrDTO dto = new CategoryAttrDTO(); // 定义商品类别属性关系类
			List<CategoryAttrDTO> categoryAttrDTOs = null;
			List<CategoryAttrDTO> attrDTOs = new ArrayList<CategoryAttrDTO>();// 属性id及属性值得对象列表
			// 很据类目id和商品类型查询属性id和属性值
			dto.setAttrCid(cid);
			dto.setAttrAttrType(attrType);
			categoryAttrDTOs = categoryAttrDAO.queryAttrNameList(dto);
			if (null == categoryAttrDTOs) {
				categoryAttrDTOs = new ArrayList<CategoryAttrDTO>();// 属性id及属性值得对象列表
			}
			// 遍历属性值列表将每一对值存入DTO列表字段中
			for (int i = 0; null != categoryAttrDTOs && i < categoryAttrDTOs.size(); i++) {
				CategoryAttrDTO attrDTO = categoryAttrDTOs.get(i);
				attrDTO.setAttrCid(cid);
				attrDTO.setValueList(categoryAttrDAO.queryValueNameList(attrDTO));
				attrDTOs.add(attrDTO);
			}
			dataGrid.setRows(attrDTOs);
			dataGrid.setTotal(Long.valueOf(categoryAttrDTOs.size()));
			executeResult.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<List<ItemCatCascadeDTO>> queryParentCategoryList(Integer lev, Long... cids) {
		ExecuteResult<List<ItemCatCascadeDTO>> result = new ExecuteResult<List<ItemCatCascadeDTO>>();
		if (cids == null || cids.length <= 0) {
			result.setResult(new ArrayList<ItemCatCascadeDTO>());
			return result;
		}
		// 查询数据库中的一二三级类目
		List<ItemCategoryCascade> dataCats = this.itemCategoryDAO.queryParentCats(lev, cids);
		// 封装成返回对象
		List<ItemCatCascadeDTO> cats = this.getItemCateCascadeDTO(dataCats);
		result.setResult(cats);
		return result;
	}

	/**
	 * <p>
	 * Discription:[只根据三级类目查询一级类目]
	 * </p>
	 * 
	 * @param cids
	 * @return
	 */
	@Override
	public ExecuteResult<List<ItemCatCascadeDTO>> queryParentCategoryList(Long... cids) {
		return queryParentCategoryList(3, cids);
	}

	@Override
	public ExecuteResult<ItemAttrDTO> addItemAttrSeller(CatAttrSellerDTO inDTO) {
		ExecuteResult<ItemAttrDTO> result = new ExecuteResult<ItemAttrDTO>();
		try {
			ItemAttrDTO attr = inDTO.getAttr();
			attr.setCreateId(inDTO.getCreateId());
			attr.setCreateName(inDTO.getCreateName());
			attr.setModifyId(inDTO.getModifyId());
			attr.setModifyName(inDTO.getModifyName());
			// 添加属性表
			ItemAttrBean attrBean = this.addItemAttr(attr);
			attr.setId(attrBean.getId());
			// 添加 卖家类目和属性的关系表
			ItemAttrSeller attrSeller = new ItemAttrSeller();
			attrSeller.setAttrId(attrBean.getId());
			attrSeller.setAttrName(attrBean.getName());
			attrSeller.setAttrType(inDTO.getAttrType());
			attrSeller.setCategoryId(inDTO.getCid());
			attrSeller.setSellerId(inDTO.getSellerId());
			attrSeller.setShopId(inDTO.getShopId());
			attrSeller.setSelectType(inDTO.getSelectType());
			attrSeller.setCreateId(inDTO.getCreateId());
			attrSeller.setCreateName(inDTO.getCreateName());
			attrSeller.setModifyId(inDTO.getModifyId());
			attrSeller.setModifyName(inDTO.getModifyName());
			attrSeller.setSortNumber(inDTO.getSortNumber());
			this.insertItemAttrSeller(attrSeller);
			result.setResult(attr);
		} catch (Exception e) {
			logger.error("addItemAttrSeller error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<List<ItemAttrDTO>> queryCatAttrSellerList(CatAttrSellerDTO inDTO) {
		ExecuteResult<List<ItemAttrDTO>> result = new ExecuteResult<List<ItemAttrDTO>>();
		try {
			if (this.checkNull(inDTO)) {
				result.addErrorMessage("参数为空！");
				return result;
			}
			List<ItemAttrDTO> attrs = new ArrayList<ItemAttrDTO>();
			// 查询商家属性
			List<ItemAttrSeller> attrRels = this.categoryAttrDAO.queryAttrSellerList(inDTO);
			// 循环属性 查询关联的属性值
			ItemAttrDTO attr = null;
			for (ItemAttrSeller attrSeller : attrRels) {
				attr = new ItemAttrDTO();
				attr.setId(attrSeller.getAttrId());
				attr.setName(attrSeller.getAttrName());
				// 查询对应属性值查询
				this.setAttrValues(attr, attrSeller);
				attrs.add(attr);
			}
			result.setResult(attrs);
		} catch (Exception e) {
			logger.error("queryCatAttrSellerList error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemAttrValueDTO> addItemAttrValueSeller(CatAttrSellerDTO inDTO) {
		ExecuteResult<ItemAttrValueDTO> result = new ExecuteResult<ItemAttrValueDTO>();
		try {
			ItemAttrValueDTO attrValue = inDTO.getAttrValue();
			ItemAttrSeller attrSeller = this.categoryAttrDAO.getItemAttrSeller(inDTO.getSellerId(), inDTO.getShopId(),
					inDTO.getCid(), attrValue.getAttrId());
			if (attrSeller == null) {
				result.addErrorMessage("该类目没有属性：属性ID：" + attrValue.getAttrId());
				return result;
			}
			// 添加属性值表
			attrValue.setCreateId(inDTO.getCreateId());
			attrValue.setCreateName(inDTO.getCreateName());
			attrValue.setModifyId(inDTO.getModifyId());
			attrValue.setModifyName(inDTO.getModifyName());
			ItemAttrValueBean attrValBean = this.insertItemAttrValSeller(attrValue);
			// 添加商家属性值关联表
			ItemAttrValueSeller attrValSeller = new ItemAttrValueSeller();
			attrValSeller.setSellerAttrId(attrSeller.getSellerAttrId());
			attrValSeller.setValueId(attrValBean.getId());
			attrValSeller.setSortNumber(inDTO.getSortNumber());
			attrValSeller.setCreateId(inDTO.getCreateId());
			attrValSeller.setCreateName(inDTO.getCreateName());
			attrValSeller.setModifyId(inDTO.getModifyId());
			attrValSeller.setModifyName(inDTO.getModifyName());
			this.categoryAttrDAO.insertItemAttrValueSeller(attrValSeller);
			attrValue.setId(attrValBean.getId());
			result.setResult(attrValue);
		} catch (Exception e) {
			logger.error("addItemAttrValueSeller error:", e);
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[拼装]
	 * </p>
	 * 
	 * @param dataCats
	 * @return
	 */
	private List<ItemCatCascadeDTO> getItemCateCascadeDTO(List<ItemCategoryCascade> dataCats) {
		List<ItemCatCascadeDTO> result = new ArrayList<ItemCatCascadeDTO>();
		ItemCatCascadeDTO firstDTO = null;
		ItemCatCascadeDTO secdDTO = null;
		ItemCatCascadeDTO thirdDTO = null;
		Map<ItemCatCascadeDTO, Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>> reMap = new HashMap<ItemCatCascadeDTO, Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>>();
		for (ItemCategoryCascade cat : dataCats) {
			firstDTO = new ItemCatCascadeDTO();
			secdDTO = new ItemCatCascadeDTO();
			thirdDTO = new ItemCatCascadeDTO();
			firstDTO.setCid(cat.getCid());
			firstDTO.setCname(cat.getCname());
			secdDTO.setCid(cat.getSecondCatId());
			secdDTO.setCname(cat.getSecondCatName());
			thirdDTO.setCid(cat.getThirdCatId());
			thirdDTO.setCname(cat.getThirdCatName());
			if (!reMap.containsKey(firstDTO)) {
				reMap.put(firstDTO, new HashMap<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>());
			}
			this.addSecondCat(reMap.get(firstDTO), secdDTO, thirdDTO);
		}
		result = this.getItemCatList(reMap);
		return result;
	}

	/**
	 * <p>
	 * Discription:[从map里解析类目层级对象]
	 * </p>
	 * 
	 * @param reMap
	 * @return
	 */
	private List<ItemCatCascadeDTO> getItemCatList(
			Map<ItemCatCascadeDTO, Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>> reMap) {
		List<ItemCatCascadeDTO> result = new ArrayList<ItemCatCascadeDTO>();
		Iterator<Entry<ItemCatCascadeDTO, Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>>> ite = reMap.entrySet()
				.iterator();
		ItemCatCascadeDTO dto = null;
		while (ite.hasNext()) {
			Entry<ItemCatCascadeDTO, Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>> kv = ite.next();
			dto = kv.getKey();
			dto.setChildCats(this.getSecondCatsFromMap(kv.getValue()));
			result.add(dto);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[从map里解析二级类目层级对象]
	 * </p>
	 * 
	 * @param value
	 */
	private List<ItemCatCascadeDTO> getSecondCatsFromMap(Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>> value) {
		List<ItemCatCascadeDTO> result = new ArrayList<ItemCatCascadeDTO>();
		Iterator<Entry<ItemCatCascadeDTO, List<ItemCatCascadeDTO>>> ite = value.entrySet().iterator();
		ItemCatCascadeDTO dto = null;
		while (ite.hasNext()) {
			Entry<ItemCatCascadeDTO, List<ItemCatCascadeDTO>> kv = ite.next();
			dto = kv.getKey();
			dto.setChildCats(kv.getValue());
			result.add(dto);
		}
		return result;
	}

	/**
	 * <p>
	 * Discription:[拼接二三级类目]
	 * </p>
	 * 
	 * @param map
	 * @param secdDTO
	 * @param thirdDTO
	 */
	private void addSecondCat(Map<ItemCatCascadeDTO, List<ItemCatCascadeDTO>> map, ItemCatCascadeDTO secdDTO,
			ItemCatCascadeDTO thirdDTO) {
		List<ItemCatCascadeDTO> list = new ArrayList<ItemCatCascadeDTO>();
		if (!map.containsKey(secdDTO)) {
			list.add(thirdDTO);
			map.put(secdDTO, list);
		} else {
			if (!map.get(secdDTO).contains(thirdDTO)) {
				map.get(secdDTO).add(thirdDTO);
			}
		}

	}

	/**
	 * <p>
	 * Discription:[向商品属性对象中添加属性值]
	 * </p>
	 * 
	 * @param attr
	 * @param attrSeller
	 */
	private void setAttrValues(ItemAttrDTO attr, ItemAttrSeller attrSeller) {
		List<ItemAttrValueSeller> vs = this.categoryAttrDAO.queryAttrValueSellerList(attrSeller.getSellerAttrId());
		List<ItemAttrValueDTO> list = new ArrayList<ItemAttrValueDTO>();
		ItemAttrValueDTO iav = null;
		for (ItemAttrValueSeller av : vs) {
			iav = new ItemAttrValueDTO();
			iav.setId(av.getValueId());
			iav.setName(av.getValueName());
			list.add(iav);
		}
		attr.setValues(list);
	}

	/**
	 * <p>
	 * Discription:[验证空值]
	 * </p>
	 * 
	 * @param inDTO
	 * @return
	 */
	private boolean checkNull(CatAttrSellerDTO inDTO) {
		if (inDTO == null) {
			return true;
		}
		if (inDTO.getAttrType() == null) {
			return true;
		}
		if (inDTO.getCid() == null) {
			return true;
		}
		if (inDTO.getSellerId() == null) {
			return true;
		}
		if (inDTO.getShopId() == null) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Discription:[向数据库中插入item_attr商家属性关联表]
	 * </p>
	 * 
	 * @param attrSeller
	 * @return
	 */
	private ItemAttrSeller insertItemAttrSeller(ItemAttrSeller attrSeller) {
		this.categoryAttrDAO.insertItemAttrSeller(attrSeller);
		return attrSeller;
	}

	/**
	 * <p>
	 * Discription:[添加商品属性,插入单表数据]
	 * </p>
	 * 
	 * @param attr
	 * @return
	 */
	private ItemAttrBean addItemAttr(ItemAttrDTO attr) {
		ItemAttrBean bean = new ItemAttrBean();
		bean.setName(attr.getName());
		bean.setCreateId(attr.getCreateId());
		bean.setCreateName(attr.getCreateName());
		bean.setModifyId(attr.getModifyId());
		bean.setModifyName(attr.getModifyName());
		this.categoryAttrDAO.insertItemAttr(bean);
		return bean;
	}

	/**
	 * <p>
	 * Discription:[向数据库中插入item_attribute_value商品属性值]
	 * </p>
	 * 
	 * @param attrValue
	 * @return
	 */
	private ItemAttrValueBean insertItemAttrValSeller(ItemAttrValueDTO attrValue) {
		ItemAttrValueBean bean = new ItemAttrValueBean();
		bean.setAttrId(attrValue.getAttrId());
		bean.setName(attrValue.getName());
		bean.setIndexKey(attrValue.getIndexKey());
		bean.setCreateName(attrValue.getCreateName());
		bean.setCreateId(attrValue.getCreateId());
		bean.setModifyId(attrValue.getModifyId());
		bean.setModifyName(attrValue.getModifyName());
		this.categoryAttrDAO.insertItemAttrValue(bean);
		return bean;
	}

	@Override
	public ExecuteResult<List<ItemAttrDTO>> queryCatAttrByKeyVals(String attr) {
		ExecuteResult<List<ItemAttrDTO>> result = new ExecuteResult<List<ItemAttrDTO>>();
		List<ItemAttrDTO> resultList = new ArrayList<ItemAttrDTO>();
		List<ItemAttrDTO> newResultList = new ArrayList<ItemAttrDTO>();
		try {
			List<String> attrIds = new ArrayList<String>(); // 属性
			List<String> attrValIds = new ArrayList<String>();// 属性值
			//
			if (!StringUtils.isBlank(attr)) {
				String[] keyVals = attr.split(";");
				String[] strs = null;
				for (String str : keyVals) {
					strs = str.split(",");
					for (String keyVal : strs) {
						String[] kvs = keyVal.split(":");
						if (!attrIds.contains(kvs[0])) {
							attrIds.add(kvs[0]);
						}
						if (!attrValIds.contains(kvs[1])) {
							attrValIds.add(kvs[1]);
						}
					}

				}
			}
			if (attrIds.isEmpty()) {
				return result;
			}
			// 查询所有属性对象
			resultList = this.itemCategoryDAO.queryItemAttrList(attrIds);
			// 查询属性值对象
			for (ItemAttrDTO itemAttr : resultList) {
				ItemAttrDTO itemAttrModel = new ItemAttrDTO();
				itemAttrModel.setId(itemAttr.getId());
				itemAttrModel.setName(itemAttr.getName());
				List<ItemAttrValueDTO> valueList = this.itemCategoryDAO.queryItemAttrValueList(itemAttr.getId(),
						attrValIds);
				itemAttrModel.setValues(valueList);
				itemAttr.setValues(valueList);
				newResultList.add(itemAttrModel);
			}
			result.setResult(newResultList);
		} catch (Exception e) {
			logger.error("getItemAttrList error:" + e.getMessage());
			result.addErrorMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ExecuteResult<ItemCategoryDTO> getCategoryByCid(Long cid) {
		ExecuteResult<ItemCategoryDTO> executeResult = new ExecuteResult();
		try {
			if (cid == null || cid <= 0) {
				executeResult.addErrorMessage("cid is empty");
				return executeResult;
			}
			ItemCategoryDTO itemCategoryDTO = itemCategoryDAO.getCategoryByChildCid(cid);
			if (itemCategoryDTO == null) {
				executeResult.addErrorMessage("itemCategory is null");
				return executeResult;
			}
			executeResult.setResult(itemCategoryDTO);
		} catch (Exception e) {
			logger.error("getCategoryByChildCid error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public ExecuteResult<List<ItemCategoryDTO>> getCategoryListByCids(List<Long> cidList) {
		ExecuteResult<List<ItemCategoryDTO>> executeResult = new ExecuteResult();
		try {
			List<ItemCategoryDTO> itemCategoryDTOList = new ArrayList<>();
			if (cidList != null && cidList.size() > 0) {
				itemCategoryDTOList = itemCategoryDAO.getCategoryListByCids(cidList);
			}
			executeResult.setResult(itemCategoryDTOList);
		} catch (Exception e) {
			logger.error("getCategoryListByCids error:", e);
			executeResult.addErrorMessage(e.getMessage());
		}
		return executeResult;
	}

	@Override
	public List<ItemDTO> getItemByCid(ItemDTO itemDTO) {
		return itemMybatisDAO.getItemByCid(itemDTO);
	}

	/**
	 * 根据属性id查询属性名
	 */
	@Override
	public String getAttrNameByAttrId(Long id) {
		return categoryAttrDAO.getAttrNameByAttrId(id);
	}

	/**
	 * 根据属性值id查询属性名
	 */
	@Override
	public String getAttrValueNameByAttrValueId(Long id) {
		return categoryAttrDAO.getAttrValueNameByAttrValueId(id);
	}

	@Override
	public List<ItemCategoryCascade> queryCategoryBySyncTime(Date syncTime) {
		return itemCategoryDAO.queryCategoryBySyncTime(syncTime);
	}

	@Override
	public List<ItemAttrDTO> queryItemAttrList(List<String> attrIds) {
		return itemCategoryDAO.queryItemAttrList(attrIds);
	}

	@Override
	public List<ItemAttrValueDTO> queryItemAttrValueList(Long key, List<String> value) {
		return this.itemCategoryDAO.queryItemAttrValueList(key, value);
	}

	@Override
	public void importCategoryAttribute() {
		List<CategoryAttrImport> allList = new ArrayList();
		List<CategoryAttrImport> failueList = new ArrayList<>();
		InputStream is = null;
		try {
			is = new FileInputStream("/home/htd/goodscenter-service-1.0.0-SNAPSHOT-package/conf/类目属性初始化.xls");
			XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						CategoryAttrImport categoryAttrImport = new CategoryAttrImport();
						String categoryName = hssfRow.getCell(2).getStringCellValue();
						String attributeName = hssfRow.getCell(3).getStringCellValue();
						if (StringUtils.isEmpty(attributeName) || attributeName.contains("品牌")) {
							continue;
						}
						List<String> attributeValueNameList = new ArrayList<>();
						for (int colum = 4; colum <= hssfRow.getPhysicalNumberOfCells(); colum++) {
							if (hssfRow.getCell(colum) == null) {
								continue;
							}
							if (StringUtils.isEmpty(getStringCellValue(hssfRow.getCell(colum)))) {
								continue;
							}
							attributeValueNameList.add(getStringCellValue(hssfRow.getCell(colum)));
						}
						categoryAttrImport.setCategoryName(categoryName);
						categoryAttrImport.setAttrebuteName(attributeName);
						categoryAttrImport.setAttrebuteValueList(attributeValueNameList);
						logger.info("excel add data : {}", categoryAttrImport);
						allList.add(categoryAttrImport);
					}
				}
			}
		} catch (Exception e) {
			logger.error("importCategoryAttribute error", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("close error;");
				}
			}
		}
		for (CategoryAttrImport categoryAttrImport : allList) {
			try {
				Long categoryId = this.itemCategoryDAO.queryThirdCategoryIdByName(categoryAttrImport.getCategoryName());
				if (categoryId == null) {
					logger.error("导入数据失败,根据类目查询不到类目ID, CNAME : {}", categoryAttrImport.getCategoryName());
					failueList.add(categoryAttrImport);
					continue;
				}
				// 添加属性
				String attrebuteName = StringUtils.removeEnd(categoryAttrImport.getAttrebuteName(), "：");
				ExecuteResult<Long> executeResult = this.addCategoryAttr(categoryId, attrebuteName, 2, 0, 0L, "批量导入");
				if (executeResult.isSuccess() || executeResult.getCode().equals(ErrorCodes.E10207.name())) {
					Long attriId = executeResult.getResult();
					for (String vlaue : categoryAttrImport.getAttrebuteValueList()) {
						ExecuteResult<Long> executeResultValue = this.addCategoryAttrValue(categoryId, attriId, vlaue,
								0, 0L, "批量导入");
						if (!executeResultValue.isSuccess()
								&& !executeResultValue.getCode().equals(ErrorCodes.E10208.name())) {
							logger.error("导入属性值失败； 类目：{}， 属性：{}, 属性值：{}", categoryAttrImport.getCategoryName(),
									categoryAttrImport.getAttrebuteName(), vlaue);
							failueList.add(categoryAttrImport);
							continue;
						}
					}
				} else {
					logger.error("导入属性失败； 类目：{}， 属性：{}", categoryAttrImport.getCategoryName(),
							categoryAttrImport.getAttrebuteName());
					failueList.add(categoryAttrImport);
					continue;
				}
			} catch (Exception e) {
				failueList.add(categoryAttrImport);
				logger.error("导入失败， categoryAttrImport ： {}", categoryAttrImport);
			}
		}
		logger.error("导入类目属性失败的数据：{}", failueList.toString());
	}

	private String getStringCellValue(XSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	@Override
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryList4SuperBoss(
			QueryItemCategoryInDTO queryItemCategoryInDTO, Pager Pager) {
		ExecuteResult<DataGrid<ItemCategoryDTO>> executeResult = new ExecuteResult();
		DataGrid<ItemCategoryDTO> dataGrid = new DataGrid();
		try {
			if (queryItemCategoryInDTO.getStartTime() == null || queryItemCategoryInDTO.getEndTime() == null) {
				executeResult.setResultMessage("startTime or　endTime　is null");
				executeResult.addErrorMessage("startTime or　endTime　is null");
				return executeResult;
			}
			List<ItemCategoryDTO> list = itemCategoryDAO.queryItemCategoryList4SB(queryItemCategoryInDTO, Pager);
			Long count = itemCategoryDAO.queryCountItemCategoryList4SB(queryItemCategoryInDTO);
			dataGrid.setRows(list);
			dataGrid.setTotal(count);
			executeResult.setResult(dataGrid);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			executeResult.getErrorMessages().add(e.getMessage());
		}
		return executeResult;
	}
	
	/**
	 * VMS - 根据品类名称模糊查询品类
	 * @time 2017-12-06
	 * @param itemCategoryDTO 查询参数
	 * @param page 分页信息
	 * @return
	 * @support
	 */
	public ExecuteResult<List<ItemCategoryDTO>> queryCategoryList(ItemCategoryDTO itemCategoryDTO, Pager page) {
		ExecuteResult<List<ItemCategoryDTO>> result = new ExecuteResult<List<ItemCategoryDTO>>();
		try {
			List<ItemCategoryDTO> list = itemCategoryDAO.queryItemCategoryList(itemCategoryDTO, page);
			result.setResult(list);
		} catch (Exception e) {
			logger.error("error:：" + e.getMessage());
			result.addErrorMessage("模糊查询品类出错");
		}
		return result;
	}
}