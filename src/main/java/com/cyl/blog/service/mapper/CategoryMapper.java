package com.cyl.blog.service.mapper;


import com.cyl.blog.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@SuppressWarnings("unchecked")
public interface CategoryMapper extends BaseMapper {

//  List<JMap> list();

  Category loadByName(String name);

  /**
   * 获取指定分类的子分类
   * 
   * @param category
   * @return
   */
  List<Category> loadChildren(Category category);

  void updateInsertLeftv(int rightv);

  void updateInsertRightv(int rightv);

  void delete(@Param("leftv") int leftv, @Param("rightv") int rightv);

  void updateDeleteLeftv(@Param("leftv") int leftv, @Param("length") int length);

  void updateDeleteRightv(@Param("rightv") int rightv, @Param("length") int length);

  List<Category> getCategorys(int limit);

  List<String> getCategoryIdsByCategory(Category category);

  List<Category> getCategorysByids(List<String> cids);

}
