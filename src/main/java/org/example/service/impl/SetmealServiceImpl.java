package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.CustomException;
import org.example.dto.SetmealDto;
import org.example.mapper.SetmealMapper;
import org.example.pojo.Dish;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;
import org.example.service.CategoryService;
import org.example.service.DishService;
import org.example.service.SetmealDishService;
import org.example.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: SetmealServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 12:08
 * @Version 1.0
 */
@Service

public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;
    @Override
    public Page page(Integer page, Integer pageSize, String name) {

        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null,Setmeal::getName,name);
        setmealMapper.selectPage(setmealPage, queryWrapper);
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<SetmealDto> collect = setmealPage.getRecords().stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            String categoryName = categoryService.getById(categoryId).getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(collect);
        return setmealDtoPage ;
    }

    @Override
    public SetmealDto getByIdWithFlavor(Long id) {
        Setmeal setmeal = this.getById(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        SetmealDto setmealDto = new SetmealDto();
        setmealDto.setSetmealDishes(list);
        BeanUtils.copyProperties(setmeal,setmealDto);
        return setmealDto;
    }

    @Override
    public void updateWithFlavor(SetmealDto setmealDto) {
        this.updateById(setmealDto);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
        List<SetmealDish> collect = setmealDto.getSetmealDishes().stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(collect);

    }

    @Override
    public void removeBatchByIds(List<Long> ids) {
        List<Setmeal> setmeals = this.listByIds(ids);

        boolean allStopped = setmeals.stream().allMatch(setmeal -> setmeal.getStatus() == 0);
        if (allStopped) {
            //先删除套餐
            this.removeByIds(ids);
            //再删除套餐对应的菜品
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            List<SetmealDish> list = setmealDishService.list(queryWrapper.in(SetmealDish::getSetmealId, ids));
            List<Long> collect = list.stream().map((item) -> {
                return item.getId();
            }).collect(Collectors.toList());
            setmealDishService.removeByIds(collect);

        }else {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
    }

    @Override
    public void status(List<Long> ids, Integer status) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> setmeals = setmealMapper.selectList(setmealLambdaQueryWrapper);
        setmeals.stream().map((item)->{
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        setmealMapper.updateById(setmeals);
    }

    @Override
    public void saveWithFlavor(SetmealDto setmealDto) {
        this.save(setmealDto);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());

        List<SetmealDish> collect = setmealDto.getSetmealDishes().stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(collect);
    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        return setmealMapper.selectList(queryWrapper);
    }


}
