package framework.rest; 

import java.util.LinkedList; 

import framework.rest.RestMethods; 

public class RootRestMethods {

	/** [J.C]
	 * @return This method get all rooms in existence in a LinkedList  <String> 
	 */
	public static LinkedList  <String> getAllRooms() {
		RestMethods getRooms = new RestMethods(); 
		return getRooms.response("rooms", "displayName", "String"); 
	}

	/** [J.C]
	 * @return This method create association between a room and resource
	 */
	public static void CreateAssociatedResource(String roomName, String fileJSON) {
		RestMethods getRoomID = new RestMethods(); 
		RestMethods createAssociatedResource = new RestMethods();
		String roomID = getRoomID.findAttributeValue("rooms", "String", "displayName", roomName, "_id");
		String resourceAssociated = "rooms/" + roomID + "/resources";
		createAssociatedResource.postAndPutMethod(resourceAssociated,fileJSON);
	}

	/** [J.C]
	 * @return This method return a names of Associated resources to a Room in a LinkedList  <String> 
	 */
	public static LinkedList <String> getNamesAssociatedResources(String roomName) {
		LinkedList <String> listResourcesRoomIds, listAllIdResources;
		LinkedList <String> listNames = new LinkedList  <String>(); 
		RestMethods getResourcesID = new RestMethods();
		RestMethods getRoomID = new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", "displayName", roomName, "_id");
		String resourceAssociated = "rooms/" + roomID + "/resources";
		listResourcesRoomIds = getResourcesID.response(resourceAssociated, "resourceId", "String");
		listAllIdResources = getAllIdResources();
		for(int pos = 0; pos<listResourcesRoomIds.size(); pos ++){
			for(int iterator = 0; iterator < listAllIdResources.size(); iterator ++ ){
				if(listResourcesRoomIds.get(pos).equals(listAllIdResources.get(iterator))){
					listNames.add(getRoomID
							.findAttributeValue("resources", "String", "_id", listResourcesRoomIds.get(pos), "customName"));
					System.out.println(listNames.get(pos));
				}
			}
		}
		return listNames;
	}
	public static LinkedList <String> getRoomsByName(String Criteria){
		LinkedList <String> listAllRooms = getAllRooms();
		LinkedList <String> listRooms = new LinkedList <String>(); 
		for(int pos = 0; pos<listAllRooms.size(); pos ++){
			if(listAllRooms.get(pos).contains(Criteria))
				listRooms.add(listAllRooms.get(pos));
		}
		return listRooms;
	}

	/** [J.C]
	 * @return This method return a names of resources in a LinkedList  <String> 
	 */
	public static LinkedList  <String> getAllIdResources() {
		RestMethods getResources = new RestMethods(); 
		return getResources.response("resources", "_id", "String"); 
	}

	/** [J.C]
	 * @return This method return a names of resources in a LinkedList  <String> 
	 */
	public static LinkedList  <String> getAllNameResources() {
		RestMethods getResources = new RestMethods(); 
		return getResources.response("resources", "customName", "String"); 
	}

	/** [J.C]
	 * @return This method delete a resource
	 */
	public static void deleteResource(String resourceName) {
		RestMethods getResourceID = new RestMethods(); 
		RestMethods deleteResource = new RestMethods(); 
		deleteResource.getAndDeleteMethods("DELETE", "resources/" 
				+ getResourceID.findAttributeValue("resources", "String", "name", resourceName, "_id")); 
	}

	/** [J.C]
	 * @return This method delete all resources
	 */
	public static void deleteResources(){
		LinkedList  <String> listNames = getAllNameResources(); 
		for(int iterator = 0; iterator < listNames.size(); iterator ++ ){
			deleteResource(listNames.get(iterator)); 
		}
	}

	/** [J.C]
	 * @return This method delete the association between a room and resource
	 */
	public static void deleteAssociatedResource(String roomName, String resourceName){
		RestMethods getResourceID = new RestMethods(); 
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getResourceAssociatedID = new RestMethods(); 
		RestMethods deleteAssociatedResource = new RestMethods(); 
		String resourceID = getResourceID.findAttributeValue
							("resources", "String", "name", resourceName, "_id");
		String roomID = getRoomID.findAttributeValue("rooms", "String", "displayName", roomName, "_id");
		String resourcesAssociated = "rooms/" + roomID + "/resources";
		String resourceAssociatedID = getResourceAssociatedID.findAttributeValue
									 (resourcesAssociated, "String", "resourceId", resourceID, "_id");
		deleteAssociatedResource.getAndDeleteMethods
								("DELETE", resourcesAssociated + "/" + resourceAssociatedID); 
	}

