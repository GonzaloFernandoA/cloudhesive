package com.example.demo.service.httputils;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.message.BasicHeader;

public class HttpServiceImpl {
	
	private Header oauthHeader;
	private Header prettyPrintHeader = new BasicHeader("X-PrettyPrint","1");
	
	public HttpPatch getHttpPatch(String uri, String loginAccessToken )
	{
		oauthHeader = new BasicHeader("Authorization","OAuth" + loginAccessToken);
		HttpPatch httpPatch = new HttpPatch(uri);
		httpPatch.addHeader(oauthHeader);
		httpPatch.addHeader(prettyPrintHeader);
		
		
		return httpPatch;
		
	}
	
}
