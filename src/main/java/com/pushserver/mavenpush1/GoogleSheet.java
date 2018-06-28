/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pushserver.mavenpush1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author workaholic
 */
public class GoogleSheet {
    
    //change the key as per your redirect page
    String GoogleScript_KEY="AKfycbzRl7gTFiHZr5KAPw8Sm4c4m-XX";
    
   public String sendToGoogleSheet(String UserID, String Data) 
    {
        
    if(UserID!=null)
    {
       String _KEY="UNKNOWN";
         UserID=UserID.toUpperCase();
         
         if(!UserID.contains("@") && !UserID.contains("."))
         {
         //_KEY=SERVER.DBlogin.GetKeyDetails(UserInfo);
             _KEY=DataBase.KeyUser.get(UserID);
         }
         else
             _KEY=UserID;
         
         if(_KEY==null)
         {
          //_KEY="UNKNOWN";
         return "ERROR";
         }
         
        }      

        String Response="FAIL";
    try {

      // String url = "https://script.google.com/macros/s/AKfycbzRl7gTFiHZr5KAPw8Sm4c4m-MeTyuZyMqKiSxa8gSYVVXhda6a/exec?key=1xSRbB7qvOyoMf4zN4dDLEZ5nI941Uf5gHewLUOzsfmE&hicData=11";//http://www.twitter.com";
  String url =Data;// "https://script.google.com/macros/s/"+GoogleScript_KEY+"/exec?"+Data;//http://www.twitter.com";

	URL obj = new URL(url);
	HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        
	conn.setReadTimeout(5000);
	//conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
	//conn.addRequestProperty("User-Agent", "Mozilla");
	//conn.addRequestProperty("Referer", "google.com");

        
       // conn.addRequestProperty("User-Agent", "esp-idf/1.0 esp32");
        //conn.addRequestProperty("Host", "script.google.com");
	
	System.out.println("Request URL ... " + url);

	boolean redirect = false;

	// normally, 3xx is redirect
	int status = conn.getResponseCode();
	if (status != HttpURLConnection.HTTP_OK) {
		if (status == HttpURLConnection.HTTP_MOVED_TEMP
			|| status == HttpURLConnection.HTTP_MOVED_PERM
				|| status == HttpURLConnection.HTTP_SEE_OTHER)
		redirect = true;
	}

	//System.out.println("Response Code ... " + status);

	if (redirect) {

		// get redirect url from "location" header field
		String newUrl = conn.getHeaderField("Location");

		// get the cookie if need, for login
		String cookies = conn.getHeaderField("Set-Cookie");

		// open the new connnection again
		conn = (HttpURLConnection) new URL(newUrl).openConnection();
		conn.setRequestProperty("Cookie", cookies);
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		conn.addRequestProperty("Referer", "google.com");
								
		//System.out.println("Redirect to URL : " + newUrl);

	}

	BufferedReader in = new BufferedReader(
                              new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer html = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		html.append(inputLine);
	}
	in.close();

	//System.out.println("URL Content... \n" + html.toString());
	//System.out.println("Done");
        Response=html.toString();
    } catch (Exception e) {
	e.printStackTrace();
        Response="FAIL";
    }

    return Response;
  }

}
