package com.kh.carlpion.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{
	
	private final S3Client s3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	
	@Override
	public String upLoad(MultipartFile file) {
	
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		
		PutObjectRequest request = PutObjectRequest.builder()
												   .bucket(bucketName)
												   .key(fileName)
												   .contentType(file.getContentType())
												   .build();
		
		try {
			s3Client.putObject(request,
					RequestBody.fromInputStream(file.getInputStream(), file.getSize()));			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
	}

	@Override
	public void deleteFile(String fileUrl) {
		
		try {
			URL url = new URL(fileUrl);
			String path = url.getPath().substring(1);
			
			DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
																   .bucket(bucketName)
																   .key(path)
																   .build();
			
			s3Client.deleteObject(deleteRequest);
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
