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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title  Verify on search page if filter by "resource"
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenSelectResourceRoomsThatMatchAreFiltered {
	SearchPage search = new SearchPage();
	ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Search");
	String resourceName = testData.get(1).get("Resource");
	
	@Test(groups = "FUNCTIONAL")
	public void testWhenSelectResourceRoomsThatMatchAreFiltered() throws BiffException, IOException {
		HomeTabletPage home = new HomeTabletPage();
		
		LinkedList<String> resCond = RootRestMethods.getRoomNamesByResource(resourceName);
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
			    .selectResource(resourceName);
		Assert.assertTrue(search.roomsInList(resCond));
	}
	
	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
