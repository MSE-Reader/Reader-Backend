package com.Capstone.Project.infra.s3.model;


import com.Capstone.Project.infra.s3.exception.InvalidImageContentTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ImageRequest {

    private final String originalImageName;
    private final MediaType contentType;
    private final InputStreamSupplier inStreamSupplier;
    private final Long size;

    public ImageRequest(MultipartFile image) {
        this.originalImageName = image.getOriginalFilename();
        this.inStreamSupplier = image::getInputStream;
        this.size = image.getSize();

        String fileMimeType = image.getContentType();
        if (fileMimeType == null) {
            this.contentType = MediaType.APPLICATION_OCTET_STREAM;
        } else {
            try {
                this.contentType = MediaType.parseMediaType(image.getContentType());
            } catch (InvalidMediaTypeException e) {
                throw new InvalidImageContentTypeException(e);
            }
        }
    }

    public InputStream getInputStream() throws IOException {
        return inStreamSupplier.get();
    }

    public static List<ImageRequest> ofList(List<MultipartFile> images) {
        List<ImageRequest> imageRequests = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                imageRequests.add(new ImageRequest(image));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return imageRequests;
    }

    @FunctionalInterface
    public interface InputStreamSupplier {

        InputStream get() throws IOException;
    }
}
