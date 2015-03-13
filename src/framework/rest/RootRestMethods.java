package framework.rest;

import java.util.LinkedList;

import framework.rest.RestMethods;

public class RootRestMethods {
	public static LinkedList <String> getAllRooms(){
		RestMethods getRooms = new RestMethods();
		return getRooms.responseString("GET","rooms","displayName");
	}
	
	//Resources Methods
	public static LinkedList <String> getAllResources(){
		RestMethods getResources = new RestMethods();
		return getResources.responseString("GET","resources","customName");
	}
	
	public static void deleteResource(String resourceName){
		RestMethods getResourceID = new RestMethods();
		RestMethods deleteResource = new RestMethods();
		deleteResource.restMethods("DELETE","resources/"+getResourceID.findAttributeValue("resources","name",resourceName));
	}
	
	public static void deleteResources(){
		LinkedList <String> listNames=getAllResources();
		for(int iterator=0;iterator<listNames.size();iterator++){
			deleteResource(listNames.get(iterator));
		}
	}
	
	public static void deleteAssociatedResource(String resourceName){
		RestMethods getResourceID = new RestMethods();
		RestMethods deleteResource = new RestMethods();
		deleteResource.restMethods("DELETE","resources/"+getResourceID.findAttributeValue("resources","name",resourceName));
	}
	//Out Of Orders
	public static void deleteOutOfOrder(String roomName, String title){
		RestMethods getRoomID = new RestMethods();
		RestMethods getoutOfOrderID = new RestMethods();
		RestMethods deleteOutOfOrder= new RestMethods();
		String roomID=getRoomID.findAttributeValue("rooms","displayName",roomName);
		String outOfOrders="rooms/"+roomID+"/out-of-orders";
		String outOfOrderID=getoutOfOrderID.findAttributeValue(outOfOrders,"title",title);
		deleteOutOfOrder.restMethods("DELETE",outOfOrders+"/"+outOfOrderID);
	}
	
	public static void deleteMeeting(String resourceName){
		/*RestMethods getResourceID = new RestMethods();
		
		RestMethods.restMethods("DELETE","resources/"+getResourceID.findAttributeValue("resources","name",resourceName));
		*/
	}
	//
	public LinkedList <String> getListByNumeric(String type,String condition){
		RestMethods restConds = new RestMethods();
		LinkedList <String> list= new LinkedList <String>();
		LinkedList <String> listRoomConds=restConds.responseNum("GET",type,condition);
		LinkedList <String> listRoomNames=getAllRooms();
		for(int iterator=0;iterator<listRoomNames.size();iterator++){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator));
			}
		}
		return list;
	}
	public LinkedList <String> getListByString(String type,String condition){
		RestMethods restConds = new RestMethods();
		LinkedList <String> list= new LinkedList <String>();
		LinkedList <String> listRoomConds=restConds.responseString("GET",type,condition);
		LinkedList <String> listRoomNames=getAllRooms();
		for(int iterator=0;iterator<listRoomNames.size();iterator++){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator));
			}
		}
		return list;
	}
}
