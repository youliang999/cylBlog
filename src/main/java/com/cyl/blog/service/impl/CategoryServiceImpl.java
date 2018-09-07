package com.cyl.blog.service.impl;


import com.cyl.blog.constants.CategoryConstants;
import com.cyl.blog.entity.Category;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.CategoryMapper;
import com.cyl.blog.util.IdGenerator;
import com.cyl.blog.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl extends BlogBaseServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public boolean insertChildren(Category category, String parentName){
        Category parent = loadByName(StringUtils.isBlank(parentName) ? CategoryConstants.ROOT : parentName);
        category.setLeftv(parent.getRightv());
        category.setRightv(parent.getRightv() + 1);

        categoryMapper.updateInsertLeftv(parent.getRightv());
        categoryMapper.updateInsertRightv(parent.getRightv());
        insert(category);

        return true;
    }

    @Transactional
    public boolean insertAfter(Category category, Category sbling){
        category.setLeftv(sbling.getRightv() + 1);
        category.setRightv(sbling.getRightv() + 2);

        categoryMapper.updateInsertLeftv(sbling.getRightv());
        categoryMapper.updateInsertRightv(sbling.getRightv());
        insert(category);

        return true;
    }

    /**
     * 此方法只被CategoryManager调用
     *
     * @param category
     * @return
     */
    @Transactional
    public void remove(Category category){
        int length = category.getRightv() - category.getLeftv() + 1;
        /* 注意:delete须第一个执行,因为updateDeleteLeftv会有影响 */
        categoryMapper.delete(category.getLeftv(), category.getRightv());
        categoryMapper.updateDeleteLeftv(category.getLeftv(), length);
        categoryMapper.updateDeleteRightv(category.getRightv(), length);
    }

    /**
     * 获取指定分类的子分类
     *
     * @param category
     * @return
     */
    public List<Category> loadChildren(Category category){
        return categoryMapper.loadChildren(category);
    }

    public void init(){
        Category root = new Category();
        root.setId(IdGenerator.uuid19());
        root.setLeftv(1);
        root.setName(CategoryConstants.ROOT);
        root.setRightv(2);
        insert(root);
    }

    public Category loadByName(String name){
        return categoryMapper.loadByName(name);
    }

    @Override
    public List<Category> getCategorys(int limit) {
        return categoryMapper.getCategorys(limit);
    }

    @Override
    public List<String> getCategoryIdsByCategory(Category category) {
        return categoryMapper.getCategoryIdsByCategory(category);
    }

    @Override
    public List<Category> getCategorysByids(List<String> cids) {
        return categoryMapper.getCategorysByids(cids);
    }

    @Override
    public BaseMapper getMapper() {
        return categoryMapper;
    }
}
