package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.utils.readers.ExcelReader;

/**
 * TC41: Verify that clicking the left arrow displays the home page
 * @author Asael Calizaya
 *
 */
public class ComeBackToHomeWhenClickingOnBack {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String roomName = meetingData.get(1).get("Room");
	
	@Test(groups = "UI")
	public void testComeBackToHomeWhenClickBackButton() {
		HomeTabletPage home = new HomeTabletPage();
		String actual = home
				.clickScheduleBtn()
				.clickBackBtn()
				.getRoomDisplayNameLbl();

		Assert.assertEquals(roomName, actual);
	}
}
