package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Category;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 16:46
 * @Version 1.0
 */
public interface CategoryService extends IService<Category> {
    Page page(Integer page, Integer pageSize);

    List<Category> list(Category category);
}
