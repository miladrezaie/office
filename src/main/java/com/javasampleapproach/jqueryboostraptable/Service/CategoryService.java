package com.javasampleapproach.jqueryboostraptable.Service;


import com.javasampleapproach.jqueryboostraptable.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<Category> getAllCategories(Pageable page);

    List<Category> findAll();

    Optional<Category> findById(long category);

    void saveCategory(Category category);

    void deleteCategory(Long id);

    Category getCategory(Long id);

    Optional<Category> findByIdCategory(Long id);
}
