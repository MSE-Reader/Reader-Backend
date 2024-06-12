//package com.Capstone.Project.global.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.regions.Region;
//
//@Configuration
//public class AwsConfig {
//
////    @Value("${cloud.aws.credentials.access-key}")
////    private String accessKey;
////
////    @Value("${cloud.aws.credentials.secret-key}")
////    private String secretKey;
////
////    @Value("${cloud.aws.region.static}")
////    private String region;
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretKey;
//
//    @Value("${cloud.aws.region.static}")
//    private String region;
//    @Bean
//    public S3Client amazonS3Client() {
//        AwsCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
//        return S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//    }
//}
