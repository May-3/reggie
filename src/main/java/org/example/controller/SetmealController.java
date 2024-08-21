package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.dto.SetmealDto;
import org.example.pojo.Setmeal;
import org.example.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: SetmealController
 * Package: org.example.controller
 * Description: 套餐管理
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 12:06
 * @Version 1.0
 */
@RestController
@RequestMapping("setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("page")
    public Result<Page> page(Integer page, Integer pageSize, String name) {
        log.info("page:{},pageSize:{},name:{}", page, pageSize, name);
        Page page1 = setmealService.page(page, pageSize, name);
        return Result.success(page1);
    }

    @GetMapping("{id}")
    public Result<SetmealDto> getById(@PathVariable Long id) {
        SetmealDto setmealDto = setmealService.getByIdWithFlavor(id);
        return Result.success(setmealDto);
    }

    @PutMapping
    public Result<String> update(@RequestBody SetmealDto setmealDto) {
        setmealService.updateWithFlavor(setmealDto);
        return Result.success("修改成功");
    }

    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long> ids) {
        setmealService.removeBatchByIds(ids);
        return Result.success("删除成功");
    }

    @PostMapping("status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        setmealService.status(ids, status);
        return Result.success("修改成功");
    }
    @PostMapping
    public Result<SetmealDto> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithFlavor(setmealDto);
        return Result.success(setmealDto);
    }
    @GetMapping("list")
    public Result<List<Setmeal>> list(Setmeal setmeal) {
        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }
}
