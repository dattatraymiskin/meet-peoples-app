package com.meetpeoples.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String username;
	    
	    @Column(nullable = false)
	    private String firstName;
	    
	    @Column(nullable = false)
	    private String lastName;
	    

	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false, unique = true)
	    private String email;
	    
	    @Column(nullable = false)
	    private String gender;
	    
	    @ManyToMany
	    @JoinTable(
	        name = "user_followers",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "follower_id")
	    )
	    private Set<User> followers = new HashSet<>();

	    @ManyToMany
	    @JoinTable(
	        name = "user_followings",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "following_id")
	    )
	    private Set<User> followings = new HashSet<>();

	    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Post> posts= new ArrayList<>();
	
}
