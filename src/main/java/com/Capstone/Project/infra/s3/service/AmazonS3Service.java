package com.Capstone.Project.infra.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class AmazonS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public AmazonS3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public String uploadPhoto(Long memberId, MultipartFile multipartFile) {
        final String originalFilename = multipartFile.getOriginalFilename();

        //멤버별 디렉토리 생성
        final String directory = memberId.toString() + "/";
        final String s3Filename = directory + originalFilename;
        final ObjectMetadata metadata = getObjectMetadata(multipartFile);

        try {
            // member 디렉토리가 없을 경우 만들기
            if (!amazonS3Client.doesObjectExist(bucket, directory)) {
                amazonS3Client.putObject(bucket, directory, "");
            }
            amazonS3Client.putObject(bucket, s3Filename, multipartFile.getInputStream(), metadata);
            return amazonS3Client.getUrl(bucket, s3Filename).toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 picture upload failed");
        }

    }

    public ResponseEntity<byte[]> getObject(Long memberId, String storedFileName)
        throws IOException {
        final String directory = memberId.toString() + "/";
        String s3FileName = directory + storedFileName;
        S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, s3FileName));
        S3ObjectInputStream objectInputStream = o.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);
        objectInputStream.close(); // 입력 스트림 닫기

        // 파일 이름에서 확장자 추출
        String fileExtension = storedFileName.substring(storedFileName.lastIndexOf(".") + 1);

        // 파일 타입에 따라 Content-Type 설정
        String contentType;
        if (fileExtension.equalsIgnoreCase("png")) {
            contentType = "image/png";
        } else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase(
            "jpeg")) {
            contentType = "image/jpeg";
        } else if (fileExtension.equalsIgnoreCase("xlsx") || fileExtension.equalsIgnoreCase(
            "xls")) {
            contentType = "";
        } else {
            // 기타 파일 형식에 대한 처리
            contentType = "application/octet-stream";
        }

        // 파일 이름을 UTF-8로 인코딩하여 공백과 같은 특수 문자를 처리
        String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");

        // HTTP 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        // ResponseEntity 생성하여 반환
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }


    public String getPresignedUrl(Long memberId, String storedFileName) {
        // 멤버별 디렉토리와 파일 이름 설정
        final String directory = memberId.toString() + "/";
        final String s3FileName = directory + storedFileName;

        // Pre-signed URL 생성 요청 생성
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
            bucket, s3FileName);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET); // GET 요청으로 설정
        generatePresignedUrlRequest.setExpiration(
            new Date(System.currentTimeMillis() + 3600000)); // URL 만료 시간 설정 (현재 시간으로부터 1시간 후)

        // Pre-signed URL 생성 및 문자열 형태로 반환
        URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }


    private ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }

    public List<String> listFolders() {
        List<String> folders = new ArrayList<>();

        // 버킷 내의 객체들을 나열하는 요청 생성
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket)
            .withDelimiter("/");

        // 객체들을 나열하고 폴더들을 추출하여 리스트에 추가
        ListObjectsV2Result result;
        do {
            result = amazonS3Client.listObjectsV2(request);
            for (String commonPrefix : result.getCommonPrefixes()) {
                folders.add(commonPrefix);
            }
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        return folders;
    }


}

