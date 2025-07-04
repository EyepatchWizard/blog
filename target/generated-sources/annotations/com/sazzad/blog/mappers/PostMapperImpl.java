package com.sazzad.blog.mappers;

import com.sazzad.blog.domain.CreatePostRequest;
import com.sazzad.blog.domain.UpdatePostRequest;
import com.sazzad.blog.domain.dtos.AuthorDto;
import com.sazzad.blog.domain.dtos.CategoryDto;
import com.sazzad.blog.domain.dtos.CreatePostRequestDto;
import com.sazzad.blog.domain.dtos.PostDto;
import com.sazzad.blog.domain.dtos.TagDto;
import com.sazzad.blog.domain.dtos.UpdatePostRequestDto;
import com.sazzad.blog.domain.entities.Category;
import com.sazzad.blog.domain.entities.Post;
import com.sazzad.blog.domain.entities.Tag;
import com.sazzad.blog.domain.entities.User;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-05T02:07:27+0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto.PostDtoBuilder postDto = PostDto.builder();

        postDto.author( userToAuthorDto( post.getAuthor() ) );
        postDto.category( categoryToCategoryDto( post.getCategory() ) );
        postDto.tags( tagSetToTagDtoSet( post.getTags() ) );
        postDto.id( post.getId() );
        postDto.title( post.getTitle() );
        postDto.content( post.getContent() );
        postDto.readingTime( post.getReadingTime() );
        postDto.createdAt( post.getCreatedAt() );
        postDto.updatedAt( post.getUpdatedAt() );
        postDto.status( post.getStatus() );

        return postDto.build();
    }

    @Override
    public CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        CreatePostRequest.CreatePostRequestBuilder createPostRequest = CreatePostRequest.builder();

        createPostRequest.title( dto.getTitle() );
        createPostRequest.content( dto.getContent() );
        createPostRequest.categoryId( dto.getCategoryId() );
        Set<UUID> set = dto.getTagIds();
        if ( set != null ) {
            createPostRequest.tagIds( new LinkedHashSet<UUID>( set ) );
        }
        createPostRequest.status( dto.getStatus() );

        return createPostRequest.build();
    }

    @Override
    public UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UpdatePostRequest.UpdatePostRequestBuilder updatePostRequest = UpdatePostRequest.builder();

        updatePostRequest.title( dto.getTitle() );
        updatePostRequest.content( dto.getContent() );
        updatePostRequest.categoryId( dto.getCategoryId() );
        Set<UUID> set = dto.getTagIds();
        if ( set != null ) {
            updatePostRequest.tagIds( new LinkedHashSet<UUID>( set ) );
        }
        updatePostRequest.status( dto.getStatus() );

        return updatePostRequest.build();
    }

    protected AuthorDto userToAuthorDto(User user) {
        if ( user == null ) {
            return null;
        }

        AuthorDto.AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.id( user.getId() );
        authorDto.name( user.getName() );

        return authorDto.build();
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );

        return categoryDto.build();
    }

    protected TagDto tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    protected Set<TagDto> tagSetToTagDtoSet(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        Set<TagDto> set1 = new LinkedHashSet<TagDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Tag tag : set ) {
            set1.add( tagToTagDto( tag ) );
        }

        return set1;
    }
}
