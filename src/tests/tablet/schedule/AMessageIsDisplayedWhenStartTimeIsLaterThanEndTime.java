package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.utils.readers.ExcelReader;

/**
 * @title  TC41: Verify that a message is displayed if the start time is later than end time
 * @author Jose Cabrera
 */
public class AMessageIsDisplayedWhenStartTimeIsLaterThanEndTime {
	private SchedulePage schedulePage;

	@Test(groups = "UI")
	public void testMessageIsDisplayedStartTimeIsLaterThanEndTime() {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
		String minStartTime = meetingData.get(1).get("Start time");
		String minEndTime = meetingData.get(1).get("End time");
		HomeTabletPage homePage = new HomeTabletPage();
		schedulePage = homePage.clickScheduleBtn();
		schedulePage.setStartTimeDate(minEndTime)
				.setEndTimeDate(minStartTime)
				.clickCreateBtn();
		Assert.assertTrue(schedulePage.isMessageOfTimeErrorDisplayed());
	}

	@AfterClass(groups = "UI")
	public void toHome() {
		schedulePage.clickBackBtn();
	}
}
