package com.mj.service;

import com.mj.annotation.S3BucketName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This service is responsible for the basic operations upload, download and delete
 * from your amazon s3 bucket.
 */
@Service
@Slf4j
public class StorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    @S3BucketName
    private String bucketName;


    /**
     * This method upload a file to your amazon s3 bucket.
     */
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(this.bucketName)
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        this.s3Client.putObject(putObjectRequest, RequestBody.fromFile(fileObj));
        // Once the file has been uploaded from amazon s3 we need to delete it from our computer
        fileObj.delete();

        return "Your file " + fileName + " was successfully uploaded.";
    }

    /**
     * This method download a file from your amazon s3 bucket.
     */
    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(this.bucketName)
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(getObjectRequest);

        try {
            byte[] content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method delete a file from your amazon s3 bucket.
     */
    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(this.bucketName)
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        s3Client.deleteObject(deleteObjectRequest);

        return "Your file " + fileName + " was successfully deleted.";
    }

    /**
     * A utility for converting multipart files into files.
     */
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
