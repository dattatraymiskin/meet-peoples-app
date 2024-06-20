package com.meetpeoples.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
    public Post createNewPost(Post post, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);
            return postRepository.save(post);
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
    public List<Post> findPostByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + postId));
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Long postId, Long userId) {
        Post post = findPostById(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
        List<Post> posts = user.getPosts();
        if(posts == null)
        	 posts = new ArrayList<>();
        	
        posts.add(post);
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));
       // post.getLikedByUsers().add(user);
        return postRepository.save(post);
    }
}