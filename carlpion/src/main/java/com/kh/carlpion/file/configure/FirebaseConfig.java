package com.kh.carlpion.file.configure;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {
	
	private final ResourceLoader resourceLoader; 
	
	@Value("${firebase.configuration-file}")
	private String configurationFile;
	@Value("${firebase.bucket}")
	private String bucket;
	
	@PostConstruct
	public void initializeFirebase() {
		if (FirebaseApp.getApps().isEmpty()) { 
			try {
				Resource serviceAccountResource = resourceLoader.getResource(configurationFile);

				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccountResource.getInputStream()))
						.setStorageBucket(bucket) 
						.build();

				FirebaseApp.initializeApp(options);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
