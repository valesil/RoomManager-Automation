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
 * @title  TC8: Verify on search page if filter by "location" and "Room Name", 
 * only the rooms that contains that especifications are displayed
 * @author Jose Cabrera
 */
public class WhenPutRoomNameFieldAndLocationRoomsThatMatchAreFiltered {
	ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Search");
	String roomName = testData.get(1).get("Room Name");
	SearchPage search = new SearchPage();

	@Test(groups={"FUNCTIONAL"})
	public void testRoomNameFieldAndLocationRoomsThatMatchAreFiltered() 
			throws BiffException, IOException{
		HomeTabletPage home=new HomeTabletPage();

		LinkedList<String> roomNameCond = RootRestMethods.getRoomsByName(roomName);
		String location = testData.get(1).get("Location");
		LinkedList<String> locationCond = RootRestMethods
				.getListByNumeric("rooms","location",location, "displayName");
		search = home.clickSearchBtn()
				.clickCollapseAdvancedBtn()
				.setName(roomName)
				.setLocation(location);
		Assert.assertTrue(search.roomsInList(RootRestMethods.mergeLists(roomNameCond, locationCond)));
	}

	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
