package com.sazzad.blog.services.impl;

import com.sazzad.blog.domain.CreatePostRequest;
import com.sazzad.blog.domain.PostStatus;
import com.sazzad.blog.domain.UpdatePostRequest;
import com.sazzad.blog.domain.entities.Category;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.Tag;
import com.sazzad.blog.domain.entities.User;
import com.sazzad.blog.repositories.PostRepository;
import com.sazzad.blog.services.CategoryService;
import com.sazzad.blog.services.PostService;
import com.sazzad.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    private static final int WORDS_PER_MINUTE = 200;

    @Override
    public Post getPost(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPost(UUID categoryId, UUID tagId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if(categoryId != null && tagId != null) {

            Category category = categoryService.findCategoryByID(categoryId);
            Tag tag = tagService.findTagById(tagId);

            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag,
                    pageable
            )
                    .getContent();
        }

        if(categoryId != null) {
            Category category = categoryService.findCategoryByID(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category,
                    pageable
            )
                    .getContent();
        }

        if(tagId != null) {

            Tag tag = tagService.findTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag,
                    pageable
            )
                    .getContent();
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED, pageable).getContent();

    }

    @Override
    public List<Post> getDraftPosts(User user, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT, pageable).getContent();
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {

        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryService.findCategoryByID(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.findTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist"));

        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();

        if(!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)) {
            Category newCategory = categoryService.findCategoryByID(updatePostRequestCategoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();

        if(!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> newTags = tagService.findTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {

        if(content == null || content.isEmpty()) {
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;

        return  (int)Math.ceil((double)wordCount / WORDS_PER_MINUTE);
    }
}
