package framework.utils.readers;

import static framework.common.AppConfigConstants.EXCEL_PATH;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 
 * @author Room Manager Team
 *
 */
public class ExcelReader {
	private Workbook workBook;

	/**
	 * This method sets the path when the file is available
	 * @param path
	 */
	public ExcelReader(String path) {
		try {
			workBook = Workbook.getWorkbook(new File(System.getProperty("user.dir") + EXCEL_PATH + path));
		} catch (BiffException | IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * This method reads a excel file
	 * @param nameSheet: Name of Sheet
	 * @return List with hashMap with all data of a sheet
	 */
	public List<Map<String, String>> getMapValues(String nameSheet) {
		String key, value;
		List<Map<String, String>> values = new LinkedList<>();
		Sheet sheet = workBook.getSheet(nameSheet);
		for (int i = 1; i < sheet.getRows(); i++) {
			Map<String, String> mapDataSheet = new HashMap<String, String>();
			for (int j = 0; j < sheet.getColumns(); j++) {
				key = sheet.getCell (j, 0).getContents();
				value = sheet.getCell (j, i).getContents();
				mapDataSheet.put(key, value);
			}
			values.add(mapDataSheet);
		}
		return values;
	}
	
	/**
	 * This method reads a excel file
	 * @param nameSheet: Name of Sheet
	 * @return Matrix with all data of a sheet
	 */
	public Object[][] getObjectValues(String nameSheet) {
		Sheet sheet = workBook.getSheet(nameSheet);
		Object[][] values = new Object[sheet.getRows() - 1][sheet.getColumns()];
		for (int i = 1; i < sheet.getRows(); i++) {
			for (int j = 0; j < sheet.getColumns(); j++) {
				values[i - 1][j] = sheet.getCell (j, i).getContents();
			}
		}
		return values;
	}
}
