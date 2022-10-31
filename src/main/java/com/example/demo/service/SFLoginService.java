package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.HttpStatus;

import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;

@Service
public class SFLoginService {
	
    @Value("${salesforce.user}")
    String USERNAME     ;
    
    @Value("${salesforce.password}")
    String PASSWORD     ;
    
    @Value("${salesforce.token}")
    String TOKEN;
    
    @Value("${salesforce.url_test}")
    String LOGINURL    ;
    
    String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    @Value("${salesforce.consumer.key}")
    String CLIENTID    ;
    @Value("${salesforce.consumer.secret}")
    String CLIENTSECRET ;
    
    private String REST_ENDPOINT = "/services/data" ;
    private String API_VERSION = "/v36.0" ;
    private String baseUri;
    private Header oauthHeader;
    private Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");

    public String getToken()
    {
  	
        HttpClient httpclient = HttpClientBuilder.create().build();
        
        // Assemble the login request URL
        String loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET +
                          "&username=" + USERNAME +
                          "&password=" + PASSWORD+ TOKEN;
 
        System.out.println(loginURL);
        
        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;
 
        try {
            // Execute the login POST request
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            cpException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
 
        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            // Error is in EntityUtils.toString(response.getEntity())
            return "Error authenticating to Force.com: "+statusCode;
        }
 
        String getResult = null;
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
 
        JSONObject jsonObject = null;
        String loginAccessToken = null;
        String loginInstanceUrl = null;
 
        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
 
        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);    
        System.out.println("All Json Login: " + jsonObject.toString());
 
    	
    	return loginAccessToken;
    }
    
  
}
