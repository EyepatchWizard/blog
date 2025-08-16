package com.sazzad.blog.services;

import com.sazzad.blog.domain.CreatePostRequest;
import com.sazzad.blog.domain.UpdatePostRequest;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post getPost(UUID id);
    List<Post> getAllPost(UUID categoryId, UUID tagId, int page, int size);
    List<Post> getDraftPosts(User user, int page, int size);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);

//    Page<Post> getAllPost(UUID categoryId, UUID tagId, int page, int size);
//    Page<Post> getDraftPosts(User user, int page, int size);
}
