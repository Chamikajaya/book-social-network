package com.chamika.books_project.shared;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

// * Generic class to represent a paginated response
public class PageResponse<T> {

    private List<T> content;
    private int number;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;


}
