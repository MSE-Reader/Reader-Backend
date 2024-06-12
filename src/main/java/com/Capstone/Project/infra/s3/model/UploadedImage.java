package com.Capstone.Project.infra.s3.model;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public class UploadedImage {

    private final String fileId;

    private final String originalName;

    private final MediaType mimeType;

    private final ImageRequest image;

    public UploadedImage(String fileId, ImageRequest image) {
        this.fileId = fileId;
        this.originalName = image.getOriginalImageName();
        this.mimeType = image.getContentType();
        this.image = image;
    }
}