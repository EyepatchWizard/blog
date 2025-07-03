package com.sazzad.blog.services.impl;

import com.sazzad.blog.domain.entities.Category;
import com.sazzad.blog.repositories.CategoryRepository;
import com.sazzad.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public Category creteCategory(Category category) {

        String categoryName = category.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)) {
            throw new IllegalArgumentException("Category already exist with name : " + categoryName);
        }

        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(UUID id) {

        Optional<Category> category = categoryRepository.findById(id);

        if(category.isPresent()) {
            if(!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Category has posts associated with it+");
            }

            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Category findCategoryByID(UUID id) {
         return categoryRepository.findById(id)
                 .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }


}
