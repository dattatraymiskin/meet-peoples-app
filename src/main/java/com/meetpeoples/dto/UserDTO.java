package com.meetpeoples.dto;

import java.util.List;

import com.meetpeoples.models.Post;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private List<Post> posts;
}	
