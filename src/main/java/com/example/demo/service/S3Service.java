package com.example.demo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Log4j
public class S3Service {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file, String bucketName) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return "File uploaded : " + fileName;
    }
    
    public String uploadFile(MultipartFile file) {
    
    	return this.uploadFile(file, bucketName);
    }


    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public byte[] downloadAllFile(String fileName) {    	
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    private String copyToDisk(byte[] bytesRead, String fileNameInDisk ) 
    {
    	try (FileOutputStream fos = new FileOutputStream(fileNameInDisk))
    	{
    		   fos.write(bytesRead);
    		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return "File copied to " + fileNameInDisk; 
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }

    public String listFilestoString( String bucketName)
    {
    	return this.listFiles(bucketName).toString();
    }
    
    public List<String> listFiles(String bucketName) {

        ListObjectsRequest listObjectsRequest =
                new ListObjectsRequest()
                        .withBucketName(bucketName);

        List<String> keys = new ArrayList<>();

        ObjectListing objects = s3Client.listObjects(listObjectsRequest);

        while (true) {
            List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
            if (objectSummaries.size() < 1) {
                break;
            }

            for (S3ObjectSummary item : objectSummaries) {
                if (!item.getKey().endsWith("/"))
                    keys.add(item.getKey());
            }

            objects = s3Client.listNextBatchOfObjects(objects);
        }

        
        
        return keys;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
        	System.out.println("Error converting multipartFile to file " +  e.getMessage() );
            // log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}