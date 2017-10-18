package cn.htd.membercenter.service.util;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberImportSuccInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingInfoDTO;
import cn.htd.membercenter.dto.SellerMeetingSignDTO;
import cn.htd.membercenter.service.MemberBaseInfoService;
@Ignore
public class MeetingFileUtil {
	ApplicationContext ctx = null;
	MemberBaseInfoService memberBaseInfoService = null;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("classpath*:/test.xml");
		memberBaseInfoService = (MemberBaseInfoService) ctx.getBean("memberBaseInfoService");
	}

	@Test
	public void testGenSQL() {
		String filedir = "D:\\会员会议0811\\";
		String TDCode = "";
		DateFormat format = new SimpleDateFormat("MMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		DateFormat mttimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tmpstr = "000000" + format.format(c.getTime());
		String meetnotime = tmpstr.substring(tmpstr.length() - 6, tmpstr.length());
		if (StringUtils.isNotEmpty(filedir)) {
			File file = new File(filedir);
			File[] files = null;
			if (file.exists()) {
				if (file.isDirectory()) {
					files = file.listFiles();
				} else {
					files = new File[] { file };
				}
			}
			StringBuilder ss = new StringBuilder();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					continue;
				}
				try {
					Workbook wb = WorkbookFactory.create(file2);
					TDCode = "";
					if (wb.getSheetAt(1) != null) {
						Sheet sheet = wb.getSheetAt(1);
						SellerMeetingSignDTO ms = null;
						SellerMeetingInfoDTO mi = null;
						mi = new SellerMeetingInfoDTO();
						ms = new SellerMeetingSignDTO();
						System.out.println("num::::" + sheet.getPhysicalNumberOfRows());
						for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
							Row row = sheet.getRow(i);
							if (row != null) {
								//System.out.println("row::::" + row.getPhysicalNumberOfCells());
								if(row.getPhysicalNumberOfCells()<10) {
									continue;
								}
								String vl = "";
								boolean skip = false;
								for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
									if (row.getCell(j) != null) {
										// row.getCell(j).setCellType(XSSFCell.CELL_TYPE_STRING);
										if (j == 6 && TDCode.equals("")) {
											vl = row.getCell(j).getStringCellValue().trim();
											String name = vl;
											ExecuteResult<MemberImportSuccInfoDTO> rt = memberBaseInfoService
													.querySellerIdByName(name);
											if (rt.isSuccess()) {
												TDCode = rt.getResult().getMemberCode();
												mi.setMeetingNo(TDCode + meetnotime);
												ms.setMeetingNo(TDCode + meetnotime);
												mi.setSellerId(new Long(rt.getResult().getMemberId()));
												ms.setSellerId(new Long(rt.getResult().getMemberId()));
												mi.setSellerName(name);
												ms.setSellerName(name);
												ms.setSellerCode(TDCode);
											}
										}
										if (j == 4 && i == 1) {
											vl = row.getCell(j).getStringCellValue().trim();
											mi.setMeetingTitle(vl);
											ms.setMeetingTitle(vl);
										}
										if (j == 3 && i == 1) {
											vl = row.getCell(j).getStringCellValue().trim();
											mi.setMeetingAddr(vl);
											ms.setMeetingAddr(vl);
										}
										if (j == 1 && i == 1) {
											Date vl1 = row.getCell(j).getDateCellValue();
											mi.setMeetingStartTime(vl1);
											mi.setMeetingEndTime(vl1);
										}
										if (j == 2 && i == 1) {
											vl = row.getCell(j).getStringCellValue().trim();

											String[] sd = vl.split("-");
											if (vl.indexOf("--") >= 0) {
												sd = vl.split("--");
											}
											String[] start = sd[0].split(":");
											String[] end = sd[1].split(":");
											c.setTime(mi.getMeetingStartTime());
											c.set(Calendar.HOUR_OF_DAY, new Integer(start[0]));
											c.set(Calendar.MINUTE, new Integer(start[1]));
											mi.setMeetingStartTime(c.getTime());
											ms.setMeetingStartTime(c.getTime());

											c.setTime(mi.getMeetingEndTime());
											c.set(Calendar.HOUR_OF_DAY, new Integer(end[0]));
											c.set(Calendar.MINUTE, new Integer(end[1]));
											mi.setMeetingEndTime(c.getTime());
											ms.setMeetingEndTime(c.getTime());
										}
										if (j == 5 && i == 1) {
											vl = row.getCell(j).getStringCellValue().trim();
											mi.setMeetingCont(vl);
										}
										if (j == 7) {
											row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
											vl = row.getCell(j).getStringCellValue().trim();
											if (vl.equals("")) {
												System.out.println("null cell:" + file2.getName() + ":" + i);
												skip = true;
												break;
											}
											ms.setMemberCode(vl);
											ExecuteResult<Long> rt = memberBaseInfoService.getMemberIdByCode(vl);
											if (rt.isSuccess()) {
												ms.setMemberId(rt.getResult());
												if(rt.getResult()==null) {
													System.out.println("null getMemberIdByCode:" + file2.getName() + ":" + i);
													skip = true;
													break;
												}
											}else {
												System.out.println("null getMemberIdByCode:" + file2.getName() + ":" + i);
												skip = true;
												break;
											}
										}
										if (j == 8) {
											vl = row.getCell(j).getStringCellValue().trim();
											ms.setMemberName(vl);
										}
										if (j == 9) {
											vl = row.getCell(j).getStringCellValue().trim();
											ms.setArtificialPersonName(vl);
										}
									}
								}
								if(skip) {
									continue;
								}
								if (i == 1) {
									ss.append(
											"\n\n\nINSERT INTO `seller_meeting_info` (`meeting_no`, `seller_id`, `seller_name`, "
													+ "`meeting_title`, `meeting_start_time`, `meeting_cont`, `meeting_end_time`, "
													+ "`meeting_addr`, `delete_flag`, `create_id`, `create_name`, `create_time`, `modify_id`,"
													+ " `modify_name`, `modify_time`) VALUES ('" + mi.getMeetingNo()
													+ "', " + mi.getSellerId() + ", '" + mi.getSellerName() + "', '"
													+ mi.getMeetingTitle() + "', '"
													+ mttimeformat.format(mi.getMeetingStartTime()) + "', '"
													+ mi.getMeetingCont() + "', '"
													+ mttimeformat.format(mi.getMeetingEndTime()) + "', '"
													+ mi.getMeetingAddr()
													+ "', 0, 0, 'sys', now(), 0, 'sys', now());\n");
								}
								ss.append("INSERT INTO `seller_meeting_sign` (`meeting_no`, `seller_id`, `seller_name`,"
										+ " `seller_code`, `meeting_title`, `meeting_start_time`, `meeting_end_time`,"
										+ " `meeting_addr`, `member_id`, `member_code`, `member_name`, "
										+ "`artificial_person_name`, `create_id`, `create_name`, `create_time`) VALUES ('"
										+ ms.getMeetingNo() + "', " + ms.getSellerId() + ", '" + ms.getSellerName()
										+ "', '" + ms.getSellerCode() + "', '" + ms.getMeetingTitle() + "', '"
										+ mttimeformat.format(ms.getMeetingStartTime()) + "', '"
										+ mttimeformat.format(ms.getMeetingEndTime()) + "', '" + ms.getMeetingAddr()
										+ "', " + ms.getMemberId() + ",'" + ms.getMemberCode() + "','"
										+ ms.getMemberName() + "','" + ms.getArtificialPersonName()
										+ "', 0, 'sys', now());\n");
							}
						}
					}
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(ss.toString());
		}
	}
}
