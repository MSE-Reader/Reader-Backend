package com.Capstone.Project.infra.s3.model;


import com.Capstone.Project.domain.user.model.entity.User;
import com.Capstone.Project.global.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ImageFile extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String fileId;

    private String contentType;

    private String fileName;

    @Builder
    private ImageFile(User user, String fileId, String contentType, String fileName) {
        this.user = user;
        this.fileId = fileId;
        this.contentType = contentType;
        this.fileName = fileName;
    }
}
