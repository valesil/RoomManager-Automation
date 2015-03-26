package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title TC15: Verify on search page that when "Clear" button  is pressed 
 * all fields are cleaned
 * @author Jose Cabrera
 */
public class WhenPressClearAllCriteriasAreCleaned {
	ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Search");
	String roomName = testData.get(0).get("Room Name");
	SearchPage search = new SearchPage();

	@Test(groups = "UI")
	public void testPressClearAllCriteriasAreCleaned () throws JSONException, 
	MalformedURLException, IOException {
		HomeTabletPage home = new HomeTabletPage();

		LinkedList<String> condition = RootRestMethods.getNamesAssociatedResources(roomName);
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.clickClearBtn();
		Assert.assertFalse(search.resourcesAreSelected(condition)&&
				search.allFiltersAreCleaned());
	}

	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}

