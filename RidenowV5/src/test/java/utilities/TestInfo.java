package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestInfo {
	private static Map<String, List<String>> data;
	private static String path = "testdata/TestCases.xlsx";
	private static final String ERROR_MSG ="No data do display.<br>To solve the issue:<br>"
			+ "1- Make sure the sheet is updated - the data is not missing from the sheet.<br>"
			+ "2- Test case name in automation file matches the test case name in the sheet.<br>";
	private TestInfo() {}
	
	public static void readTestData() {
		importTestCases(path);
	}
	
	public  static List<String> getTestInformation(String testName){
		if (data.get(testName)== null)
			return null;
		return data.get(testName);
	}
	public static String getTestSteps(String testName) {
		if (data.get(testName)== null)
			return ERROR_MSG;
		return getData(testName, 3);
	}
	public static String getTestDescription(String testName) {
		if (data.get(testName)== null)
			return ERROR_MSG;
		return getData(testName, 2);
	}
	public static String getTestStatus(String testName) {
		if (data.get(testName)== null)
			return ERROR_MSG;
		return getData(testName, 5);
	}
	
	public static String getTestRemarks(String testName) {
		if (data.get(testName)== null)
			return ERROR_MSG;
		return getData(testName, 6);
	}
	
	public static String getTestExpectedResult(String testName) {
		if (data.get(testName)== null)
			return ERROR_MSG;
		return getData(testName, 4);
	}
	private static String getData(String name, int index) {
		List<String> list = data.get(name);
		if(index < 0 || index > list.size())
			return ERROR_MSG;

		
		List<String> tempList = Arrays.asList(list.get(index).split("\n"));
		return String.join("<br>", tempList);
	}
	private static void importTestCases(String filePath) {
		try {
			data = new HashMap<String, List<String>>();
			FileInputStream file = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(file);
			int numberOfSheets = workbook.getNumberOfSheets();
			
			for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
	            Sheet sheet = workbook.getSheetAt(sheetIndex);
	            if (sheet.getLastRowNum() > 0) { // Check if the sheet has data
	                readData(data, sheet);
	            }
	        }
			
			
//			Sheet sheet = workbook.getSheetAt(0);
//			readData(data, sheet);
//			System.out.println(data);
			workbook.close();
			file.close();

		}catch(InvalidFormatException e){
			e.printStackTrace();
		}
		catch (EncryptedDocumentException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void readData(Map<String, List<String>> data, Sheet sheet) {
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			String key = null;
			List<String> list = new ArrayList<String>();;
			for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
				String value = getCellValue1(row.getCell(colNum));
				list.add(value);
				if (colNum == 1)
					key = value;
			}
			data.put(key, list);

		}
//		System.out.println(data);
	}

	private static String getCellValue1(Cell cell) {
		try {
		CellType cellType = cell.getCellTypeEnum();
		switch (cellType) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				return date.toString();
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case BLANK:
			return "";
		// Handle other cell types as needed
		default:
			return Objects.toString(cell.getCellFormula(), "");
		}
		}catch (NullPointerException e) {
			return ERROR_MSG;
		}
	}


}
