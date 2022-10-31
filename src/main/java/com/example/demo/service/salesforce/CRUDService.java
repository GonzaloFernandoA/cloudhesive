package com.example.demo.service.salesforce;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.service.salesforce.Entity.Lead;

import io.jsonwebtoken.io.IOException;

import org.json.JSONException;


@Service
public class CRUDService {
 
	private Header oauthHeader;
	private Header prettyPrintHeader = new BasicHeader("X-PrettyPrint","1");
	
    String LOGINURL   = "https://cloudhesive1--sbpcopy.sandbox.my.salesforce.com/" ;
	
	private String baseUri; 
	private String baseUriSalesForce;
	
    private String REST_ENDPOINT = "services/data" ;
    private String API_VERSION = "/v36.0" ;
    private String ENTITY = "Lead/" ;
	
	public CRUDService()
	{
		baseUriSalesForce = LOGINURL + REST_ENDPOINT + API_VERSION ; 
		baseUri = baseUriSalesForce + "/sobjects/" + ENTITY;
	}
	
	
    public void queryLeads(String loginAccessToken ) throws ClientProtocolException, java.io.IOException {
        System.out.println("\n_______________ Lead QUERY _______________");
		oauthHeader = new BasicHeader("Authorization","Bearer " + loginAccessToken );
        try {
 
            //Set up the HTTP objects needed to make the request.
            HttpClient httpClient = HttpClientBuilder.create().build();
 
            String uri = baseUriSalesForce + "/query?q=Select+Id+,+FirstName+,+LastName+,+Company+From+Lead+Limit+5";
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);
 
            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);
 
            // Process the result
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String response_string = EntityUtils.toString(response.getEntity());
                try {
                    JSONObject json = new JSONObject(response_string);
                    
                    System.out.println("Query was successful. Status code returned is " + json.toString());
                  /*  System.out.println("JSON result of Query:\n" + json.toString(1));
                    JSONArray j = json.getJSONArray("records");
                    for (int i = 0; i < j.length(); i++){
                        leadId = json.getJSONArray("records").getJSONObject(i).getString("Id");
                        leadFirstName = json.getJSONArray("records").getJSONObject(i).getString("FirstName");
                        leadLastName = json.getJSONArray("records").getJSONObject(i).getString("LastName");
                        leadCompany = json.getJSONArray("records").getJSONObject(i).getString("Company");
                        */
              //          System.out.println("Lead record is: " + i + ". " + leadId + " " + leadFirstName + " " + leadLastName + "(" + leadCompany + ")");
                   
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            } else {
                System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                System.exit(-1);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }
	
	public void update(JSONObject entity, String loginAccessToken) throws ClientProtocolException, java.io.IOException
	{
		
		String uri = baseUri + "/sobjects/Lead/" + entity.getString("Id");

        try {

    		oauthHeader = new BasicHeader("Authorization","Bearer " + loginAccessToken );
            JSONObject lead = new JSONObject();
            lead.put("LastName", "Lead --UPDATED");
            System.out.println("JSON for update of lead record:\n" + lead.toString(1));
 
            //Set up the objects necessary to make the request.
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpClient httpClient = HttpClientBuilder.create().build();
 
            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            StringEntity body = new StringEntity(entity.toString(1));
            body.setContentType("application/json");
            httpPatch.setEntity(body);
 
            //Make the request
            HttpResponse response = httpClient.execute(httpPatch);
 
            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Updated the lead successfully.");
            } else {
                System.out.println("Lead update NOT successfully. Status code is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
		
		
	}
	
	public HttpResponse insert(JSONObject lead, String loginAccessToken) throws ClientProtocolException, java.io.IOException

	{
		HttpResponse response  = null;
		System.out.println("\n____________ INSERT OBJECT________");
		System.out.println("baseUri: " + baseUri);
		System.out.println("Token: " + loginAccessToken);
		
		try {
			
			oauthHeader = new BasicHeader("Authorization","Bearer " + loginAccessToken );
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			
			HttpPost httpPost = new HttpPost(baseUri);
			httpPost.addHeader(oauthHeader);
			httpPost.addHeader(prettyPrintHeader);
			
			StringEntity body = new StringEntity(lead.toString(1));

			System.out.println("StringEntity Body: " + body);
			System.out.println("Body: " + body.toString());
			body.setContentType("application/json");
			httpPost.setEntity(body);
			
			response = httpClient.execute(httpPost);
			
			 System.out.println("getReasonPhrase: " + response.getStatusLine().getReasonPhrase());
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode == 201)
			{
				String response_string = EntityUtils.toString(response.getEntity());
				JSONObject json = new JSONObject(response_string);
				
				
				System.out.println("new entity created");
			} else {
			 System.out.println("NO entity were created." + statusCode);
			}
		
		} catch (JSONException e) {
					e.printStackTrace();
		} catch (IOException ioe) {
					ioe.printStackTrace();
		} catch (NullPointerException npe) {
				npe.printStackTrace();
		}
		return response; 
		
	
	}
}
