package com.cyl.blog.service;


import com.cyl.blog.entity.Category;

import java.util.List;

public interface CategoryService extends BlogBaseService{

    boolean insertChildren(Category category, String parentName);

    boolean insertAfter(Category category, Category sbling);

    void remove(Category category);

    Category loadByName(String name);

    List<Category> loadChildren(Category category);

    List<Category> getCategorys(int limit);

    List<String> getCategoryIdsByCategory(Category category);

    List<Category> getCategorysByids(List<String> bids);
}
