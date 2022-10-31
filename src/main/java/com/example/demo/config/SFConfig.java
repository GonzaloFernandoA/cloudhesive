package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SFConfig {

    @Value("${salesforce.user}")
	private String user;
    @Value("${salesforce.password}")
	private String password;
    @Value("${salesforce.url}")
	private String url;
    @Value("${salesforce.token}")
	private String token;
	
    @Value("${salesforce.consumer.key}")
    private String consumer_key;
    @Value("${salesforce.consumer.secret}")
    private String consumer_secret;
    
    
   
    
}
