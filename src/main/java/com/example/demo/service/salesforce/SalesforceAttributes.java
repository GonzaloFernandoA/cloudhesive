package com.example.demo.service.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SalesforceAttributes {
   private String type;
   private String url;
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
}