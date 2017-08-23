package com.icinfo.cs.es.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.icinfo.cs.common.utils.DateUtil;
import com.icinfo.cs.common.utils.StringUtil;
import com.icinfo.cs.es.dto.PanoramaResultDto;
import com.icinfo.cs.ext.dto.MidBaseInfoDto;

@Component
public class ExcelFileUtil {

	public HSSFWorkbook write(List<PanoramaResultDto> list, String year) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("企业信息综合查询");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("统一代码/注册号");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("成立日期");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("年度");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("年报状态");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("年报方式");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("年报日期");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("最近修改日期");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("当前年报状态");
		cell.setCellStyle(style);
		cell = row.createCell(10);
		cell.setCellValue("法定代表人/负责人");
		cell.setCellStyle(style);
		cell = row.createCell(11);
		cell.setCellValue("负责人电话");
		cell.setCellStyle(style);
		cell = row.createCell(12);
		cell.setCellValue("企业联络员");
		cell.setCellStyle(style);
		cell = row.createCell(13);
		cell.setCellValue("联络员电话");
		cell.setCellStyle(style);
		cell = row.createCell(14);
		cell.setCellValue("注册资本(万元)");
		cell.setCellStyle(style);
		cell = row.createCell(15);
		cell.setCellValue("企业类型");
		cell.setCellStyle(style);
		cell = row.createCell(16);
		cell.setCellValue("行业");
		cell.setCellStyle(style);
		cell = row.createCell(17);
		cell.setCellValue("住所地");
		cell.setCellStyle(style);
		cell = row.createCell(18);
		cell.setCellValue("登记机关");
		cell.setCellStyle(style);
		cell = row.createCell(19);
		cell.setCellValue("管辖单位");
		cell.setCellStyle(style);
		cell = row.createCell(20);
		cell.setCellValue("片区/商圈");
		cell.setCellStyle(style);
		cell = row.createCell(21);
		cell.setCellValue("登记状态");
		cell.setCellStyle(style);
		cell = row.createCell(22);
		cell.setCellValue("是否列入经营异常");
		cell.setCellStyle(style);
		cell = row.createCell(23);
		cell.setCellValue("是否列入严重违法失信");
		cell.setCellStyle(style);
		cell = row.createCell(23);
		cell.setCellValue("是否正在简易注销公告");
		cell.setCellStyle(style);

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < list.size(); i++) {
			if (i == 20000) {
				break;
			}
			row = sheet.createRow(i + 1);
			PanoramaResultDto panoramaResultDto = (PanoramaResultDto) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(i + 1);
			String uniscid = panoramaResultDto.getUniscid();
			if (!StringUtil.isEmpty(uniscid)) {
				row.createCell(1).setCellValue(uniscid);
			} else {
				row.createCell(1).setCellValue(panoramaResultDto.getRegNO());
			}
			row.createCell(2).setCellValue(panoramaResultDto.getEntName());
			row.createCell(3).setCellValue(panoramaResultDto.getEstDate());
			row.createCell(4).setCellValue(year);

			String isOpan = panoramaResultDto.getIsOpan();//经营异常
			String isSerVio = panoramaResultDto.getIsSerVio();//严重违法
			String isSim = panoramaResultDto.getIsSim();//简易注销

			if ("2022".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2022());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2022());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2022());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2022());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2022());
				row.createCell(9).setCellValue(yrRepStateCN);

			} else if ("2021".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2021());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2021());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2021());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2021());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2021());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2020".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2020());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2020());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2020());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2020());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2020());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2019".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2019());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2019());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2019());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2019());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2019());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2018".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2018());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2018());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2018());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2018());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2018());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2017".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2017());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2017());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2017());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2017());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2017());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2016".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2016());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2016());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2016());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2016());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2016());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2015".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2015());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2015());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2015());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2015());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2015());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2014".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2014());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2014());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2014());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2014());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2014());
				row.createCell(9).setCellValue(yrRepStateCN);
			} else if ("2013".equals(year)) {
				String yrIsRepCN = transYrIsRepCN(panoramaResultDto.getYrIsRep2013());
				row.createCell(5).setCellValue(yrIsRepCN);
				String yrRepModeCN = transYrRepModeCN(panoramaResultDto.getYrRepMode2013());
				row.createCell(6).setCellValue(yrRepModeCN);
				row.createCell(7).setCellValue(panoramaResultDto.getYrFirRepTime2013());
				row.createCell(8).setCellValue(panoramaResultDto.getYrRecRepTime2013());
				String yrRepStateCN = transYrRepStateCN(panoramaResultDto.getYrRepState2013());
				row.createCell(9).setCellValue(yrRepStateCN);
			}
			row.createCell(10).setCellValue(panoramaResultDto.getLeRep());
			row.createCell(11).setCellValue(panoramaResultDto.getTel());
			row.createCell(12).setCellValue(panoramaResultDto.getLiaName());
			row.createCell(13).setCellValue(panoramaResultDto.getLiaTel());
			row.createCell(14).setCellValue(panoramaResultDto.getRegCap());
			row.createCell(15).setCellValue(panoramaResultDto.getEntTypeName());
			row.createCell(16).setCellValue(panoramaResultDto.getIndustryCoName());
			row.createCell(17).setCellValue(panoramaResultDto.getDom());
			row.createCell(18).setCellValue(panoramaResultDto.getRegOrgName());
			row.createCell(19).setCellValue(panoramaResultDto.getLocalAdmName());
			row.createCell(20).setCellValue(panoramaResultDto.getSliceNOName());
			String regState = transRegStateCN(panoramaResultDto.getRegState());
			row.createCell(21).setCellValue(regState);
			if ("Y".equals(isOpan)) {
				row.createCell(22).setCellValue("是");
			}else if("N".equals(isOpan)){
				row.createCell(22).setCellValue("否");
			}else{
				row.createCell(22).setCellValue("-");
			}
			if ("Y".equals(isSerVio)) {
				row.createCell(23).setCellValue("是");
			}else if("N".equals(isSerVio)){
				row.createCell(23).setCellValue("否");
			}else{
				row.createCell(23).setCellValue("-");
			}
			if ("Y".equals(isSim)) {
				row.createCell(24).setCellValue("是");
			}else if("N".equals(isSim)){
				row.createCell(24).setCellValue("否");
			}else{
				row.createCell(24).setCellValue("-");
			}
		}
		return wb;

	}

	/**
	 * 年报方式编码转换中文
	 * 
	 * @param yrRepMode
	 * @return
	 */
	private String transYrRepModeCN(String yrRepMode) {
		String yrRepModeCN;
		if ("5".equals(yrRepMode)) {
			yrRepModeCN = "数字证书";
		} else if ("2".equals(yrRepMode)) {
			yrRepModeCN = "联络员";
		} else if ("6".equals(yrRepMode)) {
			yrRepModeCN = "纸质报告";
		} else if ("4".equals(yrRepMode)) {
			yrRepModeCN = "手机APP";
		} else {
			yrRepModeCN = "-";
		}
		return yrRepModeCN;
	}

	/**
	 * 年报状态编码转换中文
	 * 
	 * @param yrIsRep
	 * @return
	 */
	private String transYrIsRepCN(String yrIsRep) {
		String yrIsRepCN;
		if ("0".equals(yrIsRep)) {
			yrIsRepCN = "未年报";
		} else if ("1".equals(yrIsRep)) {
			yrIsRepCN = "已年报";
		} else if ("2".equals(yrIsRep)) {
			yrIsRepCN = "已年报(逾期)";
		} else {
			yrIsRepCN = "-";
		}
		return yrIsRepCN;
	}

	/**
	 * 年报状态编码转换中文
	 * 
	 * @param yrRepState
	 * @return
	 */
	private String transYrRepStateCN(String yrRepState) {
		String yrRepStateCN;
		if ("00".equals(yrRepState)) {
			yrRepStateCN = "未公示";
		} else if ("10".equals(yrRepState)) {
			yrRepStateCN = "已公示";
		} else if ("12".equals(yrRepState)) {
			yrRepStateCN = "已公示(敏感词待审核)";
		} else if ("11".equals(yrRepState)) {
			yrRepStateCN = "已公示(敏感词通过)";
		} else if ("13".equals(yrRepState)) {
			yrRepStateCN = "已公示(敏感词不通过)";
		} else if ("20".equals(yrRepState)) {
			yrRepStateCN = "待修改";
		} else {
			yrRepStateCN = "-";
		}
		return yrRepStateCN;
	}

	/**
	 * 登记状态编码转换中文
	 * 
	 * @param regState
	 * @return
	 */
	private String transRegStateCN(String regState) {
		String regStateCN;
		if ("K".equals(regState) || "B".equals(regState) || "A".equals(regState) || "DA".equals(regState)
				|| "X".equals(regState)) {
			regStateCN = "存续";
		} else if ("XX".equals(regState) || "DX".equals(regState)) {
			regStateCN = "注销";
		} else if ("C".equals(regState)) {
			regStateCN = "撤销";
		} else if ("D".equals(regState)) {
			regStateCN = "吊销";
		} else if ("Q".equals(regState)) {
			regStateCN = "迁出";
		} else {
			regStateCN = "-";
		}
		return regStateCN;
	}
	
	

	/**
	 * 
	 * 描述: 异常名录批列入导出excel
	 * @auther gaojinling
	 * @date 2017年7月3日 
	 * @param list
	 * @param year
	 * @return
	 */
	public HSSFWorkbook batchOpanomalyWrite(List<MidBaseInfoDto> list) {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("可批量列入异常列表");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("年度");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("统一代码/注册号");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("企业名称");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("类型");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("成立日期");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("个转企");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("个转企日期");
		cell.setCellStyle(style);
		cell = row.createCell(8);
		cell.setCellValue("登记机关");
		cell.setCellStyle(style);
		cell = row.createCell(9);
		cell.setCellValue("管辖单位");
		cell.setCellStyle(style);

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < list.size(); i++) {
			if (i == 20000) {
				break;
			}
			row = sheet.createRow(i + 1);
			MidBaseInfoDto midBaseInfoDto = (MidBaseInfoDto) list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(i + 1);
			String uniscid = midBaseInfoDto.getUniCode();
			if (!StringUtil.isEmpty(uniscid)) {
				row.createCell(2).setCellValue(uniscid);
			} else {
				row.createCell(2).setCellValue(midBaseInfoDto.getRegNO());
			}
			row.createCell(1).setCellValue(midBaseInfoDto.getYear());
			row.createCell(3).setCellValue(midBaseInfoDto.getEntName());
			row.createCell(4).setCellValue(getBatchEntType(midBaseInfoDto.getBatchEntType()));
			row.createCell(5).setCellValue(DateUtil.dateToString(midBaseInfoDto.getEstDate(), "YYYY-MM-dd"));
			if(StringUtil.isNotBlank(midBaseInfoDto.getIsIndivid())){
				row.createCell(6).setCellValue("1".equals(midBaseInfoDto.getIsIndivid()) ? "是" : "否");
			}else{
				row.createCell(6).setCellValue("否");
			}
			row.createCell(7).setCellValue(DateUtil.dateToString(midBaseInfoDto.getIndividDate(), "YYYY-MM-dd"));
			row.createCell(8).setCellValue(midBaseInfoDto.getRegOrgName());
			row.createCell(9).setCellValue(midBaseInfoDto.getLocalAdmName());
		}
		return wb;

	}
	
	
	public  String getBatchEntType(String batchEntType){
		if(StringUtil.isBlank(batchEntType)){
			return "";
		}
		if("2".equals(batchEntType)){
			return "农专社";
		}else if("3".equals(batchEntType)){
			return "外资企业";
		}else if("1".equals(batchEntType)){
			return "内资企业";
		}
		return batchEntType; 
	}
}
