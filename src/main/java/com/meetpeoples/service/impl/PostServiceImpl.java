package com.meetpeoples.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.dto.PostDTO;
import com.meetpeoples.exception.PostNotFoundException;
import com.meetpeoples.exception.UserNotFoundException;
import com.meetpeoples.models.Post;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.PostRepository;
import com.meetpeoples.repository.UserRepository;
import com.meetpeoples.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;

	@Override
	public PostDTO createNewPost(Post post, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);
            Post savedPost = postRepository.save(post);
            return modelMapper.map(savedPost, PostDTO.class);
        } else {
            throw new UserNotFoundException("User not found with id " + userId);
        }
    }

    @Override
    public String deletePost(Long postId, Long userId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getUser().getId().equals(userId)) {
                postRepository.delete(post);
                return "Post deleted successfully";
            } else {
                throw new UserNotFoundException("User not authorized to delete this post");
            }
        } else {
            throw new PostNotFoundException("Post not found with id " + postId);
        }
    }


    @Override
    public List<PostDTO> findPostByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                    .map(post -> modelMapper.map(post, PostDTO.class))
                    .collect(Collectors.toList());
    }

    @Override
    public PostDTO findPostById(Long postId) {
    	 Optional<Post> postOptional = postRepository.findById(postId);
         if (postOptional.isPresent()) {
             return modelMapper.map(postOptional.get(), PostDTO.class);
         } else {
             throw new PostNotFoundException("Post not found with id " + postId);
         }
    }

    @Override
    public List<PostDTO> findAllPost() {
    	 List<Post> posts = postRepository.findAll();
         return posts.stream()
                     .map(post -> modelMapper.map(post, PostDTO.class))
                     .collect(Collectors.toList());
    }

    @Override
    public PostDTO savedPost(Long postId, Long userId) {
        //Post post = findPostById(postId);
        Optional<Post> findById = postRepository.findById(postId);
        if (findById.isPresent()) {
        	Post post = findById.get();
        	User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
            List<Post> posts = user.getPosts();
            if(posts == null)
            	 posts = new ArrayList<>();
            	
            posts.add(post);
            userRepository.save(user);
            return modelMapper.map(post, PostDTO.class);
        } else {
            throw new PostNotFoundException("Post not found with id " + postId);
        }
    }

    
    
    @Override
    public PostDTO likePost(Long postId, Long userId) {
    	 Optional<Post> findById = postRepository.findById(postId);
         if (findById.isPresent()) {
        	 Post post = findById.get();
            User user = userRepository.findById(userId)
                  .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
            post.getLiked().add(user);
            Post save = postRepository.save(post);
            return modelMapper.map(save, PostDTO.class); 
         } else {
             throw new PostNotFoundException("Post not found with id " + postId);
         }
    }

}