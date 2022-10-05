package com.jelaniak.twittercloneproject.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private String key;

    public static final String BUCKET_NAME = "twitter-clone-tut";
    private final AmazonS3Client awsS3Client;

    public String uploadMedia(MultipartFile file) {

        //Prepare a Key
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        //Create a unique value
        key = UUID.randomUUID() + "." + filenameExtension;

        //Create object metadata
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An exception occurred while uploading the file.");
        }

        //Read this file publicly without any authentication from the angular application
        awsS3Client.setObjectAcl(BUCKET_NAME, key, CannedAccessControlList.PublicRead);

        return awsS3Client.getResourceUrl(BUCKET_NAME, key);
    }

    public void deleteS3Media(String s3MediaKey) {
        awsS3Client.deleteObject(BUCKET_NAME, s3MediaKey);
    }

    public String getKey() {
        return key;
    }
}
