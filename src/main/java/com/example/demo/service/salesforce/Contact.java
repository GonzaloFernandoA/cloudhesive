package com.example.demo.service.salesforce;


import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Contact {
   public static final String CONTACT_QUERY = "SELECT Name, Title, Department FROM Contact";

   @JsonProperty(value = "Name")
   private String name;

   @JsonProperty(value = "Title")
   private String title;

   @JsonProperty(value = "Department")
   private String department;

   @Autowired
   private SalesforceAttributes attributes;

   public String getId() {
	   
	   
/*       if (attributes != null && attributes.getUrl() != null) {
           return StringUtils.substringAfterLast(attributes.getUrl(), "/");
       }*/

       return null;
   }
}
