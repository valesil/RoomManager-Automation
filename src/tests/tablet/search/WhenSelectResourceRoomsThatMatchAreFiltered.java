package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title  Verify on search page if filter by "resource"
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenSelectResourceRoomsThatMatchAreFiltered {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	
	@Test(groups={"FUNCTIONAL"})
	public void testWhenSelectResourceRoomsThatMatchAreFiltered() throws BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String resourceName = testData.get(1).get("Resource");
		LinkedList<String> resCond = RootRestMethods.getRoomNamesByResource(resourceName);
		search=home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
			    .selectResource(resourceName);
		Assert.assertTrue(search.roomsInList(resCond));
	}
	
	@AfterClass
	public void toHome() {
		search.clickBackBtn();
	}
}
