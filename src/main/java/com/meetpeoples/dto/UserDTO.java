package com.meetpeoples.dto;

import java.util.List;

import com.meetpeoples.models.Post;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private List<Post> posts;
}	
