package com.automation.support;

/**
 * 
 */

/**
 * @author IN00456
 *
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;

import com.automation.accelerators.ActionEngine;
import com.automation.accelerators.TestEngine;



@SuppressWarnings("all")
public class ExcelReader extends ActionEngine{

	public ActionEngine ae;
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;

	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null;
	private Column col = null;
	private HSSFCell cell = null;
	private String sheetName;

	public ExcelReader(String path,String sheetName) {
		//System.out.println("Im in Exc elcel readder class");
		//System.out.println(path);
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			this.sheetName = sheetName;
		//	System.out.println(this.sheetName);
			fis.close();
			//System.out.println("Im in Exc elcel readder class Ending");

		} catch (Exception e) {
			//e.printStackTrace();
		}

	}



	// returns the row count in a sheet

	/**
	 * 
	 * @param sheetName
	 * @return
	 */



	public int getRowCount(String sheetName) {
		System.out.println("Im in Exc elcel readder getrow count class");
		int index = workbook.getSheetIndex(sheetName);// (arg0)getSheetIndex
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			//System.out.println("row number is "+number);
			return number;
		}



	}
	public String getData(String rowName,String colName) throws Throwable{
	//	System.out.println("Im in Exc elcel readder get data class");
		int rowNum=-1;
		try{

			int index = workbook.getSheetIndex(sheetName);
			int rowNumber = -1;
			int colNumber = -1;
			boolean flag = false;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);


			for (int i = 2; i < sheet.getPhysicalNumberOfRows(); ) {
				try
				{
					row = sheet.getRow(i);


					if (row.getCell(0).toString().equalsIgnoreCase(rowName)) {
						rowNumber = i;

						break;
					}
					i=i+2;
				}
				catch(NullPointerException e)
				{
					continue;
				}
			}
			row = sheet.getRow(rowNumber-1);
			for (int j = 0; j <=row.getPhysicalNumberOfCells(); j++) {
				try
				{
					if (row.getCell(j).toString().equalsIgnoreCase(colName)) {
						colNumber = j;
						break;
					}
				}
				catch(NullPointerException e)
				{
					continue;
				}
			}
			if(colNumber==-1)
			{
				//failureReport(rowName,"Unable to find the column with name"+colName);
				this.reporter.failureReport(rowName,"Unable to find the column with name"+colName, appiumDriver);
			}
			row = sheet.getRow(rowNumber);
			if (row == null)
				return "";
			cell = row.getCell(colNumber);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue().trim();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
					|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue())
						.replaceFirst(".0", "");

				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR)))
							.substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/"
							+ cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

					// System.out.println(cellText);

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue()).trim();

		}
		catch(Exception e){

			//failureReport("Excel Data Reading", "row "+rowName+" or column "+colName +" does not exist in xls");
			this.reporter.failureReport("Excel Data Reading", "row "+rowName+" or column "+colName +" does not exist in xls", appiumDriver);
			TestEngine.LOG.error(e.toString());

			return "";
		}
	}
}
