package framework.common;

import framework.pages.tablet.SchedulePage;

/**
 * Description This class is to delete the meetings created
 * @author Asael Calizaya
 *
 */
public class PostConditions {
	private SchedulePage schedule;
	
	/**
	 * [AC] This class delete a meeting
	 * @param nameMeeting: name of meeting to delete
	 * @return: This page, to use the same method repeated times
	 */
	public PostConditions deleteMeeting(String nameMeeting, String password) {
		schedule
				.clickOverMeetingCreated(nameMeeting)
				.clickRemoveBtn()
				.setPasswordTxtBox(password)
				.clickOkButton()
				.isMessageMeetingDeletedDisplayed();
		return this;
	}
	
	public void goHome() {
		schedule.clickBackBtn();
	}
}
