package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.CategoryMapper;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: CategoryServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 16:46
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public Page page(Integer page, Integer pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        categoryMapper.selectPage(categoryPage,null);
        return categoryPage;
    }

    @Override
    public List<Category> list(Category category) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        List<Category> categoryList = categoryMapper.selectList(queryWrapper);
        return categoryList;
    }
}
