package com.meetpeoples.models;

import java.util.ArrayList;
import java.util.List;

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
	    
	    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	    @JoinTable(name = "followers",
	            joinColumns = @JoinColumn(name = "followers_id"),
	            inverseJoinColumns = @JoinColumn(name = "followings_id"))
	    @JsonIgnore
	    private List<User> followings;

	    @ManyToMany(mappedBy = "followings", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	    @JsonIgnore
	    private List<User> followers;

		@OneToMany( mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
		@JsonIgnore
	    private List<Post> posts= new ArrayList<>();
	
}
