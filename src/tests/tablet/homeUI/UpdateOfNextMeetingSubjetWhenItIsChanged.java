package tests.tablet.homeUI;

import static tests.tablet.homeUI.HomeTabletSettings.createMeetingFromExcel;
import static tests.tablet.homeUI.HomeTabletSettings.getAuthenticationValue;
import static tests.tablet.homeUI.HomeTabletSettings.getMeetingSubject;
import static tests.tablet.homeUI.HomeTabletSettings.getRoomDisplayName;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;

/**
 * TC14: This test case verifies that next meeting subject is updated in {Next} Tile 
 * when it is updated.
 * @author Eliana Navia
 * 
 */
public class UpdateOfNextMeetingSubjetWhenItIsChanged {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;
	String password = getAuthenticationValue(1);
	String nextMeetingSubject = getMeetingSubject(1);
	String updateMeetingSubject = getMeetingSubject(2);

	@BeforeClass
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		createMeetingFromExcel(1);	
	}

	@Test (groups = {"ACCEPTANCE"})
	public void testRefreshOfNextMeetingNameWhenItIsUpdated() {

		schedulePage
		.clickOverMeetingCreated(nextMeetingSubject)
		.setSubjectTxtBox(updateMeetingSubject)
		.clickUpdateBtn()
		.confirmCredentials(password)
		.isMessageMeetingUpdatedDisplayed();

		schedulePage.clickBackBtn();

		String actualMeetingNameUpdated = homeTabletPage.getNextTileLbl();

		Assert.assertEquals(actualMeetingNameUpdated, updateMeetingSubject);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(getRoomDisplayName(), updateMeetingSubject, 
					getAuthenticationValue(0));
			log.info("The meeting:" + updateMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
