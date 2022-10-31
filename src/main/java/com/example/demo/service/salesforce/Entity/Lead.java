package com.example.demo.service.salesforce.Entity;


import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@AllArgsConstructor
@Value
@Data
@EntityScan
public class Lead {
	
	    public String website;
	    public String useCaseWorkload;
	    public String title;
	    public String streetAddress;
	    public String status;
	    public String state;
	    public String segmentCompanySize;
	    public String projectDescription;
	    public String postalCode;
	    public String phone;
	    public String partnerCrmLeadId;
	    public String levelofAWSUsage;
	    public String leadStatusReason;
	    public String leadSource;
	    public String leadOwnerName;
	    public String leadOwnerEmail;
	    public double leadAge;
	    public Date lastModifiedDate;
	    public String lastModifiedBy;
	    public String industry;
	    public String fullName;
	    public String email;
	    public Date createdDate;
	    public String createdBy;
	    public String country;
	    public String company;
	    public String city;
	    public String campaignName;
	    public String campaignMemberStatus;
	    public String apnCrmUniqueIdentifier;
	    
	    // JSONObject jsonObject = new JSONObject(instanceOfClass1);
	    
}