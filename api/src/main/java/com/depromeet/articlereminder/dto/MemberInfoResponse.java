package com.depromeet.articlereminder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberInfoResponse {
    private Long userId;
    private String name;
    private Boolean state;
    private String status;
    private String socialType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
