package framework.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.json.JSONArray;

import framework.common.AppConfigConstants;

public class RestMethods {
	private BufferedReader br;
	private String lineReader;
	private String output = "";
	private String urlpath =  AppConfigConstants.URL_REST;

	/** [J.C]
	 * @param type:  Type of method, can be GET or DELETE
	 * @param request: the path of the url used for the request to REST API
	 * @return This method can be used for DELETE or GET methods to obtain or delete some 
	 */
	public String getAndDeleteMethods(String type, String request) {
		try {
			URL url = new URL(urlpath + request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(type);
			conn.setRequestProperty("Accept",  "application/json");
			if (conn.getResponseCode() !=  200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+  conn.getResponseCode());
			}
			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			while ((lineReader = br.readLine()) !=  null) {
				output = output + lineReader;
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	public String postAndPutMethod(String request, String fileJSON ) {
		try {
			URL url = new URL(urlpath + request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	 
			OutputStream os = conn.getOutputStream();
			os.write(fileJSON.getBytes());
			os.flush();
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/** [J.C]
	 * @param request: the path of the url used for the request to REST API
	 * @param condition : the parameter for search Int elements in an JSONArray 
	 * @return This method return a LinkedList <String> of elements that have the condition
	 */
	public LinkedList <String> responseNum(String request,  String condition){
		LinkedList <String> list = new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(getAndDeleteMethods("GET", request));
		int iterator = 0;
		while(iterator < jsonResponse.length()){
			list.add("" + jsonResponse.getJSONObject(iterator).getInt(condition) + "");
			iterator ++ ;
		}
		return list; 
	}
	
	/** [J.C]
	 * @param request: the path of the url used for the request to REST API
	 * @param condition : the parameter for search Boolean elements in an JSONArray 
	 * @return This method return a LinkedList <String> of elements that have the condition
	 */
	public LinkedList <String> responseBool(String request,  String condition){
		LinkedList <String> list = new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(getAndDeleteMethods("GET", request));
		int iterator = 0;
		while(iterator < jsonResponse.length()){
			list.add("" + jsonResponse.getJSONObject(iterator).getBoolean(condition) + "");
			iterator ++ ;
		}
		return list; 
	}
	
	/** [J.C] 
	 * @param request: the path of the url used for the request to REST API
	 * @param condition : the parameter for search Boolean elements in an JSONArray 
	 * @return This method return a LinkedList <String> of elements that have the condition
	 */
	public LinkedList <String> responseString(String request, String condition){
		LinkedList <String> list = new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(getAndDeleteMethods("GET", request));
		int iterator = 0;
		while(iterator < jsonResponse.length()){
			list.add(jsonResponse.getJSONObject(iterator).getString(condition));
			iterator ++ ;
		}
		return list; 
	}
	
	/** [J.C]
	 * @param request: the path of the url used for the request to REST API
	 * @param condition : the parameter for search JSONObject elements in an JSONArray 
	 * @return This method return a LinkedList <String> of elements that have the condition
	 */
	public LinkedList <String> responseJSON(String type, String request, String condition){
		LinkedList <String> list = new LinkedList <String>();
		JSONArray jsonResponse = new JSONArray(getAndDeleteMethods(type, request));
		int iterator = 0;
		while(iterator < jsonResponse.length()){
			list.add(jsonResponse.getJSONObject(iterator).getJSONObject(condition).toString());
			iterator ++ ;
		}
		return list; 
	}
	
	/** [J.C]
	 * @param feature : Refers to the feature (i.e. rooms, resources, etc.) 
	 * @param attributeName : Refers to the attribute of feature 
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @param attributeValue : Refers to the value of attribute 
	 * (i.e. If attribute is displayName,attributeValue could be Room 1)
	 * @param attributeSearched : Refers to the attribute of feature that you search
	 * (i.e. If feature is rooms, attribute could be _id)
	 * @return return the value of attributeSearched
	 */
	public String findAttributeValue(String feature, String attributeName, String attributeValue, String attributeSearched){
		LinkedList <String> listNames = responseString(feature, attributeName);
		LinkedList <String> listCondition = responseString(feature, attributeSearched);
		for(int iterator = 0;iterator < listNames.size();iterator ++ ){
			if(listNames.get(iterator).equals(attributeValue)){

				return listCondition.get(iterator);
			}
		}
		return null;
	}
	
	public LinkedList <String> mergeLists(LinkedList <String> list1, LinkedList <String> list2){
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