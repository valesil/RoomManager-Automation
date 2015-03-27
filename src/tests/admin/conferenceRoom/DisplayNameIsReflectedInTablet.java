package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.UIMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC10: Verify that the changes on Displayed Name of rooms are reflected in tablet app 
 * @author Ruben Blanco
 * 
 */
public class DisplayNameIsReflectedInTablet {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName = testData1.get(0).get("DisplayName");	  	  
	String newDisplayName = testData1.get(0).get("NewDisplayName");	  	  
	
	@Test(groups = {"FUNCTIONAL"})
	public void testChangesInDisplayNameAreReflectedIntablet() throws InterruptedException, IOException, BiffException {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setDisplayName(newDisplayName)
			.clickSaveBtn();		
		
		String SavedDisplayName = confRoomPage.getRoomDisplayName(newDisplayName);
		HomeTabletPage home = new HomeTabletPage();
		SettingsPage sett = home.clickSettingsBtn();
		sett.selectRoom(newDisplayName); 	   
		String tabletDisplayName = home.getRoomDisplayNameLbl();
		Assert.assertEquals(SavedDisplayName, tabletDisplayName);
	}
	
	@AfterClass
	public void preconditions() throws InterruptedException, BiffException, IOException{
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(newDisplayName);
		roomInf
			.setDisplayName(displayName)
			.clickSaveBtn();
		UIMethods.refresh();
	}
}
