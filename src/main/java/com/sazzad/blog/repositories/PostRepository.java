package com.sazzad.blog.repositories;

import com.sazzad.blog.domain.PostStatus;
import com.sazzad.blog.domain.entities.Category;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.Tag;
import com.sazzad.blog.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);

    Page<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag, Pageable pageable);
    Page<Post> findAllByStatusAndCategory(PostStatus status, Category category, Pageable pageable);
    Page<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag, Pageable pageable);
    Page<Post> findAllByStatus(PostStatus status, Pageable pageable);
    Page<Post> findAllByAuthorAndStatus(User author, PostStatus status, Pageable pageable);
}
