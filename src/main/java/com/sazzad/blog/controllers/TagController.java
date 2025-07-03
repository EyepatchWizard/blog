package com.sazzad.blog.controllers;

import com.sazzad.blog.domain.dtos.CreateTagRequest;
import com.sazzad.blog.domain.dtos.TagDto;
import com.sazzad.blog.domain.entities.Tag;
import com.sazzad.blog.mappers.TagMapper;
import com.sazzad.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags(){

        List<Tag> tags = tagService.getTags();
        List<TagDto> tagRespons = tags.stream().map(tagMapper::toTagResponse).toList();
        return ResponseEntity.ok(tagRespons);

    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagRequest createTagRequest) {

        List<Tag> savedTags = tagService.createTags(createTagRequest.getNames());
        List<TagDto> createdTagRespons = savedTags.stream()
                .map(tagMapper::toTagResponse)
                .toList();
        return new ResponseEntity<>(
                createdTagRespons,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {

        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
