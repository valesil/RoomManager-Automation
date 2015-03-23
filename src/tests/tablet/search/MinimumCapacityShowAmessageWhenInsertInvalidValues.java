package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title  Verify that an error message is displayed when invalid values are 
 * inserted on "Minimum Capacity" field
 * @author Jose Cabrera
 */
public class MinimumCapacityShowAmessageWhenInsertInvalidValues {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	public RestMethods rooms = new RestMethods();
	
	@Test(groups = {"NEGATIVE"})
	public void testMinimumCapacityShowAmessageifInsertInvalidValues () throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String capacity = testData.get(0).get("Capacity");
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setMinimumCap(capacity);
		Assert.assertFalse(search.isEmptyMinimumCap());
	}

	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
}
