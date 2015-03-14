package framework.rest; 

import java.util.LinkedList; 

import framework.rest.RestMethods; 

public class RootRestMethods {
	
	/** [J.C]
	 * @return This method get all rooms in existence in a LinkedList  <String> 
	 */
	public static LinkedList  <String> getAllRooms() {
		RestMethods getRooms = new RestMethods(); 
		return getRooms.responseString("rooms", "displayName"); 
	}
	
	/** [J.C]
	 * @return This method create association between a room and resource
	 */
	public static void CreateAssociatedResource(String roomName, String fileJSON) {
		RestMethods getRoomID = new RestMethods(); 
		RestMethods createAssociatedResource = new RestMethods();
		String roomID = getRoomID.findAttributeValue("rooms", "displayName", roomName, "_id");
		String resourceAssociated = "rooms/"+roomID+"/resources";
		createAssociatedResource.postAndPutMethod(resourceAssociated,fileJSON);
	}
	
	/** [J.C]
	 * @return This method return a names of resources in a LinkedList  <String> 
	 */
	public static LinkedList  <String> getAllResources() {
		RestMethods getResources = new RestMethods(); 
		return getResources.responseString("resources", "customName"); 
	}
	
	/** [J.C]
	 * @return This method delete a resource
	 */
	public static void deleteResource(String resourceName) {
		RestMethods getResourceID = new RestMethods(); 
		RestMethods deleteResource = new RestMethods(); 
		deleteResource.getAndDeleteMethods("DELETE", "resources/" 
		+ getResourceID.findAttributeValue("resources", "name", resourceName, "_id")); 
	}
	
	/** [J.C]
	 * @return This method delete all resources
	 */
	public static void deleteResources(){
		LinkedList  <String> listNames = getAllResources(); 
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
		String resourceID = getResourceID.findAttributeValue("resources", "name", resourceName, "_id");
		String roomID = getRoomID.findAttributeValue("rooms", "displayName", roomName, "_id");
		String resourcesAssociated = "rooms/" + roomID + "/resources";
		String resourceAssociatedID = getResourceAssociatedID.findAttributeValue(resourcesAssociated, "resourceId", resourceID, "_id");
		deleteAssociatedResource.getAndDeleteMethods("DELETE", resourcesAssociated + "/" + resourceAssociatedID); 
	}
	
	/** [J.C]
	 * @return This method delete An OutOfOrder
	 */
	public static void deleteOutOfOrder(String roomName, String title){
		RestMethods getRoomID = new RestMethods(); 
		RestMethods getoutOfOrderID = new RestMethods(); 
		RestMethods deleteOutOfOrder =  new RestMethods(); 
		String roomID = getRoomID.findAttributeValue("rooms", "displayName", roomName, "_id"); 
		String outOfOrders = "rooms/" + roomID + "/out-of-orders"; 
		String outOfOrderID = getoutOfOrderID.findAttributeValue(outOfOrders, "title", title, "_id"); 
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
		String serviceID = getServicesID.findAttributeValue("services", "type", "exchange", "_id"); 
		String roomID = getRoomID.findAttributeValue("rooms", "displayName", roomName, "_id");
		String meetings = "services/" + serviceID + "/rooms/" + roomID + "/meetings";
		String meetingID = getMeetingID.findAttributeValue(meetings, "title", meetingTitle, "_id"); 
		System.out.println(meetings + "/" + meetingID);
		deleteMeeting.getAndDeleteMethods("DELETE", meetings + "/" + meetingID); 
	}

	/** [J.C]
	 * @return This method obtain a list of elements by condition
	 */
	public LinkedList  <String> getListByNumeric(String type, String condition){
		RestMethods restConds = new RestMethods(); 
		LinkedList  <String> list =  new LinkedList  <String>(); 
		LinkedList  <String> listRoomConds = restConds.responseNum(type, condition); 
		LinkedList  <String> listRoomNames = getAllRooms(); 
		for(int iterator = 0; iterator < listRoomNames.size(); iterator ++ ){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator)); 
			}
		}
		return list; 
	}
	
	/** [J.C]
	 * @return This method obtain a list of elements by condition
	 */
	public LinkedList  <String> getListByString(String type, String condition){
		RestMethods restConds = new RestMethods(); 
		LinkedList  <String> list =  new LinkedList  <String>(); 
		LinkedList  <String> listRoomConds = restConds.responseString(type, condition); 
		LinkedList  <String> listRoomNames = getAllRooms(); 
		for(int iterator = 0; iterator < listRoomNames.size(); iterator ++ ){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator)); 
			}
		}
		return list; 
	}
}
