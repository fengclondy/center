/**
 * Copyright (C), 2013-2018, 汇通达网络有限公司
 * FileName:    TradeOrderMiddlewareHandle.java
 * Author:      shihb
 * Date:        2018年1月22日
 * Description: 中间件处理类  
 * History:     
 * shihb     2018年1月22日 1.0         创建
 */
package cn.htd.tradecenter.service.handle;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yiji.openapi.tool.fastjson.JSON;

import cn.htd.common.middleware.MiddlewareInterfaceConstant;
import cn.htd.common.middleware.MiddlewareInterfaceUtil;
import cn.htd.goodscenter.dto.middleware.outdto.QueryItemWarehouseOutDTO;
import cn.htd.tradecenter.common.utils.MiddleWare;
import cn.htd.tradecenter.dto.DepartmentDto;
import cn.htd.tradecenter.dto.ProductPriceDTO;
import cn.htd.tradecenter.dto.RebateDTO;
import cn.htd.tradecenter.dto.ReverseCustomerBalanceDTO;

/**
 * 中间件处理类
 * 
 * @author shihb
 */
@Service("tradeOrderMiddlwareHandle")
public class TradeOrderMiddlewareHandle {

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderMiddlewareHandle.class);

    @Resource
    private MiddleWare middleware;

    /**
     * 取得中间件token
     * 
     * @return 中间件token
     */
    private String getToken() {
        String tokenUrl = middleware.getPath() + "/token/" + MiddlewareInterfaceConstant.MIDDLE_PLATFORM_APP_ID;
        String accessTokenErp = MiddlewareInterfaceUtil.httpGet(tokenUrl, Boolean.TRUE);
        if (StringUtils.isEmpty(accessTokenErp)) {
            return null;
        }
        JSONObject accessTokenMap = JSONObject.parseObject(accessTokenErp);
        String accessToken = accessTokenMap.getString("data");
        return accessToken;
    }

    /**
     * 查询账户余额
     * 
     * @param sellerCode
     *            平台公司编码
     * @param buyerCode
     *            会员店编码
     * @param departmentCode
     *            销售部门编码
     * @return 会员店在该平台公司指定销售部门的账户余额
     */
    public BigDecimal getPrivateAccount(String sellerCode, String buyerCode, String departmentCode) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return new BigDecimal(0);
        }

        StringBuilder url = new StringBuilder(middleware.getPath() + "/member/balanceList");
        url.append("?token=" + accessToken + "&memberCode=" + buyerCode);
        if (!StringUtils.isEmpty(sellerCode)) {
            url.append("&supplierCode=" + sellerCode);
        }
        if (!StringUtils.isEmpty(departmentCode)) {
            url.append("&departmentCode=" + departmentCode);
        }
        logger.info("调用中间件【查询账户余额】参数：" + url.toString());
        String responseJson = MiddlewareInterfaceUtil.httpGet(url.toString(), Boolean.TRUE);
        logger.info("调用中间件【查询账户余额】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String department = jsonObject.getString("data");
        if (StringUtils.isEmpty(department)) {
            return new BigDecimal(0);
        }
        BigDecimal privateAccount = new BigDecimal(0);
        List<DepartmentDto> departmentList = JSON.parseArray(department, DepartmentDto.class);
        if (departmentList.size() > 0) {
            for (int i = 0; i < departmentList.size(); i++) {
                privateAccount = privateAccount.add(new BigDecimal(departmentList.get(i).getUsePrice()));
            }
        }
        return privateAccount;
    }

    /**
     * 查询仓库库存
     * 
     * @param sellerCode
     *            卖家编码
     * @param productCode
     *            商品编码
     * @return 仓库库存信息列表
     */
    public List<QueryItemWarehouseOutDTO> queryItemStock(String sellerCode, String productCode) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        StringBuilder url = new StringBuilder(middleware.getPath() + "/order/findBeforeSplitOrder");
        url.append("?token=" + accessToken + "&supplierCode=" + sellerCode + "&productCode=" + productCode);
        logger.info("调用中间件【查询仓库库存】参数：" + url.toString());
        String responseJson = MiddlewareInterfaceUtil.httpGet(url.toString(), Boolean.TRUE);
        logger.info("调用中间件【查询仓库库存】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String stockData = jsonObject.getString("data");
        if (StringUtils.isEmpty(stockData)) {
            return null;
        }
        List<QueryItemWarehouseOutDTO> warehouseList = JSON.parseArray(stockData, QueryItemWarehouseOutDTO.class);
        return warehouseList;
    }

    /**
     * 查询销售部门名称
     * 
     * @param salesDepartmentCode
     * @return 销售部门名称
     */
    public String querySaleDepartmentName(String salesDepartmentCode) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        StringBuilder url = new StringBuilder(middleware.getPath() + "/member/getSaleDepartmentName");
        url.append("?token=" + accessToken + "&departmentCode=" + salesDepartmentCode);
        logger.info("调用中间件【查询销售部门名称】参数：" + url.toString());
        String responseJson = MiddlewareInterfaceUtil.httpGet(url.toString(), Boolean.TRUE);
        logger.info("调用中间件【查询销售部门名称】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String department = jsonObject.getString("data");
        return department;
    }

    /**
     * 查询返利单信息
     * 
     * @param buyerCode
     *            会员店编码
     * @param departmentName
     *            销售部门名称
     * @return 返利单信息
     */
    public List<RebateDTO> queryRebate(String buyerCode, String departmentName) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        String encDepartmentName = "";
        try {
            // 必须要转成数组
            encDepartmentName = URLEncoder.encode(MessageFormat.format("[\"{0}\"]", departmentName), "UTF-8");
        } catch (Exception e) {
            return null;
        }

        StringBuilder url = new StringBuilder(middleware.getPath() + "/order/findRebateList");
        url.append("?token=" + accessToken + "&memberCode=" + buyerCode + "&departmentName=" + encDepartmentName);
        logger.info("调用中间件【查询返利单信息】参数：" + url.toString());
        String responseJson = MiddlewareInterfaceUtil.httpGet(url.toString(), Boolean.TRUE);
        logger.info("调用中间件【查询返利单信息】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String rebateData = jsonObject.getString("data");
        if (StringUtils.isEmpty(rebateData)) {
            return null;
        }
        List<RebateDTO> rebateList = JSON.parseArray(rebateData, RebateDTO.class);
        return rebateList;
    }

    /**
     * 查询商品价格
     * 
     * @param sellerCode
     *            卖家编码
     * @param productCode
     *            商品编码
     * @return 价格信息
     */
    public ProductPriceDTO queryProductPrice(String sellerCode, String productCode) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }

        StringBuilder url = new StringBuilder(middleware.getPath() + "/product/findProductPrice");
        url.append("?token=" + accessToken + "&supplierCode=" + sellerCode + "&productCode=" + productCode);
        logger.info("调用中间件【查询商品价格】参数：" + url.toString());
        String responseJson = MiddlewareInterfaceUtil.httpGet(url.toString(), Boolean.TRUE);
        logger.info("调用中间件【查询商品价格】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String priceData = jsonObject.getString("data");
        if (StringUtils.isEmpty(priceData)) {
            return null;
        }
        ProductPriceDTO productPrice = JSON.parseObject(priceData, ProductPriceDTO.class);
        return productPrice;
    }

    /**
     * 锁定帐存
     * 
     * @param reverseBalanceList
     *            锁定帐存信息列表
     * @return 锁定结果
     */
    public boolean reverseBalance(List<ReverseCustomerBalanceDTO> reverseBalanceList) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return false;
        }

        String url = middleware.getPath() + "/order/batchLockCustomerBalance";
        Map<String, String> param = new HashMap<String, String>();
        param.put("token", accessToken);
        param.put("ordersJson", JSONObject.toJSONString(reverseBalanceList));
        logger.info("调用中间件【锁定帐存】url：{}，param：{}", url, JSONObject.toJSON(param));
        String responseJson = MiddlewareInterfaceUtil.httpPost(url, param, Boolean.TRUE);
        logger.info("调用中间件【锁定帐存】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        if (jsonObject == null) {
            return false;
        }
        return MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(jsonObject.getString("code"));
    }

    /**
     * 释放帐存锁定
     * 
     * @param orderItemNo
     *            订单行号
     * @return 释放锁定结果
     */
    public boolean releaseBalance(String orderItemNo) {
        // 取得token
        String accessToken = getToken();
        if (StringUtils.isEmpty(accessToken)) {
            return false;
        }

        String url = middleware.getPath() + "/order/unlockBalance";
        Map<String, String> param = new HashMap<String, String>();
        param.put("token", accessToken);
        param.put("merchOrderNo", orderItemNo);
        logger.info("调用中间件【释放帐存锁定】url：{}，param：{}", url, JSONObject.toJSON(param));
        String responseJson = MiddlewareInterfaceUtil.httpPost(url, param, Boolean.TRUE);
        logger.info("调用中间件【释放帐存锁定】返回值：" + responseJson);
        JSONObject jsonObject = JSONObject.parseObject(responseJson);
        String resultCode = jsonObject.getString("code");
        // 成功或者"ERP不存在锁定"都认为是正常
        return MiddlewareInterfaceConstant.MIDDLEWARE_RESPONSE_CODE_OF_SUCCESS.equals(resultCode)
                || "1001".equals(resultCode);
    }
}
