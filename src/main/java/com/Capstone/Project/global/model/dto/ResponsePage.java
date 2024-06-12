package com.Capstone.Project.global.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

@Getter
public class ResponsePage<T> implements Serializable {

    @Schema(description = "컨텐츠")
    private final List<T> content;

    @Schema(description = "다음 페이지가 있는지?", example = "true")
    private final boolean hasNext;

    @Schema(description = "총 페이지 수", example = "51")
    private final int totalPages;

    @Schema(description = "총 요소 개수", example = "1021")
    private final long totalElements;

    @Schema(description = "조회한 페이지", example = "3")
    private final int page;

    @Schema(description = "페이지 컨텐츠 개수", example = "20")
    private final int size;

    @Schema(description = "처음 페이지인지?", example = "false")
    private final boolean first;

    @Schema(description = "마지막 페이지인지?", example = "false")
    private final boolean last;

    public ResponsePage(Page<T> page) {
        final PageImpl<T> pageInfo = new PageImpl<>(page.getContent(), page.getPageable(), page.getTotalElements());
        this.content = pageInfo.getContent();
        this.hasNext = pageInfo.hasNext();
        this.totalPages = pageInfo.getTotalPages();
        this.totalElements = pageInfo.getTotalElements();
        this.page = pageInfo.getNumber();
        this.size = pageInfo.getSize();
        this.first = pageInfo.isFirst();
        this.last = pageInfo.isLast();
    }
}

