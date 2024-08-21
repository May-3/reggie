package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Category;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: CategoryController
 * Package: org.example.controller
 * Description: 菜品及套餐分类
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 16:44
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("page")
    public Result<Page> page(Integer page, Integer pageSize){
        log.info("page:{},pageSize:{}",page,pageSize);
        Page page1 = categoryService.page(page, pageSize);
        return Result.success(page1);
    }

    @PostMapping
    public Result<String> save(@RequestBody Category category){
        log.info("category:{}",category.toString());
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody Category category){
        log.info("修改分类信息:{}",category);
        categoryService.updateById(category);
        return Result.success("修改分类信息成功");
    }
    @DeleteMapping
    public Result<String> delete(@RequestParam(name = "ids") Long id){
        log.info("删除分类:{}",id);
        categoryService.removeById(id);
        return Result.success("删除分类成功");
    }
    @GetMapping("list")
    public Result<List<Category>> list(Category category){
        log.info("查询分类信息:{}",category);
        List<Category> list = categoryService.list(category);
        return Result.success(list);
    }
}
