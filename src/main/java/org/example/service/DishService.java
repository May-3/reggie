package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.DishDto;
import org.example.pojo.Dish;

import java.util.List;

/**
 * ClassName: DishService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 17:14
 * @Version 1.0
 */
public interface DishService extends IService<Dish> {
    Page page(Integer page, Integer pageSize, String name);

    void savedishWishFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void removeDishWithFlavor(List<Long> ids);

    void status(List<Long> ids, Integer status);

    void updateWithFlavor(DishDto dishDto);

    List<DishDto> list(Dish dish);
}
