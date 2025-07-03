package com.sazzad.blog.services;

import com.sazzad.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategory();

    Category creteCategory(Category category);

    void deleteCategory(UUID id);

    Category findCategoryByID(UUID id);
}
