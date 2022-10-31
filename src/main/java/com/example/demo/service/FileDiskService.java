package com.example.demo.service;


import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileDiskService {

	public MultipartFile getFileDisk(String outputFile, byte[] bytea)
	{
		String fileName = outputFile;
		CustomMultipartFile customMultipartFile = new CustomMultipartFile(bytea, fileName);
		try {
		customMultipartFile.transferTo(customMultipartFile.getFile());

		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException : " + e);
		} catch (IOException e) {
			System.out.println("IOException : " + e);
		}
		return customMultipartFile;
	}
}
