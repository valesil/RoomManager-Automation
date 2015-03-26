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
 * @title TC3: Verify when put a letter on "Room Name" field, 
 * the rooms that match with that criteria is filtered
 * @author Jose Cabrera
 */
public class WhenPutLetterOnRoomNameFieldRoomsThatMatchAreFiltered {
	ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("Search");
	String roomName = testData.get(1).get("Room Name");
	SearchPage search = new SearchPage();
	
	@Test(groups = "ACCEPTANCE")
	public void testPutLetterOnRoomNameFieldRoomsAreFiltered ()throws BiffException, IOException {
		HomeTabletPage home = new HomeTabletPage();
		LinkedList<String> condition = RootRestMethods.getRoomsByName(roomName);
		search = home.clickSearchBtn()
			  .clickCollapseAdvancedBtn()
			  .setName(roomName);
		Assert.assertTrue(search.roomsInList(condition));
	}
	
	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}
}
