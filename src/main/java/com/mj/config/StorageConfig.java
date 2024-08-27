package com.mj.config;

import com.mj.annotation.S3BucketName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;

/**
 * This class handles the basic configurations needed to connect the app to amazon s3 service.
 */
@Configuration
public class StorageConfig {
    // == fields ==
    @Value("${amazon.aws.credentials.access-key}")
    private String accessKey;

    @Value("${amazon.aws.credentials.secret-access-key}")
    private String secretAccessKey;

    @Value("${amazon.s3.region}")
    private String region;

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Bean
    public S3Client s3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretAccessKey);
        Region region = Region.of(this.region);

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(region).build();
    }

    @Bean
    @S3BucketName
    public String s3BucketName() {
        return this.bucketName;
    }
}
