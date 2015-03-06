package tests.admin.ConferenceRoomResources;

//import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.ConferenceRoomResources;
/**
 * 
 * @author administrator
 * Verify that the resources added are inserted as columns in room grid
 */
public class ResourcesAddedAreInsertedAsColumns {

	@Test(groups = {"Acceptance"})
	public void testResourcesAddedAreInsertedAsColumns() {

		 
		ConferenceRoomResources conferenceRoomResources = new ConferenceRoomResources();
		String expected = conferenceRoomResources.getResourceName();
		conferenceRoomResources.clickAddAvailableResourceBtn();
		//for(i=0;;)
		
		//Assert.assertEquals(expected, list.get("Name"));
	}
}
