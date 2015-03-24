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
 * a) Verify that the tool allows blank space between text in displayed name of room
 * b) Verify that the tool allows blank space between text in code of room
 * @author Marco Llano
 *
 */
public class RoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode {
	RoomsPage roomsPage;
	RoomInfoPage infoPage;

	@Test(groups = {"NEGATIVE"})
	public void testRoomAllowsBlankSpaceBetweenTextForDisplayNameAndCode() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomDisplayName = testData.get(0).get("Room Name");

		//Insert blank space between text in room display name and room code
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName);
		roomsPage = infoPage.setDisplayName(roomDisplayName + " " + roomDisplayName)
							.setRoomCode(roomDisplayName + " " + roomDisplayName)
							.clickSaveBtn();

		//a) Verify if exist blank space between text for room display name
		infoPage = roomsPage.doubleClickOverRoomName(roomDisplayName + " " + roomDisplayName);
		Assert.assertTrue(infoPage.getRoomDisplayName().contains(roomDisplayName + " " + roomDisplayName));
		
		//b) Verify if exist blank space between text for room code
		Assert.assertEquals(infoPage.getRoomCode(), roomDisplayName + " " + roomDisplayName);
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomDisplayName = testData.get(0).get("Room Name");
		roomsPage = infoPage.setDisplayName(roomDisplayName).clickSaveBtn();
	}
}
