package com.sazzad.blog.services.impl;

import com.sazzad.blog.domain.entities.Tag;
import com.sazzad.blog.repositories.TagRepository;
import com.sazzad.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public List<Tag> createTags(Set<String> tagNames) {

        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        Set<String> existingTagsName = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        List<Tag> newTags = tagNames.stream()
                .filter(name -> !existingTagsName.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();

        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        return savedTags;
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {

        tagRepository.findById(id).ifPresent(tag -> {
            if(!tag.getPosts().isEmpty()) {
                throw new IllegalStateException("Can not delete tag with posts");
            }

            tagRepository.deleteById(id);
        });
    }

    @Override
    public Tag findTagById(UUID id) {

        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id));
    }

    @Override
    public List<Tag> findTagByIds(Set<UUID> ids) {

        List<Tag> foundTags = tagRepository.findAllById(ids);

        if(foundTags.size() != ids.size()) {
            throw new  EntityNotFoundException("NOt all tag exists");
        }

        return foundTags;
    }
}
