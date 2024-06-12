package com.Capstone.Project.infra.s3;

import com.Capstone.Project.infra.s3.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    @Query("select i from ImageFile i where i.fileName = :imageName and i.user.id = :userId")
    Optional<ImageFile> findImageFileWithUserId(String imageName, Long userId);
}
