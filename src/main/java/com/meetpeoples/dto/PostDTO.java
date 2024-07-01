package com.meetpeoples.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;
    private UserDTO user;
}	