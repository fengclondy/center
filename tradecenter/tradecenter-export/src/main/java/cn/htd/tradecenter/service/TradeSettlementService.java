package cn.htd.tradecenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.domain.UnSettlementCount;
import cn.htd.tradecenter.domain.order.TradeSettlementComp;
import cn.htd.tradecenter.dto.TradeOrderSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementConstDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementDetailDTO;
import cn.htd.tradecenter.dto.bill.TradeSettlementWithdrawDTO;

public interface TradeSettlementService {

	/**
	 * 查询结算表
	 * @param params
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<TradeSettlementDTO>> queryTradeSettlements(HashMap<String, Object> params,
			Pager<TradeSettlementDTO> pager);
	
	public ExecuteResult<TradeSettlementDTO> queryTradeSettlementsByParams(Map<String, Object> params);

    /**
     * 查询结算表详情
     * @param params
     * @param pager
     * @return
     */
    public ExecuteResult<DataGrid<TradeSettlementDetailDTO>> queryTradeSettlementDetails(Map<String, Object> params,
                                                                             Pager<TradeSettlementDetailDTO> pager);

	/**
	 * 结算单管理（平台公司）
	 * @param params
	 * @param pager
	 * @return
	 */
	ExecuteResult<DataGrid<TradeSettlementDTO>> tradeSettlementsManageForPlatformCompany(HashMap<String, Object> params, Pager<TradeSettlementDTO> pager);

	/**
	 * 结算单详情（平台公司）
	 * @param params
	 * @param pager
	 * @return
	 */
	ExecuteResult<DataGrid<TradeSettlementDTO>> tradeSettlementsDetailForPlatformCompany(HashMap<String, Object> params, Pager<TradeSettlementDTO> pager);
	
	/**
	 * 根据结算单号重新生成结算单
	 * @param settlementNo 结算单号
	 * @param userId 操作用户ID
	 * @param userName 操作用户名称
	 * @return
	 */
	public Map<String,String> rebuildTheBillBySettlementNo(String settlementNo,Long userId,String userName);

	/**
     * 根据卖家生成结算单
     * @param tradeOrderSettlementDTO
     */
    public String generateAStatementBySeller(TradeOrderSettlementDTO tradeOrderSettlementDTO);
    
    /**
     * 根据type和key查询单条常量
     * @param type
     * @param key
     * @return
     */
	TradeSettlementConstDTO getSetConsByTypeAndKey(String type,String key);
	
	/**
	 * 根据type查询多条常量
	 * @param type
	 * @return
	 */
	List<TradeSettlementConstDTO> getSetConsByType(String type);
    
    /**
     * 查询结算状态
     * @param params
     * @return
     */
    public List<Map<String,Object>> getTradeSetConst(Map<String, Object> params);
    
    /**
     * 使结算单失效
     * @param params
     */
    public Map<String,String> tradeSetInvalid(Map<String, Object> params);
    
    /**
     * 提款
     * @param wdcParams
     * @return
     */
    public ExecuteResult<Map<String, Object>> withdrawalsConfirm(Map<String,String> params);
    
    public TradeSettlementWithdrawDTO queryTraSetWithdraw(TradeSettlementWithdrawDTO dto);
	
	public int updateTraSetWithdraw(TradeSettlementWithdrawDTO dto);
	
	public void payOperation(TradeSettlementWithdrawDTO dto);

    /**
     * 外部供应商清分
     * @param params
     * @return
     */
    public int tradeSetAffirm(Map<String, Object> params);
    
    /**
     * 平台公司确认结算单，并进行提款
     * @param params
     * @return
     */
    public int insidetradeSetAffirm(Map<String, Object> params);
   
    /**
     * 更新结算单完成状态
     * @param params
     * @return
     */
    public int updateSetSuccess(TradeSettlementWithdrawDTO dto);
    
    /**
     * 查询未结算状态结算单数(分别内部外部)
     * @author tangjiayong
     * @param params
     * @return
     */
    public ExecuteResult<List<UnSettlementCount>> queryUnSettlementCount();
    
    public Map<String, String> queryUnSettlementCount(Map<String, String> params);
    
    /**
     * 查询需要补偿的结算单
     * @return
     */
    public List<TradeSettlementComp> getTradeSettlementComps();
    
    /**
     * job补偿生成结算单
     * @param tradeSettlementComp
     */
    public void rebuildTheTradeSettlementComp(TradeSettlementComp tradeSettlementComp);
    
    public int batchDistribution(TradeSettlementWithdrawDTO dto , List<String> merchantOrderNoList , String totalCommissionAmount , String batchReturnUrl);
    
    public void doSyncExternalTradeData(String settlementNo) throws Exception;
}
