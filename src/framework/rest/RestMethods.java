package framework.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.json.JSONArray;

public class RestMethods {
	private BufferedReader br;
	private String lineReader;
	private String output="";
	private String urlpath= "http://172.20.208.177:4055/";
	public RestMethods(String type,String request) {
		try {

			URL url = new URL(urlpath+request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(type);
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((lineReader = br.readLine()) != null) {
				output=output+lineReader;
			}
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	
	public LinkedList <String> responseNum(String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(output);
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(""+jsonResponse.getJSONObject(iterator).getInt(condition)+"");
			iterator++;
		}
		return list; 
	}
	public LinkedList <String> response(String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(output);
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(jsonResponse.getJSONObject(iterator).getString(condition));
			iterator++;
		}
		return list; 
	}
	
	public LinkedList <String> getAllResources(){
		return response("customName");
	}
	
	public LinkedList <String> getAllRooms(){
		return response("displayName");
	}
	
	public LinkedList <String> getByNumeric(String type,String condition){
		LinkedList <String> list= new LinkedList <String>();
		LinkedList <String> listRoomConds=responseNum(type);
		LinkedList <String> listRoomNames=response("displayName");
		int iterator=0;
		while(iterator<listRoomNames.size()){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator));
			}
			iterator++;
		}
		System.out.println(list.toString());
		return list;
	}
	public LinkedList <String> getByString(String type,String condition){
		LinkedList <String> list= new LinkedList <String>();
		LinkedList <String> listRoomConds=response(type);
		LinkedList <String> listRoomNames=response("displayName");
		int iterator=0;
		while(iterator<listRoomNames.size()){
			if(listRoomConds.get(iterator).equals(condition)){
				list.add(listRoomNames.get(iterator));
			}
			iterator++;
		}
		System.out.println(list.toString());
		return list;
	}
	
}