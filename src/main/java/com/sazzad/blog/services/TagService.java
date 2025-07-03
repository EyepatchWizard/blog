package com.sazzad.blog.services;

import com.sazzad.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<Tag> getTags();
    List<Tag> createTags(Set<String> names);
    void deleteTag(UUID id);
    Tag findTagById(UUID id);
    List<Tag> findTagByIds(Set<UUID> ids);

}
