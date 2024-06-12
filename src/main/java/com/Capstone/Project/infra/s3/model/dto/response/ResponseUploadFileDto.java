package com.Capstone.Project.infra.s3.model.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseUploadFileDto {

    private final List<String> imageList;

    public ResponseUploadFileDto(List<String> imageList) {
        this.imageList = imageList;
        System.out.println(imageList);
    }
}
