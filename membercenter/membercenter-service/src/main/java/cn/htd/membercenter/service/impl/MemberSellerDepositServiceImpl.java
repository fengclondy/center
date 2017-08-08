/**
 * 
 * <p>Copyright (C), 2013-2016, 汇通达网络有限公司</p>  
 * <p>Title: MemberCompanyInfoServiceImpl</p>
 * @author youyajun
 * @date 2016年12月12日
 * <p>Description: 
 *			商家(外部)相关实现
 * </p>
 */
package cn.htd.membercenter.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.common.exception.MembercenterBussinessException;
import cn.htd.membercenter.dao.MemberSellerDepositDAO;
import cn.htd.membercenter.dto.MemberSellerDepositDto;
import cn.htd.membercenter.service.MemberSellerDepositService;

@Service("memberSellerDepositService")
public class MemberSellerDepositServiceImpl implements MemberSellerDepositService {
	private final static Logger logger = LoggerFactory.getLogger(MemberSellerDepositServiceImpl.class);

	// MemberSellerDepositDao接口
	@Resource
	private MemberSellerDepositDAO memberCompanyDAO;

	/**
	 * 查询商家保证金数据
	 * 
	 * @param bondNam
	 * @param bondNo
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	@Override
	public DataGrid<MemberSellerDepositDto> searchDepositInfo(String bondNam, String bondNo,
			Pager<MemberSellerDepositDto> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-searchBondData",
				JSONObject.toJSONString(bondNam), JSONObject.toJSONString(bondNo), JSONObject.toJSONString(pager));
		DataGrid<MemberSellerDepositDto> result = new DataGrid<MemberSellerDepositDto>();
		List<MemberSellerDepositDto> sellerDepositList = null;
		try {
			Long count = memberCompanyDAO.depositInfoCount(bondNam, bondNo);
			if (count > 0) {
				sellerDepositList = memberCompanyDAO.searchDepositInfo(bondNam, bondNo, pager);
				DecimalFormat myformat = new DecimalFormat("0.00");
				for (MemberSellerDepositDto company : sellerDepositList) {
					if (company.getDepositBalance() != null) {
						company.setDepositBalanceBackNmae(myformat.format(company.getDepositBalance()));
					}
				}
				result.setRows(sellerDepositList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-queryBaseAddressList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 查询商家详细信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	@Override
	public DataGrid<MemberSellerDepositDto> searchDepositChangeHistory(Long sellerId, String startTime, String endTime,
			Pager<MemberSellerDepositDto> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-searchCompanyData",
				JSONObject.toJSONString(sellerId), JSONObject.toJSONString(startTime), JSONObject.toJSONString(endTime),
				JSONObject.toJSONString(pager));
		DataGrid<MemberSellerDepositDto> result = new DataGrid<MemberSellerDepositDto>();
		List<MemberSellerDepositDto> depositChangeHistoryList = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat myformat = new DecimalFormat("0.00");
		try {
			Long count = memberCompanyDAO.depositChangeHistoryCount(sellerId, startTime, endTime);
			if (count > 0) {
				depositChangeHistoryList = memberCompanyDAO.searchDepositChangeHistory(sellerId, startTime, endTime,
						pager);
				for (MemberSellerDepositDto company : depositChangeHistoryList) {
					if ("1".equals(company.getChangeType())) {
						company.setChangeTypeName("增加");
						company.setChangeDepositName("+" + myformat.format(company.getChangeDeposit()));
					} else {
						company.setChangeTypeName("减少");
						company.setChangeDepositName("-" + myformat.format(company.getChangeDeposit()));
					}
					if (company.getCreateTime() == null || "".equals(company.getCreateTime())) {
						company.setCreateTimeName("");
					} else {
						company.setCreateTimeName(formatter.format(company.getCreateTime()));
					}
					company.setDepositBalanceBackNmae(myformat.format(company.getDepositBalanceBack()));
				}
				result.setRows(depositChangeHistoryList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-queryBaseAddressList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 商家保证金变动履历添加，商家保证金信息修改
	 * 
	 * @param memberCompanyInfoDto
	 * @return ExecuteResult<MemberSellerDepositDto>
	 */
	@Override
	public ExecuteResult<MemberSellerDepositDto> addUpdateSellerDeposit(MemberSellerDepositDto memberCompanyInfoDto) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-addCompany",
				JSONObject.toJSONString(memberCompanyInfoDto));
		ExecuteResult<MemberSellerDepositDto> result = new ExecuteResult<MemberSellerDepositDto>();
		try {
			checkInputParameter(memberCompanyInfoDto);
			memberCompanyDAO.addDepositChangeHistory(memberCompanyInfoDto);
			Long count = memberCompanyDAO.sellerDepositInfoCount(memberCompanyInfoDto.getSellerId());
			if (count == 0) {
				memberCompanyDAO.addSellerDepositInfo(memberCompanyInfoDto);
			} else {
				memberCompanyDAO.updateDepositInfo(memberCompanyInfoDto);
			}
			result.setResultMessage("修改成功！");
		} catch (MembercenterBussinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "MemberCompanyInfoServiceImpl-addCompany", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-addCompany",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 查询商家外接渠道保证金数据
	 * 
	 * @param bondNam
	 * @param bondNo
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	@Override
	public DataGrid<MemberSellerDepositDto> searchOuterChannelDepositInfo(String bondNam, String bondNo,
			Pager<MemberSellerDepositDto> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-searchBondData",
				JSONObject.toJSONString(bondNam), JSONObject.toJSONString(bondNo), JSONObject.toJSONString(pager));
		// 初始化返回类型
		DataGrid<MemberSellerDepositDto> result = new DataGrid<MemberSellerDepositDto>();
		// 定义接收数据类型
		List<MemberSellerDepositDto> sellerDepositList = null;
		try {
			// 查询商家外接渠道保证金数据，这是做翻页的总数据
			// 参数：商家名称，商家编号
			Long count = memberCompanyDAO.outerChannelDepositInfoCount(bondNam, bondNo);
			// 判断商家外接渠道保证金数据至少有一条的时候
			if (count > 0) {
				// 查询商家外接渠道保证金数据,这是需要在画面上显示的
				// 参数：商家名称，商家编号，每页显示件数
				// 返回类型：上面定义的数据接收类型（List<MemberSellerDepositDto>）
				sellerDepositList = memberCompanyDAO.searchOuterChannelDepositInfo(bondNam, bondNo, pager);
				// 初始化DecimalFormat，这是给bigdecimal类型转换成String类型并且保留2位小数的
				DecimalFormat myformat = new DecimalFormat("0.00");
				// 循环上面给页面上显示用的返回数据
				for (MemberSellerDepositDto company : sellerDepositList) {
					// 判断余额（bigdecimal）不等于null的时候
					if (company.getDepositBalance() != null) {
						// 把余额（bigdecimal类型）转换成String类型并且保留2位小数
						company.setDepositBalanceBackNmae(myformat.format(company.getDepositBalance()));
					}
				}
				// 把整理过的所有数据（List<MemberSellerDepositDto>）放入到上面初始化返回类型（DataGrid<MemberSellerDepositDto>）的Rows中
				result.setRows(sellerDepositList);
				// 把上面取出来的总数据，也就是画面需要做翻页的数据放入Total中，画面DataGrid会调用。
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-queryBaseAddressList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 查询商家外接渠道详细信息
	 * 
	 * @param sellerId
	 * @param startTime
	 * @param endTime
	 * @param pager
	 * @return DataGrid<MemberCompanyInfoDto>
	 */
	@Override
	public DataGrid<MemberSellerDepositDto> searchOuterChannelDepositChangeHistory(Long sellerId, String startTime,
			String endTime, Pager<MemberSellerDepositDto> pager) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-searchCompanyData",
				JSONObject.toJSONString(sellerId), JSONObject.toJSONString(startTime), JSONObject.toJSONString(endTime),
				JSONObject.toJSONString(pager));
		DataGrid<MemberSellerDepositDto> result = new DataGrid<MemberSellerDepositDto>();
		List<MemberSellerDepositDto> depositChangeHistoryList = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat myformat = new DecimalFormat("0.00");
		try {
			Long count = memberCompanyDAO.outerChanneldepositChangeHistoryCount(sellerId, startTime, endTime);
			if (count > 0) {
				depositChangeHistoryList = memberCompanyDAO.searchOuterChannelDepositChangeHistory(sellerId, startTime,
						endTime, pager);
				for (MemberSellerDepositDto company : depositChangeHistoryList) {
					if ("1".equals(company.getChangeType())) {
						company.setChangeTypeName("增加");
						company.setChangeDepositName("+" + myformat.format(company.getChangeDeposit()));
					} else {
						company.setChangeTypeName("减少");
						company.setChangeDepositName("-" + myformat.format(company.getChangeDeposit()));
					}
					if (company.getCreateTime() == null || "".equals(company.getCreateTime())) {
						company.setCreateTimeName("");
					} else {
						company.setCreateTimeName(formatter.format(company.getCreateTime()));
					}
					company.setDepositBalanceBackNmae(myformat.format(company.getDepositBalanceBack()));
				}
				result.setRows(depositChangeHistoryList);
				result.setTotal(count);
			}
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-queryBaseAddressList",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * 商家外接渠道保证金变动履历表修正
	 * 
	 * @param memberCompanyInfoDto
	 * @return ExecuteResult<MemberCompanyInfoDto>
	 */
	@Override
	public ExecuteResult<MemberSellerDepositDto> addUpdateSellerOuterChannel(
			MemberSellerDepositDto memberCompanyInfoDto) {
		logger.info("\n 方法[{}]，入参：[{}]", "MemberCompanyInfoServiceImpl-addCompany",
				JSONObject.toJSONString(memberCompanyInfoDto));
		ExecuteResult<MemberSellerDepositDto> result = new ExecuteResult<MemberSellerDepositDto>();
		try {
			checkInputParameter(memberCompanyInfoDto);
			memberCompanyDAO.addOuterChannelDepositChangeHistory(memberCompanyInfoDto);
			Long count = memberCompanyDAO.sellerOuterChannelDepositInfoCount(memberCompanyInfoDto.getSellerId());
			if (count == 0) {
				memberCompanyDAO.addSellerOuterChannelDepositInfo(memberCompanyInfoDto);
			} else {
				memberCompanyDAO.updateOuterChannelDepositInfo(memberCompanyInfoDto);
			}
			result.setResultMessage("修改成功！");
		} catch (MembercenterBussinessException bcbe) {
			result.addErrorMessage(bcbe.getMessage());
		} catch (Exception e) {
			result.addErrorMessage(e.getMessage());
			logger.error("\n 方法[{}]，异常：[{}]", "MemberCompanyInfoServiceImpl-addCompany", e.getMessage());
		} finally {
			logger.info("\n 方法[{}]，出参：[{}]", "MemberCompanyInfoServiceImpl-addCompany",
					JSONObject.toJSONString(result));
		}
		return result;
	}

	/**
	 * Validation的check
	 * 
	 * @param memberCompanyInfoDto
	 */
	private void checkInputParameter(MemberSellerDepositDto memberCompanyInfoDto) {
		if (memberCompanyInfoDto.getSellerId() == null) {
			throw new MembercenterBussinessException("商家ID不能是空白");
		}
		if (StringUtils.isBlank(memberCompanyInfoDto.getChangeType())) {
			throw new MembercenterBussinessException("变动类型不能是空白");
		}
		if (memberCompanyInfoDto.getChangeDeposit() == null) {
			throw new MembercenterBussinessException("变动金额不能是空白");
		}
		if (memberCompanyInfoDto.getDepositBalanceBack() == null) {
			throw new MembercenterBussinessException("变动后保证金余额不能是空白");
		}
		if (memberCompanyInfoDto.getCreateId() == null) {
			throw new MembercenterBussinessException("创建人ID不能是空白");
		}
		if (StringUtils.isBlank(memberCompanyInfoDto.getRemarks())) {
			throw new MembercenterBussinessException("备注不能是空白");
		}
		if (StringUtils.isBlank(memberCompanyInfoDto.getCreateName())) {
			throw new MembercenterBussinessException("创建人名称");
		}
	}
}
