package tests.tablet.search;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.common.AppConfigConstants;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.utils.readers.ExcelReader;

/**
 * TC7: Verify on search page when you press over some room, 
 * the sheduler page is displayed of that room
 * @author Jose Cabrera
 */
public class WhenSelectRoomSchedulerofThisAreDisplayed {
	SearchPage searchPage;
	SchedulePage schedule;
	@Test(groups = "ACCEPTANCE")
	public void testSelectRoomSchedulerofThisAreDisplayed () throws InterruptedException {
		ExcelReader excelReader = new ExcelReader(AppConfigConstants.EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(1).get("Room Name");
		HomeTabletPage homePage = new HomeTabletPage();
		searchPage = homePage.clickSearchBtn()
				.clickCollapseAdvancedBtn();
		schedule = searchPage.selectRoom(roomName);
		Assert.assertTrue(schedule.isSchedulerLblDisplayed());
	}
	
	@AfterMethod
	public void toHome() {
		schedule.clickBackBtn();
	}
	
	
}
