package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jxl.read.biff.BiffException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * a) Verify that the tool allows blank text for Room Capacity
 * b) Verify that the tool allows blank text for Room Code
 * @author Marco Llano
 *
 */
public class RoomAllowsBlankTextForRoomCodeAndCapacity {
	RoomsPage roomsPage;
	RoomInfoPage infoPage;
	
	@Test(groups = {"NEGATIVE"})
	public void testRoomAllowsBlankTextForRoomCodeAndCapacity() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomName = testData.get(0).get("Room Name");

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomName);
		roomsPage = infoPage.setRoomCapacity("").setRoomCode("").clickSaveBtn();

		//a, b)Verify if rooms capacity and code value are blank text
		infoPage = roomsPage.doubleClickOverRoomName(roomName);
		Assert.assertEquals(infoPage.getRoomCapacity(), "");
		Assert.assertEquals(infoPage.getRoomCode(), "");		
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		roomsPage = infoPage.clickCancelBtn();
	}
}
