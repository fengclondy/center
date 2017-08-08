package cn.htd.membercenter.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.membercenter.common.constant.MemberCenterCodeEnum;
import cn.htd.membercenter.dao.ConsigneeAddressDAO;
import cn.htd.membercenter.dto.MemberConsigAddressDTO;
import cn.htd.membercenter.dto.MemberInvoiceDTO;
import cn.htd.membercenter.dto.MemberJDAddress;
import cn.htd.membercenter.service.ConsigneeAddressService;
import cn.htd.membercenter.service.MemberBaseService;

/**
 * @version 创建时间：2016年12月5日 上午11:06:17 类说明：收货地址查询与修改
 */
@Service("consigneeAddressService")
public class ConsigneeAddressServiceImpl implements ConsigneeAddressService {

	private final static Logger logger = LoggerFactory.getLogger(ConsigneeAddressServiceImpl.class);

	@Resource
	private ConsigneeAddressDAO consigneeAddressDao;
	@Autowired
	private MemberBaseService memberBaseService;
	@Resource
	private DictionaryUtils dictionaryUtils;

	@Override
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressList(Pager page, Long memberId) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectConsigAddressList");
		ExecuteResult<DataGrid<MemberConsigAddressDTO>> rs = new ExecuteResult<DataGrid<MemberConsigAddressDTO>>();
		DataGrid<MemberConsigAddressDTO> dg = new DataGrid<MemberConsigAddressDTO>();
		try {
			List<MemberConsigAddressDTO> memberConsigAddressDtoList = null;
			memberConsigAddressDtoList = consigneeAddressDao.selectConsigAddressList(page, memberId);
			// 查询总个数
			Long count = consigneeAddressDao.selectConsigAddressCount(memberId);
			if (memberConsigAddressDtoList != null) {
				// for (int i = 0; i < memberConsigAddressDtoList.size(); i++) {
				// MemberConsigAddressDTO memberConsigAddress = new
				// MemberConsigAddressDTO();
				// memberConsigAddress = memberConsigAddressDtoList.get(i);
				// if (memberConsigAddress.getConsigneeAddressTown() != null) {
				// String consigAddress = memberBaseService
				// .getAddressBaseByCode(memberConsigAddress
				// .getConsigneeAddressTown())
				// + memberConsigAddress
				// .getConsigneeAddressDetail();
				// memberConsigAddressDtoList.get(i).setConsigneeAddress(
				// consigAddress);
				// }
				// }
				dg.setRows(memberConsigAddressDtoList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectConsigAddressList=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressIds(Pager page, Long memberId) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectConsigAddressIds");
		ExecuteResult<DataGrid<MemberConsigAddressDTO>> rs = new ExecuteResult<DataGrid<MemberConsigAddressDTO>>();
		DataGrid<MemberConsigAddressDTO> dg = new DataGrid<MemberConsigAddressDTO>();
		try {
			List<MemberConsigAddressDTO> memberConsigAddressDtoList = null;
			memberConsigAddressDtoList = consigneeAddressDao.selectConsigAddressIds(page, memberId);
			// 查询总个数
			Long count = consigneeAddressDao.selectConsigAddressIdsCount(memberId);
			if (memberConsigAddressDtoList != null) {
				dg.setRows(memberConsigAddressDtoList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				dg.setRows(memberConsigAddressDtoList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.addErrorMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectConsigAddressIds=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}

	@Override
	public MemberConsigAddressDTO selectConsigAddressID(Long addressId) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectConsigAddressID");
		MemberConsigAddressDTO memberConsigAddressDTO = new MemberConsigAddressDTO();
		try {
			if (addressId != null || !"".equals(addressId)) {
				memberConsigAddressDTO = consigneeAddressDao.selectConsigAddressID(addressId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectConsigAddressID=" + e);
		}
		return memberConsigAddressDTO;
	}

	@Override
	public ExecuteResult<String> updateConsigInfo(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》updateConsigInfo");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			List<MemberConsigAddressDTO> list = new ArrayList<MemberConsigAddressDTO>();
			list = consigneeAddressDao.selectConsigList(memberConsigAddressDto.getMemberId());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Long addressId = list.get(i).getAddressId();
					memberConsigAddressDto.setAddressId(addressId);
					consigneeAddressDao.updateConsigAddressInfo(memberConsigAddressDto);
				}
				rs.setResult("success");
				rs.setResultMessage("收货人信息修改成功！！");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->updateConsigInfo=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;

	}

	@Override
	public ExecuteResult<String> updatInvoiceInfo(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的增值税发票信息接口方法：ConsigneeAddressServiceImpl====》updatInvoiceInfo");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			List<MemberConsigAddressDTO> list = new ArrayList<MemberConsigAddressDTO>();
			list = consigneeAddressDao.selectInvoiceList(memberConsigAddressDto.getMemberId());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Long invoiceId = list.get(i).getInvoiceId();
					memberConsigAddressDto.setInvoiceId(invoiceId);
					if (memberConsigAddressDto.getInvoiceAddress() == null
							|| memberConsigAddressDto.getInvoiceAddress() == "") {
						if (memberConsigAddressDto.getInvoiceAddressTown() != null
								&& !memberConsigAddressDto.getInvoiceAddressTown().equals("0")) {
							String invoiceAddress = memberBaseService
									.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressTown())
									+ memberConsigAddressDto.getInvoiceAddressDetail();
							memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
						} else {
							String invoiceAddress = memberBaseService
									.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressCounty())
									+ memberConsigAddressDto.getInvoiceAddressDetail();
							memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
						}
					}
					consigneeAddressDao.updatInvoiceInfo(memberConsigAddressDto);

				}
				rs.setResult("success");
				rs.setResultMessage("增值税发票信息修改成功！！");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->updatInvoiceInfo=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;

	}

	@Override
	public ExecuteResult<String> updateConsigAddressInfo(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》updateConsigAddressInfo");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (memberConsigAddressDto.getMemberId() != null) {
				// 更新地址默认状态，默认地址唯一
				if ("1".equals(memberConsigAddressDto.getDefaultFlag())) {
					consigneeAddressDao.updateConsigAddressInfoDefault(memberConsigAddressDto.getMemberId());
				}
				if (memberConsigAddressDto.getConsigneeAddress() == null
						|| memberConsigAddressDto.getConsigneeAddress() == "") {
					if (memberConsigAddressDto.getConsigneeAddressTown() != null) {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressTown())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					}
				}
				consigneeAddressDao.updateConsigAddressInfo(memberConsigAddressDto);
				rs.setResult("success");
				rs.setResultMessage("收货地址信息修改成功！！");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->updateConsigAddressInfo=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> updateAddressList(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》updateAddressList");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (memberConsigAddressDto.getAddressId() != null && memberConsigAddressDto.getInvoiceId() != null) {
				if (memberConsigAddressDto.getConsigneeAddress() == null
						|| memberConsigAddressDto.getInvoiceAddress() == null) {
					if (memberConsigAddressDto.getConsigneeAddressTown() != null
							&& !memberConsigAddressDto.getConsigneeAddressTown().equals("0")) {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressTown())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					} else {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressDistrict())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					}
					if (memberConsigAddressDto.getInvoiceAddressTown() != null
							&& !memberConsigAddressDto.getInvoiceAddressTown().equals("0")) {
						String invoiceAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressTown())
								+ memberConsigAddressDto.getInvoiceAddressDetail();
						memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
					} else {
						String invoiceAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressCounty())
								+ memberConsigAddressDto.getInvoiceAddressDetail();
						memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
					}
				}
				consigneeAddressDao.updateConsigAddressInfo(memberConsigAddressDto);
				consigneeAddressDao.updatInvoiceInfo(memberConsigAddressDto);
				rs.setResult("success");
				rs.setResultMessage("收货地址信息修改成功！！");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->updateAddressList=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
		}
		return rs;

	}

	@Override
	public ExecuteResult<String> insertAddress(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》insertAddress");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (memberConsigAddressDto.getMemberId() != null) {
				if (memberConsigAddressDto.getConsigneeAddress() == null
						|| memberConsigAddressDto.getInvoiceAddress() == null) {
					if (memberConsigAddressDto.getConsigneeAddressTown() != null
							&& !memberConsigAddressDto.getConsigneeAddressTown().equals("0")) {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressTown())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					} else {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressDistrict())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					}
					if (memberConsigAddressDto.getInvoiceAddressTown() != null
							&& !memberConsigAddressDto.getInvoiceAddressTown().equals("0")) {
						String invoiceAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressTown())
								+ memberConsigAddressDto.getInvoiceAddressDetail();
						memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
					} else {
						String invoiceAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getInvoiceAddressCounty())
								+ memberConsigAddressDto.getInvoiceAddressDetail();
						memberConsigAddressDto.setInvoiceAddress(invoiceAddress);
					}
				}
				if ("1".equals(memberConsigAddressDto.getDefaultFlag())) {
					consigneeAddressDao.updateConsigAddressInfoDefault(memberConsigAddressDto.getMemberId());
				}
				consigneeAddressDao.insertConsigAddressInfo(memberConsigAddressDto);
				// 判断是否已经保存了当前发票信息
				// 外部渠道编码
				String channelCode = memberConsigAddressDto.getChannelCode();
				if (StringUtils.isEmpty(channelCode)) {
					channelCode = "";
				}
				MemberInvoiceDTO memberInvoiceDTO = consigneeAddressDao
						.selectInvoiceInfoDto(memberConsigAddressDto.getMemberId(), channelCode, null);
				if (memberInvoiceDTO == null) {
					consigneeAddressDao.insertInvoiceInfo(memberConsigAddressDto);
				} else {
					memberConsigAddressDto.setInvoiceId(Long.valueOf(memberInvoiceDTO.getInvoiceId()));
					consigneeAddressDao.updatInvoiceInfo(memberConsigAddressDto);
				}
				rs.setResultMessage("收货地址信息新增成功！！");
				rs.setResult("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->insertAddress=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> insertConsigAddress(MemberConsigAddressDTO memberConsigAddressDto) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》insertConsigAddress");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (memberConsigAddressDto.getMemberId() != null) {
				if ("1".equals(memberConsigAddressDto.getDefaultFlag())) {
					consigneeAddressDao.updateConsigAddressInfoDefault(memberConsigAddressDto.getMemberId());
				}
				if (memberConsigAddressDto.getConsigneeAddress() == null
						|| memberConsigAddressDto.getConsigneeAddress() == "") {
					if (memberConsigAddressDto.getConsigneeAddressTown() != null) {
						String consigAddress = memberBaseService
								.getAddressBaseByCode(memberConsigAddressDto.getConsigneeAddressTown())
								+ memberConsigAddressDto.getConsigneeAddressDetail();
						memberConsigAddressDto.setConsigneeAddress(consigAddress);
					}
				}
				consigneeAddressDao.insertConsigAddressInfo(memberConsigAddressDto);
				rs.setResultMessage("收货地址信息新增成功！！");
				rs.setResult("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->insertAddress=" + e);
			rs.setResultMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> deleteConsigAddressInfo(Long addressId, Long modifyId, String modifyName) {
		// TODO Auto-generated method stub
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》deleteConsigAddressInfo");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		try {
			if (addressId != null) {
				consigneeAddressDao.deleteConsigAddressInfo(addressId, modifyId, modifyName);
				rs.setResultMessage("收货地址信息删除成功！！");
				rs.setResult("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->deleteConsigAddressInfo=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<String> deleteConsigAddressInfoBatch(String addressId) {
		// TODO Auto-generated method stub
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》deleteConsigAddressInfoBatch");
		ExecuteResult<String> rs = new ExecuteResult<String>();
		MemberConsigAddressDTO memberConsigAddressDto = new MemberConsigAddressDTO();
		try {
			if (addressId != null) {
				String[] str2s = addressId.split(",");
				List<String> strList = new ArrayList<String>();
				for (String s2 : str2s) {
					if (s2 != null) {
						strList.add(s2);
					}
				}
				memberConsigAddressDto.setStrList(strList);
				consigneeAddressDao.deleteConsigAddressInfoBatch(memberConsigAddressDto);
				rs.setResultMessage("收货地址信息批量删除成功！！");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResult("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->deleteConsigAddressInfo=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberConsigAddressDTO> selectDefaultAddress(Long memberId) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectDefaultAddress");
		// TODO Auto-generated method stub
		ExecuteResult<MemberConsigAddressDTO> rs = new ExecuteResult<MemberConsigAddressDTO>();
		try {
			MemberConsigAddressDTO memberConsigAddressDTO = new MemberConsigAddressDTO();
			memberConsigAddressDTO = consigneeAddressDao.selectDefaultAddress(memberId);
			if (memberConsigAddressDTO != null) {
				rs.setResult(memberConsigAddressDTO);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("没查到默认地址信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectDefaultAddress=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberConsigAddressDTO> selectChannelAddressDTO(String messageId, String memberCode,
			String channelCode) {
		ExecuteResult<MemberConsigAddressDTO> rs = new ExecuteResult<MemberConsigAddressDTO>();
		logger.info("消息ID:messageID=" + messageId);
		logger.info("ConsigneeAddressServiceImpl----->selectChannelAddressDTO");
		try {
			MemberConsigAddressDTO memberConsigAddressDTO = new MemberConsigAddressDTO();
			memberConsigAddressDTO = consigneeAddressDao.selectChannelAddressDTO(memberCode, channelCode);
			if (memberConsigAddressDTO != null) {
				rs.setResult(memberConsigAddressDTO);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("没查到地址信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectChannelAddressDTO=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
		}
		return rs;
	}

	@Override
	public ExecuteResult<List<MemberConsigAddressDTO>> selectAddressCityList(Long memberId, String consigneeAddressCity,
			String consigneeAddressProvince, String sort) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectAddressCityList");
		ExecuteResult<List<MemberConsigAddressDTO>> rs = new ExecuteResult<List<MemberConsigAddressDTO>>();
		try {
			Pager page = new Pager();
			page.setPageOffset(1);
			page.setRows(20);// 写死默认显示0-20
			List<MemberConsigAddressDTO> list = consigneeAddressDao.searchAddressCityList(memberId,
					consigneeAddressCity, consigneeAddressProvince, sort, page);
			if (list.size() > 0 && list != null) {
				rs.setResult(list);
				rs.setResultMessage("success");
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("没查到数据");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectAddressCityList=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.addErrorMessage("error");
			rs.setResultMessage("异常");
		}
		return rs;
	}

	@Override
	public ExecuteResult<MemberConsigAddressDTO> selectChannelAddress(String memberCode, String channelCode) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectChannelAddress");
		ExecuteResult<MemberConsigAddressDTO> rs = new ExecuteResult<MemberConsigAddressDTO>();
		try {
			MemberConsigAddressDTO memberConsigAddressDTO = new MemberConsigAddressDTO();
			channelCode = dictionaryUtils.getValueByCode(DictionaryConst.TYPE_PRODUCT_CHANNEL, channelCode);
			memberConsigAddressDTO = consigneeAddressDao.selectChannelAddressDTO(memberCode, channelCode);
			if (memberConsigAddressDTO != null) {
				rs.setResult(memberConsigAddressDTO);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
				rs.setResultMessage("success");
			} else {
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
				rs.addErrorMessage("没查到地址信息");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectChannelAddress=" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
			rs.setResultMessage("error");
		}
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.htd.membercenter.service.ConsigneeAddressService#
	 * selectConsigAddressListByPlus(cn.htd.common.Pager, java.lang.Long)
	 */
	@Override
	public ExecuteResult<DataGrid<MemberConsigAddressDTO>> selectConsigAddressListByPlus(Pager page, Long memberId) {
		logger.info("调用的接口方法：ConsigneeAddressServiceImpl====》selectConsigAddressListByPlus");
		ExecuteResult<DataGrid<MemberConsigAddressDTO>> rs = new ExecuteResult<DataGrid<MemberConsigAddressDTO>>();
		DataGrid<MemberConsigAddressDTO> dg = new DataGrid<MemberConsigAddressDTO>();
		try {
			List<MemberConsigAddressDTO> memberConsigAddressDtoList = null;
			memberConsigAddressDtoList = consigneeAddressDao.selectConsigAddressListByPlus(page, memberId);
			// 查询总个数
			Long count = consigneeAddressDao.selectConsigAddressListByPlusCount(memberId);
			if (memberConsigAddressDtoList != null) {
				dg.setRows(memberConsigAddressDtoList);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->selectConsigAddressListByPlus=" + e);
			rs.setResultMessage("error");
			rs.addErrorMessage("error");
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}

	@Override
	public ExecuteResult<DataGrid<MemberJDAddress>> getAllJDAddressForPage(Pager<MemberJDAddress> page,
			MemberJDAddress dto) {
		logger.info("ConsigneeAddressServiceImpl--->getAllJDAddressForPage--->pager:" + JSONObject.toJSONString(page));
		logger.info("ConsigneeAddressServiceImpl--->getAllJDAddressForPage--->dto:" + JSONObject.toJSONString(dto));
		ExecuteResult<DataGrid<MemberJDAddress>> rs = new ExecuteResult<DataGrid<MemberJDAddress>>();
		DataGrid<MemberJDAddress> dg = new DataGrid<MemberJDAddress>();
		try {
			Long count = consigneeAddressDao.getAllVendorAddProductPlusCount(dto);
			List<MemberJDAddress> list = consigneeAddressDao.getAllVendorAddProductPlus(page, dto);
			// 查询总个数
			if (list != null) {
				dg.setRows(list);
				dg.setTotal(count);
				rs.setResult(dg);
				rs.setCode(MemberCenterCodeEnum.SUCCESS.getCode());
			} else {
				rs.addErrorMessage("fail");
				rs.setResultMessage("要查询的数据不存在!!");
				rs.setCode(MemberCenterCodeEnum.NO_DATA_CODE.getCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ConsigneeAddressServiceImpl----->getAllJDAddressForPage=" + e);
			rs.addErrorMessage("error");
			rs.setResultMessage("error:" + e);
			rs.setCode(MemberCenterCodeEnum.ERROR.getCode());
		}
		return rs;
	}

}
