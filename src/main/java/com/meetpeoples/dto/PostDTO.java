package com.meetpeoples.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;
    private UserDTO user;
}	