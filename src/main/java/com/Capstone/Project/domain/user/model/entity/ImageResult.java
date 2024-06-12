package com.Capstone.Project.domain.user.model.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "image_result")
public class ImageResult {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String fileId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    /*

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_category_id")
    private PerfumeCategory perfumeCategory;


    @Builder
    private ImageResult(String fileId, User user, PerfumeCategory perfumeCategory) {
        this.user = user;
        this.fileId = fileId;
        this.perfumeCategory = perfumeCategory;
    }
    */

}
