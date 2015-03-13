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
	
	public String restMethods(String type,String request) {
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
		return output;
	}

	public LinkedList <String> responseNum(String type,String request, String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(restMethods(type,request));
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(""+jsonResponse.getJSONObject(iterator).getInt(condition)+"");
			iterator++;
		}
		return list; 
	}
	
	public LinkedList <String> responseBool(String type,String request, String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(restMethods(type,request));
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(""+jsonResponse.getJSONObject(iterator).getBoolean(condition)+"");
			iterator++;
		}
		return list; 
	}
	
	public LinkedList <String> responseString(String type,String request, String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(restMethods(type,request));
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(jsonResponse.getJSONObject(iterator).getString(condition));
			iterator++;
		}
		return list; 
	}
	
	public LinkedList <String> responseJSON(String type,String request, String condition){
		LinkedList <String> list= new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(restMethods(type,request));
		int iterator=0;
		while(iterator<jsonResponse.length()){
			list.add(jsonResponse.getJSONObject(iterator).toString());
			iterator++;
		}
		return list; 
	}
	
	public String findAttributeValue(String feature, String attributeName, String attributeValue){
		LinkedList <String> listNames=responseString("GET",feature,attributeName);
		LinkedList <String> listCondition=responseString("GET",feature,"_id");
		for(int iterator=0;iterator<listNames.size();iterator++){
			if(listNames.get(iterator).equals(attributeValue)){
				
				return listCondition.get(iterator);
			}
		}
		return null;
	}
	
	public LinkedList <String> mergeLists(LinkedList <String> list1,LinkedList <String> list2){
		LinkedList <String> list= new LinkedList <String>();
		for(int iter1=0;iter1<list1.size();iter1++){
			for(int iter2=0;iter2<list2.size();iter2++){
				if(list1.get(iter1).equals(list2.get(iter2))){
					list.add(list1.get(iter1));
				}
			}
		}
		return list;
	}

}