package com.kh.carlpion.file.service;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import com.kh.carlpion.exception.exceptions.FileDeleteException;
import com.kh.carlpion.exception.exceptions.FileSaveException;

@Service
public class FirebaseServiceImpl implements FileService{

	
	@Override
	public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
        	throw new FileSaveException("저장할 파일이 비어있습니다.");
        }

        try {

            Storage storage = StorageClient.getInstance().bucket().getStorage();
            String bucketName = StorageClient.getInstance().bucket().getName(); 

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = createFileName(originalFileName); 

            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                        .setContentType(file.getContentType())
                                        .build();

            // 4. 파일 업로드
            Blob blob = storage.create(blobInfo, file.getBytes());

            return fileName; 
            
        } catch (IOException e) {
        	throw new FileSaveException("파일 저장 실패: " + e.getMessage());
        } catch (Exception e) {
        	throw new FileSaveException("Firebase Storage 연동 오류: " + e.getMessage());
        }
    }

	@Override
	public boolean deleteFile(String fileName) {
		if (fileName == null || fileName.trim().isEmpty()) {
			throw new FileDeleteException("삭제할 파일명이 유효하지 않습니다.");
        }

        try {
            
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            String bucketName = StorageClient.getInstance().bucket().getName(); 

            BlobId blobId = BlobId.of(bucketName, fileName);
            Blob blob = storage.get(blobId); 

            if (blob != null) {
                boolean deleted = blob.delete();
                if (deleted) {
                	return true;
                } else {
                	throw new FileDeleteException("파일 삭제 실패 (존재하지 않거나 권한 없음): " + fileName);
                }
            } else {
            	throw new FileDeleteException("삭제하려는 파일이 Firebase Storage에 존재하지 않습니다: " + fileName);
            }

        } catch (Exception e) {
        	throw new FileDeleteException("파일 삭제 중 오류 발생: " + e.getMessage());
        }
        
	}
	
	
	public String getSignedUrl(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        try {
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            String bucketName = StorageClient.getInstance().bucket().getName();

            BlobId blobId = BlobId.of(bucketName, fileName);
            Blob blob = storage.get(blobId); // 해당 파일이 Storage에 존재하는지 확인

            if (blob == null) {
                return null;
            }

            URL signedUrl = blob.signUrl(60, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());
            return signedUrl.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	private String createFileName(String originalFileName) {

        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < originalFileName.length() - 1) {
            fileExtension = originalFileName.substring(dotIndex);
        }

        String uuid = UUID.randomUUID().toString();

        return uuid + fileExtension;
	}

}
