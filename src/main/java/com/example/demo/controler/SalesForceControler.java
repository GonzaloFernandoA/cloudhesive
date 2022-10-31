package com.example.demo.controler;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.*;
import com.example.demo.service.salesforce.CRUDService;


import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
public class SalesForceControler {
	
    @Autowired
    private SFLoginService service;
    
    @Autowired
    private CRUDService crudService;
    
    @GetMapping("/SFLogin")
    public ResponseEntity<String> getToken() {
    	
        return new ResponseEntity<>(service.getToken(), HttpStatus.OK);
    }

    @PostMapping("/SalesForce/Lead")
    public ResponseEntity<String> postEntity(@RequestBody String Parameter1) throws ClientProtocolException, IOException {
    	
    	String token = service.getToken();
    	System.out.println("jSon:" + Parameter1);
    	
    	JSONObject newLead = new JSONObject(Parameter1);
    	
    	HttpResponse response = crudService.insert(newLead, token);
    	
        return new ResponseEntity<>("Reponse", HttpStatus.OK );
    }
    
    @GetMapping("/SalesForce/Leads")
    public ResponseEntity<String> getEntities() throws ClientProtocolException, IOException {
    	
    	String token = service.getToken();
    	
    	crudService.queryLeads( token);
    	
        return new ResponseEntity<>("Reponse", HttpStatus.OK);
    }
    
}
