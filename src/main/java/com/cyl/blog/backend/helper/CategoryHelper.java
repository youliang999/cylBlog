package com.cyl.blog.backend.helper;

import com.cyl.blog.entity.Category;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.OptionsService;
import com.cyl.blog.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/6.
 */
@Component
public class CategoryHelper {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private OptionsService optionsService;


    public List<Category> listAsTree(){
        List<Category> preOrder = categoryService.getCategorys(20);
        if(CollectionUtils.isEmpty(preOrder))
            return Collections.emptyList();
        return preOrder;
    }

    /**
     * 删除分类同时,将该分类下的文章移动到默认分类
     *
     * @param cname
     * @return
     */
    @Transactional
    public void remove(String cname){
        Category category = categoryService.loadByName(cname);
        List<Category> list = categoryService.loadChildren(category);
        List<String> all = new ArrayList<>(list.size() + 1);
        all.add(category.getId());
        for(Category temp : list){
            all.add(temp.getId());
        }
            /* 先更新post的categoryid，再删除category,外键约束 */
        //(all, optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));
        categoryService.remove(category);
    }
}
