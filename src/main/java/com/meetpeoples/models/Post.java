package com.meetpeoples.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Post  implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String image;
    
    private String video;
    
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;	

	@ManyToMany
	@JoinTable(name = "post_liked", 
	    joinColumns = @JoinColumn(name = "post_id"), 
	     inverseJoinColumns = @JoinColumn(name = "likeUser_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<User> liked = new ArrayList<>();

    @OneToMany
    @JsonIgnore
	private List<Comment> comments = new ArrayList<>();
}