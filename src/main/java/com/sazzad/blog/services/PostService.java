package com.sazzad.blog.services;

import com.sazzad.blog.domain.CreatePostRequest;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPost(UUID categoryId, UUID tagId);
    List<Post> getDraftPosts(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
}
