package com.appium.project.scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.webservice.JsonParser;
import com.automation.webservice.RestAccelerators;

public class API {
	
	public static void main(String args[])
	{
		try
		{
		RestAccelerators restAccelerators = new RestAccelerators();
		String JSONRequest = IOUtils.toString(new FileInputStream(new File("E:/Json_request.json")), "UTF-8");
		HttpResponse resopnse = restAccelerators.htttpPutJSON("https://poc.justdrive.cloudcar.com/api/v1/locations/named", JSONRequest);
		System.out.println(restAccelerators.isSuccessStatus(resopnse));
		String responseString = restAccelerators.getJsonResponseString(resopnse);
		Assert.assertTrue(restAccelerators.isSuccessStatus(resopnse), "status code 200");
		JsonParser jsonParser = new JsonParser();
		String Zipcode = jsonParser.getJsonField(responseString, "zip", "");
		String City = jsonParser.getJsonField(responseString, "city", "");
		String Source = jsonParser.getJsonField(responseString, "source", "");
		if(Zipcode.equalsIgnoreCase("94022"))
		{
			System.out.println("Zipcode success");
		}
		if(City.equalsIgnoreCase("Los Altos"))
		{
			System.out.println("City success");
		}
		if(Source.equalsIgnoreCase("CloudCar"))
		{
			System.out.println("Source success");
		}
		}
		catch(JSONException je)
		{
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Post command.
	 */
	//@Test
	public void PostCommand()
	{
		try
		{
		RestAccelerators restAccelerators = new RestAccelerators();
		String JSONRequest = IOUtils.toString(new FileInputStream(new File("E:/data.json")), "UTF-8");
		HttpResponse resopnse = restAccelerators.htttpPostJSON("https://poc.justdrive.cloudcar.com/api/v1/command", JSONRequest);
		System.out.println(restAccelerators.isSuccessStatus(resopnse));
		String responseString = restAccelerators.getJsonResponseString(resopnse);
		JsonParser jsonParser = new JsonParser();
		String ID = jsonParser.getJsonField(responseString, "id", "");
		String entityID = jsonParser.getJsonField(responseString, "data//entity//id", "Starbucks");		
		if(ID.equalsIgnoreCase("df957c7c-3530-4685-b600-985fe0568634"))
		{
			System.out.println("Id success");
		}
		if(entityID.equalsIgnoreCase("Yelp:starbucks-mountain-view-33"))
		{
			System.out.println("entityID success");
		}
		}
		catch(JSONException je)
		{
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	/**
	 * Named service.
	 */
	@Test
	public void NamedService()
	{
		try
		{
		RestAccelerators restAccelerators = new RestAccelerators();
		String JSONRequest = IOUtils.toString(new FileInputStream(new File("E:/Json_request.json")), "UTF-8");
		HttpResponse resopnse = restAccelerators.htttpPutJSON("https://poc.justdrive.cloudcar.com/api/v1/locations/named", JSONRequest);
		System.out.println(restAccelerators.isSuccessStatus(resopnse));
		String responseString = restAccelerators.getJsonResponseString(resopnse);
		
		 BufferedWriter bufferedWriter = null;
	        try {
	           
	            File myFile = new File("E:/MyTestFile.txt");
	            // check if file exist, otherwise create the file before writing
	            if (!myFile.exists()) {
	                myFile.createNewFile();
	            }
	            Writer writer = new FileWriter(myFile);
	            bufferedWriter = new BufferedWriter(writer);
	            bufferedWriter.write(responseString);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{
	            try{
	                if(bufferedWriter != null) bufferedWriter.close();
	            } catch(Exception ex){
	                 
	            }
	        }
	        
		Assert.assertTrue(restAccelerators.isSuccessStatus(resopnse), "status code is 200");
		
		
		JsonParser jsonParser = new JsonParser();
		String Zipcode = jsonParser.getJsonField(responseString, "zip", "");
		String City = jsonParser.getJsonField(responseString, "city", "");
		String Source = jsonParser.getJsonField(responseString, "source", "");
		if(Zipcode.equalsIgnoreCase("94022"))
		{
			Assert.assertTrue(Zipcode.equalsIgnoreCase("94022"), "status code is 200");
			System.out.println("Zipcode success");
		}
		if(City.equalsIgnoreCase("Los Altos"))
		{
			System.out.println("City success");
		}
		if(Source.equalsIgnoreCase("CloudCar"))
		{
			System.out.println("Source success");
		}
		}
		catch(JSONException je)
		{
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
