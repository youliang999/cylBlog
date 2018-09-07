package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.helper.CategoryHelper;
import com.cyl.blog.backend.vo.CategoryFormValidator;
import com.cyl.blog.backend.vo.CategoryVo;
import com.cyl.blog.entity.Category;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.util.IdGenerator;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/9/6.
 */
@Controller("adminCategoryController")
@RequestMapping("/backend/categorys")
@RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "backend/new/category";
    }

    @ResponseBody
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String data(){
        List<Category> list = categoryHelper.listAsTree();
        List<CategoryVo> vos = list.stream()
                .map(category -> {
                    CategoryVo categoryVo = new CategoryVo();
                    categoryVo.setId(category.getId());
                    categoryVo.setVisible(category.isVisible());
                    categoryVo.setText(category.getName());
                    return categoryVo;
                }).collect(Collectors.toList());
        log.info("===>>> vos:{}", vos);
        Map<String, Object> data = new HashMap<>();
        data.put("list", vos);
        data.put("icon", "glyphicon glyphicon-star");
        return new Gson().toJson(vos);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object insert(Category category, String parent){
        Map<String, Object> form = CategoryFormValidator.validateInsert(category);
        if(!form.isEmpty()){
            form.put("success", false);
            return form;
        }

        category.setId(IdGenerator.uuid19());
        category.setCreateTime(new Date());
        category.setLastUpdate(category.getCreateTime());
        log.info("===>>> category:{}", category);
        return new HashMap<>().put("success", categoryService.insertChildren(category, parent));
    }

    @ResponseBody
    @RequestMapping(value = "/{categoryName}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable String categoryName){
        categoryHelper.remove(categoryName);
        return new HashMap<>().put("success", true);
    }

}
