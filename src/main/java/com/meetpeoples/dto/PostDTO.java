package com.meetpeoples.dto;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private Long userId;
    private String content;
    private String createdAt;
}	