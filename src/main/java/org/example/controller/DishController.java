package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.dto.DishDto;
import org.example.pojo.Dish;
import org.example.pojo.Setmeal;
import org.example.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: DishController
 * Package: org.example.controller
 * Description: 菜品管理
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 17:12
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @GetMapping("page")
    public Result<Page> page(Integer page, Integer pageSize, String name) {
        log.info("page:{},pageSize:{},name:{}", page, pageSize, name);
        Page page1 = dishService.page(page, pageSize, name);
        return Result.success(page1);
    }

    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){
        log.info("dishDto:{}",dishDto.toString());
        dishService.savedishWishFlavor(dishDto);
        return Result.success("新增菜品成功");
    }
    @GetMapping("{id}")
    public Result<DishDto> getById(@PathVariable Long id){
        DishDto dishServiceByIdWithFlavor = dishService.getByIdWithFlavor(id);
        if (dishServiceByIdWithFlavor != null) {
            return Result.success(dishServiceByIdWithFlavor);
        }
        return Result.error("查询失败");
    }
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids){
        dishService.removeDishWithFlavor(ids);
            return Result.success("删除成功");
    }

    @PostMapping("status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status,@RequestParam List<Long> ids){
        dishService.status(ids,status);
        return Result.success("修改成功");
    }

    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return Result.success("修改成功");
    }

    @GetMapping("list")
    public Result<List<DishDto>> list(Dish dish){
        List<DishDto> list = dishService.list(dish);
        return Result.success(list);
    }

}
