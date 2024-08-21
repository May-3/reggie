package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.CustomException;
import org.example.common.Result;
import org.example.dto.DishDto;
import org.example.mapper.DishFlavorMapper;
import org.example.mapper.DishMapper;
import org.example.pojo.Dish;
import org.example.pojo.DishFlavor;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;
import org.example.service.CategoryService;
import org.example.service.DishFlavorService;
import org.example.service.DishService;
import org.example.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: DishServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 17:14
 * @Version 1.0
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;


    @Override
    public Page page(Integer page, Integer pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Dish> like = queryWrapper.like(name != null, Dish::getName, name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishMapper.selectPage(dishPage,like);
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");
        List<DishDto> collect = dishPage.getRecords().stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(collect);

        return dishDtoPage;
    }

    @Override
    public void savedishWishFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.stream().map((item)->{
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        dishFlavorMapper.insert(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);

        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        DishDto dishDto = new DishDto();
        dishDto.setFlavors(list);

        BeanUtils.copyProperties(dish,dishDto);

        return dishDto;
    }

    @Override
    public void removeDishWithFlavor(List<Long> ids) {

        List<Dish> list = this.listByIds(ids);
        boolean allStopped = list.stream().allMatch(dish -> dish.getStatus() == 0);

        if(allStopped){
            LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealDishLambdaQueryWrapper.in(SetmealDish::getDishId,ids);
            long count = setmealDishService.count(setmealDishLambdaQueryWrapper);
            if(count>0){
                throw  new CustomException("菜品正在被套餐使用，不能删除");
            }else {
                this.removeByIds(ids);
                LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.in(DishFlavor::getDishId, ids);
                dishFlavorService.remove(queryWrapper);
            }
        }
        throw new CustomException("菜品正在售卖中，不能删除");

    }

    @Override
    public void status(List<Long> ids, Integer status) {

        List<Dish> list = this.listByIds(ids);
        list.stream().map((item)->{
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        dishMapper.updateById(list);

    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorMapper.insert(flavors);
    }

    @Override
    public List<DishDto> list(Dish dish) {
        List<DishDto> dishDtoList = new ArrayList<>();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        List<Dish> dishes = dishMapper.selectList(queryWrapper);
        BeanUtils.copyProperties(dishes,dishDtoList,"records");
        dishDtoList = dishes.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getId();
            dishDto.setFlavors(dishFlavorService.list(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id)));
            return dishDto;
        }).collect(Collectors.toList());
        return dishDtoList;
    }
}