	/** [J.C]
	 * @return This method obtain info of an OutOfOrder selected
	 */
	public static String getOutOfOrderInfo(String roomName, String title, String attribute){
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getoutOfOrderInfo = new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", "displayName", roomName, "_id"); 
		String outOfOrders = "rooms/" + roomID + "/out-of-orders"; 
		if(attribute=="sendEmail"){
			return getoutOfOrderInfo.findAttributeValue(outOfOrders, "Boolean", "title", title, attribute);
		}
		return getoutOfOrderInfo.findAttributeValue(outOfOrders, "String", "title", title, attribute);
	}

	/** [J.C]
	 * @return This method obtain startDate of an OutOfOrder selected
	 */
	public static String getOutOfOrderStartDate(String roomName, String title){
		return getOutOfOrderInfo(roomName, title, "from");
	}

	/** [J.C]
	 * @return This method obtain endDate of an OutOfOrder selected
	 */
	public static String getOutOfOrderEndDate(String roomName, String title){
		return getOutOfOrderInfo(roomName, title, "to");
	}

	/** [J.C]
	 * @return This method obtain SendMail of an OutOfOrder selected
	 */
	public static String getOutOfOrderSendEmail(String roomName, String title){
		return getOutOfOrderInfo(roomName, title, "sendEmail");
	}

	/** [J.C]
	 * @return This method delete An OutOfOrder
	 */
	public static void deleteOutOfOrder(String roomName, String title){
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getoutOfOrderID = new RestMethods(); 
		RestMethods deleteOutOfOrder =  new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "String", "displayName", roomName, "_id"); 
		String outOfOrders = "rooms/" + roomID + "/out-of-orders"; 
		String outOfOrderID = getoutOfOrderID.findAttributeValue
							 (outOfOrders, "String", "title", title, "_id"); 
		deleteOutOfOrder.getAndDeleteMethods("DELETE", outOfOrders + "/" + outOfOrderID); 
	}

	/** [J.C]
	 * @return This method delete An OutOfOrder
	 */
	public static void deleteMeeting(String roomName, String meetingTitle){
		RestMethods getServicesID = new RestMethods();
		RestMethods getRoomID = new RestMethods();
		RestMethods getMeetingID = new RestMethods();
		RestMethods deleteMeeting = new RestMethods();
		String serviceID = getServicesID.findAttributeValue("services", "String","type", "exchange", "_id"); 
		String roomID = getRoomID.findAttributeValue("rooms","String", "displayName", roomName, "_id");
		String meetings = "services/" + serviceID + "/rooms/" + roomID + "/meetings";
		String meetingID = getMeetingID.findAttributeValue(meetings, "String", "title", meetingTitle, "_id"); 
		System.out.println(meetings + "/" + meetingID);
		deleteMeeting.getAndDeleteMethods("DELETE", meetings + "/" + meetingID); 
	}

	/** [J.C]
	 * @return This method obtain a list of elements by condition
	 */
	public static LinkedList  <String> getListByNumeric(String feature, String attribute, 
			String attributeValue, String attributeSearched){
		RestMethods restConds = new RestMethods(); 
		LinkedList  <String> list =  new LinkedList  <String>(); 
		LinkedList  <String> listAttributes = restConds.response(feature,"Int", attribute); 
		LinkedList  <String> listAttSearched = restConds.response(feature,"String", attributeSearched);
		for(int iterator = 0; iterator < listAttSearched.size(); iterator ++ ){
			if(listAttributes.get(iterator).equals(attributeValue)){
				list.add(listAttSearched.get(iterator)); 
			}
		}
		return list; 
	}

	/** [J.C]
	 * @return This method obtain a list of elements by condition
	 */
	public static LinkedList  <String> getListByString(String feature, String attribute, 
			String attributeValue, String attributeSearched){
		RestMethods restConds = new RestMethods(); 
		LinkedList  <String> list =  new LinkedList  <String>(); 
		LinkedList  <String> listAttributes = restConds.response(feature,"String", attribute); 
		LinkedList  <String> listAttSearched = restConds.response(feature,"String", attributeSearched);
		for(int iterator = 0; iterator < listAttSearched.size(); iterator ++ ){
			if(listAttributes.get(iterator).equals(attributeValue)){
				list.add(listAttSearched.get(iterator)); 
			}
		}
		return list; 
	}

	/** [J.C]
	 * @return This method obtain a list of common elements between two lists
	 */
	public static LinkedList <String> mergeLists(LinkedList <String> list1, LinkedList <String> list2){
		LinkedList <String> list = new LinkedList <String>();
		for(int iter1 = 0;iter1 < list1.size();iter1 ++ ){
			for(int iter2 = 0;iter2 < list2.size();iter2 ++ ){
				if(list1.get(iter1).equals(list2.get(iter2))){
					list.add(list1.get(iter1));
				}
			}
		}
		return list;
	}
}
