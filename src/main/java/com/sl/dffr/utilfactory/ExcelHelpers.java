package com.sl.dffr.utilfactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelpers {

	private static FileInputStream fis;
	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFRow row;
	private static XSSFCell cell;

	public static void setCellData(String filePath, String sheetName, int rowNumber, int cellNumber, String text)
			throws IOException {

		fis = new FileInputStream(filePath);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNumber);
		if (row == null) {
			row = sheet.createRow(rowNumber);
		}
		cell = row.getCell(cellNumber); // 4-1
		if (cell == null) {
			cell = row.createCell(cellNumber);
		}
		cell.setCellValue(text);
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.close();
	}

	public static String getCellData(String filePath, String sheetName, int rowNumber, int cellNumber)
			throws IOException {
		fis = new FileInputStream(filePath);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNumber);
		cell = row.getCell(cellNumber);
		return cell.getStringCellValue();
	}

	public static String getCellData(String sheetName, int rowNumber, int cellNumber) throws IOException {
		fis = new FileInputStream(ConfigReader.readValue("TESTDATA_EXCEL"));
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNumber);
		cell = row.getCell(cellNumber);
		return cell.getStringCellValue();
	}

	public static String getCellData(char col, int rowNumber) throws IOException {
		fis = new FileInputStream(ConfigReader.readValue("TESTDATA_EXCEL"));
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet("Sheet1");
		row = sheet.getRow(rowNumber);
		cell = row.getCell(getCellNumber(col));
		return cell.getStringCellValue();
	}

	private static int getCellNumber(char col) {
		return Character.getNumericValue((char) ((int) Character.toUpperCase(col) - 17));
	}

}
