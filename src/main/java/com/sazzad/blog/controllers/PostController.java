package com.sazzad.blog.controllers;

import com.sazzad.blog.domain.CreatePostRequest;
import com.sazzad.blog.domain.dtos.CreatePostRequestDto;
import com.sazzad.blog.domain.dtos.PostDto;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.User;
import com.sazzad.blog.mappers.PostMapper;
import com.sazzad.blog.services.PostService;
import com.sazzad.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagID) {

        List<Post> posts = postService.getAllPost(categoryId, tagID);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();

        return ResponseEntity.ok(postDtos);

    }

    @GetMapping(path = "/drafts")
    ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {

        User logedInUser = userService.getUserById(userId);
        List<Post> draftPosts = postService.getDraftPosts(logedInUser);
        List<PostDto> postDtos = draftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);

    }

    @GetMapping
    ResponseEntity<PostDto> createPost(
            @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId) {
        User logedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(logedInUser,createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }
}
