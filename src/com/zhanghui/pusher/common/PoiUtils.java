package com.zhanghui.pusher.common;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.zhanghui.pusher.template.XlsxTemplate;

public class PoiUtils {
	public static XSSFWorkbook loadTaskActionInfoTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.TEMP_TASK_ACTION_INFO));
		return wb;
	}
	public static XSSFWorkbook loadActiveUserTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.ACTIVE_USER));
		return wb;
	}
	public static XSSFWorkbook loadTopAppTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.TOP_APP));
		return wb;
	}
	public static XSSFWorkbook loadTotalDataTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.TOTAL_DATA));
		return wb;
	}
	public static XSSFWorkbook loadAdOwnerDataTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.ADOWNER_DATA));
		return wb;
	}
	public static XSSFWorkbook loadCountryDataTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.COUNTRY_DATA));
		return wb;
	}
	public static XSSFWorkbook loadAppIncomeDetailTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.APP_INCOME_DETAIL));
		return wb;
	}
	public static XSSFWorkbook loadAppIncomeTotalTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.APP_INCOME_TOTAL));
		return wb;
	}
	public static XSSFWorkbook loadPvUvDataTemplate() throws InvalidFormatException, IOException{
		XSSFWorkbook wb = (XSSFWorkbook)WorkbookFactory.create(XlsxTemplate.loadTemplate(XlsxTemplate.PVUV_DATA));
		return wb;
	}
	public static void createAndWriteCells(Row row, Object[] datas){
		int cellIdx = 0;
		for(int i=0;i<datas.length;cellIdx++, i+=2){
			Cell cell = row.createCell(cellIdx);
			if(datas[i] == String.class){
				cell.setCellValue((String)datas[i+1]);
			}else if(datas[i] == Date.class){
				cell.setCellValue((Date)datas[i+1]);
			}else if(datas[i] == Calendar.class){
				cell.setCellValue((Calendar)datas[i+1]);
			}else if(datas[i] == double.class){
				cell.setCellValue((Double)datas[i+1]);
			}else if(datas[i] == RichTextString.class){
				cell.setCellValue((RichTextString)datas[i+1]);
			}else if(datas[i] == boolean.class){
				cell.setCellValue((Boolean)datas[i+1]);
			}
		}
	}
}
