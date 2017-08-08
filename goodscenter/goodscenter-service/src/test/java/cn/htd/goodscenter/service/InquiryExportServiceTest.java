package cn.htd.goodscenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.InquiryInfoDTO;
import cn.htd.goodscenter.dto.InquiryMatDTO;
import cn.htd.goodscenter.dto.InquiryOrderDTO;
import cn.htd.goodscenter.test.common.CommonTest;

import org.junit.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InquiryExportServiceTest extends CommonTest {

	@Resource
	InquiryExportService inquiryExportService = null;

	@Test
	public void queryByInquiryInfo() {
		InquiryInfoDTO dto = new InquiryInfoDTO();
		ExecuteResult<InquiryInfoDTO> er = inquiryExportService.queryByInquiryInfo(dto);
		System.out.println(er.getResult());
		System.out.println(er.getResult().getInquiryNo());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryInquiryInfoList() {
		Pager page = new Pager();
		InquiryInfoDTO dto = new InquiryInfoDTO();
		List<String> list = new ArrayList<String>();
		list.add("2");
		list.add("3");
		dto.setStatusList(list);
		ExecuteResult<DataGrid<InquiryInfoDTO>> er = inquiryExportService.queryInquiryInfoList(dto, page);
		for (InquiryInfoDTO d : er.getResult().getRows()) {
			System.out.println(d.getInquiryName());
			System.out.println(d.getCreateBy());
			System.out.println(d.getCreateDate());
		}
		System.out.println(er.getResult().getTotal());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryInquiryInfoPager() {
		Pager page = new Pager();
		InquiryInfoDTO dto = new InquiryInfoDTO();
		List<String> list = new ArrayList<String>();
		list.add("2");
		list.add("3");
		dto.setStatusList(list);
		ExecuteResult<DataGrid<Map>> er = inquiryExportService.queryInquiryInfoPager(dto, page);
		for (Map d : er.getResult().getRows()) {
			System.out.println(d.get("itemName"));
		}
		System.out.println(er.getResult().getTotal());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryByInquiryMat() {
		InquiryMatDTO dto = new InquiryMatDTO();
		dto.setInquiryNo("11111");
		ExecuteResult<InquiryMatDTO> er = inquiryExportService.queryByInquiryMat(dto);
		System.out.println(er.getResult());
		System.out.println(er.getResult().getMatDesc());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryContractmMatList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Pager page = new Pager();
		InquiryMatDTO dto = new InquiryMatDTO();
		dto.setInquiryNo("XJ201507241326075968");
		dto.setActiveFlag("1");
		dto.setShopId(1000023);
		ExecuteResult<DataGrid<Map>> er = inquiryExportService.queryInquiryMatList(dto, page);
		for (Map d : er.getResult().getRows()) {
			System.out.println(d.get("inquiryNo"));
		}
		System.out.println(er.getResult().getTotal());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryByInquiryOrder() {
		InquiryOrderDTO dto = new InquiryOrderDTO();
		dto.setInquiryNo("11111");
		ExecuteResult<InquiryOrderDTO> er = inquiryExportService.queryByInquiryOrder(dto);
		System.out.println(er.getResult());
		System.out.println(er.getResult().getOrderNo());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void queryInquiryOrderList() {
		Pager page = new Pager();
		InquiryOrderDTO dto = new InquiryOrderDTO();
		dto.setInquiryNo("11111");
		ExecuteResult<DataGrid<InquiryOrderDTO>> er = inquiryExportService.queryInquiryOrderList(dto, page);
		for (InquiryOrderDTO d : er.getResult().getRows()) {
			System.out.println(d.getOrderNo());
		}
		System.out.println(er.getResult().getTotal());
		System.out.println(er.getResultMessage());
	}

	@Test
	public void addInquiryInfo() {

		InquiryInfoDTO dto = new InquiryInfoDTO();
		dto.setInquiryNo("XJ2015062300001");
		dto.setInquiryName("徐杰来袭询价男");
		dto.setPrinterId(1000000639);// 印刷厂用户主键
		dto.setSupplierId(1000000639);// 供货方主键
		dto.setBeginDate(new Date());// 合同有效期--开始
		dto.setEndDate(new Date());// 合同有效期--结束
		dto.setStatus("1");// 合同状态
		dto.setRemarks("徐杰来袭询价男");
		dto.setMatCd("1000000006");
		dto.setCreateBy("1000000639");//
		dto.setCreateDate(new Date());//
		dto.setUpdateBy("1000000639");//
		dto.setUpdateDate(new Date());//
		dto.setActiveFlag("0");// 有效标记 0-有效 1-无效
		ExecuteResult<String> str = inquiryExportService.addInquiryInfo(dto);
		System.out.println(str.getResultMessage() + str.getResult());

		dto.setInquiryNo("XJ2015062300002");
		dto.setInquiryName("徐杰来袭询价女");
		dto.setPrinterId(1000000639);// 印刷厂用户主键
		dto.setSupplierId(1000000639);// 供货方主键
		dto.setBeginDate(new Date());// 合同有效期--开始
		dto.setEndDate(new Date());// 合同有效期--结束
		dto.setStatus("1");// 合同状态
		dto.setRemarks("徐杰来袭询价男");
		dto.setMatCd("1000000005");
		dto.setCreateBy("1000000639");//
		dto.setCreateDate(new Date());//
		dto.setUpdateBy("1000000639");//
		dto.setUpdateDate(new Date());//
		dto.setActiveFlag("0");// 有效标记 0-有效 1-无效
		inquiryExportService.addInquiryInfo(dto);

		dto.setInquiryNo("XJ2015062300003");
		dto.setInquiryName("徐杰来袭询价男女");
		dto.setPrinterId(1000000639);// 印刷厂用户主键
		dto.setSupplierId(1000000639);// 供货方主键
		dto.setBeginDate(new Date());// 合同有效期--开始
		dto.setEndDate(new Date());// 合同有效期--结束
		dto.setStatus("1");// 合同状态
		dto.setRemarks("徐杰来袭询价男");
		dto.setMatCd("1000000008");
		dto.setCreateBy("1000000639");//
		dto.setCreateDate(new Date());//
		dto.setUpdateBy("1000000639");//
		dto.setUpdateDate(new Date());//
		dto.setActiveFlag("0");// 有效标记 0-有效 1-无效
		inquiryExportService.addInquiryInfo(dto);

	}

	@Test
	public void addInquiryMat() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		InquiryMatDTO dto = new InquiryMatDTO();
		dto.setInquiryNo("10001");
		dto.setMatCd("1000001");// 物料号
		dto.setMatDesc("Junit测试");// 物料描述
		dto.setLable1Cd("10");// 类别1
		dto.setLable1Desc("Junit测试");// 类别1描述
		dto.setLable2Cd("1010");// 类别2
		dto.setLable2Desc("Junit测试");// 类别2描述
		dto.setLable3Cd("101010");// 类别3
		dto.setLable3Desc("Junit测试");// 类别3描述
		dto.setMatSpec("Junit测试");// 物料规格
		dto.setMatBrand("Junit测试");// 品牌
		dto.setMatDiscount("5");// 折扣
		dto.setMatPrice(100.00);// 价格
		dto.setMatUnit("PC");// 单位
		dto.setCreateBy("1");// 创建人
		dto.setPrinterId(1010);
		dto.setSupplierId(1011);
		try {
			dto.setBeginDate(sdf.parse("2015-06-07"));
			dto.setEndDate(sdf.parse("2015-06-07"));
		} catch (Exception e) {

		}
		dto.setCreateDate(new Date());// 创建日期
		dto.setUpdateBy("1");// 修改人
		dto.setUpdateDate(new Date());// 修改时间
		dto.setActiveFlag("0");// 有效标记 0-有效 1-无效
		ExecuteResult<String> str = inquiryExportService.addInquiryMat(dto);
		System.out.println(str.getResultMessage() + str.getResult());
	}

	@Test
	public void addInquiryOrder() {
		InquiryOrderDTO dto = new InquiryOrderDTO();
		dto.setInquiryNo("10001");
		dto.setOrderNo("00000101");
		dto.setCreateBy("1");// 创建人
		dto.setCreateDate(new Date());// 创建日期
		dto.setUpdateBy("1");// 修改人
		dto.setUpdateDate(new Date());// 修改时间
		dto.setRemark("Junit测试");
		dto.setActiveFlag("0");// 有效标记 0-有效 1-无效
		ExecuteResult<String> str = inquiryExportService.addInquiryOrder(dto);
		System.out.println(str.getResultMessage() + str.getResult());
	}

	@Test
	public void modifyInquiryInfo() {
		InquiryInfoDTO dto = new InquiryInfoDTO();
		dto.setInquiryNo("XJ201506241605394926");
		dto.setActiveFlag("1");
		ExecuteResult<String> str = inquiryExportService.modifyInquiryInfo(dto);
		System.out.println(str.getResult());
	}

	@Test
	public void modifyInquiryInfoById() {
		InquiryInfoDTO dto = new InquiryInfoDTO();
		dto.setId(52L);
		ExecuteResult<String> str = inquiryExportService.modifyInquiryInfoById(dto);
		System.out.println(str.getResult());
	}

	@Test
	public void modifyInquiryMat() {
		InquiryMatDTO dto = new InquiryMatDTO();
		dto.setId(13L);
		dto.setMatPrice(200.00);
		ExecuteResult<String> str = inquiryExportService.modifyInquiryMat(dto);
		System.out.println(str.getResult());
	}

	@Test
	public void modifyInquiryOrder() {
		InquiryOrderDTO dto = new InquiryOrderDTO();
		dto.setId(1L);
		dto.setActiveFlag("1");
		ExecuteResult<String> str = inquiryExportService.modifyInquiryOrder(dto);
		System.out.println(str.getResult());
	}

	@Test
	public void Test() {
		ExecuteResult<String> str = inquiryExportService.createInquiryNo();
		System.out.println(str.getResult());
	}

}
