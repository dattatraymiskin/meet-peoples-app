package com.meetpeoples.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetpeoples.dto.PostDTO;
import com.meetpeoples.exception.ErrorResponseException;
import com.meetpeoples.exception.PostNotFoundException;
import com.meetpeoples.exception.UserNotFoundException;
import com.meetpeoples.models.Post;
import com.meetpeoples.models.User;
import com.meetpeoples.repository.PostRepository;
import com.meetpeoples.repository.UserRepository;
import com.meetpeoples.service.PostService;
import com.meetpeoples.utility.ConversionUtility;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ConversionUtility conversionUtility;

	@Override
	public PostDTO createNewPost(Post post, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);
            Post savedPost = postRepository.save(post);
            return conversionUtility.convertToDto(savedPost);
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
        List<PostDTO> list = new ArrayList<>();
        for (Post post : posts) {
			list.add(conversionUtility.convertToDto(post));
		}
        return list;
    }

    @Override
    public Post findPostById(Long postId) {
    	 Optional<Post> postOptional = postRepository.findById(postId);
         if (postOptional.isPresent()) {
             return postOptional.get();
         } else {
             throw new PostNotFoundException("Post not found with id " + postId);
         }
    }

	@Override
	public List<PostDTO> findAllPost() {
		List<PostDTO> postDto = new ArrayList<>();
		try {
		List<Post> posts = postRepository.findAll();
		for (Post post2 : posts) {
			PostDTO convertToDto = conversionUtility.convertToDto(post2);
			if(convertToDto!=null ) {				
				convertToDto.setUser(conversionUtility.convertToUserDtoWithoutPost(post2.getUser()));
			}
			postDto.add(convertToDto);
		}
		} catch (Exception e) {
		     throw new ErrorResponseException("unable to find all post");
		}
		return postDto;/*posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());*/
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
            return conversionUtility.convertToDto(post);
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
            return conversionUtility.convertToDto(save); 
         } else {
             throw new PostNotFoundException("Post not found with id " + postId);
         }
    }

}